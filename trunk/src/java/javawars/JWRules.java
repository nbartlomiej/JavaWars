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
 * JWRules.java
 *
 * Created on February 16, 2008, 7:08 PM
 *
 * Bartek N, nbartlomiej{at}gmail.com
 */

package javawars;

/**
 *
 * @author bartek
 */
public class JWRules {
    
    // game properties:
    private int gameLength;
    
    private int boardWidth;
    private int boardHeight;
    private boolean shootingAllowed;
    private JWTerrainGenerator terrainGenerator;
    
    private int minPopulation = 2;
    
    // robot parameters:
    private int maxWaitTimeMs;
    
    /** Creates a new instance of JWRules */
    public JWRules() {
        gameLength = 300;
        maxWaitTimeMs = 1000;
        boardWidth = 12;
        boardHeight = 12;
        terrainGenerator = new JWTerrainGenerator(){};
        shootingAllowed = true;
        
    }

    public int getGameLength() {
        return gameLength;
    }

    public int [][] getElevationMap(){
        return terrainGenerator.getElevationMap(boardWidth, boardHeight);
    }
    
    public int[][] getGemMap(){
        return terrainGenerator.getGemMap(boardWidth, boardHeight);
    }
    
    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public boolean isShootingAllowed() {
        return shootingAllowed;
    }

    public void setMinPopulation(int minPopulation) {
        this.minPopulation = minPopulation;
    }

    public int getMinPopulation() {
        return minPopulation;
    }
    
    public void setShootingAllowed(boolean shootPermitted) {
        this.shootingAllowed = shootPermitted;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public void setTerrainGenerator(JWTerrainGenerator terrainGenerator) {
        this.terrainGenerator = terrainGenerator;
    }

    public int getMaxWaitTimeMs() {
        return maxWaitTimeMs;
    }

    public void setMaxWaitTimeMs(int maxWaitTimeMs) {
        this.maxWaitTimeMs = maxWaitTimeMs;
    }

    public void setGameLength(int gameLength) {
        this.gameLength = gameLength;
    }



}
