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

package javawars.client.pages.labels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import javawars.client.data.DataProvider;
import javawars.client.pages.Workshop;
import javawars.domain.Robot;
import javawars.domain.exceptions.IncorrectParameterException;
import javawars.domain.exceptions.InvalidSessionException;

/**
 *
 * @author bartlomiej
 */
public class CreateRobot extends Composite{

    
    private VerticalPanel mainPanel = new VerticalPanel();
    private final TextBox nameTB = new TextBox();
    private final Button submitBT = new Button ("Utwórz");
    public CreateRobot(final Workshop workshop) {
        mainPanel.setWidth("219px");
        
        nameTB.setText("Nowy robot...");
        
        mainPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        mainPanel.add(nameTB);
        mainPanel.add(submitBT);
        
        nameTB.addFocusListener(new FocusListener() {

            public void onFocus(Widget arg0) {
                if (nameTB.getText().equals("Nowy robot...")){
                    nameTB.setText("");
                }
            }

            public void onLostFocus(Widget arg0) {
                if (nameTB.getText().equals("") ) {
                    nameTB.setText("Nowy robot...");
                }
            }
        });
        
        final AsyncCallback<Robot> createRobot = new AsyncCallback<Robot>(){

            public void onFailure(Throwable throwable) {
                submitBT.setEnabled(true);
                if (throwable instanceof IncorrectParameterException ){
                    IncorrectParameterException ipe = (IncorrectParameterException) throwable;
                    Window.alert("Wystąpił błąd: " + ipe.getParameter() + " " + ipe.getError());
                } else if (throwable instanceof InvalidSessionException){
                    Window.alert("Błąd sesji użytkownika: wyloguj się i zaloguj ponownie.");
                } else {
                    Window.alert("Wystąpił nieznany błąd, wyloguj się i zaloguj ponownie.");
                   RootPanel.get().add(new HTML("Exception: " + throwable.getMessage()) );
                   throwable.printStackTrace() ;
                }
            }

            public void onSuccess(Robot robot) {
                submitBT.setEnabled(true);
                workshop.addNewRobot(robot);
            }
            
        };
        
        submitBT.addClickListener(new ClickListener() {
        final AsyncCallback<Robot> createRobot = new AsyncCallback<Robot>(){

            public void onFailure(Throwable throwable) {
                submitBT.setEnabled(true);
                if (throwable instanceof IncorrectParameterException ){
                    IncorrectParameterException ipe = (IncorrectParameterException) throwable;
                    Window.alert("Wystąpił błąd: " + ipe.getParameter() + " " + ipe.getError());
                } else if (throwable instanceof InvalidSessionException){
                    Window.alert("Błąd sesji użytkownika: wyloguj się i zaloguj ponownie.");
                } else {
                    Window.alert("Wystąpił nieznany błąd, wyloguj się i zaloguj ponownie.");
                   RootPanel.get().add(new HTML("Exception: " + throwable.getMessage()) );
                   throwable.printStackTrace() ;
                }
            }

            public void onSuccess(Robot robot) {
                submitBT.setEnabled(true);
                workshop.addNewRobot(robot);
            }
            
        };
            public void onClick(Widget arg0) {
                submitBT.setEnabled(false);
                DataProvider.getInstance().getService().createRobot( nameTB.getText() , createRobot);
            }
        });
        
                
        initWidget(mainPanel);
    }

    
}
