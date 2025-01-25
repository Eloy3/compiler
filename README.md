Uni project of a compiler for a made-up imperative language

If you're using vscode, you can use the provided launch.json and .vscode foler to compile and run the project.
You just need to set the 'args' input in the launch.json file to the name of the input file you want to use, and then you can run the project by pressing F5.

### Compiling the project

If you want to compile the project manually, open a terminal and navigate to the project directory. Then, run the following command:

```
javac -d out -sourcepath src src/main/Main.java
```

This will compile all the Java files in the `src` directory and output the compiled files to the `out` directory.


To run the project, open a terminal and navigate to the project directory. Then, run the following command:

```
java -cp out main.Main <input_file>
```

Replace `<input_file>` with the name of the input file you want to use. The input file should be in the `input` directory.

For example, to run the project with the `oddOrEven.txt` input file, you would run the following command:

```
java -cp out main.Main input/testBucle.txt
```

This will run the project with the `oddOrEven.txt` input file and create all of the output files to the `output` directory.

