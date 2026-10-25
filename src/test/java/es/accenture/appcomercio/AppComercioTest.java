package es.accenture.appcomercio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Pruebas ejecutables sin frameworks externos para los comportamientos exigidos.
 */
public final class AppComercioTest {

    private AppComercioTest() {
    }

    /**
     * Ejecuta todas las comprobaciones y falla con {@link AssertionError} si
     * alguna no se cumple.
     *
     * @param args argumentos ignorados
     */
    public static void main(String[] args) {
        pruebaDescuentos();
        pruebaStock();
        pruebaReposicionSinStock();
        pruebaEntradaNoNumerica();
        pruebaCarritoOrdenado();
        pruebaCompraRepetidaConservaPrecios();
        pruebaValidacionDeDatos();
        pruebaAlmacenPorArray();
        System.out.println("Todas las pruebas de AppComercio han pasado.");
    }

    private static void pruebaDescuentos() {
        Producto producto = new Producto(1, "Producto", 100.0, 1);
        producto.actualizarPrecio();
        comprobarCasiIgual(95.0, producto.getPrecio(), "Descuento del producto base");

        Perecedero urgente = new Perecedero(2, "Urgente", 100.0, 1, 2);
        urgente.actualizarPrecio();
        comprobarCasiIgual(70.0, urgente.getPrecio(), "Descuento perecedero <= 2 dias");

        Perecedero normal = new Perecedero(3, "Normal", 100.0, 1, 3);
        normal.actualizarPrecio();
        comprobarCasiIgual(90.0, normal.getPrecio(), "Descuento perecedero > 2 dias");

        NoPerecedero noPerecedero = new NoPerecedero(4, "Duradero", 100.0, 1);
        noPerecedero.actualizarPrecio();
        comprobarCasiIgual(90.0, noPerecedero.getPrecio(), "Descuento no perecedero");
    }

    private static void pruebaStock() {
        Producto producto = new Producto(1, "Producto", 10.0, 1);
        producto.disminuirStock();
        comprobar(producto.getStock() == 0, "Disminuir stock debe restar una unidad");
        producto.disminuirStock();
        comprobar(producto.getStock() == 0, "El stock no puede ser negativo");
        producto.incrementarStock(5);
        comprobar(producto.getStock() == 5, "Incrementar stock debe sumar unidades");
    }

    private static void pruebaReposicionSinStock() {
        Producto sinStock = new Producto(99, "Agotado", 10.0, 0);
        Cliente cliente = new Cliente(new Persona("Test", "X", "Y"));

        ejecutarConEntrada("99\n", () -> {
            try (Scanner scanner = new Scanner(System.in)) {
                ComercioUtils.añadirProductoCarrito(new Producto[]{sinStock}, cliente, scanner);
            }
        });

        comprobar(sinStock.getStock() == 5, "La falta de stock debe reponer cinco unidades");
        comprobar(cliente.getCarrito().isEmpty(), "No se debe anadir un producto sin stock");
    }

    private static void pruebaEntradaNoNumerica() {
        Producto producto = new Producto(99, "Valido", 10.0, 1);
        Cliente cliente = new Cliente(new Persona("Test", "X", "Y"));
        String salida = capturarEntrada("no-es-un-numero\n99\n", () -> {
            try (Scanner scanner = new Scanner(System.in)) {
                ComercioUtils.añadirProductoCarrito(new Producto[]{producto}, cliente, scanner);
            }
        });

        comprobar(salida.contains("codigo numerico valido"),
                "La entrada no numerica debe mostrar un mensaje de validacion");
        comprobar(cliente.getCarrito().size() == 1,
                "La entrada valida posterior debe anadir el producto");
    }

    private static void pruebaCarritoOrdenado() {
        Cliente cliente = new Cliente(new Persona("Test", "X", "Y"));
        cliente.getCarrito().add(new Producto(1, "Barato", 2.0, 1));
        cliente.getCarrito().add(new Producto(2, "Caro", 8.0, 1));

        String salida = capturarSalida(cliente::mostrarCarrito);
        comprobar(salida.indexOf("Caro") < salida.indexOf("Barato"),
                "El carrito debe mostrarse por precio descendente");
        comprobarCasiIgual(10.0, cliente.calcularTotal(), "Total del carrito");
    }

    private static void pruebaCompraRepetidaConservaPrecios() {
        Producto producto = new Producto(99, "Repetido", 100.0, 2);
        Cliente cliente = new Cliente(new Persona("Test", "X", "Y"));

        ejecutarConEntrada("99\n", () -> {
            try (Scanner scanner = new Scanner(System.in)) {
                ComercioUtils.añadirProductoCarrito(new Producto[]{producto}, cliente, scanner);
            }
        });
        ejecutarConEntrada("99\n", () -> {
            try (Scanner scanner = new Scanner(System.in)) {
                ComercioUtils.añadirProductoCarrito(new Producto[]{producto}, cliente, scanner);
            }
        });

        comprobar(cliente.getCarrito().size() == 2,
                "Las compras repetidas deben crear dos lineas del carrito");
        comprobarCasiIgual(185.25, cliente.calcularTotal(),
                "Las compras repetidas no deben aplicar el descuento dos veces a la primera linea");
        comprobar(producto.getStock() == 0, "Cada compra debe descontar una unidad del almacen");
    }

    private static void pruebaValidacionDeDatos() {
        comprobarFalla(() -> new Producto(1, "", 1.0, 1),
                "El producto debe rechazar nombres vacios");
        comprobarFalla(() -> new Producto(1, "Valido", -1.0, 1),
                "El producto debe rechazar precios negativos");
        comprobarFalla(() -> new Perecedero(1, "Valido", 1.0, 1, -1),
                "El perecedero debe rechazar dias negativos");
        comprobarFalla(() -> new Persona("Nombre", "", "Direccion"),
                "La persona debe rechazar DNIs vacios");
    }

    private static void pruebaAlmacenPorArray() {
        Producto[] almacen = new Producto[10];
        ComercioUtils.crearAlmacen(almacen);
        comprobar(almacen[0] instanceof Perecedero,
                "crearAlmacen debe rellenar el array recibido");
        comprobar(almacen[9].getCodProducto() == 2005,
                "crearAlmacen debe cargar todos los productos");
        comprobarFalla(() -> ComercioUtils.crearAlmacen(new Producto[9]),
                "crearAlmacen debe validar el tamano del array");
    }

    private static String capturarSalida(Runnable accion) {
        PrintStream salidaOriginal = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            accion.run();
            return buffer.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(salidaOriginal);
        }
    }

    private static void ejecutarConEntrada(String entrada, Runnable accion) {
        capturarEntrada(entrada, accion);
    }

    private static String capturarEntrada(String entrada, Runnable accion) {
        java.io.InputStream entradaOriginal = System.in;
        PrintStream salidaOriginal = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            accion.run();
            return buffer.toString(StandardCharsets.UTF_8);
        } finally {
            System.setIn(entradaOriginal);
            System.setOut(salidaOriginal);
        }
    }

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static void comprobarCasiIgual(double esperado, double actual, String mensaje) {
        comprobar(Math.abs(esperado - actual) < 0.000001,
                mensaje + ": esperado " + esperado + ", obtenido " + actual);
    }

    private static void comprobarFalla(Runnable accion, String mensaje) {
        try {
            accion.run();
            throw new AssertionError(mensaje);
        } catch (IllegalArgumentException esperado) {
            // La validacion ha rechazado correctamente el dato.
        }
    }
}
