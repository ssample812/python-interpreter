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

		System.out.println("Enter the name of your Python file (ex: script.py): ");
		fileName = scan.nextLine();

		try {
			pythonFile = new File(fileName);
			fileScanner = new Scanner(pythonFile);
			while (fileScanner.hasNextLine()) {
				String data = fileScanner.nextLine();
				if (!data.matches("#.*")) {
					fileLines.add(data);
				}
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		for(String line : fileLines) {
			System.out.println(line);
		}

		scan.close();
	}

}
