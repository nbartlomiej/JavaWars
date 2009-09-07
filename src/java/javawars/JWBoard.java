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
 * JWBoard.java
 *
 * Created on February 15, 2008, 5:22 PM
 *
 * Bartek N, nbartlomiej{at}gmail.com
 */
package javawars;

import java.awt.Point;
import java.util.ListIterator;
import java.util.Random;

/**
 *
 * @author bartek
 */
public class JWBoard {

    private final JWField[][] fields;
    private final int width,  height;

    /** Creates a new instance of JWBoard */
//    public JWBoard(Iterator<JWRobotWrapper> robots, int newWidth, int newHeight) {
//        fields = new JWField[newWidth][newHeight];
//        width = newWidth;
//        height = newHeight;
//        Random r = new Random();
//
//        for (int a = 0; a < width; a++) {
//            for (int b = 0; b < height; b++) {
//                fields[a][b] = new JWField(
//                        (int) Math.pow(r.nextInt(r.nextInt(4) + 1), 2) * 100 + r.nextInt(100), //wysokosc
//                        Math.max(0, (r.nextInt(15) - 12)) * r.nextInt(3) * 100, //wartosc klejnotu
//                        0);
//            }
//        }
//
//        int x = r.nextInt(width);
//        int y = r.nextInt(height);
//        while (robots.hasNext() == true) {
//            while (fields[x][y].getRobot() != 0 || fields[x][y].getGem() != 0) {
//                x = r.nextInt(width);
//                y = r.nextInt(height);
//            }
//            JWRobotWrapper robot = robots.next();
//            robot.setPosition(new Point(x, y));
//            fields[x][y].setRobot(robot.getId());
//        }
//    }

    JWBoard(ListIterator<JWRobotWrapper> robots, JWRules rules) {
        fields = new JWField[rules.getBoardWidth()][rules.getBoardHeight()];
        width = rules.getBoardWidth();
        height = rules.getBoardHeight();

        for (int a = 0; a < width; a++) {
            for (int b = 0; b < height; b++) {
                fields[a][b] = new JWField(
                        rules.getElevationMap()[a][b], //wysokosc
                        rules.getGemMap()[a][b], //wartosc klejnotu
                        0);
            }
        }

        Random r = new Random();
         
        int x = r.nextInt(width);
        int y = r.nextInt(height);
        while (robots.hasNext() == true) {
            while (fields[x][y].getRobot() != 0 || fields[x][y].getGem() != 0) {
                x = r.nextInt(width);
                y = r.nextInt(height);
            }
            JWRobotWrapper robot = robots.next();
            robot.setPosition(new Point(x, y));
            fields[x][y].setRobot(robot.getId());
        }
    }

    private JWBoard(int newWidth, int newHeight, int[][] heights, int[][] gems, int[][] robots) {
        fields = new JWField[newWidth][newHeight];
        width = newWidth;
        height = newHeight;

        for (int a = 0; a < width; a++) {
            for (int b = 0; b < height; b++) {
                fields[a][b] = new JWField(
                        heights[a][b], //wysokosc
                        gems[a][b], //wartosc klejnotu
                        robots[a][b]);
            }
        }

    }

    public int getHeightAt(int x, int y) {
        return fields[x][y].getHeight();
    }

    public int getGemAt(int x, int y) {
        return fields[x][y].getGem();
    }

    public int getRobotAt(int x, int y) {
        return fields[x][y].getRobot();
    }

    public JWField[][] getFields() {
        return fields;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isOccupied(int x, int y) {
        if (fields[x][y].getRobot() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int[][] createElevationMap() {
        int result[][] = new int[width][height];
        for (int a = 0; a < width; a++) {
            for (int b = 0; b < height; b++) {
                result[a][b] = fields[a][b].getHeight();
            }
        }
        return result;
    }

    public int[][] createGemMap() {
        int result[][] = new int[width][height];
        for (int a = 0; a < width; a++) {
            for (int b = 0; b < height; b++) {
                result[a][b] = fields[a][b].getGem();
            }
        }
        return result;
    }

    public int[][] createRobotMap() {
        int result[][] = new int[width][height];
        for (int a = 0; a < width; a++) {
            for (int b = 0; b < height; b++) {
                result[a][b] = fields[a][b].getRobot();
            }
        }
        return result;
    }

    @Override
    public String toString() {

        String result = "" + "\n";
        result += "BOARD :: width: " + width + ", height: " + height + "\n";
        result += "elevation map: " + "\n";

        for (int yc = 0; yc < height; yc++) {
            for (int xc = 0; xc < width; xc++) {
                String t = "" + fields[xc][yc].getHeight();
                while (t.length() < 3) {
                    t = " " + t;
                }
                result += "[" + t + "] ";
            }
            result += "\n";
        }

        result += "gem map: " + "\n";

        for (int yc = 0; yc < height; yc++) {
            for (int xc = 0; xc < width; xc++) {
                String t = "   ";
                if (fields[xc][yc].getGem() != 0) {
                    t = "" + fields[xc][yc].getGem();
                }
                while (t.length() < 3) {
                    t = " " + t;
                }
                result += "[" + t + "] ";
            }
            result += "\n";
        }

        result += "robot map: " + "\n";

        for (int yc = 0; yc < height; yc++) {
            for (int xc = 0; xc < width; xc++) {
                String t = "   ";
                if (fields[xc][yc].getRobot() != 0) {
                    t = "" + fields[xc][yc].getRobot();
                }
                while (t.length() < 3) {
                    t = " " + t;
                }
                result += "[" + t + "] ";
            }
            result += "\n";
        }


        return result;
    }

    public void toXml(JWXML jwxml) {
        jwxml.openTag("board width='"+width+"' height='"+height+"' ");
        for (int a = 0; a < this.getWidth(); a++) {
            for (int b = 0; b < this.getHeight(); b++) {
                jwxml.writeTag("field " +
                        "x='" + a + "' " +
                        "y='" + b + "' " +
                        "elevation='" + this.getHeightAt(a, b) + "' " +
                        "gem='" + this.getGemAt(a, b) + "' " +
                        "robot='" + this.getRobotAt(a, b) + "' ");
            }
        }
        jwxml.closeTag();

    }

    JWBoard duplicate() {
        return new JWBoard(this.width, this.height, createElevationMap(), createGemMap(), createRobotMap());
    }
}
