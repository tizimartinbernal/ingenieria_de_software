package anillo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class AdditionalRingTest {

    @Test void testAdd01FromARingWithElementToAnEmptyRing(){
        assertEquals("Maria", new Ring().add("Hola")
                                                 .add(42)
                                                 .add(22.5)
                                                 .add(LocalDate.now())
                                                 .remove()
                                                 .remove()
                                                 .remove()
                                                 .remove()
                                                 .add("Maria")
                                                 .next()
                                                 .current());
    }


}
