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
package org.exoplatform.services.cms.clouddrives.onedrive.ecms;

import org.exoplatform.ecm.webui.component.explorer.control.listener.UIActionBarActionListener;
import org.exoplatform.ecm.webui.BaseConnectActionComponent;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.event.Event;

@ComponentConfig(events = { @EventConfig(listeners = ConnectOneDriveActionComponent.ConnectOneDriveActionListener.class) })
public class ConnectOneDriveActionComponent extends BaseConnectActionComponent {

  /**
   * OneDrive  id from configuration - onedrive.
   */
  protected static final String PROVIDER_ID = "onedrive";


  public static class ConnectOneDriveActionListener extends UIActionBarActionListener<ConnectOneDriveActionComponent> {

    /**
     * {@inheritDoc}
     */
    public void processEvent(Event<ConnectOneDriveActionComponent> event) throws Exception {
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getProviderId() {
    return PROVIDER_ID;
  }
}
