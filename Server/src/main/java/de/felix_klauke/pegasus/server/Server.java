/*
 * Copyright 2016 Felix Klauke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.felix_klauke.pegasus.server;

import de.felix_klauke.pegasus.server.client.ClientManager;
import de.felix_klauke.pegasus.server.network.NettyServer;

import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 05.02.2016.
 */
public class Server {

    /* ------------------------- [ Fields ] ------------------------- */

    /**
     * The port for the server that will be started. It has to be same the clients connect to.
     */
    private static final int SERVER_PORT = 27816;

    /**
     * The basic Logger for the whole Server
     */
    private static final Logger logger = Logger.getLogger( Server.class.getSimpleName() );

    /**
     * Singleton
     */
    private static Server instance;

    private final NettyServer nettyServer;
    private final ClientManager clientManager;

    /* ------------------------- [ Constructors ] ------------------------- */

    public Server() {
        instance = this;
        nettyServer = new NettyServer( SERVER_PORT );
        clientManager = new ClientManager();
    }

    /* ------------------------- [ Methods ] ------------------------- */

    public static Logger getLogger() {
        return logger;
    }

    public static void main( String[] args ) {
        new Server().getNettyServer().start();
    }

    public static Server getInstance() {
        return instance;
    }

    public NettyServer getNettyServer() {
        return nettyServer;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

}
