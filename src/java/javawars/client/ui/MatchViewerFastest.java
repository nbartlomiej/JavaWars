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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;

/**
 *
 * @author bartlomiej
 */

public class MatchViewerFastest extends MatchViewer {

    private final VerticalPanel mainPanel = new VerticalPanel();

    public MatchViewerFastest() {
        initWidget(mainPanel);
    }

    class Field {

        public final int elevation;
        public final int gem;
        public final int player;

        public Field(int elevation, int gem, int player) {
            this.elevation = elevation;
            this.gem = gem;
            this.player = player;
        }
    }
    
    class Robot {
        public final int id;
        public final String author;
        public final String name;
        public int health=100;
        public int gems=0;
        public String param1 = "";

        public Robot(int id, String name, String author) {
            this.id = id;
            this.author = author;
            this.name = name;
        }
        
    }
    
    private Field[][] fields;
    private Robot[] robots;
    
    private FlexTable table;
    
    private static final String [] playerColors = {
        "#f00", "#0f0", "#00f", "#f0f", "#ff0", "#0ff",
        "#f05", "#0f5", "#05f", "#f50", "#5f0", "#50f", 
        "#505", "#550", "#055", "#5f5", "#55f", "f55"
    };
    
    private static final String [] heatmap = {
        "#278c00", "#299503", "#499703", "#4d9d09", "#50a20e", "#54a90d",
        "#806600", "#a38d37", "#cabb80", "#d4cdb1", "#c5c4c0", "#cacae0", "#dbdbe3"
    };
    
    private static final int [] heatValues = {
        120, 140, 160, 180, 250, 400, 
        420, 450, 480, 510, 
        930, 960, 999
    };
    
    private String elevationToColor(int elevation){
        elevation = Math.max(100, elevation);
        elevation = Math.min(elevation, 999);
        
        int i = 0;
        for (; i < heatValues.length; i++){
            // suppose we iterate over the length of heatmap
            // - if so, we return the last entry of heatmap
            if ( i > heatmap.length ) return heatmap[heatmap.length-1];
            
            if (elevation < heatValues[i]) return heatmap[i];
        }
        return heatmap[heatmap.length-1];
        
    }
    
    private String renderCell(Field field){
        String result = "";
        
        String bgCol = elevationToColor(field.elevation);
        
        result += "<div " +
                "style='text-align: center; font-size: 12px; background-color: " + bgCol + "; width: 30px; height: 20px;' " +
                ">";

        if (field.player > 0){
            result += "<div style='width: 30px; height: 10px; font-size: 8px; color: #000; '>";
            result +=  robots[field.player-1].gems + ", " + robots[field.player-1].param1;
            result += "</div>";
            result += "<div style='width: 30px; height: 10px; font-size: 8px; background-color: " + playerColors[field.player-1] + "; color: #000; '>";
            result += robots[field.player-1].author.substring(0, 3);
            result += ": ";
            result += robots[field.player-1].name.substring(0,1);
            result += "</div>";
           
        } else if (field.gem > 0 ){
            result += "<span style='color: #aa0; background-color: #000;'>";
            result += field.gem;
            result += "</span>";
        } else {
            result += "&nbsp;";
        }
        
        result += "</div>";
        result += "<div " +
                "style='font-size: 8px; text-align: center; color: #060; background-color: " + bgCol + "; width: 30px; height: 10px; ' " +
                ">";
        result += field.elevation;
        result += "</div>";
        
        return result;
        
    }
    
    private String renderRobotsDescription( Robot[] robots){
        String result = "<table><tr>";
        boolean second = true;
        for ( Robot r : robots){
            
            if (second==true) second=false;
            else second = true;
            
            result+="<td>";
            result+="<span style='font-size: 10px; background-color: " + playerColors[r.id-1] + "; color: #000;'>";
            result+=r.author.substring(0, 3);
            result += ": ";
            result += r.name.substring(0,1);
            result += "</span></td><td>";
            result+="<span style='font-size: 10px; color: #000;'>";
            result+=r.author + ", " + r.name + "&nbsp;";
            result+="</span>";
            result+="</td>";
            if (second==true){
                result+= "</tr><tr>";
            }            
        }
        result+="</tr></table>";
        return result;
    }
    
    private String matchReport;
    
    public void show(final String matchReport) {
        this.matchReport = matchReport;
        
        // reinitializing the variables; the method 'show' is reuesable.
        mainPanel.clear();
        currentTime = 0;

        // Creating initial documents, parsers...
        Document d = XMLParser.parse(matchReport);
        Node match = d.getFirstChild();
        match.normalize();
        // NodeList matchContent = match.getChildNodes();

        // Debug
//        mainPanel.add(debugLabel);
//        mainPanel.add(new Label("" + matchContent.getLength()));
//        for (int i = 0; i < 100; i++) {
//            mainPanel.add(new Label("" + matchContent.item(i).getNodeName()));
//        }

        // Getting the board's node...
        Node boardNode = getNode(match, "board");
        // ...its width and elevation...
        int boardWidth = Integer.parseInt(boardNode.getAttributes().getNamedItem("width").getNodeValue());
        int boardHeight = Integer.parseInt(boardNode.getAttributes().getNamedItem("height").getNodeValue());
        // ...and creating a fields table.
        fields = new Field[boardWidth][boardHeight];

        // populating fields table with the exact values from the match_report
        Node fieldNode = getNode(boardNode, "field");
        while (fieldNode != null) {
            try {
                NamedNodeMap fieldAttributes = fieldNode.getAttributes();
                int x = Integer.parseInt(fieldAttributes.getNamedItem("x").getNodeValue());
                int y = Integer.parseInt(fieldAttributes.getNamedItem("y").getNodeValue());
                int elevation = Integer.parseInt(fieldAttributes.getNamedItem("elevation").getNodeValue());
                int gem = Integer.parseInt(fieldAttributes.getNamedItem("gem").getNodeValue());
                int robot = Integer.parseInt(fieldAttributes.getNamedItem("robot").getNodeValue());

                fields[x][y] = new Field(elevation, gem, robot);

                do {
                    fieldNode = fieldNode.getNextSibling();
                } while (fieldNode.getNodeName().equals("field") == false && fieldNode != null);

            } catch (Exception e) {
                break;
            }
        }
        
        Node robotsNode = getNode(match, "robots");
        int robotsCount = Integer.parseInt(robotsNode.getAttributes().getNamedItem("count").getNodeValue());
        robots = new Robot[robotsCount];
        Node robotNode = getNode(robotsNode, "robot");
        while (robotNode != null) {
            try {
                NamedNodeMap robotAttributes = robotNode.getAttributes();
                int id = Integer.parseInt(robotAttributes.getNamedItem("id").getNodeValue());
                String author = robotAttributes.getNamedItem("author").getNodeValue().toUpperCase();
                String name = robotAttributes.getNamedItem("name").getNodeValue().toUpperCase();
                
                robots[id-1]=new Robot(id, name, author);
                do {
                    robotNode = robotNode.getNextSibling();
                } while (robotNode.getNodeName().equals("robot") == false && robotNode != null);

            } catch (Exception e) {
                break;
            }
        }
        
        mainPanel.add(new HTML(renderRobotsDescription(robots)));
        
        table = new FlexTable();
        for (int a = 0; a < boardWidth; a++){
            for (int b = 0; b < boardHeight; b++){
                table.setHTML(b, a, renderCell(fields[a][b]));
            }
        }
        
        table.setBorderWidth(0);
        table.setCellPadding(0);
        table.setCellSpacing(0);
        mainPanel.add(table);
        
        currentNode = getNode(match, "timestamp");
        
        FlowPanel buttonsFlowPanel = new FlowPanel();
        
        Button nextFrameButton = new Button(">>|");
        nextFrameButton.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                nextFrame();
            }
        });
        // mainPanel.add(nextFrameButton);
        
        Button rewindButton = new Button("przewiń do początku");
        rewindButton.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                if (timer != null) timer.cancel();
                show(matchReport);
            }
        });
        
        buttonsFlowPanel.add(rewindButton);
        
        Button stopButton = new Button("||");
        stopButton.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                if (timer != null) timer.cancel();
            }
        });
        buttonsFlowPanel.add(stopButton);
        
        Button playButton = new Button(">");
        playButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                if (timer != null) timer.cancel();
                timer = new Timer(){
                    @Override
                    public void run() {
                        nextFrame();
                    }
                };
                timer.scheduleRepeating(500);
            }
        });
        
        buttonsFlowPanel.add(playButton);
        
        Button fastPlayButton = new Button(">>");
        fastPlayButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                if (timer != null) timer.cancel();
                timer = new Timer(){
                    @Override
                    public void run() {
                        nextFrame();
                    }
                };
                timer.scheduleRepeating(250);
            }
        });
        
        buttonsFlowPanel.add(fastPlayButton);
        
        Button fastestPlayButton = new Button(">>>");
        fastestPlayButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                if (timer != null) timer.cancel();
                timer = new Timer(){
                    @Override
                    public void run() {
                        nextFrame();
                    }
                };
                timer.scheduleRepeating(125);
            }
        });
        
        buttonsFlowPanel.add(fastestPlayButton);
        
        mainPanel.add(buttonsFlowPanel);
        
    }
    
    private Timer timer;
    private Node currentNode;
    private int currentTime;

    public void nextFrame(){
        currentTime += 1;
        Node timestamp = currentNode;
        
        // Window.alert("currentNode: " + currentNode);
        
        do { 
            timestamp = timestamp.getNextSibling();
        //    Window.alert("safetyCounter: " + safetyCounter + ", timestamp.getNodeName(): " + timestamp.getNodeName() );
        } while(timestamp != null && timestamp.getNodeName().equals("timestamp")==false );
       
        int timestampTime = -1;
        try { timestampTime = Integer.parseInt(timestamp.getAttributes().getNamedItem("time").getNodeValue());
        } catch (NullPointerException ex ){
            if (timer!=null)timer.cancel();
            Window.alert("Koniec pojedynku.");
            return;
        }
        
        if (currentTime >= timestampTime){
            currentNode = timestamp;
            
            Node declaration = currentNode.getFirstChild();
            while(declaration != null){
                if (declaration.getNodeName().equals("declaration")){
                    NamedNodeMap declarationAttributes = declaration.getAttributes();
                        int callerId = Integer.parseInt(declarationAttributes.getNamedItem("callerid").getNodeValue());
                        String param1 = "";
                        try{
                            param1=declarationAttributes.getNamedItem("parameter1").getNodeValue();
                        }catch (Exception ex){
                           ;
                        }                    
                        robots[callerId-1].param1 = param1;
                }
                declaration = declaration.getNextSibling();
            }
            
            Node robot = currentNode.getFirstChild();
            int id = 0;
            while(robot != null){
                if (robot.getNodeName().equals("robot")){
                    id+=1;
                    NamedNodeMap robotAttributes = robot.getAttributes();
                        int score = Integer.parseInt(robotAttributes.getNamedItem("score").getNodeValue());
                        // int id = Integer.parseInt(fieldAttributes.getNamedItem("id").getNodeValue());
                        robots[id-1].gems = score;
                        
                }
                robot = robot.getNextSibling();
            }
            
            Node field = currentNode.getFirstChild();
            while(field != null){
                if (field.getNodeName().equals("field")){
                    NamedNodeMap fieldAttributes = field.getAttributes();
                        int x = Integer.parseInt(fieldAttributes.getNamedItem("x").getNodeValue());
                        int y = Integer.parseInt(fieldAttributes.getNamedItem("y").getNodeValue());
                        int elevation = Integer.parseInt(fieldAttributes.getNamedItem("elevation").getNodeValue());
                        int gem = Integer.parseInt(fieldAttributes.getNamedItem("gem").getNodeValue());
                        int robotId = Integer.parseInt(fieldAttributes.getNamedItem("robot").getNodeValue());
                        fields[x][y] = new Field(elevation, gem, robotId);
                        table.setHTML(y, x, renderCell(fields[x][y]));
                }
                field = field.getNextSibling();
            }
            

        }
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


}