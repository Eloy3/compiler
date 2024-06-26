/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package data_structures;

import front_end.simbols.TipusSubjacent;


public class Parameter {
    private String nombre;
    private TipusSubjacent tipo;

    public Parameter(String nombre, TipusSubjacent tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipusSubjacent getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
