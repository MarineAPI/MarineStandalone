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

package org.marinemc.events;

import org.marinemc.game.player.Player;
import org.marinemc.game.player.WeakPlayer;

/**
 * Player Event - For events focusing on players
 *
 * @author Citymonstret
 */
public abstract class PlayerEvent extends Event {

	private final WeakPlayer player;

	/**
	 * Constructor, will default "async" to false
	 *
	 * @param player
	 *            Affected Player
	 * @param name
	 *            Event Name
	 */
	public PlayerEvent(final Player player, final String name) {
		this(player, name, false);
	}

	/**
	 * Constructor
	 *
	 * @param player
	 *            Affected Player
	 * @param name
	 *            Event Name
	 * @param async
	 *            Async
	 */
	public PlayerEvent(final Player player, final String name,
			final boolean async) {
		super("player_event:" + name, async);
		this.player = new WeakPlayer(player);
	}

	/**
	 * Get the affected player
	 *
	 * @return The affected player however this can be null if player is out of
	 *         memory
	 */
	public Player getPlayer() {
		return player.getPlayer();
	}
}
