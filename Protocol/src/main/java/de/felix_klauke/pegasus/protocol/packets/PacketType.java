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

package de.felix_klauke.pegasus.protocol.packets;

import de.felix_klauke.pegasus.protocol.Packet;

/**
 * Created by Felix Klauke for project Pegasus on 05.02.2016.
 */
public enum PacketType {

    UNKNOWN( 0x00, null ),
    TEST( 0x01, PacketTest.class );

    private int packetID;
    private Class< ? extends Packet > packetClass;

    PacketType( int packetID, Class< ? extends Packet > packetClass ) {
        this.packetID = packetID;
        this.packetClass = packetClass;
    }

    public static PacketType getTypeByID( int packetID ) {
        for ( PacketType type : values() ) {
            if ( type.getPacketID() == packetID )
                return type;
        }
        return PacketType.UNKNOWN;
    }

    public Class< ? extends Packet > getPacketClass() {
        return packetClass;
    }

    public int getPacketID() {
        return packetID;
    }
}
