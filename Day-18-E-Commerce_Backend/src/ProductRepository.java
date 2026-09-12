import java.util.ArrayList;
import java.util.List;
public class ProductRepository {
    private List<Product> products = new ArrayList<>();
    public Product getProductById(int productId){
        for(Product product:products){
            if(product.productId == productId){
                return product;
            }
        }
        return null;
    }
    public List<Product> getAllProducts(){
        return products;
    }
    public void addProduct(Product product){
        products.add(product);
    }
    public void updateProductQuantity(int productId, int newQuantity){
           for(Product product: products){
            if( product.productId == productId){
                product.quantity = newQuantity;
                return;
            }
           }
    }
    public boolean deleteProduct(int productId){
        for(Product product : products){
            if(product.productId == productId){
                products.remove(product);
                return true;
            }
        }
        return false;
    }

}

