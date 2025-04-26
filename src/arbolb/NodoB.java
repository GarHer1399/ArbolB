/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolb;

public class NodoB {
    public int[] claves;
    public NodoB[] hijos;
    public int numClaves;
    public boolean esHoja;

    public NodoB(int grado, boolean esHoja) {
        this.claves = new int[2 * grado - 1];
        this.hijos = new NodoB[2 * grado];
        this.numClaves = 0;
        this.esHoja = esHoja;
    }
}