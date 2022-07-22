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
package org.exoplatform.services.cms.clouddrives.gdrive;

import java.io.IOException;

/**
 * Indicates that Google client was unable to aquire or restore access token and need an user action to reconnect the drive.<br>
 * Created by The eXo Platform SAS
 * 
 * @author <a href="mailto:pnedonosko@exoplatform.com">Peter Nedonosko</a>
 * @version $Id: AccessTokenNotRestoredException.java 00000 Nov 28, 2019 pnedonosko $
 */
public class AccessTokenNotRestoredException extends IOException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 639413195582762663L;

  /**
   * Instantiates a new access token not restored exception.
   *
   * @param message the message
   */
  public AccessTokenNotRestoredException(String message) {
    super(message);
  }

}
