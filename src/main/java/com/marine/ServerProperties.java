///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
//     Copyright (C) IntellectualSites (marine.intellectualsites.com)
//
//     This program is free software; you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation; either version 2 of the License, or
//     (at your option) any later version.
//
//     This program is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.
//
//     You should have received a copy of the GNU General Public License along
//     with this program; if not, write to the Free Software Foundation, Inc.,
//     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package com.marine;

public class ServerProperties {

    //FINAL VALUES
    public static final int PROTOCOL_VERSION = 47;
    public static final String MINECRAFT_NAME = "1.8";
    public static final int MAX_Y = 256;
    // BUILD INFO
    public static String BUILD_VERSION = "0.0.1-SNAPSHOT";
    public static String BUILD_TYPE = "Development";
    public static String BUILD_NAME = "WorldWideWorld";
    public static boolean BUILD_STABLE = false;
    private static long currentTick;

    protected static void tick() {
        currentTick++;
    }

    public static long getLifeTime() {
        return currentTick;
    }
}
