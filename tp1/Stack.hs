module Stack (Stack, newS, freeCellsS, stackS, netS, holdsS, popS) 
    where

import AuxFunctions
import Palet
import Route

data Stack = Sta [Palet] Int deriving (Eq, Show)

-- Construye una Pila con la capacidad indicada
newS :: Int -> Stack 
newS capacity | capacity <= 0 = error "capacity must be a positive value"
              | otherwise = Sta [] capacity

-- Responde la celdas disponibles en la pila
freeCellsS :: Stack -> Int 
freeCellsS (Sta pallets_list capacity) = capacity - myLength pallets_list

-- Apila el palet indicado en la pila
stackS :: Stack -> Palet -> Stack 
stackS (Sta pallets_list capacity) pallet | freeCellsS (Sta pallets_list capacity) == 0 = Sta pallets_list capacity
                                          | otherwise = Sta (pallet:pallets_list) capacity

-- Responde el peso neto de los paletes en la pila
netS :: Stack -> Int
netS (Sta pallets_list capacity) = mySum (myMap netP pallets_list)

-- Indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS :: Stack -> Palet -> Route -> Bool
holdsS (Sta pallets_list capacity) pallet route | not (inRouteR route (destinationP pallet)) = False
                                                | (freeCellsS (Sta pallets_list capacity) /= 0) && inOrderR route (destinationP pallet) (destinationP (myHead pallets_list)) = True
                                                | otherwise = False

-- Quita del tope los paletes con destino en la ciudad indicada
popS :: Stack -> String -> Stack 
popS (Sta pallets_list capacity) destination = Sta ([y | y <- pallets_list, destinationP y /= destination]) capacity


-- en holdsS tenemos que chequear también si se puede meter en base a la capacidad?