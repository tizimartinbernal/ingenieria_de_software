module Route ( Route, newR, inOrderR, inRouteR ) 
    where

import AuxFunctions

data Route = Rou [ String ] deriving (Eq, Show) 

-- Construye una ruta segun una lista de ciudades
newR :: [ String ] -> Route 
newR cities_list = Rou cities_list

-- Indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR :: Route -> String -> String -> Bool
inOrderR (Rou cities_list) city_1 city_2 | myNull cities_list = False
                                         | myHead cities_list == city_1 = True
                                         | myHead cities_list == city_2 = False
                                         | otherwise = inOrderR (Rou (myTail cities_list)) city_1 city_2

-- Indica si la ciudad consultada está en la ruta
inRouteR :: Route -> String -> Bool 
inRouteR (Rou cities) city = myElem city cities