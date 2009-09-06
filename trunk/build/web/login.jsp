


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JavaWars Login/Register</title>
        <link rel="stylesheet" href="styles/splash.css" type="text/css" />
    </head>
    <body>
        <div class='top-bar'> <img alt="JavaWars" src="styles/splash-images/menu-logo.png" /> </div>
        <div class='content'>

            <div class='content-text'>
                <h2> Zasady:</h2>
                <ol type="1">
                    <li>Zadanie polega na zaprogramowaniu robota tak, aby poruszał się po planszy i zbierał kamienie szlachetne.</li>
                    <li>Użytkownik może zapisać swojego robota do ligi - wtedy jego robot bierze udział w pojedynkach, zdobywając punkty dla użytkownika.</li>
                    <li>W ramach pojedynku roboty użytkowników z ligi zostają umieszczone na jednej planszy. Nagradzane są według ilości zebranych kamieni szlachetnych.</li>
                    <li>Pojedynki odbywają się codziennie o godzinie 21.00.</li>
                    <li>Błędy w grze można zgłaszać na  <a href="http://wizard.ae.krakow.pl/~javawars/bugreport/">forum internetowym</a>. Zgłoszenie błędu może być podstawą do przyznania dodatkowych punktów - pod ocenę będzie brana wiedza, jaka jest konieczna do zdiagnozowania danego błędu</li>
                    <li>Przyznawanie punktów: <ul  type="disc"><li>pierwsze m-ce w pojedynku: 10 pkt</li><li>wygranie ligi: 20pkt</li><li>zlokalizowanie błędu w grze: 1-10 pkt</li><li>wartość zdobytych przez robota kamieni szlachetnych jest mnożona przez 0.01 i dodawana do puli punktów</li> </ul> </li>
                </ol>
                
            </div>
            <div class='content-text'>
                <h2> Aktualności: </h2>
                <p> 
                    <i>05 styczeń 2009</i><br/>
                    Zakończyła się ostatnia seria pojedynków trwająca od 16.12.2008 do 05.01.2009. 
                    Poniżej łączna klasyfikacja (pierwsze 10 miejsc): <br />
                    
                    <table border="0">
                        <thead>
                        <tr>
                        <th>Miejsce &nbsp;</th>
                        <th>Nazwa użytkownika &nbsp;</th>
                        <th>Suma punktów &nbsp;</th>
                        <th>Zwycięstwa &nbsp;</th>
                        <th>Lider ligi &nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr> <td>1 </td><td>michal  </td><td><b>360</b></td><td>14</td><td>Elizjum</td></tr>
                        <tr> <td>2 </td><td>Szyna   </td><td>336</td><td>13</td><td><b>Septimontium</b></td></tr>
                        <tr> <td>3 </td><td>pinky   </td><td>138</td><td> 2</td><td></td></tr>
                        <tr> <td>4 </td><td>Krulas  </td><td>120</td><td> 4</td><td></td></tr>
                        <tr> <td>5 </td><td>login   </td><td>117</td><td> 3</td><td></td></tr>
                        <tr> <td>6 </td><td>Lesiu   </td><td> 87</td><td> 2</td><td>Arkadia</td></tr>
                        <tr> <td>7 </td><td>dagape  </td><td> 67</td><td> 1</td><td></td></tr>
                        <tr> <td>8 </td><td>Ankon   </td><td> 42</td><td> 1</td><td></td></tr>
                        <tr> <td>9 </td><td>matek182</td><td> 38</td><td> 1</td><td></td></tr>
                        <tr> <td>10</td><td>pokwap  </td><td> 34</td><td> 1</td><td></td></tr>
                        </tbody>
                    </table>
                    <br/>
                    <b>Gratulacje dla wszystkich uczestników!</b>

                </p>
                
                <p>
                    Zapraszam do wzięcia udziału w <a id="surveyLink" href="http://webankieta.pl/ankiety/w1hps2gjeh8c">ankiecie</a> .
                </p>
                <p>
                    <i>06 styczeń 2009, 22:34</i><br/>
                    Dnia 06 stycznia (o 21.00) omyłkowo odbył się pojedynek; dla uniknięcia niejednoznaczności ten pojedynek i wprowadzone przez 
                    niego zmiany punktowe zostają usunięte, stan punktowy zostaje przywrócony do obowiązującego w dniu 05 stycznia. Za zamieszanie przepraszam!
                </p>
                
            </div>

            <h2> Logowanie / rejestracja: </h2>
            <div class='tiny-form'>
                <form name="processLogin" action="Login" method="POST">
                    <h3>Zaloguj się:</h3>
                    Login: <br/>
                    <input type="text" name="login" value="" /> <BR/>
                    Hasło: <br/>
                    <input type="password" name="password" value="" /> <BR/>
                    <br/>
                    <input type="submit" value="Zaloguj" name="submit" />
                </form>
            </div>
            
            <div class='tiny-form'>
                <form name="processLogin" action="Guest" method="POST">
                    <h3> Zaloguj jako gość: </h3>
                    Logując się jako 'gość' możesz zobaczyć jak wygląda gra bez zakładania nowego konta. 
                    Nie możesz zdobywać punktów (zapisywać 
                    się do ligi), ale w każdej chwili możesz się wylogować i zarejestrować
                    właściwe konto. <br/>
                    <br/>
                    <input type="submit" value="Zaloguj" name="submit" />
                </form>
            </div>
            
            <div class='tiny-form'>
                <form name="register" action="Register" method="POST">
                    <h3>Zarejestruj się:</h3>
                    Login: <br/>
                    <input type="text" name="login" value="" /> <BR/>
                    Hasło: <br/>
                    <input type="password" name="password" value="" /> <BR/>
                    Powtórz hasło: <br/>
                    <input type="password" name="repeatedPassword" value="" /> <BR/>
                    Nr albumu: <br/>
                    <input type="text" name="studentID" value="" /> <BR/>
                    <br/>
                    <input type="submit" value="Zarejestruj" name="submit" />
                </form>
            </div>
        </div>
    </body>
</html>
