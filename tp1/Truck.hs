module Truck (Truck, newT, freeCellsT, loadT, unloadT, netT)
    where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck
newT bays height route | bays <= 0 || height <= 0 = error "Bay and height must be positive values"
                       | otherwise = Tru [newS height | y <- [1..bays]] route

freeCellsT :: Truck -> Int            
freeCellsT (Tru bays route) = sum (map freeCellsS bays)

loadT :: Truck -> Palet -> Truck
loadT (Tru bays route) palet | not (inRouteR route (destinationP palet)) = error "Destination not in route"
                             | freeCellsT (Tru bays route) == 0 = error "No free cells"
                             | not (elem True [holdsS b palet route | b <- bays]) = error "No stack can hold the palet"
                             | otherwise = Tru (tryLoad bays palet route) route

tryLoad :: [Stack] -> Palet -> Route -> [Stack]
tryLoad [] _ _ = []
tryLoad (b:bs) palet route | holdsS b palet route = stackS b palet : bs
                           | otherwise = b : tryLoad bs palet route

unloadT :: Truck -> String -> Truck
unloadT (Tru stacks route) city = Tru [popS s city | s <- stacks] route

netT :: Truck -> Int
netT (Tru stacks _) = sum (map netS stacks)