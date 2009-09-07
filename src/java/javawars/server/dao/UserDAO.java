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
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import javawars.domain.User;
import javawars.domain.exceptions.EntityNotFoundException;
import javawars.domain.exceptions.Enum.Entity;

/**
 *
 * @author bartlomiej
 */
public class UserDAO extends HibernateDaoSupport{

    public void removeUser(Long userId){
        Object record = getHibernateTemplate().load(User.class, userId);
        getHibernateTemplate().delete(record);
    }
 
    public User getUser(Long id) {
        return (User) getHibernateTemplate().get(User.class, id);
    }    
    
    public User getUser(String login) throws EntityNotFoundException{
        List list = (List) getHibernateTemplate().find("from User where login=?", login);
        if (list.size()==1) return (User) list.get(0);
        else if (list.size()>1) throw new RuntimeException("Multiple users with the same login occured.");
        else throw new EntityNotFoundException(Entity.USER);
    }
    
    
//    public User getUserWithMail(String email) throws EntityNotFoundException{
//        List list = (List) getHibernateTemplate().find("from User where email=?", email);
//        if (list.size()==1) return (User) list.get(0);
//        else if (list.size()>1) throw new RuntimeException("Multiple users with the same email occured.");
//        else throw new EntityNotFoundException(Entity.USER);
//    }
//    
    
    
    public void saveOrUpdateUser(User user){
        getHibernateTemplate().saveOrUpdate(user);
    }

    
    public boolean userExists(String login){
        List list = (List) getHibernateTemplate().find("from User where login=?", login);
        if (list==null || list.size()<1) return false;
        else if (list.size()==1) return true;
        else throw new RuntimeException("illegal state in User table for login: " + login);
    }
    
    public boolean userWithMailExists(String email){
        List list = (List) getHibernateTemplate().find("from User where email=?", email);
        if (list==null || list.size()<1) return false;
        else if (list.size()==1) return true;
        else throw new RuntimeException("illegal state in User table for email: " + email);
    }


}
