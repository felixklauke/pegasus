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

package de.felix_klauke.pegasus.protocol.encoder;

import de.felix_klauke.pegasus.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * This Method will encode any Packet that wants through our Pipeline. All the content of a Packet will be
     * written into a ByteBuf and the packetID will be prepended.
     *
     * @param channelHandlerContext the context of the channel used to send the data
     * @param packet                the packet to encode
     * @param byteBuf               the bytebuf to encode the Packet hin
     * @throws Exception will be thrown when the encoding fails
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(packet.getPacketType().getPacketID());
        packet.encode(byteBuf);
    }

}
