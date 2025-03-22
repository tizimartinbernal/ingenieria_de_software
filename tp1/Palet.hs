module Palet (Palet, newP, destinationP, netP) 
    where

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet
newP destination weight | null destination = error "Destination must not be empty"
                        | weight <= 0 = error "Weight must be a positive value greater than 0"
                        | otherwise = Pal destination weight
                        
destinationP :: Palet -> String 
destinationP (Pal destination _) = destination

netP :: Palet -> Int
netP (Pal _ weight) = weight