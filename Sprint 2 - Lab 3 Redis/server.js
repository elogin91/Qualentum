const express = require('express');
const http = require('http');
const socketIO = require('socket.io');
const Redis = require('ioredis');
const session = require('express-session');
const RedisStore = require('connect-redis')(session);

// Configurar el servidor
const app = express();
const server = http.createServer(app);
const io = socketIO(server);

// Crear cliente Redis para publicar, suscribirse y almacenar mensajes
const redisHost = process.env.REDIS_HOST || 'localhost';

const redisSubscriber = new Redis(redisHost);
const redisPublisher = new Redis(redisHost);
const redisSessionClient = new Redis(redisHost);

// Configurar la sesión de Express
const sesionMiddleware = session ({
    store: new RedisStore({ client: redisSessionClient}),
    secret: 'mySecret',
    saveUninitialized: false,
    resave: false,
    cookie: {
        htttpOnly: true,
        secure: false,
        maxAge: 80000000
    }
});

app.use(sesionMiddleware);

// Compartir la sesión entre Express y Socket.io
io.use((socket, next) => {
  sesionMiddleware(socket.request, {}, next);
});

// Suscribirse al canal 'tablon'
redisSubscriber.subscribe('tablon', (err, count) => {
    if (err) {
        console.error('Error al suscribirse al canal:', err);
    } else {
        console.log(`Suscrito al canal 'tablon'. Número de suscripciones: ${count}`);
    }
});

// Escuchar cuando se recibe un mensaje en Redis
redisSubscriber.on('message', (channel, message) => {
    console.log(`Nuevo mensaje en ${channel}: ${message}`);
    // Enviar el mensaje a todos los clientes conectados a través de WebSocket
    io.emit('notificacion', message);
});

// Guardar el mensaje en Redis
function almacenarMensajeEnRedis(sesion, mensaje) {
    const username = sesion.username || 'Anónimo';
    const usuario_mensaje = `${username}: ${mensaje}`;
    redisPublisher.rpush('historico_tablon', usuario_mensaje);
    redisPublisher.publish('tablon', usuario_mensaje, (err) => {
        if (err) {
            console.error('Error al almacenar mensaje en Redis:', err);
        } else {
            console.log('Mensaje almacenado en Redis:', mensaje);
        }
    });
}

// Cargar mensajes históricos de Redis y enviarlos al cliente
function cargarMensajesHistoricos(socket) {
    redisPublisher.lrange('historico_tablon', 0, -1, (err, mensajes) => {
        if (err) {
            console.error('Error al cargar mensajes de Redis:', err);
        } else {
            console.log('Mensajes históricos cargados:', mensajes); // Log para ver qué se está recuperando
            // Enviar los mensajes históricos al cliente que acaba de conectarse
            socket.emit('mensajesHistoricos', mensajes);
        }
    });
}



// Servir el archivo index.html
app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});


// Manejar conexiones WebSocket
io.on('connection', (socket) => {
    console.log('Conectando usuario...');

    // Recuperar la sesión del handshake
    const session = socket.request.session;

    console.log(`Un usuario se ha conectado con la sesión ID: ${session.id}`);

    // Enviar mensajes históricos cuando un nuevo cliente se conecta
    cargarMensajesHistoricos(socket);

    // Escucha nombre de usuario
    socket.emit('session status', { loggedIn: !!session.username })

    // Escuchar el evento 'add user'
      socket.on('add user', (username) => {
        session.username = username; // Almacenar el nombre de usuario en la sesión
        session.save();
        socket.emit('login', { username: username });
      });

    // Escuchar mensaje
    socket.on('mensajeProfesor', (mensaje) => {
        console.log('Mensaje del profesor:', mensaje);
        // Almacenar el mensaje en Redis
        almacenarMensajeEnRedis(session, mensaje);
    });

    // Escuchar desconexiones
    socket.on('disconnect', () => {
        console.log('Usuario desconectado');
    });
});

// Iniciar el servidor en el puerto 3000
server.listen(3000, () => {
    console.log('Servidor escuchando en puerto 3000');
});

