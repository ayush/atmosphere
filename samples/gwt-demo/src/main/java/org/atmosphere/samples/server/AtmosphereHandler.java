/*
 * Copyright 2011 Jeanfrancois Arcand
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.atmosphere.samples.server;

import java.io.IOException;
import java.util.concurrent.Executors;
import javax.servlet.ServletException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import org.atmosphere.gwt.server.GwtAtmosphereResource;
import org.atmosphere.gwt.server.AtmosphereGwtHandler;

/**
 *
 * @author p.havelaar
 */
public class AtmosphereHandler extends AtmosphereGwtHandler {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        Logger.getLogger("").setLevel(Level.INFO);
        Logger.getLogger("gwtcomettest").setLevel(Level.ALL);
        Logger.getLogger("").getHandlers()[0].setLevel(Level.ALL);
        logger.trace("Updated logging levels");
    }

    @Override
    public int doComet(GwtAtmosphereResource resource) throws ServletException, IOException {
        resource.getBroadcaster().setID("GWT_COMET");
        HttpSession session = resource.getAtmosphereResource().getRequest().getSession(false);
        if (session != null) {
            logger.debug("Got session with id: " + session.getId());
            logger.debug("Time attribute: " + session.getAttribute("time"));
        } else {
            logger.warn("No session");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Url: " + resource.getAtmosphereResource().getRequest().getRequestURL()
                    + "?" + resource.getAtmosphereResource().getRequest().getQueryString());
        }
        return NO_TIMEOUT;
    }

    @Override
    public void cometTerminated(GwtAtmosphereResource cometResponse, boolean serverInitiated) {
        super.cometTerminated(cometResponse, serverInitiated);
        logger.debug("Comet disconnected");
    }

}
