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

package org.marinemc.net.play.clientbound;

import java.io.IOException;

import org.marinemc.game.player.Player;
import org.marinemc.io.binary.ByteList;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;
import org.marinemc.server.Marine;

/**
 * @author Fozie
 */
public class JoinGamePacket extends Packet {

	final Player p;

	public JoinGamePacket(final Player p) {
		super(0x01, States.INGAME);
		this.p = p;
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
		final ByteList d = new ByteList();

		d.writeInt(p.getEntityID());

		d.writeByte(p.getGamemode().getID()); // Gamemode

		d.writeByte(p.getWorld().getDimension().getID()); // Dimension
		d.writeByte(Marine.getServer().getDefaultDifficulty().getID()); // Difficulty

		d.writeByte((byte) Marine.getMaxPlayers()); // MaxPlayers
		d.writeUTF8("flat");
		d.writeBoolean(false);

		stream.write(getID(), d);
	}

}
