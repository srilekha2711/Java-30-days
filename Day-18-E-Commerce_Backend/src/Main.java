import java.util.*;
class Main{
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        ProductController productController = new ProductController(sc);
        do{

            System.out.println("===== E-COMMERCE SYSTEM =====\r\n" + //
                                "\r\n" + //
                                "1. Add Product\r\n" + //
                                "2. View Product\r\n" + //
                                "3. View All Products\r\n" + //
                                "4. Purchase Product\r\n" + //
                                "5. Delete Product\r\n" + //
                                "6. Exit\r\n" + //
                                "\r\n" + //
                                "Enter your choice:");
            choice = sc.nextInt();
            switch(choice){
                case 1:
                    productController.addProduct();
                    break;
                case 2:
                    productController.viewProduct();
                    break;
                case 3:
                    productController.viewAllProducts();
                    break;
                case 4:
                    productController.purchaseProduct();
                    break;
                case 5:
                    productController.deleteProduct();
                    break;
                case 6:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");        
            }
        }while(choice !=6);
        sc.close();
}
}