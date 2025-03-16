import Palet
import Route
import Stack
import Truck
import Control.Exception
import System.IO.Unsafe
import qualified Data.Type.Bool as Fernando

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

testPallet = [testF (newP "General Pico" (-9)), -- check negative weight exception
    destinationP p1 == "Rosario", -- check pallet 1 destination
    netP p1 == 5 -- check pallet 1 weight
    ]

r1 = newR ["Rosario", "Buenos Aires", "La Plata", "San Fernando"]

testRoute = [testF (inOrderR (newR []) "Rosario" "Buenos Aires"), -- check empty route list
            testF (inOrderR r1 "Rosario" "General Pico"), -- check first city exists, second city does not.
            testF (inOrderR r1 "General Pico" "Rosario"), -- check second city exists, first city does not.
            inOrderR r1 "Buenos Aires" "San Fernando", -- must be true, Buenos Aires is before San Fernando. They are in order
            not (inOrderR r1 "La Plata" "Rosario"), -- must be false, La Plata is After Rosario. They are not in order 
            not (inRouteR (newR []) "Rosario"), -- Rosario is not in an empty list
            inRouteR r1 "Buenos Aires", -- Buenos Aires is in the route
            not (inRouteR r1 "General Pico") -- General Pico is not in the route
            ]
