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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import javawars.domain.IpAddress;
import javawars.domain.exceptions.EntityNotFoundException;
import javawars.domain.exceptions.Enum.Entity;

/**
 *
 * @author bartlomiej
 */
public class IpAddressDAO extends HibernateDaoSupport{

    public void removeUser(Long ipAddressId){
        Object record = getHibernateTemplate().load(IpAddress.class, ipAddressId);
        getHibernateTemplate().delete(record);
    }
 
    public IpAddress getIpAddress(Long id) {
        return (IpAddress) getHibernateTemplate().get(IpAddress.class, id);
    }    
    
    public IpAddress getIpAddress(String ipAddress) throws EntityNotFoundException{
        List list = (List) getHibernateTemplate().find("from IpAddress where ipAddress=?", ipAddress);
        if (list.size()==1) return (IpAddress) list.get(0);
        else if (list.size()>1) throw new RuntimeException("Multiple users with the same login occured.");
        else throw new EntityNotFoundException(Entity.IP_ADDRESS);
    }
    
    public void saveOrUpdateIpAddress (IpAddress ipAddress){
        getHibernateTemplate().saveOrUpdate(ipAddress);
    }

    
    public boolean ipAddressExists(String ipAddress){
        List list = (List) getHibernateTemplate().find("from IpAddress where ipAddress=?", ipAddress);
        if (list==null || list.size()<1) return false;
        else if (list.size()==1) return true;
        else throw new RuntimeException("illegal state in IpAddress table for ipAddress: " + ipAddress);
    }
    
    public IpAddress getOrCreateIpAddress(String ipAddress){
        if (ipAddressExists(ipAddress) == false ){
            IpAddress i = new IpAddress();
            i.setIpAddress(ipAddress);
            saveOrUpdateIpAddress(i);
        } 
        try {
            return getIpAddress(ipAddress);
        } catch (EntityNotFoundException ex) {
            throw new RuntimeException("illegal state in IpAddress table for ipAddress: " + ipAddress);
        }
    }

    
    
}
