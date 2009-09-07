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

package javawars.server.dao;

import javawars.domain.MatchReport;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author bartlomiej
 */
public class MatchReportDAO extends HibernateDaoSupport {

    public void removeRobot(Long matchReportID){
        Object record = getHibernateTemplate().load(MatchReport.class, matchReportID);
        getHibernateTemplate().delete(record);
    }
 
    public MatchReport getMatchReport(Long id) {
        return (MatchReport) getHibernateTemplate().get(MatchReport.class, id);
    }    
    
    public void saveOrUpdateMatchReport(MatchReport matchReport){
        getHibernateTemplate().saveOrUpdate(matchReport);
    }   

}