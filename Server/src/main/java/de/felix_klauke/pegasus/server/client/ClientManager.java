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

package de.felix_klauke.pegasus.server.client;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;

import java.util.List;

/**
 * Created by Felix Klauke for project Pegasus on 07.02.2016.
 */
public class ClientManager {

    /* ------------------------- [ Fields ] ------------------------- */

    /**
     * All clients will be saved in this List. In future releases this will be replaced by a Guava Cache to add a
     * TimeOut for authed clients.
     */
    private List< Client > clients;

    /* ------------------------- [ Constructors ] ------------------------- */

    public ClientManager() {
        this.clients = Lists.newArrayList();
    }

    /* ------------------------- [ Methods ] ------------------------- */

    /**
     * Get a Client by its username
     *
     * @param username the username of the client
     * @return the client associated with the username. When no one is known this method will return null.
     */
    public Client getClient( String username ) {
        for ( Client c : clients ) {
            if ( c.getUsername().equalsIgnoreCase( username ) ) return c;
        }
        return null;
    }

    /**
     * get a Client by its Netty Channel
     *
     * @param channel the channel that is used by the client in Netty
     * @return the Client associated with the channel. When no one is known this method will return null.
     */
    public Client getClient( Channel channel ) {
        for ( Client c : clients ) {
            if ( c.getChannel() == channel ) return c;
        }
        return null;
    }

    /**
     * This method will be used whenever a new client connects to the server and sends a valid PacketHandshake
     *
     * @param client the client to register
     */
    public void registerClient( Client client ) {
        clients.add( client );
    }

    /**
     * This method will be used whenever a new client disconnects from the server.
     *
     * @param client the client to unregister
     */
    public void unregisterClient( Client client ) {
        clients.remove( client );
    }

    /**
     * This method will authenticate a Client with the given password.
     *
     * @param client   the client to auth
     * @param password the password to try to auth the client with
     */
    public void authClient( Client client, String password ) {
        //TODO: Add authentication
        client.setAuthed( false );
    }

    public List< Client > getClients() {
        return clients;
    }

}
