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

    /* ----------------------------------- [ Fields ] ----------------------------------- */

    /**
     * The packetHandler that will handle all packets.
     */
    private PacketHandler packetHandler;

    /**
     * The Logger to log all important information.
     */
    private Logger logger;

    /* ----------------------------------- [ Constructors ] ----------------------------------- */

    /**
     * The Logger and the packetHandler will be assigned.
     *
     * @param logger        the Logger to log all important inforamtion
     * @param packetHandler the packetHandler that will handle all packets.
     */
    public NettyClientChannelHandler(Logger logger, PacketHandler packetHandler) {
        this.logger = logger;
        this.packetHandler = packetHandler;
    }

    /**
     * Netty will call this method whenever a new Channel has to get initialized.
     * The ChannelPipeline will be filled in this Method. Basically with this structure:
     * <p>
     * <ol>
     * <li>{@link de.felix_klauke.pegasus.protocol.encoder.PacketEncoder}</li>
     * <li>{@link de.felix_klauke.pegasus.protocol.decoder.PacketDecoder}</li>
     * <li>{@link de.felix_klauke.pegasus.client.handler.PacketHandler}</li>
     * </ol>
     * <p>
     * In a future Version a two new Elements will be added. Especially:
     * The {@link io.netty.handler.codec.LengthFieldPrepender} and
     * {@link io.netty.handler.codec.LengthFieldBasedFrameDecoder} to ensure all data is
     * received before the decoding starts.
     *
     * @param socketChannel the cocketChannel that is getting initialized
     * @throws Exception will be thrown when an error occured during creating the pipeline.
     */
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
