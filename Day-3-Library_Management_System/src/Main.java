import java.util.*;


abstract class LibraryItem {
    int itemId;
    String title;
    String author;
    boolean available=true;
    abstract void displayInfo();
   // public abstract boolean getStatus();
    void borrow() {
    if (available) {
        available = false;
        System.out.println("Item borrowed");
    } else {
        System.out.println("Item is not available");
    }
}
    void returnItem() {
    if (!available) {
        available = true;
        System.out.println("Item returned");
    } else {
        System.out.println("Item was not borrowed");
    }
}
    void isAvailable(boolean available) {
        if(available){
            System.out.println("Item is available");
        } else {
            System.out.println("Item is not available");
        }
    }
}

class Book extends LibraryItem{
    private int ISBN;
    
    
    Book(int ISBN, int id, String title, String author){
        this.ISBN = ISBN;
        this.itemId = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }               
      void displayInfo(){
        System.out.println("ISBN: " + ISBN);
        System.out.println("ID: " + itemId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Status: " + available);
}
    
}
class Magazine extends LibraryItem{
        private int issueNumber;

        Magazine(int itemId, int issueNumber, String title, String author){
            this.itemId = itemId;
            this.issueNumber = issueNumber;
            this.title = title;
            this.author = author;
            this.available = true;
        }
        void displayInfo(){
            System.out.println("Item ID: " + itemId);
            System.out.println("Issue Number: " + issueNumber);
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Status: " + available);
        }
        
}
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<LibraryItem> libraryItems = new ArrayList<>();

        int itemId;
        int choice = 0;
        do{
        System.out.println("========== LIBRARY MANAGEMENT ==========" +"\n"+
                            "1. Add Book" + "\n"+
                             "2. Add Magazine" + "\n"+
                             "3. View All Items" + "\n"+
                             "4. Search Item" + "\n"+
                             "5. Borrow Item" + "\n"+
                             "6. Return Item" + "\n"+
                             "7. Exit");
        System.out.print("Enter your choice: ");
        choice = scanner.nextInt();
        if(choice==1){
            System.out.println("Enter Item ID: ");
            itemId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter Title: ");
            String title = scanner.nextLine();
            System.out.println("Enter Author: ");
            String author = scanner.nextLine();
            System.out.println("Enter ISBN: ");
            int ISBN = scanner.nextInt();
            Book book = new Book(ISBN, itemId, title, author);
            libraryItems.add(book);
        }
        else if(choice ==2){
            System.out.println("Enter Item ID:");
            itemId = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter Title:");
            String title = scanner.nextLine();
            System.out.println("Enter Author:");
            String author = scanner.nextLine();
            System.out.println("Enter Issue Number:");
           
            int issueNumber = scanner.nextInt();
            Magazine magazine = new Magazine(itemId,issueNumber, title, author);
            libraryItems.add(magazine);
        }
        else if (choice ==3){
            System.out.println("======All Library Items======");
            for(LibraryItem item: libraryItems){
                item.displayInfo();
                System.out.println("-----------------------------");

            }
            
        }
        else if(choice ==4){
            System.out.println("Enter Item ID to search:");
            int searchId= scanner.nextInt();
            boolean found = false;
            for(LibraryItem item: libraryItems){
                if(item.itemId == searchId){
                    item.displayInfo();
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println("Item not found");
            }
        }
        else if(choice ==5){
            System.out.println("Enter Item Id to borrow:"); 
            
            int borrowId = scanner.nextInt();
            boolean found = false;
            for(LibraryItem item: libraryItems){
                if(item.itemId == borrowId){
                    item.borrow();
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println("Item not found");
            }

        }
        else if(choice ==6){
            System.out.println("Enter Item Id to return:"); 
            int returnId = scanner.nextInt();
            boolean found = false;
            for(LibraryItem item: libraryItems){
                if(item.itemId == returnId){
                    item.returnItem();
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println("Item not found");
            }
        }
        else if(choice ==7){
            System.out.println("Exiting...");
        }
        else{
            System.out.println("Invalid choice");
        }  
    }while (choice!=7);
    scanner.close();
}
}
