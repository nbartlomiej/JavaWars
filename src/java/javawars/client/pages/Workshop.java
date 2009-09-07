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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.List;
import javawars.client.data.DataProvider;
import javawars.client.pages.labels.CreateRobot;
import javawars.client.pages.labels.SimpleLabel;
import javawars.client.ui.Page;
import javawars.client.ui.PageWithNoMenu;
import javawars.client.ui.PageWithVerticalMenu;
import javawars.domain.Robot;

/**
 *
 * @author bartlomiej
 */
public class Workshop extends PageWithVerticalMenu {
    // list where all robots of the current user are shown.
    // it is always up-to-date. Initialized when a response
    // is obtained after invoking the getRobots() method (on server)
    private List<Robot> robotList = new ArrayList<Robot>();

    /**
     * Returns the up-to-date list of robots belonging to the current user.
     * The list is initialized when Workshop objects receives response of
     * the method getRobots() from server.
     * @return
     */
    public List<Robot> getRobotList() {
        return robotList;
    }
    /**
     * The default composite that is shown when a workshop is entered 
     */
    private final Composite infoComposite = new Composite() {

        private VerticalPanel mainVP = new VerticalPanel();
        

        {
            mainVP.add(new HTML("<h2 style='margin-left: 10px;'>Warsztat:</h2>"));
            mainVP.add(new HTML("<span style='margin-left: 10px'>Utwórz nowego robota lub edytuj już stworzone " +
                    "modele korzystając z menu po lewej stronie.</span>"));
            initWidget(mainVP);
        }

        @Override
        protected void onLoad() {
            removeAllChildrenPages();
            addChild(new CreateRobotPage());
            final AsyncCallback<List<Robot>> loadRobots = new AsyncCallback<List<Robot>>() {

                public void onFailure(Throwable arg0) {
                    Window.alert("Wystąpił nieznany błąd. Wyloguj się i zaloguj ponownie");
                    RootPanel.get().add(new HTML(arg0.getMessage()));
                    for (StackTraceElement s : arg0.getStackTrace() )
                        RootPanel.get().add(new HTML(s.toString()));
                }

                public void onSuccess(List<Robot> robots) {
                    for (Robot r : robots) {
                        addNewRobot(r);
                    }
                }
            };
            DataProvider.getInstance().getService().getRobots(loadRobots);

        }
    };
    /**
     * Empty composite to serve as a content for the createRobotLabel
     * (we cannot use the workshop's oryginal content because we would have to 
     * deal with an issue of adding the same content in multiple places)
     */
    private final Composite emptyComposite = new Composite() {

        private VerticalPanel mainPanel = new VerticalPanel();
        

        {
            mainPanel.add(new Label("Invisible text!"));
            initWidget(mainPanel);
        }
    };
    /**
     * javawars.client.ui.label with tools to create new robot
     */
    private final CreateRobot createRobotLabel = new CreateRobot(this);

    /**
     * Wrapper for the label for creating new robots; it makes the label a 
     * full-featured Page although with the empty composite as content.
     */
    class CreateRobotPage extends PageWithNoMenu {

        @Override
        public Composite getContent() {
            return emptyComposite;
        }

        @Override
        public Composite getLabel() {
            return createRobotLabel;
        }

        public CreateRobotPage() {
            super("CreateRobot");
            setDisabled(true);
        }
    }

    public Workshop() {
        super("Workshop");
        setOmmitDefaultContent(false);
    }

    @Override
    public Composite getContent() {
        return infoComposite;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Warsztat");
    }

    // when a robot is deleted, we have to modify our internal list of robots;
    // we're implementing this behaviour by overriding onChildRemove because on
    // robot deletion this method must (should ? ) be sooner or later invoked.
    @Override
    protected void onChildRemove(Page child, int childIndex) {
        super.onChildRemove(child, childIndex);
        if (child instanceof RobotPage) {
            Robot deletedRobot = ((RobotPage) child).getRobot();
            for (int counter = 0; counter < robotList.size(); counter++) {
                if (robotList.get(counter).getName().equals(deletedRobot.getName())) {
                    robotList.remove(counter);
                }
            }
        }
    }

    /**
     * Adds a new robot to the workshop; in this process a new Page is being 
     * created upon the parameters of the given robot and it is later added to 
     * the workshop menu.
     * @param robot
     */
    public void addNewRobot(Robot robot) {
        // noting the change in our local robot list too
        robotList.add(robot);
        // adding the child page
        addChild(new RobotPage(robot, this));
    }
}
