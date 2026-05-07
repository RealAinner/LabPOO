public class Main {
    public static void main(String[] args){
        Peliculas[] pelis = new Peliculas[3];
        //Intentos validos
        try{
            Peliculas p1 = new Peliculas("Avengers",150,120,"B");
            pelis[0] = p1; //Una vez que se valida se agrega al arreglo para ser impreso posteriormente
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        try{
            Peliculas p2 = new Peliculas("Batman",120,95,"B");
            pelis[1] = p2;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        //Intento invalido 
        try{
            System.out.println("\nAqui el programa inenta ingresar una pelicula pero solo poniendo el titulo, en este caso Iron Man");
            Peliculas p3 = new Peliculas("Iron Man",0,0,"a");
            pelis[2] = p3; //Al haber errores no se guarda en el arreglo y por lo tanto no se muestra
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    
        Cine[] cines = new Cine[3];
        try{
            Cine cine1 = new Cine("Cinepolis Adana Lincoln", "Cumbres, Monterrey"); //Aqui las ubicaciones son validas
            cines[0] = cine1;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        try{
            Cine cine2 = new Cine("Cinepolis Galerias","Sur, Monterrey");
            cines[1] = cine2;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println("Aqui el programa intenta ingresar un cine pero faltandole un parametro a la ubicacion");
            Cine cine3 = new Cine("Cinemex Anahuac", "Monterrey"); //Aqui la ubicacion es invalida
            cines[2] = cine3;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        System.out.println("\n\n\t-------Peliculas-------");
        System.out.printf("| %-25s | %-15s | %-10s | %-15s |","Titulo", "Duracion","Precio","Clasificacion");
        System.out.print("\n------------------------------------------------------------------------------");
        for(Peliculas p : pelis){
            if(p != null)
                p.mostrarPelicula();
        }

        System.out.println("\n\n\n\t-------Cines-------");
        System.out.printf("| %-30s | %-30s |","Nombre","Ubicacion");
        System.out.print("\n-------------------------------------------------------------------");
        for(Cine c : cines){
            if(c != null)
                c.mostrarCine();
        }
        System.out.println("\n");
    }
}
