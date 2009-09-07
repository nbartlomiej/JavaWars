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
 * ServiceProviderImpl.java
 *
 * Created on October 8, 2008, 4:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package javawars.server.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javawars.JWMatch;
import javawars.JWRobotWrapper;
import javawars.JWRules;
import javawars.JWTerrainGenerator;
import javawars.client.data.SessionConstants;
import javawars.client.services.ServiceProvider;
import javawars.domain.IpAddress;
import javawars.server.Filter;
import javawars.server.dao.LeagueDAO;
import javawars.server.dao.RobotDAO;
import javawars.server.dao.UserDAO;
import javawars.domain.League;
import javawars.domain.MatchReport;
import javawars.domain.Robot;
import javawars.domain.User;
import javawars.domain.exceptions.AuthenticationException;
import javawars.domain.exceptions.EntityNotFoundException;
import javawars.domain.exceptions.Enum.Parameter;
import javawars.domain.exceptions.Enum.Error;
import javawars.domain.exceptions.IncorrectParameterException;
import javawars.domain.exceptions.InvalidSessionException;
import javawars.server.LeagueData;
import javawars.server.dao.IpAddressDAO;
import javawars.server.dao.MatchReportDAO;
import javawars.server.exceptions.TooManyClassDefinitions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.hibernate4gwt.gwt.HibernateRemoteService;

/**
 *
 * @author bartlomiej
 */
public class ServiceProviderImpl extends HibernateRemoteService implements
        ServiceProvider {
    // the path to the application, when deployed on server
    private String applicationPath;
    // the classpath, based on the applicationPath, set also by the
    // applicationPath's setter
    private String classpath;

    public void setApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
        this.classpath = applicationPath + "WEB-INF/classes/";
    }    
    
    private String robotsBinPath;

    public void setRobotsBinPath(String robotsBinPath) {
        this.robotsBinPath = robotsBinPath;
    }
    
    // the location of the javac program on server
    private String javac;

    public void setJavac(String javac) {
        this.javac = javac;
    }    // path to the robot's folder
    private final String robotsPath = "javawars/robots/";
    // robot's package name
    private final String robotsPackage = "javawars.robots.";    // the code which all new robots receive
    private final String initialCode =
            "package javawars.robots;\n\n" +
            "import javawars.ErisMessage;\n" +
            "import javawars.JWRobot;\n" +
            "import javawars.JWAction;\n" +
            "import javawars.JWDirection;\n" +
            "import javawars.actions.*;\n\n" +
            "public class MojRobot implements JWRobot {\n\n" +
            "   public void receiveData(ErisMessage message) {\n" +
            "       ;\n" +
            "   }\n\n" +
            "   public JWAction nextAction() {\n" +
            "       return new Move(JWDirection.N);\n" +
            "   }\n\n" +
            "}\n\n";
    private HttpServletRequest request;

    /**
     * Invoked by Spring IOC right before evocing any method of this class
     * providing the fresh SttpServletRequest.
     * @param request
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    private HttpServletResponse response;

    /**
     * Invoked by Spring IOC right before evocing any method of this class
     * providing the fresh SttpServletResponse.
     * @param request
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    UserDAO userDAO;

    /**
     * Invoked by Spring IOC after creating an object of this class; provides 
     * a User's Data Access Object.
     * @param userDAO
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    RobotDAO robotDAO;

    public void setRobotDAO(RobotDAO robotDAO) {
        this.robotDAO = robotDAO;
    }
    LeagueDAO leagueDAO;

    public void setLeagueDAO(LeagueDAO leagueDAO) {
        this.leagueDAO = leagueDAO;
    }
    MatchReportDAO matchReportDAO;

    public void setMatchReportDAO(MatchReportDAO matchReportDAO) {
        this.matchReportDAO = matchReportDAO;
    }
    IpAddressDAO ipAddressDAO;

    public void setIpAddressDAO(IpAddressDAO ipAddressDAO) {
        this.ipAddressDAO = ipAddressDAO;
    }

    public SessionConstants getSessionConstants() {
        SessionConstants sessionConstants = (SessionConstants) request.getSession().getAttribute("sessionConstants");
        if (sessionConstants == null) {
            throw new NullPointerException();
        }
        return sessionConstants;
    }

    public Long newUser(String login, String password, String email, String studentID, String ipAddress, Boolean guest) throws IncorrectParameterException {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setStudentID(studentID);
        user.setActive(false);
        user.setGuest(guest);

        if (user.getPassword() == null || user.getPassword().equals("") || user.getPassword().length() < 4) {
            throw new IncorrectParameterException(Parameter.PASSWORD, Error.TOO_SHORT);
        }

        if (user.getPassword().length() > 36) {
            throw new IncorrectParameterException(Parameter.PASSWORD, Error.TOO_LONG);
        }

        // commenting out - no email gathering right now...
//        if (userDAO.userWithMailExists(email) == true) {
//            throw new IncorrectParameterException(Parameter.EMAIL, Error.IN_USE);
//        }

        // snafu: it is crucial that checks regarding the login should stay below 
        // checks regarding mail.
        if (user.getLogin() == null || user.getLogin().equals("") || user.getLogin().length() < 4) {
            throw new IncorrectParameterException(Parameter.LOGIN, Error.TOO_SHORT);
        }

        if (user.getLogin().length() < 4 || user.getLogin().equals("")) {
            throw new IncorrectParameterException(Parameter.LOGIN, Error.TOO_SHORT);
        }

        if (user.getLogin().length() > 26) {
            throw new IncorrectParameterException(Parameter.LOGIN, Error.TOO_LONG);
        }

        if (user.getLogin().matches("\\w*") == false) {
            throw new IncorrectParameterException(Parameter.LOGIN, Error.INCORRECT_CHARACTER);
        }

        if (user.getLogin().matches("\\w*") == false) {
            throw new IncorrectParameterException(Parameter.LOGIN, Error.INCORRECT_CHARACTER);
        }

        if (userDAO.userExists(login) == true) {
            throw new IncorrectParameterException(Parameter.LOGIN, Error.IN_USE);
        }

        user.setPassword(Filter.md5(password));

        user.setCreationDate(new Date());
        user.setLastLoginDate(new Date());

        userDAO.saveOrUpdateUser(user);

        IpAddress i = ipAddressDAO.getOrCreateIpAddress(ipAddress);
        user.getIpAddresses().add(i);
        i.getUsers().add(user);


        return user.getId();
    }

    public Long login(String login, String password, String ipAddress) throws AuthenticationException {

        try {
            User user = userDAO.getUser(login);
            if (user.getPassword().equals(Filter.md5(password))) {
                // if (user.isActive()==true ){
                user.setLastLoginDate(new Date());

                IpAddress i = ipAddressDAO.getOrCreateIpAddress(ipAddress);
                user.getIpAddresses().add(i);
                i.getUsers().add(user);

                return user.getId();
//                } else {
//                    throw new InactiveAccountException();
//                }
            } else {
                throw new AuthenticationException();
            }
        } catch (EntityNotFoundException entityNotFoundException) {
            throw new AuthenticationException();
        }




    }

    public Robot createRobot(String robotName) throws IncorrectParameterException {
        if (robotName.length() < 4) {
            throw new IncorrectParameterException(Parameter.NAME, Error.TOO_SHORT);
        }
        if (robotName.length() > 16) {
            throw new IncorrectParameterException(Parameter.NAME, Error.TOO_LONG);
        }
        if (robotName.matches("\\w*") == false) {
            throw new IncorrectParameterException(Parameter.NAME, Error.INCORRECT_CHARACTER);
        }

        User user = getCurrentUser();

        for (Robot r : user.getRobots()) {
            if (r.getName().equals(robotName)) {
                throw new IncorrectParameterException(Parameter.NAME, Error.IN_USE);
            }
        }

        Robot robot = new Robot();
        robot.setName(robotName);
        robot.setCreationDate(new Date());
        robot.setModificationDate(new Date());
        robot.setCode(initialCode);

        robot.getUsers().add(user);
        robotDAO.saveOrUpdateRobot(robot);

        user.getRobots().add(robot);
        userDAO.saveOrUpdateUser(user);

        return robot;
    }

    public List<Robot> getRobots() {
        User user = getCurrentUser();

        Robot[] robotsArray = user.getRobots().toArray(new Robot[]{});
        Arrays.sort(robotsArray, new Robot());

        List<Robot> robots = new ArrayList<Robot>();
        for (Robot r : robotsArray) {
            robots.add(r);
        }

        return robots;
    }

    public void deleteRobot(String robotName) {
        for (Robot r : getRobots()) {
            if (r.getName().equals(robotName)) {
                robotDAO.removeRobot(r.getId());
            }
        }
    }

    public String updateRobotCode(String robotName, String newCode) {
        
        try {
        for (Robot r : getRobots()) {
            if (r.getName().equals(robotName)) {
                r.setCode(newCode);
                Date currentDate = new Date();
                r.setModificationDate(currentDate);
                robotDAO.saveOrUpdateRobot(r);

                SessionConstants sessionConstants = (SessionConstants) request.getSession().getAttribute("sessionConstants");
                String errors = compileRobot(sessionConstants.getCurrentuser().getLogin(), r.getCode(), "" + currentDate.getTime());

                if (errors.equals("")) {
                    return "Robot source code updated, no errors found.";
                } else {
                    return errors;
                }
            }
        }
             } catch (Exception exception) {
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception caught in the outmost method: " + exception.getMessage(), exception);
        }


        throw new RuntimeException("No robot with the specified name found OR session error.");

    }

    /**
     * Compiles the source of a robot. The output file's names are login+id 
     * .java/.class
     * @param login - the login of robot's owner
     * @param source - robot's source file
     * @param id - unique id of the compilation; preferably a date's Long value;
     * will be appended to the end of the file name for uniqueness
     * @return error output of the compiler
     */
    private String compileRobot(String login, String source, String id) {
        if (source == null) {
            return "No source.";
        }
        FileWriter fileWriter = null;
        String mainClassName = "";
        String codeErrors = "";
        try {
            source = renameClass(source, login + id);
            mainClassName = findMainClassName(source);
            File file = new File(classpath + robotsPath + mainClassName + ".java");
            fileWriter = new FileWriter(file);
            fileWriter.write(source);
        } catch (TooManyClassDefinitions tmce) {
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception occured: " + tmce.getMessage(), tmce);
            codeErrors = "Too many class definitions; the maximum number is: " + MAX_CLASSES;
        } catch (IOException ex) {
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception occured: " + ex.getMessage(), ex);
            codeErrors = "Preprocessor IO error.";
        } catch (Exception exception) {
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception occured: " + exception.getMessage(), exception);
            codeErrors = "The given class either doesn't implement the JWRobot interface or contains typing mistakes.";
        } finally {
            try {
                fileWriter.close();
            } catch (Exception ex) {
                // Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception occured while closing a file: " + ex.getMessage(), ex);
            }
        }
        if (codeErrors.equals("")==false ){
            return codeErrors;
        } else { 
            String filepath = classpath + robotsPath + mainClassName + ".java";
            String errors = execute(javac + " " + filepath + " -classpath " + classpath, true);

            // we will wrap the output in the 'pre' tags on client side so we don't need to
            // replace newline chars with br's.
            //errors = errors.replaceAll("\n", "<BR/>");
            errors = errors.replaceAll(classpath, "CLASSPATH/");
            return errors;
        }
    }

    private static String findMainClassName(String source) {
        Matcher lineMatcher = Pattern.compile("\\bclass[\\s]+[^\\s]*[\\s]+implements[\\s]+JWRobot[^\\s]*\\b").matcher(source);
        lineMatcher.find();
        source = source.substring(lineMatcher.start());

        Matcher classMatcher = Pattern.compile("\\bclass\\b").matcher(source);
        classMatcher.find();
        source = source.substring(classMatcher.end());

        Pattern classNamePattern = Pattern.compile("\\b\\w[^\\s]*\\b");
        Matcher classNameMatcher = classNamePattern.matcher(source);
        classNameMatcher.find();
        String className = classNameMatcher.group();

        return className;
    }
    
    private static final int MAX_CLASSES = 5;

    /**
     * Adds an id prefeix to all the classes found in the source file.
     * @param source
     * @param id
     * @return
     */
    private String renameClass(String source, String id) throws TooManyClassDefinitions {
        String scanner = source;
        Pattern classPattern = Pattern.compile("\\bclass\\b");
        int classCount = 0;
        while (classPattern.matcher(scanner).find()) {
            if (++classCount > MAX_CLASSES) throw new TooManyClassDefinitions();
            Matcher classMatcher = classPattern.matcher(scanner);
            classMatcher.find();
            scanner = scanner.substring(classMatcher.end());
            Pattern classNamePattern = Pattern.compile("\\b[\\w]+\\b");
            Matcher classNameMatcher = classNamePattern.matcher(scanner);
            classNameMatcher.find();
            String className = classNameMatcher.group();
            source = source.replaceAll("\\b" + className + "\\b", id + className);
        }
        return source;

    }

    /**
     * Executes a given command and returns the standard output or error output
     * depending on the second argument.
     * @param command
     * @param returnErrors
     * @return standard output OR standard error output - based on the second 
     * parameter
     */
    private String execute(String command, boolean returnErrors) {
        String s = null;
        String errors = "";
        String output = "";
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = stdOutput.readLine()) != null) {
                output += s + "\n";
            }
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdError.readLine()) != null) {
                errors += s + "\n";
            }
            if (returnErrors == true) {
                return errors;
            } else {
                return output;
            }
        } catch (IOException e) {
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception occured while executing a command: " + e.getMessage(), e);
        }
        return "error";
    }

    public void selectAsRepresentant(String robotName) {
        User user = getCurrentUser();

        for (Robot r : user.getRobots()) {
            if (r.equals(robotName)) {
                r.setRepresentant(true);
            } else {
                r.setRepresentant(false);
            }
        }

        user.setSelectedRobot(robotName);
    }

    public MatchReport testRobots(int leagueNumber, List<String> robotNames) {
        try {
            // the id of the match; we will use it later on, for 
            // identification purposes
            String id = "" + new Date().getTime();

            SessionConstants sessionConstants = (SessionConstants) request.getSession().getAttribute("sessionConstants");
            User user = sessionConstants.getCurrentuser();

            // transforming the string list of the robot names 
            // to the list of the robots themselves.
            List<Robot> robotList = new ArrayList<Robot>();
            for (String s : robotNames) {
                for (Robot r : getRobots()) {
                    if (r.getName().equals(s)) {
                        robotList.add(r);
                    }
                }
            }

            // A list that will store all the participants' class names
            List<String> participants = new LinkedList<String>();
            // List for storing authors of the robots; in this case only one author.
            List<String> authors = new LinkedList<String>();


            for (Robot robot : robotList) {
                // compiling each robot...
                // ommmiting any robots whose code returns any errors
                if (compileRobot(user.getLogin(), robot.getCode(), id).equals("") == false) {
                    continue;
                // recording the name of its class
                }
                try {
                    participants.add("javawars.robots." + findMainClassName(renameClass(robot.getCode(), user.getLogin() + id)));
                } catch (Exception ex) {
                    continue;
                }
                // filling the author list with one entry for as many times, as many robots 
                // there are...
                authors.add(user.getLogin());
            }

            // creating a new match
            JWRules rules = new JWRules();
            try { if (leagueNumber>=0) rules = generateRules( getLeagues().get(leagueNumber));
            } catch (Exception ex ){
                Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Invalid league number!... Exception message: " + ex.getMessage(), ex);
            }
            rules.setMaxWaitTimeMs(1000);
            rules.setMinPopulation(0);
            String[] authorsArray = authors.toArray(new String[]{});
            String[] robotNamesArray = robotNames.toArray(new String[]{});
            String[] participantsArray = participants.toArray(new String[]{});
            JWMatch match = new JWMatch(classpath+robotsPath, robotsBinPath, rules, authorsArray, robotNamesArray, participantsArray);

            match.run();
            MatchReport matchReport = new MatchReport();
            matchReport.setXmlDescription(match.getJwxml().getContent());

            return matchReport;

        //the below is just for debug purposes...
        } catch (Exception exception) {
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception occured while testing a robot: " + exception.getMessage(), exception);
            return null;
        }
    }

    /**
     * Returns an extracted via hibernate current instance of user object with 
     * the same login as one found in the sessionConstants
     * @return
     */
    private User getCurrentUser() {
        SessionConstants sessionConstants = (SessionConstants) request.getSession().getAttribute("sessionConstants");
        User user;
        try {
            user = userDAO.getUser(sessionConstants.getCurrentuser().getLogin());
            return user;
        } catch (EntityNotFoundException ex) {
            throw new InvalidSessionException();
        }
    }

    public User getUser(String login) {
        SessionConstants sessionConstants = (SessionConstants) request.getSession().getAttribute("sessionConstants");
        if (login.equals(sessionConstants.getCurrentuser().getLogin())) {
            return getCurrentUser();
        }
        // currently only fetching the logged in user data is implemented.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<League> getLeagues() {
        User user = getCurrentUser();

        List<League> leagues = leagueDAO.getAllLeagues();

        // if no leagues found in the database we have to initialize the list
        // of leagues - create them and put them into the database.
        if (leagues.isEmpty() == true) {
            for (League l : LeagueData.getLeagueList()) {
                leagueDAO.saveOrUpdateLeague(l);
                leagues.add(l);
            }
        }

        List<League> accessibleLeagues = new LinkedList<League>();
        for (League l : leagues) {
            for (User u : l.getUsers()) {
                u.getLogin();
            }
            accessibleLeagues.add(l);
            if (user.getScore() < l.getMinScore()) {
                break;
            }
        }

        return accessibleLeagues;

    }

    public League subscribeToLeague(String leagueName) throws AuthenticationException {

        try {
            User user = getCurrentUser();
            League league = leagueDAO.getLeague(leagueName);
            if (user.isGuest() == true) {
                throw new AuthenticationException();
            } else if (league.getMinScore() > user.getScore()) {
                throw new AuthenticationException();
            } else if (league.getMaxParticipants() <= league.getUsers().size()) {
                throw new AuthenticationException();
            } else {

                league.getUsers().add(user);
                user.getLeagues().clear();
                user.getLeagues().add(league);

                user.setStartScore(user.getScore());
                user.setStartGems(user.getGems());
                user.setStartKills(user.getKills());
                user.setStartWins(user.getWins());

                return league;
            }

        } catch (EntityNotFoundException ex) {
            throw new AuthenticationException();
        }
    }

    private JWRules generateRules(League league) {
        JWRules rules = new JWRules();
        rules.setBoardHeight(league.getHeight());
        rules.setBoardWidth(league.getWidth());
        if (league.getGameLength() != 0) {
            rules.setGameLength(league.getGameLength());
        }
        rules.setShootingAllowed(league.getShootingAllowed());
        JWTerrainGenerator tg = new JWTerrainGenerator(league.getGrasslandPercentage(), league.getHillsPercentage(), league.getMountainsPercentage(), league.getGemPercentage());
        rules.setTerrainGenerator(tg);
        return rules;
    }

    public void doMatch() {
        try {
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Starting new match: " + new Date());
            if (request == null) {

                final String matchID = "" + new Date().getTime();

                for (League league : leagueDAO.getAllLeagues()) {
                    LinkedList<User> users = new LinkedList<User>();
                    LinkedList<Robot> robots = new LinkedList<Robot>();

                    if (league.getUsers().size() >= 1) {
                        for (User user : league.getUsers()) {
                            for (Robot robot : user.getRobots()) {
                                if (robot.getName().equalsIgnoreCase(user.getSelectedRobot())) {
                                    users.add(user);
                                    robots.add(robot);
                                    break;
                                }
                            }
                        }

                        String[] classesTable;
                        String[] usersTable;
                        String[] robotsTable;

                        if (users.size() >= 1) {
                            classesTable = new String[users.size()];
                            usersTable = new String[users.size()];
                            robotsTable = new String[users.size()];
                            for (int a = 0; a < classesTable.length; a++) {
                                try{
                                String username = users.get(a).getLogin();
                                String robotName = robots.get(a).getName();
                                String robotCode = robots.get(a).getCode();
                                    compileRobot(username, robotCode, matchID);
                                
                                classesTable[a] = "javawars.robots." + username + "" + robotName + matchID;
                                classesTable[a] = "javawars.robots." + findMainClassName(renameClass(robotCode, username + matchID));
                                usersTable[a] = username;
                                robotsTable[a] = robotName;
                                } catch (Exception ex){
                                    Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception while running a match: ", ex);
                                    ex.printStackTrace();
                                }
                            }

                            JWRules rules = generateRules(league);
                            JWMatch match = new JWMatch(classpath+robotsPath, robotsBinPath, rules, usersTable, robotsTable, classesTable);
                            match.run();

                            int bestGems = 0;
                            int bestRobot = -1;
                            String bestRobotOwner = "";
                            for (int a = 0; a < match.getResults().size(); a++) {
                                if (match.getResults().get(a).getScore() > bestGems) {
                                    bestGems = match.getResults().get(a).getScore();
                                    bestRobot = a;
                                    bestRobotOwner = match.getResults().get(a).getAuthor();
                                }
                            }



                            MatchReport matchReport = new MatchReport();
                            matchReport.setDate(new Date());
                            matchReport.setXmlDescription(match.getJwxml().getContent());

                            matchReportDAO.saveOrUpdateMatchReport(matchReport);

                            league.getMatchReports().add(matchReport);
                            matchReport.getLeagues().add(league);

                            Iterator<JWRobotWrapper> jwrobotIterator = match.getResults().iterator();
                            while (jwrobotIterator.hasNext()) {
                                JWRobotWrapper jwrobot = jwrobotIterator.next();

                                try {
                                    
                                    User user = userDAO.getUser( jwrobot.getAuthor() );
                                    Robot robot = robotDAO.getRobot(user.getLogin(), jwrobot.getName());
                                    
                                    // int uid = jwrobot.getId() - 1;

                                    int gems = jwrobot.getScore();
                                    int score = (int) (0.01 * gems * league.getScoreMultiplier()) + (user.getLogin().equals(bestRobotOwner) ? 10 : 0);

                                    user.setGems(user.getGems() + gems);
                                    user.setScore(user.getScore() + score);
                                    if (user.getLogin().equals(bestRobotOwner) ) {
                                        user.setWins(user.getWins() + 1);
                                    }
                                    if (user.getLogin().equals(bestRobotOwner) ) {
                                        robot.setWins(robot.getWins() + 1);
                                    }

                                    robot.getMatches().add(matchReport);
                                    matchReport.getRobots().add(robot);
                                } catch (Exception ex) {
                                    Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception while recording score: ", ex);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, "Exception while running a match: ", ex);
            ex.printStackTrace();
        }
    }

    public List<MatchReport> fetchLeagueMatchReports(String leagueName) {
        try {
            MatchReport[] matchReportsArray = leagueDAO.getLeague(leagueName).getMatchReports().toArray(new MatchReport[]{});
            Arrays.sort(matchReportsArray, new MatchReport());
            List<MatchReport> matchReports = new ArrayList<MatchReport>();
            int counter = 10;
            for (MatchReport m : matchReportsArray) {
                matchReports.add(m);
                if (counter-- <= 0) {
                    break;
                }
            }
            return matchReports;
        } catch (EntityNotFoundException ex) {
            throw new InvalidSessionException();
        }
    }

    public List<MatchReport> fetchRobotMatchReports(String robotName) {
        Robot usersRobot = null;
        for (Robot r : getCurrentUser().getRobots()) {
            if (r.getName().equals(robotName)) {
                usersRobot = r;
                break;
            }
        }
        if (usersRobot == null) {
            throw new InvalidSessionException();
        }
        MatchReport[] matchReportsArray = usersRobot.getMatches().toArray(new MatchReport[]{});
        Arrays.sort(matchReportsArray, new MatchReport());
        List<MatchReport> matchReports = new ArrayList<MatchReport>();
        int counter = 10;
        for (MatchReport m : matchReportsArray) {
            matchReports.add(m);
            if (counter-- <= 0) {
                break;
            }
        }
        return matchReports;
    }
}
