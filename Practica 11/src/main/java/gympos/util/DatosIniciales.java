package gympos.util;

import gympos.modelo.cliente.Cliente;
import gympos.modelo.membresia.Membresia;
import gympos.modelo.membresia.TipoMembresia;
import gympos.modelo.pago.Pago;
import gympos.modelo.equipo.Equipo;
import gympos.modelo.clase.ClaseGrupal;
import gympos.modelo.acceso.RegistroAcceso;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatosIniciales {

    public static List<Cliente> GenerarClientes(){
        List<Cliente> lista = new ArrayList<>();
        String[][] datos = {
            {"Carlos","Herrera","carlos.herrera@mail.com","8112345678"},
            {"Ana","Martinez","ana.martinez@mail.com","8119876543"},
            {"Luis","Gonzalez","luis.gonzalez@mail.com","8114561234"},
            {"Maria","Lopez","maria.lopez@mail.com","8117894561"},
            {"Pedro","Ramirez","pedro.ramirez@mail.com","8113216549"},
            {"Sofia","Torres","sofia.torres@mail.com","8116549873"},
            {"Diego","Flores","diego.flores@mail.com","8118763214"},
            {"Laura","Morales","laura.morales@mail.com","8112143657"},
            {"Jorge","Castillo","jorge.castillo@mail.com","8115678901"},
            {"Valeria","Reyes","valeria.reyes@mail.com","8119012345"},
            {"Miguel","Vargas","miguel.vargas@mail.com","8113456789"},
            {"Paola","Jimenez","paola.jimenez@mail.com","8116789012"},
            {"Ricardo","Cruz","ricardo.cruz@mail.com","8110123456"},
            {"Fernanda","Medina","fernanda.medina@mail.com","8113450987"},
            {"Hector","Gutierrez","hector.gutierrez@mail.com","8117890123"},
            {"Monica","Diaz","monica.diaz@mail.com","8114321098"},
            {"Roberto","Soto","roberto.soto@mail.com","8118901234"},
            {"Claudia","Mendoza","claudia.mendoza@mail.com","8112345090"},
            {"Andres","Ramos","andres.ramos@mail.com","8115432109"},
            {"Patricia","Ibarra","patricia.ibarra@mail.com","8119012346"}
        };
        for(int i = 0; i < datos.length; i++){
            Cliente c = new Cliente(i + 1, datos[i][0], datos[i][1], datos[i][2], datos[i][3]);
            c.SetPuntos((i + 1) * 15);
            lista.add(c);
        }
        return lista;
    }

    public static List<Membresia> GenerarMembresias(){
        List<Membresia> lista = new ArrayList<>();
        TipoMembresia[] tipos = TipoMembresia.values();
        for(int i = 1; i <= 20; i++){
            Membresia m = new Membresia(i, i, tipos[i % tipos.length]);
            m.SetRenovacionAutomatica(i % 3 == 0);
            lista.add(m);
        }
        return lista;
    }

    public static List<Pago> GenerarPagos(){
        List<Pago> lista = new ArrayList<>();
        Pago.MetodoPago[] metodos = Pago.MetodoPago.values();
        for(int i = 1; i <= 20; i++){
            Pago p = new Pago(i, i, i, 299.0 + (i * 50), metodos[i % metodos.length]);
            p.SetEstado(Pago.EstadoPago.COMPLETADO);
            lista.add(p);
        }
        return lista;
    }

    public static List<Equipo> GenerarEquipos(){
        List<Equipo> lista = new ArrayList<>();
        String[][] eq = {
            {"Caminadora","Cardio","8"},
            {"Bicicleta estatica","Cardio","6"},
            {"Eliptica","Cardio","4"},
            {"Press de banca","Fuerza","5"},
            {"Rack de sentadillas","Fuerza","3"},
            {"Mancuernas","Fuerza","20"},
            {"Cable cruzado","Fuerza","2"},
            {"Bolsa de boxeo","Combate","4"}
        };
        for(int i = 0; i < eq.length; i++){
            lista.add(new Equipo(i + 1, eq[i][0], eq[i][1], Integer.parseInt(eq[i][2])));
        }
        return lista;
    }

    public static List<ClaseGrupal> GenerarClases(){
        List<ClaseGrupal> lista = new ArrayList<>();
        String[][] cl = {
            {"Zumba","Diana Ruiz"},
            {"Spinning","Marco Avila"},
            {"Yoga","Sara Perez"},
            {"CrossFit","Ivan Leal"},
            {"Pilates","Tania Rios"}
        };
        LocalDateTime base = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0);
        for(int i = 0; i < cl.length; i++){
            lista.add(new ClaseGrupal(i + 1, cl[i][0], cl[i][1], base.plusHours(i * 2), 15));
        }
        return lista;
    }

    public static List<RegistroAcceso> GenerarAccesos(){
        List<RegistroAcceso> lista = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            RegistroAcceso r = new RegistroAcceso(i, i);
            if (i <= 7) r.SetSalida(LocalDateTime.now().minusHours(i));
            lista.add(r);
        }
        return lista;
    }
}
