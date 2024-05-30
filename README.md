# Manu

Para iniciar el proyecto ir hasta src/main/java/com/example/manu/ManuApplication.java y lanzar el proyecto.

Después se prodrá acceder a http://localhost:8080/swagger-ui/index.html#/ donde habrá diferentes opciones:

• Consultar todas las naves utilizando paginación.  --> En /api/naves/getAll
• Consultar una única nave por id.  --> En /api/naves/{id}
• Consultar todas las naves que contienen, en su nombre, el valor de un parámetro enviado en la petición. Por ejemplo, si enviamos “wing” devolverá “x-wing”.  --> En /api/naves/findByName
• Crear una nueva nave.  --> En /api/naves/createNave
• Modificar una nave.  --> /api/naves/{id}
• Eliminar una nave.  --> /api/naves/{id}
• Test unitario de como mínimo de una clase.  --> Los Test Unitarios se encuentran en src/test/java/com/example/manu
• Desarrollar un @Aspect que añada una línea de log cuando nos piden una nave con un id negativo. --> la Clase se encuentra en src/main/java/com/example/manu/logger/LoggerNegativo.java
• Gestión centralizada de excepciones. --> Se lanzan las excepciones y se devuelven errores según el caso
• Utilizar cachés de algún tipo.  --> Las naves están cacheadas

Hay Test de Integración de casi todas las clases y el programa lanzará automáticamente los Insert necesarios para popular la tabla Naves (src/main/resources/data.sql)
