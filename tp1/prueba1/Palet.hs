module Palet (Palet, newP, destinationP, netP)
    where

data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet -- Construye un Palet dada una ciudad de destino y un peso en toneladas
newP destination weight = Pal destination weight

destinationP :: Palet -> String -- Responde la ciudad destino del palet
destinationP (Pal destination _) = destination

netP :: Palet -> Int -- responde el peso en toneladas del palet
netP (Pal _ weight) = weight