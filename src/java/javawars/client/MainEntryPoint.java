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
 * MainEntryPoint.java
 *
 * Created on October 2, 2008, 12:15 PM
 *
 */
package javawars.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import javawars.client.data.DataProvider;
import javawars.client.data.SessionConstants;
import javawars.client.pages.Account;
import javawars.client.pages.Introduction;
import javawars.client.pages.Leagues;
import javawars.client.pages.RootPage;
import javawars.client.pages.Workshop;

/**
 *
 * @author bartlomiej
 */
public class MainEntryPoint implements EntryPoint {

    /** 
    The entry point method, called automatically by loading a module
    that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {

        final RootPage rootPage = new RootPage();

        rootPage.addChild(new Introduction());
        rootPage.addChild(new Workshop());
        rootPage.addChild(new Account());


        RootPanel.get().add(rootPage);

        DataProvider.getInstance().getSessionConstants(new AsyncCallback<SessionConstants>() {

            public void onFailure(Throwable throwable) {
                // Window.alert(("Wystąpił błąd połączenia: wyloguj się i zaloguj ponownie."));
            }

            public void onSuccess(SessionConstants sessionConstants) {
                // Window.alert("User login (stored in sessionConstants): " + sessionConstants.getCurrentuser().getLogin() );
                if (sessionConstants.getCurrentuser().isGuest() == false) {
                    rootPage.addChild(new Leagues());
                }
            }
        });

        // Creating a HistoryManager object;
        HistoryManager historyManager = new HistoryManager(rootPage);

        // resolving the current history content.
        historyManager.onHistoryChanged(History.getToken());

        // registering the historyManager as historyListener to solve 
        // further history changes.
        History.addHistoryListener(historyManager);

    }
}
