# Usar la imagen oficial de Python como base
FROM python:3.8

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo requirements.txt al directorio de trabajo
COPY requirements.txt .

# Instalar las dependencias de Python necesarias
RUN pip install -r requirements.txt

# Copiar el resto de la aplicación al contenedor
COPY . .

# Establecer las variables de entorno para la conexión a MySQL
ENV FLASK_APP=app.py
ENV FLASK_ENV=development
ENV MYSQL_HOST=mysql
ENV MYSQL_DATABASE=todo_app
ENV MYSQL_ROOT_PASSWORD=password

# Exponer el puerto en el que correrá la aplicación Flask
EXPOSE 8080

# Comando para ejecutar la aplicación Flask
CMD ["flask", "run", "--host=0.0.0.0", "--port=8080"]
