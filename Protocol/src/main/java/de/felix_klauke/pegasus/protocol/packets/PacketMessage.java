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
public class PacketMessage extends Packet {

    private String author = "A";
    private String message;

    public PacketMessage() {
        super(PacketType.MESSAGE);
    }

    public PacketMessage(String message) {
        super(PacketType.MESSAGE);
        this.message = message;
    }

    @Override
    public void encode(ByteBuf byteBuf) {
        ByteBufUtils.writeUTF8String(byteBuf, author);
        ByteBufUtils.writeUTF8String(byteBuf, message);
    }

    @Override
    public void decode(ByteBuf byteBuf) {
        message = ByteBufUtils.readUTF8String(byteBuf);
        author = ByteBufUtils.readUTF8String(byteBuf);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
