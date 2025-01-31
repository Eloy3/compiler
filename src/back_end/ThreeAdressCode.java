

package back_end;

import data_structures.Procedure;
import data_structures.Variable;
import data_structures.Parameter;
import front_end.simbols.Simbol;
import front_end.simbols.Tipus;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ThreeAdressCode {

    private final String TAC_PATH = "output/codiIntermitg.txt";
    private final String TVAR_PATH = "output/Taula_variables.txt";
    private final String TPROC_PATH = "output/Taula_Procedures.txt";
    private final String TSYM_PATH = "output/Taula_simbols.txt";

    private InstructionsList instructionList = new InstructionsList();
    private ArrayList<Variable> tv = new ArrayList<>();
    private ArrayList<Procedure> tp = new ArrayList<>();
    private ArrayList<Simbol> ts = new ArrayList<>();
    
    public ThreeAdressCode() {
        loadTs();
        loadTv();
        loadTp();
        loadInstructions();
    }
    
    private void loadInstructions() {
        try (BufferedReader br = new BufferedReader(new FileReader(TAC_PATH))) {
            String instruction;
    
            while ((instruction = br.readLine()) != null) {
                instruction = instruction.trim();
                if (instruction.isEmpty()) continue;
    
                String[] parts = instruction.split("\\s+"); 
                if (parts.length == 0) {
                    System.err.println("Error: Empty instruction: " + instruction);
                    continue;
                }
    
                try {
                    switch (parts[0]) {
                        case "pmb":
                            instructionList.addInst(Operation.PMB, null, null, parts[1]);
                            break;
    
                        case "goto":
                            instructionList.addInst(Operation.GOTO, null, null, parts[1]);
                            break;
    
                        case "call":
                            instructionList.addInst(Operation.CALL, null, null, parts[1]);
                            break;
    
                        case "param_s":
                            instructionList.addInst(Operation.PARAM_S, null, null, parts[1]);
                            break;
    
                        case "param_c": 
                            instructionList.addInst(Operation.PARAM_C, parts[1], null, null);
                            break;
                        
                        case "param_t":
                            instructionList.addInst(Operation.PARAM_T, parts[1], null, null);
                            break;
    
                        case "rtn":
                            instructionList.addInst(Operation.RTN, parts[1], null, null);
                            break;
    
                        case "if":
                            handleConditional(parts, instruction);
                            break;
    
                        default:
                            if (instruction.contains("=")) {
                                handleAssignmentOrOperation(parts, instruction);
                            } else if (instruction.contains(":skip")) {
                                String label = instruction.split(":")[0].trim();
                                instructionList.addInst(Operation.SKIP, null, null, label);
                            } else {
                                System.err.println("Error: Unknown instruction: " + instruction);
                            }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing instruction: " + instruction + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private void handleConditional(String[] parts, String instruction) {

        if (parts.length < 5) {
            System.err.println("Error: Malformed conditional: " + instruction);
            return;
        }
        
        if (parts.length == 5) {
            instructionList.addInst(Operation.IF, parts[1], null, parts[4]);
            return;
        }
    
        String op1 = parts[1];
        String operator = parts[2];
        String op2 = parts[3];
        String label = parts[6];

        switch (operator) {
            case "!=":
                instructionList.addInst(Operation.IFDIFERENT, op1, op2, label);
                break;
            case "==":
                instructionList.addInst(Operation.IFIGUAL, op1, op2, label);
                break;
            case ">":
                instructionList.addInst(Operation.IFMAJOR, op1, op2, label);
                break;
            case "<":
                instructionList.addInst(Operation.IFMENOR, op1, op2, label);
                break;
            case "ILogic":
                instructionList.addInst(Operation.IFAND, op1, op2, label);
                break;
            case "OLogic":
                instructionList.addInst(Operation.IFOR, op1, op2, label);
                break;
            default:
                System.err.println("Error: Unsupported conditional operator: " + operator);
        }
    }
    
    
    private void handleAssignmentOrOperation(String[] parts, String instruction) {
        if (parts.length < 5) {
            // Handle simple assignments: `x = y`
            String[] assignParts = instruction.split("=");
            if (assignParts.length == 2) {
                String lhs = assignParts[0].trim(); // Left-hand side
                String rhs = assignParts[1].trim(); // Right-hand side


                if (lhs.matches(".+\\[\\w+]")) {
                    String arrayName = lhs.substring(0, lhs.indexOf('['));
                    String index = lhs.substring(lhs.indexOf('[') + 1, lhs.indexOf(']'));
                    instructionList.addInst(Operation.IND_ASS, rhs, index, arrayName);
                } 

                else if (rhs.matches(".+\\[\\w+]")) {
                    String arrayName = rhs.substring(0, rhs.indexOf('['));
                    String index = rhs.substring(rhs.indexOf('[') + 1, rhs.indexOf(']'));
                    instructionList.addInst(Operation.IND_VAL, arrayName, index, lhs);
                } 
                else {
                    instructionList.addInst(Operation.ASSIGNA, rhs, null, lhs);
                }
            } else {
                System.err.println("Error: Malformed assignment: " + instruction);
            }
        } else {
            // Handle arithmetic operations: `x = y + z`
            switch (parts[3]) {
                case "+" -> instructionList.addInst(Operation.SUMA, parts[2], parts[4], parts[0]);
                case "-" -> instructionList.addInst(Operation.RESTA, parts[2], parts[4], parts[0]);
                case "*" -> instructionList.addInst(Operation.MULTIPLICACIO, parts[2], parts[4], parts[0]);
                case "/" -> instructionList.addInst(Operation.DIVISIO, parts[2], parts[4], parts[0]);
                case "mod" -> instructionList.addInst(Operation.MODUL, parts[2], parts[4], parts[0]);

            // Handle logical operations: `x = y ILogic z`
                case "ILogic" -> instructionList.addInst(Operation.AND, parts[2], parts[4], parts[0]);
                case "OLogic" -> instructionList.addInst(Operation.OR, parts[2], parts[4], parts[0]);
                default -> System.err.println("Error: Unknown operation in instruction: " + instruction);
            }
        }
    }
    
    
    private void loadTv() {
        try {
            BufferedReader br = baseTable(TVAR_PATH);
            String variable;
    
            while ((variable = br.readLine()) != null) {
                if (!variable.trim().isEmpty() && !variable.startsWith("Nombre")) { 

                    String[] split = variable.trim().split("\\s{2,}");
                    
                    if (split.length >= 6) {
                        try {
                            String name = split[0].trim();
                            int nv = Integer.parseInt(split[1].trim());
                            String subprogram = split[2].trim();
                            int store = Integer.parseInt(split[3].trim());
                            int offset = Integer.parseInt(split[4].trim());
                            String type = split[5].trim();
                            
                            String value = (split.length > 6) ? split[6].trim() : null;
                            
                            tv.add(new Variable(name, nv, subprogram, store, offset, type, value));
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            System.err.println("Error parsing variable line: " + variable + " - " + e.getMessage());
                        }
                    } else {
                        System.err.println("Error: Insufficient fields in variable line: " + variable);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private void loadTp() {
        try {
            BufferedReader br = baseTable(TPROC_PATH);
            if (br == null) {
                System.err.println("BufferedReader is null; check file path or permissions.");
                return;
            }
    
            String proc;
            while ((proc = br.readLine()) != null) {
                if (!proc.trim().isEmpty() && !proc.startsWith("NP")) { 
                    String[] split = proc.trim().split("\\s{2,}");
                    
                    if (split.length >= 5) {
                        try {
                            int np = Integer.parseInt(split[0].trim());
                            int depth = Integer.parseInt(split[1].trim());
                            String startLabel = split[2].trim();
                            String paramsStr = split[3].trim();
                            int localVarSize = Integer.parseInt(split[4].trim());
                            String returnTypeStr = split[5].trim();
    
                            Tipus returnType;
                            try {
                                returnType = Tipus.valueOf(returnTypeStr.toUpperCase());
                            } catch (IllegalArgumentException | NullPointerException e) {
                                System.err.println("Unknown return type in procedure: " + returnTypeStr);
                                returnType = null;
                            }
    
                            tp.add(new Procedure(
                                    np,
                                    depth,
                                    startLabel,
                                    (paramsStr.equals("[]")) ? new ArrayList<Parameter>() : extractParamsTv(paramsStr,startLabel),
                                    localVarSize,
                                    returnType
                            ));
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            System.err.println("Error parsing procedure line: " + proc + " - " + e.getMessage());
                        }
                    } else {
                        System.err.println("Error: Insufficient fields in procedure line: " + proc);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Simbol createSimbolFromLine(String line) {
        String[] parts = line.split(", ");

        String id = parts[0].split("=")[1];
        String tipus = parts[1].split("=")[1];
        String valorStr = parts[2].split("=")[1];
        
        if(!valorStr.equals("null")){
            return new Simbol(id, tipus);
        }

        return new Simbol(id, tipus);
    }
    public static Object parseValue(String tipus, String valorStr) {
        Object valor;
        switch (tipus) {
            case "ENT":
                valor = Integer.parseInt(valorStr);
                break;
            case "BOOL":
                valor = Boolean.parseBoolean(valorStr);
                break;
            default:
                valor = valorStr;
                break;
        }
        return valor;
    }
    
    private void loadTs() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(TSYM_PATH));
            String line;
            
            // Skip the header line
            line = br.readLine(); 
            line = br.readLine(); 
    
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;
    
                String[] parts = line.split("\\s{2,}|\t");
    
                if (parts.length >= 4) {
                    String id = parts[0].trim();       
                    String tipus = parts[1].trim();     
                    
                    // Parse ARGS (reverse the order back)
                    String argsStr = parts[3].trim();
                    ArrayList<String> args = new ArrayList<>();
                    if (!argsStr.equals("[]")) {
                        String[] argsArray = argsStr.substring(1, argsStr.length() - 1).split(", ");
                        for (int i = argsArray.length - 1; i >= 0; i--) {
                            args.add(argsArray[i].trim());
                        }
                    }
    

                    Simbol simbol = new Simbol(id, tipus);
                    ts.add(simbol);

                } else {
                    System.err.println("Invalid line in symbol table: " + line);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private BufferedReader baseTable(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            for (int i = 0; i < 3 && (br.readLine()) != null; i++) {
            }
            return br;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Parameter> extractParamsTv(String params, String functionName) {
        ArrayList<Parameter> parameters = new ArrayList<>();

        // Remove the brackets [ ] and split the string by commas
        params = params.substring(1, params.length() - 1).trim();
        if (!params.isEmpty()) {
            String[] paramArray = params.split(", ");

            // Reverse the parameters to match the original order
            for (int i = paramArray.length - 1; i >= 0; i--) {
                String paramName = paramArray[i].trim();

                //Simbol simbol = getSymbol(paramName);
                Variable var = getVar(paramName, functionName);
                if (var != null) {
                    Tipus tipus = Tipus.valueOf(var.getType());
                    parameters.add(new Parameter(var.getName(), tipus, var.getSubprog()));
                } else {
                    System.err.println("Warning: Parameter symbol '" + paramName + "' not found in the symbol table.");
                }
            }
        }

        return parameters;
    }

    public Simbol getSymbol(String id){
        for (Simbol s : ts){
            if (s.getNom().equals(id)) return s;
        }
        return null;
    }

    public InstructionsList getInstructionList() {
        return instructionList;
    }

    public void updateInstructionList(InstructionsList instructionList){
        this.instructionList = instructionList;
    }

    public ArrayList<Variable> getTv() {
        return tv;
    }

    public ArrayList<Procedure> getTp() {
        return tp;
    }

    public Variable getVar(String id){
        for(Variable v : this.tv){
            if(v.getName().equals(id)){
                return v;
            }
        }
        return null;
    }
    
    public boolean isSimbol(String id){
        for(Simbol s : this.ts){
            if(s.getNom().equals(id)){
                return true;
            }
        }
        return false;
    }
    public Variable getVar(String id, String subprogram){
        for(Variable v : this.tv){
            if(v.getName().equals(id) && v.getSubprog().equals(subprogram)){
                return v;
            }
        }
        return null;
    }

    public void deleteVar(String id){
        for(int i = 0; i < tv.size(); i++){
            if(tv.get(i).getName().equals(id)){
                this.tv.remove(i);
            }
        }
    }

    public Procedure getProc(String id){
        for(Procedure p : this.tp){
            if(p.getStart_label().equals(id)){
                return p;
            }
        }
        return null;
    }

    public String getReturnProc(String id){
        Procedure proc = getProc(id);
        if (proc == null) return null;
        switch (proc.getType_return()){
            case ENT:
                return "retInt";
            case BOOL:
                return "retBool";
            default: return null;
        }
    }
}
