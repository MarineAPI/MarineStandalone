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

package org.marinemc.net.interceptors;

import org.marinemc.game.async.ChatManager;
import org.marinemc.io.binary.ByteInput;
import org.marinemc.net.Client;
import org.marinemc.server.Marine;

/**
 * Intercepts ingamepackets
 * 
 * @author Fozie
 */
public class IngameInterceptor implements PacketInterceptor {

	public IngameInterceptor() {
	}

	@Override
	public boolean intercept(final int id, final ByteInput data, final Client c) {
		switch (id) {
		case 0x00:
			System.out.println("Keepem alive ;O");
			break;
		case 0x01:
			ChatManager.getInstance().interceptChatPacket(data, c);
			break;
		case 0x04:
			Marine.getServer().getPlayerManager().movePlayerFromPacket(c, data);
			break;
		case 0x06:
			Marine.getServer().getPlayerManager().moveLookPlayerFromPacket(c, data);
			break;
		default:
			return false;
		}
		return true;
	}
}
