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
import org.marinemc.events.Event;
import org.marinemc.util.Position;
import org.marinemc.world.BlockID;

/**
 * @author Fozie
 */
public class BlockChangeEvent extends Event implements Cancellable {

	private final Position blockPos;
	private final BlockID current;
	boolean isCancelled;
	private BlockID target;

	public BlockChangeEvent(final Position blockPos, final BlockID current,
			final BlockID target) {
		super("BlockChangeEvent");
		this.blockPos = blockPos;
		this.current = current;
		this.target = target;
		isCancelled = false;
	}

	public BlockID getPrevious() {
		return current;
	}

	public BlockID getNew() {
		return target;
	}

	public void setNew(final BlockID target) {
		if (target == getPrevious())
			setCancelled(true);
		else
			this.target = target;
	}

	public Position getPosition() {
		return blockPos;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(final boolean b) {
		isCancelled = true;
	}

}
