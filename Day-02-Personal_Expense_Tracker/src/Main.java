import java.util.*;

class Expense{
	private int expenseId;
	private String description;
	private double amount;
	private Category category;
    enum Category{FOOD,TRAVEL,SHOPPING,ENTERTAINMENT,OTHER};
	
	public Expense(int expenseId,String description, double amount, Category category){
		this.expenseId=expenseId;
		this.description=description;
		this.amount=amount;
		this.category=category;
	}
	
	//getters
	
	public int getExpenseId() {
		return this.expenseId;
	}
	public String getDescription() {
		return this.description;
	}
	public double getAmount() {
		return this.amount;
	}
	public Expense.Category getCategory() {
		return this.category;
	}
	
}
public class Main {
	
	     public static Expense getObjectByexpenseId(List<Expense> expenses, int expenseId) {
	    	 
	    	 for(Expense e:expenses) {
	    		 if (e.getExpenseId()==expenseId) {
	    			 return e;
	    		 }
	    	 }
	    	 return null;
	     }
	     
	     public static void getAllExpenses(List<Expense> expenses) {
	    	 
	    	 for(Expense e: expenses) {
	    		 System.out.println(e.getExpenseId()+"    "+ e.getDescription()+"    "+ e.getAmount()+"    "+ e.getCategory());
	    	 }
	     }
	     public static double getTotalspending(List<Expense> expenses) {
	    	 double amt=0;
	    	 for(Expense e:expenses) {
	    		 amt+=e.getAmount();
	    	 }
	    	 return amt;
	     }
	     public static void getHighestExpense(List<Expense> expenses) {
	    	 Expense obj=null;
	    	 double highest=0.0;
	    	 for(Expense e:expenses) {
	    		 if(e.getAmount()>highest) {
	    			 highest=e.getAmount();
	    			 obj=e;
	    		 }
	    	 }
             if (obj == null) {
                 System.out.println("No expenses found.");
                 return;
             }
	    	 System.out.println(
	    	 		"ID: "+ obj.getExpenseId() + "\n"
	    	 		+ "Description: " +obj.getDescription()+"\n"
	    	 		+ "Amount: "+obj.getAmount()+"\n"
	    	 		+ "Category: "+obj.getCategory());
	     }
	     public static void getFilterByCategory(List<Expense> expenses, Expense.Category category)
	     {
	    	 System.out.println(category+" Expenses:");
	    	 for(Expense e: expenses) {
	    		 if (e.getCategory().equals((category))) {
	    			 System.out.println(e.getExpenseId()+"->"+e.getDescription()+"->"+e.getAmount());
	    		 }
	    	 }
	     }
	     
	     public static void main(String args[]) {
	     Scanner sc=new Scanner(System.in);
	     
	     List<Expense> expenses=new ArrayList<>();
	     int expenseId;
	     String description;
	     double amount;
	     //String category;
	     int choice;
	     do {
	    	 System.out.println("========== EXPENSE TRACKER ==========\r\n"
	    	 		+ "\r\n"
	    	 		+ "1. Add Expense\r\n"
	    	 		+ "2. View All Expenses\r\n"
	    	 		+ "3. Calculate Total Spending\r\n"
	    	 		+ "4. Find Highest Expense\r\n"
	    	 		+ "5. Filter by Category\r\n"
	    	 		+ "6. Delete Expense\r\n"
	    	 		+ "7. Exit\r\n"
	    	 		+ "\r\n"
	    	 		+ "Enter choice:");
	    	 
	    	 choice=sc.nextInt();
	    	 if (choice == 1) {
	    		 System.out.println("Enter the expense id :");
	    		 expenseId=sc.nextInt();
	    		 sc.nextLine();
	    		 System.out.println("Enter description :");
	    		 description=sc.nextLine();
	    		 System.out.println("Enter amount :");
	    		 amount=sc.nextDouble();
	    		 //System.out.println("Enter category :");
                 
                 System.out.println("Enter category (FOOD, TRAVEL, SHOPPING, ENTERTAINMENT, OTHER):");
                 sc.nextLine();
                 String categoryInput = sc.nextLine().trim().toUpperCase();

                Expense.Category category = null;
                    try {
                        category = Expense.Category.valueOf(categoryInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid category. Please enter one of: FOOD, TRAVEL, SHOPPING, ENTERTAINMENT, OTHER");
                        continue; // go back to menu without crashing
                    }

	    		 //Expense.Category category=Expense.Category.valueOf(sc.nextLine().toUpperCase());
	    		 if(getObjectByexpenseId(expenses,expenseId)!=null || description.isBlank() || amount<=0 ) {
	    			 System.out.println("Invalid");
	    			 continue;
	    		 }
	    		 Expense expense=new Expense(expenseId,description, amount, category);
	    		 expenses.add(expense);
	    	 }
	    	 else if (choice == 2) {
	    		 System.out.println("ID          Description      Amount       category");
	    		 System.out.println("====================================================");
	    		 getAllExpenses(expenses);
	    	 }
	    	 else if(choice == 3) {
	    		 System.out.println("Total spending : "+getTotalspending(expenses));
	    		 
	    	 }
	    	 else if( choice == 4) {
	    		 System.out.println("Highest Expense");
	    		 getHighestExpense(expenses);
	    	 }
	    	 else if (choice ==5) {
	    		 //filter by category
                 sc.nextLine();
                 System.out.println("Enter category (FOOD, TRAVEL, SHOPPING, ENTERTAINMENT, OTHER):");
                 String categoryInput = sc.nextLine().trim().toUpperCase();

                 Expense.Category category = null;
                try {
                    category = Expense.Category.valueOf(categoryInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid category. Please enter one of: FOOD, TRAVEL, SHOPPING, ENTERTAINMENT, OTHER");
                    continue; // go back to menu without crashing
                }

	    		 //System.out.println("Enter category :");
	    		// Expense.Category category=Expense.Category.valueOf(sc.nextLine().toUpperCase());
                 //sc.nextLine();
	    		 getFilterByCategory(expenses,category);
	    	 }
	    	 else if(choice ==6) {
	    		 System.out.println("Enter ExpenseId to delete");
	    		 expenseId=sc.nextInt();
	    		 Expense obj=getObjectByexpenseId(expenses, expenseId);
	    		 if (obj == null)
	    		 {
	    			 System.out.println("Expense not found");
	    			 continue;
	    		 }
	    		 expenses.remove(obj);
	    		 System.out.println("Expense deleted successfully");
	    	 }
	    	 else if(choice ==7) {
	    		 System.out.println("Thank you for using Expense Tracker!");
	    	 }
	    	 else {
	    		 System.out.println("Please enter valid choice");
	    	 }
	    	 
	     }while(choice!=7);
}
}
