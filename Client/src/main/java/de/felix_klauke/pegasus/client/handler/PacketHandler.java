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

import com.google.common.collect.Maps;
import de.felix_klauke.pegasus.protocol.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class PacketHandler extends ChannelHandlerAdapter {

    private final Logger logger;
    Map<PacketListener, Class> listeners;

    public PacketHandler(Logger logger) {
        this.logger = logger;
        this.listeners = Maps.newConcurrentMap();
    }

    public void registerListener(PacketListener listener, Class<? extends Packet> clazz) {
        listeners.put(listener, clazz);
    }

    public Map<PacketListener, Class> getListeners() {
        return listeners;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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
        logger.warning("An error occured in the client packet handler: " + cause.getLocalizedMessage());
        cause.printStackTrace();
    }
}
