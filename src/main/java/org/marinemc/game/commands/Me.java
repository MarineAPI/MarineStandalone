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

package org.marinemc.game.commands;

import org.marinemc.game.chat.ChatColor;
import org.marinemc.game.command.Command;
import org.marinemc.game.command.CommandSender;
import org.marinemc.game.player.Player;
import org.marinemc.server.Marine;
import org.marinemc.util.StringUtils;

/**
 * Created 2014-12-14 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Me extends Command {

	public Me() {
		super("me", "marine.me", "Do something...", "emote");
	}

	@Override
	public void execute(final CommandSender sender, final String[] args) {
		Marine.broadcastMessage(String.format(ChatColor.GRAY + "* %s %s",
				sender instanceof Player ? ((Player) sender).getUserName()
						: "Console", StringUtils.join(replaceAll(args, sender),
						" ")));
	}
}
