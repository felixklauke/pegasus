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
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class NettyClient {

    /* ----------------------------------- [ Fields ] ----------------------------------- */

    /**
     * The logger to log errors with.
     */
    private Logger logger;

    /**
     * The netty bootstrap that will be used for the connection to the server.
     */
    private Bootstrap bootstrap;

    /**
     * Netty will need an Initializer that handles the initialization of a new Channel.
     */
    private NettyClientChannelHandler channelInitializer;

    /**
     * Netty will create this Channel. It's the way in the wide world or just the channel ending
     * at the server.
     */
    private Channel channel;

    /**
     * All Packets will be handled by this handler.
     */
    private PacketHandler packetHandler;

    /* ----------------------------------- [ Constructors ] ----------------------------------- */

    /**
     * This Constructor will create a new NettyClient. To connect the Client to the server you
     * will have to use {@link NettyClient#start()}. The Logger will be assigned, the Bootstrap
     * and the packethandler will be created. At the end the Netty ChannelInitializer will be created.
     *
     * @param logger the logger to log all information with
     */
    public NettyClient(Logger logger) {
        this.logger = logger;
        this.bootstrap = new Bootstrap();
        this.packetHandler = new PacketHandler(logger);
        this.channelInitializer = new NettyClientChannelHandler(logger, packetHandler);
    }

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * Start the Netty Client and connect it to the server.
     */
    public void start() {
        logger.info("Starting netty Client...");

        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.handler(channelInitializer);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        logger.info("NettyClient is configured. Client will be connect now...");

        try {

            ChannelFuture future = bootstrap.connect("felix-klauke.de", 27816).sync();
            channel = future.channel();

            logger.info("NettyClient is connected. Blocking Thread with the Client now...");
            future.channel().closeFuture();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Use this channel to write any packet to the server.
     *
     * @return the channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     *
     * Use this PacketHandler to register all your PacketListeners.
     *
     * @return the packetHandler
     */
    public PacketHandler getPacketHandler() {
        return packetHandler;
    }

    /**
     *
     * You can send a new Object (would be cool when you choose a
     * {@link de.felix_klauke.pegasus.protocol.Packet}).
     *
     * @param object the object to send through our pipeline
     */
    public void send(Object object) {
        System.out.println("Sending a packet");
        ChannelFuture future = getChannel().writeAndFlush(object);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (!future.isSuccess()) {
                    future.cause().printStackTrace();
                }
            }
        });
    }

    /**
     * Disconnects the Client from the server.
     */
    public void disconnect() {
        channel.close();
    }

}
