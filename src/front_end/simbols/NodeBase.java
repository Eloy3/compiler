package front_end.simbols;

import data_structures.TaulaProcediments;
import data_structures.SymbolTable;
import data_structures.VariableTable;
import back_end.generate_code.ThreeAdressCode;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;


public class NodeBase extends ComplexSymbol{
    private static int i = 0;
    protected static SymbolTable ts = new SymbolTable();
    protected static VariableTable tv = new VariableTable();
    protected static TaulaProcediments tp = new TaulaProcediments();
    protected static ThreeAdressCode cta = new ThreeAdressCode(tv);
    
    public NodeBase(String nom, Integer valor){
        super(nom, i++, valor);
    }
    
    public void tancaFitxers(){
        tv.closeFile();
        cta.closeFile();
        tp.closeFile();
    }
    public static Tipus stringToTipus(String inputString) {
        for (Tipus value : Tipus.values()) {
                if (value.name().equals(inputString)) {
                        return value;
                }
        }
        throw new IllegalArgumentException("Error lèxic, no es reconeix el token: " + inputString);
    }
}
