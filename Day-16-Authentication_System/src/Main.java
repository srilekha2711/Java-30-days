import java.util.*;
import java.security.MessageDigest;
import java.security.SecureRandom;



class User{
    private String username;
    private String passwordHash;
    private String salt;
    User(String username,String passwordHash, String salt){
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }
    public String getPasswordHash() {
       return passwordHash;
    }

    public String getSalt() {
        return salt;
    }
    public String getUsername(){
        return username;
    }
}
class Main{

    public static String generateSalt() {

                        byte[] salt = new byte[16];      // 16 random bytes
                        SecureRandom random = new SecureRandom();
                        random.nextBytes(salt);
                        return Base64.getEncoder().encodeToString(salt);
                    }


    
    public static String hashPassword(String password, String salt) throws Exception {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String input = password + salt;

            byte[] hash = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();

            for(byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        }
    public static void main(String[] args) throws Exception {
        Map<String, User> users = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        int choice;
        do{

            System.out.println("========== AUTHENTICATION SYSTEM ==========\r\n" + //
                                "\r\n" + //
                                "1. Register\r\n" + //
                                "2. Login\r\n" + //
                                "3. Display Users\r\n" + //
                                "4. Exit\r\n" + //
                                "\r\n" + //
                                "Enter choice: ");
            choice = sc.nextInt();
            if(choice == 1){
                System.out.println("Enter username: ");
                String username = sc.next();
                if(users.containsKey(username)){
                        System.out.println("Username already exists.");
                        continue;
                    }
                System.out.println("Enter password: ");
                String password = sc.next();
                if(password.length() < 8){
                    System.out.println("Password must contain at least 8 characters.");
                    continue;
                }
                String salt = generateSalt();
                String passwordHash = hashPassword(password, salt);
                User user = new User(username, passwordHash, salt);
                users.put(username, user);
            }
            else if(choice == 2){
                System.out.println("Enter username: ");
                String username = sc.next();
                System.out.println("Enter password: ");
                String password = sc.next();
                User user = users.get(username);
                if(user != null){
                    String salt = user.getSalt();
                    String passwordHash = hashPassword(password, salt);
                    if(passwordHash.equals(user.getPasswordHash())){
                        System.out.println("Login successful!");
                    }
                    else{
                        System.out.println("Invalid password.");
                    }
                }
                else{
                    System.out.println("User not found.");
                }
            }
            else if(choice == 3){
                System.out.println("Displaying users...");
                for(String username : users.keySet()){
                    System.out.println(username);
                }
            }
            else if(choice == 4){
                System.out.println("Exiting...");
            }
            else{
                System.out.println("Invalid choice. Please try again.");
            }
        }while (choice != 4);
        sc.close();
    }
}