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

package javawars.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javawars.domain.League;


/**
 *
 * @author bartek
 */
public class LeagueData {
    
    private static final List<League> leagueList = new LinkedList<League>();

    private static void initialize() {
        
        League league = null;
        
        // the leagues must be defined in ascending order of minimum required score
        // this is obligatory for the ServiceProviderImpl.getLeagues() method to
        // work properly

        league = new League();
        league.setDescription("Plansza sredniej wielkosci, teren o malej amplitudzie wysokosci, brak mozliwości strzelania.");
        league.setName("arkadia");
        league.setMinScore(0);
        league.setShootingAllowed(false);
        league.setTerrainOdds(100, 0, 0);
        league.setGemPercentage(7);
        league.setMaxParticipants(10);
        league.setSize(11, 11);
        league.setScoreMultiplier(1);
        leagueList.add(league);

        league = new League();
        league.setDescription("Plansza dużej wielkosci, teren o malej amplitudzie wysokosci, brak mozliwości strzelania. ");
        league.setName("elizjum");
        league.setMinScore(0);
        league.setShootingAllowed(false);
        league.setTerrainOdds(100, 0, 0);
        league.setGemPercentage(7);
        league.setMaxParticipants(13);
        league.setSize(15, 15);
        league.setScoreMultiplier(1);
        leagueList.add(league);
        
        league = new League();
        league.setDescription("Duza plansza, teren lekko gorzysty, brak mozliwości strzelania.");
        league.setName("septimontium");
        league.setMinScore(200);
        league.setShootingAllowed(false);
        league.setTerrainOdds(70, 30, 0);
        league.setGemPercentage(6);
        league.setMaxParticipants(10);
        league.setSize(15, 15);
        league.setScoreMultiplier(2);
        leagueList.add(league);
        
        league = new League();
        league.setDescription("Wąski pas gór, brak mozliwości strzelania.");
        league.setName("pireneje");
        league.setMinScore(400);
        league.setShootingAllowed(false);
        league.setTerrainOdds(50, 30, 20);
        league.setGemPercentage(5);
        league.setMaxParticipants(10);
        league.setSize(20, 5);
        league.setScoreMultiplier(3);
        leagueList.add(league);
        
        league = new League();
        league.setDescription("Oddalony od cywilizacji górzysty zakątek.");
        league.setName("termopile");
        league.setMinScore(800);
        league.setShootingAllowed(true);
        league.setTerrainOdds(50, 40, 10);
        league.setGemPercentage(6);
        league.setMaxParticipants(7);
        league.setSize(11, 11);
        league.setScoreMultiplier(4);
        leagueList.add(league);
        
        
    }
    
    public static List<League> getLeagueList(){
        if (leagueList.isEmpty() == true) initialize();
        return new LinkedList<League>(leagueList);
    }

}
