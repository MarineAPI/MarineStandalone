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

package org.marinemc.util;

import org.marinemc.io.Base64Encoding;
import org.marinemc.io.BinaryFile;

import java.io.File;
import java.io.IOException;

/**
 * Created 2014-12-21 for MarineStandalone
 *
 * @author Citymonstret
 */
public class Base64Image {

    private final File file;
    private final String string;

    public Base64Image(final File file) {
        if (file == null) {
            this.file = null;
            this.string = "";
            return;
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("File cannot be null, and has to exist");
        }
        BinaryFile f = new BinaryFile(file);
        try {
            f.readBinary();
        } catch (final IOException e) {
            throw new RuntimeException("Unable to read in the binary data", e);
        }
        this.string = new String(Base64Encoding.encode(f.getData().getBytes()));
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    @Override
    public String toString() {
        return this.string;
    }
}
