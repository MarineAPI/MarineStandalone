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

package org.marinemc.net.packets.player;

import java.io.IOException;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.Location;

/**
 * @author Fozie
 */
public class PlayerLookPacket extends Packet {

	private float yaw, pitch;
	private boolean onGround;

	public PlayerLookPacket() {
		super(0x05, States.INGAME);
	}
	
	public PlayerLookPacket(Location l) {
		this();
		this.yaw = l.getYaw();
		this.pitch = l.getPitch();
		this.onGround = l.isOnGround();
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public boolean getOnGround() {
		return onGround;
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
		final ByteList data = new ByteList();

		data.writeFloat(yaw);
		data.writeFloat(pitch);
		data.writeBoolean(onGround);

		stream.write(getID(), data);
	}

	@Override
	public void readFromBytes(final ByteInput input) {
		yaw = input.readFloat();
		pitch = input.readFloat();
		onGround = input.readBoolean();
	}

}
