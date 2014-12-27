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

package org.marinemc.settings.files;

import org.json.JSONArray;
import org.marinemc.settings.StorageConfig;

import java.io.File;
import java.net.InetAddress;
import java.util.UUID;

/**
 * Created 2014-12-27 for MarineStandalone
 *
 * @author Citymonstret
 */
public class BanFile extends StorageConfig {

    private JSONArray players, ips;

    public BanFile(File file) {
        super(file, "banned");
        setIfNull("players", new JSONArray());
        setIfNull("ips", new JSONArray());
        players = get("players");
        ips = get("ips");
    }

    @Override
    public void saveFile() {
        set("players", players);
        super.saveFile();
    }

    public void setBanned(UUID uuid, boolean b) {
        for (int x = 0; x < players.length(); x++) {
            if (players.getString(x).equals(uuid.toString())) {
                if (!b)
                    players.remove(x);
                return;
            }
        }
        if (b) {
            players.put(uuid.toString());
        } else {
            throw new UnsupportedOperationException("You cannot unban a player unless it's banned");
        }
    }

    public void setBanned(InetAddress address, boolean b) {
        for (int x = 0; x < ips.length(); x++) {
            if (ips.getString(x).equals(address.getHostAddress())) {
                if (!b) {
                    ips.remove(x);
                }
                return;
            }
        }
        if (b) {
            ips.put(address.getHostAddress());
        } else {
            throw new UnsupportedOperationException("You cannot unban an address unless it's banned!");
        }
    }

    public boolean isBanned(InetAddress address) {
        for (int x = 0; x < ips.length(); x++) {
            if (ips.getString(x).equals(address.getHostAddress())) {
                return true;
            }
        }
        return false;
    }

    public boolean isBanned(UUID uuid) {
        for (int x = 0; x < players.length(); x++) {
            if (players.getString(x).equals(uuid.toString()))
                return true;
        }
        return false;
    }
}