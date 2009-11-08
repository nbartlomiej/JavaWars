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
 * ServiceProviderAsync.java
 *
 * Created on October 8, 2008, 4:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package javawars.client.services;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;
import javawars.domain.League;
import javawars.domain.Robot;


/**
 *
 * @author bartlomiej
 */
public interface ServiceProviderAsync {
    public void getSessionConstants(AsyncCallback callback);
    public void newUser(String login, String password, String email, String studentID, String ipAddress, Boolean guest, AsyncCallback callback);
    public void login (String login, String password, String ipAddress, AsyncCallback callback);
    public void createRobot (String robotName, AsyncCallback callback);
    public void getRobots(AsyncCallback callback);
    public void deleteRobot(String robotName, AsyncCallback callback);
    public void updateRobotCode(String robotName, String newCode, AsyncCallback callback);
    public void selectAsRepresentant(String robotName, AsyncCallback callback);
    public void testRobots(int leagueNumber, List<String> robotNames, AsyncCallback callback);
    public void getUser (String login, AsyncCallback callback);
    public void getLeagues(AsyncCallback callback);
    
    public void subscribeToLeague(String leagueName, AsyncCallback callback);
    public void unsubscribeFromLeague(AsyncCallback callback);
    
    public void doMatch(AsyncCallback callback);
    
    public void fetchLeagueMatchReports(String league, AsyncCallback callback);
    public void fetchRobotMatchReports(String robot, AsyncCallback callback);
    
}
