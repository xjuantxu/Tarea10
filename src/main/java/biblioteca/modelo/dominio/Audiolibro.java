package biblioteca.modelo.dominio;

import java.time.Duration;

public class Audiolibro extends Libro {

    private Duration duracion;
    private String formato;

    //Constructor
    public Audiolibro(String isbn,
                      String titulo,
                      int anio,
                      Categoria categoria,
                      Duration duracion,
                      String formato) {

        super(isbn, titulo, anio, categoria);
        setDuracion(duracion);
        setFormato(formato);
    }

    public Audiolibro(Audiolibro otro) {
        super(otro);
        setDuracion(otro.getDuracion());
        setFormato(otro.getFormato());
    }
    public Duration getDuracion() {
        return duracion;
    }
    public void setDuracion(Duration duracion) throws IllegalArgumentException {
        if (duracion == null || duracion.isZero() || duracion.isNegative())
            throw new IllegalArgumentException("La duración es inválida.");
        this.duracion = duracion;
    }

    public String getFormato() {
        return formato;
    }
    public void setFormato(String formato) throws IllegalArgumentException {
        if (formato == null) throw new IllegalArgumentException("Formato no puede ser nulo");
        if (formato.trim().isEmpty()) throw new IllegalArgumentException("Formato no puede estar vacío");
        this.formato = formato;
    }

    private String formatearDuracion() {
        long horas = duracion.toHours();
        long minutos = duracion.toMinutes() % 60;
        long segundos = duracion.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    @Override
    public String toString() {
        return "Audiolibro{" +
                "isbn='" + getISBN() + '\'' +
                ", titulo='" + getTitulo() + '\'' +
                ", anio=" + getAnio() +
                ", categoria=" + getCategoria() +
                ", duracion=" + formatearDuracion() +
                ", formato='" + formato + '\'' +
                '}';
    }
}