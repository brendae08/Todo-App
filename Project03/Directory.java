// Author: Brenda Ebba
// File name: Directory.java
// Represents: A directory in the file system, which holds files and ohter directories as well

import java.util.ArrayList;


public class Directory extends Contents{

	public Directory parent; //Points to the parent directory : StackOverFlow
	public ArrayList<Contents> children = new ArrayList<>(); //Holds all files and directories inside this directory

	public Contents findByName(String name){ //Searches for a file or directory by name in teh current directory, returns null if not found

		for(int i = 0; i < children.size(); i++){ //Loops through all children to find a match

			if(children.get(i).name.equals(name)){
				return children.get(i); //Returns the match if found
			}
		}
		return null; //Returns null if nothing was found
	}



	public Directory(String directoryName){ //Constructor that sets the directory name
		super(directoryName);
	}

	public void directorySort(){// Sorts the children alphabetically by name using bubble sort
		for(int i = 0; i < children.size()-1; i++){
			for(int j = 0; j < children.size()-i-1; j++){
				if(children.get(j).name.compareTo(children.get(j+1).name) > 0){

					Contents temp = children.get(j);
					children.set(j, children.get(j+1));
					children.set(j+1, temp);
				}
			}
		}

	}



	public int duRecursive(){ // Adds up the toal size of all files, and uses recursion to go through subdirectories

		int total = 0;
		for(int i = 0; i < children.size(); i++){
			if(children.get(i) instanceof Files){ //If it's a file, add its size to the total
				Files f = (Files) children.get(i);
				total += f.data.length();
			}else{ // it's a directory, recursively add up its contents
				Directory d = (Directory) children.get(i);
				total += d.duRecursive();
			}
		}

		return total;
	}


	

	public void findMethod(String command, String path){ // Searches for a file or directory by name, and uses recursion to check subdirectories as well

		for(int i = 0; i < children.size(); i++){
			String childPath = path + "/" + children.get(i).name; //Builds the full pathway for each child
			if(children.get(i).name.equals(command)){ //If the name matches, it prints the full path
				System.out.println(childPath);

			} 
			if(children.get(i) instanceof Directory){ // It it's a directory, recursively search inside it
				Directory child = (Directory) children.get(i);
				child.findMethod(command, childPath);
			}
		}


	}
}