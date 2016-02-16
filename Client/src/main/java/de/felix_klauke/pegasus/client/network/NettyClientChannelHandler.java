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
import de.felix_klauke.pegasus.protocol.decoder.PacketDecoder;
import de.felix_klauke.pegasus.protocol.encoder.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class NettyClientChannelHandler extends ChannelInitializer<SocketChannel> {

    private PacketHandler packetHandler;
    private Logger logger;

    public NettyClientChannelHandler(Logger logger, PacketHandler packetHandler) {
        this.logger = logger;
        this.packetHandler = packetHandler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        logger.info("A new Channel has been initialized.\n" +
                "Channel: " + socketChannel.id().asShortText());
        logger.info("Preparing a new Channelpipeline.");

        socketChannel.pipeline().addLast(
                new PacketEncoder(),
                new PacketDecoder(),
                packetHandler
        );

        logger.info("Channelpipeline has been created.");
    }

}
