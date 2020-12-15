package com.popl.python_interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;



public class PythonInterpreter {

	private static HashMap<String, String> variables = new HashMap<>();

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Scanner fileScanner;
		String fileName;
		File pythonFile;
		List<String> fileLines = new ArrayList<>();

		variables.put("charmender_attack", "knife");
		variables.put("squirtle_HP", "5");
		variables.put("num", "3");
		variables.put("name", "Sam");

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
				print(data);
			}
			fileScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// for(String line : fileLines) {
		// 	System.out.println(line);
		// }

		scan.close();
	}

	private static void print(String line) {
		String str = line.substring(line.indexOf("(") + 1, line.length() - 1);
		String str_segments[] = str.split("\\+");

		String output = "";

		for (String seg: str_segments){
			if (seg.charAt(0) == '\"') {
				output += seg.substring(1, seg.length() - 1);
			} else if (seg.startsWith("str")) {
				String var = seg.substring(4, seg.length() - 1);
				output += variables.get(var);
			} else {
				output += variables.get(seg);
			}
		}
		System.out.println(output);
	}

	private static boolean evaluate(String line) {
		return true;
	}
}
