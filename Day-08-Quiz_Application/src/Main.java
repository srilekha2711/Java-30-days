import java.util.*;
enum level{
                EASY,
                MEDIUM,
                DIFFICULT
            }
class Quizq{
    private int id;
    private String question;
    private char correctOption;
    private level diff;
    private String[] options;

    Quizq(int id, String question, String[] options, char correctOption,level diff ){
             this.id=id;
             this.question=question;
             this.options=options;
             this.correctOption=correctOption;
             this.diff=diff;

    }
    public int getId(){
        return this.id;
    }
    public String getQuestion(){
        return this.question;
    }
    public level getDifficulty(){
        return this.diff;
    }
    public char getcorrectOption(){
        return correctOption;
    }
    public String getOptions(){
        return "A."+options[0]+ "\n"+ "B. "+ options[1]+ "\n"+"C. "+ options[2]+ "\n"+ "D. "+ options[3]+ "\n"+ "Difficulty: "+diff;
    }
    
    @Override
    public String toString(){
        return "ID: "+ id + "\n" + "Quesion: "+ question+ "\n"+ "A."+options[0]+ "\n"+ "B. "+ options[1]+ "\n"+"C. "+ options[2]+ "\n"+ "D. "+ options[3]+ "\n"+ "Difficulty: "+diff;
    }
}
class Main{
        
        public static boolean searchQuestion(int id,List<Quizq> quizquestions){
                
            for(Quizq q:quizquestions){
                if(q.getId()==id) return true;
            }
                   return false;
        }
        public static void displayResult(int total,int correct, int wrong){
            if(total ==0){
                System.out.println("No quiz has been attempted yet.");

            }
            else{
                System.out.println("====QUIZ RESULT====");
                System.out.println("Total Questions: "+total);
                System.out.println("Correct Answers: "+ correct);
                System.out.println("Wrong answers: "+wrong);
                System.out.println("Score: "+correct+"/"+total);
                System.out.println("Percentage: "+ (correct*100.00/total));
        
            }
        }
        public static void main(String args[]){

            Scanner sc=new Scanner(System.in);
            List<Quizq> quizquestions=new ArrayList<>();
            int choice;
            int id;
            String question;
            List<Character> arr=new ArrayList<>(List.of('A','B','C','D'));
            int correct=0;
            int total=0;
            int wrong=0;
            do{
                
                System.out.println("===== QUIZ APPLICATION =====\r\n" + //
                                        "\r\n" + //
                                        "1. Add Question\r\n" + //
                                        "2. Display All Questions\r\n" + //
                                        "3. Start Quiz\r\n" + //
                                        "4. Search Questions by Difficulty\r\n" + //
                                        "5. Display Quiz Result\r\n" + //
                                        "6. Exit");
                System.out.println("Enter your choice:" );
                choice=sc.nextInt();

                if(choice == 1){
                    System.out.println("Question ID: ");
                    id=sc.nextInt();
                    sc.nextLine();
                    System.out.println("Question:");
                    question=sc.nextLine();
                    String[] options=new String[4];
                    System.out.println("Enter options");
                    options[0]=sc.nextLine();
                    options[1]=sc.nextLine();
                    options[2]=sc.nextLine();
                    options[3]=sc.nextLine();
                    System.out.println("Enter correct option: A/B/C/D");
                    char correctOption=sc.next().charAt(0);
                    sc.nextLine();
                    if (!arr.contains(Character.toUpperCase(correctOption))) {
                            System.out.println("Invalid option! Must be A/B/C/D.");
                            continue; 
                        }

                   // if(arr.contains(correctOption));
                    System.out.println("Enter the level of difficulty ");
                    String val=sc.nextLine().toUpperCase();
                    try{
                    level diff=level.valueOf(val);
                    System.out.println("You selected: " + diff);
                    if(searchQuestion(id,quizquestions)){
                        System.out.println("This id already exists");
                    }
                    else{
                        quizquestions.add(new Quizq(id,question,options,correctOption,diff));
                        System.out.println("Question added successfully!");
                    }
                    }
                    catch (IllegalArgumentException e) {
                    System.out.println("Invalid difficulty level entered!");
                }
                }
                else if(choice == 2){
                    System.out.println("=====Questions====");
                    for(Quizq q:quizquestions){
                        System.out.println(q);
                    }
                }
                else if(choice ==3){
                    correct = 0;
                    wrong = 0;
                    total = 0;
                    System.out.println("Quiz started");
                    
                    for(Quizq q:quizquestions){
                        System.out.println(q);
                        System.out.println("Enter your answer: A/B/C/D");
                        char ch=Character.toUpperCase(sc.next().charAt(0));
                        if(ch==(q.getcorrectOption())){
                            correct++;
                        }
                        else{
                            wrong++;
                        }
                        total++;
                    }
                    displayResult(total,correct,wrong);
                    
                }
                else if(choice == 4){
                    System.out.println("Enter the level of difficulty: ");
                    sc.nextLine();
                    String val=sc.nextLine().toUpperCase();
                    try{
                    level diff=level.valueOf(val);
                    System.out.println("You selected: " + diff);
                    for(Quizq q:quizquestions){
                        if(q.getDifficulty().equals(diff)){
                            System.out.println(q.getQuestion());
                            System.out.println(q.getOptions());
                        }
                    }
                    
                    }
                    catch (IllegalArgumentException e) {
                    System.out.println("Invalid difficulty level entered!");
                }
                }
                else if(choice ==5){
                    displayResult(total,correct,wrong);
                }
            }while (choice !=6);
            sc.close();
        }
}