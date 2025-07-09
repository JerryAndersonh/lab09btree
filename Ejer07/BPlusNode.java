package Ejercicios_Propuestos.Ejer07;
import java.util.ArrayList;
import java.util.Collections;

//nodo del arbol B+
public class BPlusNode<E extends Comparable<E>> {
    ArrayList<E> keys;              //claves del nodo
    ArrayList<BPlusNode<E>> childs;     //hijos del nodo
    int count;                      //cantidad de claves actuales
    boolean isLeaf;                 //indica si es hoja
    BPlusNode<E> next;                  //apunta al siguiente nodo hoja (solo en hojas)

    //constructor para nodo con tipo hoja o interno
    public BPlusNode(int orden, boolean isLeaf) {
        this.count = 0;
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>(Collections.nCopies(orden - 1, null));
        this.childs = new ArrayList<>(Collections.nCopies(orden, null));
        this.next = null;
    }

    //constructor rápido (asume hoja por defecto)
    public BPlusNode(int orden) {
        this(orden, true);
    }

    //verifica si el nodo está lleno
    public boolean nodeFull(int maxKeys) {
        return this.count >= maxKeys;
    }

    //busca si existe un dato y retorna su posición
    public boolean searchNode(E key, int[] pos) {
        pos[0] = 0;
        while (pos[0] < this.count && key.compareTo(this.keys.get(pos[0])) > 0) {
            pos[0]++;
        }
        return (pos[0] < this.count && key.compareTo(this.keys.get(pos[0])) == 0);
    }
}

