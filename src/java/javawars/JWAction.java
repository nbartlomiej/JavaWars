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
 * JWAction.java
 *
 * Created on February 15, 2008, 1:58 PM
 *
 * Bartek N, nbartlomiej{at}gmail.com
 */

package javawars;

import java.util.List;

/**
 *
 * @author bartek
 */
public interface JWAction {
    
    public int getCost( int callerID, List<JWRobotWrapper> robots, JWBoard board, JWXML jwxml);
    public void execute( int callerID, List<JWRobotWrapper> robots, JWBoard board, JWXML jwxml);
    
}
