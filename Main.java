import java.util.Scanner;

class Globals {

    public static CoffeeMachine machine;
    public static Scanner sc;
}

public class Main {

    public static void main(String[] args) {
        Globals.machine = new CoffeeMachine();
        Globals.sc = new Scanner(System.in);

        // Products
        Globals.machine.addProduct("Espresso", 1.50, 10, "cl", 20, "g");
        Globals.machine.addProduct("Allongé", 2, 20, "cl", 10, "g");

        // Consumables
        Globals.machine.setConsumable("water", 100, 100, "cl");
        Globals.machine.setConsumable("grains", 50, 50, "g");
        Globals.machine.setConsumable("cup", 10, 10, "");

        Menu.displayChoiceMenu();
    }
}
