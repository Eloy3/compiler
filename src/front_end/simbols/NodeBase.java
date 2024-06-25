
package compilador.simbols;

import compilador.estructura_de_dades.TaulaProcediments;
import compilador.estructura_de_dades.TaulaSimbols;
import compilador.estructura_de_dades.TaulaVariables;
import compilador.generar_codi.CodiTresAdreces;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;


public class NodeBase extends ComplexSymbol{
    private static int i = 0;
    protected static TaulaSimbols ts = new TaulaSimbols();
    protected static TaulaVariables tv = new TaulaVariables();
    protected static TaulaProcediments tp = new TaulaProcediments();
    protected static CodiTresAdreces cta = new CodiTresAdreces(tv);
    
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
