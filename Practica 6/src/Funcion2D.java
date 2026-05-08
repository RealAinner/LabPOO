public class Funcion2D extends FuncionCineBase implements Cobro {

    private boolean subtitulada;

    public Funcion2D(String titulo, int duracion, double precioBase, String clasificacion, boolean subtitulada) {
        super(titulo, duracion, precioBase, clasificacion);
        SetSubtitulada(subtitulada);
    }

    public boolean GetSubtitulada() {
        return subtitulada;
    }

    public void SetSubtitulada(boolean subtitulada) {
        this.subtitulada = subtitulada;
    }

    @Override
    public String GetTipoFuncion() {
        return "Funcion 2D";
    }

    @Override
    public String ObtenerFormato() {
        return subtitulada ? "Subtitulada" : "Doblada";
    }

    @Override
    public double CalcularTotal() {
        return GetPrecioBase();
    }

    @Override
    public double CalcularTotal(int CantidadBoletos) {
        if (CantidadBoletos <= 0) {
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }

        return GetPrecioBase() * CantidadBoletos;
    }
}
