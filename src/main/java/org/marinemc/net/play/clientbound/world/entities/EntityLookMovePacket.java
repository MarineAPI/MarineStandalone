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

package org.marinemc.net.play.clientbound.world.entities;

import java.io.IOException;

import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.util.vectors.Vector3;
import org.marinemc.world.entity.Entity;

/**
 * @author Fozie
 */
public class EntityLookMovePacket extends Packet {
	
	final Entity e;
	
	public EntityLookMovePacket(Entity ent) {
		super(0x17, States.INGAME);
		this.e = ent;
	}

	@Override
	public void writeToStream(PacketOutputStream stream) throws IOException {
		ByteList data = new ByteList();
		
		data.writeVarInt(e.getEntityID());
		
		final Vector3<Byte> sub = e.getTrackedLocation().getDifferentialFixed32();
		
		data.writeByte(sub.x);
		data.writeByte(sub.y);
		data.writeByte(sub.z);

		data.writeByte((byte) (((e.getLocation().getYaw() % 360) / 360) * 256));
		data.writeByte((byte) (((e.getLocation().getPitch() % 360) / 360) * 256));
		
		data.writeBoolean(e.getLocation().isOnGround());
		
		stream.write(getID(), data);
	}

}
