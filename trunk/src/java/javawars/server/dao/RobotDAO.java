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
import javawars.domain.Robot;
import javawars.domain.User;
import javawars.domain.exceptions.EntityNotFoundException;
import javawars.domain.exceptions.Enum.Entity;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author bartlomiej
 */
public class RobotDAO extends HibernateDaoSupport {

    public void removeRobot(Long robotID){
        Object record = getHibernateTemplate().load(Robot.class, robotID);
        getHibernateTemplate().delete(record);
    }
 
    public Robot getRobot(Long id) {
        return (Robot) getHibernateTemplate().get(Robot.class, id);
    }    
    
    public Robot getRobot(String userLogin, String robotName) throws EntityNotFoundException{
        List list = (List) getHibernateTemplate().find("from Robot where name=?", robotName);
        for (Robot r : (List<Robot>)list){
            if (r.getUsers().toArray(new User[]{new User()})[0].getLogin().equals(userLogin))
                return r;
        }
        throw new EntityNotFoundException(Entity.ROBOT);
    }
    
    public void saveOrUpdateRobot(Robot robot){
        getHibernateTemplate().saveOrUpdate(robot);
    }   

}
