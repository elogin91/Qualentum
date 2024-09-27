# PLANTILLA DE DOCUMENTACIÓN DE EVENTOS PARA EDA

## PedidoCreado

### Nombre del evento:
**PedidoCreado**

### Descripción breve:
El evento indica que un pedido ha sido creado con éxito. Contiene la información del pedido, incluyendo los productos vendidos y las cantidades de cada uno. Este evento se emite por el servicio de gestión de pedidos y es consumido por el servicio de inventario para actualizar las existencias.

### Detalles del evento

1. **ID del evento**: PedidoCreado
2. **Tipo de evento**: Evento de Dominio
3. **Origen del evento**: microservicio Servicio de Gestión de Pedidos.
4. **Consumidores del evento**: Servicio de Gestión de Inventario y Servicio de Gestión de Pedidos.
5. **Datos del evento**: 
   - `idPedido` (String): Identificador único del pedido.
   - `fechaPedido` (DateTime): Fecha y hora en que se creó el pedido.
   - `clienteId` (String): Identificador del cliente que realizó el pedido.
   - `productos` (Array de Objetos):
     - `productoId` (String): Identificador único del producto.
     - `cantidad` (Integer): Cantidad vendida del producto.

### Especificaciones técnicas

A. **Formato**: JSON  
B. **Esquema del mensaje**:  
   ```bash
   1. {
   2.   "evento": "PedidoCreado",
   3.   "idPedido": "12345",
   4.   "fechaPedido": "2024-09-27T14:30:00Z",
   5.   "clienteId": "cliente123",
   6.   "productos": [
   7.     {
   8.       "productoId": "zapato001",
   9.       "cantidad": 2
   10.     },
   11.     {
   12.       "productoId": "zapato002",
   13.       "cantidad": 1
   14.     }
   15.   ]
   16. }
   ```

C. **Validaciones**: 
   - `idPedido`: No puede estar vacío.
   - `productos`: Debe contener al menos un producto.
   - `cantidad`: Debe ser mayor que 0.

D. **Versionado**: Versión inicial 1.0.0.

### Reglas de negocio asociadas

I. **Descripción de cualquier regla de negocio o lógica asociada con el evento**:
   - Solo se puede generar un pedido si el cliente está registrado.
   - El stock debe ser verificado antes de crear el pedido.

II. **Casos de uso**:
   - Un cliente realiza una compra con uno o varios productos.

III. **Producción del evento**: 
   - El evento es generado cuando se confirma un pedido en el servicio de gestión de pedidos.

IV. **Consumo del evento**: 
   - El servicio de inventario consume este evento para reducir el stock de los productos correspondientes.

V. **Seguridad y privacidad**
   - No se incluye información sensible en el evento.

VI. **Consideraciones de seguridad**: Medidas de seguridad aplicables al manejo del evento.
   - Verificar autenticidad de los mensajes antes de procesarlos.

VII. **Consideraciones de privacidad**: Consideraciones sobre datos personales y cumplimiento de normativas de privacidad.
   - El evento no contiene datos personales sensibles.

VIII. **Historial de cambios**
   - Versión 1.0.0 inicial.

IX. **Registro de versiones**: Historial de cambios y actualizaciones del evento.
   - 1.0.0: Versión original del evento.


---

## InventarioActualizado

### Nombre del evento:
**InventarioActualizado**

### Descripción breve:
El evento confirma que se ha actualizado el inventario de los productos tras la creación de un pedido. Este evento es emitido por el servicio de inventario una vez que ha procesado el evento PedidoCreado.

### Detalles del evento

1. **Tipo de evento**: Evento de Sistema.
2. **Origen del evento**: microservicio Servicio de Gestión de Inventario.
3. **Consumidores del evento**: microservicios de Servicio de Gestión de Pedidos y de Servicio de Facturación.
4. **Datos del evento**: 
   - `idPedido` (String): Identificador único del pedido.
   - `fechaActualizacion` (DateTime): Fecha y hora en que se actualizó el inventario.
   - `productosActualizados` (Array de Objetos):
     - `productoId` (String): Identificador único del producto.
     - `cantidadRestante` (Integer): Cantidad restante en el inventario.

### Especificaciones técnicas

A. **Formato**: JSON  
B. **Esquema del mensaje**:  

   ```bash
        1. {
        2.   "evento": "InventarioActualizado",
        3.   "idPedido": "12345",
        4.   "fechaActualizacion": "2024-09-27T14:35:00Z",
        5.   "productosActualizados": [
        6.     {
        7.       "productoId": "zapato001",
        8.       "cantidadRestante": 18
        9.     },
        10.     {
        11.       "productoId": "zapato002",
        12.       "cantidadRestante": 9
        13.     }
        14.   ]
        15. }
   ```


C. **Validaciones**: 
   - `idPedido`: No puede estar vacío.
   - `productosActualizados`: Debe contener al menos un producto.
   - `cantidadRestante`: Debe ser un número entero positivo.

D. **Versionado**: Versión inicial 1.0.0.

### Reglas de negocio asociadas

I. **Descripción de cualquier regla de negocio o lógica asociada con el evento**:
   - No se puede actualizar el inventario si los productos están agotados.

II. **Casos de uso**:
   - Después de una venta, el stock es ajustado.

III. **Producción del evento**: 
   - El evento es generado después de procesar un evento PedidoCreado en el servicio de gestión de inventario.

IV. **Consumo del evento**: 
   - El servicio de pedidos puede consumir este evento para confirmar que el stock ha sido actualizado, y el servicio de facturación para proceder con la emisión de facturas.

V. **Seguridad y privacidad**
   - No se incluye información sensible en el evento.

VI. **Consideraciones de seguridad**: 
   - Verificar la integridad de los datos antes de actualizar el inventario.

VII. **Consideraciones de privacidad**: 
   - El evento no contiene datos personales sensibles.

VIII. **Historial de cambios**
   - Versión 1.0.0 inicial estable.

IX. **Registro de versiones**: Historial de cambios y actualizaciones del evento.
   - 1.0.0: Versión original del evento.



# RESOLUCIÓN MODO 1 DESDE CONSOLA: 


1. Abrimos una terminal.
2. Nos situamos en la carpeta correspondiente al ejercicio.
3. Ejecutamos los siguientes comandos:


   ```bash
  #Creamos la red
  docker network create app-tier --driver bridge
   
  #Descargamos y arrancamos el contenedor
  docker run -d --name kafka-server --hostname kafka-server \
    --network app-tier \
    -e KAFKA_CFG_NODE_ID=0 \
    -e KAFKA_CFG_PROCESS_ROLES=controller,broker \
    -e KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
    -e KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT \
    -e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@kafka-server:9093 \
    -e KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER \
    bitnami/kafka:latest
  
  #Entramos en el servicio de Kafka  
  docker exec -it kafka-server sh 
  
  #Creamos los topics
  kafka-topics.sh --create --topic pedido-creado --bootstrap-server localhost:9092
  kafka-topics.sh --create --topic inventario-actualizado --bootstrap-server localhost:9092

  #Producimos eventos
  kafka-console-producer.sh --topic pedido-creado --bootstrap-server localhost:9092
  //introduzco datos en JSON
  kafka-console-producer.sh --topic inventario-actualizado --bootstrap-server localhost:9092
  //introduzco datos en JSON
  
  #Consumimos los eventos desde el principio
  kafka-console-consumer.sh --topic pedido-creado --from-beginning --bootstrap-server localhost:9092
  kafka-console-consumer.sh --topic inventario-actualizado --from-beginning --bootstrap-server localhost:9092

   ```

# ARRANCAR SERVICIOS MODO 2 CON DOCKER COMPOSE: 

   ```bash
   docker-compose up --build
   ```

