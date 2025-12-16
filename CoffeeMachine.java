import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {

    int coffeeCount;
    double moneyInserted;
    double moneyGained;

    public class Product {

        String name;
        double price;
        int water_consumption;
        String water_consumption_unit;
        int grains_consumption;
        String grains_consumption_unit;

        Product(
            String name,
            double price,
            int water_consumption,
            String water_consumption_unit,
            int grains_consumption,
            String grains_consumption_unit
        ) {
            this.name = name;
            this.price = price;
            this.water_consumption = water_consumption;
            this.water_consumption_unit = water_consumption_unit;
            this.grains_consumption = grains_consumption;
            this.grains_consumption_unit = grains_consumption_unit;
        }
    }

    private static List<Product> products = new ArrayList<Product>();

    public Product addProduct(
        String name,
        double price,
        int water_consumption,
        String water_consumption_unit,
        int grains_consumption,
        String grains_consumption_unit
    ) {
        Product product = new Product(
            name,
            price,
            water_consumption,
            water_consumption_unit,
            grains_consumption,
            grains_consumption_unit
        );
        products.add(product);
        return product;
    }

    public Product getProduct(String name) {
        Product product = products
            .stream()
            .filter(fproduct -> name.equals(fproduct.name))
            .findAny()
            .orElse(null);
        return product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public class Consumable {

        String name;
        int stockCount;
        int maxStockCount;
        String unit;

        Consumable(
            String name,
            int stockCount,
            int maxStockCount,
            String unit
        ) {
            this.name = name;
            this.stockCount = stockCount;
            this.maxStockCount = maxStockCount;
            this.unit = unit;
        }
    }

    private static List<Consumable> consumables = new ArrayList<Consumable>();

    public Consumable setConsumable(
        String name,
        int stockCount,
        int maxStockCount,
        String unit
    ) {
        Consumable consumable = new Consumable(
            name,
            stockCount,
            maxStockCount,
            unit
        );
        consumables.add(consumable);
        return consumable;
    }

    public Consumable getConsumable(String name) {
        Consumable consumable = consumables
            .stream()
            .filter(fconsumable -> name.equals(fconsumable.name))
            .findAny()
            .orElse(null);
        return consumable;
    }
}
