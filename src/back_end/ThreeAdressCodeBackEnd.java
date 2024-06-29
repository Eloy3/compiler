

package back_end;

import data_structures.Procedure;
import data_structures.Variable;
import data_structures.Parameter;
import front_end.simbols.Simbol;
import front_end.simbols.TipusSubjacent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ThreeAdressCodeBackEnd {

    private final String TAC_PATH = "output/codiIntermitg.txt";
    private final String TVAR_PATH = "output/Taula_variables.txt";
    private final String TPROC_PATH = "output/Taula_Procedures.txt";
    private final String TSYM_PATH = "output/Taula_simbols.txt";

    private InstructionsList instructionList = new InstructionsList();
    private ArrayList<Variable> tv = new ArrayList<>();
    private ArrayList<Procedure> tp = new ArrayList<>();
    private ArrayList<Simbol> ts = new ArrayList<>();
    
    public ThreeAdressCodeBackEnd() {
        loadTs();
        loadTv();
        loadTp();
        loadInstructions();
    }
    
    private void loadInstructions() {

        try {
            FileReader fr = new FileReader(TAC_PATH);
            BufferedReader br = new BufferedReader(fr);
            String instruction;

            while ((instruction = br.readLine()) != null) {


                if (!instruction.equalsIgnoreCase("")) {

                    switch (instruction.split(" ")[0]) {

                        case "pmb":
                            instructionList.addInst(Operation.PMB,  null, null,  instruction.split(" ")[1]);
                            break;

                        case "goto":
                            instructionList.addInst(Operation.GOTO, null, null, instruction.split(" ")[1]);
                            break;
                        case "call":
                            instructionList.addInst(Operation.CALL, null, null, instruction.split(" ")[1]);
                            break;
                        case "param_c":
                            instructionList.addInst(Operation.PARAM_C, null, null, instruction.split(" ")[1].replace("\"", ""));
                            break;
                        case "param_s":
                            instructionList.addInst(Operation.PARAM_S, null, null, instruction.split(" ")[1]);
                            break;
                        case "rtn":
                            instructionList.addInst(Operation.RTN, instruction.split(" ")[2], null, instruction.split(" ")[1]);
                            break;

                        default:

                            if (instruction.contains(":skip")) { //Etiqueta
                                instructionList.addInst(Operation.SKIP, null, null, instruction.split(":")[0]);
                            } else if (instruction.split(" ")[1].equalsIgnoreCase("=") && !instruction.split(" ")[2].equals("call")) {

                                if (instruction.split(" ").length < 4) { //asignació simple (a = b).
                                    String [] splitCode = instruction.split(" ");
                                    if (instruction.contains("[")) {
                                        String [] splitAdress = instruction.split("[\\[\\]=\\s]+");
                                        if (splitCode[0].contains("[")){
                                            instructionList.addInst(Operation.ASSIGNA, splitAdress[2], splitAdress[1], splitAdress[0]);
                                        }
                                        else instructionList.addInst(Operation.ASSIGNA, splitAdress[1], splitAdress[2], splitAdress[0]);

                                    } else instructionList.addInst(Operation.ASSIGNA, splitCode[2], null, splitCode[0]);

                                } else {
                                    switch (instruction.split(" ")[3]) {
                                        case "+" -> instructionList.addInst(Operation.SUMA, instruction.split(" ")[2], instruction.split(" ")[4], instruction.split(" ")[0]);
                                        case "-" -> instructionList.addInst(Operation.RESTA, instruction.split(" ")[2], instruction.split(" ")[4], instruction.split(" ")[0]);
                                        case "*" -> instructionList.addInst(Operation.MULTIPLICACIO, instruction.split(" ")[2], instruction.split(" ")[4], instruction.split(" ")[0]);
                                        case "/" -> instructionList.addInst(Operation.DIVISIO, instruction.split(" ")[2], instruction.split(" ")[4], instruction.split(" ")[0]);
                                        default -> //Es una assignació de string.
                                                instructionList.addInst(Operation.ASSIGNA, instruction.split("=")[1].replaceAll("\"", ""), null, instruction.split(" ")[0]);
                                    }
                                }
                            } else if (instruction.split(" ")[0].equalsIgnoreCase("if")) {
                                switch (instruction.split(" ")[2]) {
                                    case ">=":
                                        instructionList.addInst(Operation.IFMAJORIGUAL,  instruction.split(" ")[1], instruction.split(" ")[3], instruction.split(" ")[5]);
                                        break;
                                    case "==":
                                        instructionList.addInst(Operation.IFIGUAL,  instruction.split(" ")[1], instruction.split(" ")[3], instruction.split(" ")[5]);
                                        break;
                                    case ">":
                                        instructionList.addInst(Operation.IFMAJOR, instruction.split(" ")[1], instruction.split(" ")[3], instruction.split(" ")[5]);
                                        break;
                                    case "<=":
                                        instructionList.addInst(Operation.IFMENORIGUAL,  instruction.split(" ")[1], instruction.split(" ")[3], instruction.split(" ")[5]);
                                        break;
                                    case "<":
                                        instructionList.addInst(Operation.IFMENOR,  instruction.split(" ")[1], instruction.split(" ")[3], instruction.split(" ")[5]);
                                        break;
                                    case "!=":
                                        instructionList.addInst(Operation.IFDIFERENT,  instruction.split(" ")[1], instruction.split(" ")[3], instruction.split(" ")[5]);
                                        break;
                                    default:
                                        instructionList.addInst(Operation.IF,  instruction.split(" ")[1], null, instruction.split(" ")[4]);
                                }
                            } else if (instruction.split(" ")[2].equals("call")) {
                                instructionList.addInst(Operation.CALL, null, null, instruction.split(" ")[3]);
                                if (instruction.split(" ")[1].equals("=")){
                                    instructionList.addInst(Operation.ASSIGNA, getReturnProc(instruction.split(" ")[3]), null, instruction.split(" ")[0]);
                                }
                            }
                    }
                }
            }
            br.close();
            //System.out.println(instructionList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadTv(){
        try {
            BufferedReader br = baseTable(TVAR_PATH);
            String variable;
            String [] split;

            while ((variable = br.readLine()) != null) {
                if (!variable.equals("")) {
                    split = variable.split("\\t+");

                    tv.add(new Variable(
                            split[0],
                            Integer.parseInt(split[1]),
                            split[2],
                            Integer.parseInt(split[3]),
                            Integer.parseInt(split[4]),
                            split[5],
                            (split.length==7)? split[6] : null //variable pot tenir valor o no
                    ));
                }

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTp(){
        try {
            BufferedReader br = baseTable(TPROC_PATH);
            String proc;
            String [] split;

            while ((proc = br.readLine()) != null) {
                if (!proc.equals("")) {
                    split = proc.split("\\t+");

                    tp.add(new Procedure(
                            Integer.parseInt(split[0]),
                            Integer.parseInt(split[1]),
                            split[2],
                            (split[3].equals("[]"))? new ArrayList<Parameter>() : extractParamsTs(split[3]),
                            Integer.parseInt(split[4]),
                            TipusSubjacent.valueOf(split[5])
                    ));
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
            Object valor = parseValue(tipus, valorStr);
            return new Simbol(id, tipus, valor);
        }
        

        return new Simbol(id, tipus, null);
    }
    public static Object parseValue(String tipus, String valorStr) {
        Object valor;
        switch (tipus) {
            case "ent":
                valor = Integer.parseInt(valorStr);
                break;
            case "bool":
                valor = Boolean.parseBoolean(valorStr);
                break;
            default:
                valor = valorStr;
                break;
        }
        return valor;
    }
    
    private void loadTs(){
        try {
            FileReader fr = new FileReader(TSYM_PATH);
            BufferedReader br = new BufferedReader(fr);
            String sym;

            while ((sym = br.readLine()) != null) {
                Simbol simbol = createSimbolFromLine(sym);
                ts.add(simbol);
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

            for (int i=0; i<3 && (br.readLine()) != null; i++){
            }

            return br;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Parameter> extractParamsTs(String params){
        params = params.substring(1, params.length() - 1);

        String[] stringArray = params.split(", ");

        ArrayList<Parameter> Parameters = new ArrayList<>();

        for (String s : stringArray) {
            Simbol sym = getSymbol(s);
            Parameters.add(new Parameter(sym.getNom(), TipusSubjacent.valueOf(sym.getTipus().toUpperCase())));
        }
        return Parameters;
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
            case STRING:
                return "retStr";
            default: return null;
        }
    }

}
