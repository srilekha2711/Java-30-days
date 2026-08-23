import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;
enum level{
    INFO,
    WARN,
    ERROR,
    DEBUG,
}
class LogEntry{
    private LocalDateTime timestamp;
    private level logLevel;
    private String message;
    
    LogEntry(LocalDateTime time, level logLevel, String message){
        this.timestamp=time;
        this.logLevel=logLevel;
        this.message=message;
    }
    public LocalDateTime getTimeStamp(){
      return timestamp;}
    
    public level getLogLevel(){
        return logLevel;
    }
    public String getMessage(){
        return message;
    }
    @Override
    public String toString(){
        return timestamp + " | "+ logLevel + " | " + message;
    }

}
class Main{
     
        public static void main(String args[]){

            Scanner sc=new Scanner(System.in);
            int choice;
            List<LogEntry> logs=new ArrayList<>();
            do{
                System.out.println("===== LOG ANALYZER =====\r\n" + //
                                        "\r\n" + //
                                        "1. Add Log\r\n" + //
                                        "2. Display All Logs\r\n" + //
                                        "3. Filter Logs by Level\r\n" + //
                                        "4. Search Logs by Keyword\r\n" + //
                                        "5. Sort Logs by Timestamp\r\n" + //
                                        "6. Count Logs by Level\r\n" + //
                                        "7. Display Error Logs\r\n" + //
                                        "8. Display Most Recent Log\r\n" + //
                                        "9. Exit\r\n" + //
                                        "\r\n" + //
                                        "Enter your choice:");
                choice=sc.nextInt();
                sc.nextLine();
                if(choice == 1){
                    System.out.println("Enter timestamp (yyyy-MM-dd HH:mm:ss): ");
                    String input=sc.nextLine().trim();
                    LocalDateTime timestamp=null;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    try{
                        timestamp=LocalDateTime.parse(input, formatter);
                        System.out.println("Stored timestamp: " + timestamp);
                    }
                    catch(DateTimeParseException e){
                         System.out.println("Please enter mentioned format"+e);
                         continue;
                    }
                    System.out.println("Enter level (INFO/WARN/ERROR/DEBUG): ");
                    input=sc.nextLine().toUpperCase();
                    level logLevel=null;
                    try{
                        logLevel=level.valueOf(input);
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("Invalid loglevel " +e);
                        continue;
                    }
                    System.out.println("Enter message:");
                    String message=sc.nextLine();
                    logs.add(new LogEntry(timestamp,logLevel,message));
                    System.out.println("Log entry added successfully!");
                }
                else if(choice == 2){
                    if(logs.size()==0){
                        System.out.println("No logs");
                        continue;
                    }
                    for(LogEntry l:logs){
                        System.out.println(l);
                    }
                }
                else if(choice == 3){
                    System.out.println("Enter level:");
                    String input=sc.nextLine();
                    final level logLevel;
                    try{
                        logLevel=level.valueOf(input);
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("Invalid loglevel"+e);
                        continue;
                    }
                    System.out.println("===== "+logLevel+" LOGS =====");
                    logs.stream().filter(log -> log.getLogLevel().equals(logLevel))
                    .forEach(System.out::println);
                }
                else if(choice == 4){
                    System.out.println("Enter keyword: ");
                    String keyword=sc.nextLine().toLowerCase();
                    logs.stream()
                    .filter(log -> log.getMessage().toLowerCase().contains(keyword))
                    .forEach(System.out::println);
                }
                else if(choice == 5){
                    logs.stream()
                    .sorted(Comparator.comparing(LogEntry:: getTimeStamp))
                    .forEach(System.out::println);
                }

                else if(choice == 6){
                    System.out.println("=====LOG STATISTICS=====");
                    Map<level, Long> statistics =
                                                logs.stream()
                                                    .collect(
                                                        Collectors.groupingBy(
                                                            LogEntry::getLogLevel,
                                                            Collectors.counting()
                                                        )
                                                    );
                    statistics.forEach((level, count) ->
                                        System.out.println(level + " logs: " + count)
                                    );
                                                        /*for(level l: level.values()){
                       long count = logs.stream()
                                     .filter(log -> log.getLogLevel().equals(l))
                                     .count();
                        System.out.println(l + " logs: " + count);
                    }*/
    
                }

                else if(choice == 7){
                    logs.stream().filter(log -> log.getLogLevel().equals(level.ERROR))
                    .forEach(System.out::println);
                }
                else if( choice == 8){
                    Optional<LogEntry> recentLog = logs.stream()
                                                   .max(Comparator.comparing(LogEntry::getTimeStamp)); 
                    if(recentLog.isPresent()){
                        System.out.println("Most recent log : "+ recentLog);
                    }
                    else{
                        System.out.println("No logs available");
                    }
                }
                else if(choice == 9){
                    System.out.println("Exited");

                }
                else{
                    System.out.println("Invalid choice");
                }
                            

            }while(choice != 9);
            sc.close();
        }
}