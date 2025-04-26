package arbolb;

import javax.swing.*;
import java.awt.*;

public class VisualizadorArbolB extends JFrame {
    private ArbolB arbol;

    public VisualizadorArbolB(ArbolB arbol) {
        this.arbol = arbol;
        setTitle("Visualización Gráfica del Árbol B");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (arbol != null && arbol.getRaiz() != null) {
                    dibujarArbol(g, arbol.getRaiz(), getWidth()/2, 50, getWidth()/4);
                } else {
                    g.drawString("El árbol está vacío", getWidth()/2 - 50, getHeight()/2);
                }
            }
        };
            
        panel.setBackground(new Color(240, 240, 240));
        add(panel);
        setVisible(true);
    }

    private void dibujarArbol(Graphics g, NodoB nodo, int x, int y, int espacio) {
        // Dibujar el nodo actual
        g.setColor(new Color(0, 120, 215));
        g.fillRoundRect(x - 30, y - 15, 60, 30, 10, 10);
        g.setColor(Color.WHITE);
        
        // Mostrar claves
        StringBuilder claves = new StringBuilder();
        for (int i = 0; i < nodo.numClaves; i++) {
            claves.append(nodo.claves[i]);
            if (i < nodo.numClaves - 1) claves.append(",");
        }
        
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(claves.toString());
        g.drawString(claves.toString(), x - textWidth/2, y + 5);

        // Dibujar hijos si no es hoja
        if (!nodo.esHoja) {
            int nuevoEspacio = espacio * 2 / 3;
            int separacion = espacio * 2 / (nodo.numClaves + 1);
            
            for (int i = 0; i <= nodo.numClaves; i++) {
                int hijoX = x - espacio + i * separacion;
                int hijoY = y + 80;
                
                // Dibujar línea de conexión
                g.setColor(Color.GRAY);
                g.drawLine(x, y + 15, hijoX, hijoY - 15);
                
                // Dibujar hijo recursivamente
                dibujarArbol(g, nodo.hijos[i], hijoX, hijoY, nuevoEspacio);
            }
        }
    }
}