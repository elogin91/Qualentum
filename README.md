--- 

# Instrucciones para Ejecutar las Soluciones de Cada Laboratorio

### **Sprint 1:**

#### - Laboratorio 4
Instrucciones específicas del laboratorio 4.

#### - Laboratorio 5
Instrucciones específicas del laboratorio 5.

---

### **Sprint 2:**

#### - Laboratorio 1
1. Abrimos una terminal.
2. Nos situamos en la carpeta correspondiente al ejercicio.
3. Ejecutamos los siguientes comandos:

   ```bash
   docker-compose up
   ```

   Para detener el servicio:

   ```bash
   docker-compose down
   ```

---

#### - Laboratorio 2
1. Abrimos una terminal.
2. Nos situamos en la carpeta correspondiente al ejercicio.
3. Ejecutamos los siguientes comandos:

   ```bash
   docker-compose up
   ```

4. Para acceder al contenedor de MySQL, ejecutamos:

   ```bash
   docker exec -it mysql_service mysql -u root -p
   ```

   **En MySQL** (cuando se te pida, introduce la contraseña):

   ```sql
   show databases;
   use cv;
   show tables;
   ```

5. Para listar candidatos con recomendaciones, usa la siguiente consulta SQL:

   ```sql
   SELECT *
   FROM candidato c
   JOIN recomendacion r ON c.id = r.id_candidato;
   ```

6. Para listar candidatos que sepan inglés, usa la siguiente consulta SQL:

   ```sql
   SELECT *
   FROM candidato c
   JOIN candidato_idioma ci ON c.id = ci.id_candidato
   JOIN idioma i ON ci.id_idioma = i.id
   WHERE i.nombre LIKE '%Inglés%';
   ```

7. Para acceder al contenedor de MongoDB, ejecutamos:

   ```bash
   docker exec -it mongodb_service mongosh
   ```

   **En MongoDB**, ejecuta los siguientes comandos:

   ```bash
   show dbs;
   use cv;
   show tables;
   ```

8. Para detener los servicios, ejecutamos:

   ```bash
   docker-compose down
   ```

--- 

