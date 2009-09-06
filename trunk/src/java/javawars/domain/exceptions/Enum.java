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

package javawars.domain.exceptions;

import java.io.Serializable;


/**
 *
 * @author bartlomiej
 */
public class Enum  implements Serializable {
    
    public enum Parameter implements Serializable {
        LOGIN,
        PASSWORD,
        EMAIL,
        NAME,
        OTHER,
    }
    
    public enum Error implements Serializable {
        TOO_LONG,
        TOO_SHORT,
        MALFORMED,
        INCORRECT_CHARACTER,
        IN_USE,
        OTHER,
    }
    
    public enum Entity implements Serializable {
        IP_ADDRESS,
        USER,
        LEAGUE,
        ROBOT,
        MATCH_REPORT,
    }
}
