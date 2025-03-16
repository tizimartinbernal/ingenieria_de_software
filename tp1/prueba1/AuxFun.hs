mySum :: Num a => [a] -> a
mySum = foldr (+) 0

myMap :: (a -> b) -> [a] -> [b]
myMap f = foldr (\x xs -> f x : xs) []
