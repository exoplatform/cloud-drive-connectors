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

import org.exoplatform.services.cms.clouddrives.CloudDriveException;
import org.exoplatform.services.cms.clouddrives.CloudProvider;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

/**
 * The Class OneDriveProvider.
 */
public class OneDriveProvider extends CloudProvider {
  protected static final Log LOG = ExoLogger.getLogger(OneDriveProvider.class);
  private final String authUrl;
  private final String redirectUrl;

  /**
   * Instantiates a new cloud provider.
   *
   * @param id the id
   * @param name the name
   */
  protected OneDriveProvider(String id, String name, String authUrl, String redirectUrl) {
    super(id, name);
    this.authUrl = authUrl;
    this.redirectUrl = redirectUrl;
  }

  @Override
  public String getAuthURL() throws CloudDriveException {
    return this.authUrl;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  @Override
  public boolean retryOnProviderError() {
    return false;
  }
}
