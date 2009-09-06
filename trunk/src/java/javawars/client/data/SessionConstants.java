/*
 * JavaWars - browser game that teaches Java
 * Copyright (C) 2008-2009  Bartlomiej N (nbartlomiej@gmail.com)
 * 
 * This file is part of JavaWars. JavaWars is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU General 
 * Public License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 * 
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;
import javawars.domain.User;

/**
 * An object that gathers all the data that don't change
 * during one session.
 * @author bartlomiej
 */
public class SessionConstants implements IsSerializable {
    
    /**
     * The date of creation of this object
     */
    Date objectCreationDate = new Date();
    
    /**
     * The current (logged in) user.
     */
    private User currentuser;
    
    /**
     * Constructor created for the purpose of GWT serializatoin.
     * Should not be used in client code - use the constructor with
     * User parameter instead
     * @deprecated 
     */
    @Deprecated
    private SessionConstants(){ 
    }

    public SessionConstants(User currentuser) {
        this.currentuser = currentuser;
    }

    /**
     * For the purpose of GWT serialization only.
     * @param currentuser
     * @deprecated
     */
    @Deprecated
    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }
    
    /**
     * 
     * @return The current, logged in user.
     */
    public User getCurrentuser() {
        return currentuser;
    }

    public Date getObjectCreationDate() {
        return objectCreationDate;
    }

    @Override
    public String toString() {
        return "Session Constants, object created on: " + getObjectCreationDate();
    }
    
    
    
    
}
