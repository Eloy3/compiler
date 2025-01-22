package front_end.simbols.Array;

import java.util.ArrayList;

import front_end.simbols.NodeBase;
import front_end.simbols.NodeExprsimple;
import front_end.simbols.NodeLlistaValors;

public class NodeInicialitzacio_taula extends NodeBase {
    private NodeLlistaValors llistavalors;
    private NodeAssignacio_memoria assignacio_memoria;
    private int[] lineCode;

    public NodeInicialitzacio_taula (NodeLlistaValors a, int[] l){
        super("Assignacio_dimensional", 0);
        this.llistavalors = a;
        this.lineCode = l;
    }

    public NodeInicialitzacio_taula(NodeAssignacio_memoria a, int[] l){
        super("Assignacio_dimensional", 0);
        this.assignacio_memoria = a;
        this.lineCode = l;
    }

    public NodeInicialitzacio_taula(int[] l){
        super("Assignacio_dimensional", 0);
        this.lineCode = l;
    }

    public NodeLlistaValors getLlistavalors() {
        return llistavalors;
    }

    public void setLlistavalors(NodeLlistaValors llistavalors) {
        this.llistavalors = llistavalors;
    }

    public NodeAssignacio_memoria getAssignacio_memoria() {
        return assignacio_memoria;
    }

    public void setAssignacio_memoria(NodeAssignacio_memoria assignacio_memoria) {
        this.assignacio_memoria = assignacio_memoria;
    }

    public int[] getLineCode() {
        return lineCode;
    }

    public void setLineCode(int[] lineCode) {
        this.lineCode = lineCode;
    }

    public ArrayList<NodeExprsimple> getDimensionList(NodeLlistaValors valors) {
        ArrayList<NodeExprsimple> list = new ArrayList<>();
        NodeLlistaValors aux = valors;
        while (aux != null) {
            list.add(aux.getValor());
            aux = aux.getLlistaValors();
        }
        return list;
    }

    public ArrayList<NodeExprsimple> extractParamList() {
        ArrayList<NodeExprsimple> list = new ArrayList<>();
        NodeEspecificacio_dimensio current = assignacio_memoria.getEspecificacio_dimensio();
        while (current != null) {
            list.add(current.getExprsimple());
            current = current.getAssignacio_dimension();
        }
        return list;
    }
}
