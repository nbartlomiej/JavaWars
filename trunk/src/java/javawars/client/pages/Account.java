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

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import javawars.client.data.DataProvider;
import javawars.client.data.SessionConstants;
import javawars.client.pages.labels.SimpleLabel;
import javawars.client.ui.DateFormatter;
import javawars.client.ui.PageWithNoMenu;
import javawars.client.ui.SpacerFactory;
import javawars.domain.User;

/**
 *
 * @author bartlomiej
 */
public class Account extends PageWithNoMenu {

    
    @Override
    public Composite getContent() {
        return myAccountContent;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Konto");
    }

    public Account() {
        super("Account");
    }
    
    
    private final Composite myAccountContent = new Composite(){
        private final User currentUser = new User();
        
        // initializing the storage points for data that must be downloaded 
            // from server
            final HTML userScore = new HTML();
            
            // creating the callback which will update the display after downloading
            // data from server
            final AsyncCallback<User> setCurrentUserData = new AsyncCallback<User>(){

                public void onFailure(Throwable arg0) {
                    Window.alert("Wystąpił błąd przy odświeżaniu informacji");
                }

                public void onSuccess(User user) {
                    userScore.setHTML("<i>"+user.getScore() +"</i>");
                }
                
            };
                
        @Override
        protected void onLoad(){
            if (currentUser.getLogin()!=null){
                DataProvider.getInstance().getService().getUser(currentUser.getLogin(), setCurrentUserData);
            }
        }
        
        private final VerticalPanel mainPanel = new VerticalPanel();
        {
            

            // initializing the storage points for the session constants
            final HTML userLogin = new HTML();
            final HTML userCreationDate = new HTML();
            final HTML userLastLoginDate = new HTML();
            
            // creating the callback which will process the session constants and
            // invoke the service downloading the current data from server
            final AsyncCallback<SessionConstants> setConstantUserData = new AsyncCallback<SessionConstants>() {

                public void onFailure(Throwable arg0) {
                    // Window.alert("Wystąpił nieznany błąd.");
                }

                public void onSuccess(SessionConstants sessionConstants) {
                    User user = sessionConstants.getCurrentuser();
                    userLogin.setHTML("<h2>"+user.getLogin()+"</h2>");
                    userLastLoginDate.setText(DateFormatter.extractDateAndHour(user.getLastLoginDate()));
                    userCreationDate.setText(DateFormatter.extractDateAndHour(user.getCreationDate()));
                    
                    // storing the current user's login for future refreshes
                    currentUser.setLogin(user.getLogin());
                    
                    // we have the login of the logged-in user, so we can request
                    // more data
                    DataProvider.getInstance().getService().getUser(user.getLogin(), setCurrentUserData);
                }
            };
            
            
                    
            mainPanel.add(userLogin);
            
            FlexTable table = new FlexTable();
            SpacerFactory.adjustFlexTable(table, 150, 10, 150, 10, 150, 10, 150);
            table.setText(1, 0, "Punkty:");
            table.setWidget(1, 2, userScore);
            table.setText(2, 0, "Konto założono:");
            table.setWidget(2, 2, userCreationDate);
            table.setText(2, 4, "Ostatnie logowanie:");
            table.setWidget(2, 6, userLastLoginDate);
            
            mainPanel.add(table);
            
            mainPanel.add(SpacerFactory.getHTMLSpacer(20, 20));
            
            mainPanel.add(new HTML("<a href='Logout'>Wyloguj</a>"));
            
            initWidget(mainPanel);
            
            DataProvider.getInstance().getSessionConstants(setConstantUserData);
        }
        
    };

}
