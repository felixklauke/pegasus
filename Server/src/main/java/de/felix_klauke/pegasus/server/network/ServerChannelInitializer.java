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

import de.felix_klauke.pegasus.protocol.decoder.PacketDecoder;
import de.felix_klauke.pegasus.protocol.encoder.PacketEncoder;
import de.felix_klauke.pegasus.server.Server;
import de.felix_klauke.pegasus.server.handler.ServerPacketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by Felix Klauke for project Pegasus on 05.02.2016.
 */
public class ServerChannelInitializer extends ChannelInitializer< SocketChannel > {

    /* ------------------------- [ Methods ] ------------------------- */

    /**
     * This method is used when Netty creates a new SocketChannel.
     * The Pipeline will be created here and all En- and Decoders will be added.
     * <p>
     * Basically:
     * <ul>
     * <li>The LengthFieldPrepender: It will prepend a Field that contains the length of
     * the data that will be passed through the pipeline.</li>
     * <li>The PacketEncoder: It will write all the data of a packet into it. </li>
     * <li>The LengthFieldBasedFrameDecoder: This Decoder is used to ensure that all data of
     * a packet is received before the Decoding starts.</li>
     * <li>The PacketDecoder: This Decoder will write the binary data into its Packet</li>
     * <li>The ServerPacketHandler: The Packets created by the PacketDecoder will be handled
     * here</li>
     * </ul>
     *
     * @param socketChannel The channel created by the Netty server
     * @throws Exception
     */
    @Override
    protected void initChannel( SocketChannel socketChannel ) throws Exception {
        Server.getLogger().info( "New Channel has been initialized." );
        Server.getLogger().info( "Hostname: " + socketChannel.remoteAddress() );

        socketChannel.pipeline().addLast(
                new LengthFieldPrepender( 4 ),
                new PacketEncoder(),
                new LengthFieldBasedFrameDecoder( 1024, 0, 4, 0, 4 ),
                new PacketDecoder(),
                new ServerPacketHandler() );
    }

}
