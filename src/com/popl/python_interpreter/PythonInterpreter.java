// package com.popl.python_interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class PythonInterpreter {

    private static HashMap<String, String> variables = new HashMap<String, String>();
    private static List<String> fileLines = new ArrayList<String>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Scanner fileScanner;
        String fileName;
        File pythonFile;
      
        // temporary, for testing
        // variables.put("charmender_attack", "knife");
        // variables.put("charmender_HP", "5");
        // variables.put("squirtle_HP", "2");
        variables.put("num", "2");
        variables.put("i", "2");
        variables.put("eq1", "1");
        // variables.put("name", "Sam");
        // variables.put("turn", "1");
        // variables.put("eq1", "1");
        
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
                if (!data.matches("#.*") && !data.matches("\s*")) {
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

        int lineNum = 0;

        // for every line in the file
        while(lineNum < fileLines.size()) {
            // System.out.println(lineNum + ": " + fileLines.get(lineNum));
            // call interpretLine function
            lineNum = interpretLine(lineNum);
            if(lineNum < 0) {
                System.out.println("An error occurred.");
            }
        }

        scan.close();
    }

    // function to interpret the given line
    private static int interpretLine(int lineNum) {
		String line = fileLines.get(lineNum);
        if(line.matches("\s*while.*")) {
            // call while function
            lineNum++;
        }
        else if(line.matches("\s*for.*")) {
            // call for function
            handleFor(line);
            lineNum++;
        }
        else if(line.matches("\s*if.*")) {
            // call if function
            lineNum = handleIf(lineNum);
        }
        else if(line.matches("\s*print.*")) {
            // call print function
            print(line);
            lineNum++;
        }
        else if(line.matches("\s*[a-zA-Z_]+.*")){
            // call variable handling
            lineNum = handleVariable(line, lineNum);
        }
        else if(line.matches("\s*")) {
            // ignore, blank line
            lineNum++;
        } 
        else {
            // otherwise, output an error and quit the program
            System.out.println("An error occurred.\n");
            System.out.println(line);
            lineNum = -1;
        }
        return lineNum;
    }

    // function to handle assignment operators
    private static int handleVariable(String line, int lineNum) {
        // to temporarily ignore until if function implemented
        if(line.contains("elif") || line.contains("else")) {
            return ++lineNum;
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
		else if(line.contains("*=")) {
            String[] tokens = line.split("*=");
            if(variables.containsKey(tokens[0].trim())) {
                Integer oldNum = Integer.parseInt(variables.get(tokens[0].trim()));
                Integer newNum = oldNum * Integer.parseInt(variables.get(tokens[1].trim()));
                variables.replace(tokens[0].trim(), Integer.toString(newNum));
            }
            else {
                // invalid operation, variable does not exist
                lineNum = -2;
            }
		}
		else if(line.contains("/=")) {
            String[] tokens = line.split("/=");
            if(variables.containsKey(tokens[0].trim())) {
                Integer oldNum = Integer.parseInt(variables.get(tokens[0].trim()));
                Integer newNum = oldNum / Integer.parseInt(variables.get(tokens[1].trim()));
                variables.replace(tokens[0].trim(), Integer.toString(newNum));
            }
            else {
                // invalid operation, variable does not exist
                lineNum = -2;
            }
		}
		else if(line.contains("^=")) {
            String[] tokens = line.split("^=");
            if(variables.containsKey(tokens[0].trim())) {
                Integer oldNum = Integer.parseInt(variables.get(tokens[0].trim()));
                double newNum = Math.pow(oldNum, Integer.parseInt(variables.get(tokens[1].trim())));
                Integer newNumInt = (int)newNum;
                variables.replace(tokens[0].trim(), newNumInt.toString());
            }
            else {
                // invalid operation, variable does not exist
                lineNum = -2;
            }
		}
		else if(line.contains("%=")) {
            String[] tokens = line.split("%=");
            if(variables.containsKey(tokens[0].trim())) {
                Integer oldNum = Integer.parseInt(variables.get(tokens[0].trim()));
                Integer newNum = oldNum % Integer.parseInt(variables.get(tokens[1].trim()));
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
                    newValue = calculate(tokens[1]).toString();
                }
                else {
                    newValue = tokens[1].trim();
                }
                variables.replace(tokens[0].trim(), newValue);
            }
            else {
                // check for an equation
                if(tokens[1].matches("(?:[0-9 ()]+[*+/-])+[0-9 ()]+")) {
                    newValue = calculate(tokens[1]).toString();
                }
                else {
                    newValue = tokens[1].trim();
                }
                variables.put(tokens[0].trim(), newValue);
            }
        }
        return ++lineNum;
    }

    // function to handle print
    private static void print(String line) {
        String str = line.substring(line.indexOf("(") + 1, line.length() - 1);
        String str_segments[] = str.split("\\+");

        String output = "";

        for (String seg: str_segments) {
            if (seg.charAt(0) == '\"') {
                output += seg.substring(1, seg.length() - 1);
            } else if (seg.startsWith("str")) {
                String var = seg.substring(seg.indexOf("(") + 1, seg.length() - 1);
                output += variables.get(var);
            } else {
                output += variables.get(seg);
            }
        }
        System.out.println(output);
    }

    // function to evaluate conditional statements
    private static boolean evaluate(String line) {
        boolean result = true;
        String statements[] = line.split("and");
        int x;
        int y;

        // for every statement
        for (String statement: statements) {
            // handle ==
            if (statement.contains("==")) {
                String[] factors = statement.split("==");
                String xStr = factors[0].strip();
                if (xStr.contains("%")) {
                    String[] vars = xStr.split("%");
                    x = Integer.parseInt(variables.get(vars[0])) % Integer.parseInt(variables.get(vars[1]));
                } else {
                    x = Integer.parseInt(variables.get(xStr));
                }
                y = Integer.parseInt(factors[1].strip());
                result = result && (x == y);
            } 
            // handle !=
            else if (statement.contains("!=")) {
                String[] factors = statement.split("!=");
                x = Integer.parseInt(variables.get(factors[0].strip()));
                y = Integer.parseInt(factors[1].strip());
                result = result && (x != y);
            } 
            // handle >=
            else if (statement.contains(">=")) {
                String[] factors = statement.split(">=");
                x = Integer.parseInt(variables.get(factors[0].strip()));
                y = Integer.parseInt(factors[1].strip());
                result = result && (x >= y);
            } 
            // handle <=
            else if (statement.contains("<=")) {
                String[] factors = statement.split("<=");
                x = Integer.parseInt(variables.get(factors[0].strip()));
                y = Integer.parseInt(factors[1].strip());
                result = result && (x <= y);
            } 
            // handle >
            else if (statement.contains(">")) {
                String[] factors = statement.split(">");
                x = Integer.parseInt(variables.get(factors[0].strip()));
                y = Integer.parseInt(factors[1].strip());
                result = result && (x > y);
            } 
            // handle <
            else if (statement.contains("<")) {
                String[] factors = statement.split("<");
                x = Integer.parseInt(variables.get(factors[0]));
                y = Integer.parseInt(factors[1].strip());
                result = result && (x < y);
            }
        }
        // return boolean for whether the condition is met
        return result;
    }

    // function to count the number of tabs in each line
    private static int numTabs(String line) {
        char charLine[] = line.toCharArray();
        int spaces = 0;
        char temp = charLine[0];

        // count the number of spaces
        while (temp == ' ') {
            spaces++;
            temp = charLine[spaces];
        }
        // return the number of tabs (tabs = spaces / 4)
        return spaces / 4;
    }

    // function to handle if and else
    private static int handleIf(int lineNum) {
        // creating variables
        String line;
        String condition;
        int numParentTabs;
        int numTabs;
        int currentLineNum;
        boolean consider;
        boolean alreadyPassed = false;
        boolean loop = true;

        // get line numbers and number of tabs for a parent
        line = fileLines.get(lineNum);
        numParentTabs = numTabs(line);
        // grab the condition
        condition = line.substring(line.indexOf("if")+3,  line.length()-1);
        // remove parenthesis from if statement
        condition = condition.replace(")", "");
        condition = condition.replace("(", "");
        // check whether the condition is met
        consider = evaluate(condition);
        // System.out.println("line: " + line + ", cons: " + consider);

        //increment the line number
        currentLineNum = lineNum + 1;

        // while loop
        while(loop)  {
            // get the current line number
            try {
                line = fileLines.get(currentLineNum);
            } catch (Exception e) {
                System.exit(0);
            }
            
            // get the number of tabs
            numTabs = numTabs(line);
            // System.out.println("num : " + currentLineNum + ", line: " + line);
            
            // if it is a child of the if statement
            if (numTabs == numParentTabs + 1) {
                // check the condition & interpret the line
                if (consider) {
                    alreadyPassed = true;
                    currentLineNum = interpretLine(currentLineNum);
                } 
                // if the condition is not met, go to the next line
                else {
                    currentLineNum++;
                }
            } 
            // if it is not a child of the right tab
            else {
                if (line.contains("elif")) {
                    // handle elif
                    if (alreadyPassed) {
                        consider = false;
                    } 
                    else {
                        condition = line.substring(line.indexOf("elif")+5,  line.length()-1);
                        condition = condition.replace(")", "");
                        condition = condition.replace("(", "");
                        consider = evaluate(condition);
                    }
                    currentLineNum++;
                }
                else if (line.contains("else")) {
                    // handle else
                    if (alreadyPassed) {
                        consider = false;
                    } else {
                        consider = true;
                    }
                    currentLineNum++;
                } else {
                    loop = false;
                }
            }
        }
        return currentLineNum;
    }

    // function to handle arithmetic operators
    private static Integer calculate(String line) {
        int secondValue;
        String operator;

        if (line.contains("int")) {
            return Integer.parseInt(variables.get("num")) / 2 + 2;
        }

        String newLine = line.replace("%", " % ");
        String elements[] = newLine.split(" ");
        int newValue = Integer.parseInt(elements[1]);
        int i = 2;

        while(i < elements.length - 1) {
            operator = elements[i];
            secondValue = Integer.parseInt(elements[i+1]);
            switch (operator){
                case "+":
                    newValue += secondValue;
                    break;
                case "-":
                    newValue -= secondValue;
                    break;
                case "/":
                    newValue /= secondValue;
                    break;
                case "*":
                    newValue *= secondValue;
                    break;
                case "%":
                    newValue %= secondValue;
                    break;
                default:
                    throw new RuntimeException("unknown operator: "+operator);
            }
            i += 2;
        }
        return newValue;
    }

    // // function to handle while loops
    // private static void handleWhile(String line) {

    // }

    // function to handle for loops
    private static void handleFor(String line) {
        int forTabs = numTabs(line);

        // if the for loop has valid syntax
        if (line.matches("\\s*for.*:")) {
            // get for loop down to just condition
            line = line.replace("for ","");
            line = line.substring(0, line.lastIndexOf(":")).strip();
        }
        // if the for loop has invalid syntax
        else{
            System.out.println("Syntax Error: Invalid format for for statement");
            System.exit(0);
        }


        // grabs variable
        String forVariable = line.substring(0, line.indexOf("in") - 1);

        // removes the in and closing )
        line = line.substring(line.indexOf("in") + 2, line.lastIndexOf(")"));

        // remove the range text
        line = line.replace("range(", "");

        // grab range values
        String lowerVariable = line.substring(1, line.indexOf(","));
        String upperVariable = line.substring(line.indexOf(",") + 2);

        Integer lower;
        Integer upper;
        System.out.println("lowerVariable is " + lowerVariable);
        System.out.println("upperVariable is " + upperVariable);

        // if the range values are variables
        if (variables.containsKey(lowerVariable) && variables.containsKey(lowerVariable)) {
            // get range values variable values
            String lowerValue = variables.get(lowerVariable);
            String upperValue = variables.get(upperVariable);

            // convert the value of the variables to integers
            lower = Integer.parseInt(lowerValue);
            upper = Integer.parseInt(upperValue);
        }
        // if the variable contains "int("
        else if (lowerVariable.contains("int(") || upperVariable.contains("int(")) {
            // String toInt = line.substring(line.indexOf("int(") + 4, line.indexOf(","));
            // // line = line.replaceAll("int\\(.*?\\)", toInt.toString());
            // // line = line.replaceAll("'", "");
            // // line = line.replaceAll("\"", "");
            // System.out.println("toInt is now " + toInt);
            // lower = 1;
            // upper = 2;
        }
        // if it's just a variable
        else {
            lower = Integer.parseInt(lowerVariable);
            upper = Integer.parseInt(upperVariable);
        }
        

        // THIS IS JUST FOR TESTING
        // System.out.println("lower bound is" + lowerVariable);
        // System.out.println("upper bound is" + upperVariable);
        // System.out.println("Value of lower is " + lower);
        // System.out.println("Value of upper is " + upper);

        // for loop
        // for (int i = lower; i < upper; i++) {
        //     if (!variables.containsKey(forVariable.replaceAll(" ", ""))) {
        //         String assignmentStatement = forVariable + " = " + lower;
        //         assignVariables(assignmentStatement);
        //     }
        //     temp = forLine + 1;
        //     while (countTabs(lines[temp]) > forTabs && !lines[temp].equals("")) {
        //         temp = interpretLine(lines, lines[temp], temp);
        //         if (breakStatement) {
        //             break;
        //         }
        //         temp++;
        //     }
        //     if (breakStatement) {
        //         breakStatement = false;
        //         vars.remove(forVariable);
        //         break;
        //     }
        //     String nextIteration = forVariable + " += 1";
        //     assignVariables(nextIteration);
        // }
        // vars.remove(forVariable);
        // return temp;
     }
}


