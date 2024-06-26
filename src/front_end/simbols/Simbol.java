
package front_end.simbols;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import back_end.Types;
import java.util.ArrayList;
public class Simbol{
    String nom;
    String tipus;
    String tipusSubjacent;
    Object valor;
    ArrayList<Simbol> args;

    public Simbol(String nom, String tipus, Object valor) {
        this.nom = nom;
        this.tipus = tipus;
        this.valor = valor;
    }
    public Simbol (String tipus, Object valor){
        this.tipus = tipus;
        this.valor = valor;
    }
    public Simbol(String id, Types types, String subtype, ArrayList<Simbol> args) {
        this.nom = id;
        this.tipus = types.toString();
        this.tipusSubjacent = subtype;
        this.args = args;
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

    @Override
    public String toString() {
        return  "id=" + nom + ", tipus=" + tipus + ", valor=" + valor;
    }

    public String getTipusSubjacent() {
        return tipusSubjacent;
    }
    
    
 }
