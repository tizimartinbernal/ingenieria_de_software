package uno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {
    private Card redZero, redOne, redTwo, redThree, redFour, redFive, redSix, redSeven, redEight, redNine;
    private Card blueZero, blueOne, blueTwo, blueThree, blueFour, blueFive, blueSix, blueSeven, blueEight, blueNine;
    private Card greenZero, greenOne, greenTwo, greenThree, greenFour, greenFive, greenSix, greenSeven, greenEight, greenNine;
    private Card yellowZero, yellowOne, yellowTwo, yellowThree, yellowFour, yellowFive, yellowSix, yellowSeven, yellowEight, yellowNine;
    private Card redSkip, redReverse, redDrawTwo;
    private Card blueSkip, blueReverse, blueDrawTwo;
    private Card greenSkip, greenReverse, greenDrawTwo;
    private Card yellowSkip, yellowReverse, yellowDrawTwo;
    private Card wildCard;

    @BeforeEach public void setUp() {
        redZero = new NumberedCard("0", "red");
        redOne = new NumberedCard("1", "red");
        redTwo = new NumberedCard("2", "red");
        redThree = new NumberedCard("3", "red");
        redFour = new NumberedCard("4", "red");
        redFive = new NumberedCard("5", "red");
        redSix = new NumberedCard("6", "red");
        redSeven = new NumberedCard("7", "red");
        redEight = new NumberedCard("8", "red");
        redNine = new NumberedCard("9", "red");
        blueZero = new NumberedCard("0", "blue");
        blueOne = new NumberedCard("1", "blue");
        blueTwo = new NumberedCard("2", "blue");
        blueThree = new NumberedCard("3", "blue");
        blueFour = new NumberedCard("4", "blue");
        blueFive = new NumberedCard("5", "blue");
        blueSix = new NumberedCard("6", "blue");
        blueSeven = new NumberedCard("7", "blue");
        blueEight = new NumberedCard("8", "blue");
        blueNine = new NumberedCard("9", "blue");
        greenZero = new NumberedCard("0", "green");
        greenOne = new NumberedCard("1", "green");
        greenTwo = new NumberedCard("2", "green");
        greenThree = new NumberedCard("3", "green");
        greenFour = new NumberedCard("4", "green");
        greenFive = new NumberedCard("5", "green");
        greenSix = new NumberedCard("6", "green");
        greenSeven = new NumberedCard("7", "green");
        greenEight = new NumberedCard("8", "green");
        greenNine = new NumberedCard("9", "green");
        yellowZero = new NumberedCard("0", "yellow");
        yellowOne = new NumberedCard("1", "yellow");
        yellowTwo = new NumberedCard("2", "yellow");
        yellowThree = new NumberedCard("3", "yellow");
        yellowFour = new NumberedCard("4", "yellow");
        yellowFive = new NumberedCard("5", "yellow");
        yellowSix = new NumberedCard("6", "yellow");
        yellowSeven = new NumberedCard("7", "yellow");
        yellowEight = new NumberedCard("8", "yellow");
        yellowNine = new NumberedCard("9", "yellow");
        redSkip = new SkipCard("red");
        redReverse = new ReverseCard("red");
        redDrawTwo = new DrawTwoCard("red");
        blueSkip = new SkipCard("blue");
        blueReverse = new ReverseCard("blue");
        blueDrawTwo = new DrawTwoCard("blue");
        greenSkip = new SkipCard("green");
        greenReverse = new ReverseCard("green");
        greenDrawTwo = new DrawTwoCard("green");
        yellowSkip = new SkipCard("yellow");
        yellowReverse = new ReverseCard("yellow");
        yellowDrawTwo = new DrawTwoCard("yellow");
        wildCard = new WildCard();
    }


    @Test public void test00CreateOneCardGame() {
        List <Card> cards = List.of(redOne);
        int numberToDeal = 0;
        assertEquals(new NumberedCard("1", "red"), new Game(cards, numberToDeal, "Juan", "Paco", "Pedro")
                                                                         .getPileCard());
    }

    @Test public void test01CreateGameAndDeal2CardsEach(){
        List <Card> cards = List.of(yellowOne, greenThree, greenTwo, greenOne, blueThree, blueTwo, blueOne, redThree, redTwo, redOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "yellow"), new Game(cards, numberToDeal, "Juan", "Paco", "Pedro")
                                                                            .getPileCard());
    }

    @Test public void test02PlayACardComparableByColor() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenThree, greenTwo, blueThree, blueTwo, blueOne, redThree, redTwo, redOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("2", "yellow"), new Game(cards, numberToDeal, "Juan", "Paco") // Si le paso un tercer jugador se rompe
                                                                            .playCard(yellowTwo, "Juan")
                                                                            .getPileCard());
    }

    @Test public void test03PlayACardComparableByNumber() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenOne, greenTwo, blueThree, blueTwo, blueOne, redThree, redTwo, redOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "green"), new Game(cards, numberToDeal, "Juan", "Paco")
                                                                            .playCard(greenOne, "Juan")
                                                                            .getPileCard());
    }

    @Test public void test04PlayACardComparableBySymbol() { //  LA PRIMER CARTA DEL POZO DEBE EJECUTAR SU ACCIÓN (o capaz no, hay que consultar)
        List<Card> cards = List.of(redDrawTwo, blueDrawTwo, greenDrawTwo, yellowDrawTwo, greenOne, greenTwo, blueThree, blueTwo, blueOne, redThree, redTwo, redOne);
        int numberToDeal = 2;
        assertEquals(new DrawTwoCard("blue"), new Game(cards, numberToDeal, "Juan", "Paco")
                .playCard(blueDrawTwo, "Juan")
                .getPileCard());
    }

    @Test public void test05PlayACardComparableByColorAndNumber() {
        List<Card> cards = List.of(yellowOne, yellowOne, greenOne, greenTwo, blueThree, blueTwo, blueOne, redThree, redTwo, redOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "yellow"), new Game(cards, numberToDeal, "Juan", "Paco")
                                                                            .playCard(yellowOne, "Juan")
                                                                            .getPileCard());
    }


}


/*
Tests:
Se mandan también!

* Alguna duda de funcionalidad? Ver el juego de minijuegos que nos pasó Emilio!
* Otro method que tendríamos que tener es el de poder repartirle cartas a un jugador .repartir(Juan, [R1, B2, G3])
* Pienso que tal vez podríamos tener otro méthod interno que permita repartir cartas a todos los jugadores dado un mazo, capaz no, ni idea.
* Por la funcionalidad del juego, preguntar por la carta que está en el pozo se queda corto, tendríamos que poder ir más granular y poder preguntar
- por ejemplo, J.colorPozo(), J.numeroPozo()
* Focalizarnos en hacer pasos concretos que nos permita construir funcionalidad, no detenernos en detalles respecto del juego real
- Los errores que puedo tirar si me dan una carta incorrecta no ayudan a la construcción del problema

Dos alternativas para modelar lo de cartar uno y jugar la anteultima.
Una creo que dice jugarCantando (Juan, R2)
Otra es jugar (Juan, R2.uno() ).

Hacer distintos constructores. Algunos más laxos.
Tipos de constructores:
* Uno de esos constructores puede tener la cantidad de cartas que se le reparten a cada jugador: Game(cards, players, 7) por ejemplo
* Otro puede tener simplemente la cantidad de jugadores y el mazo de cartas: Game(cards, players) y reparte 7 a cada uno
* Otro puede tener solo los jugadores: Game(players) y reparte 7 a cada uno con el mazo completo de 108 cartas

Las cartas a considerar en el juego:
- Todas las cartas numeradas.
- Carta de robar 2.
- Carta de reversa.
- Carta de saltar.
- Carta comodín.

La implementación y sus tests deben ser determinísticos.
La partida debe poder iniciar con *2 o más jugadores hasta que alguno se descarte* de todas su cartas siguiendo las reglas del juego.
*Una vez terminado no se puede seguir jugando*

Es requisito cumplir con todos los criterios vistos a lo largo de la cursada.

DUDAS:

 */
