# Utilizar una imagen base de Node.js
FROM node:18-alpine

# Crear un directorio de trabajo dentro del contenedor
WORKDIR /usr/src/app

# Copiar los archivos package.json y package-lock.json
COPY package*.json ./

# Instalar las dependencias
RUN npm install

# Copiar todo el código de la aplicación desde el directorio actual
COPY ./*.js .
COPY ./*.html .

# Exponer el puerto en el que corre tu aplicación (ajusta si es necesario)
EXPOSE 3000

# Comando para ejecutar la aplicación
CMD ["npm", "start"]
