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

package de.felix_klauke.pegasus.protocol.packets.wrapper;

/**
 * Created by Felix Klauke for project Pegasus on 14.02.2016.
 */
public enum HandshakeResult {

    /* ----------------------------------- [ Enumeration ] ----------------------------------- */

    SUCCESS(0),
    FAILURE(1),
    ERROR(2);

    /* ----------------------------------- [ Fields ] ----------------------------------- */

    /**
     * The uniqueID of the given Result
     */
    private final int statusID;

    /* ----------------------------------- [ Constructors ] ----------------------------------- */

    /**
     * Basic enum constructor
     *
     * @param statusID the uniqueID of the status
     */
    HandshakeResult(int statusID) {
        this.statusID = statusID;
    }

    /* ----------------------------------- [ Methods ] ----------------------------------- */

    /**
     * Get any Result by its ID
     *
     * @param statusID the id of the status
     * @return the status with the given id, will return null when no status with the id is known
     */
    public static HandshakeResult lookup(int statusID) {
        for (HandshakeResult result : values()) {
            if (result.getStatusID() == statusID) return result;
        }
        return null;
    }

    /**
     *
     * @return the statusID of the given status
     */
    public int getStatusID() {
        return statusID;
    }
}
