package back_end;
import data_structures.VariableTable;
import front_end.simbols.Simbol;
import data_structures.SymbolTable;
import data_structures.Variable;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Stack;

public class IntermediateCode {

    private int n_var, label_num, disp = 0;
    private BufferedWriter bw;
    private final Stack<String> true_stack, false_stack, end_stack, start_stack, pproc;
    private final ArrayList<String> operands, instruction_list;
    private String temp_id = null, cur_type = "";
    private static final String FILE_PATH = "output/codiIntermitg.txt";
    private final VariableTable tv;
    
    public IntermediateCode(VariableTable tv){
        this.true_stack = new Stack<>();
        this.false_stack = new Stack<>();
        this.end_stack = new Stack<>();
        this.start_stack = new Stack<>();
        this.pproc = new Stack<>();

        this.operands = new ArrayList<>();
        this.instruction_list = new ArrayList<>();
        this.tv = tv;
        
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, true), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            
            e.printStackTrace();
        }
    }
    public void assign(String dest, String val) {

        instruction_list.add(dest + " = " + val + "\n");
    }

    public String newTempVar(String type) {

        if (type.equals("")) type = cur_type;

        tv.addRow(new Variable
            ("t" +
                    n_var,
                    n_var,
                    pproc.peek(),
                tv.calculateStore(type, ""),
                    disp,
            type,"")
        );

        incDisplacement(tv.calculateStore(type, ""));
        cur_type = type;
        
        return "t" + n_var++;
    }


    public String newTempVar(String type, String val) {
        if (type.equals("")) {
            type = cur_type;
        }

        tv.addRow(new Variable
            ("t" + n_var,
                    n_var,
                    pproc.peek(),
                tv.calculateStore(type, val),
                    disp,
                type,
            val)
        );

        cur_type = type;
        incDisplacement(tv.calculateStore(type, val));
        return "t" + n_var++;
    }

    public String newVar(String name, String type) {

        tv.addRow(new Variable
            (name,
                    n_var,
                    pproc.peek(),
                tv.calculateStore(type, ""),
                    disp,
            type, "")
        );

        incDisplacement(tv.calculateStore(type, ""));
        n_var++;

        return name;
    }

    public String newArrayElement(String name, String type, int i, SymbolTable ts) {
        return newVar(name+"["+i+"]", type);
    }

    public String newVar(String name, String type, String value) {
        
        tv.addRow(new Variable
            (name,
                    n_var,
                    pproc.peek(),
                tv.calculateStore(type, value),
                    disp,
                type,
            value)
        );

        incDisplacement(tv.calculateStore(type, value));
        n_var++;

        return name;
    }

    public String newVarLabel(String name, String type, String value) {
        
        tv.addRow(new Variable
            (name,
                    n_var,
                    pproc.peek(),
                tv.calculateStore(type, value),
                    disp,
                type,
            value)
        );

        incDisplacement(tv.calculateStore(type, value));
        n_var++;

        return name;
    }
    
    public String newVarArray(String name, String type, int length) {
        int store = tv.calculateStore(type, "") * length;
        tv.addRow(new Variable
                (name,
                        n_var,
                        pproc.peek(),
                        store,
                        disp,
                        type, "")
        );

        incDisplacement(store);
        n_var++;

        return name;
    }

    public void removeGotoElse() {

        for (int i = instruction_list.size() - 1; i >= 0; i--) {

            if (instruction_list.get(i).startsWith("goto")) {
                instruction_list.remove(i);

                break;
            }
        }
    }

    public void closeFile() {

        try {
            for (String s : instruction_list) {
                bw.write(s);
            }
            bw.close();
        } catch (IOException e) {e.printStackTrace();}
    }


    public String newLabel(){
        String label = "e" + label_num++;
        //newVar(label, "bool");
        return label;}

    //Procediment que afegeix una instruccio de 3@C
    public void generateCode(String code){
        instruction_list.add(code);
    }
        
    public void generateCode(String param, String arg, SymbolTable ts){
        switch(param){
            case "param_s":
                instruction_list.add("param_s " + arg + "\n");
                break;
            case "param_c":
                instruction_list.add("param_c " + arg + "\n");
                break;
        }
    }

    public void generateCode(String operation, String dest, String value, SymbolTable ts) {
        switch (operation) {
            case "param_s":
            case "param_c":
                instruction_list.add(operation + " " + dest + "\n");
                break;
            case "assign":
                instruction_list.add(dest + " = " +value + "\n");
                break;
        }
    }
    
    public void generateNewVarAssign(Simbol target, String tempVar, String value, SymbolTable ts) {
        instruction_list.add(newVar(target.getNom(), target.getTipus(), value) + " = " + tempVar + "\n");
    }

    public void generateNewVarAssign(Simbol target, String tempVar, SymbolTable ts) {
        instruction_list.add(newVar(target.getNom(), target.getTipus()) + " = " + tempVar + "\n");
    }

    public void generateAssignComposite(String tempVar, String valueA, String operator, String valueB, SymbolTable ts) {
        instruction_list.add(tempVar + " = " + valueA + " " + operator + " " + valueB + "\n");
    }

    public Variable getVar(String id){
        return this.tv.getVar(id);
    }


    public String getTemp_id(){return temp_id;}
    

    public void setTemp_id(String temp_id){this.temp_id = temp_id;}

    public Stack<String> getTrue_stack(){return true_stack;}

    public Stack<String> getFalse_stack(){return false_stack;}

    public Stack<String> getEnd_stack(){return end_stack;}

    public Stack<String> getStart_stack(){return start_stack;}

    public Stack<String> getPproc(){return pproc;}

    public void pop(Stack<String> stack){stack.pop();}

    public String getTop(Stack<String> stack){return stack.peek();}

    public void push(Stack<String> stack, String label){stack.push(label);}

    public ArrayList<String> getOperands() {
        return operands;
    }
    
    public void incDisplacement(int val){
        disp += val;}

    public void resetDisp(){
        disp = 0;}
    
}
