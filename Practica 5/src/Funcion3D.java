class Funcion3D extends FuncionCine {
    private boolean gafas;

    public Funcion3D(String titulo, int duracion, double precio, boolean gafas){
        super(titulo, duracion, precio);
        SetGafas(gafas);
    }

    public boolean GetGafas(){
        return gafas;
    }

    public void SetGafas(boolean gafas){
        this.gafas = gafas;
    }

    @Override
    public String ObtenerTipo(){
        return "Funcion 3D";
    }

    @Override
    public String ObtenerFormato(){
        return gafas ? "3D con gafas" : "3D sin gafas";
    }

    @Override
    public double CalcularPrecio(){
        return GetPrecio() + 30;
    }

    @Override
    public double CalcularPrecio(int boletos){
        if(boletos <= 0){
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }
        return (GetPrecio() + 30) * boletos;
    }

    @Override
    public double CalcularPrecio(int boletos, boolean combo){
        if(boletos <= 0){
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }
        double total = (GetPrecio() + 30) * boletos;
        if(combo){
            total = total + (55 * boletos);
        }
        return total;
    }

    public double CalcularPrecio(int boletos, boolean combo, double descuento){
        if(descuento < 0 || descuento > 100){
            throw new IllegalArgumentException("Error: El descuento debe estar entre 0 y 100.");
        }
        double total = CalcularPrecio(boletos, combo);
        return total * (1 - (descuento / 100));
    }
}
