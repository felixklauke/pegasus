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

package de.felix_klauke.pegasus.protocol;

import io.netty.buffer.ByteBuf;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public abstract class Packet {

    /* ----------------------------------- [ Fields ] ----------------------------------- */

    /**
     * The type of this packet
     */
    private PacketType packetType;

    /* ----------------------------------- [ Constructors ] ----------------------------------- */

    /**
     * @param packetType the PacketType the packet will have
     */
    public Packet( PacketType packetType ) {
        this.packetType = packetType;
    }

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    public PacketType getPacketType() {
        return packetType;
    }

    /**
     *
     * The Method to implement
     *
     * @param byteBuf the ByteBuf to encode in
     */
    public abstract void encode(ByteBuf byteBuf);

    /**
     *
     * The Method to implement
     *
     * @param byteBuf the ByteBuf to decode from
     */
    public abstract void decode(ByteBuf byteBuf);

}
