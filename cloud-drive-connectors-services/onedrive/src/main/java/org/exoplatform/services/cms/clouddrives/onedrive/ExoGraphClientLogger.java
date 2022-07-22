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
package org.exoplatform.services.cms.clouddrives.onedrive;

import com.microsoft.graph.logger.ILogger;
import com.microsoft.graph.logger.LoggerLevel;

import org.exoplatform.services.log.Log;

/**
 * Exo logger adapter for use in graph client.
 */
class ExoGraphClientLogger implements ILogger {

  private final Log log;

  public ExoGraphClientLogger(Log log) {
    this.log = log;
  }

  @Override
  public void setLoggingLevel(LoggerLevel loggerLevel) {

  }

  @Override
  public LoggerLevel getLoggingLevel() {
    return LoggerLevel.DEBUG;
  }

  @Override
  public void logDebug(String s) {
    if (log.isDebugEnabled()) {
      log.debug(s);
    }
  }

  @Override
  public void logError(String s, Throwable throwable) {
    log.error(s, throwable);
  }
}
