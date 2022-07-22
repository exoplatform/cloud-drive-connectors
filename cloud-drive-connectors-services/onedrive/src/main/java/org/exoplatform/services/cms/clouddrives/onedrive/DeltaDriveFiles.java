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

import java.util.List;

import com.microsoft.graph.models.extensions.DriveItem;

/**
 * Stores data that we receive when track changes: new items since the previous
 * call and token for getting changes later.
 */
class DeltaDriveFiles {
  private String          deltaToken;

  private List<DriveItem> items;

  DeltaDriveFiles(String deltaToken, List<DriveItem> items) {
    this.deltaToken = deltaToken;
    this.items = items;
  }

  public String getDeltaToken() {
    return deltaToken;
  }

  public void setDeltaToken(String deltaToken) {
    this.deltaToken = deltaToken;
  }

  public List<DriveItem> getItems() {
    return items;
  }

  public void setItems(List<DriveItem> items) {
    this.items = items;
  }
}
