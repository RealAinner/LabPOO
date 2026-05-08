public class Funcion3D extends FuncionCineBase implements Cobro, Promociones {

    private boolean GafasIncluidas;
    private double recargo3D;

    public Funcion3D(String titulo, int duracion, double PrecioBase, String clasificacion, boolean GafasIncluidas) {
        super(titulo, duracion, PrecioBase, clasificacion);
        SetGafasIncluidas(GafasIncluidas);
        SetRecargo3D(35);
    }

    public boolean GetGafasIncluidas() {
        return GafasIncluidas;
    }

    public void SetGafasIncluidas(boolean GafasIncluidas) {
        this.GafasIncluidas = GafasIncluidas;
    }

    public double GetRecargo3D() {
        return recargo3D;
    }

    public void SetRecargo3D(double recargo3D) {
        if (recargo3D >= 0) {
            this.recargo3D = recargo3D;
        } else {
            throw new IllegalArgumentException("Error: El recargo 3D no puede ser negativo.");
        }
    }

    @Override
    public String GetTipoFuncion() {
        return "Funcion 3D";
    }

    @Override
    public String ObtenerFormato() {
        return GafasIncluidas ? "3D con gafas" : "3D sin gafas";
    }

    @Override
    public double CalcularTotal() {
        return GetPrecioBase() + recargo3D;
    }

    @Override
    public double CalcularTotal(int CantidadBoletos) {
        if (CantidadBoletos <= 0) {
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }

        return (GetPrecioBase() + recargo3D) * CantidadBoletos;
    }

    @Override
    public double AplicarDescuento(double PorcentajeDescuento, double Monto) {
        if (PorcentajeDescuento < 0 || PorcentajeDescuento > 100) {
            throw new IllegalArgumentException("Error: El porcentaje de descuento debe estar entre 0 y 100.");
        }

        if (Monto < 0) {
            throw new IllegalArgumentException("Error: El monto no puede ser negativo.");
        }

        return Monto * (1 - (PorcentajeDescuento / 100));
    }

    @Override
    public String ObtenerPromocion() {
        return "Promocion 3D: 10% en funciones seleccionadas.";
    }
}
