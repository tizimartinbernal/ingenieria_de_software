package anillo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

// Revisión de casos no contemplados en RingTest.java
public class AdditionalRingTest {

    @Test void testAdd00RemoveinEmptyRing(){
        assertThrows(Exception.class, () -> new Ring().remove());
    }

    @Test void test01RemoveUntilEmpty() {
        assertThrows(Exception.class, () -> new Ring().add("Mateo")
                                                .add("Manuela")
                                                .add("Tiziano")
                                                .remove()
                                                .remove()
                                                .remove()
                                                .current());
    }

    @Test void testAdd02FromARingWithElementToAnEmptyRing(){
        assertEquals("José", new Ring().add("Hola")
                                                 .add(new Integer(42))
                                                 .add(new Float(22.5))
                                                 .add(LocalDate.now())
                                                 .remove()
                                                 .remove()
                                                 .remove()
                                                 .remove()
                                                 .add("José")
                                                 .next()
                                                 .current());
    }

    @Test void testAdd03CompleteLapOfTheRing(){
        assertEquals("Mateo", new Ring().add(LocalDate.now())
                                                 .add(new Integer(42))
                                                 .add(new Float(22.5))
                                                 .add("Mateo")
                                                 .next()
                                                 .next()
                                                 .next()
                                                 .next()
                                                 .current());
    }

    @Test void testAdd04InsertInMiddleKeepsRingIntact() {
        assertEquals("C", new Ring().add("A")
                                             .add("B")
                                             .add("C")
                                             .next()
                                             .add("X")
                                             .next()
                                             .next()
                                             .next()
                                             .current());
    }
}
