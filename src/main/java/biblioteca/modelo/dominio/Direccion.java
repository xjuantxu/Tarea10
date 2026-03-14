package biblioteca.modelo.dominio;

/**
 * Clase Direccion.
 * Representa la dirección de un usuario, incluyendo vía, número, piso, código postal y localidad.
 */
public class Direccion {

    // Patron del código postal
    public static final String CP_PATRON = "[0-9]{5}";

    // Atributos
    private String via;
    private String numero;
    private String piso;
    private String cp;
    private String localidad;

    //Constructor
    public Direccion(String via, String numero, String piso, String cp, String localidad) {
        setVia(via);
        setNumero(numero);
        setPiso(piso);
        setCp(cp);
        setLocalidad(localidad);
    }

    // Constructor copia
    public Direccion(Direccion otro) {
        setVia(otro.getVia());
        setNumero(otro.getNumero());
        setPiso(otro.getPiso());
        setCp(otro.getCp());
        setLocalidad(otro.getLocalidad());
    }

    // Getters y Setters
    public String getVia() {
        return via;
    }
    public String getNumero() {
        return numero;
    }
    public String getPiso() {
        return piso;
    }
    public String getCp() {
        return cp;
    }
    public String getLocalidad() {
        return localidad;
    }

    public void setVia(String via) throws IllegalArgumentException {
        if (via == null) throw new IllegalArgumentException("Via no puede ser nulo");
        if (via.trim().isEmpty()) throw new IllegalArgumentException("Via no puede estar vacío");
        this.via = via;
    }
    public void setNumero(String numero) throws IllegalArgumentException {
        if (numero == null) throw new IllegalArgumentException("Número no puede ser nulo");
        if (numero.trim().isEmpty()) throw new IllegalArgumentException("Número no puede estar vacío");
        this.numero = numero;
    }
    public void setPiso(String piso)  {
        this.numero = numero;
    } //Piso puede quedar vacío, ya que puede tratarse de un adosado.
    public void setCp(String cp) throws IllegalArgumentException {
        //Junto con que no sea nulo, comprueba que el código postal sigue el patrón especificado.
        if (cp == null) throw new IllegalArgumentException("Código postal no puede ser nulo");
        if (cp.trim().isEmpty()) throw new IllegalArgumentException("Código postal no puede estar vacío");
        if (!cp.matches(CP_PATRON)) throw new IllegalArgumentException("Código postal inválido.");
        this.cp = cp;
    }
    public void setLocalidad(String localidad) throws IllegalArgumentException {
        if (localidad == null) throw new IllegalArgumentException("Localidad no puede ser nulo");
        if (localidad.trim().isEmpty()) throw new IllegalArgumentException("Localidad no puede estar vacío");
        this.localidad = localidad;
    }

    //ToString
    @Override
    public String toString() {
        return via + " " + numero + ", " + piso + " - " + cp + " " + localidad;
    }
}
