///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// MarineStandalone is a minecraft server software and API.
// Copyright (C) MarineMC (marinemc.org)
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package org.marinemc.net.play.clientbound.world;

import java.io.IOException;

import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.world.World;
/**
 * @author Fozie
 */
public class TimeUpdatePacket extends Packet {

    final long worldTime;
    final long worldAge;

    public TimeUpdatePacket(long worldTime, long worldAge) {
        super(0x03, States.INGAME);
        this.worldTime = worldTime;
        this.worldAge = worldAge;
    }

    public TimeUpdatePacket(World w) {
        this(w.getTime(), w.getWorldAge());
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
    	ByteList d = new ByteList();

        d.writeLong(worldAge);
        d.writeLong(worldTime);

        stream.write(getID(), d);
    }

}
