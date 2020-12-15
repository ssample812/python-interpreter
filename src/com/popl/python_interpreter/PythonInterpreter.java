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
		variables.put("charmender_HP", "5");
		variables.put("squirtle_HP", "2");
		variables.put("num", "3");
		variables.put("name", "Sam");
		variables.put("turn", "1");

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
				evaluate(data);
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

		for (String seg: str_segments) {
			if (seg.charAt(0) == '\"') {
				output += seg.substring(1, seg.length() - 1);
			} else if (seg.startsWith("str")) {
				String var = seg.substring(line.indexOf("(") + 1, seg.length() - 1);
				output += variables.get(var);
			} else {
				output += variables.get(seg);
			}
		}
		System.out.println(output);
	}

	private static boolean evaluate(String line) {
		boolean result = true;
		String statements[] = line.split("and");
		int x;
		int y;

		System.out.println(line);
		System.out.println(statements[0]);

		for (String statement: statements) {
			if (statement.contains("==")) {
				String[] factors = statement.split("==");
				x = Integer.parseInt(variables.get(factors[0].strip()));
				y = Integer.parseInt(factors[1].strip());
				result = result && (x == y);
			} else if (statement.contains("!=")) {
				String[] factors = statement.split("!=");
				x = Integer.parseInt(variables.get(factors[0].strip()));
				y = Integer.parseInt(factors[1].strip());
				result = result && (x != y);
			} else if (statement.contains(">=")) {
				String[] factors = statement.split(">=");
				x = Integer.parseInt(variables.get(factors[0].strip()));
				y = Integer.parseInt(factors[1].strip());
				result = result && (x >= y);
			} else if (statement.contains("<=")) {
				String[] factors = statement.split("<=");
				x = Integer.parseInt(variables.get(factors[0].strip()));
				y = Integer.parseInt(factors[1].strip());
				result = result && (x <= y);
			} else if (statement.contains(">")) {
				String[] factors = statement.split(">");
				x = Integer.parseInt(variables.get(factors[0].strip()));
				y = Integer.parseInt(factors[1].strip());
				result = result && (x > y);
			} else if (statement.contains("<")) {
				String[] factors = statement.split("<");
				x = Integer.parseInt(variables.get(factors[0]));
				y = Integer.parseInt(factors[1].strip());
				result = result && (x < y);
			}
		}
		System.out.println(result);
		return result;
	}

	/*
		charmender_HP > 0 and squirtle_HP > 0
		turn == 1
		charmender_HP >= 1
		squirtle_HP >=1
		num > 0
		num == 2
		num%i==0
		eq1 != 0
	*/
}
