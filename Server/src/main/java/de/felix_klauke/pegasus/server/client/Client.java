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

import de.felix_klauke.pegasus.protocol.Packet;
import io.netty.channel.Channel;

/**
 * Created by Felix Klauke for project Pegasus on 07.02.2016.
 */
public class Client {

    /* ------------------------- [ Fields ] ------------------------- */

    private String username;
    private Channel channel;
    private boolean authed;

    /* ------------------------- [ Constructors ] ------------------------- */

    public Client( String username, Channel channel ) {
        this.username = username;
        this.channel = channel;
        this.authed = false;
    }

    /* ------------------------- [ Methods ] ------------------------- */

    /**
     * This Method will send the packet directly.
     *
     * @param packet the packet to send
     */
    public void sendPacket( Packet packet ) {
        channel.writeAndFlush( packet );
    }

    /**
     * This Method will write all given packets in a buffer and will flush them after adding all.
     *
     * @param packets
     */
    public void sendPackets( Packet... packets ) {
        for ( Packet packet : packets ) {
            channel.write( packet );
        }
        channel.flush();
    }

    public Channel getChannel() {
        return channel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public boolean isAuthed() {
        return authed;
    }

    public void setAuthed( boolean authed ) {
        this.authed = authed;
    }
}
