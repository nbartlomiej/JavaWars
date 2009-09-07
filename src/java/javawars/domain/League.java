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

package javawars.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
//import javawars.JWRules;
//import javawars.JWTerrainGenerator;

/**
 *
 * @author bartek
 */
public class League implements Serializable {
    private Long id;
    
    private String name;
    private String password;
    private String description;

    private int grasslandPercentage;
    private int hillsPercentage;
    private int mountainsPercentage;
    private int gemPercentage;
    
    private boolean shootingAllowed;
    
    private int width;
    private int height;
    private int gameLength;

    private int maxParticipants;
    private int minScore;
    
    private int scoreMultiplier;
    
    
    private Set<User> users = new HashSet<User>();
    private Set<MatchReport> matchReports = new HashSet<MatchReport>();
    
    /**
     * Commented out to avoid problems with gwt serializer (which would stuck at
     * JWTerrainGenerator and JWRules).
     * @param grassland
     * @param hills
     * @param mountains
     */
//    public JWRules generateRules(){
//        JWRules rules = new JWRules();
//        rules.setBoardHeight(height);
//        rules.setBoardWidth(width);
//        if (gameLength!=0) rules.setGameLength(gameLength);
//        rules.setShootingAllowed(shootingAllowed);
//        JWTerrainGenerator tg = new JWTerrainGenerator(grasslandPercentage, hillsPercentage, mountainsPercentage, gemPercentage);
//        rules.setTerrainGenerator(tg);
//        return rules;
//    }
    
    public void setTerrainOdds(int grassland, int hills, int mountains){
        grasslandPercentage = grassland;
        hillsPercentage = hills;
        mountainsPercentage = mountains;
    }

    public int getScoreMultiplier() {
        return scoreMultiplier;
    }

    public void setScoreMultiplier(int scoreMultiplier) {
        this.scoreMultiplier = scoreMultiplier;
    }
    
    public int getWidth() {
        return width;
    }

    public void setSize(int newWidth, int newHeight){
        width = newWidth;
        height = newHeight;
    }

    public void setGemPercentage(int gemPercentage) {
        this.gemPercentage = gemPercentage;
    }

    public int getGemPercentage() {
        return gemPercentage;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setGameLength(int gameLength) {
        this.gameLength = gameLength;
    }

    public int getGameLength() {
        return gameLength;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getGrasslandPercentage() {
        return grasslandPercentage;
    }

    public int getHeight() {
        return height;
    }

    public int getHillsPercentage() {
        return hillsPercentage;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMountainsPercentage() {
        return mountainsPercentage;
    }

    public void setGrasslandPercentage(int grasslandPercentage) {
        this.grasslandPercentage = grasslandPercentage;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHillsPercentage(int hillsPercentage) {
        this.hillsPercentage = hillsPercentage;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public void setMountainsPercentage(int mountainsPercentage) {
        this.mountainsPercentage = mountainsPercentage;
    }

    public void setShootingAllowed(boolean shootingAllowed) {
        this.shootingAllowed = shootingAllowed;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean getShootingAllowed() {
        return shootingAllowed;
    }

    public Set<MatchReport> getMatchReports() {
        return matchReports;
    }

    public void setMatchReports(Set<MatchReport> matchReports) {
        this.matchReports = matchReports;
    }

}
