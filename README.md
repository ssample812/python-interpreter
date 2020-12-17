# Python Interpreter

## Project Explanation
For our project, we implemented a basic interpreter for Python in Java.  First, parse the file provided by the user, storing it as a global array of Strings.  Next, we loop through each line of the file using its line number, calling the interpretLine() function on each.  This function detects what the purpose of the line is (ex: print, for, while, etc), and calls on another function to handle its execution.  For example, a line beginning with "if" will be handled by the handleIf function.  The next line to interpret is determined using the index returned by the interpretLine() function when it is complete.

## Team Members
- Rebecca Parker
- Samantha Sample
- Kaitlyn Zahn

## Requirements
- if/else blocks
- Variable definitions
- while and for Loops
- Arithmetic operators (+, -, *, /, %, ^)
- Assignment operators (=, +=, -=, *=, /=, ^=, %=)
- Conditional statements (<, <=, >, >=, ==, !=)
- Comments (lines that start with a ‘#’)
- Support Output operation (print function).
- *BONUS*: Syntax error message (this is where we did the accept/reject string. If the given code aka. grammar is not a Python language, reject it. In other words, throw a syntax error message).

## How to Use/Run the Interpreter
- In your command line, navigate to the src/com/popl/python-interpreter directory and run "java -cp bin com.popl.python_interpreter.PythonInterpreter".  When prompted for a file, you can enter "python_test_code.py".  You must have at least JDK 11 for the program to run successfully.

## Grading Criteria
- A working interpreter – 60%
    - (15 pts)if/else blocks
    - (15 pts) Variable definitions
    - (15 pts) while and for Loops
    - (15 pts) Arithmetic operators (+, -, *, /, %, ^)
    - (15 pts) Assignment operators (=, +=, -=, *=, /=, ^=, %=)
    - (15 pts) Conditional statements (<, <=, >, >=, ==, !=)
    - (5 pts) Comments (lines that start with a ‘#’)
    - (5 pts) Support Output operation (print function).
- Report (Writing README.md on your Github repo., see “Github Repository Page” section) – 10%
- Individual contribution – 30%
- Syntax error message (10 pts directly added to you project grade)

