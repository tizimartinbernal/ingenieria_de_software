data Palet = Pal String Int deriving (Eq, Show)

newP :: String -> Int -> Palet   -- construye un Palet dada una ciudad de destino y un peso en toneladas
newP destination weight = Pal destination weight -- Warning?

destinationP :: Palet -> String  -- responde la ciudad destino del palet
destinationP (Pal destination _) = destination

netP :: Palet -> Int             -- responde el peso en toneladas del palet
netP (Pal _ weight) = weight

data Route = Rou [ String ] deriving (Eq, Show) -- Warning?

newR :: [ String ] -> Route                    -- construye una ruta segun una lista de ciudades
newR cities_list = Rou cities_list -- Warning

inOrderR :: Route -> String -> String -> Bool  -- indica si la primer ciudad consultada esta antes que la segunda ciudad en la ruta
inOrderR (Rou cities_list) city_1 city_2| null cities_list = False
                                        | head cities_list == city_1 = True
                                        | head cities_list == city_2 = False
                                        | otherwise = inOrderR (Rou (tail cities_list)) city_1 city_2
-- null se hace en O(1) cierto?

data Stack = Sta [ Palet ] Int deriving (Eq, Show)

newS :: Int -> Stack                      -- construye una Pila con la capacidad indicada 
newS capacity = Sta [] capacity -- Warning?

freeCellsS :: Stack -> Int                -- responde la celdas disponibles en la pila
freeCellsS (Sta pallets_list capacity) = capacity - length pallets_list

stackS :: Stack -> Palet -> Stack         -- apila el palet indicado en la pila
stackS (Sta pallets_list capacity) (Pal destination weight) | freeCellsS (Sta pallets_list capacity) == 0 = Sta pallets_list capacity
                                                            | otherwise = Sta (Pal destination weight:pallets_list) capacity

netS :: Stack -> Int                      -- responde el peso neto de los paletes en la pila
netS (Sta pallets_list capacity) = sum (map netP pallets_list) -- REVISAR PORQUE SE PUEDE HACER DE OTRA MANERA

holdsS :: Stack -> Palet -> Route -> Bool -- indica si la pila puede aceptar el palet considerando las ciudades en la ruta
holdsS (Sta pallets_list _) (Pal destination _) (Rou cities_list) | inOrderR (Rou cities_list) destination (destinationP (head pallets_list)) = True
                                                                | otherwise = False

popS :: Stack -> String -> Stack          -- quita del tope los paletes con destino en la ciudad indicada
popS (Sta pallets_list capacity) destination = Sta ([y | y <- pallets_list, destinationP y /= destination]) capacity

inRouteR :: Route -> String -> Bool -- Indica si la ciudad consultada está en la ruta
inRouteR (Rou cities) city = elem city cities


data Truck = Tru [ Stack ] Route deriving (Eq, Show)

newT :: Int -> Int -> Route -> Truck -- construye un camion según una cantidad de bahias, la altura de las mismas y una ruta
newT bays height route = Tru (replicate bays (newS height)) route

freeCellsT :: Truck -> Int
freeCellsT (Tru stacks _) = sum (map freeCellsS stacks)

loadT :: Truck -> Palet -> Truck

{-
Acá no hacemos el chequeo porque no tenemos acceso a la ruta,
Pero cuando hagamos la parte del camión hay que chequear si un
palet que quiero meter no tiene destino posterior a otro palet de abajo

Preguntarle a emilio cómo atajamos el error de stackS de cuando quieren
meter algo y no hay espacio: con un error, o no hacemos nada?

La solucion para netS que obtuvimos fue con map y con sum. A su la podemos hacer facilmente
Pero MAPS esta muy buena pero la forma que habíamos encontrado para hacerla sin eso
es alto bardo :(

en la funcion popS por que nos pasan el string de destino, si agarrando el primer
elemento de la pila le puedo ver el destino y con eso seguir sacando el resto mientras
se mantenga el mismo destino? Para facilitar el código?

Quieren que chequeemos en popS si el destino que nos pasan difiere del destino que esta para salir en la cola?

-}