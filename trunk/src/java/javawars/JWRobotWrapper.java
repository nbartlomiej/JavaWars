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
 * JWRobotWrapper.java
 *
 * Created on February 15, 2008, 4:09 PM
 *
 * Bartek N, nbartlomiej{at}gmail.com
 */

package javawars;

import java.awt.Point;
import javawars.actions.Wait;

/**
 *
 * @author bartek
 */
public class JWRobotWrapper {
    
    private final JWRobot jwrobot;
    private final String author, name;
    
    private JWAction declaredAction = new Wait (10); // TODO: correct the 'wait' issue      
    
    private final int id;
    
    private Point position;
    
    private int health = 100;
    private int score = 0;

    private int actionDelay = 0;
    
    private boolean dead = false;
    private boolean flawed = false;

    public void setFlawed() {
        this.flawed = true;
    }

    public boolean isFlawed() {
        return flawed;
    }
    
    

    public boolean isDead() {
        return dead;
    }
    
    public void kill(){
        dead = true;
    }

    /** Creates a new instance of JWRobotWrapper */
    public JWRobotWrapper(JWRobot newJwrobot, int newID, String newAuthor, String newName) {
        jwrobot = newJwrobot;
        id = newID;
        author = newAuthor;
        name = newName;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public void setActionDelay(int actionDelay) {
        this.actionDelay = actionDelay;
    }

    public int getActionDelay() {
        return actionDelay;
    }

    public JWRobot getJwrobot() {
        return jwrobot;
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String toString() {
        
        return ""+getId();
    }

    public JWAction getDeclaredAction() {
        return declaredAction;
    }

    public void setDeclaredAction(JWAction declaredAction) {
        this.declaredAction = declaredAction;
    }
    

}
