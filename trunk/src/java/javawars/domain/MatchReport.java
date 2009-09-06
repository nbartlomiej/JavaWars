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
public class MatchReport implements Serializable, Comparator{
    
    private Long id;

    private Date date;

    private Set<Robot> robots = new HashSet<Robot>();

    private Set<League> leagues = new HashSet<League>();

    private String xmlDescription;

    public MatchReport() {
    }

    public Date getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private void setId(Long id) {
        this.id = id;
    }
    public void setXmlDescription(String xmlDescription) {
        this.xmlDescription = xmlDescription;
    }

    public String getXmlDescription() {
        return xmlDescription;
    }

    public Set<Robot> getRobots() {
        return robots;
    }

    public void setRobots(Set<Robot> robots) {
        this.robots = robots;
    }

    public void setLeagues(Set<League> leagues) {
        this.leagues = leagues;
    }

    public Set<League> getLeagues() {
        return leagues;
    }
    
        @Override
    public String toString() {
        if (getDate()!=null) {
            return ""+getDate().getTime();
        } else {
            return ""+0;
        }
    }

    public int compare(Object objectOne, Object objectTwo) {
        return (-1)*(objectOne.toString().compareToIgnoreCase(objectTwo.toString()));
    }
    
}
