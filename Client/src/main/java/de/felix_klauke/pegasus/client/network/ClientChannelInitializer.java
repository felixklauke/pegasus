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

package de.felix_klauke.pegasus.client.network;

import de.felix_klauke.pegasus.client.Client;
import de.felix_klauke.pegasus.client.handler.ClientPacketHandler;
import de.felix_klauke.pegasus.protocol.decoder.PacketDecoder;
import de.felix_klauke.pegasus.protocol.encoder.PacketEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by Felix Klauke for project Pegasus on 05.02.2016.
 */
public class ClientChannelInitializer extends ChannelInitializer< SocketChannel > {

    private Channel channel;

    @Override
    protected void initChannel( SocketChannel socketChannel ) throws Exception {
        Client.getLogger().info( "New Channel has been initialized." );

        socketChannel.pipeline().addLast(
                new LengthFieldPrepender( 4 ),
                new PacketEncoder(),
                new LengthFieldBasedFrameDecoder( 1024, 0, 4, 0, 4 ),
                new PacketDecoder(),
                new ClientPacketHandler() );

        channel = socketChannel.pipeline().channel();
    }

    public Channel getChannel() {
        return channel;
    }
}
