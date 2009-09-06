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
package javawars.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

/**
 *
 * @author bartek
 */
public class MatchViewerClassic extends MatchViewer {

    private final VerticalPanel mainPanel = new VerticalPanel();
    public MatchViewerClassic() {
        initWidget(mainPanel);
    }
    
    
    
    class Board {

        public class Field {

            int elevation = 0;
            int gem = 0;
            int robot = 0;

            public void setElevation(int elevation) {
                this.elevation = elevation;
            }

            public Field() {
            }

            public void setGem(int gems) {
                this.gem = gems;
            }

            public void setRobot(int robots) {
                this.robot = robots;
            }

            public int getElevation() {
                return elevation;
            }

            public int getGem() {
                return gem;
            }

            public int getRobot() {
                return robot;
            }
        }
        Field[][] fields;
        private final int width;
        private final int height;
        private int time = -1;

        public Board(int newWidth, int newHeight) {
            width = newWidth;
            height = newHeight;
            fields = new Field[width][height];
            for (int a = 0; a < width; a++) {
                for (int b = 0; b < height; b++) {
                    fields[a][b] = new Field();
                }
            }
        }

        public Board(Board source) {
            width = source.getWidth();
            height = source.getHeight();
            fields = new Field[width][height];
//            for (int a = 0; a < width; a++) {
//                for (int b = 0; b < height; b++) {
//                    fields[a][b] = new Field();
//                }
//            }
            for (int heightCounter = 0; heightCounter < height; heightCounter++) {
                for (int widthCounter = 0; widthCounter < width; widthCounter++) {
                    fields[widthCounter][heightCounter]=source.getField(widthCounter, heightCounter);
//                    fields[widthCounter][heightCounter].setElevation(source.getElevationAt(widthCounter, heightCounter));
//                    fields[widthCounter][heightCounter].setGem(source.getGemAt(widthCounter, heightCounter));
//                    fields[widthCounter][heightCounter].setRobot(source.getRobotAt(widthCounter, heightCounter));
                }
            }
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getTime() {
            return time;
        }
        public Field getField (int x, int y){
            return fields[x][y];
        }
        
        public void flushField (int x, int y){
            fields[x][y] = new Field();
        }
        
        public void setElevationAt(int x, int y, int newValue) {
            fields[x][y].setElevation(newValue);
        }

        public void setGemAt(int x, int y, int newValue) {
            fields[x][y].setGem(newValue);
        }

        public void setRobotAt(int x, int y, int newValue) {
            fields[x][y].setRobot(newValue);
        }
        
        public int getElevationAt(int x, int y) {
//            try {
                return fields[x][y].getElevation();
//            } catch (NullPointerException ex ){
//                fields[x][y] = new Field();
//                return fields[x][y].getElevation();
//            }
        }

        public int getGemAt(int x, int y) {
            return fields[x][y].getGem();
        }

        public int getRobotAt(int x, int y) {
            return fields[x][y].getRobot();
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    

    class Robot {

        int id;
        String name;
        String author;
        int health;
        int score;
        int kills;
        String action;
        int waitTime;

        private Robot(int id, String name, String author, int health, int score, int kills, String action, int waitTime) {
            this.id = id;
            this.name = name.substring(0, Math.min(name.length(), 7) );
            this.author = author.substring(0, Math.min(author.length(), 7) );;
            this.health = health;
            this.score = score;
            this.kills = kills;
            
            action = action.substring("class.javawars.actions.".length() );
            
            this.action = action;
            this.waitTime = waitTime;
        }
    }
    
    
    
    class Display extends Composite {
        
        class InteractiveCell extends Composite {
            private final int elevation, gem, robot, x, y;

            public InteractiveCell(int newX, int newY, int newElevation, int newGem, int newRobot) {
                this.x = newX;
                this.y = newY;
                this.elevation = newElevation;
                this.gem = newGem;
                this.robot = newRobot;
                
                String path = "images/matchViewer/";
                String imgsrc = "";

                if (gem > 300) {
                    imgsrc = "gem3.gif";
                } else if (gem > 100) {
                    imgsrc = "gem2.gif";
                } else if (gem > 0) {
                    imgsrc = "gem1.gif";
                } else if (robot > 0) {
                    imgsrc = "robot"+robot+".gif";
                } else {
                    imgsrc = "elevation1.gif";
                }
//                String content = "<div style='background: url('../images/"+backgroundsrc+"');'> <img src='"+ path+imgsrc+"' width='30' height='30'></img></span>";
                int imageWidth = 36;
                if ( boards[0].getWidth() < 20 )  imageWidth=36;
                else imageWidth = 26;
//               String content = "<div style=\"background: url(\"../images/"+backgroundsrc+"\");\"> <img src=\""+ path+imgsrc+"\" width=\""+imageWidth+"\" height=\""+imageWidth+"\"></img></div>";
                String content = "<img src=\""+ path+imgsrc+"\" width=\""+imageWidth+"\" height=\""+imageWidth+"\"></img></div>";
                HTML HTMLContent = new HTML(content);

                HTMLContent.addMouseListener(new MouseListenerAdapter(){
                    private String infoString = "["+x+","+y+"]: wysokość:"+elevation;
                    {if (robot > 0) infoString += ", robot: " + robot;
                    else if (gem > 0) infoString += ", klejnot: " + gem;}
//                    InfoPanel info = new InfoPanel(infoString);
                    public void onMouseEnter(Widget arg0) {
//                        info.show();
                        detailsLB.setText(infoString);
                    }
//                    public void onMouseMove(Widget parent, int xmouse, int ymouse) {
//                        info.setPopupPosition(parent.getAbsoluteLeft()+xmouse, parent.getAbsoluteTop()+ymouse+10);
//                    }
                    public void onMouseLeave(Widget arg0) {
//                        info.hide();
                        detailsLB.setText("Umieść kursor nad wybranym polem aby zobaczyć szczegóły");
                    }
                });

                initWidget( HTMLContent );
                
            }
            
//            class InfoPanel extends PopupPanel {
//
//                public InfoPanel( String content) {
//                    setStyleName("gwt-PopupPanel");
//                    setWidget(new Label(content));
//                }
//                
//            }
            
        }

        class RobotsInfo extends Grid {
            Robot [] oldSet;
            Robot [] currentSet;
            Robot [] rootSet;
            
            boolean backgroundDrawed = false;
            
            public RobotsInfo() {
                resize(robots[0].length, 8);
                rootSet = robots[0];
            }
            
            public void draw() {
                
                if (backgroundDrawed == false){
                    backgroundDrawed = true;
                    for (int a = 0; a < rootSet.length; a++) {
                        setText(a, 0, "" + rootSet[a].id);
                        setText(a, 1, "" + rootSet[a].name);
                        setText(a, 2, "" + rootSet[a].author);
                        String path = "http://wizard.ae.krakow.pl/~javawars/imgs/";
                        String imgsrc = "robot"+rootSet[a].id+".gif";
                        int imageWidth = 26;
//                        if ( boards[0].getWidth() < 20 )  imageWidth=36;
//                        else imageWidth = 26;
                        String content = "<img src=\""+ path+imgsrc+"\" width=\""+imageWidth+"\" height=\""+imageWidth+"\"></img></div>";
//                        HTML HTMLContent = new HTML(content);
                        setHTML(a, 0, content);
                    }
                }
                
                int position = display.getPosition();
                currentSet = robots[position];
                for (int a = 0; a < currentSet.length; a++) {
                    setText(a, 3, "" + currentSet[a].health);
                    setText(a, 4, "" + currentSet[a].score);
                    setText(a, 5, "" + currentSet[a].kills);
                    setText(a, 6, "" + currentSet[a].action);
                    setText(a, 7, "" +  (currentSet[a].waitTime-localTimer) );

                }
            }
            public void updateTimers(){
                    int position = display.getPosition();
                    currentSet = robots[position];
                    for (int a = 0; a < currentSet.length; a++) {
                        setText(a, 7, "" +  (currentSet[a].waitTime-localTimer) );
                    }
            }
        }
        

        Board[] boards;
        int position = 0;
        private final Grid displayGrid = new Grid();
        private final VerticalPanel displayVP = new VerticalPanel();
        private final Label detailsLB = new Label("Umieść kursor nad wybranym polem aby zobaczyć szczegóły");
        private final Label timeLB = new Label("Klatka: 0 ");
        private RobotsInfo robotsInfoGrid = new RobotsInfo();
        
        public Display(Board[] newBoards) {
            boards = newBoards;
//            displayGrid.setCellPadding(0);
//            displayGrid.setCellSpacing(0);
            
            HorizontalPanel displayHP = new HorizontalPanel();
            VerticalPanel leftVP = new VerticalPanel();
            VerticalPanel rightVP = new VerticalPanel();
            
            
            leftVP.add(displayGrid);
            leftVP.add(detailsLB);

            
            
            rightVP.add(robotsInfoGrid);
            rightVP.add(timeLB);
            
            FlowPanel buttonsFlow = new FlowPanel();

            final Button rewindButton = new Button("|<<");
            final Button previousButton = new Button("|<");
            final Button nextButton = new Button(">|");
            final Button playButton = new Button(">");
            final Button fastForwardButton = new Button(">>");
            final Button tripleForwardButton = new Button(">>>");



            nextButton.addClickListener(new ClickListener() {

                public void onClick(Widget arg0) {
                    display.next();
                }
            });

            previousButton.addClickListener(new ClickListener() {

                public void onClick(Widget arg0) {
                    display.previous();
                }
            });

            rewindButton.addClickListener(new ClickListener() {

                public void onClick(Widget arg0) {
                    display.rewind();
                }
            });

            playButton.addClickListener( new ClickListener() {
                Timer t = new Timer(){
                    public void run() {
                        display.increment();
                    }
                };
                boolean playing = false;
                public void onClick(Widget arg0) {
                    if (playing == false){
                        playing = true;
                        t.scheduleRepeating(300);
                        playButton.setText("||");
                        rewindButton.setEnabled(false);
                        previousButton.setEnabled(false);
                        nextButton.setEnabled(false);
                        fastForwardButton.setEnabled(false);
                        tripleForwardButton.setEnabled(false);
                    } else if (playing == true){
                        playing = false;
                        t.cancel();
                        playButton.setText(">");
                        rewindButton.setEnabled(true);
                        previousButton.setEnabled(true);
                        nextButton.setEnabled(true);
                        fastForwardButton.setEnabled(true);
                        tripleForwardButton.setEnabled(true);


                    }
                }
            });

            fastForwardButton.addClickListener( new ClickListener() {
                Timer t = new Timer(){
                    public void run() {
                        display.increment();
                    }
                };
                boolean playing = false;
                public void onClick(Widget arg0) {
                    if (playing == false){
                        playing = true;
                        t.scheduleRepeating(100);
                        fastForwardButton.setText("||");
                        rewindButton.setEnabled(false);
                        playButton.setEnabled(false);
                        nextButton.setEnabled(false);
                        previousButton.setEnabled(false);
                        tripleForwardButton.setEnabled(false);
                    } else if (playing == true){
                        playing = false;
                        t.cancel();
                        fastForwardButton.setText(">>");
                        playButton.setEnabled(true);
                        nextButton.setEnabled(true);
                        rewindButton.setEnabled(true);
                        previousButton.setEnabled(true);
                        tripleForwardButton.setEnabled(true);
                    }
                }
            });
            
            tripleForwardButton.addClickListener( new ClickListener() {
                Timer t = new Timer(){
                    public void run() {
                        display.increment();
                    }
                };
                boolean playing = false;
                public void onClick(Widget arg0) {
                    if (playing == false){
                        playing = true;
                        t.scheduleRepeating(40);
                        tripleForwardButton.setText("||");
                        rewindButton.setEnabled(false);
                        playButton.setEnabled(false);
                        nextButton.setEnabled(false);
                        previousButton.setEnabled(false);
                        fastForwardButton.setEnabled(false);
                    } else if (playing == true){
                        playing = false;
                        t.cancel();
                        tripleForwardButton.setText(">>>");
                        rewindButton.setEnabled(true);
                        playButton.setEnabled(true);
                        nextButton.setEnabled(true);
                        previousButton.setEnabled(true);
                        fastForwardButton.setEnabled(true);
                    }
                }
            });            


            buttonsFlow.add(rewindButton);
            buttonsFlow.add(previousButton);
            buttonsFlow.add(playButton);
            buttonsFlow.add(fastForwardButton);
            buttonsFlow.add(tripleForwardButton);
            buttonsFlow.add(nextButton);

            rightVP.add(buttonsFlow);

           
            
            
            
            displayHP.add(leftVP);
            displayHP.add(rightVP);
            displayVP.add(displayHP);

            
            
            
            
            
            
            initWidget(displayVP);
            displayGrid.setStylePrimaryName("matchviewer");
            robotsInfoGrid.setStylePrimaryName("robotsinfo");
            
            
        }
        
        private int positionCache = position;
        private boolean initialized = false;
        Board pastBoard;

        private Board getBoard(int position){
            pastBoard = boards[positionCache];
            // lazy initialization of next boards - if not yet initialized
            if (boards[position]==null && pastBoard != null) {
                boards[position] = new Board(pastBoard);
                boards[position].setTime(Integer.parseInt(timestamps[position-1].getAttributes().getNamedItem("time").getNodeValue()));
                Node[] fields = getFields(timestamps[position-1]);
                if (fields != null) {
                    for (int b = 0; b < fields.length; b++) {
                        NamedNodeMap fieldAttributes = fields[b].getAttributes();
                        int x = Integer.parseInt(fieldAttributes.getNamedItem("x").getNodeValue());
                        int y = Integer.parseInt(fieldAttributes.getNamedItem("y").getNodeValue());
                        int elevation = Integer.parseInt(fieldAttributes.getNamedItem("elevation").getNodeValue());
                        int gem = Integer.parseInt(fieldAttributes.getNamedItem("gem").getNodeValue());
                        int robot = Integer.parseInt(fieldAttributes.getNamedItem("robot").getNodeValue());
                        boards[position].flushField(x, y);
                        boards[position].setElevationAt(x, y, elevation);
                        boards[position].setGemAt(x, y, gem);
                        boards[position].setRobotAt(x, y, robot);
                    }
                }
            }
            return boards[position];
        }
        
        public void draw() {
            robotsInfoGrid.draw();
            Board board = getBoard(position);

                displayGrid.resize(board.getWidth(), board.getHeight());

            for (int a = 0; a < board.getWidth(); a++) {
                for (int b = 0; b < board.getHeight(); b++) {
                    int elevation = board.getElevationAt(a, b);
                    int gem = board.getGemAt(a, b);
                    int robot = board.getRobotAt(a, b);

                    int pastElevation = pastBoard.getElevationAt(a, b);
                    int pastGem = pastBoard.getGemAt(a, b);
                    int pastRobot = pastBoard.getRobotAt(a, b);
                    
                    if (elevation!=pastElevation || gem != pastGem || robot != pastRobot || initialized == false ){
                        displayGrid.setWidget(b, a, new InteractiveCell(a, b, elevation, gem, robot) );
                        String backgroundsrc="";
                        if (elevation < 300) {
                            backgroundsrc = "elevation1";
                        } else if (elevation < 600) {
                            backgroundsrc = "elevation2";
                        } else {
                            backgroundsrc = "elevation3";
                        }
                        displayGrid.getCellFormatter().setStyleName(b, a, backgroundsrc);
                    }
                }
            }

            if (initialized == false) initialized = true;
            positionCache = position;
            
        }

        private void updateTimer(){
            timeLB.setText( "Klatka: " + getTime() );
        }
        
        public void next() {
            if (position < boards.length) {
                localTimer = 0;
                position++;
            }
            draw();
            updateTimer();
        }

        
        private int localTimer = 0;
        public void increment(){
            
            int currentTime = getBoard(position).getTime();
            int nextTime;
            try {
                nextTime = getBoard(position+1).getTime();
            } catch (NullPointerException e ){
                return;
            }
            if (currentTime + localTimer >= nextTime-1 ){
                next();
            } else {
                localTimer++;
            }
            robotsInfoGrid.updateTimers();
            updateTimer();
        }

        public void previous() {
            localTimer = 0;
            if (position > 0) {
                position--;
            }
            draw();
            updateTimer();
        }

        public void rewind(){
            localTimer = 0;
            position = 0;
            draw();
            updateTimer();
        }        

        public int getPosition() {
            return position;
        }

        public int getTime() {
            return boards[position].getTime()+localTimer;
        }
        
    }

  

    Display display;
    private Node[] timestamps;
    private Robot[][] robots;
    
    public void show(String string) {


        Document d = XMLParser.parse(string);
        Node match = d.getFirstChild();
        NodeList matchContent = match.getChildNodes();
        matchContent.getLength();

        Node boardNode = getNode(match, "board");
        int boardWidth = Integer.parseInt(boardNode.getAttributes().getNamedItem("width").getNodeValue());
        int boardHeight = Integer.parseInt(boardNode.getAttributes().getNamedItem("height").getNodeValue());
        timestamps = getTimestamps(match);

        Board[] boards = new Board[timestamps.length + 1];

        Board startBoard = new Board(boardWidth, boardHeight);
//      add (new Label("" + string));
//        int fieldCounter = 0;
        Node focus = getNode(boardNode, "field");
        while (focus != null) {
try{            NamedNodeMap fieldAttributes = focus.getAttributes();
            int x = Integer.parseInt(fieldAttributes.getNamedItem("x").getNodeValue());
//            add (new Label("x: " + x));
            int y = Integer.parseInt(fieldAttributes.getNamedItem("y").getNodeValue());
            int elevation = Integer.parseInt(fieldAttributes.getNamedItem("elevation").getNodeValue());
            int gem = Integer.parseInt(fieldAttributes.getNamedItem("gem").getNodeValue());
            int robot = Integer.parseInt(fieldAttributes.getNamedItem("robot").getNodeValue());
            startBoard.setElevationAt(x, y, elevation);
            startBoard.setGemAt(x, y, gem);
            startBoard.setRobotAt(x, y, robot);
//            focus = getNode(boardNode, "field", ++fieldCounter);
            do {
                focus = focus.getNextSibling();
            } while ( focus.getNodeName().equals("field") == false && focus!=null);

} catch (Exception e ){
    break;
}
        }
        startBoard.setTime(0);


        boards[0] = startBoard;

        
        Node robotsNode = getNode(match, "robots");
        int robotsCount = Integer.parseInt(robotsNode.getAttributes().getNamedItem("count").getNodeValue());
        Node[] initialRobots = getRobots(robotsNode);


        robots = new Robot[timestamps.length + 1][robotsCount];


        for (int a = 0; a < robots.length; a++) {
            Node[] robotsAlive;
            if (a != 0) {
                robotsAlive = getRobots(timestamps[a - 1]);
            } else {
                robotsAlive = initialRobots;
            }

            for (int b = 1; b < robotsAlive.length; b++) {
                if (robotsAlive[b] != null) {
                    NamedNodeMap robotAttributes = robotsAlive[b].getAttributes();
//                    int id = 0;
//                    try {
//                        id = Integer.parseInt(robotAttributes.getNamedItem("id").getNodeValue());
//                    } catch (Exception e) {
//                    }
                    String name = "null";
                    try {
                        name = robotAttributes.getNamedItem("name").getNodeValue();
                    } catch (Exception e) {
                    }
                    String author = "null";
                    try {
                        author = robotAttributes.getNamedItem("author").getNodeValue();
                    } catch (Exception e) {
                    }
                    int health = 100;
                    try {
                        health = Integer.parseInt(robotAttributes.getNamedItem("health").getNodeValue());
                    } catch (Exception e) {
                    }
                    int score = 0;
                    try {
                        score = Integer.parseInt(robotAttributes.getNamedItem("score").getNodeValue());
                    } catch (Exception e) {
                    }
                    int kills = 0;
                    try {
                        kills = Integer.parseInt(robotAttributes.getNamedItem("kills").getNodeValue());
                    } catch (Exception e) {
                    }
                    String action = "null";
                    try {
                        action = robotAttributes.getNamedItem("action").getNodeValue();
                    } catch (Exception e) {
                    }
                    int waitTime = 0;
                    try {
                        waitTime = Integer.parseInt(robotAttributes.getNamedItem("waitTime").getNodeValue());
                    } catch (Exception e) {
                    }
                    robots[a][b - 1] = new Robot(b, name, author, health, score, kills, action, waitTime);
                }
            }
        }
        
        
        
        display = new Display(boards);
        display.draw();
        mainPanel.add(display);

    }

    private Node[] getRobots(Node parent) {
        int count = -1;
        while (getNode(parent, "robot", ++count) != null) {
            ;
        }
        Node[] timestamps = new Node[count];
        for (int a = 0; a < timestamps.length; a++) {
            timestamps[a] = getNode(parent, "robot", a);
        }
        return timestamps;
    }
    
    private Node getNode(Node parent, String name) {
        Node focus = parent.getFirstChild();
        while (focus != null) {
            if (focus.getNodeName().equals(name)) {
                return focus;
            }
            focus = focus.getNextSibling();
        }
        return null;
    }

    private Node[] getTimestamps(Node parent) {
        int count = -1;
        while (getNode(parent, "timestamp", ++count) != null) {
            ;
        }
        Node[] timestamps = new Node[count];
        for (int a = 0; a < timestamps.length; a++) {
            timestamps[a] = getNode(parent, "timestamp", a);
        }
        return timestamps;
    }

    private Node[] getFields(Node parent) {
        int count = -1;
        while (getNode(parent, "field", ++count) != null) {
            ;
        }
        Node[] timestamps = new Node[count];
        for (int a = 0; a < timestamps.length; a++) {
            timestamps[a] = getNode(parent, "field", a);
        }
        return timestamps;
    }

    private Node getNode(Node parent, String name, int position) {
        Node focus = parent.getFirstChild();
        while (focus != null) {
            if (focus.getNodeName().equals(name)) {
                position--;
                if (position <= 0) {
                    return focus;
                }
            }
            focus = focus.getNextSibling();
        }
        return null;
    }

    public static void main(String[] args) {

    }
}