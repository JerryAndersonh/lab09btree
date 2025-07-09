import java.util.*;
public class TestBTree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BTree<Integer> tree = new BTree<>(4);
        int opcion;
        do {
            System.out.println("\n--- MENU ARBOL B ---");
            System.out.println("1. Insertar");
            System.out.println("2. Eliminar");
            System.out.println("3. Buscar");
            System.out.println("4. Mostrar arbol");
            System.out.println("5. Minimo");
            System.out.println("6. Maximo");
            System.out.println("7. Predecesor");
            System.out.println("8. Sucesor");
            System.out.println("9. Verificar si esta vacio");
            System.out.println("10. Destruir arbol");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");
            opcion = sc.nextInt();
            
            switch(opcion) {
                case 1:
                    System.out.print("Dato a insertar: ");
                    int x = sc.nextInt();
                    tree.insert(x);
                    break;
                case 2:
                    System.out.print("Dato a eliminar: ");
                    int del = sc.nextInt();
                    tree.remove(del);
                    break;
                case 3:
                    System.out.print("Dato a buscar: ");
                    int b = sc.nextInt();
                    System.out.println(tree.search(b) ? "Existe" : "No existe");
                    break;
                case 4:
                    System.out.println(tree.toString());
                    break;
                case 5:
                    System.out.println("Minimo: " + tree.Min());
                    break;
                case 6:
                    System.out.println("Maximo: " + tree.Max());
                    break;
                case 7:
                    System.out.print("Dato: ");
                    int p = sc.nextInt();
                    System.out.println("Predecesor: " + tree.predecesor(p));
                    break;
                case 8:
                    System.out.print("Dato: ");
                    int s = sc.nextInt();
                    System.out.println("Sucesor: " + tree.sucesor(s));
                    break;
                case 9:
                    System.out.println(tree.isEmpty() ? "Arbol vacio" : "Arbol no vacio");
                    break;
                case 10:
                    tree.destroy();
                    System.out.println("Arbol destruido");
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while(opcion != 0);
        sc.close();
    }
}
