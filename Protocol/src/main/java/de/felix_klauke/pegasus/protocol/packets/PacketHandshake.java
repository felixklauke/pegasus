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
import de.felix_klauke.pegasus.protocol.PacketType;
import de.felix_klauke.pegasus.protocol.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class PacketHandshake extends Packet {

    private String username;
    private String password;
    private String protocolVersion;

    public PacketHandshake() {
        super(PacketType.HANDSHAKE);
    }

    public PacketHandshake(String username, String password, String protocolVersion) {
        super( PacketType.HANDSHAKE );
        this.username = username;
        this.password = password;
        this.protocolVersion = protocolVersion;
    }

    @Override
    public void encode( ByteBuf byteBuf ) {
        ByteBufUtils.writeUTF8String(byteBuf, username);
        ByteBufUtils.writeUTF8String(byteBuf, password);
        ByteBufUtils.writeUTF8String(byteBuf, protocolVersion);
    }

    @Override
    public void decode( ByteBuf byteBuf ) {
        username = ByteBufUtils.readUTF8String( byteBuf );
        password = ByteBufUtils.readUTF8String(byteBuf);
        protocolVersion = ByteBufUtils.readUTF8String(byteBuf);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

}
