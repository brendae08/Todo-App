import java.util.ArrayList;


public class TaskManager{

	private ArrayList<Task> tasks;

	public TaskManager(){

		tasks = new ArrayList<>();
	}


	public void addTask(String title, String dueDate, String priority){

			Task newTask = new Task(title, dueDate, priority);
			tasks.add(newTask);
			System.out.println("Task added successfully!");
		
	}

	public void viewTasks(){

		if(tasks.isEmpty()){
			System.out.println("No tasks yet!");
			return;
		}

		System.out.println("\n===== Your Tasks =====");
		for(int i = 0; i < tasks.size(); i++){
			System.out.println((i + 1) + ". " + tasks.get(i));
		}
		System.out.println("======================\n");
	}

	public void markComplete(int index){
		if(index < 1 || index > tasks.size()){
			System.out.println("Invalid task number.");
			return;
		}
		tasks.get(index - 1).markComplete();
		System.out.println("Task marked complete!");
	}

	public void deleteTask(int index){
		if(index < 1 || index > tasks.size()){
			System.out.println("Invalid task number.");
			return;
		}
		String title = tasks.get(index - 1).getTitle();
		tasks.remove(index - 1);
		System.out.println("\"" + title + "\" deleted.");
	}

	public ArrayList<Task> getTasks(){
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks){
		this.tasks = tasks;
	}
}