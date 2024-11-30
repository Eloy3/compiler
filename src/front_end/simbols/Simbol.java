
package front_end.simbols;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import back_end.Types;
import java.util.ArrayList;

public class Simbol{
    String nom;
    String tipus;
    String tipusSubjacent;
    Object valor;
    private ArrayList<String> args;

    public Simbol(String nom, String tipus, Object valor) {
        this.nom = nom;
        this.tipus = tipus;
        this.valor = valor;
    }
    public Simbol (String tipus, Object valor){
        this.tipus = tipus;
        this.valor = valor;
    }

    public Simbol(){
        
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public ArrayList<String> getArgs() {
        return args;
    }
    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }
    
    @Override
    public String toString() {
        return  "id=" + nom + ", tipus=" + tipus + ", valor=" + valor;
    }

    public String getTipusSubjacent() {
        return tipusSubjacent;
    }
    
    
 }
