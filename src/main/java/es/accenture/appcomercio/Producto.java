package es.accenture.appcomercio;

/**
 * Producto base del almacen.
 */
public class Producto implements GestionProductos {

    private int codProducto;
    private String nombre;
    private double precio;
    private int stock;

    /**
     * Crea un producto.
     *
     * @param codProducto codigo identificador
     * @param nombre nombre del producto
     * @param precio precio inicial
     * @param stock unidades disponibles
     */
    public Producto(int codProducto, String nombre, double precio, int stock) {
        validarNombre(nombre);
        validarPrecio(precio);
        validarStock(stock);
        this.codProducto = codProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    /**
     * Devuelve el codigo identificador del producto.
     *
     * @return codigo del producto
     */
    public int getCodProducto() {
        return codProducto;
    }

    /**
     * Actualiza el codigo identificador del producto.
     *
     * @param codProducto codigo del producto
     */
    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    /**
     * Devuelve el nombre del producto.
     *
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Actualiza el nombre del producto.
     *
     * @param nombre nombre del producto
     */
    public void setNombre(String nombre) {
        validarNombre(nombre);
        this.nombre = nombre;
    }

    /**
     * Devuelve el precio actual.
     *
     * @return precio actual
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Actualiza el precio del producto.
     *
     * @param precio precio actual
     */
    public void setPrecio(double precio) {
        validarPrecio(precio);
        this.precio = precio;
    }

    /**
     * Devuelve las unidades disponibles.
     *
     * @return stock actual
     */
    public int getStock() {
        return stock;
    }

    /**
     * Actualiza las unidades disponibles.
     *
     * @param stock stock actual
     */
    public void setStock(int stock) {
        validarStock(stock);
        this.stock = stock;
    }

    /**
     * Muestra la informacion basica del producto.
     */
    public void mostrarInfo() {
        System.out.printf("Codigo: %d | Nombre: %s | Precio: %.2f € | Stock: %d%n",
                codProducto, nombre, precio, stock);
    }

    /** {@inheritDoc} */
    @Override
    public void incrementarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a incrementar debe ser positiva.");
        }
        if (stock > Integer.MAX_VALUE - cantidad) {
            throw new IllegalArgumentException("El stock resultante es demasiado grande.");
        }
        stock += cantidad;
    }

    /** {@inheritDoc} */
    @Override
    public void disminuirStock() {
        if (stock > 0) {
            stock--;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void actualizarPrecio() {
        precio *= 0.95;
    }

    /**
     * Crea una instantanea del producto para una linea del carrito.
     *
     * @return copia independiente con el estado actual
     */
    public Producto copiaParaCarrito() {
        return new Producto(codProducto, nombre, precio, 1);
    }

    private static void validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
    }

    private static void validarPrecio(double precio) {
        if (!Double.isFinite(precio) || precio < 0) {
            throw new IllegalArgumentException("El precio debe ser un numero no negativo.");
        }
    }

    private static void validarStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
    }
}
