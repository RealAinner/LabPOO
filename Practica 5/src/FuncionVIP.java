class FuncionVIP extends FuncionCine{
    private boolean snack;

    public FuncionVIP(String titulo, int duracion, double precio, boolean snack){
        super(titulo, duracion, precio);
        SetSnack(snack);
    }

    public boolean GetSnack(){
        return snack;
    }

    public void SetSnack(boolean snack){
        this.snack = snack;
    }

    @Override
    public String ObtenerTipo(){
        return "Funcion VIP";
    }

    @Override
    public String ObtenerFormato(){
        return snack ? "VIP con snack" : "VIP sin snack";
    }

    @Override
    public double CalcularPrecio(){
        return GetPrecio() + 90;
    }

    @Override
    public double CalcularPrecio(int boletos){
        if(boletos <= 0){
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }
        return (GetPrecio() + 90) * boletos;
    }

    @Override
    public double CalcularPrecio(int boletos, boolean combo){
        if(boletos <= 0){
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }
        double total = (GetPrecio() + 90) * boletos;
        if(combo){
            total = total + (70 * boletos);
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

