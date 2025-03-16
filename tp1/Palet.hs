module Palet (Palet, newP, destinationP, netP) 
    where

data Palet = Pal String Int deriving (Eq, Show)

-- Construye un Palet dada una ciudad de destino y un peso en toneladas
newP :: String -> Int -> Palet
newP destination weight | weight <= 0 = error "Weight must be a positive value"
                        | otherwise = Pal destination weight
                        
-- Responde la ciudad destino del palet
destinationP :: Palet -> String -- Responde la ciudad destino del palet
destinationP (Pal destination _) = destination

-- Responde el peso en toneladas del palet
netP :: Palet -> Int
netP (Pal _ weight) = weight