import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler{

	public void saveTask(ArrayList<Task> tasks){

		try{
			FileWriter fw = new FileWriter("tasks.txt");
			for(int i = 0; i < tasks.size(); i++){

				fw.write(tasks.get(i).getTitle() + "," + tasks.get(i).getDueDate() + "," + tasks.get(i).getPriority() + "," + tasks.get(i).isComplete() + "\n");
			}
			fw.close();
		}catch(IOException e1){
			System.out.println("Error saving tasks.");
		}
		
	}

	public ArrayList<Task> loadTask(){

		ArrayList<Task> tasks = new ArrayList<>();
		
		try{

			String line;
			BufferedReader br = new BufferedReader(new FileReader("tasks.txt"));

			while((line = br.readLine()) != null){
				String[] parts = line.split(",");

				Task t = new Task(parts[0], parts[1], parts[2]);

				if(parts[3].equals("true")){
					t.markComplete();
				}
				tasks.add(t);
			}
			br.close();
			

		}catch(IOException e2){
			System.out.println("Error Reading your tasks.");
		}
		return tasks;

		
	}
}