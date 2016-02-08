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

package de.felix_klauke.pegasus.client.handler;

import com.google.common.collect.Lists;
import de.felix_klauke.pegasus.client.handler.listener.PacketListener;
import de.felix_klauke.pegasus.protocol.Packet;
import io.netty.channel.Channel;

import java.util.List;

/**
 * Created by Felix Klauke for project Pegasus on 08.02.2016.
 */
public class PacketHandler {

    /* ------------------------- [ Fields ] ------------------------- */

    private List< PacketListener > listeners;

    /* ------------------------- [ Constructors ] ------------------------- */

    public PacketHandler() {
        this.listeners = Lists.newArrayList();
    }

    /* ------------------------- [ Methods ] ------------------------- */

    public void registerListener( PacketListener packetListener ) {
        listeners.add( packetListener );
    }

    public void handlePacket( Channel channel, Packet packet ) {
        for ( final PacketListener listener : listeners ) {
            if ( listener.getClazz() == packet.getPacketType().getPacketClass() ) {
                listener.handlePacket( channel, packet );
            }
        }
    }

}
