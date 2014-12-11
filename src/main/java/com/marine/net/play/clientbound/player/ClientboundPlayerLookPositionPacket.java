package com.marine.net.play.clientbound.player;

import com.marine.io.data.ByteData;
import com.marine.net.Packet;
import com.marine.net.PacketOutputStream;
import com.marine.net.States;
import com.marine.net.play.serverbound.player.ServerboundPlayerLookPositionPacket;
import com.marine.util.Location;
import com.marine.util.Position;

import java.io.IOException;

public class ClientboundPlayerLookPositionPacket extends Packet { //TODO Relative positions

    final Location l;
    final Position p;

    public ClientboundPlayerLookPositionPacket(Location l) {
        this.l = l;
        p = null;
    }

    public ClientboundPlayerLookPositionPacket(Position p) {
        this.l = null;
        this.p = p;
    }

    public ClientboundPlayerLookPositionPacket(ServerboundPlayerLookPositionPacket l) {
        this(l.getLocation());
    }

    @Override
    public int getID() {
        return 0x08;
    }

    @Override
    public void writeToStream(PacketOutputStream stream) throws IOException {
        ByteData d = new ByteData();
        if (p == null) {
            d.writeDouble(l.getX());
            d.writeDouble(l.getY());
            d.writeDouble(l.getZ());

            d.writeFloat(l.getYaw());
            d.writeFloat(l.getPitch());
        } else {
            d.writeDouble(p.getX());
            d.writeDouble(p.getY());
            d.writeDouble(p.getZ());

            d.writeFloat(l.getYaw());
            d.writeFloat(l.getPitch());
        }

        d.writeByte((byte) 0);

        stream.write(getID(), d);
    }

    @Override
    public void readFromBytes(ByteData input) {

    }

    @Override
    public States getPacketState() {
        return States.INGAME;
    }
}
