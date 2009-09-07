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
 * Move.java
 *
 * Created on February 15, 2008, 4:33 PM
 *
 * Bartek N, nbartlomiej{at}gmail.com
 */
package javawars.actions;

import java.awt.Point;
import java.util.List;
import javawars.JWAction;
import javawars.JWBoard;
import javawars.JWDirection;
import javawars.JWRobotWrapper;
import javawars.JWXML;

/**
 *
 * @author bartek
 */
public final class Move implements JWAction {

    private final JWDirection direction;

    /** Creates a new instance of Move */
    public Move(JWDirection where) {
        direction = where;
    }

    public JWDirection getDirection() {
        return direction;
    }

    public final int getCost(int callerID, List<JWRobotWrapper> robots, JWBoard board, JWXML jwxml) {

        JWRobotWrapper caller = robots.get(callerID - 1);

        Move move = this;
        
        jwxml.writeTag("declaration callerid='"+caller.getId()+"' class='"+this.getClass().getName()+"' parameter1='"+this.getDirection()+"'");

        Point current = new Point(caller.getPosition().x, caller.getPosition().y);
        Point target = new Point(caller.getPosition().x, caller.getPosition().y);
        JWDirection d = move.getDirection();
        switch (d) {
            case N:
                target.translate(0, -1);
                break;
            case NE:
                target.translate(1, -1);
                break;
            case E:
                target.translate(1, 0);
                break;
            case SE:
                target.translate(1, 1);
                break;
            case S:
                target.translate(0, 1);
                break;
            case SW:
                target.translate(-1, 1);
                break;
            case W:
                target.translate(-1, 0);
                break;
            case NW:
                target.translate(-1, -1);
                break;
            default:
                return 2;
            }

        boolean diagonal;

        if (target.x - current.x != 0 && target.y - current.y != 0) {
            diagonal = true;
        } else {
            diagonal = false;
        }

        int result = 2;
        try {
            result = Math.abs(board.getHeightAt(current.x, current.y) - board.getHeightAt(target.x, target.y));
            result = (int) (result / 10);
            result = Math.max(1, result);
            if (diagonal == true) {
                result = (int) (result * 1.5);
            }
        } catch (IndexOutOfBoundsException ex) {
//            JWLog.add(JWMessage.ROBOTERROR, "" + caller.getJwrobot().getName() + " (#" + callerID + ") didn't choose well, IndexOutOfBoundsException.");
            result = 20;
        }
        return result;

    }

    public final void execute(int callerID, List<JWRobotWrapper> robots, JWBoard board, JWXML jwxml) {
        JWRobotWrapper caller = robots.get(callerID - 1);
        Move move = this;
        Point current = new Point(caller.getPosition().x, caller.getPosition().y);
        Point target = new Point(caller.getPosition().x, caller.getPosition().y);
        JWDirection d = move.getDirection();
        switch (d) {
            case N:
                target.translate(0, -1);
                break;
            case NE:
                target.translate(1, -1);
                break;
            case E:
                target.translate(1, 0);
                break;
            case SE:
                target.translate(1, 1);
                break;
            case S:
                target.translate(0, 1);
                break;
            case SW:
                target.translate(-1, 1);
                break;
            case W:
                target.translate(-1, 0);
                break;
            case NW:
                target.translate(-1, -1);
                break;
            default:
                return;
            }
        try {
            if (board.isOccupied(target.x, target.y) == false) {
                board.getFields()[current.x][current.y].setRobot(0);
                board.getFields()[target.x][target.y].setRobot(callerID);
                if (board.getFields()[target.x][target.y].getGem() > 0) {
                    caller.setScore(caller.getScore() + board.getFields()[target.x][target.y].getGem());
//                    JWLog.add(JWMessage.ROBOTMISC, "" + caller.getJwrobot().getName() + " (#" + callerID + ") collects a gem worth " + board.getFields()[target.x][target.y].getGem() + ", his score is now: " + caller.getScore());
                    board.getFields()[target.x][target.y].setGem(0);
                }
                caller.setPosition(target);
//                JWLog.add(JWMessage.ROBOTACTION, "" + caller.getJwrobot().getName() + " (#" + callerID + ") moved successfully to " + caller.getPosition());
            }
        } catch (IndexOutOfBoundsException ex) {
//            JWLog.add(JWMessage.ROBOTERROR, "" + caller.getJwrobot().getName() + " (#" + callerID + ") failed in moving to " + target + ", IndexOutOfBoundsException");
        }

    }
}
