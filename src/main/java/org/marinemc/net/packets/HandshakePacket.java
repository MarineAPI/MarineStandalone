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

package org.marinemc.net.packets;

import java.io.IOException;

import org.marinemc.io.binary.ByteInput;
import org.marinemc.io.binary.ByteList;
import org.marinemc.io.binary.ByteUtils;
import org.marinemc.net.Packet;
import org.marinemc.net.PacketOutputStream;
import org.marinemc.net.States;

/**
 * Sent by the client to introduce the client to the server, All new connections
 * should begin with this packet
 * 
 * @author Fozie
 */
public class HandshakePacket extends Packet {

	public int protocolVersion;
	public String serverAddress;
	public int port;
	public int nextState;

	public HandshakePacket() {
		super(0x00, States.HANDSHAKE);
	}

	@Override
	public void writeToStream(final PacketOutputStream stream)
			throws IOException {
		final ByteList d = new ByteList();
		d.writeVarInt(protocolVersion);
		d.writeUTF8(serverAddress);
		d.writeShort((short) port);
		d.writeVarInt(nextState);
		stream.write(getID(), d);
	}

	@Override
	public void readFromBytes(final ByteInput input) {
		protocolVersion = input.readVarInt();
		serverAddress = ByteUtils.readUTF8VarInt(input);
		port = input.readUnsignedShort();
		nextState = input.readVarInt();
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "Protocol: " + protocolVersion + " connected to "
				+ serverAddress + " : " + port + " target state " + nextState;
	}

	public int getProtocolVersion() {
		return protocolVersion;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public int getPort() {
		return port;
	}

	public int getState() {
		return nextState;
	}

}
