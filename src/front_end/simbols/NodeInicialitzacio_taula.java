package front_end.simbols;

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

    

}
