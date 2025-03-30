import Palet
import Route
import Stack
import Truck
import Control.Exception
import System.IO.Unsafe

-- Test function that evaluates an action and returns True if an exception is thrown, False otherwise.
testF :: Show a => a -> Bool
testF action = unsafePerformIO $ do
    result <- tryJust isException (evaluate action)
    return $ case result of
        Left _ -> True
        Right _ -> False
    where
        isException :: SomeException -> Maybe ()
        isException _ = Just ()

-- Palet 
p1 = newP "Rosario" 1 -- Create a pallet with destination Rosario and weight 1
p2 = newP "Buenos Aires" 2 -- Create a pallet with destination Buenos Aires and weight 2
p3 = newP "La Plata" 2 -- Create a pallet with destination La Plata and weight 2
p4 = newP "San Fernando" 3 -- Create a pallet with destination San Fernando and weight 3
p5 = newP "General Pico" 1 -- Create a pallet with destination General Pico and weight 1

-- Route
r1 = newR ["Rosario", "Buenos Aires", "La Plata", "San Fernando"] -- Create a route with the cities in order
r2 = newR ["San Nicolas"] -- Create a route with only one city

-- Stack
s1 = newS 5 -- Create an empty stack with capacity 5
s2 = stackS s1 p4 -- Stack the pallet from San Fernando in the stack
s3 = stackS s2 p3 -- Stack the pallet from La Plata in the stack
s4 = stackS s3 p2 -- Stack the pallet from Buenos Aires in the stack
s5 = stackS s4 p1 -- Stack the pallet from Rosario in the stack
s6 = stackS s5 p5 -- Stack the pallet from General Pico in the stack

-- Truck
t1 = newT 3 4 r1 -- Create a truck with 3 bays, 4 height and the route r1
t2 = loadT (loadT (loadT (loadT t1 p4) p3) p2) p1 -- Load the pallets in the truck
t3 = loadT (loadT t2 p1) p2 -- Load the pallets in the truck
t_almost_full = loadT (loadT (loadT (loadT t2 p4) p3) p2) p1 -- Load the pallets in the truck
t_full = loadT (loadT (loadT (loadT t_almost_full p4) p3) p2) p1 -- Load the pallets in the truck

-- Test for Palet.hs
testPallet :: [Bool]
testPallet = [
             testF (newP "General Pico" (-9)), -- Check negative weight exception.
             testF (newP "Rosario" 0), -- Check zero weight exception.
             testF (newP "" 10), -- Check empty destination exception.
             destinationP p1 == "Rosario", -- Check pallet 1 (p1) destination.
             netP p1 == 1 -- Check pallet 1 (p1) weight
             ]

-- Test for Route.hs
testRoute :: [Bool]
testRoute = [
            testF (newR []), -- Check empty route list
            testF (inOrderR r1 "Rosario" "General Pico"), -- Check first city exists, second city does not.
            testF (inOrderR r1 "General Pico" "Rosario"), -- Check second city exists, first city does not.
            testF (inOrderR r1 "San Nicolas" "General Pico"), -- Check both cities do not exist.
            testF (inOrderR r2 "San Nicolas" "General Pico"), -- Check route with one city and one city that does not exist.
            testF (inOrderR r2 "General Pico" "San Nicolas"), -- Check route with one city and one city that does not exist.
            testF (inOrderR r2 "Rosario" "General Pico"), -- Check route with one city and both cities do not exist.
            inOrderR r1 "Buenos Aires" "San Fernando", -- Must be True, Buenos Aires is before San Fernando. They are in order.
            inOrderR r1 "Buenos Aires" "Buenos Aires", -- Must be True, both cities are the same.
            not (inOrderR r1 "La Plata" "Rosario"), --  Must be False, La Plata is After Rosario. They are not in order. 
            inOrderR r2 "San Nicolas" "San Nicolas", -- Must be True, both cities are the same.
            inRouteR r1 "Buenos Aires", -- Must be True, Buenos Aires is in the route.
            not (inRouteR r1 "General Pico"), -- Must be False, General Pico is not in the route.
            inRouteR r2 "San Nicolas", -- Must be True, San Nicolas is in the route.
            not (inRouteR r2 "Rosario") -- Must be False, Rosario is not in the route.
            ]

-- Test for Stack.hs
testStack :: [Bool]
testStack = [
            testF (newS (-9)), -- Check non positive capacity of a stack
            testF (newS 0), -- Check zero capacity of a stack
            freeCellsS s1 == 5, -- Check free cells in an empty stack
            freeCellsS s5 == 1, -- Check free cells in a stack almost full
            freeCellsS s6 == 0, -- Check free cells in a full stack
            netS s1 == 0, -- Check net weight in an empty stack
            netS s6 == (1+2+2+3+1), -- Check net weight in a full stack
            not (holdsS s6 (newP "General Pico" 1) (newR ["General Pico", "Rosario", "Buenos Aires", "La Plata", "San Fernando"])), -- Check if the full stack can hold a pallet
            not (holdsS s5 (newP "Rosario" 6) r1), -- Check if the stack can hold a pallet that exceeds the net weight limit
            holdsS (newS 1) (newP "Rosario" 1) r1, -- Check if the empty stack can hold a pallet
            not (holdsS s5 (newP "Buenos Aires" 1) r1), -- Check if the stack can hold a pallet with a destination that is in the route but not in order
            testF (holdsS s5 (newP "Rosario" 1) r2), -- Check if the stack can hold a pallet with a destination that is not in the route
            holdsS s5 (newP "Rosario" 1) r1, -- Check if the stack can hold a pallet with a destination that is in the route and in order
            netS (popS s6 "General Pico") == (1+2+2+3), -- Check net weight in a stack after unloading a pallet
            freeCellsS (popS s6 "General Pico") == 1, -- Check free cells in a stack after unloading a pallet
            netS (popS s6 "Rosario") == (1+2+2+3+1), -- Check net weight in a stack after trying to unload a pallet with a destination that is not in the top of the stack
            freeCellsS (popS s6 "Rosario") == 0, -- Check free cells in a stack after trying to unload a pallet with a destination that is not in the top of the stack
            netS (stackS s4 (newP "Rosario" 2)) == (2+2+3+2), -- Check net weight in a stack after stacking a pallet
            freeCellsS (stackS s4 (newP "Rosario" 2)) == 1 -- Check free cells in a stack after stacking a pallet
            ]

-- Test for Truck.hs
testTruck :: [Bool]
testTruck = [
            testF (newT 0 2 r1), -- Check non positive bays
            testF (newT 2 0 r1), -- Check non positive height
            testF (newT 0 0 r1), -- Check non positive bays and height
            testF (newT (-1) 2 r1), -- Check negative bays
            testF (newT 2 (-1) r1), -- Check negative height
            testF (newT (-1) (-1) r1), -- Check negative bays and height
            freeCellsT t1 == 12, -- Check free cells in an empty truck
            freeCellsT (loadT t1 p1) == (12-1), -- Check free cells in the truck with a pallet
            testF (loadT t1 (newP "General Pico" 1)), -- Check if the truck can load a pallet that is not in the route
            testF (loadT t3 p3) && freeCellsT t3 /= 0, -- The truck is not full, but the stack can't hold the pallet from La Plata
            freeCellsT t_full == 0, -- Check free cells in the full truck
            testF (loadT t_full p1), -- Check if we can put something in the truck that is full
            testF (loadT t3 (newP "Rosario" 10)), -- Check if we can put a pallet in t3 from Rosario but exceeds the net weight limit
            netT t1 == 0, -- Check net weight in an empty truck
            netT t2 == (1+2+2+3), -- Check net weight in a truck with 4 pallets
            testF (unloadT t1 "Victoria"), -- Check if I can unload a city that is not in the route
            netT (unloadT t3 "Rosario") == ((2+2+3) + (0) + (2)), -- check if I can unload a city that is in the route: Rosario
            freeCellsT (unloadT t3 "Rosario") == 8, -- check free cells in a truck after unloading a city that is in the route: Rosario
            netT (unloadT t1 "Rosario") == 0, -- check net weight in a truck after unloading a city that is in the route: Rosario
            freeCellsT (unloadT t1 "Rosario") == 12 -- check free cells in a truck after unloading a city that is in the route: Rosario
            ] 

-- Test all
testAll :: [Bool]
testAll = testPallet ++ testRoute ++ testStack ++ testTruck

-- Final result
result :: Bool
result = foldl (&&) True testAll

