import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
enum roomType{
    SINGLE,
    DOUBLE,
    SUITE,  
}
class Room{
    private int roomNumber;
    //
    private roomType type;
    private double price;
   

     Room(int roomNumber, double price, roomType type){
        this.roomNumber=roomNumber;
        this.price=price;
        this.type=type;
  
     } 
     public int getroomNumber(){
      return roomNumber;
     }
     public roomType getRoomType(){
      return type;
     }
     public double getPrice(){
      return price;
     }
     

     //setters
     public void setPrice(double price){
      this.price=price;
     }
   
     public double calculatePrice(long days){
            return days*price; 
     }

     @Override
     public String toString(){
       return "Room number: "+ roomNumber +" Price: "+ price + " Type: "+ type;
     }

}
class Reservation{
     private int reservationId;
     private String custName;
     private Room room;
     private LocalDate check_in;
     private LocalDate check_out;
     long noOfnights;
     Reservation(int reservationId, String custName, Room room, LocalDate check_in,LocalDate check_out){
           this.reservationId=reservationId;
           this.custName=custName;
           this.check_in=check_in;
           this.room=room;
           this.check_out=check_out;
           this.noOfnights=ChronoUnit.DAYS.between( check_in,check_out);
     }
      public int getReservationId(){
        return reservationId;
      }
      public String getCustName(){
        return custName;
      }
      public Room getRoom(){
        return room;
      }
      public LocalDate getCheckIn(){
        return check_in;
      }
      public LocalDate getCheckOut(){
        return check_out;
      }
     @Override
     public String toString(){
        return "Reservation Id: "+ reservationId + "\nCustomer: "+ custName + "\nRoom: "
                + room.getroomNumber()+ "\nCheck-in: "+ check_in 
                +"\nCheck-out: "+ check_out + "\nNights: "+ noOfnights
                + "\n TotalAmount: "+ room.calculatePrice(noOfnights);
     }

}
public class Main {
    
    public static boolean isRoomAvailable(
        Room room,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Reservation> reservations) {

        for(Reservation r : reservations) {

            if(r.getRoom().getroomNumber() == room.getroomNumber()) {

                if(checkIn.isBefore(r.getCheckOut())
                        && checkOut.isAfter(r.getCheckIn())) {

                    return false;
                }
            }
        }

        return true;
    }

    public static boolean search(List<Room> rooms, int roomNumber){
         for(Room r:rooms){
          if( r.getroomNumber()==roomNumber){
             return true;
             }
         }
         return false;
}
    public static List<Room> checkRoomsAvailable(
        List<Room> rooms,
        List<Reservation> reservations,
        LocalDate checkIn,
        LocalDate checkOut){
          List<Room> availableRooms =new ArrayList<>();
       for(Room r : rooms) {
            if(isRoomAvailable(r, checkIn, checkOut, reservations)) {
                availableRooms.add(r);
            }
          }
          return availableRooms;
        }
       
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int choice;
        roomType type;
        double price;
        //boolean available;
        String custName;
        int roomNumber;
        LocalDate check_in=null;
        LocalDate check_out=null;
        List<Room> rooms=new ArrayList<>();
        List<Reservation> reservations=new ArrayList<>();
        int reservationId = 1000;

        do{

            System.out.println("===== HOTEL RESERVATION SYSTEM =====\r\n" + //
                                "\r\n" + //
                                "1. Add Room\r\n" + //
                                "2. Display Available Rooms\r\n" + //
                                "3. Book Room\r\n" + //
                                "4. Cancel Reservation\r\n" + //
                                "5. Search Reservation\r\n" + //
                                "6. Display All Reservations\r\n" + //
                                "7. Exit\r\n" + //
                                "\r\n" + //
                                "Enter your choice:");
            choice=sc.nextInt();
            if(choice == 1){
                  System.out.println("Enter room number: ");
                  roomNumber=sc.nextInt();
                  System.out.println("Enter room type: SINGLE/DOUBLE/SUITE");
                  String value=sc.next();
                  try{
                   type=roomType.valueOf(value);
                  }
                  catch(IllegalArgumentException e){
                    System.out.println(e);
                    continue;
                  }
                  System.out.println("Enter price per night: ");
                  price=sc.nextDouble();
                  if(!search(rooms,roomNumber)){
                    rooms.add(new Room(roomNumber,price,type));
                    System.out.println("Room added successfully");
                  }
                  else{
                    System.out.println("Room already exists");
                  }

            }
            else if(choice ==2){
                    check_in = null;
                    check_out = null;
                   
                   while (check_in == null) {
                      System.out.print("Enter check-in date (dd-MM-yyyy): ");
                      String input = sc.next();
                      try {
                          check_in = LocalDate.parse(input, formatter);
                      } catch (DateTimeParseException e) {
                          System.out.println("Invalid date format! Please enter in dd-MM-yyyy format.");
                      }
        }

        // Loop until valid check-out date
                while (check_out == null) {
                      System.out.print("Enter check-out date (dd-MM-yyyy): ");
                      String input = sc.next();
                      try {
                          check_out = LocalDate.parse(input, formatter);
                      } catch (DateTimeParseException e) {
                          System.out.println("Invalid date format! Please enter in dd-MM-yyyy format.");
                      }
                }
                  LocalDate today = LocalDate.now();

                  long days = ChronoUnit.DAYS.between(check_in, check_out);
                  if (days<=0){
                    System.out.println("Check-out must be after check-in");
                  }
                  else if(check_in.isBefore(today) ){
                    System.out.println("Check-in date cannot be before today");
                    continue;

                  }
                  
                  List<Room> roomsAvailable=checkRoomsAvailable(rooms,reservations,check_in,check_out);
                  if(roomsAvailable.size()==0){
                    System.out.println("There are no rooms");
                    continue;  
                }
                  System.out.println("Available rooms");
                  for(Room r:roomsAvailable){
                    System.out.println(r);
                  }
            }
            else if(choice ==3){
              boolean found=false;
                check_in = null;
                check_out = null;
                sc.nextLine();
                System.out.println("Enter customer name: ");
                custName=sc.nextLine();
                System.out.println("Enter room number: ");
                roomNumber=sc.nextInt();
                while (check_in == null) {
                      System.out.print("Enter check-in date (dd-MM-yyyy): ");
                      String input = sc.next();
                      try {
                          check_in = LocalDate.parse(input, formatter);
                      } catch (DateTimeParseException e) {
                          System.out.println("Invalid date format! Please enter in dd-MM-yyyy format.");
                      }
        }

        //until valid check-out date
                while (check_out == null) {
                      System.out.print("Enter check-out date (dd-MM-yyyy): ");
                      String input = sc.next();
                      try {
                          check_out = LocalDate.parse(input, formatter);
                      } catch (DateTimeParseException e) {
                          System.out.println("Invalid date format! Please enter in dd-MM-yyyy format.");
                      }
                }

                
                  LocalDate today = LocalDate.now();

                  long days = ChronoUnit.DAYS.between(check_in, check_out);
                  if (days<=0){
                    System.out.println("Check-out must be after check-in");
                  }
                  else if(check_in.isBefore(today) ){
                    System.out.println("Check-in date cannot be before today");

                  }
                  else{
                       for(Room r:rooms){
                        if(r.getroomNumber()==roomNumber){
                                found=true;
                                 if(isRoomAvailable(r, check_in, check_out, reservations)) {

                                      reservations.add(
                                          new Reservation(
                                              reservationId,
                                              custName,
                                              r,
                                              check_in,
                                              check_out
                                          )
                                      );

                                      System.out.println("Room booked successfully.");
                                      reservationId++;

                                  }
                                  else {
                                      System.out.println(
                                          "Room is not available for the selected dates."
                                      );
                                  }
                    
                                }
                        }
                       }
                       if(!found){
                        System.out.println("No such room exits!");
                       }
                  }
            
            else if(choice ==4){
              boolean found=false;
              System.out.println("Enter reservation Id: ");
              int id=sc.nextInt();
              Iterator<Reservation> iterator = reservations.iterator();

              while(iterator.hasNext()) {
                  Reservation r = iterator.next();

                  if(r.getReservationId() == id) {
                      iterator.remove();
                      System.out.println("Reservation cancelled successfully.");
                      found = true;
                      break;
                  }
              }
              if(!found){
                System.out.println("Reservation not found.");
              }
            }
            else if(choice ==5){
                  boolean found=false;
                  System.out.println("Enter reservation Id to search: ");
                  int id=sc.nextInt();
                  sc.nextLine();
                  for(Reservation r :reservations){
                    if(r.getReservationId()==id){
                      found=true;
                      System.out.println(r);
                }
                  }
                  if(!found){
                    System.out.println("No Reservation with this id");
                  }
            }
            else if(choice ==6){

              for(Reservation r: reservations){
                System.out.println(r);
              }
            }
            else if(choice==7){
              System.out.println("Exiting");
            }
            else{
              System.out.println("Invalid choice");
            }

        }while(choice!=7);

        sc.close();
    }

    
}
