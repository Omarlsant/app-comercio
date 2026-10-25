package es.accenture.appcomercio;

/**
 * Define las operaciones disponibles para un cliente.
 */
public interface GestionClientes {

    /** Muestra los datos del cliente. */
    void infoCliente();

    /** Muestra el carrito y su total. */
    void mostrarCarrito();

    /** Simula el envio del pedido. */
    void enviarPedido();
}
