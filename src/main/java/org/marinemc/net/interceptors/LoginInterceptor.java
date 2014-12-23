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

package org.marinemc.net.interceptors;

import org.marinemc.io.data.ByteData;
import org.marinemc.net.Client;
import org.marinemc.net.login.DisconnectPacket;
import org.marinemc.net.login.LoginPacket;
import org.marinemc.server.Marine;

/**
 * @author Fozie
 */
public class LoginInterceptor implements PacketInterceptor {


    public LoginInterceptor() {
    }

    @Override
    public boolean intercept(int ID, ByteData data, Client c) {
       switch(ID) {
       
       case 0: {
    	   LoginPacket packet = new LoginPacket();
    	   packet.readFromBytes(data);
		   
    	   final String s = Marine.getServer().getPlayerManager().login(c, packet);
		   System.out.println("Gg");
    	   if(s == null)
    		   return true; // End the interception with a positive interception
    	   else {
    		   DisconnectPacket disc = new DisconnectPacket(s);
    		   c.sendPacket(disc);
    	   }
       }
       	
       default: return false;
       }
    }

}
