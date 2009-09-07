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
 * Main.java
 *
 * Created on 1 luty 2008, 14:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package javawars;

/**
 *
 * @author s-009-37
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String [] robots = new String[] {"javawars.robots.Alpha", "javawars.robots.Delta", "javawars.robots.Beta"};

//        new JWSecurityManager().initialize( Thread.currentThread() );
        
        String [] robots = new String[] {"javawars.robots.Gamma"};

        JWRules rules = new JWRules();
//        rules.setGameLength( 500);
        JWMatch match = new JWMatch("", "", rules, robots);
        match.run();
        System.out.println("");
        System.out.println("***************************************");
        System.out.println("");

        System.out.println(match.getJwxml().getContent());
        
//        JWLog.print();
        

        System.out.println("");
        System.out.println("***************************************");
        System.out.println("");
        
//        JWGame gm = new JWGame(100, robots);
//        gm.editRules().setGameLength(500);
//        gm.run();
//        JWLog.print(JWMessage.GAMERESULTS);
        
        
    }
    
}
