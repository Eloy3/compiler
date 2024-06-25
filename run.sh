#!/bin/bash

# Navigate to the project directory (compilador)
cd "$(dirname "$0")"

# Compile all Java files
javac -d out -sourcepath src src/main/Main.java

# Check if the compilation was successful
if [ $? -eq 0 ]; then
    # Run the Java program
    java -cp out main.Main "$@"
else
    echo "Compilation failed."
fi
