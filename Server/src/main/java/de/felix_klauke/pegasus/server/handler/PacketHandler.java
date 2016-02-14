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

package de.felix_klauke.pegasus.server.handler;

import com.google.common.collect.Maps;
import de.felix_klauke.pegasus.protocol.Packet;
import de.felix_klauke.pegasus.protocol.packets.PacketHandshake;
import de.felix_klauke.pegasus.protocol.packets.PacketHandshakeResponse;
import de.felix_klauke.pegasus.protocol.packets.PacketMessage;
import de.felix_klauke.pegasus.protocol.packets.wrapper.HandshakeResult;
import de.felix_klauke.pegasus.server.handler.listener.PacketMessageListener;
import de.felix_klauke.pegasus.server.user.User;
import de.felix_klauke.pegasus.server.user.UserManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
@ChannelHandler.Sharable
public class PacketHandler extends ChannelHandlerAdapter {

    private final Logger logger;
    private Map<PacketListener, Class> listeners;
    private UserManager userManager;

    public PacketHandler(Logger logger) {
        this.logger = logger;
        this.listeners = Maps.newConcurrentMap();
        this.userManager = new UserManager();

        registerListener(new PacketMessageListener(userManager), PacketMessage.class);
    }

    public void registerListener(PacketListener listener, Class<? extends Packet> clazz) {
        listeners.put(listener, clazz);
    }

    public Map<PacketListener, Class> getListeners() {
        return listeners;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        User user = userManager.getUser(ctx.pipeline().channel());
        if (user == null) {
            System.out.println(msg.getClass());
            if (msg instanceof PacketHandshake) {
                PacketHandshake packetHandshake = (PacketHandshake) msg;
                boolean success = userManager.authUser(packetHandshake.getUsername(), packetHandshake.getPassword());

                PacketHandshakeResponse response = new PacketHandshakeResponse();
                response.setResult(success ? HandshakeResult.SUCCESS : HandshakeResult.FAILURE);
                ctx.pipeline().channel().writeAndFlush(response);
                return;
            }
            ctx.pipeline().channel().close();
            return;
        }

        if (msg instanceof Packet) {
            handlePacket(ctx.pipeline().channel(), (Packet) msg);
        }
    }

    public void handlePacket(Channel channel, Packet packet) {
        logger.info("Handling a new Packet: " + packet.getPacketType().name());
        for (Map.Entry<PacketListener, Class> entry : listeners.entrySet()) {
            if (entry.getValue() == packet.getClass()) {
                entry.getKey().handlePacket(channel, packet);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("An error occured in the server packet handler: " + cause.getLocalizedMessage());
    }
}
