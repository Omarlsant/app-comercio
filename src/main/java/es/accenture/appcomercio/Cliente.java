package es.accenture.appcomercio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Cliente que mantiene sus datos personales y su carrito.
 */
public class Cliente implements GestionClientes {

    private Persona persona;
    private final List<Producto> carrito;

    /**
     * Crea un cliente con un carrito vacio.
     *
     * @param persona datos personales
     */
    public Cliente(Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException("El cliente debe tener datos personales.");
        }
        this.persona = persona;
        this.carrito = new ArrayList<>();
    }

    /**
     * Devuelve los datos personales del cliente.
     *
     * @return datos personales
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Actualiza los datos personales del cliente.
     *
     * @param persona datos personales
     */
    public void setPersona(Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException("El cliente debe tener datos personales.");
        }
        this.persona = persona;
    }

    /**
     * Devuelve la lista de productos del carrito.
     *
     * @return carrito del cliente
     */
    public List<Producto> getCarrito() {
        return carrito;
    }

    /** {@inheritDoc} */
    @Override
    public void infoCliente() {
        System.out.printf("Cliente: %s | DNI: %s | Direccion: %s%n",
                persona.getNomCompleto(), persona.getDni(), persona.getDireccion());
    }

    /** {@inheritDoc} */
    @Override
    public void mostrarCarrito() {
        System.out.printf("%n--- Contenido del carrito de %s ---%n", persona.getNomCompleto());
        carrito.stream()
                .sorted(Comparator.comparingDouble(Producto::getPrecio).reversed())
                .forEach(Producto::mostrarInfo);
        System.out.printf("Total del carrito: %.2f €%n", calcularTotal());
    }

    /** {@inheritDoc} */
    @Override
    public void enviarPedido() {
        System.out.printf("Enviando pedido a %s, %s%n",
                persona.getNomCompleto(), persona.getDireccion());
    }

    /**
     * Calcula el precio total de los productos del carrito.
     *
     * @return total del pedido
     */
    public double calcularTotal() {
        return carrito.stream().mapToDouble(Producto::getPrecio).sum();
    }
}
