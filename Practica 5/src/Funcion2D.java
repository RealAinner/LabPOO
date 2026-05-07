class Funcion2D extends FuncionCine{
    private boolean subtitulada;

    public Funcion2D(String titulo, int duracion, double precio, boolean subtitulada){
        super(titulo, duracion, precio);
        SetSubtitulada(subtitulada);
    }

    public boolean GetSubtitulada(){
        return subtitulada;
    }

    public void SetSubtitulada(boolean subtitulada){
        this.subtitulada = subtitulada;
    }

    @Override
    public String ObtenerTipo(){
        return "Funcion 2D";
    }

    @Override
    public String ObtenerFormato(){
        return subtitulada ? "Subtitulada" : "Doblada";
    }

    @Override
    public double CalcularPrecio(){
        return GetPrecio();
    }

    @Override
    public double CalcularPrecio(int boletos){
        if(boletos <= 0){
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }
        return GetPrecio() * boletos;
    }

    @Override
    public double CalcularPrecio(int boletos, boolean combo) {
        if(boletos <= 0){
            throw new IllegalArgumentException("Error: La cantidad de boletos debe ser mayor a 0.");
        }
        double total = GetPrecio() * boletos;
        if(combo){
            total = total + (45 * boletos);
        }
        return total;
    }

    public double CalcularPrecio(int boletos, boolean combo, double descuento) {
        if(descuento < 0 || descuento > 100){
            throw new IllegalArgumentException("Error: El descuento debe estar entre 0 y 100.");
        }
        double total = CalcularPrecio(boletos, combo);
        return total * (1 - (descuento / 100));
    }
}