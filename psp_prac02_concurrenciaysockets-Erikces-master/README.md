
# PSP - Concurrencia y Sockets

Completa el código proporcionado. Los test unitarios y los ficheros de salida de ejemplo te ayudarán a completar los detalles que no estén en estas especificaciones.

## Especificaciones:

Escribe el sistema para gestionar las puertas y los salones de un restaurante.
- Sistema se encarga de abrir el restarurante, y pasado un tiempo, cerrarlo.
- El Restaurante tendrá tantos salones y puertas como reciba por parámetro.
- El Salón 0 tendrá 10 plazas; el 1, 5 plazas más, 15; el 2 otras 5 más, 20 plazas... y así para tantos como sea necesario.
- Cada puerta recibirá Reservas por el puerto basePuerto + i.
- El Restaurante escribirá mensajes de estado en el cartel.
- Mientras el restaurante esté abierto, Cartel publicará, cada x tiempo el estado de la agenda y los salones.
- Para cerrar el restaurante, primero se cierran las puertas (ya no se admiten más reservas). Tras cerrar las puertas, se cierran los salones: se descartan las reservas pendientes, pero se deja terminar a los comensales ya sentados.
- Cuando una Puerta recibe una Reserva, se añade a la agenda del restaurante (común para todas las puertas). Una reserva tiene un nombre y el número de comensales.
- Los salones atenderán las reservas en la agenda del restaurante (común para todos los salones), siempre y cuando tengan capacidad para atender a todos los comensales de la reserva.
- Cuando un Salón atiende a una reserva, lanza su ejecución. El tiempo de ocupación de la mesa será BASE_MESA + (comensales * EXTRA_X_PERS).
- Cuando los comensales de una Reserva terminan de comer, liberan el espacio en el salón, que puede ser reutilizado por otra reserva.
- Utilizando la clase Salida (a completar), se loguearán los eventos convenientes.
- Los ficheros cartel.txt y logs.txt proporcionados incluyen una muestra de los mensajes esperados.

## Requisitos:
- Debes respetar el código proporcionado.
- Cuida la calidad de tu código.
- Si haces cambios sobre los tests, no debes comitearlos.

Se recomienda intentar superar los tests uno a uno, y por el orden de numeración indicado.

## Rúbrica:
- Test 01 Sin Reservas: 3 puntos
- Test 02 Una Reserva: 5 puntos
- Test 03 Una Reserva Grande: 1 punto
- Test 04 Dos Reservas: 3 puntos
- Test 05 Dos Reservas Una Grande: 1 punto
- Test 06 Una reserva ko 1: 1 punto
- Test 07 Una reserva ko 2: 1 punto
- Test 08 Muchas Reservas Poco Tiempo: 3 puntos
- Test 09 Muchas Reservas Con Tiempo: 3 puntos
- Test 10 Muchas Reservas Orden: 3 puntos
- Test 11 Restaurante Lleno: 1 punto
- Revisión manual: 10 puntos (solo se tomará en cuenta si se superan los 12 puntos en los tests automáticos)
