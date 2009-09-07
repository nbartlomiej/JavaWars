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
 * Shoot.java
 *
 * Created on February 15, 2008, 4:59 PM
 *
 * Bartek N, nbartlomiej{at}gmail.com
 */
package javawars.actions;

import java.awt.Point;
import java.util.List;
import javawars.JWAction;
import javawars.JWBoard;
import javawars.JWRobotWrapper;
import javawars.JWXML;

/**
 *
 * @author bartek
 */
public final class Shoot implements JWAction {

    private int x,  y,  power;

    public int getPower() {
        return power;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** Creates a new instance of Shoot */
    public Shoot(int x, int y, int power) {
        this.x = x;
        this.y = y;
        this.power = power;
    }

    public final int getCost(int callerID, List<JWRobotWrapper> robots, JWBoard board, JWXML jwxml) {
        JWRobotWrapper caller = robots.get(callerID - 1);
        Shoot shoot = this;
        jwxml.writeTag("declaration callerid='"+caller.getId()+"' class='"+this.getClass().getName()+"' parameter1='"+this.getX()+"' parameter2='"+this.getY()+"' ");

        Point current = new Point(caller.getPosition().x, caller.getPosition().y);
        Point target = new Point(shoot.getX(), shoot.getY());

        try {
//            JWLog.add(JWMessage.ROBOTDECLARATION, "" + caller.getJwrobot().getName() + " (#" + callerID + ") , situated: " + caller.getPosition() + " declares to shoot " + target + " with power: " + power);
            int cost = (int) (Math.sqrt(Math.abs(current.x - target.x) * Math.abs(current.y - target.y)) + (power));
            return cost;
        } catch (IndexOutOfBoundsException ex) {
//            JWLog.add(JWMessage.ROBOTERROR, "" + caller.getJwrobot().getName() + " (#" + callerID + ") didn't choose well, IndexOutOfBoundsException.");
            return 20;
        }

    }

    public final void execute(int callerID, List<JWRobotWrapper> robots, JWBoard board, JWXML jwxml) {
        JWRobotWrapper caller = robots.get(callerID - 1);
        Shoot shoot = this;

        Point current = new Point(caller.getPosition().x, caller.getPosition().y);
        Point target = new Point(shoot.getX(), shoot.getY());

//        JWLog.add(JWMessage.ROBOTACTION, "" + caller.getJwrobot().getName() + " (#" + callerID + ") delivered a shot at " + target);
        board.getFields()[target.x][target.y].setHeight((int) (board.getFields()[target.x][target.y].getHeight() / 2));
        if (board.getFields()[target.x][target.y].getRobot() != 0) {
            int shotRobot = board.getFields()[target.x][target.y].getRobot();
            robots.get(shotRobot - 1).setHealth(robots.get(shotRobot - 1).getHealth() - power);
            if (robots.get(shotRobot - 1).getHealth() <= 0) {
                robots.get(shotRobot - 1).kill();
                board.getFields()[target.x][target.y].setRobot(0);
                board.getFields()[target.x][target.y].setGem(robots.get(shotRobot - 1).getScore());
                robots.get(shotRobot - 1).setScore(0);
            }
        }

    }
}
