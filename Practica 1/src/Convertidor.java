import java.util.Scanner;

public class Convertidor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double kg, onzas;

        System.out.print("Ingresa los kilogramos: ");
        kg = sc.nextDouble();

        onzas = kg * 35.274;

        System.out.println("Equivale a: " + onzas + " onzas");
        sc.close();
    }
}