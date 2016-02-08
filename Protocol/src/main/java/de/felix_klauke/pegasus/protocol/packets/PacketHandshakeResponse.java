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
import io.netty.buffer.ByteBuf;

/**
 * Created by Felix Klauke for project Pegasus on 07.02.2016.
 */
public class PacketHandshakeResponse extends Packet {

    /* ------------------------- [ Fields ] ------------------------- */

    private HandshakeResult result;

    /* ------------------------- [ Constructors ] ------------------------- */

    public PacketHandshakeResponse() {
        super( PacketType.HANDSHAKE_RESPONSE );
    }

    public PacketHandshakeResponse( HandshakeResult result ) {
        super( PacketType.HANDSHAKE_RESPONSE );
        this.result = result;
    }

    /* ------------------------- [ Methods ] ------------------------- */

    public HandshakeResult getResult() {
        return result;
    }

    public void setResult( HandshakeResult result ) {
        this.result = result;
    }

    @Override
    public void encode( ByteBuf byteBuf ) {
        byteBuf.writeInt( result.getCode() );
    }

    @Override
    public void decode( ByteBuf byteBuf ) {
        result = HandshakeResult.getByCode( byteBuf.readInt() );
    }

    /* ------------------------- [ Inner classes ] ------------------------- */

    public enum HandshakeResult {

        /* ------------------------- [ Enumeration Objects ] ------------------------- */

        LOGIN_FAILED( 0 ),
        LOGIN_SUCCEEDED( 1 ),
        ERROR( 2 );

        /* ------------------------- [ Fields ] ------------------------- */

        private final int code;

        /* ------------------------- [ Constructors ] ------------------------- */

        HandshakeResult( int code ) {
            this.code = code;
        }

        /* ------------------------- [ Methods ] ------------------------- */

        public static HandshakeResult getByCode( int code ) {
            for ( HandshakeResult result : values() ) {
                if ( result.getCode() == code ) return result;
            }
            return HandshakeResult.ERROR;
        }

        public int getCode() {
            return code;
        }

    }

}
