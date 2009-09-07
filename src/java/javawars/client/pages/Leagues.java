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
package javawars.client.pages;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import javawars.client.data.DataProvider;
import javawars.client.pages.labels.SimpleLabel;
import javawars.client.ui.PageWithNoMenu;
import javawars.client.ui.PageWithVerticalMenu;
import javawars.domain.League;

/**
 *
 * @author bartlomiej
 */
public class Leagues extends PageWithVerticalMenu {

// KISS - we don't need it now; this content emerged because the Leagues page
// itself has been created from a refactoried copy of Workshop
//    
//    // list where all leagues accessible for the current user are shown.
//    // it is always up-to-date. Initialized when a response
//    // is obtained after invoking the getLeagues() method (on server)
//    private List<League> leagueList = new ArrayList<League>();
//
//    /**
//     * Returns the up-to-date list of leagues accessible for the current user.
//     * The list is initialized when Leagues object receives response of
//     * the method getLeagues() from server.
//     * @return
//     */
//    public List<League> getLeagueList() {
//        return leagueList;
//    }
    
    PageWithNoMenu fillerPage = new PageWithNoMenu("filler") {

        {
            setDisabled(true);
        }
        @Override
        public Composite getContent() {
            return new SimpleLabel(".");
        }

        @Override
        public Composite getLabel() {
            return new SimpleLabel("Dostępne ligi:");
        }
        
    };
    
    
    /**
     * The default composite that is shown when a leagues page is entered 
     */
    private final Composite infoComposite = new Composite() {

        private VerticalPanel mainVP = new VerticalPanel();
        

        {
            mainVP.add(new HTML("<h2 style='margin-left: 10px;'>Liga:</h2>"));
            mainVP.add(new HTML("<span style='margin-left: 10px'>Przeglądaj dostępne ligi korzystając " +
                    "z menu po lewej stronie</span>"));
            initWidget(mainVP);
        }

        @Override
        protected void onLoad() {
            removeAllChildrenPages();
            
            addChild(fillerPage);
            
            
            final AsyncCallback<List<League>> loadLeagues = new AsyncCallback<List<League>>() {
                public void onFailure(Throwable arg0) {
                    Window.alert("Wystąpił nieznany błąd. Wyloguj się i zaloguj ponownie");
                }
                public void onSuccess(List<League> leagues) {
                    for (League l : leagues) {
                        addNewLeague(l);
                    }
                }
            };

            DataProvider.getInstance().getService().getLeagues(loadLeagues);
        }
    };

    public Leagues() {
        super("Leagues");
        setOmmitDefaultContent(false);
    }

    @Override
    public Composite getContent() {
        return infoComposite;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Liga");
    }

    /**
     * Adds a new robot to the workshop; in this process a new Page is being 
     * created upon the parameters of the given robot and it is later added to 
     * the workshop menu.
     * @param robot
     */
    public void addNewLeague(League league) {
        // noting the change in our local leagues list too
        // KISS - commented out, because we disabled also the
        // local leagues list
        // leagueList.add(league);

        // adding the child page
        addChild(new LeaguePage(league, this));
    }
}