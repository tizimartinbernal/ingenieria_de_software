module Route ( Route, newR, inOrderR, inRouteR ) 
    where

data Route = Rou [ String ] deriving (Eq, Show) 

newR :: [ String ] -> Route 
newR cities_list | null cities_list = error "Route cannot be empty" 
                 | otherwise = Rou cities_list

inOrderR :: Route -> String -> String -> Bool
inOrderR (Rou cities_list) city_1 city_2 | not (inRouteR (Rou cities_list) city_1) || 
                                           not (inRouteR (Rou cities_list) city_2) = error "Some city is not in the route"
                                         | otherwise = checkCitiesOrder cities_list city_1 city_2

checkCitiesOrder :: [String] -> String -> String -> Bool
checkCitiesOrder cities_list city_1 city_2 | head cities_list == city_1 = True
                                           | head cities_list == city_2 = False
                                           | otherwise = checkCitiesOrder (tail cities_list) city_1 city_2

inRouteR :: Route -> String -> Bool 
inRouteR (Rou cities) city = elem city cities