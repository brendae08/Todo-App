//File name: FileSys.java
// Author: Brenda Ebba
// Represents: A simple file system terminal reads and runs commands given by the user.

import java.util.Scanner; // importing scanner to read input from System.in
import java.util.ArrayList;

public class FileSys{


	public static String command;
	
	public static Directory root;
	public static Directory current;

	public static void createMethod(String arg, Scanner scan){ //Creates a new file in the current directory and reads its content by line

		Contents existing = current.findByName(arg);

		if(existing != null){
			System.out.println("ERROR: file or directory already exists");

		}else{
			String data = "";
			String input = scan.nextLine();


			while(!input.contains("~")){ //Keeps reading lines until the user types ~
							
				data += input + "\n";
				input = scan.nextLine();
			}
			//Stops at the ~ and saves everything before it			
			data += input.substring(0, input.indexOf("~"));
			Files newFile = new Files(arg);
			newFile.data = data;
			current.children.add(newFile);
		}

	}

	public static void catMethod(String arg){ //Prints the contens of a file in the current directory

		Contents existing = current.findByName(arg);// Finds the file in the current directory

		if(existing == null){ //If there is no exisitng file then it will print no contents in the directory
			System.out.println("ERROR: There are no contents in the file");

		}else if(existing instanceof Directory){ //If the existing file is a directory it will print, an error, since cat can't be used on a directory.
			System.out.println("ERROR: cannot call cat on a directory");

		}else{ //otherwise it will cast it to a file object and print out the contents of the file
			Files theFile = (Files) existing;
			System.out.println(theFile.data);
		}	

	}

	public static void rmMethod(String arg){ //Removes a file from the current directory

		Contents existingFile = current.findByName(arg); //Tries to find the file in the current directory, returns null if not found

		if(existingFile == null){ //If the file the user enters doesn't exist it will print an error
			System.out.println("ERROR: File does not exists");

		}else if(existingFile instanceof Directory){ //If the existing file is actually a directory it will print error, because you can't call rm on a directory
			System.out.println("ERROR: cannot call rm on a directory");

		}else{
			current.children.remove(existingFile); //otherwise it will removes the file
		}
	}

	public static void mkdirMethod(String arg){ //Makes a directory

		Contents existingDir = current.findByName(arg); //Checks if there's already a directory with that name

		if(existingDir == null){ //If there is no existing directory with that name then it will create the directory
			Directory newDir = new Directory(arg);
			newDir.parent = current;
			current.children.add(newDir); //Adds the directory as a child

		}else{
			System.out.println("ERROR: directory or file already exists"); //If that name already exists it prints this error saying that the directory or file already exists.
		}
	}

	public static void rmdirMethod(String arg){ //Removes a directory from the current directory

		Contents existingDir = current.findByName(arg); //Checks to see if that directory exists

		if(existingDir == null){
			System.out.println("ERROR: Directory does not exists"); //If the directory doesn't exists it will print this error

		}else if(existingDir instanceof Files){ //If the directory is actually a file then it will call this error
			System.out.println("ERROR: Cannot call rmdir on a file"); //Error, because you can't call rmdir on a file

		}else{
			current.children.remove(existingDir); //else removes the directory
		}
	}

	public static void cdMethod(String arg){ //changes the current directory to the one the user enters

		String[] pathParts = arg.split("/"); //Splits the pathway by / so we can look through each part one at a time
		Directory temp = current;

		if(arg.startsWith("/")){ //If the path starts with / then it will start looking from the root
			temp = root;
		}

		boolean error = false; // Keeps track of whether an error occurs so we don't have to update if it crashes 
		
		for(int i = 0; i < pathParts.length; i++){ //Loops through each part of the path and looks for the correct directory
			String part = pathParts[i];

			if(part.isEmpty() || part.equals(".")){ //If the part is empty then it will just skip with continue.

				continue;

			}else if(part.equals("..")){ //Goes back to the parent directory
				if(temp.parent == null){
					temp = root;

				}else{
					temp = temp.parent; //Otherwise tries to find and move into that parent directory
				}
						
			}else{
				Contents next = temp.findByName(part);
				if(next == null){
					System.out.println("ERROR: Directory doesn't exist");
					error = true;
					break;

				}else if(next instanceof Files){
					System.out.println("ERROR: cannot cd into a file");
					error = true;
					break;

				}else{
					temp = (Directory) next;
				}
			}
		}

		if(!error){ //Only updates current if no errors occur
			current = temp;						
		}

	}

	public static void findMethod(String arg){ //Tries to find either a file or a directory with the given name starting from teh current directory.
		if(current == root){
			current.findMethod(arg, ""); //calls the find method with the correct arguments if its the root

		}else{
			String path = current.name;
			Directory temp = current;

			while(temp.parent != null){ //Going through the parent directories to build the full pathway
				temp = temp.parent;
				path = temp.name + "/" + path;
			}
			current.findMethod(arg, path);
		}
					
	}



	public static void lsMethod(){ //Lists all of the contents in the directory

		current.directorySort();

		for(int i = 0; i < current.children.size(); i++){

			if(current.children.get(i) instanceof Directory){ // If it's a directory it will print the name of the name with (*) to show its a directory 
				System.out.println(current.children.get(i).name + " (*)");

			}else{
				System.out.println(current.children.get(i).name); //Just prints the file name
			}
		}

	}

	public static void pwdMethod(){ //Prints the pathway of a directory

		String path = current.name;
		Directory temp = current;

		while(temp.parent != null){ //Goes through the parent directories to build the full pathway
			temp = temp.parent;
			path = temp.name + "/" + path;
		}
		System.out.println(path);
		
	}

	public static void main(String[] args){ //Does error checking for the empty commands and if there are no errrors it will call the correct method depending on the comamand.

		root = new Directory("");
		current = root;

		Scanner scan = new Scanner(System.in); //Creating a scanner object that reads from System.in
		System.out.print("prompt> "); //Prompts the user



		while(scan.hasNextLine()){ //This for loop will read the commands until there is no more input or until the user enters "exit"

			
			String line = scan.nextLine();

			if(line.isEmpty()){ // Ignores the empty lines
				continue;
			}

			String[] parts = line.split(" ",2); //Splits the input into the command and the argument
			command = parts[0];

			String arg = "";
			if(parts.length > 1){
				arg = parts[1].trim();
			}

			if(command.equals("exit")){ //If user enters exit the program will quit
				break;

			}else if(command.equals("create")){ //Create a new file with the given file name
	
				if(arg.isEmpty()){
					System.out.println("ERROR: file name required"); //If no file name then the program will print this error with an explanation for the error.
				}else{

					createMethod(arg, scan);
					
				}

			}else if(command.equals("cat")){ //Print the contents of the given file
				
				if(arg.isEmpty()){
					System.out.println("ERROR: file name required");
				
				}else{
					catMethod(arg);				
				}

			}else if(command.equals("rm")){ //Removes the given file from the current directory
				
				if(arg.isEmpty()){
					System.out.println("ERROR: file name required");
				
				}else{
					
					rmMethod(arg);
				}
			
			}else if(command.equals("mkdir")){ //Makes a new directory inside of the current directory
				
				if(arg.isEmpty()){
					System.out.println("ERROR: directory name required"); //If no directory name is given it will print this error, with the given explanation
				}else{
					
					mkdirMethod(arg);
				}
			
			}else if(command.equals("rmdir")){ //Remove the given directory and all its contents
				
				if(arg.isEmpty()){
					System.out.println("ERROR: directory name required");
				}else{
					
					rmdirMethod(arg);
				}
			
			}else if(command.equals("cd")){ //Changes the current directory to the directory the user inputs.
				
				if(arg.isEmpty()){
					System.out.println("ERROR: directory name required");
				
				}else{
					cdMethod(arg);
				}
					
			
			}else if(command.equals("find")){ //Find all the files or directories with the given name
				
				if(arg.isEmpty()){
					System.out.println("ERROR: directory name required");
				
				}else{

					findMethod(arg);
					
				}
			
			}else if(command.equals("ls")){ //List all the files and directories in the current directory
				lsMethod();
			
			}else if(command.equals("du")){ //Prints the total size of all the files in the current directory
				
				System.out.println(current.duRecursive());
			
			}else if(command.equals("pwd")){ //Prints the full pathway to the current directory
				
				if(current == root){
					System.out.println("/");
				
				}else{

					pwdMethod();
				}
				
			}else{
				System.out.println("ERROR: unknown command "  + command + "'"); //If an invalid command is given then the program will print an Error with the unknown command explanation. Along with the invalid command.
			}
			System.out.print("prompt> ");
		}
		scan.close(); //Closing the scanner.
	}






}

