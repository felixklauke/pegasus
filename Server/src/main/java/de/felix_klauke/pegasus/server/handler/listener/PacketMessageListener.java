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

import de.felix_klauke.pegasus.protocol.packets.PacketMessage;
import de.felix_klauke.pegasus.server.handler.PacketListener;
import de.felix_klauke.pegasus.server.user.User;
import de.felix_klauke.pegasus.server.user.UserManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class PacketMessageListener implements PacketListener<PacketMessage> {

    /* ----------------------------------- [ Fields ] ----------------------------------- */

    /**
     * Use this Manager to handle the users
     */
    private UserManager userManager;

    /* ----------------------------------- [ Constructors ] ----------------------------------- */

    /**
     * @param userManager the UserManager
     */
    public PacketMessageListener(UserManager userManager) {
        this.userManager = userManager;
    }

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * Handle any incoming PacketMessage.
     *
     * @param channel the channel the message cam from
     * @param packet  the packet
     */
    public void handlePacket(Channel channel, PacketMessage packet) {
        PacketMessage packetMessage = new PacketMessage(packet.getMessage());
        packetMessage.setAuthor(userManager.getUser(channel).getUsername());
        for (User user : userManager.getUsers()) {
            if (user.getChannel().id() == channel.id()) continue;
            System.out.println("Sending Packet to: " + user.getUsername() + " -> " + packetMessage.getMessage());
            ChannelFuture future = user.getChannel().writeAndFlush(packetMessage);
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (!future.isSuccess()) {
                        future.cause().printStackTrace();
                    }
                }
            });
        }
    }

}
