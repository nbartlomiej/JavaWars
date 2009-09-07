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

package javawars;

import java.awt.Point;

/**
 *
 * @author bartek
 */
public final class ErisMessage {
    private final int[][] elevationMap;
    private final int[][] gemMap;
    private final int[][] robotMap;
    private final JWRobotWrapper robotWrapper;

    ErisMessage(int[][] createElevationMap, int[][] createGemMap, int[][] createRobotMap, JWRobotWrapper r) {
        elevationMap = createElevationMap;
        gemMap = createGemMap;
        robotMap = createRobotMap;
        robotWrapper = r;
    }

    /**
     * Returns the ID of the robot accessing the ErisMessage object.
     * @return
     */
    public int getMyId() {
        return robotWrapper.getId();
    }
    
    /**
     * Returns the position of the robot 
     * @return java.awt.Point object, whose .x and
     * .y attributes represent the robot's position on
     * the board
     */
    public Point getMyPosition(){
        
        return new Point(robotWrapper.getPosition().x, robotWrapper.getPosition().y);
    }

    /**
     * Returns the two-dimensional array of integers which describe
     * the height of corresponding fields on board.
     * @return
     */
    public int[][] getElevationMap() {
        return elevationMap;
    }

    /**
     * Returns the two-dimensional array of integers which describe
     * the value of gems located on the corresponding field on board;
     * 0 means 'no gem'
     * @return
     */
    public int[][] getGemMap() {
        return gemMap;
    }

    /**
     * Returns the two-dimensional array of integers which contain ID's of 
     * the robot that occupies the corresponding field. To know its own ID,
     * robot may invoke the getMyId() method on ErisMessage object.
     * @return
     */
    public int[][] getRobotMap() {
        return robotMap;
    }
}
