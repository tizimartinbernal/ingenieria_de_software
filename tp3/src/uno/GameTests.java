package uno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTests {
    private Card R1;
    private Card R2;
    private Card R3;
    private Card B1;
    private Card B2;
    private Card B3;
    private Card G1;
    private Card G2;
    private Card G3;
    private Card Y1;
    private Card Y2;
    private Card Y3;

    @BeforeEach public void setUp() {
        R1 = new NumberedCard("1", "red");
        R2 = new NumberedCard("2", "red");
        R3 = new NumberedCard("3", "red");
        B1 = new NumberedCard("1", "blue");
        B2 = new NumberedCard("2", "blue");
        B3 = new NumberedCard("3", "blue");
        G1 = new NumberedCard("1", "green");
        G2 = new NumberedCard("2", "green");
        G3 = new NumberedCard("3", "green");
        Y1 = new NumberedCard("1", "yellow");
        Y2 = new NumberedCard("2", "yellow");
        Y3 = new NumberedCard("3", "yellow");
    }


    @Test public void test00CreateOneCardGame() {
        List <Card> cards = List.of(R1);
        int numberToDeal = 0;
        Game game = new Game(cards, numberToDeal, "Juan", "Paco", "Pedro");
        assertEquals(new NumberedCard("1", "red"), game.getPileCard());
    }

    @Test public void test01CreateGameAndDeal2CardsEach(){
        List <Card> cards = List.of(Y1, G3, G2, G1, B3, B2, B1, R3, R2, R1);
        int numberToDeal = 2;
        Game game = new Game(cards, numberToDeal, "Juan", "Paco", "Pedro");
        assertEquals(new NumberedCard("1", "yellow"), game.getPileCard());
    }
}

/*
Tests:
Se mandan también!

* Alguna duda de funcionalidad? Ver el juego de minijuegos que nos pasó Emilio!
* Una cosa es cómo es el juego en realidad (108 cartas, 7 a c/u, etc) y otra cosa son las implementaciones.
- Lo que quiero decir con esto que tenemos que poder abstraernos de ese número, y que repartamos X cartas, juguemos con un mazo de Y cartas, etc.
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

La idea de comparar cartas definiendo un .equals en cartas está bien!!!


Desarrollar la lógica de una partida del juego del uno.

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
