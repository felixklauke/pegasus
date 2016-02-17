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

package de.felix_klauke.pegasus.server.network;

import de.felix_klauke.pegasus.server.handler.PacketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class NettyServer {

    /* ----------------------------------- [ Fields ] ----------------------------------- */

    /**
     * The logger to log errors with.
     */
    private Logger logger;

    /**
     * The netty bootstrap that will be used for the connection from all Clients.
     */
    private ServerBootstrap serverBootstrap;

    /**
     * Netty will need an Initializer that handles the initialization of a new Channel.
     */
    private NettyServerChannelHandler channelInitializer;

    /**
     * Netty will create this Channel. It's the way in the wide world or just the channel ending
     * at the client.
     */
    private Channel channel;

    /**
     * All Packets will be handled by this handler.
     */
    private PacketHandler packetHandler;

    /* ----------------------------------- [ Constructors ] ----------------------------------- */

    /**
     * This Constructor will create a new NettyServer. The Logger will be assigned, the Bootstrap
     * and the packethandler will be created. At the end the Netty ChannelInitializer will be created.
     *
     * @param logger the logger to log all information with
     */
    public NettyServer(Logger logger) {
        this.logger = logger;
        this.serverBootstrap = new ServerBootstrap();
        this.packetHandler = new PacketHandler(logger);
        this.channelInitializer = new NettyServerChannelHandler(logger, packetHandler);
    }

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * Start the most epic Server ever.
     */
    public void start() {
        logger.info("Starting Netty Server...");

        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup());
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childHandler(channelInitializer);

        logger.info("NettyServer is configured. Server will be binded now...");

        ChannelFuture future = serverBootstrap.bind(27816);

        logger.info("Nettyserver is bound. Blocking Thread with the Server now...");

        try {
            future.sync().channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }


}
