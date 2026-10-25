package es.accenture.appcomercio;

/**
 * Datos personales de una persona.
 */
public class Persona {

    private String nomCompleto;
    private String dni;
    private String direccion;

    /**
     * Crea una persona.
     *
     * @param nomCompleto nombre completo
     * @param dni documento nacional de identidad
     * @param direccion direccion postal
     */
    public Persona(String nomCompleto, String dni, String direccion) {
        validarTexto(nomCompleto, "El nombre completo");
        validarTexto(dni, "El DNI");
        validarTexto(direccion, "La direccion");
        this.nomCompleto = nomCompleto;
        this.dni = dni;
        this.direccion = direccion;
    }

    /**
     * Devuelve el nombre completo.
     *
     * @return nombre completo
     */
    public String getNomCompleto() {
        return nomCompleto;
    }

    /**
     * Actualiza el nombre completo.
     *
     * @param nomCompleto nombre completo
     */
    public void setNomCompleto(String nomCompleto) {
        validarTexto(nomCompleto, "El nombre completo");
        this.nomCompleto = nomCompleto;
    }

    /**
     * Devuelve el documento de identidad.
     *
     * @return DNI
     */
    public String getDni() {
        return dni;
    }

    /**
     * Actualiza el documento de identidad.
     *
     * @param dni DNI
     */
    public void setDni(String dni) {
        validarTexto(dni, "El DNI");
        this.dni = dni;
    }

    /**
     * Devuelve la direccion postal.
     *
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Actualiza la direccion postal.
     *
     * @param direccion direccion
     */
    public void setDireccion(String direccion) {
        validarTexto(direccion, "La direccion");
        this.direccion = direccion;
    }

    private static void validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacio.");
        }
    }
}
