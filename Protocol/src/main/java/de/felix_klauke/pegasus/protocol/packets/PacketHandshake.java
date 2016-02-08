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
import de.felix_klauke.pegasus.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

/**
 * Created by Felix Klauke for project Pegasus on 07.02.2016.
 */
public class PacketHandshake extends Packet {

    /* ------------------------- [ Fields ] ------------------------- */

    private String protocolVersion;
    private String username;
    private String encryptedPassword;

    /* ------------------------- [ Constructors ] ------------------------- */

    public PacketHandshake() {
        super( PacketType.HANDSHAKE );
    }

    public PacketHandshake( String protocolVersion, String username, String encryptedPassword ) {
        super( PacketType.HANDSHAKE );
        this.protocolVersion = protocolVersion;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    /* ------------------------- [ Methods ] ------------------------- */

    @Override
    public void encode( ByteBuf byteBuf ) {
        ByteBufUtils.writeUTF8Strings( byteBuf, protocolVersion, username, encryptedPassword );
    }

    @Override
    public void decode( ByteBuf byteBuf ) {
        protocolVersion = ByteBufUtils.readUTF8String( byteBuf );
        username = ByteBufUtils.readUTF8String( byteBuf );
        encryptedPassword = ByteBufUtils.readUTF8String( byteBuf );
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public String getUsername() {
        return username;
    }

}
