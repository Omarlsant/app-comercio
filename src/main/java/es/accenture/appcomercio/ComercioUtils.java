package es.accenture.appcomercio;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Operaciones auxiliares del almacen y del carrito.
 */
public final class ComercioUtils {

    private static final int STOCK_REPOSICION = 5;

    private ComercioUtils() {
    }

    /**
     * Crea el almacen inicial con diez productos.
     *
     * @return array de productos
     */
    public static Producto[] crearAlmacen() {
        Producto[] almacen = new Producto[10];
        crearAlmacen(almacen);
        return almacen;
    }

    /**
     * Rellena el array recibido con el almacen inicial.
     *
     * @param almacen array con diez posiciones disponibles
     */
    public static void crearAlmacen(Producto[] almacen) {
        if (almacen == null || almacen.length != 10) {
            throw new IllegalArgumentException("El almacen debe tener exactamente diez posiciones.");
        }
        Producto[] productos = new Producto[]{
                new Perecedero(1001, "Leche", 1.50, 8, 2),
                new Perecedero(1002, "Yogur", 0.80, 0, 5),
                new Perecedero(1003, "Pan", 1.20, 6, 1),
                new Perecedero(1004, "Queso fresco", 3.95, 4, 8),
                new Perecedero(1005, "Huevos", 2.75, 10, 12),
                new NoPerecedero(2001, "Arroz", 1.90, 7),
                new NoPerecedero(2002, "Pasta", 1.30, 5),
                new NoPerecedero(2003, "Cafe", 4.50, 3),
                new NoPerecedero(2004, "Aceite", 6.80, 2),
                new NoPerecedero(2005, "Galletas", 2.40, 9)
        };
        System.arraycopy(productos, 0, almacen, 0, productos.length);
    }

    /**
     * Solicita un codigo, anade el producto al carrito si hay stock y actualiza
     * el almacen.
     *
     * @param almacen productos disponibles
     * @param cliente cliente comprador
     * @param scanner lector de consola
     */
    public static void añadirProductoCarrito(Producto[] almacen, Cliente cliente, Scanner scanner) {
        if (almacen == null || cliente == null || scanner == null) {
            throw new IllegalArgumentException("Almacen, cliente y scanner son obligatorios.");
        }
        int codigo = leerEntero(scanner, "Introduce el codigo del producto: ");
        Producto producto = buscarPorCodigo(almacen, codigo);

        if (producto == null) {
            System.out.println("¡¡No existe el producto en el almacen!!");
            return;
        }
        if (producto.getStock() <= 0) {
            System.out.println("¡¡No hay stock suficiente!!");
            producto.incrementarStock(STOCK_REPOSICION);
            return;
        }

        producto.actualizarPrecio();
        cliente.getCarrito().add(producto.copiaParaCarrito());
        producto.disminuirStock();
        System.out.println("¡¡Producto añadido al carrito!!");
    }

    /**
     * Alias ASCII para entornos que no trabajan con identificadores Unicode.
     *
     * @param almacen productos disponibles
     * @param cliente cliente comprador
     * @param scanner lector de consola
     */
    public static void anadirProductoCarrito(Producto[] almacen, Cliente cliente, Scanner scanner) {
        añadirProductoCarrito(almacen, cliente, scanner);
    }

    /**
     * Muestra el almacen ordenado por codigo o en su orden actual.
     *
     * @param almacen productos del almacen
     * @param ordenar indica si se ordena por codigo ascendente
     */
    public static void mostrarProductos(Producto[] almacen, boolean ordenar) {
        if (almacen == null) {
            throw new IllegalArgumentException("El almacen es obligatorio.");
        }
        System.out.println(ordenar
                ? "\n--- Lista de productos ordenada por codigo ---"
                : "\n--- Lista de productos actualizada ---");
        Producto[] productos = Arrays.copyOf(almacen, almacen.length);
        if (ordenar) {
            Arrays.sort(productos, Comparator.comparingInt(Producto::getCodProducto));
        }
        Arrays.stream(productos).forEach(Producto::mostrarInfo);
    }

    private static Producto buscarPorCodigo(Producto[] almacen, int codigo) {
        return Arrays.stream(almacen)
                .filter(producto -> producto != null && producto.getCodProducto() == codigo)
                .findFirst()
                .orElse(null);
    }

    private static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("No se recibio ningun codigo de producto.");
            }
            if (scanner.hasNextInt()) {
                int valor = scanner.nextInt();
                scanner.nextLine();
                return valor;
            }
            System.out.println("Introduce un codigo numerico valido.");
            scanner.nextLine();
        }
    }
}
