module Truck ( Truck, newT, freeCellsT, loadT, unloadT, netT ) where

import Palet
import Stack
import Route

data Truck = Tru [ Stack ] Route deriving (Eq, Show)