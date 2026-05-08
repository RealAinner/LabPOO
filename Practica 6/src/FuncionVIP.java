public class FuncionVIP extends FuncionCineBase implements Cobro, Promociones, Reservaciones {

    private int capacidad;
    private int boletosReservados;
    private double recargoVIP;

    public FuncionVIP(String titulo, int duracion, double PrecioBase, String clasificacion, int capacidad) {
        super(titulo, duracion, PrecioBase, clasificacion);
        SetCapacidad(capacidad);
        this.boletosReservados = 0;
        this.recargoVIP = 90;
    }

    public int GetCapacidad() {
        return capacidad;
    }

    public void SetCapacidad(int capacidad) {
        if (capacidad > 0) {
            this.capacidad = capacidad;
        } else {
            throw new IllegalArgumentException("Error: La capacidad debe ser mayor a 0.");
        }
    }

    public double GetRecargoVIP() {
        return recargoVIP;
    }

    public void SetRecargoVIP(double recargoVIP) {
        if (recargoVIP >= 0) {
            this.recargoVIP = recargoVIP;
        } else {
            throw new IllegalArgumentException("Error: El recargo VIP no puede ser negativo.");
        }
    }

    @Override
    public String GetTipoFuncion() {
        return "Funcion VIP";
    }

    @Override
    public String ObtenerFormato() {
        return "Sala VIP";
    }

    @Override
    public double CalcularTotal() {
        return GetPrecioBase() + recargoVIP;
    }

    @Override
    public double CalcularTotal(int CantidadBoletos) {
        if (CantidadBoletos <= 0) {
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }

        return (GetPrecioBase() + recargoVIP) * CantidadBoletos;
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
        return "Promocion VIP: upgrade de bebida en funciones de fin de semana.";
    }

    @Override
    public boolean Reservar(int CantidadBoletos) {
        if (CantidadBoletos <= 0) {
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }

        if (boletosReservados + CantidadBoletos <= capacidad) {
            boletosReservados = boletosReservados + CantidadBoletos;
            return true;
        }

        return false;
    }

    @Override
    public void CancelarReserva() {
        boletosReservados = 0;
    }

    @Override
    public int GetBoletosReservados() {
        return boletosReservados;
    }
}
