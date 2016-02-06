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

package de.felix_klauke.pegasus.server.handler.listener;

import de.felix_klauke.pegasus.protocol.packets.PacketTest;
import de.felix_klauke.pegasus.protocol.packets.PacketType;

/**
 * Created by Felix Klauke for project Pegasus on 06.02.2016.
 * //TODO: Remove Test Code
 */
public class PacketTestListener extends PacketListener< PacketTest > {

    public PacketTestListener() {
        super( PacketType.TEST.getPacketClass() );
    }

    @Override
    public void handlePacket( PacketTest packet ) {
        System.out.println( "Received PacketTest!" );
    }
}
