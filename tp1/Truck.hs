module Truck (Truck, newT, freeCellsT, loadT, unloadT, netT)
    where

import AuxFunctions
import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

-- Construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT :: Int -> Int -> Route -> Truck
newT bays height route = Tru [newS height | y <- [1..bays]] route

-- Responde la celdas disponibles en el camion
freeCellsT :: Truck -> Int            
freeCellsT (Tru bays route) = mySum (myMap freeCellsS bays)

-- Carga un palet en el camion
loadT :: Truck -> Palet -> Truck
loadT (Tru bays route) palet | not (inRouteR route (destinationP palet)) = Tru bays route
                             | otherwise = Tru (tryLoad bays palet route) route 

-- Función auxiliar que intenta cargar un palet en una de las bahias del camion
tryLoad :: [Stack] -> Palet -> Route -> [Stack]
tryLoad [] _ _ = []
tryLoad (b:bs) palet route | holdsS b palet route && freeCellsS b > 0 = stackS b palet : bs
                           | otherwise = b : tryLoad bs palet route

-- Responde un camion al que se le han descargado los paletes que podían descargarse en la ciudad
unloadT :: Truck -> String -> Truck
unloadT (Tru stacks route) city = Tru [popS s city | s <- stacks] route

-- Responde el peso neto en toneladas de los paletes en el camion
netT :: Truck -> Int
netT (Tru stacks _) = mySum (myMap netS stacks)

-- tenemos que chequear el caso en que nos esten queriendo sacar algo del camión y todavía queden cosas de estaciones anteriores?
-- está buena nuestra implementación de loadT?
-- siempre tenemos que devolver cosas de manera pasiva o agresiva o podemos mechar?