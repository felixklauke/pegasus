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

package de.felix_klauke.pegasus.protocol.decoder;

import de.felix_klauke.pegasus.protocol.Packet;
import de.felix_klauke.pegasus.protocol.packets.PacketType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;

import java.util.List;

/**
 * Created by Felix Klauke for project Pegasus on 05.02.2016.
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode( ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List< Object > list ) throws Exception {
        if ( byteBuf instanceof EmptyByteBuf ) return;
        PacketType packetType = PacketType.getTypeByID( byteBuf.readInt() );

        if ( packetType == PacketType.UNKNOWN ) {
            throw new UnsupportedMessageTypeException();
        }
        Packet packet = packetType.getPacketClass().newInstance();
        packet.decode( byteBuf );
        list.add( packet );
    }

}
