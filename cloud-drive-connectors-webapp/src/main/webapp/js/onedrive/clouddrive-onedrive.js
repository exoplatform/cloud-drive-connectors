/*
 * Copyright (C) 2022 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
(function ($, cloudDriveDocuments, utils, socketIO) {

  function OneDriveClient() {

    // TODO Deprecated
    const drives = {};

    // TODO Deprecated
    function ChangesSubscription(userId, notificationUrl) {
      const self = this;
      this.changed = false;
      this.userId = userId;

      let socket = socketIO.io(notificationUrl);
      socket.on("notification", function(data) {
        console.log("> OneDrive notification: " + data);
        // TODO here we should immediately invoke change processing!!!
        self.changed = true;
      });
    }

    // TODO Deprecated
    var processChange = function (subscription, process, drive) {

      console.log("> OneDrive processChange");
      const nowTime = new Date().getTime();

      if (nowTime >= drive.state.expirationDateTime) {
        renewState(process, drive);
        return;
      }
      if (subscription.changed) {
        subscription.changed = false;
        process.resolve();
      } else {
        // TODO why we need re-execute it every second?
        setTimeout(function () {
          processChange(subscription, process, drive);
        }, 1000);
      }
    };

    // TODO cleanup
    // var findInMap = function (map, userId) {
    //   var mapIter = map.values();
    //   var element;
    //   while (element = mapIter.next().value) {
    //     if (userId && element.userId) {
    //       console.log('userId ' + userId + ' elementUserId ' + element.userId);
    //
    //       if (userId.includes(element.userId)) {
    //         console.log(element);
    //         return element;
    //       } else {
    //         console.log('not includes: = ' + element.userId);
    //       }
    //     }
    //   }
    //   return null;
    // };

    // TODO Deprecated
    var renewState = function(process, drive) {
      console.log(">> OneDrive renew state");
      cloudDriveDocuments.getState(drive).done(function(res) {
        drive.state = res;
        // TODO cleanup
        // if (oneDrives.has(drive.state.creatorId)) {
        // oneDrives.delete(drive.state.creatorId);
        // }
        delete drives[drive.state.creatorId];
        process.resolve();
      }).fail(function(response, status, err) {
        process.reject("Error getting new changes link. " + err + " (" + status + ")");
      });
    };

    // TODO Deprecated
    this.onChangeOld = function(drive) {
      console.log("> OneDrive onChange");
      const process = $.Deferred();
      if (drive) {
        if (drive.state) {
          const nowTime = new Date().getTime();
          if (nowTime >= drive.state.expirationDateTime) {
            renewState(process, drive);
          } else {
            // TODO cleanup
            // if (drive.state.creatorId) {
            //   console.log('creatorId = ' + drive.state.creatorId);
            // }else{
            //   console.log('creatorId = undefined' );
            // }
            // if (!oneDrives.has(drive.state.creatorId)) {
            //   oneDrives.set(drive.state.creatorId, new OneDriveSubscription(drive.state.creatorId, drive.state.url));
            // }

            let subscription = drives[drive.state.creatorId];
            if (!subscription) {
              subscription = new ChangesSubscription(drive.state.creatorId, drive.state.url);
              drives[drive.state.creatorId] = subscription;
            }

            processChange(subscription, process, drive);
          }
        } else {
          process.reject("Cannot check for changes. No state object for CloudDrive on " + drive.path);
        }
      } else {
        process.reject("Null drive in onChange()");
      }
      return process.promise();
    };
    
    // ******** New implementation of onChange() using asynchronous notifications and changes queue ********  
    
    let socket;
    let changesQueue = [];
    
    var initListener = function(driveState, change) {
      console.log("> OneDrive initListener");
      // Here we start/continue listen on Socket for changes
      socket = socketIO.io(driveState.url);
      socket.on("notification", function(data) {
        console.log(">> OneDrive changes notification: " + data);
        // Tell CloudDrive core script to run the drive synchronization, 
        // here we may return a timeout to wait for a next changes (default 10sec)
        change = changesQueue.slice(-1).pop();
        if (change /*&& change.state() == "pending"*/) {
          // it's OK if already resolved
          // but if rejected (by socket disconnect/error), then need review the logic
          change.resolve();
        } else {
          changesQueue.push($.Deferred().resolve());
        } // otherwise, it's already tracked a change but not yet consumed
      }).on("connect", function(attemptNumber) {
        console.log("<<< initListener: connect notified");
      }).on("disconnect", function(reason) {
        console.log(">>> initListener: disconnect notified with reason: " + reason);
        // reason (String) either ‘io server disconnect’, ‘io client disconnect’, or ‘ping timeout’
        if (reason === "io server disconnect") {
          // the disconnection was initiated by the server, you need to reconnect manually
          // TODO may be we need a new changes link here?
          socket.open();
          console.log("<<< initListener: disconnect notified, socket opened");
        } // else the socket will automatically try to reconnect
      }).on("reconnect_attempt", function(attemptNumber) {
        console.log(">>> initListener: reconnect_attempt notified with attemptNumber: " + attemptNumber);
      }).on("reconnecting", function(attemptNumber) {
        console.log(">>> initListener: reconnecting notified with attemptNumber: " + attemptNumber);
      }).on("reconnect", function(attemptNumber) {
        console.log("<<< initListener: reconnect notified with attemptNumber: " + attemptNumber);
      }).on("reconnect_failed", function() {
        // TODO reject/resolve last process or get fresh changes link?
        console.log("<<< initListener: reconnect_failed notified");
      }).on("reconnect_error", function(error) {
        // TODO reject/resolve last process
        console.log("<<< initListener: reconnect_error notified, error: " + error);
      }).on("connect_error", function(error) {
        // TODO reject/resolve last process or get fresh changes link?
        console.log("<<< initListener: connect_error notified, error: " + error);
      }).on("connect_timeout", function(timeout) {
        // TODO reject/resolve last process or get fresh changes link or need wait and try again?
        console.log("<<< initListener: connect_timeout notified, error: " + timeout);
      }).on("error", function(error) {
        // TODO reject/resolve last process or get fresh changes link or need wait and try again?
        console.log("<<< initListener: error notified, error: " + error);
      });
    };
    
    const closeListener = function() {
      if (socket) {
        // close the socket 
        socket.close();
        socket = null;
      }
    };
    
    this.onChange = function(drive) {
      console.log("> OneDrive onChange");
      let process = changesQueue.shift(); // remove from the queue if any
      if (!process) {
        // Establish a new listener or wait for notifications from already established one.
        // Insert sooner for concurrent calls of onChange() and from initListener()
        changesQueue.push(process = $.Deferred());
        if (drive) {
          if (drive.state) {
            let state;
            if (new Date().getTime() >= drive.state.expirationDateTime) {
              // We need renew the state to get fresher changes link in it
              state = cloudDriveDocuments.getState(drive);
              // close expired listener 
              closeListener();
            } else if (!socket) {
              state = $.Deferred().resolve(drive.state);
            }
            if (state) {
              // Use given drive state to establish a changes listener
              state.done(function(driveState) {
                // Here we start listen on Socket for changes
                initListener(driveState);
              }).fail(function(response, status, err) {
                // Drive state request failed
                process.reject("Error getting drive state with changes link: " + err + " (" + status + ")");
              });          
            } // otherwise we already have an established listener - continue wait for it
          } else {
            process.reject("Cannot check for changes. No state object for CloudDrive on " + drive.path);
          }
        } else {
          process.reject("Null drive in onChange()");
        }
        process.always(function() {
          // When complete (resolved or rejected), we remove the process created here from the queue - 
          // it's already consumed by returning its promise from this method
          changesQueue = changesQueue.filter(function(p) {
            return p !== process;
          });
        });
      } // otherwise, change was already tracked by currently established listener - return (consume) it
      return process.promise();
    };
  }

  return new OneDriveClient();

})($, cloudDriveDocuments, cloudDriveUtils, cloudDriveSocketIO);