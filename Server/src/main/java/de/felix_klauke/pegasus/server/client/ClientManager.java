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
import de.felix_klauke.pegasus.protocol.Packet;
import io.netty.channel.Channel;

import java.util.List;

/**
 * Created by Felix Klauke for project Pegasus on 07.02.2016.
 */
public class ClientManager {

    private List< Client > clients;

    public ClientManager() {
        this.clients = Lists.newArrayList();
    }

    public List< Client > getClients() {
        return clients;
    }

    public Client getClient( String username ) {
        for ( Client c : clients ) {
            if ( c.getUsername().equalsIgnoreCase( username ) ) return c;
        }
        return null;
    }

    public Client getClient( Channel channel ) {
        for ( Client c : clients ) {
            if ( c.getChannel() == channel ) return c;
        }
        return null;
    }

    public void registerClient( Client client ) {
        clients.add( client );
    }

    public void sendPacket( Client client, Packet packet ) {
        client.sendPacket( packet );
    }

    public void authClient( Client client, String password ) {
        client.setAuthed( true );
    }
}
