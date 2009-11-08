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
 * ServiceProvider.java
 *
 * Created on October 8, 2008, 4:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package javawars.client.services;
import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;
import javawars.client.data.SessionConstants;
import javawars.domain.League;
import javawars.domain.MatchReport;
import javawars.domain.Robot;
import javawars.domain.User;
import javawars.domain.exceptions.AuthenticationException;
import javawars.domain.exceptions.IncorrectParameterException;

/**
 *
 * @author bartlomiej
 */
public interface ServiceProvider extends RemoteService{
    
    /**
     * Returns the SessionConstants object that contains all the values that 
     * won't change during one session (i.e user's login, etc.).
     * @return
     */
    public SessionConstants getSessionConstants();
    
    /**
     * Creates a new user with the given login and password and inserts it into 
     * he database. If any of the parameters disallows the proper creation, an 
     * IncorrectParameterException is thrown. Returns the database's id (Long) 
     * of the newly created object.
     * @param login
     * @param password
     * @param email
     * @return
     * @throws javawars.domain.exceptions.IncorrectParameterException
     */
    public Long newUser(String login, String password, String email, String studentID, String ipAddress, Boolean guest) throws IncorrectParameterException;
    
    /**
     * Loggs a certain user in, that is checks whether the login corresponds 
     * with the password and returns an id (Long) of the object in the User's 
     * database. If login/password mismatch occur, or login is not in the 
     * database, an AuthenticationException is thrown.
     * @param login
     * @param password
     * @return
     * @throws javawars.domain.exceptions.AuthenticationException
     */
    public Long login (String login, String password, String ipAddress) throws AuthenticationException;
    
    /**
     * Creates a robot with the given name and assigns it to the user whose login
     * was found in the sessionConstants object stored in the session.
     * @param robotName
     * @return a newly created robot object - for the ease of showing UI
     * @throws javawars.domain.exceptions.IncorrectParameterException
     */
    public Robot createRobot(String robotName) throws IncorrectParameterException;
    
    /**
     * Returns a list of robots belonging to a user found in sessionConstants object
     * of the session. If no sessionConstants object found, an InvalidSessionException 
     * is thrown
     * @return
     */
    public List<Robot> getRobots();
    
    /**
     * Deletes a robot from the robots of the logged-in user (taken from the 
     * sessionConstants)
     * @param robotName
     */
    public void deleteRobot(String robotName);
    
    /**
     * Updates the code of a robot and returns the compiler error output or 
     * information about correctness.
     * @param robotName
     * @param newCode
     * @return
     */
    public String updateRobotCode(String robotName, String newCode);
    
    /**
     * Sets this robot as the user's representant in league.
     * @param robotName
     */
    public void selectAsRepresentant(String robotName);

    /**
     * Arranges a test match between the given user's robots. It does not 
     * have any influence of user's / robot's scores. The results are later 
     * sent back to user as a MatchReport.
     * @param robotNames
     * @return
     */
    public MatchReport testRobots(int leagueNumber, List<String> robotNames);


    /**
     * Returns a user with the specified login.
     */
    public User getUser(String login);
    
    /**
     * Returns a list of leagues accessible to a current user
     * @return
     */
    public List<League> getLeagues();
    
    
    public League subscribeToLeague(String leagueName) throws AuthenticationException;

    /**
     * Unsubscribes the user from his current league
     */
    public void unsubscribeFromLeague() throws AuthenticationException;
    
    public void doMatch();
    
    public List<MatchReport> fetchLeagueMatchReports(String leagueName);
    public List<MatchReport> fetchRobotMatchReports(String robotName);
}
