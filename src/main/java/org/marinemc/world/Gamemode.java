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

package org.marinemc.world;

/**
 * A enum of all Gamemodes in minecraft Contains its ID used in packets, and a
 * friendly String name
 * 
 * @author Fozie
 */
public enum Gamemode {
	SURVIVAL(0, "Survival"), CREATIVE(1, "Creative"), ADVENTURE(2, "Adventure"), SPECTATOR(
			3, "Spectator");

	private final int id;
	private final String name;

	private Gamemode(final int id, final String name) {
		this.id = id;
		this.name = name;
	}

	public byte getID() {
		return (byte) id;
	}

	public String getName() {
		return name;
	}
}
