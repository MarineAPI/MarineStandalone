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

package org.marinemc.events.standardevents;

import org.marinemc.events.Cancellable;
import org.marinemc.events.PlayerEvent;
import org.marinemc.game.player.Player;

/**
 * Created 2014-12-13 for MarineStandalone
 *
 * @author Citymonstret
 * @author Fozie
 */
public class JoinEvent extends PlayerEvent implements Cancellable {

	private String joinMessage;

	public JoinEvent(final Player player, final String joinMessage) {
		super(player, "join");
		this.joinMessage = joinMessage;
	}

	public String getJoinMessage() {
		return joinMessage;
	}

	public void setJoinMessage(final String joinMessage) {
		this.joinMessage = joinMessage;
	}

	/*
	 * Kicking stuff:
	 */

	boolean isCancelled = false;
	String leaveMessage = "Login Cancelled!";

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(final boolean n) {
		isCancelled = n;
	}

	public void setLeaveMessage(final String msg) {
		leaveMessage = msg;
		isCancelled = true;
	}

	public String getLeaveMessage() {
		return leaveMessage;
	}

}
