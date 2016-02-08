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

import de.felix_klauke.pegasus.protocol.packets.PacketHandshake;
import de.felix_klauke.pegasus.protocol.packets.PacketHandshakeResponse;
import de.felix_klauke.pegasus.server.Server;
import de.felix_klauke.pegasus.server.client.Client;
import de.felix_klauke.pegasus.server.client.ClientManager;

/**
 * Created by Felix Klauke for project Pegasus on 07.02.2016.
 */
public class PacketHandshakeListener extends PacketListener< PacketHandshake > {

    /* ------------------------- [ Fields ] ------------------------- */

    /**
     * The ClientManager that will get the related client when any data is received. It will try to authenticate a
     * Client when it isnt authed yet.
     */
    private ClientManager clientManager = Server.getInstance().getClientManager();

    /* ------------------------- [ Constrcutors ] ------------------------- */

    public PacketHandshakeListener() {
        super( PacketHandshake.class );
    }

    /* ------------------------- [ Methods ] ------------------------- */

    @Override
    public void handlePacket( Client client, PacketHandshake packet ) {

        client.setUsername( packet.getUsername() );
        clientManager.authClient( client, packet.getEncryptedPassword() );

        PacketHandshakeResponse response = new PacketHandshakeResponse();

        response.setResult( client.isAuthed() ? PacketHandshakeResponse.HandshakeResult.LOGIN_SUCCEEDED : PacketHandshakeResponse.HandshakeResult.LOGIN_FAILED );
        client.sendPacket( response );
    }

}
