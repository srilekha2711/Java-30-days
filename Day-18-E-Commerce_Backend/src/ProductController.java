import java.util.*;
public class ProductController{
   ProductService productService = new ProductService();
   Scanner sc;
   ProductController(Scanner sc){
      this.sc=sc;
   }
    public void addProduct() {
            System.out.println("Enter Product ID:");
            int productId=sc.nextInt();
            sc.nextLine();
            System.out.println("Enter Product Name:");
            String productName=sc.nextLine();
            System.out.println("Enter Product Price:");     
            double price=sc.nextDouble();
            System.out.println("Enter Product Quantity:");
            int quantity=sc.nextInt();
            boolean res = productService.checkProductValidity(productId, productName, price, quantity);
            if (res) {
                System.out.println("Product added successfully.");
            }
    }
    public void viewProduct() {
           System.out.println("Enter product ID:");
           int productId = sc.nextInt();
           Product product = productService.getProduct(productId);
           if (product != null) {
               System.out.println("Product ID: " + product.productId);
               System.out.println("Product Name: " + product.productName);
               System.out.println("Product Price: " + product.price);
               System.out.println("Product Quantity: " + product.quantity);
           } else {
               System.out.println("Product not found.");
           }
           
    }
    public void viewAllProducts() {

        List<Product> products = productService.getAllProducts();
        if(products.isEmpty()){
            System.out.println("No products available.");
            return;
        }
        for (Product product : products) {
            System.out.println("Product ID: " + product.productId);
            System.out.println("Product Name: " + product.productName);
            System.out.println("Product Price: " + product.price);
            System.out.println("Product Quantity: " + product.quantity);
            System.out.println("--------------------");
        }

    }
    public void purchaseProduct() {
        System.out.println("Enter product ID:");
        int productId= sc.nextInt();
        System.out.println("Enter quantity to purchase:");
        int quantity = sc.nextInt();
        boolean res = productService.purchaseProduct(productId, quantity);
        if(res){
            System.out.println("Product purchased successfully.");

        }
        else{
            System.out.println("Product purchase failed. Please check the product ID and quantity.");
        }

    }
    public void deleteProduct() {
        System.out.println("Enter productId to delete:");
        int productId =sc.nextInt();
        boolean res=productService.deleteProduct(productId);
        if(res){
            System.out.println("Product deleted successfully.");
        }
        else{
            System.out.println("Product not found.");
        }
        }
    
}
