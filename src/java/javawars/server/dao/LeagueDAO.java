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

import java.util.List;
import javawars.domain.League;
import javawars.domain.exceptions.EntityNotFoundException;
import javawars.domain.exceptions.Enum.Entity;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author bartlomiej
 */
public class LeagueDAO  extends HibernateDaoSupport {

    public void removeLeague(Long league){
        Object record = getHibernateTemplate().load(League.class, league);
        getHibernateTemplate().delete(record);
    }
 
    public League getLeague(Long id) {
        return (League) getHibernateTemplate().get(League.class, id);
    }    
     
    public League getLeague(String name) throws EntityNotFoundException{
        List list = (List) getHibernateTemplate().find("from League where name=?", name);
        if (list.size()==1) return (League) list.get(0);
        else if (list.size()>1) throw new RuntimeException("Multiple leagues with the same name occured.");
        else throw new EntityNotFoundException(Entity.LEAGUE);
    }
    
    public void saveOrUpdateLeague(League league){
        getHibernateTemplate().saveOrUpdate(league);
    }   
    
    public List<League> getAllLeagues() {
        List<League> list = (List<League>) getHibernateTemplate().find("from League ");
        return list;
    }
        

}