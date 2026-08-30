import java.util.*;
enum CabType {
    MINI,
    SEDAN,
    SUV
}
enum BookingStatus {
    PENDING,
    CONFIRMED,
    FAILED
}
class Cab{

    private int cabId;
    private String driverName;
    private CabType cabType;
    private boolean available;
    Cab(int id, String driverName, CabType cabType,boolean available){
        this.cabId=id;
        this.driverName=driverName;
        this.cabType=cabType;
        this.available=available;
    }
    
    public int getId(){
        return cabId;
    }
    public String getDriverName(){
        return driverName;
    }
    public CabType getCabType(){
        return cabType;
    }
    public boolean getAvailable(){
        return available;
    }
    public void setAvailable(boolean available){
          this.available=available;
    }
   
    @Override
    public String toString(){
        return "Cab Id: " + cabId +
            "\nDriver: " + driverName +
            "\nCab Type: " + cabType +
            "\nAvailable: " + available;
    }
    
}

class Booking{
    private int bookingId;
    private Cab cab;
    private String customerName;
    private BookingStatus status;

    Booking(int bookingId, Cab cab, String customerName, BookingStatus status){
        this.status=status;
        this.bookingId = bookingId;
        this.cab = cab;
        this.customerName = customerName;
    }

    public int getbookingId(){
        return bookingId;
    }
    public String getCustomerName(){
        return customerName;
    }
    public Cab getCab(){
        return this.cab;
    }
    public BookingStatus getStatus(){
    return status;
    }

    public void setStatus(BookingStatus status){
        this.status = status;
    }
        @Override
    public String toString(){
          String s1="Booking Id: "+ bookingId ;
          String s2="Cab Id: " + this.cab.getId();
          String s3= "Customer Name: "+ customerName;
          return s1 + "\n" + s2 + "\n" + s3+ "\n"+"Status: " + status;
    }
}

class BookingTask implements Runnable{

    private Booking book;
 
    public BookingTask(Booking book){
        this.book = book;
    }
    public void run(){
        synchronized(book.getCab()) {

            if(book.getCab().getAvailable()){ 

                book.getCab().setAvailable(false);
                book.setStatus(BookingStatus.CONFIRMED);

                System.out.println(
                    "Booked " + book.getCab().getId() +
                    " cab for: " + book.getCustomerName()
                );
                
            }
            else{ 

                book.setStatus(BookingStatus.FAILED);

                System.out.println(
                    "Cab is already booked."
                );
            }
        }

    }

}
public class Main {
        
         public static Cab getCabWithId(int id, List<Cab> cabs){
            for(Cab cab: cabs){
                  if(cab.getId() == id){
                    return cab;
                  }
            }
            return null;

         }
         public static void main(String args[]){
             Scanner sc = new Scanner(System.in);
             int choice;
             List<Cab> cabs = new ArrayList<>();
             List<Booking> bookings = new ArrayList<>();
             do{

                System.out.println("========== CAB BOOKING SYSTEM ==========\r\n" + //
                                        "\r\n" + //
                                        "1. Add Cab\r\n" + //
                                        "2. Display Cabs\r\n" + //
                                        "3. Book Cab\r\n" + //
                                        "4. Display Bookings\r\n" + //
                                        "5. Start Concurrent Bookings\r\n" + //
                                        "6. Exit\n"+ "Enter your choice: ");
                choice=sc.nextInt();
                sc.nextLine();
                if(choice == 1){
                        System.out.println("Enter cab Id: ");
                        int cabId=sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter driver name: ");
                        String driverName=sc.nextLine();
                        System.out.println("Enter cab type: MINI/SEDAN,/SUV");
                        String type=sc.nextLine().toUpperCase();
                        CabType cabType = null;
                        try{
                            cabType =CabType.valueOf(type);
                        }
                        catch(Exception e){
                    System.out.println("Invalid. Enter MINI/SEDAN,/SUV");
                    continue;
                        }
                        cabs.add(new Cab(cabId, driverName, cabType, true));
                                         
                        
                }
                else if( choice == 2){
                     for(Cab cab: cabs){
                        System.out.println(cab+"\n");
                     }

                }
                else if(choice == 3){
                    System.out.println("Enter bookingId: ");
                        int bid=sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter customerName: ");
                        String customerName = sc.nextLine(); 
                        Cab cab=null;
                        System.out.println("Enter cab id: ");
                        int id=sc.nextInt();
                        cab=getCabWithId(id,cabs);
                        if(cab == null){
                            System.out.println("Cab not found");
                            continue;
                        }
                        Booking book = new Booking(bid, cab, customerName, BookingStatus.PENDING);
                        bookings.add(book);
                        Runnable task=new BookingTask(book);
                        Thread t= new Thread(task);
                        t.start();

                }
                else if( choice == 4){
                    for(Booking b:bookings)
                    System.out.println(b);
                }
                else if( choice == 5){
                      System.out.println("Enter no of concurrent bookings: ");
                      int n=sc.nextInt();
                      for(int i = 0; i < n; i++){
                        System.out.println("Enter bookingId: ");
                        int bid=sc.nextInt();
                        sc.nextLine();
                        System.out.println("Enter customerName: ");
                        String customerName = sc.nextLine(); 
                        Cab cab=null;
                        System.out.println("Enter cab id: ");
                        int id=sc.nextInt();
                        cab=getCabWithId(id,cabs);
                        if(cab == null){
                            System.out.println("Cab not found");
                            continue;
                        }
                        Booking book = new Booking(bid, cab, customerName, BookingStatus.PENDING);
                        bookings.add(book);
                        Runnable task=new BookingTask(book);
                        Thread t= new Thread(task);
                        t.start();
                    }
                        
         
                }
                else if(choice == 6){
                    System.out.println("Thankyou. Exited!");
                }
                else{
                    System.out.println("Invalid choice");
                }

             }while( choice !=6); 
             sc.close();
         }
}