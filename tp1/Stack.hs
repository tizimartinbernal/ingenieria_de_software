module Stack (Stack, newS, freeCellsS, stackS, netS, holdsS, popS) 
    where

import Palet
import Route

data Stack = Sta [Palet] Int deriving (Eq, Show)

newS :: Int -> Stack 
newS capacity | capacity <= 0 = error "Capacity must be a positive value"
              | otherwise = Sta [] capacity

freeCellsS :: Stack -> Int 
freeCellsS (Sta pallets_list capacity) = capacity - length pallets_list

stackS :: Stack -> Palet -> Stack 
stackS (Sta pallets_list capacity) pallet = Sta (pallet:pallets_list) capacity

netS :: Stack -> Int
netS (Sta pallets_list capacity) = sum (map netP pallets_list)

holdsS :: Stack -> Palet -> Route -> Bool
holdsS (Sta pallets_list capacity) pallet route | freeCellsS (Sta pallets_list capacity) == 0 = False
                                                | netS (Sta (pallet:pallets_list) capacity) > 10 = False
                                                | null pallets_list = True
                                                | otherwise = inOrderR route (destinationP pallet) (destinationP (head pallets_list))

popS :: Stack -> String -> Stack
popS (Sta pallets_list capacity) destination | null pallets_list = Sta [] capacity
                                             | destinationP (head pallets_list) == destination = popS (Sta (tail pallets_list) capacity) destination
                                             | otherwise = Sta pallets_list capacity
