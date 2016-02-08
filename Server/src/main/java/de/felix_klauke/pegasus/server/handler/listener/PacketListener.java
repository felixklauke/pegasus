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

import de.felix_klauke.pegasus.protocol.Packet;
import de.felix_klauke.pegasus.server.client.Client;

/**
 * Created by Felix Klauke for project Pegasus on 06.02.2016.
 */
public abstract class PacketListener< T extends Packet > {

    /* ------------------------- [ Fields ] ------------------------- */

    /**
     * The workaround to save the class this listener should listen for. This is necessary, because it's way
     * easier than trying to get it from the type parameter.
     */
    private Class< ? extends Packet > clazz;

    /* ------------------------- [ Constructors ] ------------------------- */

    public PacketListener( Class< ? extends Packet > clazz ) {
        this.clazz = clazz;
    }

    /* ------------------------- [ Methods ] ------------------------- */

    /**
     * The abstract Method to implement in all subclasses. It will be called whenever a Packet of the specific type of
     * this Listener has to be handled.
     *
     * @param client the client the packet came from
     * @param packet the packet that was received
     */
    public abstract void handlePacket( Client client, T packet );

    public Class< ? extends Packet > getClazz() {
        return clazz;
    }


}
