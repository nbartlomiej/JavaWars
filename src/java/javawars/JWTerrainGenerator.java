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

package javawars;

import java.util.Random;

/**
 *
 * @author bartek
 */
public class JWTerrainGenerator {
    
    int grasslandOdds = 70;
    int hillsOdds = 20;
    int mountainsOdds = 10;
    int gemOdds = 7;
    
    public int getElevation (){
        Random r = new Random();
        //return (int) Math.pow(r.nextInt(r.nextInt(4) + 1), 2) * 100 + r.nextInt(100);
        int rValue = r.nextInt(100)+1;
        int range = 0;
        if (rValue > 0 && rValue < grasslandOdds ) range = 100;
        else if (rValue >= grasslandOdds && rValue <grasslandOdds + hillsOdds) range = 400;
        else range = 900;
        int result = range + r.nextInt(100);
        return result;
    }
    public int getGem () {
        Random r = new Random();
        
        if (r.nextInt(100)+1 < gemOdds ) return (r.nextInt(3)+1)*100;
        else return 0;
//        return Math.max(0, (r.nextInt(15) - 12)) * r.nextInt(3) * 100;
    }
    
    public int[][] getElevationMap(int width, int height){
        int [][] result = new int[width][height];
        for (int a = 0; a < width; a++) {
            for (int b = 0; b < height; b++) {
                result[a][b] = getElevation();
            }
        }
        return result;
    }

    public int[][] getGemMap(int width, int height){
        int [][] result = new int[width][height];
        for (int a = 0; a < width; a++) {
            for (int b = 0; b < height; b++) {
                result[a][b] = getGem();
            }
        }
        return result;
    }
    
    public JWTerrainGenerator(){
        int zero;
    }
    
    public JWTerrainGenerator(int grasslandOdds, int hillsOdds, int mountainsOdds, int gemOdds){
        if (grasslandOdds+hillsOdds+mountainsOdds != 100 ) return;
        else if (gemOdds > 100 || gemOdds < 1) return;
        this.grasslandOdds = grasslandOdds;
        this.hillsOdds = hillsOdds;
        this.mountainsOdds = mountainsOdds;
        this.gemOdds = gemOdds;
    }

}
