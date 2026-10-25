package es.accenture.appcomercio;

import java.util.Scanner;

/**
 * Punto de entrada de la aplicacion de comercio.
 */
public final class AppComercio {

    private AppComercio() {
    }

    /**
     * Ejecuta el flujo completo de compra por consola.
     *
     * @param args argumentos de la aplicacion
     */
    public static void main(String[] args) {
        Producto[] almacen = ComercioUtils.crearAlmacen();
        ComercioUtils.mostrarProductos(almacen, true);

        Cliente cliente = new Cliente(new Persona(
                "Rodrigo Tapias Herrero",
                "11.111.111-H",
                "Calle Federico Garcia Lorca, 3"));

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n--- Comenzamos con la compra ---");
            boolean continuar = true;
            while (continuar) {
                ComercioUtils.añadirProductoCarrito(almacen, cliente, scanner);
                continuar = leerContinuar(scanner);
            }
        }

        cliente.mostrarCarrito();
        cliente.enviarPedido();
        ComercioUtils.mostrarProductos(almacen, false);
    }

    private static boolean leerContinuar(Scanner scanner) {
        while (true) {
            System.out.print("¿Quieres añadir otro producto? (s/n): ");
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("No se recibio confirmacion para continuar.");
            }
            String respuesta = scanner.nextLine().trim();
            if (respuesta.equalsIgnoreCase("s")) {
                return true;
            }
            if (respuesta.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Respuesta no valida. Introduce 's' o 'n'.");
        }
    }
}
