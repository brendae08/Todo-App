


public class Task{

	private String title;
	private String dueDate;
	private String priority;
	private boolean isComplete;

	Task(String title, String dueDate, String priority){

		this.isComplete = false;
		this.title = title;
		this.dueDate = dueDate;
		this.priority = priority;
	}

	public String getTitle(){

		return title;
	}

	public String getDueDate(){

		return dueDate;
	}

	public String getPriority(){

		return priority;
	}

	public void markComplete(){

		isComplete = true;
	}

	public boolean isComplete(){

		return isComplete;
	}

	public String toString(){
		if(isComplete == false){

			return "[  ] " + title + " | Due: " + dueDate + " | Priority: " + priority;
		
		}else{

			return "[DONE] " + title + " | Due: " + dueDate + " | Priority: " + priority;

		}
	}
}