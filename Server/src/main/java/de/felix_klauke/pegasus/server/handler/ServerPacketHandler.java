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

package de.felix_klauke.pegasus.server.handler;

import de.felix_klauke.pegasus.protocol.Packet;
import de.felix_klauke.pegasus.server.handler.listener.PacketHandshakeListener;
import de.felix_klauke.pegasus.server.handler.listener.PacketTestListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Felix Klauke for project Pegasus on 06.02.2016.
 */
public class ServerPacketHandler extends ChannelHandlerAdapter {

    private PacketHandler packetHandler;

    public ServerPacketHandler() {
        this.packetHandler = new PacketHandler();
        //TODO: Remove Test Code
        packetHandler.registerListener( new PacketTestListener() );
        packetHandler.registerListener( new PacketHandshakeListener() );
    }

    @Override
    public void channelRead( ChannelHandlerContext ctx, Object msg ) throws Exception {
        Packet packet = ( Packet ) msg;
        packetHandler.handlePacket( ctx.pipeline().channel(), packet );
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        System.out.println( "Fehler: " + cause.getLocalizedMessage() );
        cause.printStackTrace();
    }
}
