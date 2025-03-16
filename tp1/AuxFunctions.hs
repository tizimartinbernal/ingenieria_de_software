module AuxFunctions (myNull, myHead, myTail, myInit, myElem, myLength, mySum, myMap)
    where

-- Dado una lista, devuelve True si la lista es vacía.
myNull :: [a] -> Bool
myNull [] = True
myNull _ = False

-- Recibe una lista y devuelve el primer elemento de la lista.
myHead :: [a] -> a
myHead [] = error "Empty list"
myHead (x:_) = x

-- Recibe una lista y devuelve la lista sin el primer elemento.
myTail :: [a] -> [a]
myTail [] = []
myTail (_:xs) = xs  

-- Recibe una lista y devuelve la lista sin el último elemento.
myInit :: [a] -> [a]
myInit [] = []
myInit [x] = []
myInit (x:xs) = x : myInit xs

-- Dado un elemento y una lista, devuelve True si el elemento está en la lista
myElem :: Eq a => a -> [a] -> Bool
myElem x = foldr (\y b -> b || x == y) False

-- Calcula la longitud de una lista
myLength :: [a] -> Int
myLength = foldr (\_ n -> n + 1) 0

-- Suma los elementos de una lista
mySum :: Num a => [a] -> a
mySum = foldr (+) 0

-- Aplica una función a cada elemento de una lista
myMap :: (a -> b) -> [a] -> [b]
myMap f = foldr (\x xs -> f x : xs) []
