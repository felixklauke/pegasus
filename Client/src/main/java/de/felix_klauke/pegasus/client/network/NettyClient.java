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

import de.felix_klauke.pegasus.client.handler.PacketHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class NettyClient {

    private Logger logger;
    private Bootstrap bootstrap;

    private NettyClientChannelHandler channelInitializer;
    private Channel channel;

    private PacketHandler packetHandler;

    public NettyClient(Logger logger) {
        this.logger = logger;
        this.bootstrap = new Bootstrap();
        this.packetHandler = new PacketHandler(logger);
        this.channelInitializer = new NettyClientChannelHandler(logger, packetHandler);
    }

    public void start() {
        logger.info("Starting netty Client...");

        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(channelInitializer);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        logger.info("NettyClient is configured. Client will be connect now...");

        try {

            ChannelFuture future = bootstrap.connect("felix-klauke.de", 27816).sync();
            channel = channelInitializer.getChannel();

            logger.info("NettyClient is connected. Blocking Thread with the Client now...");

            future.sync().channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public PacketHandler getPacketHandler() {
        return packetHandler;
    }

    public void send(Object object) {
        System.out.println("Sending a packet");
        getChannel().writeAndFlush(object);
    }

}
