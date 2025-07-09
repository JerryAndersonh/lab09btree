
import java.util.Scanner;

public class GraphBApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el orden del Árbol B (mínimo 3): ");
        int orden = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        if (orden < 3) {
            System.out.println("El orden mínimo válido es 3. Finalizando...");
            return;
        }

        BVisualizer<Integer> btree = new BVisualizer<>(orden);

        while (true) {
            System.out.println("\n=== VISUALIZADOR DE ÁRBOL B (orden " + orden + ") ===");
            System.out.println("1. Insertar desde palabra");
            System.out.println("2. Insertar números manualmente");
            System.out.println("3. Buscar elemento");
            System.out.println("4. Mostrar información del árbol");
            System.out.println("5. Visualizar árbol");
            System.out.println("6. Limpiar árbol");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese una palabra: ");
                    String palabra = scanner.nextLine();
                    for (char c : palabra.toCharArray()) {
                        btree.insert((int) c);
                        System.out.println("Insertado: '" + c + "' (ASCII: " + (int) c + ")");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese números separados por espacios: ");
                    String[] numeros = scanner.nextLine().split(" ");
                    for (String num : numeros) {
                        try {
                            int valor = Integer.parseInt(num.trim());
                            btree.insert(valor);
                            System.out.println("Insertado: " + valor);
                        } catch (NumberFormatException e) {
                            System.out.println("'" + num + "' no es un número válido.");
                        }
                    }
                    break;

                case 3:
                    System.out.print("Ingrese elemento a buscar: ");
                    int buscar = Integer.parseInt(scanner.nextLine());
                    boolean encontrado = btree.search(buscar);
                    System.out.println("Elemento " + buscar + (encontrado ? " SÍ está en el árbol." : " NO está en el árbol."));
                    break;

                case 4:
                    if (btree.isEmpty()) {
                        System.out.println("El árbol está vacío.");
                    } else {
                        btree.showTreeInfo();
                    }
                    break;

                case 5:
                    if (btree.isEmpty()) {
                        System.out.println("El árbol está vacío. Inserte elementos primero.");
                    } else {
                        System.out.println("Mostrando visualización del árbol...");
                        btree.displayTree();
                    }
                    break;

                case 6:
                    btree.destroy();
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

