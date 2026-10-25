package es.accenture.appcomercio;

/**
 * Define las operaciones comunes de gestion de un producto.
 */
public interface GestionProductos {

    /**
     * Incrementa el stock del producto.
     *
     * @param cantidad unidades que se anaden
     */
    void incrementarStock(int cantidad);

    /**
     * Disminuye el stock del producto en una unidad.
     */
    void disminuirStock();

    /**
     * Actualiza el precio aplicando el descuento del tipo de producto.
     */
    void actualizarPrecio();
}
