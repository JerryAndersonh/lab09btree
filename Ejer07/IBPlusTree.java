package Ejercicios_Propuestos.Ejer07;

public interface IBPlusTree<E extends Comparable<E>> {
    void insert(E key);              // Insertar clave
    void remove(E key);              // Eliminar clave
    boolean search(E key);           // Buscar clave
    E Min();                         // Obtener mínimo
    E Max();                         // Obtener máximo
    E predecesor(E key);             // Obtener predecesor
    E sucesor(E key);                // Obtener sucesor
    boolean isEmpty();               // Verificar si el árbol está vacío
    void destroy();                  // Vaciar el árbol
    String toString(); 
}
