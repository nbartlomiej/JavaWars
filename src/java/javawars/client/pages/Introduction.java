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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import javawars.client.pages.labels.SimpleLabel;
import javawars.client.ui.PageWithHorizontalMenu;
import javawars.client.ui.PageWithNoMenu;

/**
 *
 * @author bartlomiej
 */
public class Introduction extends PageWithHorizontalMenu {

    private final Composite emptyComposite = new Composite() {
        private VerticalPanel introVP = new VerticalPanel();
        {
            initWidget(introVP);
        }
    };

    public Introduction() {
        super("Introduction");
        addChild(new IntroductionImages() );
        addChild(new FirstRobot() );
        addChild(new Moving() );
        addChild(new FetchingData() );
        addChild(new LeaguesExplanation() );
    }

    @Override
    public Composite getContent() {
        return emptyComposite;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Wstęp");
    }
}

class IntroductionImages extends PageWithNoMenu {
    private final Composite introductionImages = new Composite() {

        private VerticalPanel introVP = new VerticalPanel();
        

        {
            initWidget(introVP);
            introVP.add(new HTML(
                    "<img src='images/init_p1.png' /><br/>" +
                    "<img src='images/init_p2.png' /><br/>" +
                    "<img src='images/init_p3.png' /><br/>" +
                    "<img src='images/init_p4.png' />"));
        }
    };
   
    public IntroductionImages() {
        super("Images");
    }

    @Override
    public Composite getContent() {
        return introductionImages;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Wprowadzenie");    
    }
    
}

class FirstRobot extends PageWithNoMenu {

    VerticalPanel firstRobotVP = new VerticalPanel();

    public FirstRobot() {
        super("FirstRobot");
    }
    private Composite content = new Composite() {

        {
            VerticalPanel firstRobotVP = new VerticalPanel();
            initWidget(firstRobotVP);
            firstRobotVP.add(new HTML("<h3> Pierwszy robot: </h3>"));
            firstRobotVP.add(new HTML("<h4> Tworzenie: </h4>"));
            firstRobotVP.add(new HTML("Wejdź w zakładkę <b>'Warsztat'</b> i stwórz swojego pierwszego robota. " +
                    "Klikając na <b>'Edycja'</b> możesz zmieniać jego kod. W zakładce <b>'Testuj'</b> możesz porównać " +
                    "zachowanie różnych swoich robotów. "));
            firstRobotVP.add(new HTML("<h4> Kod źródłowy: </h4>"));
            firstRobotVP.add(new HTML("Twój robot powinien implementować <b>interfejs JWRobot</b>; przykład poniżej: "));

            String interfaceSource = "package javawars.robots;\n\n" +
                    "import javawars.ErisMessage;\n" +
                    "import javawars.JWRobot;\n" +
                    "import javawars.JWAction;\n" +
                    "import javawars.JWDirection;\n" +
                    "import javawars.actions.*;\n\n" +
                    "public class MojPierwszyRobot implements JWRobot {\n\n" +
                    "   public void receiveData(ErisMessage message) {\n" +
                    "       ;\n" +
                    "   }\n\n" +
                    "   public JWAction nextAction() {\n" +
                    "       return new Move(JWDirection.N);\n" +
                    "   }\n\n" +
                    "}\n\n";
            firstRobotVP.add(new HTML("<pre>" + interfaceSource + "</pre>"));
            firstRobotVP.add(new HTML("Metoda <b>'nextAction'</b> jest wywoływana za każdym razem gdy Twój robot " +
                    "może wykonać jakąś akcję. Metoda <b>'receiveData'</b> służy do przyjmowania informacji o planszy. " +
                    "Sprawdź na 'poligonie' jak zachowuje się powyższy robot a potem przejdź do kolejnej lekcji."));
        }
    };

    @Override
    public Composite getContent() {
        return content;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Pierwszy robot");
    }
}

class Moving extends PageWithNoMenu{
    
    private final Composite movingContent = new Composite() { 

    VerticalPanel moveVP = new VerticalPanel();

    {
        moveVP.add( new HTML("<h3> Poruszanie się: </h3>"));
        moveVP.add( new HTML("<h4> System akcji: </h4>"));
        moveVP.add( new HTML("Na planszy wydarzenia odbywają się w systemie turowym.<ol>"+
                "<li>Twój robot zgłasza jaką akcję chce wykonać.</li>"+
                "<li> Obliczany jest czas jej wykonywania (w turach).</li>"+
                "<li> Po upływie tego czasu akcja jest wykonywana. Robot ma możliwość zgłoszenia kolejnej.</li></ol>"));
        moveVP.add( new HTML("Poniżej te same zdarzenia opisane z uwzględnieniem języka programowania: <ol>"+
                "<li> System gry wywołuje metodę <b>nextAction</b> z kodu Twojego robota i pobiera wynik - obiekt opisujący akcję.</li>"+
                "<li> System gry oblicza ile czasu zajmie Twojemu robotowi wykonanie zgłoszonej akcji.</li>"+
                "<li> Żądana akcja jest wykonywana, a system gry ponownie wywołuje metodę <b>nextAction</b>.</li></ol>"));
        moveVP.add( new HTML("<h4> Poruszanie się: </h4>"));
        moveVP.add( new HTML("Aby Twój robot się przemieścił metoda <b>nextAction</b> powinna zwrócić obiekt <b>Move</b>. " +
                "Przyjrzyj się kodowi robota z poprzedniej lekcji - jedyną instrukcją w metodzie jest <b>'return new Move(JWDirection.N);'</b>. " +
                "Przy każdym wywołaniu metody nextAction Twój robot zwraca ten sam obiekt ruchu i dzięki temu porusza się cały czas na północ."));
        moveVP.add( new HTML("<h4> Kierunki: </h4>"));
        moveVP.add( new HTML("Konstruktor obiektu Move wymaga podania jako parametr kierunku. Twój robot może " +
                "się poruszać w ośmiu kierunkach:"));
        moveVP.add( new HTML("<img src='images/moveDirections.jpg'/>"));
        moveVP.add( new HTML("<h4> Koszt ruchu: </h4>"));
        moveVP.add( new HTML("Twój robot zużywa odpowiednio więcej czasu na pokonywanie różnic terenu:"));
        moveVP.add( new HTML("<img src='images/moveCosts.jpg'/>"));
        moveVP.add(new HTML("Na zakończenie kod robota, który za każdym razem idzie w stronę losowo wybranego kierunku."));

        String directionsSource = "package javawars.robots;\n\n" +
                "import javawars.ErisMessage;\n" +
                "import javawars.JWRobot;\n" +
                "import javawars.JWAction;\n" +
                "import javawars.JWDirection;\n" +
                "import javawars.actions.*;\n\n" +
                "import java.util.*;\n\n"+
                "public class MojDrugiRobot implements JWRobot {\n\n" +
                "   public void receiveData(ErisMessage message) {\n" +
                "       ;\n" +
                "   }\n\n" +
                "   public JWAction nextAction() {\n" +
                "       // tworzenie nowego obiektu java.util.Random \n" +
                "       Random r = new Random();\n" +
                "       // losowanie liczby z przedziału <0, 4) \n" +
                "       int losowaLiczba = r.nextInt(4); \n" +
                "       // jeśli wylosowana liczba jest równa zero \n" +
                "       // robot idzie na północ \n" +
                "       if (losowaLiczba==0) return new Move(JWDirection.N);\n "+
                "       // jeśli wylosowana liczba jest równa jeden \n" +
                "       // robot idzie na wschód \n" +
                "       else if (losowaLiczba==1) return new Move(JWDirection.E);\n" +
                "       // jeśli wylosowana liczba jest równa dwa \n" +
                "       // robot idzie na południe \n" +
                "       else if (losowaLiczba==2) return new Move(JWDirection.S);\n" +
                "       // jeśli wylosowana liczba jest równa trzy \n" +
                "       // robot idzie na zachód \n" +
                "       else return new Move(JWDirection.W);\n" +
                "   }\n\n" +
                "}\n\n";
        moveVP.add(new HTML("<pre>" + directionsSource + "</pre>"));
        initWidget(moveVP);
    }
    };




    @Override
    public Composite getContent() {
        return movingContent;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Poruszanie");
    }

    public Moving() {
        super("Moving");
    }
    
}


    
class FetchingData extends PageWithNoMenu{
    
    private final Composite mainContent = new Composite() { 

    VerticalPanel mainPanel = new VerticalPanel();

    {
        mainPanel.add( new HTML("<h3> Pobieranie informacji: </h3>"));
        mainPanel.add( new HTML("<h4> Metoda receiveData: </h4>"));
        mainPanel.add( new HTML("Tuż przed każdym wywołaniem metody <b>nextAction</b> Twój robot dostaje " +
                "informacje o aktualnym stanie planszy. System gry wywołuje metodę <b>receiveData</b> " +
                "z kodu Twojego robota i przesyła jako argument obiekt ErisMessage."));
        mainPanel.add( new HTML("<h4> Obiekt ErisMessage: </h4>"));
        mainPanel.add( new HTML("Otrzymawszy obiekt ErisMessage Twój robot może wywoływać jego metody: <ul>" +
                "<li> <b>int [][] getElevationMap()</b> - zwraca dwuwymiarową tablicę wysokości pól na planszy </li>" +
                "<li> <b>int [][] getGemMap()</b> - zwraca dwuwymiarową tablicę wartości kamieni szlachetnych na planszy </li>" +
                "<li> <b>int [][] getRobotMap()</b> - zwraca dwuwymiarową tablicę identyfikatorów robotów na planszy </li> </ul>"));
        mainPanel.add( new HTML("Przykładowe działanie wyżej wymienionych metod: "));
        mainPanel.add( new HTML("<img src='images/fetchingData.jpg'/>" ));
        mainPanel.add(new HTML ("Robot może również zażądać informacji o sobie: <ul>" +
                "<li> <b>int getMyId()</b> - zwraca identyfikator robota </li> " +
                "<li> <b>int getMyPosition().x</b> - zwraca współrzędną x położenia robota na planszy</li>" +
                "<li> <b>int getMyPosition().y</b> - zwraca współrzędną y położenia robota na planszy</li>" +
                "<li> Point getMyPosition() - zwraca obie współrzędne położenia robota na planszy w formie obiektu java.awt.Point</li></ul>"));
        mainPanel.add( new HTML("Poniżej kod robota, który idzie cały czas na północ, a gdy dojdzie " +
                "do granicy planszy - skręca w lewo."));
        String borderSource = "package javawars.robots;\n\n" +
                "import javawars.ErisMessage;\n" +
                "import javawars.JWRobot;\n" +
                "import javawars.JWAction;\n" +
                "import javawars.JWDirection;\n" +
                "import javawars.actions.*;\n\n" +
                "import java.util.*;\n\n"+
                "public class MojKolejnyRobot implements JWRobot {\n\n" +
                "   // zdefiniowanie pola myMessage do przechowywania \n" +
                "   // obiektu ErisMessage \n" +
                "   ErisMessage myMessage = null; \n\n" +
                "   public void receiveData(ErisMessage message) {\n" +
                "       // przypisanie obiektu message do pola myMessage \n" +
                "       // aby móc skorzystać z otrzymanych informacji w \n" +
                "       // metodzie nextAction() \n"+
                "       myMessage = message;\n" +
                "   }\n\n" +
                "   public JWAction nextAction() {\n" +
                "       // jeśli współrzędna y położenia robota jest większa \n" +
                "       // od zera (tzn robot nie jest przy północnej granicy \n" +
                "       // planszy) - robot idzie na północ \n" +
                "       if (myMessage.getMyPosition().y>0) return new Move(JWDirection.N);\n "+
                "       // jeśli współrzędna x położenia robota jest większa \n" +
                "       // od zera (tzn robot nie jest przy zachodniej granicy \n" +
                "       // planszy) - robot idzie na zachód \n" +
                "       else if (myMessage.getMyPosition().x>0) return new Move(JWDirection.W);\n" +
                "       // w przeciwnym razie (tzn gdy robot jest w lewym górnym \n" +
                "       // rogu) robot idzie na południowy wschód \n" +
                "       else return new Move(JWDirection.SE);\n" +
                "   }\n\n" +
                "}\n\n";
        mainPanel.add(new HTML("<pre>" + borderSource + "</pre>"));
        
        mainPanel.add(new HTML("Kolejny robot będzie skanował rozmieszczenie klejnotów na planszy " +
                "(kolumnami: z góry na dół, od lewej strony do prawej), a potem szedł w stronę " +
                "ostatniego klejnotu jaki zauważył."));
        
        String seekerSource = "    package javawars.robots;\n\n"+
                "import javawars.ErisMessage;\n" +
                "import javawars.JWRobot;\n" +
                "import javawars.JWAction;\n" +
                "import javawars.JWDirection;\n" +
                "import javawars.actions.*;\n\n" +
                "import java.util.*;\n\n"+
                "public class MojKolejnyRobot implements JWRobot {\n\n" +
                "   // zdefiniowanie pola myMessage do przechowywania \n" +
                "   // obiektu ErisMessage \n" +
                "   ErisMessage myMessage = null; \n\n" +
                "   public void receiveData(ErisMessage message) {\n" +
                "       // przypisanie obiektu message do pola myMessage \n" +
                "       // aby móc skorzystać z otrzymanych informacji w \n" +
                "       // metodzie nextAction() \n"+
                "       myMessage = message;\n" +
                "   }\n\n" +
                "   public JWAction nextAction() {\n" +
                "       // przechowujemy dwuwymiarowa tablice \n" +
                "       // wysokosci w zmiennej lokalnej gemMap \n" +
                "       int [][] gemMap = myMessage.getGemMap(); \n" +
                "       // odczytujemy dlugosc i szerokosc planszy \n" +
                "       int width = gemMap.length; \n" +
                "       int height = gemMap[0].length; \n\n" +
                
                "       // odczytujemy pozycje naszego robota \n" +
                "       int myPositionX = myMessage.getMyPosition().x; \n" +
                "       int myPositionY = myMessage.getMyPosition().y; \n\n" +

                "       // inicjalizujemy zmienne w ktorych \n" +
                "       // zapiszemy wspolrzedne klejnotu \n" +
                "       int gemPositionX = 0; \n" +
                "       int gemPositionY = 0; \n\n" +

                "       // zmienna 'a' przyjmuje wartosci od \n" +
                "       // zera do szerokosci planszy \n" +
                "       for (int a = 0; a < width; a++){ \n" +
                "           // zmienna 'b' przyjmuje wartosci od \n" +
                "           // zera do wysokosci planszy \n" +
                "           for (int b = 0; b < height; b++){ \n" +
                "               // jesli w polu o wspolrzednych  \n" +
                "               // dlugosc: 'a', szerokosc: 'b' \n" +
                "               // znajduje sie klejnot \n" +
                "               if (gemMap[a][b] > 0 ){ \n" +
                "                       // zapamietaj wspolrzedne \n" +
                "                       // tego pola \n" +
                "                       gemPositionX = a; \n" +
                "                       gemPositionY = b; \n" +
                "                } \n" +
                "            }\n" +
                "        } \n\n" +

                "       // jesli wspolrzedna X klejnotu wieksza \n" +
                "       // od wspolrzednej X naszego robota \n" +
                "       if (gemPositionX > myPositionX )  \n" +
                "               // idz na wschod \n" +
                "               return new Move(JWDirection.E); \n\n" +

                "       // w przeciwnym razie jesli wspolrzedna  \n" +
                "       // X klejnotu mniejsza od wspolrzednej \n" +
                "       // X naszego robota \n" +
                "       else if (gemPositionX < myPositionX ) \n" +
                "               // idz na zachod \n" +
                "               return new Move(JWDirection.W); \n\n" +

                "       // w przeciwnym razie(tzn gdy wspolrzedna X \n" +
                "       // klejnotu i robota jest rowna) jezeli  \n" +
                "       // wspolrzedna Y klejnotu wieksza od \n" +
                "       // wspolrzednej Y robota \n" +
                "       else if (gemPositionY > myPositionY )  \n" +
                "               // idz na poludnie \n" +
                "               return new Move(JWDirection.S); \n\n" +

                "       // w przeciwnym razie idz na polnoc \n" +
                "       else return new Move(JWDirection.N); \n\n" +

                "    } \n" +
                "}";
        
        mainPanel.add(new HTML("<pre>" + seekerSource + "</pre>"));
        mainPanel.add(new HTML("Jak napisać robota który jeszcze szybciej " +
                "będzie zbierał kamienie szlachetne?"));

        initWidget(mainPanel);
    }
    };




    @Override
    public Composite getContent() {
        return mainContent;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Pobieranie danych");
    }

    public FetchingData() {
        super("Fetching-Data");
    }
    
    
}
    
class LeaguesExplanation extends PageWithNoMenu{
    
    private final Composite mainContent = new Composite() { 

    VerticalPanel mainPanel = new VerticalPanel();

    {
        mainPanel.add(new HTML("<h3> Liga: </h3>"));
        mainPanel.add(new HTML("<h4> Cel: </h4>"));
        mainPanel.add(new HTML("W lidze Twój robot może zmierzyć się z robotami innych " +
                "użytkowników. Aby brać udział w lidze należy się zapisać do jednej z nich, a potem " +
                "wybrać robota który będzie Cię reprezentował. Roboty biorące udział w pojedynku ligowym " +
                "należą do różnych użytkowników. Pojedynki i przyznawanie punktów odbywają się w każdej " +
                "lidze <b>codziennie o 21.00</b>."));
        mainPanel.add(new HTML("<h4> Punkty: </h4>"));
        mainPanel.add(new HTML("W grze istnieje wiele lig o zróżnicowanych parametrach. " +
                "Aby zapisać się do wyższych lig wymagane jest posiadanie odpowiedniej ilości punktów. " +
                "Punkty zdobywa dla Ciebie Twój robot - na przykład poprzez zbieranie " +
                "kamieni szlachetnych."));
        mainPanel.add(new HTML("<h4> Parametry ligi: </h4>"));
        mainPanel.add(new HTML("Każda liga ma swoje inwywidualne właściwości:<dl>" +
                "<dt><i>wymiary planszy</i></dt>" +
                "<dd>wymiary planszy na której rozgrywane są pojedynki</dd>" +
                "<dt><i>charakterystyka terenu</i></dt>" +
                "<dd>plansze różnią się pod względem struktury terenowej</dd>" +
                "<dt><i>próg punktowy</i></dt>" +
                "<dd>minimalna ilość punktów konieczna do zapisania się do ligi</dd>" +
                "<dt><i>mnożnik punktów</i></dt>" +
                "<dd><u>przez taką liczbę są mnożone wszystkie zdobyte przez Ciebie punkty w danej lidze</u></dd>" +
                "<dt><i>strzał</i></dt>" +
                "<dd>pozwolenie (lub brak) na używanie akcji strzału</dd>" +
                "</du>"));
        
        mainPanel.add(new HTML("<h4>Tu kończy się dokumentacja gry JavaWars </h4>" +
                "Akcja strzału, przejmowanie kamieni szlachetnych innych robotów, obniżanie terenu, " +
                "detale służące pisaniu zaawansowanych robotów - wkrótce."));
        initWidget(mainPanel);
    }
 };




    @Override
    public Composite getContent() {
        return mainContent;
    }

    @Override
    public Composite getLabel() {
        return new SimpleLabel("Udział w ligach");
    }

    public LeaguesExplanation() {
        super("Leagues-Explanation");
    }
    
    
}


