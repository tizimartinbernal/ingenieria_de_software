package uno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

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
        assertEquals(new NumberedCard("1", "red"), new Game(cards, numberToDeal, "Mateo", "Tiziano", "Matias")
                                                                         .getPileCard());
    }

    @Test public void test01CreateGameAndDealTwoCardsEach(){
        List <Card> cards = List.of(yellowOne, greenThree, greenTwo, greenOne, blueThree, blueTwo, blueOne, redThree, redTwo, redOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano", "Matias")
                                                                            .getPileCard());
    }

    @Test public void test02StackANumberedCardOnANumberedCardByColor() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenThree, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("2", "yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano") // Si le paso un tercer jugador se rompe
                                                                            .playCard(yellowTwo, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test03StackANumberedCardOnANumberedCardByNumber() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenOne, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenOne, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test04StackANumberedCardOnANumberedCardByColorAndNumber() {
        List<Card> cards = List.of(yellowOne, greenTwo, yellowOne, greenOne, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowOne, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test05StackADrawTwoCardOnANumberedCardByColor() {
        List<Card> cards = List.of(yellowOne, yellowOne, yellowDrawTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new DrawTwoCard("yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowDrawTwo, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test06StackASkipCardOnANumberedCardByColor() {
        List<Card> cards = List.of(yellowOne, yellowOne, yellowSkip, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new SkipCard("yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowSkip, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test07StackAReverseCardOnANumberedCardByColor() {
        List<Card> cards = List.of(yellowOne, yellowOne, yellowReverse, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new ReverseCard("yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowReverse, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test08StackAWildCardOnANumberedCardByColor() {   // Tiene sentido tirar una WildCard sin asignar color? Denería tirar algun error?
        List<Card> cards = List.of(yellowOne, yellowOne, wildCard, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new WildCard(), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(wildCard, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test09StackANumberedCardOnADrawTwoCardByColor() {
        List<Card> cards = List.of(yellowDrawTwo, yellowOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowOne, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test10StackADrawTwoOnADrawTwoCardBySymbol() {
        List<Card> cards = List.of(yellowDrawTwo, greenDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new DrawTwoCard("green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenDrawTwo, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test11StackADrawTwoOnADrawTwoCardByColorAndSymbol() {
        List<Card> cards = List.of(yellowDrawTwo, yellowDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new DrawTwoCard("yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowDrawTwo, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test12StackASkipOnADrawTwoCardByColor() {
        List<Card> cards = List.of(yellowDrawTwo, yellowSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new SkipCard("yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowSkip, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test13StackAReverseOnADrawTwoCardByColor() {
        List<Card> cards = List.of(yellowDrawTwo, yellowReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new ReverseCard("yellow"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowReverse, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test14StackAWildCardOnADrawTwoCardByColor() {   // Tiene sentido tirar una WildCard sin asignar color? Denería tirar algun error?
        List<Card> cards = List.of(yellowDrawTwo, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new WildCard(), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(wildCard, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test15StackANumberedCardOnASkipCardByColor() {
        List<Card> cards = List.of(blueSkip, blueOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "blue"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueOne, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test16StackADrawTwoOnASkipCardByColor() {
        List<Card> cards = List.of(blueSkip, blueDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new DrawTwoCard("blue"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueDrawTwo, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test17StackASkipOnASkipCardBySymbol() {
        List<Card> cards = List.of(blueSkip, greenSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new SkipCard("green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenSkip, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test18StackASkipOnASkipCardByColorAndSymbol() {
        List<Card> cards = List.of(blueSkip, blueSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new SkipCard("blue"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueSkip, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test19StackAReverseOnASkipCardByColor() {
        List<Card> cards = List.of(blueSkip, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new ReverseCard("blue"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueReverse, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test20StackAWildCardOnASkipCardByColor() {   // Tiene sentido tirar una WildCard sin asignar color? Denería tirar algun error?
        List<Card> cards = List.of(blueSkip, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new WildCard(), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(wildCard, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test21StackANumberedCardOnAReverseCardByColor() {
        List<Card> cards = List.of(greenReverse, greenOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenOne, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test22StackADrawTwoOnAReverseCardByColor() {
        List<Card> cards = List.of(greenReverse, greenDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new DrawTwoCard("green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenDrawTwo, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test23StackASkipOnAReverseCardByColor() {
        List<Card> cards = List.of(greenReverse, greenSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new SkipCard("green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenSkip, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test24StackAReverseOnAReverseCardBySymbol() {
        List<Card> cards = List.of(greenReverse, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new ReverseCard("blue"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueReverse, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test25StackAReverseOnAReverseCardByColorAndSymbol() {
        List<Card> cards = List.of(greenReverse, greenReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new ReverseCard("green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenReverse, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test26StackAWildCardOnAReverseCardByColor() {   // Tiene sentido tirar una WildCard sin asignar color? Denería tirar algun error?
        List<Card> cards = List.of(greenReverse, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new WildCard(), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(wildCard, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test27StackANumberedCardOnAWildCard() {
        List<Card> cards = List.of(wildCard, greenOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenOne, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test28StackADrawTwoOnAWildCard() {
        List<Card> cards = List.of(wildCard, greenDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new DrawTwoCard("green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenDrawTwo, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test29StackASkipOnAWildCard() {
        List<Card> cards = List.of(wildCard, greenSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new SkipCard("green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenSkip, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test30StackAReverseOnAWildCard() {
        List<Card> cards = List.of(wildCard, greenReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new ReverseCard("green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenReverse, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test31StackAWildCardOnAWildCard() {   // Tiene sentido tirar una WildCard sin asignar color? Denería tirar algun error?
        List<Card> cards = List.of(wildCard, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new WildCard(), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(wildCard, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test32StackANumberedCardOnAWildCardWithColor() {
        List<Card> cards = List.of(wildCard, greenOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertEquals(new NumberedCard("1", "green"), new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenOne, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test32StackANumberedCardOnANumberedCardWithDifferentColorAndNumberShouldFail() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenTwo, greenOne, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(greenTwo, "Mateo"));
    }

    @Test public void test33StackANumberedCardOnADrawTwoCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(blueDrawTwo, yellowOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowOne, "Mateo"));
    }

    @Test public void test34StackANumberedCardOnASkipCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(blueSkip, yellowOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowOne, "Mateo"));
    }

    @Test public void test35StackANumberedCardOnAReverseCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(blueReverse, yellowOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(yellowOne, "Mateo"));
    }

    @Test public void test36StackADrawTwoOnANumberedCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowOne, blueDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueDrawTwo, "Mateo"));
    }

    @Test public void test37StackADrawTwoOnASkipCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redSkip, blueDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueDrawTwo, "Mateo"));
    }

    @Test public void test38StackADrawTwoOnAReverseCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redReverse, blueDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueDrawTwo, "Mateo"));
    }

    @Test public void test39StackASkipOnANumberedCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowOne, blueSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueSkip, "Mateo"));
    }

    @Test public void test40StackASkipOnADrawTwoCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redDrawTwo, blueSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueSkip, "Mateo"));
    }

    @Test public void test41StackASkipOnAReverseCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowReverse, blueSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueSkip, "Mateo"));
    }

    @Test public void test42StackAReverseOnANumberedCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowOne, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueReverse, "Mateo"));
    }

    @Test public void test43StackAReverseOnADrawTwoCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redDrawTwo, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueReverse, "Mateo"));
    }

    @Test public void test44StackAReverseOnASkipCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redSkip, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne);
        int numberToDeal = 2;
        assertThrows(IllegalStateException.class, () -> new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                                                            .playCard(blueReverse, "Mateo"));
    }

}


/*
Tests:
Se mandan también!

* Alguna duda de funcionalidad? Ver el juego de minijuegos que nos pasó Emilio!
* Otro method que tendríamos que tener es el de poder repartirle cartas a un jugador .repartir(Mateo, [R1, B2, G3])
* Pienso que tal vez podríamos tener otro méthod interno que permita repartir cartas a todos los jugadores dado un mazo, capaz no, ni idea.
* Por la funcionalidad del juego, preguntar por la carta que está en el pozo se queda corto, tendríamos que poder ir más granular y poder preguntar
- por ejemplo, J.colorPozo(), J.numeroPozo()
* Focalizarnos en hacer pasos concretos que nos permita construir funcionalidad, no detenernos en detalles respecto del juego real
- Los errores que puedo tirar si me dan una carta incorrecta no ayudan a la construcción del problema

Dos alternativas para modelar lo de cartar uno y jugar la anteultima.
Una creo que dice jugarCantando (Mateo, R2)
Otra es jugar (Mateo, R2.uno() ).

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
