module Route ( Route, newR, inOrderR, inRouteR ) where

data Route = Rou [ String ] deriving (Eq, Show) 

newR :: [ String ] -> Route -- Construye una ruta segun una lista de ciudades
newR cities_list = Rou cities_list -- Warning

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou cities_list) city_1 city_2 | null cities_list = False
                                         | head cities_list == city_1 = True
                                         | head cities_list == city_2 = False
                                         | otherwise = inOrderR (Rou (tail cities_list)) city_1 city_2

inRouteR :: Route -> String -> Bool -- Indica si la ciudad consultada está en la ruta
inRouteR (Rou cities) city = elem city cities