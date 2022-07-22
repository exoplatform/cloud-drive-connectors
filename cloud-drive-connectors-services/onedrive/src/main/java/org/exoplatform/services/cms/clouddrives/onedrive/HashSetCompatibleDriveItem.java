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

import com.microsoft.graph.models.extensions.DriveItem;

/**
 * DriveItem wrapper for use with HashSet.
 */
class HashSetCompatibleDriveItem {
  DriveItem item;

  public HashSetCompatibleDriveItem(DriveItem item) {
    this.item = item;
  }

  public DriveItem getItem() {
    return item;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    HashSetCompatibleDriveItem that = (HashSetCompatibleDriveItem) o;

    return item.id.equals(that.item.id);
  }

  @Override
  public int hashCode() {
    return item.id.hashCode();
  }
}
