public interface IBTree<E extends Comparable<E>> {
    void insert(E x);          //inserta un dato
    void remove(E x);          //elimina un dato
    boolean search(E x);       //busca un dato
    boolean isEmpty();         //verifica si el arbol esta vacio
    void destroy();            //elimina todo el arbol

    E Min();                   //retorna el menor valor
    E Max();                   //retorna el mayor valor
    E predecesor(E x);         //busca el predecesor de un dato
    E sucesor(E x);            //busca el sucesor de un dato

    String toString();         //devuelve el arbol en formato string
}