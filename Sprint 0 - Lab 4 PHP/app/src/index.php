<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de Usuario</title>
</head>
<body>
    <h1>Formulario de Usuario</h1>
    <form action="index.php" method="post">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br><br>
        <label for="edad">Edad:</label>
        <input type="number" id="edad" name="edad" required><br><br>
        <label for="descripcion">Descripción:</label>
        <textarea id="descripcion" name="descripcion" required></textarea><br><br>
        <input type="submit" value="Enviar">
    </form>

    <?php
    if ($_SERVER["REQUEST_METHOD"] == "POST") {
        // Obtener la información del formulario
        $nombre = $_POST['nombre'];
        $edad = $_POST['edad'];
        $descripcion = $_POST['descripcion'];

        // Guardar el nombre y la descripción en nombre.txt
        $nombreFile = fopen("nombre.txt", "a");
        fwrite($nombreFile, "Nombre: $nombre\nDescripción: $descripcion\n\n");
        fclose($nombreFile);

        // Guardar la edad en edad.txt
        $edadFile = fopen("edad.txt", "a");
        fwrite($edadFile, "$edad\n");
        fclose($edadFile);

        // Función para calcular la media de edad
        function calcularMediaEdad() {
            $edades = file("edad.txt", FILE_IGNORE_NEW_LINES);
            $totalEdades = 0;
            $cantidadEdades = count($edades);

            foreach ($edades as $edad) {
                $totalEdades += (int)$edad;
            }

            $mediaEdad = $cantidadEdades > 0 ? $totalEdades / $cantidadEdades : 0;
            return $mediaEdad;
        }

        // Calcular y mostrar la media de edad
        $mediaEdad = calcularMediaEdad();
        echo "<p>La media de edad de tus usuarios es: $mediaEdad</p>";
    }
    ?>
</body>
</html>
