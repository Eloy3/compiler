/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package data_structures;

import front_end.simbols.Tipus;


public class Parameter {
    private String nombre;
    private Tipus tipo;
    private String subprogram;

    public Parameter(String nombre, Tipus tipo, String subprograma) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.subprogram = subprograma;
    }

    public Parameter(String nombre, Tipus tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
    public String getNombre() {
        return nombre;
    }

    public Tipus getTipo() {
        return tipo;
    }

    public String getSubprogram() {
        return subprogram;
    }

    public void setSubprogram(String subprogram) {
        this.subprogram = subprogram;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
