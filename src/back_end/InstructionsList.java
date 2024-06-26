package back_end;

import java.util.ArrayList;

public class InstructionsList {
    private ArrayList<Instruction3a> inst_list;

    public InstructionsList() {
        inst_list = new ArrayList<>();
    }

    public Instruction3a getInstruction(int idx){
        return inst_list.get(idx);
    }

    public void addInst(Operation operation, String op1, String op2, String destination) {

        inst_list.add(new Instruction3a(operation, op1, op2, destination));
    }

    //Borra una instrucció
    public void deleteInst(int i) {
        inst_list.remove(i);
    }


    public ArrayList<Instruction3a> getInst_list() {
        return inst_list;
    }

    public int getSize(){return inst_list.size();}

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Instruction3a inst : inst_list) {
            s.append(inst.toString()).append("\n");
        }

        return s.toString();
    }
}
