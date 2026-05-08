public interface Reservaciones {
    boolean Reservar(int CantidadBoletos);
    void CancelarReserva();
    int GetBoletosReservados();
}
