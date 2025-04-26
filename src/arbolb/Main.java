package arbolb;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configuración inicial con JOptionPane
        String gradoStr = JOptionPane.showInputDialog(
            null,
            "INGRESE EL GRADO DEL ARBOL (mínimo 2):",
            "Arbol B",
            JOptionPane.QUESTION_MESSAGE
        );

        if (gradoStr == null) System.exit(0); // Si el usuario cancela

        int grado = Integer.parseInt(gradoStr);
        ArbolB arbol = new ArbolB(grado);

        // Menú interactivo
        while (true) {
            String opcion = (String) JOptionPane.showInputDialog(
                null,
                "MENÚ ÁRBOL B\n\n" +
                "1. Insertar clave\n" +
                "2. Eliminar clave\n" +
                "3. Buscar clave\n" +
                "4. Visualizar árbol\n" +
                "5. Salir\n\n" +
                "Seleccione una opción:",
                "Menú Principal",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null
            );

            if (opcion == null || opcion.equals("5")) {
                System.exit(0);
            }

            switch (opcion) {
                case "1":
                    String claveInsertarStr = JOptionPane.showInputDialog("Ingrese la clave a insertar:");
                    if (claveInsertarStr != null) {
                        arbol.insertar(Integer.parseInt(claveInsertarStr));
                        JOptionPane.showMessageDialog(null, "Clave insertada correctamente");
                    }
                    break;

                case "2":
                    String claveEliminarStr = JOptionPane.showInputDialog("Ingrese la clave a eliminar:");
                    if (claveEliminarStr != null) {
                        arbol.eliminar(Integer.parseInt(claveEliminarStr));
                        JOptionPane.showMessageDialog(null, "Operación completada");
                    }
                    break;

                case "3":
                    String claveBuscarStr = JOptionPane.showInputDialog("Ingrese la clave a buscar:");
                    if (claveBuscarStr != null) {
                        boolean existe = arbol.buscar(Integer.parseInt(claveBuscarStr));
                        JOptionPane.showMessageDialog(null, 
                            existe ? "La clave existe" : "La clave NO existe");
                    }
                    break;

                case "4":
                    SwingUtilities.invokeLater(() -> {
                        new VisualizadorArbolB(arbol);
                    });
                    break;
            }
        }
    }
}