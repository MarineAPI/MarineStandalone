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

package org.marinemc.io.nbt;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.ByteList;

/**
 * @author Fozie
 */
public class NBTDouble extends NBTTag<Double> {

	private final double data;

	public NBTDouble(final String name, final double v) {
		super(name, 6);
		data = v;
	}

	public NBTDouble(final String name, final ByteInput data) {
		this(name, data.readDouble());
	}

	@Override
	public byte[] toByteArray() {
		final ByteList d = new ByteList();
		d.writeByte(getTagID());
		d.writeUTF8Short(name);
		d.writeDouble(data);
		return d.toBytes();
	}

	public double toDouble() {
		return data;
	}

	@Override
	public byte[] toNonPrefixedByteArray() {
		final ByteList data = new ByteList();
		data.writeDouble(this.data);
		return data.toBytes();
	}
}