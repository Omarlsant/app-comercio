package es.accenture.appcomercio;

/**
 * Producto no perecedero con un descuento fijo.
 */
public class NoPerecedero extends Producto {

    /**
     * Crea un producto no perecedero.
     *
     * @param codProducto codigo identificador
     * @param nombre nombre del producto
     * @param precio precio inicial
     * @param stock unidades disponibles
     */
    public NoPerecedero(int codProducto, String nombre, double precio, int stock) {
        super(codProducto, nombre, precio, stock);
    }

    /** {@inheritDoc} */
    @Override
    public void actualizarPrecio() {
        setPrecio(getPrecio() * 0.90);
    }

    /** {@inheritDoc} */
    @Override
    public Producto copiaParaCarrito() {
        return new NoPerecedero(getCodProducto(), getNombre(), getPrecio(), 1);
    }
}
