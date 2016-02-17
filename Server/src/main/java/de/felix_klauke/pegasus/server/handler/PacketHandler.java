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
import io.netty.channel.*;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
@ChannelHandler.Sharable
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

    /**
     * The UserManager
     */
    private UserManager userManager;

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
        this.userManager = new UserManager();

        //Register Listeners here
        registerListener(new PacketMessageListener(userManager), PacketMessage.class);
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
     * Used to check if all Listeners are registered. Will be removed in future versions.
     *
     * @return the map all listeners are stored in
     */
    public Map<PacketListener, Class> getListeners() {
        return listeners;
    }

    /**
     *
     *  The Method everything is about. All incoming data will be handled by this method.
     *  It will check all received data. When the object containing this data is an instance
     *  of {@link de.felix_klauke.pegasus.protocol.Packet}.
     *
     *  This is the Main Handler for Handshakes.
     *
     *  The other packets will be
     *  passed to the method that will handle all incoming packets:
     *  {@link de.felix_klauke.pegasus.server.handler.PacketHandler#handlePacket(Channel, Packet)}
     *
     * @param ctx the context of the channel that received the data
     * @param msg the data the channel received
     * @throws Exception the exception that occurs when receiving data fails
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        logger.info("Handling incoming data.");

        User user = userManager.getUser(ctx.pipeline().channel());
        if (user == null) {
            System.out.println(msg.getClass());
            if (msg instanceof PacketHandshake) {
                PacketHandshake packetHandshake = (PacketHandshake) msg;

                logger.info("Authenticating: " + packetHandshake.getUsername() + " with password " + packetHandshake.getPassword());

                boolean success = userManager.authUser(packetHandshake.getUsername(), packetHandshake.getPassword());

                PacketHandshakeResponse response = new PacketHandshakeResponse();
                response.setResult(success ? HandshakeResult.SUCCESS : HandshakeResult.FAILURE);
                if (success) {
                    userManager.createUser(packetHandshake.getUsername(), ctx.channel());
                }
                ChannelFuture future = ctx.channel().writeAndFlush(response);
                future.addListener(new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (!future.isSuccess()) {
                            future.cause().printStackTrace();
                        }
                    }
                });
                return;
            }
            ctx.pipeline().channel().close();
            return;
        }

        if (msg instanceof Packet) {
            handlePacket(ctx.pipeline().channel(), (Packet) msg);
        }
    }

    /**
     *
     * This Method will handle all incoming Packets. It will log the type of the packet to the
     * console (Will be removed in future versions). Then the method will iterate through all
     * PacketListeners {@link de.felix_klauke.pegasus.server.handler.PacketListener}  that are
     * stored in {@link de.felix_klauke.pegasus.server.handler.PacketHandler#listeners}. If the map
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
        logger.warning("An error occured in the server packet handler: " + cause.getLocalizedMessage());
    }
}
