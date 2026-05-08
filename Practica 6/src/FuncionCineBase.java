public abstract class FuncionCineBase {

    protected String titulo;
    protected int duracion;
    protected double PrecioBase;
    protected String clasificacion;

    public FuncionCineBase(String titulo, int duracion, double PrecioBase, String clasificacion) {
        SetTitulo(titulo);
        SetDuracion(duracion);
        SetPrecioBase(PrecioBase);
        SetClasificacion(clasificacion);
    }

    public String GetTitulo() {
        return titulo;
    }

    public void SetTitulo(String titulo) {
        if (titulo != null && titulo.trim().length() >= 2) {
            this.titulo = titulo.trim();
        } else {
            throw new IllegalArgumentException("Error: El titulo debe tener al menos 2 caracteres.");
        }
    }

    public int GetDuracion() {
        return duracion;
    }

    public void SetDuracion(int duracion) {
        if (duracion >= 60 && duracion <= 300) {
            this.duracion = duracion;
        } else {
            throw new IllegalArgumentException("Error: La duracion debe estar entre 60 y 300 minutos.");
        }
    }

    public double GetPrecioBase() {
        return PrecioBase;
    }

    public void SetPrecioBase(double PrecioBase) {
        if (PrecioBase > 0) {
            this.PrecioBase = PrecioBase;
        } else {
            throw new IllegalArgumentException("Error: El precio base debe ser mayor a 0.");
        }
    }

    public String GetClasificacion() {
        return clasificacion;
    }

    public void SetClasificacion(String clasificacion) {
        if (clasificacion != null &&
            (clasificacion.equals("A") || clasificacion.equals("B") || clasificacion.equals("C"))) {
            this.clasificacion = clasificacion;
        } else {
            throw new IllegalArgumentException("Error: La clasificacion solo puede ser A, B o C.");
        }
    }

    public abstract String GetTipoFuncion();
    public abstract String ObtenerFormato();

    public String GenerarFicha() {
        return String.format(
            "| %-20s | %-20s | %-6d min | %-15s | %-20s | %12.2f |",
            GetTipoFuncion(), titulo, duracion, clasificacion, ObtenerFormato(), PrecioBase);
    }

    public void MostrarFicha() {
        System.out.println(GenerarFicha());
    }

    @Override
    public String toString() {
        return GenerarFicha();
    }
}
