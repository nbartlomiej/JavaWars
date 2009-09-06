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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author bartek
 */
public class User implements Serializable{

    private Long id;

    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String studentID;
    
    private boolean active = false;
    private boolean guest;
    
    private Set<Robot> robots = new HashSet<Robot>();
    private String selectedRobot;

    private Set<League> leagues = new HashSet<League>();

    private int score;
    private int gems;
    private int kills;
    private int wins;
    
    private int startScore;
    private int startGems;
    private int startKills;
    private int startWins;
    
    private Date creationDate;
    private Date lastLoginDate;
    
    private List<IpAddress> ipAddresses = new ArrayList<IpAddress>();

    public void setIpAddresses(List<IpAddress> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    public List<IpAddress> getIpAddresses() {
        return ipAddresses;
    }
    
    public User() {
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentID() {
        return studentID;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLeagues(Set<League> leagues) {
        this.leagues = leagues;
    }

    public Set<League> getLeagues() {
        return leagues;
    }
    
    public String getSelectedRobot() {
        return selectedRobot;
    }

    public void setSelectedRobot(String selectedRobot) {
        this.selectedRobot = selectedRobot;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public int getGems() {
        return gems;
    }

    public int getStartGems() {
        return startGems;
    }

    public void setStartGems(int startGems) {
        this.startGems = startGems;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Long getId() {
        return id;
    }
    
    public int getKills() {
        return kills;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public Set<Robot> getRobots() {
        return robots;
    }

    public int getScore() {
        return score;
    }

    public String getPassword() {
        return password;
    }

    public int getStartKills() {
        return startKills;
    }

    public int getStartScore() {
        return startScore;
    }

    public int getStartWins() {
        return startWins;
    }

    public String getSurname() {
        return surname;
    }

    public int getWins() {
        return wins;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartKills(int startKills) {
        this.startKills = startKills;
    }

    public void setRobots(Set<Robot> robots) {
        this.robots = robots;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStartScore(int startScore) {
        this.startScore = startScore;
    }

    public void setStartWins(int startWins) {
        this.startWins = startWins;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User ["+getLogin()+"]";
    }

    
    // KISS. i.e. commented out because we don't need it at the moment
    // and by the way, i'm tracking a bug.
//    @Override
//    public boolean equals(Object object) {
//        if (object instanceof User){
//            User u = (User)object;
//            if (u.getLogin().equals(getLogin()) ){
//                return true;
//            }
//        }
//        return false;
//    }
//    
//    

 
            
    
}
