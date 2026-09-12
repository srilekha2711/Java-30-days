import java.util.*;
public class ProductService {
    private ProductRepository productRepository;
    ProductService(){
         this.productRepository = new ProductRepository();
    }
    public boolean checkProductValidity(int productId, String productName, double price, int quantity){
        
      
        if(productRepository.getProductById(productId) !=null){
            System.out.println("Product with this Id already exits.");
            return false;
         }
        if(productName ==null || productName.isEmpty()){
            System.out.println("Product name cannot be empty.");
            return false;
        }
        if(price <=0){
            System.out.println("Product price should be greater than 0.");
            return false;
        }
        if(quantity <0){
            System.out.println("Stock quantity should not be negative");
            return false;
        }
        Product product = new Product(productId, productName, price, quantity);
        productRepository.addProduct(product);
        return true;
    }

    public boolean purchaseProduct(int productId, int quantity){
       
      
        Product product = productRepository.getProductById(productId);
        if(product == null){
            System.out.println("Product not found.");
            return false;
        }
        if(quantity <= 0){
            System.out.println("Quantity should be greater than 0.");
            return false;
        }
        if(product.quantity < quantity){
            System.out.println("Insufficient stock. Available quantity: " + product.quantity);
            return false;
        }
        productRepository.updateProductQuantity(productId, product.quantity - quantity);
        System.out.println("Total amount: " + (product.price * quantity));
        return true;

    }
    public Product getProduct(int productId){
        
      
        return productRepository.getProductById(productId);
    }
    public List<Product> getAllProducts(){
        return productRepository.getAllProducts();
    }
    public boolean deleteProduct(int productId){
        return productRepository.deleteProduct(productId);
    }
}
