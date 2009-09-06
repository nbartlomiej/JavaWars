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

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import javawars.client.data.DataProvider;
import javawars.client.data.SessionConstants;
import javawars.client.pages.labels.SimpleLabel;
import javawars.client.ui.DateFormatter;
import javawars.client.ui.MatchViewer;
import javawars.client.ui.MatchViewerFast;
import javawars.client.ui.MatchViewerClassic;
import javawars.client.ui.MatchViewerFastest;
import javawars.client.ui.PageWithHorizontalMenu;
import javawars.client.ui.PageWithNoMenu;
import javawars.client.ui.SpacerFactory;
import javawars.domain.League;
import javawars.domain.MatchReport;
import javawars.domain.User;
import javawars.domain.exceptions.AuthenticationException;

/**
 *
 * @author bartlomiej
 */
public class LeaguePage extends PageWithHorizontalMenu {

    @Override
    public Composite getContent() {
        return new SimpleLabel(".");
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel(league.getName());
    }

    private final League league;
    private final Leagues leagues;
    private final Composite aboutComposite;
    private final Composite tableComposite;
    private final Composite matchesComposite;
    
    public LeaguePage(final League league, Leagues leagues) {
        super(league.getName() );
        this.league = league;
        this.leagues = leagues;
        
        final Button subscribeButton = new Button("zapisz się");
        final AsyncCallback refreshLeagues = new AsyncCallback(){

            public void onFailure(Throwable throwable) {
                subscribeButton.setEnabled(true);
                if (throwable instanceof AuthenticationException){
                    Window.alert("Brak możliwości zapisania się do ligi - sprawdź wymaganą liczbę punktów i wolne miejsca");
                } else {
                    Window.alert("Wystąpił nieznany błąd.");
                }
            }

            public void onSuccess(Object arg0) {
                subscribeButton.setEnabled(true);
                History.newItem("Leagues");
            }
            
        };
        
        subscribeButton.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                subscribeButton.setEnabled(false);
                DataProvider.getInstance().getService().subscribeToLeague(league.getName(), refreshLeagues);
            }
        });
                
        tableComposite  = new Composite() {
            private VerticalPanel mainPanel = new VerticalPanel();
            {
                mainPanel.add(new HTML("<h2>Tabela punktacji dla ligi " + league.getName() +":" ));
                
                if (league.getUsers().isEmpty() == true ){
                    mainPanel.add(new Label("Nie ma użytkowników zapisanych do tej ligi."));
                } else {
                    FlexTable table = new FlexTable();
                    SpacerFactory.adjustFlexTable(table, 10, 150, 10, 150, 10, 150, 10, 150);
                    table.setText(1, 1, "Użytkownik:");
                    table.setText(1, 3, "Punkty w tej lidze:");
                    table.setText(1, 5, "Wszystkie punkty:");
                    table.setText(1, 7, "Aktualny robot:");


                    User [] users = league.getUsers().toArray(new User[]{});
                    
                    // sorting the table 
                    for (int a = 0; a < users.length; a ++){
                        for (int b = a; b < users.length; b++){
                            if (a!=b && 
                                    (users[a].getScore()-users[a].getStartScore() )
                                        < (users[b].getScore()-users[b].getStartScore() )   ){
                                User c = users[a];
                                users[a] = users[b];
                                users[b] = c;
                            }
                        }
                    }
                    
                    for (int counter = 0; counter < users.length; counter++){
                        table.setText(2+counter, 0, ""+(counter+1)+".");
                        table.setText(2+counter, 1, users[counter].getLogin());
                        table.setText(2+counter, 3, ""+(users[counter].getScore()-users[counter].getStartScore()));
                        table.setText(2+counter, 5, ""+(users[counter].getScore()));
                        table.setText(2+counter, 7, ""+users[counter].getSelectedRobot() );
                    }
                    
                    mainPanel.add(table);
                }
                
                mainPanel.add(SpacerFactory.getHTMLSpacer(20, 20));
                AsyncCallback<SessionConstants> addButton = new AsyncCallback<SessionConstants>(){

                    public void onFailure(Throwable arg0) {
                        
                    }

                    public void onSuccess(SessionConstants sessionConstants) {
                        for (User u : league.getUsers() ){
                            if (u.getLogin().equals(sessionConstants.getCurrentuser().getLogin())){
                                return;
                            }
                        }
                        mainPanel.add(subscribeButton);
                    }
                    
                };
                
                if (league.getUsers().isEmpty()== true){
                    mainPanel.add(subscribeButton);
                } else {
                    DataProvider.getInstance().getSessionConstants(addButton);
                }
                
                initWidget(mainPanel);
            }
        };
        
        aboutComposite  = new Composite() {
            private VerticalPanel mainPanel = new VerticalPanel();
            {
                mainPanel.add(new HTML("<h2>Liga "+league.getName() +":" ));
                mainPanel.add(new Label(league.getDescription() ));
                mainPanel.add(SpacerFactory.getHTMLSpacer(20, 20));
                
                FlexTable table = new FlexTable();
                SpacerFactory.adjustFlexTable(table, 150, 10, 50, 10, 150, 10, 50, 10, 150);
                table.setText(1, 0, "Trawa:");
                table.setText(1, 2, league.getGrasslandPercentage()+"%");
                table.setText(1, 4, "Wzniesienia:");
                table.setText(1, 6, league.getHillsPercentage()+"%");
                table.setText(1, 8, "Góry:");
                table.setText(1, 10, league.getMountainsPercentage()+"%");
                table.setWidget(2, 0, SpacerFactory.getHTMLSpacer(10, 10));
                table.setText(3, 0, "Użycie ognia (akcja 'strzał'):");
                table.setText(3, 2, league.getShootingAllowed()?"TAK":"NIE");
                table.setWidget(4, 0, SpacerFactory.getHTMLSpacer(10, 10));
                table.setText(5, 0, "Minimalna liczba punktów:");
                table.setText(5, 2, ""+league.getMinScore());
                table.setText(6, 0, "Mnożnik otrzymywanych punktów:");
                table.setText(6, 2, "x"+league.getScoreMultiplier());
                
                mainPanel.add(table);
                initWidget(mainPanel);
            }
        };
        
        matchesComposite  = new Composite() {
            private final List<MatchReport> matches = new ArrayList<MatchReport>();
            private VerticalPanel mainPanel = new VerticalPanel();
            {
                
                AsyncCallback<List<MatchReport>> processMatches = new AsyncCallback<List<MatchReport>>(){

                    public void onFailure(Throwable arg0) {
                        Window.alert("Wystąpił błąd.");
                    }

                    public void onSuccess(final List<MatchReport> matchReports) {
                        mainPanel.clear();
                        mainPanel.add(new HTML("<h3>Ostatnie dziesięć pojedynków: </h3>"));
                        mainPanel.add(SpacerFactory.getHTMLSpacer(20, 20));
                        final ListBox matchesListBox = new ListBox();
                        
                        matchesListBox.setVisibleItemCount(5);
                        for(MatchReport m : matchReports){
                            matchesListBox.addItem(DateFormatter.extractDateAndHour(m.getDate()));
                        }
                        
                        mainPanel.add(matchesListBox);
                        mainPanel.add(SpacerFactory.getHTMLSpacer(20, 20));
                        
                        final VerticalPanel viewerPanel = new VerticalPanel();
                        
                        final Button showMatch = new Button("wyświetl");
                        showMatch.addClickListener(new ClickListener() {

                            public void onClick(Widget arg0) {
                                if (matchesListBox.getSelectedIndex() == -1 )
                                    Window.alert("nie wybrano pojedynku");
                                showMatch.setEnabled(false);
                                showMatch.setText("proszę czekać...");
                                viewerPanel.clear();
                                // viewerPanel.add(new Label("Proszę czekać"));
                                MatchViewer mv = new MatchViewerFast();
                                viewerPanel.add(mv);
                                mv.show(matchReports.get(matchesListBox.getSelectedIndex()).getXmlDescription());
                                //viewerPanel.getWidget(0).removeFromParent();
                                showMatch.setText("wyświetl");
                                showMatch.setEnabled(true);
                           }

                        });
                        
                        mainPanel.add(showMatch);
                        mainPanel.add(SpacerFactory.getHTMLSpacer(20, 20));
                        mainPanel.add(viewerPanel);

                    }
                    
                };
                
                mainPanel.add(new Label("proszę czekać..."));

                DataProvider.getInstance().getService().fetchLeagueMatchReports(league.getName(), processMatches);
                
                initWidget(mainPanel);
            }
        };
        
        
        this.addChild(new LeagueTable());
        this.addChild(new LeagueDetails());
        this.addChild(new LeagueMatches());
        

    }
    
    class LeagueTable extends PageWithNoMenu{

        @Override
        public Composite getContent() {
            return tableComposite;
        }

        @Override
        public Composite getLabel() {
            return new SimpleLabel("Tabela");
        }

        public LeagueTable() {
            super("Table");
        }
    }
    
    class LeagueMatches extends PageWithNoMenu{

        @Override
        public Composite getContent() {
            return matchesComposite;
        }

        @Override
        public Composite getLabel() {
            return new SimpleLabel("Pojedynki");
        }

        public LeagueMatches() {
            super("Matches");
        }
    }

    
    class LeagueDetails extends PageWithNoMenu{

        @Override
        public Composite getContent() {
            return aboutComposite;
        }

        @Override
        public Composite getLabel() {
            return new SimpleLabel("Opis");
        }

        public LeagueDetails() {
            super("Details");
        }
    }

    
}
