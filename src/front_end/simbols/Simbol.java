
package front_end.simbols;
import java.util.ArrayList;

public class Simbol{
    String nom;
    String tipus;
    Object valor;
    boolean constant = false;

    ArrayList<Integer> arrayDimensions;
    private ArrayList<String> args;

    public Simbol(String nom, String tipus, Object valor) {
        this.nom = nom;
        this.tipus = tipus;
        this.valor = valor;
    }

    public Simbol(String nom, String tipus, ArrayList<Integer> dimensions) {
        this.nom = nom;
        this.tipus = tipus;
        this.arrayDimensions = dimensions;
    }
    
    public Simbol (String nom, String tipus, boolean constant){
        this.tipus = tipus;
        this.nom = nom;
        this.constant = constant;
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

    public ArrayList<Integer> getArrayDimensions() {
        return arrayDimensions;
    }

    public void setArrayDimensions(ArrayList<Integer> arrayDimensions) {
        this.arrayDimensions = arrayDimensions;
    }

    public boolean isConstant() {
        return constant;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }
    
 }
