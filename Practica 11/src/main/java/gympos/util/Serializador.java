package gympos.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serializador {

    public static <T> void Guardar(List<T> lista, String archivo){
        new File("datos").mkdirs();
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos/" + archivo))){
            oos.writeObject(lista);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> Cargar(String archivo){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("datos/" + archivo))){
            return (List<T>) ois.readObject();
        }catch(IOException | ClassNotFoundException e){
            return new ArrayList<>();
        }
    }
}
