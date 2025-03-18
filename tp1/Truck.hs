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
loadT (Tru bays route) palet | not (inRouteR route (destinationP palet)) = Tru bays route
                             | freeCellsT (Tru bays route) == 0 = Tru bays route  -- Optimización
                             | otherwise = Tru (tryLoad bays palet route) route 

tryLoad :: [Stack] -> Palet -> Route -> [Stack]
tryLoad [] _ _ = []
tryLoad (b:bs) palet route | holdsS b palet route = stackS b palet : bs
                           | otherwise = b : tryLoad bs palet route

unloadT :: Truck -> String -> Truck
unloadT (Tru stacks route) city = Tru [popS s city | s <- stacks] route

netT :: Truck -> Int
netT (Tru stacks _) = sum (map netS stacks)

-- tenemos que chequear el caso en que nos esten queriendo sacar algo del camión y todavía queden cosas de estaciones anteriores?
-- está buena nuestra implementación de loadT?
-- siempre tenemos que devolver cosas de manera pasiva o agresiva o podemos mechar?
-- chequeamos que una ruta tenga destinos?
-- nuestra solución pasiva contra 
-- hay algun problema si en vez de usar las funciones builtin tipo elem usamos las creadas por nosotros tipo myElem?
-- SI PONEMOS EL FREECELLS EN HOLDS TENEMOS QUE SACARLO DEL CHEQUEO EN EL LOAD