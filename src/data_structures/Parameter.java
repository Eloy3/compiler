/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package data_structures;

import front_end.simbols.Tipus;


public class Parameter {
    private String nombre;
    private Tipus tipo;

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

    @Override
    public String toString() {
        return nombre;
    }
}
