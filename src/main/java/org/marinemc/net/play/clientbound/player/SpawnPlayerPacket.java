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

package org.marinemc.net.play.clientbound.player;

import org.marinemc.game.player.Player;
import org.marinemc.io.data.ByteData;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

import java.io.IOException;

/*
 * 
 * Used to spawn a player locally when they are in sight.
 * Warning this is not used to tell a client to spawn themself!
 * 
 * @author Fozie
 * 
 */
public class SpawnPlayerPacket extends Packet {
	final Player p;
	
	public SpawnPlayerPacket(final Player p) {
		super(0x0C, States.INGAME);
		this.p = p;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteData d = new ByteData();
		
		d.writeVarInt(p.getEntityID());
		
		d.writeUUID(p.getUUID());
		
		d.writeInt((int) p.getX() * 32);
		d.writeInt((int) p.getY() * 32);
		d.writeInt((int) p.getZ() * 32);
		
		d.writeByte((byte) (((p.getLocation().getYaw() % 360) / 360) * 256));
		
		d.writeByte((byte) (((p.getLocation().getPitch() % 360) / 360) * 256));
		
		// WARNING FOLLOWING CANT BE -1 IT WILL CRASH THE CLIENT
		d.writeShort((short) 1); // TODO : In hand item like p.getInHand(); 
		
		d.writeByte((byte) 127); // TODO: Entity Metadata :p
		
		stream.write(getID(), d);
	}

	@Override
	public void readFromBytes(ByteData input) {
	}

}
