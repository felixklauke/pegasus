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

package de.felix_klauke.pegasus.protocol.util;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public class ByteBufUtils {

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * Write an UTF-8 String into a ByteBuf.
     *
     * @param byteBuf the bytebuf to write the String in
     * @param string  the String to write
     */
    public static void writeUTF8String( ByteBuf byteBuf, String string ) {
        byte[] bytes = string.getBytes( Charsets.UTF_8 );
        byteBuf.writeInt( bytes.length );
        byteBuf.writeBytes( bytes );
    }

    /**
     *
     * Write UTF-8 Strings into a ByteBuf.
     *
     * @param byteBuf the bytebuf to write the Strings in
     * @param strings the Strings to write
     */
    public static void writeUTF8Strings( ByteBuf byteBuf, String... strings ) {
        for ( String string : strings ) {
            writeUTF8String( byteBuf, string );
        }
    }

    /**
     *
     * Read an UTF-8 String from a ByteBuf.
     *
     * @param byteBuf the bytebuf to read from
     * @return the String
     */
    public static String readUTF8String( ByteBuf byteBuf ) {
        int length = byteBuf.readInt();
        String string = byteBuf.toString( byteBuf.readerIndex(), length, Charsets.UTF_8 );
        byteBuf.readerIndex( byteBuf.readerIndex() + length );
        return string;
    }

}
