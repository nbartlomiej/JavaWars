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
package javawars;

import java.security.Permission;
import javawars.actions.Move;
import javawars.actions.Shoot;
import javawars.actions.Wait;

/**
 *
 * @author bartek
 */
public class JWSecurityManager {

    public JWSecurityManager() {
    }

    public void initialize(final Thread safeThread, final String deploymentPath, final String binPath) {

        // some variables so that JVM could load their .class files
        // and client robots need not to do this (and face a permission deny)

        Wait wait = new Wait(1);
        Move move = new Move(JWDirection.N);
        Shoot shoot = new Shoot(1, 1, 1);
        
        SecurityManager sm = new SecurityManager() {

            @Override
            public void checkPermission(Permission permission) {
//                super.checkPermission(permission);
                if (Thread.currentThread() == safeThread) {
                    return;
                } else if (Thread.currentThread().getThreadGroup().getName().equals("clientRobot")) {
                    if (permission.getClass() == java.io.FilePermission.class && permission.getActions().equals("read") 
                            && ( 
                              permission.getName().indexOf( deploymentPath ) == 0  
                              || permission.getName().indexOf(binPath)==0 
                            ) 
                       ) {
                        System.out.println("Security Manager: PERMISSION ALLOWED TO LOAD A ROBOT-SPECIFIC CLASS FILE");
                        System.out.println("  Thread name: " + Thread.currentThread().getName());
                        System.out.println("   permission class: " + permission.getClass());
                        System.out.println("   permission getActions: " + permission.getActions() + ", getName: " + permission.getName());
                        System.out.println("END");
                        System.out.println("");
                    } else {
                        System.out.println("Security Manager: PERMISSION DENIAL!");
                        System.out.println("  Thread name: " + Thread.currentThread().getName());
                        System.out.println("   permission class: " + permission.getClass());
                        System.out.println("   permission getActions: " + permission.getActions() + ", getName: " + permission.getName());
                        System.out.println("END");
                        System.out.println("");
                        throw new SecurityException("JAVAWARS SECURITY ERROR! ");
                    }
                } else {
//                    System.out.println("Security Manager: PERMISSION ALLOWED");
//                    System.out.println("  Thread name: " + Thread.currentThread().getName());
//                    System.out.println("   permission class: " + permission.getClass());
//                    System.out.println("   permission getActions: " + permission.getActions() + ", getName: " + permission.getName());
//                    System.out.println("END");
//                    System.out.println("");
                }
            }
        };
        System.setSecurityManager(sm);
    }

    public void killGuests() {
        // Find the root thread group
        ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
        while (root.getParent() != null) {
            root = root.getParent();
        }
        seek(root, 0);
    }
    // This method recursively visits all thread groups under `group'.
    private void seek(ThreadGroup group, int level) {
        // Get threads in `group'
        int numThreads = group.activeCount();
        Thread[] threads = new Thread[numThreads * 2];
        numThreads = group.enumerate(threads, false);

        // Enumerate each thread in `group'
        for (int i = 0; i < numThreads; i++) {
            // Get thread
            Thread thread = threads[i];

            if (thread == Thread.currentThread() || level == 0) {
            } else if (thread.getThreadGroup().getName().equals("clientRobot")) {
                kill(thread);
            }
        }

        // Get thread subgroups of `group'
        int numGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[numGroups * 2];
        numGroups = group.enumerate(groups, false);

        // Recursively visit each subgroup
        for (int i = 0; i < numGroups; i++) {
            seek(groups[i], level + 1);
        }
    }

    @SuppressWarnings("deprecation")
    private void kill(Thread thread) {
        System.out.println("============secManager: killing: " + thread.getName() + ", " + thread.getThreadGroup().getName());
        thread.interrupt();
        thread.stop();
    }
}
