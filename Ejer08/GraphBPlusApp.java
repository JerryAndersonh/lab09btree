import java.util.Scanner;

public class GraphBPlusApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BPlusVisualizer<Integer> bplus = new BPlusVisualizer<>(5);

        while (true) {
            System.out.println("\n=== VISUALIZADOR DE ÁRBOL B+ ===");
            System.out.println("1. Insertar números");
            System.out.println("2. Buscar elemento");
            System.out.println("3. Mostrar árbol");
            System.out.println("4. Visualizar árbol (GraphStream)");
            System.out.println("5. Eliminar elemento");
            System.out.println("6. Limpiar árbol");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese números separados por espacio: ");
                    String[] datos = scanner.nextLine().split(" ");
                    for (String num : datos) {
                        try {
                            bplus.insert(Integer.parseInt(num));
                        } catch (NumberFormatException e) {
                            System.out.println("Número inválido: " + num);
                        }
                    }
                    break;

                case 2:
                    System.out.print("Elemento a buscar: ");
                    int buscar = scanner.nextInt();
                    System.out.println("¿Está presente?: " + bplus.search(buscar));
                    break;

                case 3:
                    System.out.println(bplus.toString());
                    break;

                case 4:
                    bplus.displayTree();
                    break;

                case 5:
                    System.out.print("Elemento a eliminar: ");
                    int eliminar = scanner.nextInt();
                    bplus.remove(eliminar);
                    break;

                case 6:
                    bplus.destroy();
                    System.out.println("Árbol limpiado.");
                    break;

                case 0:
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
