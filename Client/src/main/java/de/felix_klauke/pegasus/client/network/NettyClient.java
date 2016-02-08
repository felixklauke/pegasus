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

import de.felix_klauke.pegasus.protocol.Packet;
import de.felix_klauke.pegasus.protocol.packets.PacketHandshake;
import de.felix_klauke.pegasus.protocol.packets.PacketType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Felix Klauke for project Pegasus on 05.02.2016.
 */
public class NettyClient {

    private final String serverHostname;
    private final int serverPort;

    private Bootstrap bootstrap;

    private ClientChannelInitializer channelInitializer = new ClientChannelInitializer();

    public NettyClient( String serverHostname, int serverPort ) {
        this.serverHostname = serverHostname;
        this.serverPort = serverPort;
        this.bootstrap = new Bootstrap();
    }

    public void start() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ChannelFuture future = bootstrap
                    .group( workerGroup )
                    .channel( NioSocketChannel.class )
                    .handler( channelInitializer )
                    .connect( serverHostname, serverPort ).sync();

            future.sync().channel().writeAndFlush( new PacketHandshake( PacketType.getProtocolVersion(), "Hey", "Hey"
            ) );

            future.sync().channel().closeFuture().sync();

        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    public void sendPacket( Packet packet ) {
        channelInitializer.getChannel().writeAndFlush( packet );
    }

}
