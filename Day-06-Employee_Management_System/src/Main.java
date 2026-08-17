import java.util.*;
class Employee{
        private int id;
        private String type;
        private String name;
        private double basicSalary;
        Employee(int id, String type, String name, double basicSalary){
            this.id=id;
            this.type=type;
            this.name=name;
            this.basicSalary=basicSalary;
        }

        public int getId() {
            return this.id;
            }
        public String getType() {
            return this.type;
           }
        public String getName(){
            return name;
        }
        public double getBasicPay(){
            return this.basicSalary;
        }
        public double getFinalSalary() {
                return basicSalary;
            }
        @Override
        public String toString(){
               return "Employee Id: "+getId()+" Name: "+getName()+" Basic Salary: "+getBasicPay();
        }
       

}
class Developer extends Employee{
    
    Developer(int id, String name, double basicsalary){
        super(id, "D", name, basicsalary);
    }

    private double finalSalary;
    
     @Override
     public double getFinalSalary(){
                finalSalary=0.2*getBasicPay();
                return  finalSalary+getBasicPay();
     }
            
   
}

class Manager extends Employee{
  
    Manager(int id, String name, double basicsalary){
        super(id, "M", name, basicsalary);
    }
    private double finalSalary;
     @Override
     public double getFinalSalary(){
                finalSalary=0.3*getBasicPay();
                return  finalSalary+getBasicPay();
     }
    
}

class Main{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        List<Employee> emp=new ArrayList<>();
        String type;
        String name;
        double  basicSalary;
        int id;
        int choice;
        do{
                System.out.println("===== Employee Management System =====\n" +
                                    "1. Add Employee\n" + 
                                    "2. Display All Employees\n" + 
                                    "3. Calculate Salary\n" + 
                                    "4. Exit\n");
                System.out.println("Enter your choice: ");
                choice=sc.nextInt();
                if (choice == 1){
                    System.out.println("Enter employee type (D/M):");
                    type=sc.next();
                    System.out.println("Enter ID: ");
                    id=sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Name: ");
                    name=sc.nextLine();
                    System.out.println("Enter Basic salary: ");
                    basicSalary=sc.nextDouble();
                    Employee obj=null;
                    if(type.toUpperCase().equals("D")){
                        obj=new Developer(id, name, basicSalary);}
                    else if(type.toUpperCase().equals("M")){
                        obj=new Manager(id,name,basicSalary);}
                    else{
                        System.out.println("Invalid type");
                        continue;
                    }
                    boolean found=false;
                    for(Employee e: emp){
                        if( e.getId() == id){
                            System.out.println("This Id already exists");
                            found=true;
                            break;
                        }
                    }
                    if(!found){
                        emp.add(obj);
                        System.out.println("Employee added successfully");
                    }
                }
                else if(choice == 2){
                    System.out.println("====Employee list====");
                    for(Employee e:emp){
                        System.out.println(e);
                    }
                }
                else if(choice==3){
                    System.out.println("Enter ID: ");
                    id=sc.nextInt();
                    sc.nextLine();
                    boolean found=false;
                    for(Employee e: emp){
                        if( e.getId() == id){
         
                            System.out.println("Final Salary: "+e.getFinalSalary());
                           
                            found=true;
                            break;
                        }
                    }
                    if(!found)
                    System.out.println("Id does not exist");
                }
                else if(choice ==4){
                    System.out.println("Thankyou.\nExisting");
                }
                else{
                    System.out.println("Invalid");
                }

        }while (choice !=4);
    sc.close();
    }
}