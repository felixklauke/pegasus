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

package de.felix_klauke.pegasus.client.handler;

import io.netty.channel.Channel;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public interface PacketListener<T> {

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * The Method to implment.
     *
     * @param channel the channel any packet came from
     * @param packet  the packet that was received
     */
    void handlePacket(Channel channel, T packet);

}
