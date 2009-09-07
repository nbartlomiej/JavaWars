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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javawars.client.data.DataProvider;
import javawars.client.pages.labels.SimpleLabel;
import javawars.client.ui.DateFormatter;
import javawars.client.ui.MatchViewer;
import javawars.client.ui.MatchViewerFast;
import javawars.client.ui.MatchViewerClassic;
import javawars.client.ui.PageWithHorizontalMenu;
import javawars.client.ui.PageWithNoMenu;
import javawars.client.ui.SpacerFactory;
import javawars.domain.League;
import javawars.domain.MatchReport;
import javawars.domain.Robot;

/**
 *
 * @author bartlomiej
 */
class RobotPage extends PageWithHorizontalMenu {

    private final Robot robot;
    private final Workshop workshop;
    private final Composite robotLabel;
    private final Composite statisticsContent;
    private final Composite editContent;
    private final Composite testContent;
    private final Composite matchesContent;

    public Robot getRobot() {
        return robot;
    }

    RobotPage(final Robot robot, final Workshop workshop) {
        super(robot.getName());
        this.robot = robot;
        this.workshop = workshop;


        /**
         * The robot label - displayed in a side menu.
         */
        robotLabel = new Composite() {

            private VerticalPanel mainPanel = new VerticalPanel();
            

            {
                FlexTable table = new FlexTable();

                table.setWidget(0, 0, SpacerFactory.getHTMLSpacer(100, 0));
                table.setWidget(0, 1, SpacerFactory.getHTMLSpacer(100, 0));
                Label robotName = new Label(robot.getName());
                robotName.setStyleName("RobotName");
                table.setWidget(1, 0, robotName);
                table.setText(1, 1, "" + robot.getScore());
                table.setText(2, 0, "" + DateFormatter.extractDate(robot.getCreationDate()));
                table.setText(2, 1, "" + DateFormatter.extractDate(robot.getModificationDate()));



                mainPanel.add(table);
                table.setStyleName("Table");
                mainPanel.setStyleName("RobotLabel");
                initWidget(mainPanel);
            }
        };

        /**
         * Content of the statistics subpage
         */
        statisticsContent = new Composite() {

            private VerticalPanel mainPanel = new VerticalPanel();
            

            {

                mainPanel.add(new HTML("<h2>" + robot.getName() + "</h2>"));

                final FlexTable table = new FlexTable();
                SpacerFactory.adjustFlexTable(table, 120, 50, 50, 120, 50, 50);
                table.setText(1, 0, "Data utworzenia:");
                table.setText(1, 1, "" + DateFormatter.extractDateAndHour(robot.getCreationDate()));
                table.setText(1, 3, "Ostatnia modyfikacja: ");
                table.setText(1, 4, "" + DateFormatter.extractDateAndHour(robot.getModificationDate()));
                table.setText(2, 0, "Punkty: ");
                table.setText(2, 1, "" + robot.getScore());

                // temporary commenting out - the 'choose' button doesn't get back 
                // when another robot is chosen as a representant
                //if (robot.isRepresentant() == false) {
                table.setWidget(3, 0, SpacerFactory.getHTMLSpacer(1, 15));

                table.setText(4, 0, "Ustaw jako reprezentanta:");

                final Button representButton = new Button("reprezentant");
                table.setWidget(4, 1, representButton);

                final AsyncCallback representCallback = new AsyncCallback() {

                    public void onFailure(Throwable arg0) {
                        representButton.setEnabled(true);
                    }

                    public void onSuccess(Object arg0) {
                        representButton.setEnabled(true);
                    }
                };
                representButton.addClickListener(new ClickListener() {

                    public void onClick(Widget arg0) {
                        representButton.setEnabled(false);
                        DataProvider.getInstance().getService().selectAsRepresentant(robot.getName(), representCallback);
                    }
                });

                //  }

                table.setWidget(5, 0, SpacerFactory.getHTMLSpacer(1, 15));

                table.setText(6, 0, "Usuń robota:  ");

                final Button deleteButton = new Button("usuń");
                table.setWidget(6, 1, deleteButton);

                final AsyncCallback deleteCallback = new AsyncCallback() {

                    public void onFailure(Throwable arg0) {
                        Window.alert("Wystąpił nieznany błąd.");
                        deleteButton.setEnabled(true);
                    }

                    public void onSuccess(Object arg0) {
                        History.newItem("Workshop");
                        workshop.removeChildPage(getUrl());
                        deleteButton.setEnabled(true);
                    }
                };
                deleteButton.addClickListener(new ClickListener() {

                    public void onClick(Widget arg0) {
                        deleteButton.setEnabled(false);
                        DataProvider.getInstance().getService().deleteRobot(robot.getName(), deleteCallback);
                    }
                });


                mainPanel.add(table);
                initWidget(mainPanel);
            }
        };

        /**
         * The composite which will be used as a content in the Edit subpage
         */
        editContent = new Composite() {

            private final HTML codeTextArea;
            private VerticalPanel mainPanel = new VerticalPanel();
            

            {

                codeTextArea = new HTML("<textarea id='codeTextArea' " +
                        "class='codepress java' " +
                        "style='width: " + Math.max(300, Window.getClientWidth() - 250) + "px; " +
                        "height: " + Math.max(300, Window.getClientHeight() - 300) + "px; '" +
                        ">" + robot.getCode() + "</textarea>");

                mainPanel.add(codeTextArea);
                
                final FlowPanel editors = new FlowPanel();

                CheckBox useVi = new CheckBox("Edytor VI  ", false);
                useVi.addClickListener(new ClickListener() {

                    private boolean enabled = false;

                    public void onClick(Widget arg0) {
                        if (enabled == false) {
                            enabled = true;
                            DOM.getElementById("codeTextArea").setAttribute("onfocus", "editor(this);");
                        } else {
                            enabled = false;
                            DOM.getElementById("codeTextArea").setAttribute("onfocus", ";");
                        }
                    }
                });
                editors.add(useVi);
                // editors.add(SpacerFactory.getHTMLSpacer(20, 20));
                
                final Button launchCodepress = new Button("Edytor CodePress");
                launchCodepress.addClickListener(new ClickListener() {

                    public void onClick(Widget arg0) {
                        editors.setVisible(false);
                        initializeCodePress();
                    }
                });
                
                editors.add(launchCodepress);
                mainPanel.add(editors);

                final Button save = new Button("Zapisz");
                final HTML compilerOutput = new HTML();

                final AsyncCallback<String> processOutput = new AsyncCallback<String>() {

                    String startingCode;
                    

                    {
                        startingCode = robot.getCode();
                    }

                    public void onFailure(Throwable arg0) {
                        compilerOutput.setText("Błąd połączenia z serwerem!");
                        save.setEnabled(true);
                    }

                    public void onSuccess(String output) {
                        compilerOutput.setHTML("<pre>" + output + "</pre>");
                        robot.setCode(startingCode);
                        save.setEnabled(true);
                    }
                };

                save.addClickListener(new ClickListener() {
                    
                    public native String getRobotCode() /*-{
                        return $wnd.codeTextArea.getCode();
                    }-*/;
                    
                    public native String storeRobotCode() /*-{
                        return $doc.getElementById("codeTextArea_cp").value = $wnd.codeTextArea.getCode()
                    }-*/;
                    
                    public void onClick(Widget arg0) {
                        save.setEnabled(false);
                        if (editors.isVisible()) {
                            DataProvider.getInstance().getService().updateRobotCode(robot.getName(), DOM.getElementById("codeTextArea").getPropertyString("value"), processOutput);
                        } else {
                            storeRobotCode();
                            DataProvider.getInstance().getService().updateRobotCode(robot.getName(), getRobotCode() , processOutput);
                        }
                    }
                });

                mainPanel.add(save);
                mainPanel.add(compilerOutput);

                initWidget(mainPanel);
            }
            boolean initialized = false;

            @Override
            protected void onLoad() {
                ;
            }

            public native void initializeCodePress() /*-{
            $wnd.CodePress.run();
            }-*/;
        };

        // content of the test subpage...

        testContent = new Composite() {

            private final ListBox robotList = new ListBox(true);
            private final VerticalPanel mainPanel = new VerticalPanel();
            private final VerticalPanel matchViewerPanel = new VerticalPanel();
            private final ListBox leaguesList = new ListBox();
            

            {
                HTML testHeadline = new HTML("<h2>Testuj:</h2>");
                Label testDescription = new Label("Wybierz z listy roboty, które " +
                        "chcesz przetestować na jednej planszy; Ctrl+klik - zaznaczenie więcej niż jednego robota. " +
                        "Po dokonaniu wyboru naciśni przycisk.");
                mainPanel.add(testHeadline);
                mainPanel.add(testDescription);
                mainPanel.add(SpacerFactory.getHTMLSpacer(10, 10));
                mainPanel.add(robotList);

                mainPanel.add(SpacerFactory.getHTMLSpacer(10, 10));
                HTML leagueHeadline = new HTML("<h3>Liga:</h3>");
                Label leagueDescription = new Label("Wybierz w jakiej lidze chcesz dokonać testu:");
                
                mainPanel.add(leagueHeadline);
                mainPanel.add(leagueDescription);
                mainPanel.add(leaguesList);
                mainPanel.add(SpacerFactory.getHTMLSpacer(10, 10));


                final Button testButton = new Button("Testuj");

                final AsyncCallback<MatchReport> testCallback = new AsyncCallback<MatchReport>() {

                    public void onFailure(Throwable arg0) {
                        Window.alert(("Wystąpił nieznany błąd."));
                        testButton.setEnabled(true);
                    }

                    public void onSuccess(MatchReport matchReport) {
                        try {
                            matchViewerPanel.clear();
                            MatchViewer matchViewer = new MatchViewerFast();
                            matchViewerPanel.add(matchViewer);
                            matchViewer.show(matchReport.getXmlDescription());
                            testButton.setEnabled(true);
                        } catch (Exception exception) {
                            Window.alert("Wystąpił nieznany błąd podczas wyświetlania przebiegu pojedynku!");
                        }
                    }
                };

                testButton.addClickListener(new ClickListener() {

                    public void onClick(Widget arg0) {
                        if (robotList.getSelectedIndex()==-1){
                            Window.alert("Proszę wybrać przynajmniej jednego robota");
                            return;
                        }
                        testButton.setEnabled(false);

                        // adding all the selected items of the listbox to one list
                        List<String> robotNames = new LinkedList<String>();
                        int counter = 0;
                        while (counter < robotList.getItemCount()) {
                            if (robotList.isItemSelected(counter) == true) {
                                robotNames.add(robotList.getItemText(counter));
                            }
                            counter++;
                        }

                        // invoking the method on server
                        DataProvider.getInstance().getService().testRobots(leaguesList.getSelectedIndex(), robotNames, testCallback);
                    }
                });

                mainPanel.add(SpacerFactory.getHTMLSpacer(10, 10));
                mainPanel.add(testButton);
                mainPanel.add(matchViewerPanel);
                initWidget(mainPanel);
            }            // a boolean that provides us a lock for the listbox
            boolean robotListLocked = false;
            boolean leaguesListLocked = false;

            /**
             * Refreshes the content of the listBox;
             */
            private void populateRobotListBox(List<Robot> list) {
                // if the list is locked, it means there already is an update 
                // in progress; we don't need to update it after such a short
                // time, so we quit updating completely.
                if (robotListLocked == false) {
                    robotListLocked = true;
                    robotList.clear();
                    for (Robot r : list) {
                        robotList.addItem(r.getName());
                    }
                    robotListLocked = false;
                }
            }
            /**
             * Refreshes the content of the leaguesListBox;
             */
            private void populateLeaguesListBox(List<League> list) {
                // if the list is locked, it means there already is an update 
                // in progress; we don't need to update it after such a short
                // time, so we quit updating completely.
                if (leaguesListLocked == false) {
                    leaguesListLocked = true;
                    leaguesList.clear();
                    for (League l : list) {
                        leaguesList.addItem(l.getName());
                    }
                    leaguesListLocked = false;
                }
            }
            
            private final AsyncCallback<List<League>> processLeagueCallback = new AsyncCallback<List<League>>(){

                public void onFailure(Throwable exception) {
                    Window.alert("Error, couldn't update leagues list.");
                }

                public void onSuccess(List<League> list) {
                    populateLeaguesListBox(list);
                }
                
            };

            @Override
            protected void onLoad() {
                populateRobotListBox(workshop.getRobotList());
                DataProvider.getInstance().getService().getLeagues(processLeagueCallback);
            }
        };

        // matchesContent

        matchesContent = new Composite() {

            private final List<MatchReport> matches = new ArrayList<MatchReport>();
            private VerticalPanel mainPanel = new VerticalPanel();
            

            {

                AsyncCallback<List<MatchReport>> processMatches = new AsyncCallback<List<MatchReport>>() {

                    public void onFailure(Throwable arg0) {
                        Window.alert("Wystąpił błąd.");
                    }

                    public void onSuccess(final List<MatchReport> matchReports) {
                        mainPanel.clear();
                        mainPanel.add(new HTML("<h3>Ostatnie dziesięć pojedynków: </h3>"));
                        mainPanel.add(SpacerFactory.getHTMLSpacer(20, 20));
                        final ListBox matchesListBox = new ListBox();

                        matchesListBox.setVisibleItemCount(5);
                        for (MatchReport m : matchReports) {
                            matchesListBox.addItem(DateFormatter.extractDateAndHour(m.getDate()));
                        }

                        mainPanel.add(matchesListBox);
                        mainPanel.add(SpacerFactory.getHTMLSpacer(20, 20));

                        final VerticalPanel viewerPanel = new VerticalPanel();
                        mainPanel.add(viewerPanel);

                        matchesListBox.addClickListener(new ClickListener() {

                            public void onClick(Widget arg0) {
                                matchesListBox.setEnabled(false);
                                viewerPanel.clear();
                                viewerPanel.add(new Label("Proszę czekać"));
                                MatchViewer mv = new MatchViewerFast();
                                viewerPanel.add(mv);
                                mv.show(matchReports.get(matchesListBox.getSelectedIndex()).getXmlDescription());
                                viewerPanel.getWidget(0).removeFromParent();
                                matchesListBox.setEnabled(true);
                            }
                        });

                    }
                };

                mainPanel.add(new Label("proszę czekać..."));

                DataProvider.getInstance().getService().fetchRobotMatchReports(robot.getName(), processMatches);

                initWidget(mainPanel);
            }
        };


        // adding the subpages...
        addChild(new Statistics());
        addChild(new Edit());
        addChild(new Test());
        addChild(new MatchReports());

    }
    ;

    @Override
    public Composite getContent() {
        return new SimpleLabel("To jest strona robota: " + robot.getName());
    }

    @Override
    public Composite getLabel() {
        return robotLabel;
    }

    private class Statistics extends PageWithNoMenu {

        @Override
        public Composite getContent() {
            return statisticsContent;
        }

        @Override
        public Composite getLabel() {
            return new SimpleLabel("Statystyki");
        }

        public Statistics() {
            super("Statistics");
        }
    }

    private class Edit extends PageWithNoMenu {

        @Override
        public Composite getContent() {
            return editContent;
        }

        @Override
        public Composite getLabel() {
            return new SimpleLabel("Edycja");
        }

        public Edit() {
            super("Edit");
        }
    }

    private class Test extends PageWithNoMenu {

        @Override
        public Composite getContent() {
            return testContent;
        }

        @Override
        public Composite getLabel() {
            return new SimpleLabel("Testuj");
        }

        public Test() {
            super("Test");
        }
    }

    private class MatchReports extends PageWithNoMenu {

        @Override
        public Composite getContent() {
            return matchesContent;
        }

        @Override
        public Composite getLabel() {
            return new SimpleLabel("Pojedynki");
        }

        public MatchReports() {
            super("Matches");
        }
    }
}

