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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Felix Klauke for project Pegasus on 05.02.2016.
 */
public class NettyServer {

    /* ------------------------- [ Fields ] ------------------------- */

    private final int serverPort;
    private final ServerBootstrap bootstrap;

    /* ------------------------- [ Constructors ] ------------------------- */

    public NettyServer( int serverPort ) {
        this.serverPort = serverPort;
        this.bootstrap = new ServerBootstrap();
    }

    /* ------------------------- [ Methods ] ------------------------- */

    /**
     * Start the basic nettyserver
     */
    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ChannelFuture future = bootstrap
                    .group( bossGroup, workerGroup )
                    .channel( NioServerSocketChannel.class )
                    .option( ChannelOption.SO_BACKLOG, 128 )
                    .childOption( ChannelOption.SO_KEEPALIVE, true )
                    .childHandler( new ServerChannelInitializer() )
                    .bind( serverPort );

            future.sync().channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }

}
