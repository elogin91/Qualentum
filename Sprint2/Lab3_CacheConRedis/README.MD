# ARRANCAR SERVICIOS MODO 1 DEDE CONSOLA: 


1. Abrimos una terminal.
2. Nos situamos en la carpeta correspondiente al ejercicio.
3. Ejecutamos los siguientes comandos:


   ```bash
  docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
  npm install
  npm start
   ```

# ARRANCAR SERVICIOS MODO 2 CON DOCKER COMPOSE: 

   ```bash
   docker-compose up --build
   ```

# REDIS-CLI: 

1. Abrimos una terminal.
2. Nos situamos en la carpeta correspondiente al ejercicio.
3. Ejecutamos los siguientes comandos para resolver la tarea:

   ```bash
   #Comando del alumno subscribiendose a un tablón
   docker exec -it redis-stack redis-cli subscribe tablon
   
   #Comando del alumno subscribiendose a un tablón
   docker exec -it redis-stack redis-cli publish tablon 'Van a ser publicadas las notas en el día de hoy.'
   ```
   
# También podemos usar en un navegadorla App Web que hemos creado con la siguiente dirección: http://localhost:3000/
