import java.util.*;

class Product{
        private int productId;
        private String name;
        private double price;
        private Category category;
        Product(int pid, String name, double price, Category category){
            this.productId=pid;
            this.name=name;
            this.price=price;
            this.category=category;
        }
    public int getProductId() {
            return productId;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public Category getCategory() {
            return category;
        }
        @Override
        public String toString() {
            return "Product{" +
                    "id=" + productId +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", category=" + category +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;

            if (!(obj instanceof Product)) return false;

            Product other = (Product) obj;

            return this.productId == other.productId;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(productId);
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity() {
        this.quantity--;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " × " + quantity +
                " | ₹" + product.getPrice() +
                " each | Subtotal: ₹" + getSubtotal();
    }
}

class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                item.increaseQuantity(quantity);
                return;
            }
        }

        items.add(new CartItem(product, quantity));
    }

    public boolean removeProduct(int productId, boolean removeCompletely) {
        for (CartItem item : items) {
            if (item.getProduct().getProductId() == productId) {

                if (removeCompletely) {
                    items.remove(item);
                    return true;
                }

                item.decreaseQuantity();

                if (item.getQuantity() <= 0) {
                    items.remove(item);
                }

                return true;
            }
        }

        return false;
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        for (CartItem item : items) {
            System.out.println(item);
        }
    }

    public double calculateTotal() {
        double total = 0;

        for (CartItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }
}

enum Category {
    ELECTRONICS,
    CLOTHING,
    FOOD,
    BOOKS,
    HOME,
    OTHER
}

public class Main {
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    Set<Product> products = new HashSet<>();
    Cart cart = new Cart();
   // Set<Cart> cartt=new HashSet<>();
    int productId;
    String name;
    double price;
    int quantity;
    int choice;
    //Product p=null;
    do{

        System.out.println("1. Add Product\n" + 
                        "2. View Products\n" + //
                        "3. Add Product to Cart\n" + //
                        "4. Remove Product from Cart\n" + //
                        "5. View Cart\n" + //
                        "6. Calculate Total\n" + //
                        "7. Search Product\n" + //
                        "8. Exit");
        System.out.print("Enter your choice: ");
        choice = sc.nextInt();

        if (choice == 1) {

            System.out.print("Enter Product ID: ");
            productId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Product Name: ");
            name = sc.nextLine();

            System.out.print("Enter Price: ");
            price = sc.nextDouble();
            sc.nextLine();

            if (price <= 0) {
                System.out.println("Price must be greater than zero.");
                continue;
            }

            System.out.println("Enter Category:");
            System.out.println("ELECTRONICS, CLOTHING, FOOD, BOOKS, HOME, OTHER");

            String input = sc.nextLine().trim().toUpperCase();

            Category category;

            try {
                category = Category.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category.");
                continue;
            }

            Product p = new Product(productId, name, price, category);

            if (products.contains(p)) {
                System.out.println("Product already exists.");
                continue;
            }

            products.add(p);

            System.out.println("Product added successfully.");
        }

        else if(choice ==2){
            System.out.println("======PRODUCTS======");
            for(Product pi:products){
                System.out.println(pi);
                //System.out.println(pi.toString());
            }
        }
        else if (choice == 3) {

            System.out.print("Enter Product ID: ");
            productId = sc.nextInt();

            System.out.print("Enter Quantity: ");
            quantity = sc.nextInt();

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero.");
                continue;
            }

            Product prod = null;

            for (Product pi : products) {
                if (pi.getProductId() == productId) {
                    prod = pi;
                    break;
                }
            }

            if (prod == null) {
                System.out.println("Product not found. Please add the product first.");
                continue;
            }

            cart.addProduct(prod, quantity);

            System.out.println("Product added to cart successfully.");
}
        else if (choice == 4) {

            System.out.print("Enter Product ID: ");
            productId = sc.nextInt();

            System.out.println("1. Remove completely");
            System.out.println("2. Reduce quantity by 1");

            int option = sc.nextInt();

            boolean removed = false;

            if (option == 1) {
                removed = cart.removeProduct(productId, true);
            }
            else if (option == 2) {
                removed = cart.removeProduct(productId, false);
            }
            else {
                System.out.println("Invalid option.");
                continue;
            }

            if (removed) {
                System.out.println("Cart updated successfully.");
            }
            else {
                System.out.println("Product not found in cart.");
            }
}
        else if (choice == 5) {

            System.out.println("====== CART PRODUCTS ======");
            cart.displayCart();
}
       else if (choice == 6) {

            double total = cart.calculateTotal();

            System.out.println("Total bill: ₹" + total);
        }
        else if (choice == 7) {

                System.out.println("Enter Product ID:");
                productId = sc.nextInt();

                Product foundProduct = null;

                for (Product pi : products) {
                    if (pi.getProductId() == productId) {
                        foundProduct = pi;
                        break;
                    }
                }

                if (foundProduct != null) {
                    System.out.println("Product found:");
                    System.out.println(foundProduct);
                }
                else {
                    System.out.println("Product not found.");
                }
}
        else if(choice ==8){
            System.out.println("Exiting");
        }
        else{
            System.out.println("Invalid choice");
        }

    }while(choice !=8);
}
}
