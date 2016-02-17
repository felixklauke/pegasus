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
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class Client {

    /* ----------------------------------- [ Fields ] ----------------------------------- */

    /**
     * The logger to log all information
     */
    private Logger logger;

    /*
     * The NettyClient that will be created after calling the Cunstructor or the main Method.
     */
    private NettyClient nettyClient;

    /* ----------------------------------- [ Constructors ] ----------------------------------- */

    /**
     * The Logger and the NettyClient will be created.
     */
    public Client() {
        logger = Logger.getLogger(Client.class.getSimpleName());
        nettyClient = new NettyClient(logger);
    }

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * Nothing to say... zzzzzZZZZZ
     *
     * @param args args... use them or let it be...
     */
    public static void main( String[] args ) {
        new Client().start();
    }

    /**
     * Start the NettyClient
     */
    public void start() {
        nettyClient.start();
    }

    /**
     * @return the NettyClient
     */
    public NettyClient getNettyClient() {
        return nettyClient;
    }
}
