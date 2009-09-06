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
 * Delta.java
 *
 * Created on February 17, 2008, 4:36 PM
 *
 * Bartek N, nbartlomiej{at}gmail.com
 */

package javawars.robots;

import java.awt.Point;
import javawars.ErisMessage;
import javawars.JWRobot;
import javawars.JWAction;
import javawars.JWDirection;
import javawars.actions.*;

/**
 *
 * @author bartek
 */
public class Delta implements JWRobot {
    
    private ErisMessage message;
    
    public JWAction nextAction() {
        return new Move(JWDirection.E);
        
    }
    
    public String getName() {
        return "Delta";
    }
    
    public String getAuthor() {
        return "JavaWars";
    }
    
    public void receiveData(ErisMessage message) {
        this.message = message;
    }
        
    
}
