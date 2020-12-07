package com.popl.python_interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class PythonInterpreter {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Scanner fileScanner;
		String fileName;
		File pythonFile;
		List<String> fileLines = new ArrayList<>();

		//prompt user to insert their file name and save variable for the file
		System.out.println("Enter the name of your Python file (ex: script.py): ");
		fileName = scan.nextLine();

		//try to read the file
		try {
			//create a python file and scan it
			pythonFile = new File(fileName);
			fileScanner = new Scanner(pythonFile);
			//for every line in the file
			while (fileScanner.hasNextLine()) {
				String data = fileScanner.nextLine();
				//ignore lines with comments, read file
				if (!data.matches("#.*")) {
					fileLines.add(data);
				}
			}
			fileScanner.close();
		} 
		//throw errors
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		for(String line : fileLines) {
			System.out.println(line);
		}

		scan.close();
	}

}
