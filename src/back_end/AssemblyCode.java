
package back_end;


import data_structures.Procedure;
import data_structures.Parameter;
import data_structures.Variable;
import front_end.simbols.TipusSubjacent;
import static front_end.simbols.TipusSubjacent.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class AssemblyCode {

    private ArrayList<String> code;
    private ArrayList<String> conststrings;
    private int conststringidx;
    private ThreeAdressCodeBackEnd c3a;
    private TipusSubjacent param;

    private int int_store;
    private int str_store;
    private int logic_store;
    private int null_store;
    public BufferedReader br;

    public AssemblyCode(ThreeAdressCodeBackEnd c3a) throws IOException {
        this.c3a = c3a;
        this.code = new ArrayList<>();
        this.conststrings = new ArrayList<>();
        setStore();
    }

    public void generate(){

        code.clear();
        conststrings.clear();
        conststringidx = 0;
        param = null;

        this.code.add("\torg $1000");
        code.add("START:");
        code.add("\tJSR SCREENSIZE");
        for (Instruction3a i : c3a.getInstructionList().getInst_list()){
            traslate(i);
        }
        code.add("\tSIMHALT");

        code.add(" ");
        setMemory(c3a);
        code.add(" ");
        conststringidx = 0;
        for (String str : conststrings){
            code.add("aux" + conststringidx + ": DC.B '" + str + "',0");
            conststringidx++;
        }

        code.add("strout: DS.B 100");
        code.add("\tDC.W 0");
        code.add(" ");
        IOsubrutines();

        code.add("\tEND START");
    }

    private void setMemory(ThreeAdressCodeBackEnd tac){
        boolean bytes = false;

        for (Variable v : tac.getTv()){
            String name = varnom(v);
            switch (TipusSubjacent.valueOf(v.getType().toUpperCase())){
                case BOOL:
                    bytes = true;
                    code.add(name + ": DS.B 1");
                    break;
                case ENT:
                    if(bytes){
                        bytes = false;
                        code.add("\tDC.W 0");
                    }
                    code.add(name + ": DS.W "+v.getStore()/calculateStore(v.getType(),""));
                    break;
                case STRING:
                    bytes = true;
                    code.add(name + ": DS.B 100");
                    break;
            }
        }
    }

    private void traslate(Instruction3a i){

        code.add("* -->" + i.toString());
        switch (i.getOperation()){
            case ASSIGNA:
                iasigna(i);
                break;
            case GOTO:
                code.add("\tJMP " + i.getDestiny());
                break;
            case SKIP:
                code.add(i.getDestiny() + ":");
                break;
            case CALL:
                icall(i);
            case PARAM_C:
            case PARAM_S:
                iparam(i);
                break;
            case PMB:
                ipmb(i);
                break;
            case RTN:
                irtn(i);
                break;
            case IFIGUAL:
                compare(i);
                code.add("\tBEQ " + i.getDestiny());
                break;
            case IFDIFERENT:
                compare(i);
                code.add("\tBNE " + i.getDestiny());
                break;
            case IFMAJOR:
                compare(i);
                code.add("\tBGT " + i.getDestiny());
                break;
            case IFMAJORIGUAL:
                compare(i);
                code.add("\tBGE " + i.getDestiny());
                break;
            case IFMENOR:
                compare(i);
                code.add("\tBLT " + i.getDestiny());
                break;
            case IFMENORIGUAL:
                compare(i);
                code.add("\tBLE " + i.getDestiny());
                break;
            case SUMA:
                isuma(i);
                break;
            case RESTA:
                iresta(i);
                break;
            case DIVISIO:
                idivision(i);
                break;
            case MULTIPLICACIO:
                imultiplicacion(i);
                break;
            case AND:
                iand(i);
                break;
            case OR:
                ior(i);
                break;
            case IF:
                Variable v = c3a.getVar(i.getOperand1());
                code.add("\tMOVE.B " + varnom(v) + ",D0");
                code.add("\tMOVE.B #" + -1 + ",D1");
                code.add("\tCMP.B D0,D1");
                code.add("\tBEQ " + i.getDestiny());
                break;
            case DESPLAZAR_BITS:
                idesplazar(i);
                break;
        }
    }

    private void iasigna(Instruction3a i){
        Variable d = c3a.getVar(i.getDestiny());
        switch (TipusSubjacent.valueOf(d.getType().toUpperCase())){
            case STRING:
                if (!i.getOperand1().equals("retStr")){
                    Variable v = c3a.getVar(i.getOperand1());
                    if(v == null){
                        code.add("\tLEA.L " + setConstString(i.getOperand1()) + ",A0");
                        code.add("\tLEA.L " + varnom(d) + ",A1");
                        code.add("\tJSR STRCPY");
                    } else{
                        code.add("\tLEA.L " + varnom(v) + ",A0");
                        code.add("\tLEA.L " + varnom(d) + ",A1");
                        code.add("\tJSR STRCPY");
                    }
                } else {
                    code.add("\tMOVE.L (A7)+,A0");
                    code.add("\tLEA.L " + varnom(d) + ",A1");
                    code.add("\tJSR STRCPY");
                }
                break;
            case BOOL:
                if (!i.getOperand1().equals("retBool")) {
                    code.add("\tMOVE.B " + getop(i.getOperand1()) + "," + getop(i.getDestiny()));
                } else {
                    code.add("\tMOVE.W (A7)+,D0");
                    code.add("\tMOVE.B D0," + varnom(d));
                }
                break;
            case ENT:
                if (checkHasAllFields(i)){
                    Variable o1 = c3a.getVar(i.getOperand1());
                    Variable o2 = c3a.getVar(i.getOperand2());

                    code.add("\tLEA.L "+varnom(o1)+ ",A0");
                    code.add("\tMOVE.W "+varnom(o2)+ ",A1");
                    code.add("\tADD.L A1,A0");
                    code.add("\tMOVE.W (A0),D0");
                    code.add("\tMOVE.W D0,"+varnom(d));

                } else if (!i.getOperand1().equals("retInt")) {
                    code.add("\tMOVE.W " + getop(i.getOperand1()) + "," + getop(i.getDestiny()));
                } else {
                    code.add("\tMOVE.W (A7)+," + getop(i.getDestiny()));
                }
                break;
        }
    }

    private void icall(Instruction3a i){
        Procedure p = c3a.getProc(i.getDestiny());
        if(p == null){
            switch (i.getDestiny()) {
                case "print":
                    if (param == ENT) {
                        code.add("\tJSR IPRINT");
                        code.add("\tADDA.L #2,A7");
                    } else {
                        code.add("\tJSR SPRINT");
                        code.add("\tADDA.L #4,A7");
                    }
                    break;
                case "line":
                    if (param == ENT) {
                        code.add("\tJSR ILINE");
                        code.add("\tADDA.L #2,A7");
                    } else {
                        code.add("\tJSR SLINE");
                        code.add("\tADDA.L #4,A7");
                    }
                    break;
                case "getStr":
                    code.add("\tMOVE.L #strout,-(A7)");
                    code.add("\tJSR GETSTR");
                    break;
                case "getInt":
                    code.add("\tSUBA.L #2,A7");
                    code.add("\tJSR GETINT");
                    break;
            }
        } else {
            if(p.getType_return() != null){
                switch (p.getType_return()){
                    case ENT:
                    case BOOL:
                        code.add("\tSUBA.L #2,A7");
                        break;
                    case STRING:
                        code.add("\tSUBA.L #4,A7");
                }
            }
            code.add("\tJSR " + i.getDestiny());
            int k = 0;
            for(Parameter param : p.getParametros()){
                switch (param.getTipo()){
                    case ENT:
                    case BOOL:
                        k += 2;
                        break;
                    case STRING:
                        k += 4;
                        break;
                }
            }
            if(k > 0){
                if(p.getType_return() != null){
                    switch (p.getType_return()){
                        case ENT:
                        case BOOL:
                            code.add("\tMOVE.W (A7)," + k + "(A7)");
                            code.add("\tADDA.L #" + k + ",A7");
                            break;
                        case STRING:
                            code.add("\tMOVE.L (A7)," + k + "(A7)");
                            code.add("\tADDA.L #" + k + ",A7");
                            break;
                    }
                }else {
                    code.add("\tADDA.L #" + k + ",A7");
                }
            }
        }
        param = null;
    }

    private void iparam(Instruction3a i){
        Variable d = c3a.getVar(i.getDestiny());
        switch (i.getOperation()){
            case PARAM_C:
                if (d == null){
                    switch (i.getDestiny()) {
                        case "retStr":
                            param = STRING;
                            break;
                        default:
                            code.add("\tMOVE.L #" + setConstString(i.getDestiny()) + ",-(A7)");
                            break;
                    }
                } else {
                    code.add("\tMOVE.L #" + varnom(d) + ",-(A7)");
                    param = STRING;
                }
                break;
            case PARAM_S:
                if(d != null){
                    if (TipusSubjacent.valueOf(d.getType().toUpperCase()) == ENT){
                        code.add("\tMOVE.W " + varnom(d) + ",-(A7)");
                        param =  ENT;
                    } else{
                        code.add("\tCLR.W D0");
                        code.add("\tMOVE.B " + varnom(d) + ",D0");
                        code.add("\tEXT.W D0");
                        code.add("\tMOVE.W D0,-(A7)");
                        param =  ENT;
                    }
                } else{
                    switch (i.getDestiny()){
                        case "retLogic":
                        case "retInt":
                            param = ENT;
                            break;
                        default:
                            try {
                                Integer.parseInt(i.getDestiny());
                                code.add("\tMOVE.W #" + i.getDestiny() + ",-(A7)");
                            }catch(NumberFormatException e){
                                code.add("\tMOVE.L #" + setConstString(i.getOperand1()) + ",-(A7)");
                            }
                            break;
                    }
                }
                break;
        }
    }

    private void ipmb(Instruction3a i){
        Procedure p = c3a.getProc(i.getDestiny());
        ArrayList<Parameter> param = p.getParametros();
        int ind = param.size() - 1;
        int k = 4;
        if(p.getType_return() != null){
            switch (p.getType_return()){
                case STRING:
                    k = 8;
                    break;
                case ENT:
                case BOOL:
                    k = 6;
                    break;
            }
        }

        while(ind >= 0){
            Parameter aux = param.get(ind);
            Variable v = c3a.getVar(aux.getNombre());
            switch (aux.getTipo()){
                case BOOL:
                    code.add("\tMOVE.W " + k +"(A7),D0");
                    code.add("\tMOVE.B D0," + varnom(v));
                    k += 2;
                    break;
                case ENT:
                    code.add("\tMOVE.W " + k +"(A7)," + varnom(v));
                    k += 2;
                    break;
                case STRING:
                    code.add("\tMOVEA.L " + k +"(A7),A0");
                    code.add("\tLEA.L " + varnom(v) + ",A1");
                    code.add("\tJSR STRCPY");
                    //code.add("\tMOVE.L " + k +"(A7)," + varnom(v));
                    k += 4;
                    break;
            }
            ind--;
        }
    }

    private void irtn(Instruction3a i){
        Variable r = c3a.getVar(i.getOperand1());
        if(r != null){
            Procedure p = c3a.getProc(i.getDestiny());
            switch (p.getType_return()){
                case ENT:
                    code.add("\tMOVE.W " + varnom(r) + ",4(A7)");
                    break;
                case BOOL:
                    code.add("\tCLR.W D0");
                    code.add("\tMOVE.B " + varnom(r) + ",D0");
                    code.add("\tMOVE.W D0,4(A7)");
                    break;
                case STRING:
                    code.add("\tMOVE.L #" + varnom(r) + ",4(A7)");
                    break;
            }
        } else if (i.getOperand1() != null){
            switch (i.getOperand1()){
                case "retInt":
                case "retBool":
                    code.add("\tMOVE.W (A7),6(A7)");
                    code.add("\tADDA.L #2,A7");
                    break;
                case "retStr":
                    code.add("\tMOVE.W (A7),8(A7)");
                    code.add("\tADDA.L #4,A7");
                    break;
            }
        }
        code.add("\tRTS");
    }

    private void compare(Instruction3a i){
        Variable op1 = c3a.getVar(i.getOperand1());
        if(op1 == null){
            boolean numero;
            try {
                Integer.parseInt(i.getOperand1());
                numero = true;
            }catch(NumberFormatException e){
                numero = false;
            }
            if (numero){
                code.add("\tMOVE.W #" + i.getOperand1() + ",D1");
            } else {
                code.add("\tLEA.L " + setConstString(i.getOperand1()) + ",A0");
            }
        } else {
            switch (TipusSubjacent.valueOf(op1.getType().toUpperCase())){
                case ENT:
                    code.add("\tMOVE.W " + varnom(op1) + ",D1");
                    break;
                case BOOL:
                    code.add("\tCLR.W D1");
                    code.add("\tMOVE.B " + varnom(op1) + ",D1");
                    code.add("\tEXT.W D1");
                    break;
                case STRING:
                    code.add("\tLEA.L " + varnom(op1) + ",A0");
                    break;
            }
        }

        Variable op2 = c3a.getVar(i.getOperand2());
        if(op2 == null){
            boolean numero;
            try {
                Integer.parseInt(i.getOperand2());
                numero = true;
            }catch(NumberFormatException e){
                numero = false;
            }
            if (numero){
                code.add("\tMOVE.W #" + i.getOperand2() + ",D0");
                code.add("\tCMP.W D0,D1");
            } else {
                code.add("\tLEA.L " + setConstString(i.getOperand2()) + ",A1");
                code.add("\tJSR STRCMP");
            }
        } else {
            switch (TipusSubjacent.valueOf(op1.getType().toUpperCase())){
                case ENT:
                    code.add("\tMOVE.W " + varnom(op2) + ",D0");
                    code.add("\tCMP.W D0,D1");
                    break;
                case BOOL:
                    code.add("\tCLR.W D0");
                    code.add("\tMOVE.B " + varnom(op2) + ",D0");
                    code.add("\tCMP.B D0,D1");
                    break;
                case STRING:
                    code.add("\tLEA.L " + varnom(op2) + ",A1");
                    code.add("\tJSR STRCMP");
                    break;
            }

        }
    }

    private void isuma(Instruction3a i){
        Variable destino = c3a.getVar(i.getDestiny());
        if(TipusSubjacent.valueOf(destino.getType().toUpperCase()) == STRING){
            Variable op1 = c3a.getVar(i.getOperand1());
            if (op1 == null){
                code.add("\tLEA.L " + setConstString(i.getOperand1()) + ",A0");
            } else {
                code.add("\tLEA.L " + varnom(op1) + ",A0");
            }
            Variable op2 = c3a.getVar(i.getOperand2());
            if (op2 == null){
                code.add("\tLEA.L " + setConstString(i.getOperand2()) + ",A1");
            } else {
                code.add("\tLEA.L " + varnom(op2) + ",A1");
            }
            code.add("\tLEA.L " + varnom(destino) + ",A2");
            code.add("\tJSR STRCON");
        } else {
            code.add("\tMOVE.W " + getop(i.getOperand1()) + ",D0");
            code.add("\tMOVE.W " + getop(i.getOperand2()) + ",D1");
            code.add("\tJSR ISUMA");
            code.add("\tMOVE.W D1," + varnom(destino));
        }
    }

    private void iresta(Instruction3a i){
        Variable d = c3a.getVar(i.getDestiny());
        code.add("\tMOVE.W " + getop(i.getOperand1()) + ",D1");
        code.add("\tMOVE.W " + getop(i.getOperand2()) + ",D0");
        code.add("\tJSR IRESTA");
        code.add("\tMOVE.W D1," + varnom(d));
    }

    private void idivision(Instruction3a i){
        Variable d = c3a.getVar(i.getDestiny());
        code.add("\tMOVE.W " + getop(i.getOperand1()) + ",D1");
        code.add("\tEXT.L D1");
        code.add("\tMOVE.W " + getop(i.getOperand2()) + ",D0");
        code.add("\tEXT.L D0");
        code.add("\tDIVS.W D0,D1");
        code.add("\tMOVE.W D1," + varnom(d));
    }

    private void imultiplicacion(Instruction3a i){
        Variable d = c3a.getVar(i.getDestiny());
        code.add("\tMOVE.W " + getop(i.getOperand1()) + ",D0");
        code.add("\tEXT.L D0");
        code.add("\tMOVE.W " + getop(i.getOperand2()) + ",D1");
        code.add("\tEXT.L D1");
        code.add("\tMULS.W D0,D1");
        code.add("\tMOVE.W D1," + varnom(d));
    }

    private void iand(Instruction3a i){
        Variable d = c3a.getVar(i.getDestiny());
        code.add("\tMOVE.B " + getop(i.getOperand1()) + ",D0");
        code.add("\tMOVE.B " + getop(i.getOperand2()) + ",D1");
        code.add("\tAND.B D0,D1");
        code.add("\tMOVE.B D1," + varnom(d));
    }

    private void ior(Instruction3a i){
        Variable d = c3a.getVar(i.getDestiny());
        code.add("\tMOVE.B " + getop(i.getOperand1()) + ",D0");
        code.add("\tMOVE.B " + getop(i.getOperand2()) + ",D1");
        code.add("\tOR.B D0,D1");
        code.add("\tMOVE.B D1," + varnom(d));
    }

    private void idesplazar(Instruction3a i){
        Variable op = c3a.getVar(i.getOperand2());
        if(op == null){
            code.add("\tMOVE.W #" + i.getOperand2()+ ",D0");
        }else{
            code.add("\tMOVE.W " + varnom(op) + ",D0");
        }
        code.add("\tEXT.L D0");
        Variable destino = c3a.getVar(i.getDestiny());
        int aux = Integer.valueOf(i.getOperand1());
        if (aux < 0){
            code.add("\tLSR.L #" + -aux + ",D0");
        } else {
            code.add("\tLSL.L #" + aux + ",D0");
        }
        code.add("\tMOVE.W D0," + varnom(destino));
    }

    private String getop(String identificador){
        Variable v = c3a.getVar(identificador);
        if (v == null){
            if (identificador.startsWith("ret")){
                return "(A7)+";
            } else {
                if (identificador.equals("cert")) identificador = "-1";
                else if (identificador.equals("fals")) identificador = "0";
                return "#" + identificador;
            }
        } else{
            return varnom(v);
        }
    }

    private String varnom(Variable v){
        return v.getName() + "_" + v.getSubprog();
    }

    private void IOsubrutines(){
        code.add("SCREENSIZE:");
        code.add("\tMOVE.L #1024*$10000+768,D1");
        code.add("\tMOVE.B  #33,D0");
        code.add("\tTRAP    #15");
        code.add("\tRTS");

        code.add("ISUMA:");
        code.add("\tBTST.L #15,D0");
        code.add("\tBEQ ADD2");
        code.add("\tNOT.W D0");
        code.add("\tADDQ.W #1,D0");
        code.add("\tBTST.L #15,D1");
        code.add("\tBEQ ADD1");
        code.add("\tNOT.W D1");
        code.add("\tADDQ.W #1,D1");
        code.add("\tADD.W D0,D1");
        code.add("\tNOT.W D1");
        code.add("\tADDQ.W #1,D1");
        code.add("\tJSR ADD4");
        code.add("ADD1:");
        code.add("\tSUB.W D0,D1");
        code.add("\tJSR ADD4");
        code.add("ADD2:");
        code.add("\tBTST.L #15,D1");
        code.add("\tBEQ ADD3");
        code.add("\tNOT.W D1");
        code.add("\tADDQ.W #1,D1");
        code.add("\tSUB.W D1,D0");
        code.add("\tMOVE.W  D0,D1");
        code.add("\tJSR ADD4");
        code.add("ADD3:");
        code.add("\tADD.W D0,D1");
        code.add("ADD4:");
        code.add("\tRTS");

        code.add("IRESTA:");
        code.add("\tBTST.L #15,D1");
        code.add("\tBEQ SUB2");
        code.add("\tNOT.W D1");
        code.add("\tADDQ.W #1,D1");
        code.add("\tBTST.L #15,D0");
        code.add("\tBEQ SUB1");
        code.add("\tNOT.W D0");
        code.add("\tADDQ.W #1,D0");
        code.add("\tSUB.W D0,D1");
        code.add("\tNOT.W D1");
        code.add("\tADDQ.W #1,D1");
        code.add("\tRTS");
        code.add("SUB1:");
        code.add("\tADD.W D0,D1");
        code.add("\tNOT.W D1");
        code.add("\tADDQ.W #1,D1");
        code.add("\tRTS");
        code.add("SUB2:");
        code.add("\tBTST.L #15,D0");
        code.add("\tBEQ SUB3");
        code.add("\tNOT.W D0");
        code.add("\tADDQ.W #1,D0");
        code.add("\tADD.W D0,D1");
        code.add("\tRTS");
        code.add("SUB3:");
        code.add("\tSUB.W D0,D1");
        code.add("\tRTS");

        code.add("SPRINT:");
        code.add("\tMOVE.L 4(A7),A1");
        code.add("\tMOVE.L #1,D0");
        code.add("\tMOVE.W #100,D1");
        code.add("\tTRAP #15");
        code.add("\tRTS");

        code.add("SLINE:");
        code.add("\tMOVE.L 4(A7),A1");
        code.add("\tMOVE.L A1,-(A7)");
        code.add("\tJSR SPRINT");
        code.add("\tADDA.L #4,A7");
        code.add("\tMOVE.L #11,D0");
        code.add("\tMOVE.W #$00FF,D1");
        code.add("\tTRAP #15");
        code.add("\tADD.W #1,D1");
        code.add("\tAND.W #$00FF,D1");
        code.add("\tTRAP #15");
        code.add("\tRTS");

        code.add("IPRINT:");
        code.add("\tCLR.L D1");
        code.add("\tMOVE.W 4(A7),D1");
        code.add("\tEXT.L D1");
        code.add("\tMOVE.L #3,D0");
        code.add("\tTRAP #15");
        code.add("\tRTS");

        code.add("ILINE:");
        code.add("\tMOVE.W 4(A7),D1");
        code.add("\tMOVE.W D1,-(A7)");
        code.add("\tJSR IPRINT");
        code.add("\tADDA.W #2,A7");
        code.add("\tMOVE.L #11,D0");
        code.add("\tMOVE.W #$00FF,D1");
        code.add("\tTRAP #15");
        code.add("\tADD.W #1,D1");
        code.add("\tAND.W #$00FF,D1");
        code.add("\tTRAP #15");
        code.add("\tRTS");

        code.add("GETINT:");
        code.add("\tMOVE.L #4,D0");
        code.add("\tTRAP #15");
        code.add("\tMOVE.W D1,4(A7)");
        code.add("\tRTS");

        code.add("GETSTR:");
        code.add("\tMOVEA.L 4(A7),A1");
        code.add("\tMOVE.L #2,D0");
        code.add("\tTRAP #15");
        code.add("\tRTS");

        code.add("STRCPY:");
        code.add("\tMOVE.B (A0)+,(A1)+");
        code.add("\tBNE STRCPY");
        code.add("\tRTS");

        code.add("STRCMP:");
        code.add("\tCMPM.B (A0)+,(A1)+");
        code.add("\tBNE STRRET");
        code.add("\tTST.B -1(A0)");
        code.add("\tBNE STRCMP");
        code.add("STRRET:");
        code.add("\tRTS");

        code.add("STRCON:");
        code.add("\tCLR.W D0");
        code.add("\tMOVE.W #100,D1");
        code.add("STRCON1:");
        code.add("\tMOVE.B (A0)+,(A2)+");
        code.add("\tBEQ STRCON2");
        code.add("\tADDQ.W #1,D0");
        code.add("\tCMP.W D0,D1");
        code.add("\tBEQ STRCON3");
        code.add("\tJMP STRCON1");
        code.add("STRCON2:");
        code.add("\tSUBA.L #1,A2");
        code.add("STRCON3:");
        code.add("\tMOVE.B (A1)+,(A2)+");
        code.add("\tBEQ STRCON4");
        code.add("\tADDQ.W #1,D0");
        code.add("\tCMP.W D0,D1");
        code.add("\tBEQ STRCON4");
        code.add("\tJMP STRCON3");
        code.add("STRCON4:");
        code.add("\tRTS");
    }

    public String getCode() {
        String str = "";
        for (String s : code){
            str += s + "\n";
        }
        return str;
    }

    public String setConstString(String str){
        int idx = conststrings.indexOf(str);
        if(idx == -1){
            conststrings.add(str);
            String nom = "aux" + conststringidx;
            conststringidx++;
            return nom;
        } else {
            return "aux" + idx;
        }
    }

    private void setStore() throws IOException {
        br = new BufferedReader(new FileReader("src/back_end/stores.txt"));
        int_store = Integer.parseInt(br.readLine().split(" ")[2]);
        str_store = Integer.parseInt(br.readLine().split(" ")[2]);
        logic_store = Integer.parseInt(br.readLine().split(" ")[2]);
        null_store = Integer.parseInt(br.readLine().split(" ")[2]);
        br.close();
    }

    public int calculateStore(String type, String s) {
        TipusSubjacent enum_type = TipusSubjacent.valueOf(type.toUpperCase());
        switch (enum_type) {
            case ENT:
                return int_store;
            case STRING:
                return str_store * s.length();
            case BOOL:
                return logic_store;
            case NULL:
                return null_store;
        }
        return -1;
    }

    private boolean checkHasAllFields(Instruction3a i){
        return i.getOperand1() != null && i.getOperand2() != null && i.getDestiny() != null;
    }
}
