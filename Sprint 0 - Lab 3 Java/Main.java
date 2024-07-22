import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Lista para almacenar los nombres
    private static List<String> nombres = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
            // Mostrar cabecera y opciones
            System.out.println("Bienvenido al programa de gestión de nombres");
            System.out.println("1. Añadir nombres");
            System.out.println("2. Mostrar nombres");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción (1, 2, 3): ");

            // Leer opción del usuario
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    // Añadir nombres
                    añadirNombres(scanner);
                    break;
                case 2:
                    // Mostrar nombres
                    mostrarNombres();
                    break;
                case 3:
                    // Salir
                    System.out.println("El programa ha finalizado.");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija 1, 2 o 3.");
            }
        }

        scanner.close();
    }

    // Método para añadir nombres a la lista (reemplazar el listado anterior)
    private static void añadirNombres(Scanner scanner) {
        System.out.print("Introduzca los nombres separados por comas: ");
        String entrada = scanner.nextLine();
        String[] nombresArray = entrada.split(",");

        // Limpiar la lista actual antes de añadir nuevos nombres
        nombres.clear();

        for (String nombre : nombresArray) {
            nombres.add(nombre.trim());
        }
    }

    // Método para mostrar los nombres de la lista
    private static void mostrarNombres() {
        if (nombres.isEmpty()) {
            System.out.println("No hay nombres almacenados.");
        } else {
            System.out.println("Nombres almacenados:");
            for (String nombre : nombres) {
                System.out.println(nombre);
            }
        }
    }
}
