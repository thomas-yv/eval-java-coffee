import java.util.List;

public class Menu {

    public static void displayProducts() {
        System.out.println("Nos produits :");

        for (CoffeeMachine.Product product : Globals.machine.getProducts()) {
            System.out.println(
                (((Globals.machine.getConsumable("water").stockCount <
                                product.water_consumption) ||
                            (Globals.machine.getConsumable(
                                    "grains"
                                ).stockCount <
                                product.water_consumption))
                        ? "Hors stock"
                        : "En stock") +
                    " - " +
                    product.name +
                    " [" +
                    product.price +
                    "€]"
            );
        }
    }

    public static void displayChoiceMenu() {
        System.out.print("\033[H\033[2J"); // this hack clear terminal
        System.out.flush();

        System.out.println("--- Machine à café ---");
        System.out.println("Bienvenue !");
        System.out.println();
        Menu.displayProducts();
        System.out.println();
        System.out.println("1. Commander");
        System.out.println("2. Menu technicien");
        System.out.println("3. Quitter");
        String choiceInput = Globals.sc.nextLine();

        switch (choiceInput) {
            case "1":
                if (Globals.machine.getConsumable("cup").stockCount == 0) {
                    System.out.println(
                        "Il n'y a plus de goblet dans la machine à café, la machine est temporairement hors service."
                    );

                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}

                    displayChoiceMenu();
                    return;
                }

                if (Globals.machine.coffeeCount >= 5) {
                    System.out.println(
                        "Malheureusment, la machine a café est temporairement hors service."
                    );

                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}

                    displayChoiceMenu();
                } else {
                    System.out.println();
                    displayCommandMenu();
                }
                break;
            case "2":
                System.out.println();
                displayTechnicianMenu();
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println();
                System.out.println("Choix invalide.");
                System.out.println();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
                displayChoiceMenu();
                break;
        }
    }

    public static void displayCommandMenu() {
        System.out.print("\033[H\033[2J"); // this hack clear terminal
        System.out.flush();

        System.out.println("--- Commander ---");
        List<CoffeeMachine.Product> products = Globals.machine.getProducts();

        for (int i = 0; i < Globals.machine.getProducts().size(); i++) {
            CoffeeMachine.Product product = products.get(i);
            System.out.println(
                i +
                    1 +
                    ". Commander un " +
                    product.name +
                    " [" +
                    product.price +
                    "€]"
            );
        }

        String choiceInput = Globals.sc.nextLine();

        try {
            int parsedChoice = Integer.parseInt(choiceInput);
            try {
                CoffeeMachine.Product product = products.get(parsedChoice - 1);

                if (
                    Globals.machine.getConsumable("water").stockCount <
                        product.water_consumption ||
                    Globals.machine.getConsumable("grains").stockCount <
                    product.grains_consumption
                ) {
                    System.out.println(
                        "Malheureusment, notre stock interne ne permet pas de vous servir un " +
                            product.name +
                            "."
                    );

                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}

                    displayChoiceMenu();
                    return;
                } else {
                    insertCoinsMenu(product);
                    return;
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("Choix invalide.");
                System.out.println();

                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {}

                displayCommandMenu();
            }
        } catch (Exception e) {
            System.out.println();
            System.out.println("Choix invalide.");
            System.out.println();

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {}

            displayCommandMenu();
        }
    }

    public static void insertCoinsMenu(CoffeeMachine.Product product) {
        System.out.print("\033[H\033[2J"); // this hack clear terminal
        System.out.flush();

        System.out.println("Prix demandé : " + product.price + "€");
        System.out.println(
            "Vous avez insérez " + Globals.machine.moneyInserted + "€."
        );

        if (Globals.machine.moneyInserted == product.price) {
            Globals.machine.coffeeCount += 1;
            Globals.machine.moneyGained += Globals.machine.moneyInserted;
            Globals.machine.moneyInserted = 0;

            Globals.machine.getConsumable("water").stockCount -=
                product.water_consumption;
            Globals.machine.getConsumable("grains").stockCount -=
                product.grains_consumption;
            Globals.machine.getConsumable("cup").stockCount -= 1;
            System.out.println();
            System.out.println("Votre boisson est servie ! Bonne journée !");
            System.out.println();

            try {
                Thread.sleep(3000);
            } catch (Exception e) {}

            displayChoiceMenu();
        } else {
            System.out.println();
            System.out.println("1. Insérer 0.50€");
            System.out.println("2. Annuler");
            String choiceInput = Globals.sc.nextLine();

            switch (choiceInput) {
                case "1":
                    Globals.machine.moneyInserted += 0.50;
                    insertCoinsMenu(product);
                    break;
                case "2":
                    displayChoiceMenu();
                    break;
                default:
                    System.out.println();
                    System.out.println("Choix invalide.");
                    System.out.println();

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {}

                    insertCoinsMenu(product);
                    break;
            }
        }
    }

    public static void displayTechnicianMenu() {
        System.out.print("\033[H\033[2J"); // this hack clear terminal
        System.out.flush();

        System.out.println("--- MODE TECHNICIEN ---");
        System.out.println("1. Recharger les consommables");
        System.out.println("2. Récupérer l'argent");
        System.out.println("3. Détartrer la machine");
        System.out.println("4. Quitter");

        String choiceInput = Globals.sc.nextLine();

        switch (choiceInput) {
            case "1":
                System.out.println();
                System.out.println("Etat des consommables actuel :");
                System.out.println(
                    "Eau : " +
                        Globals.machine.getConsumable("water").stockCount +
                        " " +
                        Globals.machine.getConsumable("water").unit
                );
                System.out.println(
                    "Grains : " +
                        Globals.machine.getConsumable("grains").stockCount +
                        " " +
                        Globals.machine.getConsumable("grains").unit
                );
                System.out.println(
                    "Goblets : " +
                        Globals.machine.getConsumable("cup").stockCount +
                        " " +
                        Globals.machine.getConsumable("cup").unit
                );

                Globals.machine.setConsumable("water", 100, 100, "cl");
                Globals.machine.setConsumable("grains", 50, 50, "g");
                Globals.machine.setConsumable("cup", 10, 10, "");

                System.out.println();
                System.out.println(
                    "Les consommables ont été rechargés avec succès."
                );
                System.out.println();

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {}

                displayTechnicianMenu();
                break;
            case "2":
                System.out.println();
                System.out.println(
                    "Il y a actuellement " + Globals.machine.moneyGained + "€"
                );

                Globals.machine.moneyGained = 0;

                System.out.println();
                System.out.println("L'argent a bien été récupéré.");

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {}

                displayChoiceMenu();
                break;
            case "3":
                Globals.machine.coffeeCount = 0;

                System.out.println();
                System.out.println(
                    "La machine a café a été détartré avec succès."
                );
                System.out.println();

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {}

                displayTechnicianMenu();
                break;
            case "4":
                System.out.println();
                displayChoiceMenu();
                break;
            default:
                System.out.println();
                System.out.println("Choix invalide.");
                System.out.println();

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}

                displayTechnicianMenu();
                break;
        }
    }
}
