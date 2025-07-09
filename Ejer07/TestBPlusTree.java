package Ejercicios_Propuestos.Ejer07;

import java.util.Scanner;

public class TestBPlusTree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BPlusTree<Integer> tree = new BPlusTree<>(5); // Orden del arbol B+

        int option;
        do {
            System.out.println("\n--- MENU B+ TREE ---");
            System.out.println("1. Insertar clave");
            System.out.println("2. Eliminar clave");
            System.out.println("3. Buscar clave");
            System.out.println("4. Mostrar arbol (en orden)");
            System.out.println("5. Obtener minimo");
            System.out.println("6. Obtener maximo");
            System.out.println("7. Obtener predecesor");
            System.out.println("8. Obtener sucesor");
            System.out.println("9. Vaciar arbol");
            System.out.println("10. Verificar si esta vacio");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            
            // Validar si se ingresa numero
            while (!sc.hasNextInt()) {
                System.out.print("Por favor ingrese un numero valido: ");
                sc.next();
            }
            option = sc.nextInt();

            switch (option) {
                case 1 -> {
                    System.out.print("Ingrese clave a insertar: ");
                    int insertKey = sc.nextInt();
                    tree.insert(insertKey);
                    System.out.println("Insertado correctamente.");
                }
                case 2 -> {
                    System.out.print("Ingrese clave a eliminar: ");
                    int deleteKey = sc.nextInt();
                    tree.remove(deleteKey);
                }
                case 3 -> {
                    System.out.print("Ingrese clave a buscar: ");
                    int searchKey = sc.nextInt();
                    System.out.println("Existe?: " + tree.search(searchKey));
                }
                case 4 -> {
                    System.out.println("Arbol B+ en orden:");
                    System.out.println(tree);
                }
                case 5 -> System.out.println("Minimo: " + tree.Min());
                case 6 -> System.out.println("Maximo: " + tree.Max());
                case 7 -> {
                    System.out.print("Ingrese clave para predecesor: ");
                    int keyPred = sc.nextInt();
                    System.out.println("Predecesor: " + tree.predecesor(keyPred));
                }
                case 8 -> {
                    System.out.print("Ingrese clave para sucesor: ");
                    int keySucc = sc.nextInt();
                    System.out.println("Sucesor: " + tree.sucesor(keySucc));
                }
                case 9 -> {
                    tree.destroy();
                    System.out.println("Arbol eliminado.");
                }
                case 10 -> System.out.println("Esta vacio?: " + tree.isEmpty());
                case 0 -> System.out.println("Hasta luego!");
                default -> System.out.println("Opcion no valida.");
            }

        } while (option != 0);

        sc.close();
    }
}
