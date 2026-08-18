import java.util.*;

class Student{
    private int id;
    private String name;
    private double[] arr;
    
    //private 
    Student(int id, String name, double[] arr){
        this.id=id;
        this.name=name;
        this.arr=arr;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getMarks(){
        return "s1: "+arr[0]+" s2: "+ arr[1]+ " s3: "+arr[2]; 
    }
    public double getAverage(){
        return (arr[0]+arr[1]+arr[2])/3;
    }
    public String getGrade(){
           double avg=getAverage();
           String grade;
           if(avg>=90) grade="A";
           else if(avg>=80) grade= "B";
           else if(avg>=70) grade="C";
           else if(avg>=60) grade="D";
           else grade="F";
           return grade;
    }
    @Override
    public String toString(){
        return "ID: "+id+ " Name: "+ name + " average: "+ getAverage()+ " Grade: "+getGrade();
    }
}
class Main{
       public static void main(String args[]){
        Scanner sc=new Scanner(System.in);

        int id;
        String name;
        double[] marks=new double[3];
        List<Student> students=new ArrayList<>();
        int choice;

        do{

            System.out.println("\n====== Student Grade Management ======\r\n" + //
                                "\n" + //
                                "1. Add Student\r\n" + 
                                "2. Display All Students\r\n" + 
                     
                                "3. Sort Students by Average Marks\r\n" +
                                "4. Sort Students by Name\r\n" + 
                                "5. Display Top Performer\r\n" +
                                "6. Search Student by ID\r\n" +  
                                "7. Exit\r\n" 
                              
                                );

            System.out.println("Enter choice:");
            choice=sc.nextInt();
            if(choice ==1){
                System.out.println("Enter Student ID: ");
                id=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter Name:");
                name=sc.nextLine();
                System.out.println("Enter marks in 3 subjects:");
                marks[0]=sc.nextDouble();
                sc.nextLine();
                marks[1]=sc.nextDouble();
                sc.nextLine();
                marks[2]=sc.nextDouble();
                sc.nextLine();
                boolean found=false;
                
                for(Student stu:students){
                    if(stu.getId()==id){
                        found=true;
                        System.out.println("Id already exists");
                        break;
                    }
                }
                if(!found){
                    if(marks[0]>=0 && marks[0]<=100 && marks[1]>=0 && marks[1]<=100 && marks[2]>=0 && marks[2]<=100){
                        double[] studentMarks = Arrays.copyOf(marks, marks.length);
                    students.add(new Student(id,name,studentMarks));
                    System.out.println("Student added successfully");
                    }
                    else{
                        System.out.println("invalid marks");
                    }  
                }
            }
            else if(choice ==2){
                System.out.println("Students data");
                for(Student s: students){
                    System.out.println(s);
                }
            }
            else if(choice==3){
                List<Student> byAvg=new ArrayList<>(students);
                Collections.sort(byAvg, new Comparator<Student>(){
                    @Override
                    public int compare(Student s1,Student s2){
                        return Double.compare(s2.getAverage(),s1.getAverage());
                    }
                });
                for(Student stu:byAvg){
                    System.out.println(stu);
                }
            }
            else if(choice == 4){
                List<Student> byAvg=new ArrayList<>(students);
                Collections.sort(byAvg, new Comparator<Student>(){
                    @Override
                    public int compare(Student s1,Student s2){
                        return s1.getName().compareToIgnoreCase(s2.getName());
                    }
                });
                for(Student stu:byAvg){
                    System.out.println(stu);
                }

            }
            else if(choice == 5){
                System.out.println("Top performer");
                double avg=0.0;
                Student top=null;
                for(Student s:students){
                    if(s.getAverage()>avg){
                        avg=s.getAverage();
                        top=s;
                    }
                }
                if(top == null){
                    System.out.println("No such student exists");

                }
                else{
                    System.out.println(top);
                }
            }
            else if(choice == 6){
                System.out.println("Enter student id: ");
                id=sc.nextInt();
                boolean found=false;
                for(Student s:students){
                    if(s.getId()==id){
                        found=true;
                        System.out.println(s);
                    }
                }
                if(!found){
                    System.out.println("No such student exists");
                }
            }
            else if(choice ==7){
                System.out.println("Thankyou. Exiting!!");
            }
        }while( choice !=7);
        sc.close();
       }
}