package front_end.simbols;

import java.util.ArrayList;
import java.util.List;

public class NodeValorTaula extends NodeBase {
    private NodeEspecificacio_dimensio especificacio_dimensio;
    private String id;
    private final int[] lineCode;

    public NodeValorTaula(String id, NodeEspecificacio_dimensio especificacio_dimensio, int[] l){
        super("ValorTaula", 0);
        this.id = id;
        this.especificacio_dimensio = especificacio_dimensio;
        this.lineCode = l;
    }

    public List<String> getIndexes() {
        return extractParamList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int[] getLineCode() {
        return lineCode;
    }

    public List<String> extractParamList() {
        List<String> list = new ArrayList<>();
        NodeEspecificacio_dimensio current = especificacio_dimensio;
        while (current != null) {
            list.add(current.getExprsimple().toString());
            current = current.getAssignacio_dimension();
        }
        return list;
    }

}
