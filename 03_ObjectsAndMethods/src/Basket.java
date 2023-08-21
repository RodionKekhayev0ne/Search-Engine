public class Basket {

    private static int allPrice = 0;
    private static int itemsCount = 0;
    private static int count = 0;
    private String items = "";
    private int totalPrice = 0;
    private int limit;

    public Basket() {
        increaseCount(1);

        items = "Список товаров:";
        this.limit = 1000000;
    }

    public Basket(int limit) {
        this();
        this.limit = limit;
    }

    public Basket(String items, int totalPrice) {
        this();
        this.items = this.items + items;
        this.totalPrice = totalPrice;
    }


    public static int getCount() {
        return count;
    }

    public static int getTotalPrice() {
        return allPrice;
    }

    public static void increaseItemCount(int count) {
        Basket.itemsCount += count;
    }

    public static void increasePrice(int price) {
        Basket.allPrice += price;
    }

    public static void increaseCount(int count) {
        Basket.count += count;
    }

    public static int midItemsPrice() {
        return allPrice / itemsCount;
    }

    public static int midBasketPrice() {
        return allPrice / count;
    }

    public void add(String name, int price) {
        add(name, price, 1);
    }

    public void add(String name, int price, int count) {
        boolean error = false;
        if (contains(name)) {
            error = true;
        }

        if (totalPrice + count * price >= limit) {
            error = true;
        }

        if (error) {
            System.out.println("Error occured :(");
            return;
        }

        items = items + "\n" + name + " - " +
                count + " шт. - " + price;
        totalPrice = totalPrice + count * price;

        increasePrice(price);
        increaseItemCount(1);
    }

    public void clear() {
        items = "";
        totalPrice = 0;
    }

    public int getPrice() {
        return totalPrice;
    }

    public boolean contains(String name) {
        return items.contains(name);
    }

    public void print(String title) {
        System.out.println(title);
        if (items.isEmpty()) {
            System.out.println("Корзина пуста");
        } else {
            System.out.println(items);
        }
    }
}
