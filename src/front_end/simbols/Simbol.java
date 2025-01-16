
package front_end.simbols;
import java.util.ArrayList;

public class Simbol{
    String nom;
    String tipus;
    String tipusSubjacent;
    String subprogram;
    Object valor;

    int arrayDimensions;
    private ArrayList<String> args;

    public Simbol(String nom, String tipus, String subprogram, Object valor) {
        this.nom = nom;
        this.tipus = tipus;
        this.valor = valor;
        this.subprogram = subprogram;
    }

    public Simbol(String nom, String tipus, Object valor) {
        this.nom = nom;
        this.tipus = tipus;
        this.valor = valor;
    }

    public Simbol(String nom, String tipus, int dimensions) {
        this.nom = nom;
        this.tipus = tipus;
        this.arrayDimensions = dimensions;
    }
    
    public Simbol (String nom, String tipus){
        this.tipus = tipus;
        this.nom = nom;
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

    public String getSubprogram() {
        return subprogram;
    }

    public void setSubprogram(String subprogram) {
        this.subprogram = subprogram;
    }

    public void setTipusSubjacent(String tipusSubjacent) {
        this.tipusSubjacent = tipusSubjacent;
    }

    public int getArrayDimensions() {
        return arrayDimensions;
    }

    public void setArrayDimensions(int arrayDimensions) {
        this.arrayDimensions = arrayDimensions;
    }
    
    
    
 }
