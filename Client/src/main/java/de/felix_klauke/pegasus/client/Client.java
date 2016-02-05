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

package de.felix_klauke.pegasus.client;

import de.felix_klauke.pegasus.client.network.NettyClient;

import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 05.02.2016.
 */
public class Client {

    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 27816;

    private static final Logger logger = Logger.getLogger( Client.class.getSimpleName() );
    private final NettyClient nettyServer;

    public Client() {
        nettyServer = new NettyClient( SERVER_HOSTNAME, SERVER_PORT );
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void main( String[] args ) {
        new Client().getNettyClient().start();
    }

    public NettyClient getNettyClient() {
        return nettyServer;
    }

}
