/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arbolb;

public class ArbolB {
    private NodoB raiz;
    private int grado;

    public ArbolB(int grado) {
        this.raiz = null;
        this.grado = grado;
    }

    // Método público para buscar una clave
    public boolean buscar(int clave) {
        return (raiz == null) ? false : buscarClave(raiz, clave);
    }

    private boolean buscarClave(NodoB nodo, int clave) {
        int i = 0;
        while (i < nodo.numClaves && clave > nodo.claves[i]) {
            i++;
        }

        if (i < nodo.numClaves && clave == nodo.claves[i]) {
            return true;
        }

        return nodo.esHoja ? false : buscarClave(nodo.hijos[i], clave);
    }

    // Método público para insertar una clave
    public void insertar(int clave) {
        if (raiz == null) {
            raiz = new NodoB(grado, true);
            raiz.claves[0] = clave;
            raiz.numClaves = 1;
        } else {
            if (raiz.numClaves == 2 * grado - 1) {
                NodoB nuevaRaiz = new NodoB(grado, false);
                nuevaRaiz.hijos[0] = raiz;
                dividirNodo(nuevaRaiz, 0, raiz);
                raiz = nuevaRaiz;
            }
            insertarNoLleno(raiz, clave);
        }
    }

    private void insertarNoLleno(NodoB nodo, int clave) {
        int i = nodo.numClaves - 1;

        if (nodo.esHoja) {
            while (i >= 0 && clave < nodo.claves[i]) {
                nodo.claves[i + 1] = nodo.claves[i];
                i--;
            }
            nodo.claves[i + 1] = clave;
            nodo.numClaves++;
        } else {
            while (i >= 0 && clave < nodo.claves[i]) {
                i--;
            }
            i++;

            if (nodo.hijos[i].numClaves == 2 * grado - 1) {
                dividirNodo(nodo, i, nodo.hijos[i]);
                if (clave > nodo.claves[i]) {
                    i++;
                }
            }
            insertarNoLleno(nodo.hijos[i], clave);
        }
    }

    private void dividirNodo(NodoB padre, int indice, NodoB nodo) {
        NodoB nuevoNodo = new NodoB(grado, nodo.esHoja);
        nuevoNodo.numClaves = grado - 1;

        for (int j = 0; j < grado - 1; j++) {
            nuevoNodo.claves[j] = nodo.claves[j + grado];
        }

        if (!nodo.esHoja) {
            for (int j = 0; j < grado; j++) {
                nuevoNodo.hijos[j] = nodo.hijos[j + grado];
            }
        }

        nodo.numClaves = grado - 1;

        for (int j = padre.numClaves; j > indice; j--) {
            padre.hijos[j + 1] = padre.hijos[j];
        }

        padre.hijos[indice + 1] = nuevoNodo;

        for (int j = padre.numClaves - 1; j >= indice; j--) {
            padre.claves[j + 1] = padre.claves[j];
        }

        padre.claves[indice] = nodo.claves[grado - 1];
        padre.numClaves++;
    }

    // Método público para eliminar una clave
    public void eliminar(int clave) {
        if (raiz == null) {
            System.out.println("El árbol está vacío");
            return;
        }

        eliminarClave(raiz, clave);

        if (raiz.numClaves == 0) {
            raiz = raiz.esHoja ? null : raiz.hijos[0];
        }
    }

    private void eliminarClave(NodoB nodo, int clave) {
        int indice = encontrarClave(nodo, clave);

        if (indice < nodo.numClaves && nodo.claves[indice] == clave) {
            if (nodo.esHoja) {
                eliminarDeHoja(nodo, indice);
            } else {
                eliminarDeNoHoja(nodo, indice);
            }
        } else {
            if (nodo.esHoja) {
                System.out.println("La clave " + clave + " no existe en el árbol");
                return;
            }

            boolean ultimoHijo = (indice == nodo.numClaves);
            
            if (nodo.hijos[indice].numClaves < grado) {
                llenarNodo(nodo, indice);
            }

            if (ultimoHijo && indice > nodo.numClaves) {
                eliminarClave(nodo.hijos[indice - 1], clave);
            } else {
                eliminarClave(nodo.hijos[indice], clave);
            }
        }
    }

    private int encontrarClave(NodoB nodo, int clave) {
        int indice = 0;
        while (indice < nodo.numClaves && nodo.claves[indice] < clave) {
            indice++;
        }
        return indice;
    }

    private void eliminarDeHoja(NodoB nodo, int indice) {
        for (int i = indice + 1; i < nodo.numClaves; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }
        nodo.numClaves--;
    }

    private void eliminarDeNoHoja(NodoB nodo, int indice) {
        int clave = nodo.claves[indice];

        if (nodo.hijos[indice].numClaves >= grado) {
            int predecesor = obtenerPredecesor(nodo, indice);
            nodo.claves[indice] = predecesor;
            eliminarClave(nodo.hijos[indice], predecesor);
        } else if (nodo.hijos[indice + 1].numClaves >= grado) {
            int sucesor = obtenerSucesor(nodo, indice);
            nodo.claves[indice] = sucesor;
            eliminarClave(nodo.hijos[indice + 1], sucesor);
        } else {
            fusionarNodos(nodo, indice);
            eliminarClave(nodo.hijos[indice], clave);
        }
    }

    private int obtenerPredecesor(NodoB nodo, int indice) {
        NodoB actual = nodo.hijos[indice];
        while (!actual.esHoja) {
            actual = actual.hijos[actual.numClaves];
        }
        return actual.claves[actual.numClaves - 1];
    }

    private int obtenerSucesor(NodoB nodo, int indice) {
        NodoB actual = nodo.hijos[indice + 1];
        while (!actual.esHoja) {
            actual = actual.hijos[0];
        }
        return actual.claves[0];
    }

    private void llenarNodo(NodoB nodo, int indice) {
        if (indice != 0 && nodo.hijos[indice - 1].numClaves >= grado) {
            prestarDeAnterior(nodo, indice);
        } else if (indice != nodo.numClaves && nodo.hijos[indice + 1].numClaves >= grado) {
            prestarDeSiguiente(nodo, indice);
        } else {
            fusionarNodos(nodo, indice != nodo.numClaves ? indice : indice - 1);
        }
    }

    private void prestarDeAnterior(NodoB nodo, int indice) {
        NodoB hijo = nodo.hijos[indice];
        NodoB hermano = nodo.hijos[indice - 1];

        for (int i = hijo.numClaves - 1; i >= 0; i--) {
            hijo.claves[i + 1] = hijo.claves[i];
        }

        if (!hijo.esHoja) {
            for (int i = hijo.numClaves; i >= 0; i--) {
                hijo.hijos[i + 1] = hijo.hijos[i];
            }
        }

        hijo.claves[0] = nodo.claves[indice - 1];

        if (!hijo.esHoja) {
            hijo.hijos[0] = hermano.hijos[hermano.numClaves];
        }

        nodo.claves[indice - 1] = hermano.claves[hermano.numClaves - 1];

        hijo.numClaves++;
        hermano.numClaves--;
    }

    private void prestarDeSiguiente(NodoB nodo, int indice) {
        NodoB hijo = nodo.hijos[indice];
        NodoB hermano = nodo.hijos[indice + 1];

        hijo.claves[hijo.numClaves] = nodo.claves[indice];

        if (!hijo.esHoja) {
            hijo.hijos[hijo.numClaves + 1] = hermano.hijos[0];
        }

        nodo.claves[indice] = hermano.claves[0];

        for (int i = 1; i < hermano.numClaves; i++) {
            hermano.claves[i - 1] = hermano.claves[i];
        }

        if (!hermano.esHoja) {
            for (int i = 1; i <= hermano.numClaves; i++) {
                hermano.hijos[i - 1] = hermano.hijos[i];
            }
        }

        hijo.numClaves++;
        hermano.numClaves--;
    }

    private void fusionarNodos(NodoB nodo, int indice) {
        NodoB hijo = nodo.hijos[indice];
        NodoB hermano = nodo.hijos[indice + 1];

        hijo.claves[grado - 1] = nodo.claves[indice];

        for (int i = 0; i < hermano.numClaves; i++) {
            hijo.claves[i + grado] = hermano.claves[i];
        }

        if (!hijo.esHoja) {
            for (int i = 0; i <= hermano.numClaves; i++) {
                hijo.hijos[i + grado] = hermano.hijos[i];
            }
        }

        for (int i = indice + 1; i < nodo.numClaves; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }

        for (int i = indice + 2; i <= nodo.numClaves; i++) {
            nodo.hijos[i - 1] = nodo.hijos[i];
        }

        hijo.numClaves += hermano.numClaves + 1;
        nodo.numClaves--;
    }

    // Método para visualizar el árbol 
    public void imprimir() {
        if (raiz != null) {
            imprimirNodo(raiz, "");
        }
    }

    private void imprimirNodo(NodoB nodo, String sangria) {
        System.out.print(sangria);
        for (int i = 0; i < nodo.numClaves; i++) {
            System.out.print(nodo.claves[i] + " ");
        }
        System.out.println();

        if (!nodo.esHoja) {
            for (int i = 0; i <= nodo.numClaves; i++) {
                imprimirNodo(nodo.hijos[i], sangria + "    ");
            }
        }
    }
    public NodoB getRaiz() {
    return this.raiz;
}
}