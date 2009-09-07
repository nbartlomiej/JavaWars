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
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bartek
 */
public class Robot implements Serializable, Comparator {

    private Long id;
    private String name;
    private String code;
    private int score;
    private int wins;
    private int kills;
    private int games;
    private Date creationDate;
    private Date modificationDate;
    private boolean representant = false;
    private Set<User> users = new HashSet<User>();
    private Set<MatchReport> matches = new HashSet<MatchReport>();

    public boolean isRepresentant() {
        return representant;
    }

    public void setRepresentant(boolean representant) {
        this.representant = representant;
    }

    public Set<User> getUsers() {
        return users;
    }

    protected void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public int getGames() {
        return games;
    }

    public Long getId() {
        return id;
    }

    public int getKills() {
        return kills;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setGames(int games) {
        this.games = games;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public Set<MatchReport> getMatches() {
        return matches;
    }

    public void setMatches(Set<MatchReport> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return getName();
    }

    public int compare(Object objectOne, Object objectTwo) {
        return objectOne.toString().compareToIgnoreCase(objectTwo.toString());
    }

    // commented out - because I don't know how to solve the problem;
    // particularily the part with Hibernate: sometimes the user owning this 
    // robot is not defined; and it is impossible to judge whether a robot 
    // isequal to other instance or not.
    //
//    @Override
//    public boolean equals(Object object) {
//        if (object instanceof Robot) {
//            Robot r = (Robot) object;
//            if (r.getName().equals(getName())) {
//                if (r.getUsers().iterator().hasNext() && getUsers().iterator().hasNext() ){
//                    if (r.getUsers().iterator().next().equals(getUsers().iterator().next()) ){
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
}
