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
 * JWMatch.java
 *
 * Created on February 10, 2008, 6:29 PM
 *
 * Bartek N, nbartlomiej{at}gmail.com
 */
package javawars;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javawars.actions.Shoot;
import javawars.actions.Wait;
import javawars.actions.Error;

/**
 *
 * @author bartek
 */
public class JWMatch {

    private final List<JWRobotWrapper> robots = new ArrayList<JWRobotWrapper>();
    private final JWBoard board;
    private JWBoard oldBoard;
    private int timer = 0;
    private final JWXML jwxml = new JWXML();
    private final JWRules rules;

    public JWMatch(String robotsDeployPath, String robotsBinPath, JWRules setRules, String... paths) {


        new JWSecurityManager().initialize(Thread.currentThread(), robotsDeployPath, robotsBinPath);

        jwxml.openTag("JWMatch");
        rules = setRules;
        for (int pathNumber = 0; pathNumber < paths.length; pathNumber++) {
            String path = paths[pathNumber];
            try {
                JWRobot jwr = (JWRobot) (Class.forName(path).newInstance());
                JWRobotWrapper jwrw = new JWRobotWrapper(jwr, pathNumber + 1, "null", "null");
                robots.add(jwrw);
            } catch (InstantiationException ex) {
                System.out.println("Got an exception: " + ex.getMessage());
                ex.printStackTrace();
//                JWLog.add(JWMessage.LOADINGERROR, "Robot: \'" + path + "\' could not be loaded. InstantiationException.");
            } catch (IllegalAccessException ex) {
                System.out.println("Got an exception: " + ex.getMessage());
                ex.printStackTrace();
//                JWLog.add(JWMessage.LOADINGERROR, "Robot: \'" + path + "\' could not be loaded. IllegalAccessException.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Got an exception: " + ex.getMessage());
                ex.printStackTrace();
//                JWLog.add(JWMessage.LOADINGERROR, "Robot: \'" + path + "\' could not be loaded. ClassNotFoundException.");
            } catch (NoClassDefFoundError ex) {
                System.out.println("Got an exception: " + ex.getMessage());
                ex.printStackTrace();
//                JWLog.add(JWMessage.LOADINGERROR, "Robot: \'" + path + "\' could not be loaded. NoClassDefFoundError.");
            }
        }


        if (robots.size() != 0) {
//            board = new JWBoard(robots.listIterator(), robots.size() + 10, robots.size() + 10);
            board = new JWBoard(robots.listIterator(), rules);
        } else {
            board = null;
        }
    }

    /**
     * Creates a new instance of JWMatch
     */
    public JWMatch(String robotsDeployPath, String robotsBinPath, JWRules setRules, String[] authors, String[] names, String... paths) {


        new JWSecurityManager().initialize(Thread.currentThread(), robotsDeployPath, robotsBinPath);

        jwxml.openTag("JWMatch");
        rules = setRules;
        int errorCount = 0;
        for (int pathNumber = 0; pathNumber < paths.length; pathNumber++) {
            String path = paths[pathNumber];
            try {
                JWRobot jwr = (JWRobot) (Class.forName(path).newInstance());
                JWRobotWrapper jwrw = new JWRobotWrapper(jwr, pathNumber + 1 - errorCount, authors[pathNumber], names[pathNumber]);
                robots.add(jwrw);
            } catch (InstantiationException ex) {
                errorCount++;
                System.out.println("Got an exception: " + ex.getMessage());
                ex.printStackTrace();
//                JWLog.add(JWMessage.LOADINGERROR, "Robot: \'" + path + "\' could not be loaded. InstantiationException.");
            } catch (IllegalAccessException ex) {
                errorCount++;
                System.out.println("Got an exception: " + ex.getMessage());
                ex.printStackTrace();
//                JWLog.add(JWMessage.LOADINGERROR, "Robot: \'" + path + "\' could not be loaded. IllegalAccessException.");
            } catch (ClassNotFoundException ex) {
                errorCount++;
                System.out.println("Got an exception: " + ex.getMessage());
                ex.printStackTrace();
//                JWLog.add(JWMessage.LOADINGERROR, "Robot: \'" + path + "\' could not be loaded. ClassNotFoundException.");
            } catch (NoClassDefFoundError ex) {
                errorCount++;
                System.out.println("Got an exception: " + ex.getMessage());
                ex.printStackTrace();
//                JWLog.add(JWMessage.LOADINGERROR, "Robot: \'" + path + "\' could not be loaded. NoClassDefFoundError.");
            }
        }

        while (robots.size() < rules.getMinPopulation() ) {
            try {
                JWRobot jwr;
                jwr = (JWRobot) (Class.forName("javawars.robots.Beta").newInstance());
                // todo: fixit: do somethig! when robot with id one has an error it isn't  added counted and
                // if there's a second robot, he and the robot below will both have an id = 2;
                robots.add(new JWRobotWrapper(jwr, robots.size() + 1, "Javawars", "Beta"));
            } catch (Exception ex) {
                Logger.getLogger(JWMatch.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
        
        if (robots.size() != 0) {
//            board = new JWBoard(robots.listIterator(), robots.size() + 10, robots.size() + 10);
            board = new JWBoard(robots.listIterator(), rules);
        } else {
            board = null;
        }

    }

    public void run() {
        if (board == null) throw new NullPointerException();
        board.toXml(jwxml);

        jwxml.openTag("robots count='" + robots.size() + "' ");
        Iterator<JWRobotWrapper> xmlIterator = robots.iterator();
        while (xmlIterator.hasNext()) {
            JWRobotWrapper focus = xmlIterator.next();

            jwxml.writeTag("robot id='" + focus.getId() + "' name='" + focus.getName() + "' author='" + focus.getAuthor() + "' health='" + focus.getHealth() + "' score='" + focus.getScore() + "' action='" + focus.getDeclaredAction().getClass() + "' waitTime='" + focus.getActionDelay() + "' ");
        }
        jwxml.closeTag();


        while (timer < rules.getGameLength()) {
            Iterator<JWRobotWrapper> iterator = robots.iterator();
            timer++;
            while (iterator.hasNext()) {
//            timer++;
                JWRobotWrapper r = iterator.next();
                r.setActionDelay(r.getActionDelay() - 1);
                if (r.getActionDelay() <= 0 && r.isDead() == false) {
                    timer++;
                    // adding the timestamp
                    jwxml.openTag("timestamp time='" + timer + "'");
                    // archiving the board...
                    oldBoard = board.duplicate();
                    // executing previously declared action
                    r.getDeclaredAction().execute(r.getId(), robots, board, jwxml);
                    // update jwxml (if any changes on board, etc...);
                    updateJwxml(jwxml);

                    // generaitng ErisMessage
                    ErisMessage erisMessage = new ErisMessage(board.createElevationMap(), board.createGemMap(), board.createRobotMap(), r);
                    // sending ErisMessage to the robot
                    //r.getJwrobot().receiveData(erisMessage); <- moved to the inner thread
                    // getting action declaration
                    JWAction action = getAction(erisMessage, r);
                    // not allowing to shoot if rules forbid
                    if (rules.isShootingAllowed() == true && action instanceof Shoot) {
                        action = new Wait(20);
                    // suppose securitymanager interferes and action is not initialized
                    }
                    if (action == null) {
                        r.setFlawed();
                        action = new Error();
                    }
                    // calculating the next position
                    int newActionDelay = action.getCost(r.getId(), robots, board, jwxml);
                    r.setActionDelay(newActionDelay);
                    r.setDeclaredAction(action);

                    Iterator<JWRobotWrapper> innerXmlIterator = robots.iterator();
                    while (innerXmlIterator.hasNext()) {
                        JWRobotWrapper focus = innerXmlIterator.next();
                        jwxml.writeTag("robot health='" + focus.getHealth() + "' score='" + focus.getScore() + "' action='" + focus.getDeclaredAction().getClass() + "' waitTime='" + focus.getActionDelay() + "' ");
                    }

                    jwxml.closeTag();
                }
            }
        }
        jwxml.closeTag();
    }

    public List<JWRobotWrapper> getResults() {
        return robots;
    }

    public JWXML getJwxml() {
        return jwxml;
    }
    
    private JWAction getAction(ErisMessage erisMessage, JWRobotWrapper from) {
        
        JWAction action = null;

        try {
            // no exception shall pass!!
            if (from.isFlawed() ) throw new NullPointerException("This robot is flawed, we shall not give him another chance.");
            ThreadGroup t = new ThreadGroup("clientRobot");
            CommunicationThread thread = new CommunicationThread(t, from, erisMessage);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
            thread.join(rules.getMaxWaitTimeMs());
            
            // thread.stop();
            new JWSecurityManager().killGuests();

            action = thread.getAction();
            if (action == null ) throw new NullPointerException("Null action - we shall not tolerate that.");
            
        } catch (Exception e) {
            // e.printStackTrace();
            from.setFlawed();
            action = new Error();
        }
        return action;
    }

    private void updateJwxml(JWXML jwxml) {
        for (int a = 0; a < board.getWidth(); a++) {
            for (int b = 0; b < board.getHeight(); b++) {
                if (board.getFields()[a][b].equals(oldBoard.getFields()[a][b]) == false) {
//                    jwxml.writeTag("field " +
//                            "x='" + a + "' " +
//                            "y='" + b + "' " +
//                            "elevation='" + board.getHeightAt(a, b) + "' " +
//                            "gem='" + board.getGemAt(a, b) + "' " +
//                            "robot='" + board.getRobotAt(a, b) + "' ");

                    if (board.getRobotAt(a, b) > 0) {
                        JWRobotWrapper robotWrapper = robots.get(board.getRobotAt(a, b) - 1);
                        jwxml.writeTag("field " +
                                "x='" + a + "' " +
                                "y='" + b + "' " +
                                "elevation='" + board.getHeightAt(a, b) + "' " +
                                "gem='" + board.getGemAt(a, b) + "' " +
                                "robot='" + robotWrapper.getId() + "' " +
                                "robotHealth='" + robotWrapper.getHealth() + "' " +
                                "robotAction='" + robotWrapper.getDeclaredAction().getClass() + "' " +
                                "robotGems='" + robotWrapper.getScore() + "' ");
                    } else {
                        jwxml.writeTag("field " +
                                "x='" + a + "' " +
                                "y='" + b + "' " +
                                "elevation='" + board.getHeightAt(a, b) + "' " +
                                "gem='" + board.getGemAt(a, b) + "' " +
                                "robot='" + board.getRobotAt(a, b) + "' ");
                    }
                }
            }
        }
    }
}

class CommunicationThread extends Thread {
    
    private final JWRobotWrapper robot;
    private JWAction action;
    private final ErisMessage erisMessage;

    @Override
    public void run() {
        try {
        robot.getJwrobot().receiveData(erisMessage);
        action = robot.getJwrobot().nextAction();
        } catch (Exception e){
            e.printStackTrace();
            robot.setFlawed();
        }
    }

    CommunicationThread(ThreadGroup threadGroup, JWRobotWrapper setRobot, ErisMessage erisMessage) {
        super(threadGroup, "root");
        this.erisMessage = erisMessage;
        robot = setRobot;
    }

    public JWAction getAction() {
        return action;
    }
}

