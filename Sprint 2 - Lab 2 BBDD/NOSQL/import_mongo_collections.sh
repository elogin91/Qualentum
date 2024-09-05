#!/bin/bash

# Recorre todos los archivos JSON en el directorio /data
for file in /docker-entrypoint-initdb.d/*.json; do
    # Extrae el nombre de la base de datos y la colección del nombre del archivo
    dbname=$(basename "$file" .json | cut -d'.' -f1)
    collection=$(basename "$file" .json | cut -d'.' -f2)

    # Asegúrate de que ambos valores estén definidos
    if [[ -z "$dbname" || -z "$collection" ]]; then
        echo "Error: No se pudo determinar la base de datos o la colección para el archivo $file"
        exit
    fi

    echo "Importando $file en $dbname.$collection"

    # Ejecuta el comando mongoimport para importar los datos
    mongoimport --db "$dbname" --collection "$collection" --file "$file" --jsonArray
done
