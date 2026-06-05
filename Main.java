import java.util.Scanner;


public class Main{

	public static void main(String[] args){

	Scanner scan = new Scanner(System.in);

	TaskManager task = new TaskManager();

	FileHandler file = new FileHandler();

	task.setTasks(file.loadTask());

	while(true){

		System.out.println("==== To-Do App ======");

		System.out.println("1.) Add task");

		System.out.println("2.) View all tasks");

		System.out.println("3.) Mark task as complete");

		System.out.println("4.) Delete task");

		System.out.println("5.) Save & Exit");

		System.out.println("===========================");
	
		System.out.println("Enter your choice: ");

		int userChoice = scan.nextInt();
		scan.nextLine();

		if(userChoice < 1 || userChoice > 5){

			System.out.println("Invalid choice. Enter a number 1-5");

		}else if(userChoice == 1){

			System.out.println("Enter Title: ");

			String title = scan.nextLine();

			System.out.println("Enter Due Date: ");

			String dueDate = scan.nextLine();

			System.out.println("Enter Priority (High/Medium/Low): ");
			String priority = scan.nextLine();

			task.addTask(title, dueDate, priority);

		}else if(userChoice == 2){
			task.viewTasks();

		}else if(userChoice == 3){

			System.out.println("Enter task number: ");
			int index = scan.nextInt();

			task.markComplete(index);

		}else if(userChoice == 4){

			System.out.println("Enter task number: ");
			int index = scan.nextInt();

			task.deleteTask(index);

		}else{
			file.saveTask(task.getTasks());
			System.out.println("Tasks saved. Goodbye!");
			break;
			}
		}
	
	}
	

}

