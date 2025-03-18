import Palet
import Route
import Stack
import Truck
import Control.Exception
import System.IO.Unsafe
import qualified Data.Type.Bool as Fernando
import Distribution.Simple.Test (test)

testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()

p1 = newP "Rosario" 5
p2 = newP "Buenos Aires" 10
p3 = newP "La Plata" 3
p4 = newP "San Fernando" 15

testPallet = [testF (newP "General Pico" (-9)), -- Check negative weight exception
              testF (newP "" 10), -- Check empty destination exception
              destinationP p1 == "Rosario", -- Check pallet 1 destination
              netP p1 == 5 -- Check pallet 1 weight
            ]

r1 = newR ["Rosario", "Buenos Aires", "La Plata", "San Fernando"]

testRoute = [testF (newR []), -- check empty route list
            testF (inOrderR r1 "Rosario" "General Pico"), -- check first city exists, second city does not.
            testF (inOrderR r1 "General Pico" "Rosario"), -- check second city exists, first city does not.
            inOrderR r1 "Buenos Aires" "San Fernando", -- must be true, Buenos Aires is before San Fernando. They are in order
            not (inOrderR r1 "La Plata" "Rosario"), -- must be false, La Plata is After Rosario. They are not in order 
            inRouteR r1 "Buenos Aires", -- Buenos Aires is in the route
            not (inRouteR r1 "General Pico") -- General Pico is not in the route
            ]

s1 = newS 5 -- create an empty stack with capacity 5
s2 = stackS s1 p4 -- stack the pallet from San Fernando in the stack
s3 = stackS s2 p3 -- stack the pallet from La Plata in the stack
s4 = stackS s3 p2 -- stack the pallet from Buenos Aires in the stack
s5 = stackS s4 p1 -- stack the pallet from Rosario in the stack

s6 = stackS s5 (newP "General Pico" 13) -- stack the pallet from General Pico in the stack

testStack = [testF (newS (-9)), -- check non positive capacity of a stack
            freeCellsS s1 == 5, -- check free cells in an empty stack
            freeCellsS s5 == 1, -- check free cells in the current stack
            netS s1 == 0, -- check net weight in an empty stack
            netS s5 == (5 + 10 + 3 + 15), -- check net weight in a full stack
            testF (holdsS s5 (newP "General Pico" 13) r1), -- check if the stack can hold a pallet with a destination that is not in the route
            holdsS s5 (newP "Rosario" 4) r1, -- check if the stack can hold a pallet with a destination that is in the route and in order
            not (holdsS s5 (newP "Rosario" 4) (newR ["General Pico", "Rosario", "Buenos Aires", "La Plata", "San Fernando"])),
            -- check if the stack can hold a pallet with a destination that is in the route but not in order
            holdsS s5 (newP "General Pico" 13) (newR ["General Pico", "Rosario", "Buenos Aires", "La Plata", "San Fernando"]),
            -- check if the stack can hold a pallet with a destination that is in the route and in order
            not (holdsS s6 (newP "General Pico" 13) (newR ["General Pico", "Rosario", "Buenos Aires", "La Plata", "San Fernando"])),
            -- check if the full stack can hold a pallet with a destination that is in the route and in order
            s6 == popS s6 "Victoria", -- check if the stack can pop something that is not in the stack #
            s5 == popS s6 "General Pico" -- check if the stack can pop the pallet from General Pico #
            ]
roma = "Roma"
paris = "Paris"
mdq = "Mar del Plata"
berna = "Berna"

rutaCorta = newR [roma]
rutaLarga = newR [roma, paris, mdq, berna]

paletRoma = newP roma 5
paletParis = newP paris 3
paletMdq = newP mdq 8
paletInvalido = newP "Madrid" 7

pilaVacia = newS 2
pilaConUno = stackS pilaVacia paletRoma
pilaLlena = stackS (stackS pilaVacia paletRoma) paletParis

camionVacio = newT 2 2 rutaLarga
camionConCarga = loadT (loadT camionVacio paletRoma) paletParis

testTruck = [
    freeCellsT camionVacio == 4,
    freeCellsT camionConCarga == 2,
    netT camionConCarga == 8,
    freeCellsT (unloadT camionConCarga roma) == 3,
    testF (newT 0 2 rutaLarga),
    testF (newT 2 0 rutaLarga),
    testF (newT (-1) 2 rutaLarga),
    freeCellsT (loadT camionConCarga paletInvalido) == 2,
    freeCellsT (loadT (loadT camionConCarga paletMdq) (newP mdq 8)) == 0,
    netT (unloadT camionVacio roma) == 0
  ]