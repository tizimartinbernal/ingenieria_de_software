--module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where
module Truck (Truck, newT, freeCellsT) 
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


