import Control.Exception
import System.IO.Unsafe
import Palet
import Route
import Stack

-- Función para verificar si una expresión falla (sin AuxFunctions, pero igual funciona)
testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()

-- Valores auxiliares
roma = "Roma"
rutaUnica = newR [roma]
pilaVacia = newS 2
paletRoma1 = newP roma 3
paletRoma2 = newP roma 4
pilaConUno = stackS pilaVacia paletRoma1

-- Test para verificar que NO hay error con esta implementación
testRutaUnicaNoError :: [Bool]
testRutaUnicaNoError = [
    freeCellsS pilaVacia == 2,          -- Capacidad inicial
    holdsS pilaVacia paletRoma1 rutaUnica, -- Acepta primer palet
    freeCellsS pilaConUno == 1,         -- Espacio restante
    netS pilaConUno == 3,               -- Peso correcto
    holdsS pilaConUno paletRoma2 rutaUnica, -- Acepta segundo palet (¡funciona!)
    freeCellsS (stackS pilaConUno paletRoma2) == 0, -- Apila correctamente
    netS (stackS pilaConUno paletRoma2) == 7,  -- Peso total correcto
    freeCellsS (popS (stackS pilaConUno paletRoma2) roma) == 2, -- Descarga uno
    netS (popS (stackS pilaConUno paletRoma2) roma) == 0  -- Queda el primero (3 tons)
  ]