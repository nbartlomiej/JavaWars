
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
                    <li>Przyznawanie punktów: 
                        <ul  type="disc">
                            <li>pierwsze m-ce w pojedynku: 10 pkt</li>
                            <li>wartość zdobytych przez robota kamieni szlachetnych jest mnożona przez 0.01 i dodawana do puli punktów</li>
                        </ul> 
                    </li>
                </ol>
                <p>Kontakt, informacje o bledach: <i>javawars(at)gmail.com</i></p>
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
