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

package org.marinemc.util.vectors;

/**
 * Double implementation of a 3D Vector
 *
 * @author Citymonstret
 * @author Fozie
 */
public class Vector3d extends Vector3<Double> {

	public Vector3d() {
		super(0d, 0d, 0d);
	}

	public Vector3d(final int x, final int y, final int z) {
		super((double) x, (double) y, (double) z);
	}

	public Vector3d(final double x, final double y, final double z) {
		super(x, y, z);
	}

	public Vector3d(final double t) {
		super(t, t, t);
	}

	public double getLengthSquared() {
		return x * x + y * y + z * z;
	}

	public double getLength() {
		return Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
	}

	public Vector3i toIntVector() {
		return new Vector3i(getX().intValue(), getY().intValue(), getZ()
				.intValue());
	}

	@Override
	public void add(final Vector3<Double> v2) {
		x += v2.x;
		y += v2.y;
		z += v2.z;
	}

	@Override
	public void subtract(final Vector3<Double> v2) {
		x -= v2.x;
		y -= v2.y;
		z -= v2.y;
	}

	@Override
	public void multiply(final int n) {
		x *= n;
		y *= n;
		z *= n;
	}

	@Override
	public void divide(final int n) {
		x /= n;
		y /= n;
		z /= n;
	}

	public Vector3<Double> getGen() {
		return this;
	}

	@Override
	public void divide(final Double number) {
		x /= number;
		y /= number;
		z /= number;
	}

	public Vector3b getDifferientialFixed32(final Vector3d target) {
		return new Vector3b((byte) (x - target.x * 32),
				(byte) (y - target.y * 32), (byte) (y - target.y * 32));
	}

	@Override
	public void multiply(final Double number) {
		x *= number;
		y *= number;
		z *= number;
	}

	@Override
	public void add(final int factor) {
		x += factor;
		y += factor;
		z += factor;
	}

	@Override
	public void add(final Double number) {
		x += number;
		y += number;
		z += number;
	}

	@Override
	public void sub(final int factor) {
		x -= factor;
		y -= factor;
		z -= factor;
	}

	@Override
	public void sub(final Double number) {
		x -= number;
		y -= number;
		z -= number;
	}

}
