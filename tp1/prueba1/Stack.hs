module Stack (Stack, newS, freeCellsS, stackS, netS, holdsS, popS) where

import Palet
import Route

data Stack = Sta [Palet] Int deriving (Eq, Show)

newS :: Int -> Stack -- Construye una Pila con la capacidad indicada 
newS capacity = Sta [] capacity

freeCellsS :: Stack -> Int -- Responde la celdas disponibles en la pila
freeCellsS (Sta pallets_list capacity) = capacity - length pallets_list

stackS :: Stack -> Palet -> Stack -- Apila el palet indicado en la pila
stackS (Sta pallets_list capacity) pallet | freeCellsS (Sta pallets_list capacity) == 0 = Sta pallets_list capacity
                                          | otherwise = Sta (newP (destinationP pallet) (netP pallet):pallets_list) capacity

netS :: Stack -> Int -- Responde el peso neto de los paletes en la pila
netS (Sta pallets_list capacity) = sum (map netP pallets_list) -- REVISAR PORQUE SE PUEDE HACER DE OTRA MANERA

holdsS :: Stack -> Palet -> Route -> Bool -- Indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta pallets_list _) pallet route | not (inRouteR route (destinationP pallet)) = False
                                         | inOrderR route (destinationP pallet) (destinationP (head pallets_list)) = True
                                         | otherwise = False

popS :: Stack -> String -> Stack -- Quita del tope los paletes con destino en la ciudad indicada
popS (Sta pallets_list capacity) destination = Sta ([y | y <- pallets_list, destinationP y /= destination]) capacity


-- tenemos que reemplazar Pal destination weight con newP
-- en holdsS tenemos que chequear también si se puede meter en base a la capacidad?