package com.popl.python_interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class PythonInterpreter {

    private static HashMap<String, String> variables = new HashMap<String, String>();
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

        int lineNum = 0;
        for(String line : fileLines) {
            lineNum = interpretLine(line, lineNum);
            if(lineNum < 0) {
                System.out.println("An error occurred.");
            }
        }

        scan.close();
    }

    private static int interpretLine(String line, int lineNum) {
        if(line.matches("\s*while.*")) {
            // call while function
            // return new line num
        }
        else if(line.matches("\s*for.*")) {
            // call for function
            // return new line num
        }
        else if(line.matches("\s*if.*")) {
            // call if function
            // return new line num
        }
        else if(line.matches("\s*print.*")) {
            // call print function
            // return new line num
        }
        else if(line.matches("\s*[a-zA-Z_]+.*")){
            // call variable handling
            // return new line num
            lineNum = handleVariable(line, lineNum);
        }
        else if(line.matches("\s*")) {
            // ignore, blank line
            lineNum++;
        } 
        else {
            System.out.println("An error occurred.\n");
            System.out.println(line);
            lineNum = -1;
        }
        return lineNum;
    }

    private static int handleVariable(String line, int lineNum) {
        // to temporarily ignore until if function implemented
        if(line.contains("elif") || line.contains("else")) {
            return lineNum++;
        }
        if(line.contains("-=")) {
            String[] tokens = line.split("-=");
            if(variables.containsKey(tokens[0].trim())) {
                Integer oldNum = Integer.parseInt(variables.get(tokens[0].trim()));
                Integer newNum = oldNum - Integer.parseInt(variables.get(tokens[1].trim()));
                variables.replace(tokens[0].trim(), Integer.toString(newNum));
            }
            else {
                // invalid operation, variable does not exist
                lineNum = -2;
            }
        }
        else if(line.contains("+=")) {
            String[] tokens = line.split("+=");
            if(variables.containsKey(tokens[0].trim())) {
                Integer oldNum = Integer.parseInt(variables.get(tokens[0].trim()));
                Integer newNum = oldNum + Integer.parseInt(variables.get(tokens[1].trim()));
                variables.replace(tokens[0].trim(), Integer.toString(newNum));
            }
            else {
                // invalid operation, variable does not exist
                lineNum = -2;
            }
        }
        else if(line.contains("=")) {
            String[] tokens = line.split("=");
            String newValue;
            if(variables.containsKey(tokens[0].trim())) {
                if(tokens[1].matches("(?:[0-9 ()]+[*+/-])+[0-9 ()]+")) {
                    // implement equation
                    newValue = "1";
                }
                else {
                    newValue = tokens[1].trim();
                }
                variables.replace(tokens[0].trim(), newValue);
            }
            else {
                // check for an equation
                if(tokens[1].matches("(?:[0-9 ()]+[*+/-])+[0-9 ()]+")) {
                    // implement equation
                    newValue = "1";
                }
                else {
                    newValue = tokens[1].trim();
                }
                variables.put(tokens[0].trim(), newValue);
            }
        }
        return lineNum++;
    }

}

