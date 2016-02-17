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

    /* ----------------------------------- [ Fields ] ----------------------------------- */

    /**
     * The Logger to log all important information
     */
    private final Logger logger;

    /**
     * All PacketListeners will be stored in this Map. They're stored with an instance of themselves
     * and the class of the packet they're listening for.
     */
    private Map<PacketListener, Class> listeners;

    /* ----------------------------------- [ Constructors ] ----------------------------------- */

    /**
     * The basic Constructor fot his class. The logger will be assigned to the local var
     * and the map for the Listeners will be created using the google Collections Library.
     *
     * @param logger the Logger to log all important information
     */
    public PacketHandler(Logger logger) {
        this.logger = logger;
        this.listeners = Maps.newConcurrentMap();
    }

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * You have to register all PacketListeners with this method.
     *
     * @param listener the Listener to register
     * @param clazz    the class of the Packet the listener to register should listener
     */
    public void registerListener(PacketListener listener, Class<? extends Packet> clazz) {
        listeners.put(listener, clazz);
    }

    /**
     *
     * Used to check if all Listeners are registered. Will be removed in future versions.
     *
     * @return the map all listeners are stored in
     */
    @Deprecated
    public Map<PacketListener, Class> getListeners() {
        return listeners;
    }

    /**
     *
     *  The Method everything is about. All incoming data will be handled by this method.
     *  It will check all received data. When the object containing this data is an instance
     *  of {@link de.felix_klauke.pegasus.protocol.Packet}. If that is true the packet will be
     *  passed to the method that will handle all incoming packets:
     *  {@link de.felix_klauke.pegasus.client.handler.PacketHandler#handlePacket(Channel, Packet)}
     *
     * @param ctx the context of the channel that received the data
     * @param msg the data the channel received
     * @throws Exception the exception that occurs when receiving data fails
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            handlePacket(ctx.pipeline().channel(), (Packet) msg);
        }
    }

    /**
     *
     * This Method will handle all incoming Packets. It will log the type of the packet to the
     * console (Will be removed in future versions). Then the method will iterate through all
     * PacketListeners {@link de.felix_klauke.pegasus.client.handler.PacketListener}  that are
     * stored in {@link de.felix_klauke.pegasus.client.handler.PacketHandler#listeners}. If the map
     * contains Listeners that are listening for the specific type of the packet the pakcet will
     * be passed to these Listeners.
     *
     * @param channel the channel the packet came from
     * @param packet the packet that was received
     */
    public void handlePacket(Channel channel, Packet packet) {
        logger.info("Handling a new Packet: " + packet.getPacketType().name());
        for (Map.Entry<PacketListener, Class> entry : listeners.entrySet()) {
            if (entry.getValue() == packet.getClass()) {
                entry.getKey().handlePacket(channel, packet);
            }
        }
    }

    /**
     *
     * The way of netty to handle any error that occurs during the data is passed through the
     * Pipeline. This includes {@link de.felix_klauke.pegasus.protocol.decoder.PacketDecoder}
     * and {@link de.felix_klauke.pegasus.protocol.encoder.PacketEncoder}, because the aren't
     * able to handle errors themselves.
     *
     * @param ctx the context of the channel the error occured in
     * @param cause the cause why the error occured
     * @throws Exception will be thrown when the exception handling fails
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("An error occured in the client packet handler: " + cause.getLocalizedMessage());
        cause.printStackTrace();
    }

}
