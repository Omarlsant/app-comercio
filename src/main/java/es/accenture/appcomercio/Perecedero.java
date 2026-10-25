package es.accenture.appcomercio;

/**
 * Producto cuya rebaja depende de los dias que faltan para caducar.
 */
public class Perecedero extends Producto {

    private int diasCaducar;

    /**
     * Crea un producto perecedero.
     *
     * @param codProducto codigo identificador
     * @param nombre nombre del producto
     * @param precio precio inicial
     * @param stock unidades disponibles
     * @param diasCaducar dias restantes hasta la caducidad
     */
    public Perecedero(int codProducto, String nombre, double precio, int stock, int diasCaducar) {
        super(codProducto, nombre, precio, stock);
        validarDiasCaducar(diasCaducar);
        this.diasCaducar = diasCaducar;
    }

    /**
     * Devuelve los dias restantes hasta la caducidad.
     *
     * @return dias restantes hasta caducar
     */
    public int getDiasCaducar() {
        return diasCaducar;
    }

    /**
     * Actualiza los dias restantes hasta la caducidad.
     *
     * @param diasCaducar dias restantes hasta caducar
     */
    public void setDiasCaducar(int diasCaducar) {
        validarDiasCaducar(diasCaducar);
        this.diasCaducar = diasCaducar;
    }

    /** {@inheritDoc} */
    @Override
    public void mostrarInfo() {
        System.out.printf("Codigo: %d | Nombre: %s | Precio: %.2f € | Stock: %d | Dias a caducar: %d%n",
                getCodProducto(), getNombre(), getPrecio(), getStock(), diasCaducar);
    }

    /** {@inheritDoc} */
    @Override
    public void actualizarPrecio() {
        setPrecio(getPrecio() * (diasCaducar <= 2 ? 0.70 : 0.90));
    }

    /** {@inheritDoc} */
    @Override
    public Producto copiaParaCarrito() {
        return new Perecedero(getCodProducto(), getNombre(), getPrecio(), 1, diasCaducar);
    }

    private static void validarDiasCaducar(int diasCaducar) {
        if (diasCaducar < 0) {
            throw new IllegalArgumentException("Los dias para caducar no pueden ser negativos.");
        }
    }
}
