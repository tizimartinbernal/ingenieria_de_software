package uno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GameTests {
    private NumberedCard redZero, redOne, redTwo, redThree, redFour, redFive, redSix, redSeven, redEight, redNine;
    private NumberedCard blueZero, blueOne, blueTwo, blueThree, blueFour, blueFive, blueSix, blueSeven, blueEight, blueNine;
    private NumberedCard greenZero, greenOne, greenTwo, greenThree, greenFour, greenFive, greenSix, greenSeven, greenEight, greenNine;
    private NumberedCard yellowZero, yellowOne, yellowTwo, yellowThree, yellowFour, yellowFive, yellowSix, yellowSeven, yellowEight, yellowNine;
    private SkipCard redSkip, blueSkip, greenSkip, yellowSkip;
    private ReverseCard redReverse, blueReverse, greenReverse, yellowReverse;
    private DrawTwoCard redDrawTwo, blueDrawTwo, greenDrawTwo, yellowDrawTwo;
    private WildCard wildCard;

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
        assertEquals(redOne, new Game(cards, 0, "Mateo", "Tiziano", "Matias")
                                     .getPileCard());
    }

    @Test public void test01CreateGameAndDealTwoCardsEach(){
        List <Card> cards = List.of(yellowOne, greenThree, greenTwo, greenOne, blueThree, blueTwo, blueOne, redThree, redTwo, redSeven);
        assertEquals(yellowOne, new Game(cards, 2, "Mateo", "Tiziano", "Matias")
                                        .getPileCard());
    }

    @Test public void test02StackANumberedCardOnANumberedCardByColor() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenThree, greenTwo, blueThree, blueTwo, blueOne, yellowThree, yellowFour);
        assertEquals(yellowTwo, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(yellowTwo, "Mateo")
                                        .getPileCard());
    }

    @Test public void test03StackANumberedCardOnANumberedCardByNumber() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenOne, greenTwo, blueSeven, blueSix, blueFive, yellowFive, yellowSix);
        assertEquals(greenOne, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(greenOne, "Mateo")
                                        .getPileCard());
    }

    @Test public void test04StackANumberedCardOnANumberedCardByColorAndNumber() {
        List<Card> cards = List.of(yellowThree, greenTwo, yellowThree, greenSeven, greenEight, blueTwo, blueOne, yellowSeven, yellowEight);
        assertEquals(yellowThree, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(yellowThree, "Mateo")
                                        .getPileCard());
    }

    @Test public void test05StackADrawTwoCardOnANumberedCardByColor() {
        List<Card> cards = List.of(blueEight, yellowOne, blueDrawTwo, greenTwo, blueThree, blueTwo, blueOne, blueZero, yellowZero, yellowOne);
        assertEquals(blueDrawTwo, new Game(cards, 3, "Mateo", "Tiziano")
                                          .playCard(blueDrawTwo, "Mateo")
                                          .getPileCard());
    }

    @Test public void test06StackASkipCardOnANumberedCardByColor() {
        List<Card> cards = List.of(greenZero, yellowOne, greenSkip, greenTwo, blueThree, blueTwo, blueOne, blueNine, yellowNine);
        assertEquals(greenSkip, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(greenSkip, "Mateo")
                                        .getPileCard());
    }

    @Test public void test07StackAReverseCardOnANumberedCardByColor() {
        List<Card> cards = List.of(redZero, yellowOne, redReverse, greenTwo, blueThree, blueTwo, blueOne, blueNine, yellowNine);
        assertEquals(redReverse, new Game(cards, 3, "Mateo", "Tiziano")
                                         .playCard(redReverse, "Mateo")
                                         .getPileCard());
    }

    @Test public void test08StackAWildCardOnANumberedCard() {
        List<Card> cards = List.of(yellowOne, yellowOne, wildCard, greenTwo, blueThree, blueTwo, blueOne, blueNine, yellowNine);
        assertEquals(wildCard, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(wildCard.assignColor("blue"), "Mateo")
                                        .getPileCard());
    }

    @Test public void test09StackANumberedCardOnADrawTwoCardByColor() {
        List<Card> cards = List.of(yellowDrawTwo, yellowOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueNine, yellowNine);
        assertEquals(yellowOne, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(yellowOne, "Mateo")
                                        .getPileCard());
    }

    @Test public void test10StackADrawTwoOnADrawTwoCardBySymbol() {
        List<Card> cards = List.of(blueDrawTwo, greenDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueSkip, greenReverse);
        assertEquals(greenDrawTwo, new Game(cards, 3, "Mateo", "Tiziano")
                                            .playCard(greenDrawTwo, "Mateo")
                                            .getPileCard());
    }

    @Test public void test11StackADrawTwoOnADrawTwoCardByColorAndSymbol() {
        List<Card> cards = List.of(redDrawTwo, redDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueSkip, greenReverse);
        assertEquals(redDrawTwo, new Game(cards, 3, "Mateo", "Tiziano")
                                         .playCard(redDrawTwo, "Mateo")
                                         .getPileCard());
    }

    @Test public void test12StackASkipOnADrawTwoCardByColor() {
        List<Card> cards = List.of(greenDrawTwo, greenSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueSkip, greenReverse);
        assertEquals(greenSkip, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(greenSkip, "Mateo")
                                        .getPileCard());
    }

    @Test public void test13StackAReverseOnADrawTwoCardByColor() {
        List<Card> cards = List.of(yellowDrawTwo, yellowReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueSkip, greenReverse);
        assertEquals(yellowReverse, new Game(cards, 3, "Mateo", "Tiziano")
                                            .playCard(yellowReverse, "Mateo")
                                            .getPileCard());
    }

    @Test public void test14StackAWildCardOnADrawTwoCardByColor() {
        List<Card> cards = List.of(blueDrawTwo, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueSkip, greenReverse);
        assertEquals(wildCard, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(wildCard.assignColor("green"), "Mateo")
                                        .getPileCard());
    }

    @Test public void test15StackANumberedCardOnASkipCardByColor() {
        List<Card> cards = List.of(blueSkip, blueOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, greenReverse);
        assertEquals(blueOne, new Game(cards, 3, "Mateo", "Tiziano")
                                       .playCard(blueOne, "Mateo")
                                       .getPileCard());
    }

    @Test public void test16StackADrawTwoOnASkipCardByColor() {
        List<Card> cards = List.of(greenSkip, greenDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueZero, yellowZero, yellowOne);
        assertEquals(greenDrawTwo, new Game(cards, 3, "Mateo", "Tiziano")
                                            .playCard(greenDrawTwo, "Mateo")
                                            .getPileCard());
    }

    @Test public void test17StackASkipOnASkipCardBySymbol() {
        List<Card> cards = List.of(redSkip, greenSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueSkip, greenReverse);
        assertEquals(greenSkip, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(greenSkip, "Mateo")
                                        .getPileCard());
    }

    @Test public void test18StackASkipOnASkipCardByColorAndSymbol() {
        List<Card> cards = List.of(blueSkip, blueSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, greenReverse);
        assertEquals(blueSkip, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(blueSkip, "Mateo")
                                        .getPileCard());
    }

    @Test public void test19StackAReverseOnASkipCardByColor() {
        List<Card> cards = List.of(yellowSkip, yellowReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, greenReverse);
        assertEquals(yellowReverse, new Game(cards, 3, "Mateo", "Tiziano")
                                            .playCard(yellowReverse, "Mateo")
                                            .getPileCard());
    }

    @Test public void test20StackAWildCardOnASkipCardByColor() {
        List<Card> cards = List.of(blueSkip, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, greenReverse);
        assertEquals(wildCard, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(wildCard.assignColor("blue"), "Mateo")
                                        .getPileCard());
    }

    @Test public void test21StackANumberedCardOnAReverseCardByColor() {
        List<Card> cards = List.of(greenReverse, greenOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, greenReverse);
        assertEquals(greenOne, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(greenOne, "Mateo")
                                        .getPileCard());
    }

    @Test public void test22StackADrawTwoOnAReverseCardByColor() {
        List<Card> cards = List.of(redReverse, redDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueZero, yellowZero, yellowOne);
        assertEquals(redDrawTwo, new Game(cards, 3, "Mateo", "Tiziano")
                                         .playCard(redDrawTwo, "Mateo")
                                         .getPileCard());
    }

    @Test public void test23StackASkipOnAReverseCardByColor() {
        List<Card> cards = List.of(yellowReverse, redZero, yellowTwo, yellowSkip, blueThree, blueTwo, blueOne, yellowZero, yellowOne);
        assertEquals(yellowSkip, new Game(cards, 3, "Mateo", "Tiziano")
                                          .playCard(yellowSkip, "Mateo")
                                          .getPileCard());
    }

    @Test public void test24StackAReverseOnAReverseCardBySymbol() {
        List<Card> cards = List.of(greenReverse, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, greenReverse);
        assertEquals(blueReverse, new Game(cards, 3, "Mateo", "Tiziano")
                                           .playCard(blueReverse, "Mateo")
                                           .getPileCard());
    }

    @Test public void test25StackAReverseOnAReverseCardByColorAndSymbol() {
        List<Card> cards = List.of(redReverse, redReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, greenReverse);
        assertEquals(redReverse, new Game(cards, 3, "Mateo", "Tiziano")
                                          .playCard(redReverse, "Mateo")
                                          .getPileCard());
    }

    @Test public void test26StackAWildCardOnAReverseCard() {
        List<Card> cards = List.of(yellowReverse, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, greenReverse);
        assertEquals(wildCard, new Game(cards, 3, "Mateo", "Tiziano")
                                        .playCard(wildCard.assignColor("yellow"), "Mateo")
                                        .getPileCard());
    }

    @Test public void test27StackANumberedCardOnAWildCardByColor() {
        List<Card> cards = List.of(greenZero, greenOne, yellowTwo, greenTwo, yellowNine, wildCard, yellowOne, yellowZero, blueSkip);
        assertEquals(yellowTwo, new Game(cards, 4, "Mateo", "Tiziano")
                                        .playCard(greenOne, "Mateo")
                                        .playCard(wildCard.assignColor("yellow"), "Tiziano")
                                        .playCard(yellowTwo, "Mateo")
                                        .getPileCard());
    }

    @Test public void test28StackADrawTwoOnAWildCardByColor() {
        List<Card> cards = List.of(yellowNine, greenDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, wildCard, blueZero, yellowZero, yellowOne, redDrawTwo);
        assertEquals(greenDrawTwo, new Game(cards, 4, "Mateo", "Tiziano")
                                            .playCard(yellowTwo, "Mateo")
                                            .playCard(wildCard.assignColor("green"), "Tiziano")
                                            .playCard(greenDrawTwo, "Mateo")
                                            .getPileCard());
    }

    @Test public void test29StackASkipOnAWildCard() {
        List<Card> cards = List.of(redNine, blueSkip, yellowTwo, redSix, blueThree, blueTwo, blueOne, wildCard, blueZero, yellowZero, yellowOne);
        assertEquals(blueSkip, new Game(cards, 4, "Mateo", "Tiziano")
                                        .playCard(redSix, "Mateo")
                                        .playCard(wildCard.assignColor("blue"), "Tiziano")
                                        .playCard(blueSkip, "Mateo")
                                        .getPileCard());
    }

    @Test public void test30StackAReverseOnAWildCard() {
        List<Card> cards = List.of(blueZero, redReverse, yellowTwo, greenTwo, blueThree, wildCard, blueOne, redZero, redOne, greenSkip, yellowSkip);
        assertEquals(redReverse, new Game(cards, 4, "Mateo", "Tiziano")
                                          .playCard(blueThree, "Mateo")
                                          .playCard(wildCard.assignColor("red"), "Tiziano")
                                          .playCard(redReverse, "Mateo")
                                          .getPileCard());
    }

    @Test public void test31StackAWildCardOnAWildCard() {
        List<Card> cards = List.of(blueOne, wildCard, yellowTwo, greenTwo, blueThree, wildCard, blueOne, redZero, redOne, greenSkip, yellowSkip);
        assertEquals(wildCard, new Game(cards, 4, "Mateo", "Tiziano")
                                                                            .playCard(wildCard.assignColor("yellow"), "Mateo")
                                                                            .playCard(wildCard.assignColor("blue"), "Tiziano")
                                                                            .getPileCard());
    }

    @Test public void test33StackANumberedCardOnANumberedCardWithDifferentColorAndNumberShouldFail() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenTwo, greenOne, blueThree, blueTwo, blueOne, redThree, redTwo);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(greenTwo, "Mateo"));
    }

    @Test public void test34StackANumberedCardOnADrawTwoCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(blueDrawTwo, yellowOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redDrawTwo, redSkip, greenReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(yellowOne, "Mateo"));
    }

    @Test public void test35StackANumberedCardOnASkipCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(blueSkip, yellowOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(yellowOne, "Mateo"));
    }

    @Test public void test36StackANumberedCardOnAReverseCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(blueReverse, yellowOne, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redReverse, redSkip);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(yellowOne, "Mateo"));
    }

    @Test public void test36StackANumberedCardOnAWildCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(blueSix, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, blueZero, redSkip);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(wildCard.assignColor("red"), "Mateo")
                                                .playCard(blueTwo, "Tiziano"));
    }

    @Test public void test37StackADrawTwoOnANumberedCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowOne, blueDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redDrawTwo, redSkip);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueDrawTwo, "Mateo"));
    }

    @Test public void test38StackADrawTwoOnASkipCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redSkip, blueDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redDrawTwo, redSkip);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueDrawTwo, "Mateo"));
    }

    @Test public void test39StackADrawTwoOnAReverseCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redReverse, blueDrawTwo, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redDrawTwo, redSkip);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueDrawTwo, "Mateo"));
    }

    @Test public void test39StackADrawTwoOnAWildCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redZero, wildCard, yellowTwo, greenTwo, blueThree, blueTwo, blueDrawTwo, redDrawTwo, redSkip);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(wildCard.assignColor("yellow"), "Mateo")
                                                .playCard(blueDrawTwo, "Tiziano"));
    }

    @Test public void test40StackASkipOnANumberedCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowOne, blueSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueSkip, "Mateo"));
    }

    @Test public void test41StackASkipOnADrawTwoCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redDrawTwo, blueSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueSkip, "Mateo"));
    }

    @Test public void test42StackASkipOnAReverseCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowReverse, blueSkip, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueSkip, "Mateo"));
    }

    @Test public void test42StackASkipOnAWildCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowReverse, wildCard, yellowTwo, greenTwo, blueSkip, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                .playCard(wildCard.assignColor("green"), "Mateo")
                .playCard(blueSkip, "Tiziano"));
    }

    @Test public void test43StackAReverseOnANumberedCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(yellowOne, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueReverse, "Mateo"));
    }

    @Test public void test44StackAReverseOnADrawTwoCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redDrawTwo, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueReverse, "Mateo"));
    }

    @Test public void test45StackAReverseOnASkipCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redSkip, blueReverse, yellowTwo, greenTwo, blueThree, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                .playCard(blueReverse, "Mateo"));
    }

    @Test public void test45StackAReverseOnAWildCardWithDifferentColorShouldFail() {
        List<Card> cards = List.of(redZero, wildCard, yellowTwo, greenTwo, blueReverse, blueTwo, blueOne, redSkip, redReverse);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                .playCard(wildCard.assignColor("red"), "Mateo")
                .playCard(blueReverse, "Tiziano"));
    }

    @Test public void testXXEqualsWorksOnNumberedCard() {
        List<Card> cards = List.of(yellowOne, yellowTwo, greenThree, greenTwo, blueThree, blueTwo, blueOne, yellowThree, yellowFour);
        assertEquals(new NumberedCard("2", "yellow"), new Game(cards, 3, "Mateo", "Tiziano")
                                                                            .playCard(new NumberedCard("2", "yellow"), "Mateo")
                                                                            .getPileCard());
    }

    @Test public void testXXEqualsWorksOnDrawTwoCard() {
        List<Card> cards = List.of(blueOne, blueDrawTwo, greenThree, greenTwo, blueThree, blueTwo, blueOne, yellowThree, yellowFour);
        assertEquals(new DrawTwoCard("blue"), new Game(cards, 3, "Mateo", "Tiziano")
                                                            .playCard(new DrawTwoCard("blue"), "Mateo")
                                                            .getPileCard());
    }

    @Test public void testXXEqualsWorksOnSkipCard() {
        List<Card> cards = List.of(blueOne, blueSkip, greenThree, greenTwo, blueThree, blueTwo, blueOne, yellowThree, yellowFour);
        assertEquals(new SkipCard("blue"), new Game(cards, 3, "Mateo", "Tiziano")
                                                                            .playCard(new SkipCard("blue"), "Mateo")
                                                                            .getPileCard());
    }

    @Test public void testXXEqualsWorksOnReverseCard() {
        List<Card> cards = List.of(blueOne, blueReverse, greenThree, greenTwo, blueThree, blueTwo, blueOne, yellowThree, yellowFour);
        assertEquals(new ReverseCard("blue"), new Game(cards, 3, "Mateo", "Tiziano")
                                                            .playCard(new ReverseCard("blue"), "Mateo")
                                                            .getPileCard());
    }

    @Test public void testXXEqualsWorksOnWildCard() {
        List<Card> cards = List.of(blueOne, wildCard, greenThree, greenTwo, blueThree, blueTwo, blueOne, yellowThree, yellowFour);
        assertEquals(new WildCard(), new Game(cards, 3, "Mateo", "Tiziano")
                                                            .playCard(new WildCard().assignColor("blue"), "Mateo")
                                                            .getPileCard());
    }

    @Test public void test46AllPlayersPlayOneCardAndCompleteTheRound() {
        List<Card> cards = List.of(redOne, redTwo, blueZero, redFour, redFive, redSix, yellowTwo, yellowReverse, blueZero, yellowZero, yellowOne, redSkip, redDrawTwo, blueDrawTwo, blueSkip);
        assertEquals(blueZero, new Game(cards, 4, "Mateo", "Tiziano", "Matias")
                                        .playCard(redTwo, "Mateo")
                                        .playCard(yellowTwo, "Tiziano")
                                        .playCard(yellowZero, "Matias")
                                        .playCard(blueZero, "Mateo")
                                        .getPileCard());
    }

    @Test public void test47APlayerTrysToPlayOutOfTurn() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redFour, redFive, redSix, greenFour, redEight, redNine);
        assertThrows(Error.class, () -> new Game(cards, 2, "Mateo", "Tiziano", "Matias")
                                                .playCard(redTwo, "Mateo")
                                                .playCard(redSix, "Matias"));
    }

    @Test public void test48APlayerThatNotInTheGameTrysToPlayACard() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redFour, redFive, redSix, greenFour, redEight, redNine);
        assertThrows(Error.class, () -> new Game(cards, 2, "Mateo", "Tiziano", "Matias")
                                                .playCard(redTwo, "Julio"));
    }

    @Test public void test48APlayerTrysToPlayACardNotInHand() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redFour, redFive, redSix, greenFour, redEight, redNine);
        assertThrows(Error.class, () -> new Game(cards, 2, "Mateo", "Tiziano", "Matias")
                                                .playCard(redNine, "Mateo"));
    }

    @Test public void test49ShowDrawTwoCardAction() {
        List<Card> cards = List.of(redOne, redDrawTwo, redThree, redFour, redFive, greenFive, redSix, redEight, redSkip, redNine, blueSkip, blueDrawTwo);
        assertEquals(redNine, new Game(cards, 4, "Mateo", "Tiziano")
                                    .playCard(redDrawTwo, "Mateo")
                                    .playCard(redThree, "Mateo")
                                    .playCard(redNine, "Tiziano")
                                    .getPileCard());
    }

    @Test public void test50ShowSkipCardAction() {
        List<Card> cards = List.of(redOne, redSkip, redThree, redFour, redFive, greenFive, redSix, redEight, redNine, redDrawTwo, blueSkip, blueDrawTwo);
        assertEquals(redNine, new Game(cards, 3, "Mateo", "Tiziano", "Matias")
                                                                        .playCard(redSkip, "Mateo")
                                                                        .playCard(redNine, "Matias")
                                                                        .getPileCard());
    }

    @Test public void test51ShowReverseCardAction() {
        List<Card> cards = List.of(redOne, redReverse, redThree, redFour, redFive, greenFive, redSix, redSkip, redNine, redDrawTwo, redEight, blueSkip, blueDrawTwo, blueReverse);
        assertEquals(redSix, new Game(cards, 3, "Mateo", "Tiziano", "Matias", "Julio")
                                                                            .playCard(redReverse, "Mateo")
                                                                            .playCard(redEight, "Julio")
                                                                            .playCard(redNine, "Matias")
                                                                            .playCard(redSix, "Tiziano")
                                                                            .getPileCard());
    }

    @Test public void test53PickUpACardThatCanBePlayed() {
        List<Card> cards = List.of(redOne, redTwo, redThree, blueFour, greenFive, redSix, greenSix, redEight, redNine);
        assertEquals(redEight, new Game(cards, 3, "Mateo", "Tiziano")
                                    .playCard(redTwo, "Mateo")
                                    .pickCard("Tiziano")
                                    .getPileCard());
    }

    @Test public void test54PickUpACardThatCannotBePlayed() {
        List<Card> cards = List.of(redOne, redTwo, redThree, blueFour, greenFive, greenSix, redEight, blueSkip, blueDrawTwo);
        assertEquals(redTwo, new Game(cards, 3, "Mateo", "Tiziano")
                                    .playCard(redTwo, "Mateo")
                                    .pickCard("Tiziano")
                                    .getPileCard());
    }

    @Test public void test55PickUpACardWhenIsNotYourTurn() {
        List<Card> cards = List.of(redOne, redTwo, redThree, blueFour, greenFive, greenSix, redEight, redNine);
        assertThrows(Error.class, () -> new Game(cards, 2, "Mateo", "Tiziano")
                                                                            .playCard(redTwo, "Mateo")
                                                                            .pickCard("Mateo"));
    }

    @Test public void test56MakeAValidUnoCall() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redSkip, greenFive, greenSix, redEight, redNine, blueSkip);
        assertEquals(new SkipCard("red"), new Game(cards, 3, "Mateo", "Tiziano")
                                                                            .playCard(redTwo, "Mateo")
                                                                            .playCard(redEight, "Tiziano")
                                                                            .playCard(redThree.uno(), "Mateo")
                                                                            .pickCard("Tiziano")
                                                                            .playCard(redSkip, "Mateo")
                                                                            .getPileCard());

    }

    @Test public void test57MakeAValidUnoCallWithAWildCard() {
        List<Card> cards = List.of(redOne, redTwo, greenZero, wildCard, greenFive, greenSix, redEight, redNine, blueSkip);
        assertEquals(greenZero, new Game(cards, 3, "Mateo", "Tiziano")
                                                                            .playCard(redTwo, "Mateo")
                                                                            .playCard(redEight, "Tiziano")
                                                                            .playCard(wildCard.assignColor("green").uno(), "Mateo")
                                                                            .pickCard("Tiziano")
                                                                            .playCard(greenZero, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test58MakeAnInvalidUnoCall() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redSkip, greenFive, greenSix, redEight, redNine, redZero, yellowDrawTwo);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                                            .playCard(redTwo, "Mateo")
                                                                            .playCard(redEight.uno(), "Tiziano"));
    }

    @Test public void test59ForgetToCallUno() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redZero, greenFive, redEight, blueSkip, redNine);
        assertEquals(redEight, new Game(cards, 2, "Mateo", "Tiziano")
                                                                            .playCard(redTwo, "Mateo")
                                                                            .playCard(redZero.uno(), "Tiziano")
                                                                            .playCard(redEight, "Mateo")
                                                                            .getPileCard());
    }

    @Test public void test60CannotPlayACardWhenGameIsOver() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redSkip, greenFive, greenSix, redEight, redNine, blueSkip);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                    .playCard(redTwo, "Mateo")
                                                    .playCard(redEight, "Tiziano")
                                                    .playCard(redThree.uno(), "Mateo")
                                                    .pickCard("Tiziano")
                                                    .playCard(redSkip, "Mateo")
                                                    .playCard(redNine, "Tiziano"));
    }

    @Test public void test61CannotPickACardWhenGameIsOver() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redSkip, greenFive, greenSix, redEight, redNine, blueSkip);
        assertThrows(Error.class, () -> new Game(cards, 3, "Mateo", "Tiziano")
                                                    .playCard(redTwo, "Mateo")
                                                    .playCard(redEight, "Tiziano")
                                                    .playCard(redThree.uno(), "Mateo")
                                                    .pickCard("Tiziano")
                                                    .playCard(redSkip, "Mateo")
                                                    .pickCard("Tiziano"));
    }

    @Test public void test62CannotPlayACardThatWasAlreadyPlayed() {
        List<Card> cards = List.of(redOne, redTwo, redThree, redSkip, greenFive, greenSix, redEight, redNine, blueSkip, yellowDrawTwo, redDrawTwo);
        assertThrows(Error.class, () -> new Game(cards, 4, "Mateo", "Tiziano")
                                                    .playCard(redTwo, "Mateo")
                                                    .playCard(redEight, "Tiziano")
                                                    .playCard(redTwo, "Mateo"));

    }

    @Test public void test63CanPlayTheSameCardTwiceIfIsDuplicate() {
        List<Card> cards = List.of(redOne, redTwo, redTwo, redThree, greenFive, greenSix, redEight, redNine, blueSkip, yellowDrawTwo, redDrawTwo);
        assertEquals(new NumberedCard("2", "red"), new Game(cards, 4, "Mateo", "Tiziano")
                                                    .playCard(redTwo, "Mateo")
                                                    .playCard(redEight, "Tiziano")
                                                    .playCard(redTwo, "Mateo")
                                                    .getPileCard());
    }

    @Test public void test64WildCardPlayedDifferentTimesInTheSameGame() {
        List<Card> cards = List.of(redOne, redTwo, greenZero, wildCard, blueFive, blueSeven, greenSix, redEight, redNine, wildCard, yellowDrawTwo, redDrawTwo);
        int numberToDeal = 5;
        assertEquals(blueFive, new Game(cards, numberToDeal, "Mateo", "Tiziano")
                                        .playCard(redTwo, "Mateo")
                                        .playCard(redEight, "Tiziano")
                                        .playCard(wildCard.assignColor("green"), "Mateo")
                                        .playCard(greenSix, "Tiziano")
                                        .playCard(greenZero, "Mateo")
                                        .playCard(wildCard.assignColor("blue"), "Tiziano")
                                        .playCard(blueFive.uno(), "Mateo")
                                        .getPileCard());
    }
}