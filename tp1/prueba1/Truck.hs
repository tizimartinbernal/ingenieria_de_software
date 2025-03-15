--module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where
module Truck (Truck, newT, freeCellsT, loadT) where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck  -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT bays height route = Tru [newS height | y <- [1..bays]] route

freeCellsT :: Truck -> Int            -- responde la celdas disponibles en el camion
freeCellsT (Tru bays route) = sum (map freeCellsS bays)

loadT :: Truck -> Palet -> Truck      -- carga un palet en el camion
loadT (Tru bays route) palet = Tru 

