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

p1 = newP "Rosario" 1
p2 = newP "Buenos Aires" 2
p3 = newP "La Plata" 2
p4 = newP "San Fernando" 3

testPallet = [testF (newP "General Pico" (-9)), -- Check negative weight exception
              testF (newP "" 10), -- Check empty destination exception
              destinationP p1 == "Rosario", -- Check pallet 1 destination
              netP p1 == 1 -- Check pallet 1 weight
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

s6 = stackS s5 (newP "General Pico" 1) -- stack the pallet from General Pico in the stack

testStack = [testF (newS (-9)), -- check non positive capacity of a stack

            freeCellsS s1 == 5, -- check free cells in an empty stack

            freeCellsS s5 == 1, -- check free cells in the current stack

            netS s1 == 0, -- check net weight in an empty stack

            netS s5 == (1+2+2+3), -- check net weight in a full stack

            not (holdsS s6 (newP "General Pico" 1) (newR ["General Pico", "Rosario", "Buenos Aires", "La Plata", "San Fernando"])),
            -- check if the full stack can hold a pallet
            
            not (holdsS s5 (newP "Rosario" 6) r1), -- check if the stack can hold a pallet that exceeds the net weight limit

            holdsS (newS 1) (newP "Rosario" 1) r1, -- check if the empty stack can hold a pallet
            
            not (holdsS s5 (newP "Buenos Aires" 1) r1),
            -- check if the stack can hold a pallet with a destination that is in the route but not in order
            
            holdsS s5 (newP "Rosario" 1) r1, -- check if the stack can hold a pallet with a destination that is in the route and in order
            
            s6 == popS s6 "Victoria", -- check if the stack can pop something that is not in the stack
            
            s5 == popS s6 "General Pico" -- check if the stack can pop the pallet from General Pico
            ]

t1 = newT 3 4 r1 -- create a truck with 2 bays and 2 height
-- primero cargar de san fernando, luego de la plata, luego de buenos aires y por ultimo de rosario
t2 = loadT (loadT (loadT (loadT t1 p4) p3) p2) p1

-- ahora quiero meter algo de rosario, luego algo de buenos aires
t3 = loadT (loadT t2 p1) p2

-- ahora a partir de t2 quiero llenar todo el espacio posible con SF, LP, BA y RO
t_almost_full = loadT (loadT (loadT (loadT t2 p4) p3) p2) p1
t_full = loadT (loadT (loadT (loadT t_almost_full p4) p3) p2) p1
{-
T2
|RO||__|
|BA||__|
|LP||__|
|SF||__|

T3
|RO||__||__|
|BA||__||__|
|LP||__||__|
|SF||RO||BA|

T_FULL
|RO||RO||RO|
|BA||BA||BA|
|LP||LP||LP|
|SF||SF||SF|
-}

testTruck1 = [testF (newT 0 2 r1), -- check non positive bays

            testF (newT 2 0 r1), -- check non positive height

            freeCellsT t1 == 12, -- check free cells in an empty truck

            freeCellsT (loadT t1 p1) == (12-1), -- check free cells in the truck with a pallet

            testF (loadT t1 (newP "General Pico" 1)), -- check if the truck can load a pallet that is not in the route
            
            testF (loadT t3 p3) && freeCellsT t3 /= 0, -- the truck is not full, but the stack can't hold the pallet from La Plata

            freeCellsT t_full == 0, -- check free cells in the full truck

            testF (loadT t_full p1), -- check if we can put something in the truck that is full

            testF (loadT t3 (newP "Rosario" 10)), -- check if we can put a pallet in t3 from rosario but exceeds the net weight limit

            netT t1 == 0, -- check net weight in an empty truck

            netT t2 == (1+2+2+3), -- check net weight in a truck with 4 pallets

            -- check if I can unload a city that is not in the route
            testF (unloadT t1 "Victoria"),

            netT (unloadT t3 "Rosario") == ((2+2+3) + (0) + (2)) -- check if I can unload a city that is in the route: Rosario
            ] 

testAll = testPallet ++ testRoute ++ testStack ++ testTruck1

result = foldl (&&) True testAll