public class BTree<E extends Comparable<E>> implements IBTree<E> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;

    public BTree(int orden) {
        this.orden = orden;
        this.root = null;
    }
    public BTree() {}
    //verifica si el arbol esta vacio
    public boolean isEmpty() {
        return this.root == null;
    }
    //elimina todo el arbol
    public void destroy() {
        this.root = null;
    }
    //inserta un nuevo dato al arbol
    public void insert(E cl) {
        up = false;
        E mediana;
        BNode<E> pnew;
        //insertamos el dato y verificamos si hay subida
        mediana = push(this.root, cl);
        if (up) {
            //si sube una mediana, se crea nueva raiz
            pnew = new BNode<E>(this.orden);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }
    //inserta recursivamente y propaga si es necesario
    private E push(BNode<E> current, E cl) {
        int pos[] = new int[1];
        E mediana;
        if (current == null) {
            //si llegamos a hoja nula, se retorna el dato para subir
            up = true;
            nDes = null;
            return cl;
        }
        boolean existe = current.searchNode(cl, pos);
        if (existe) {
            System.out.println("Item duplicado\n");
            up = false;
            return null;
        }
        //bajamos por el hijo que corresponde segun el valor
        mediana = push(current.childs.get(pos[0]), cl);
        if (up) {
            if (current.nodeFull(this.orden - 1)) {
                //si esta lleno se divide
                mediana = dividedNode(current, mediana, pos[0]);
            } else {
                //si hay espacio solo se inserta
                putNode(current, mediana, nDes, pos[0]);
                up = false;
            }
        }
        return mediana;
    }
    //inserta un valor y su hijo derecho en el nodo actual
    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        //desplazamos claves y punteros a la derecha
        for (int i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }
        //insertamos la clave y el hijo
        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }
    //divide un nodo en dos y retorna la mediana que subira
    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;
        int posMediana;
        //calculamos posicion de la mediana segun donde entra el nuevo dato
        posMediana = (k <= this.orden / 2) ? this.orden / 2 : this.orden / 2 + 1;
        nDes = new BNode<E>(this.orden);
        //pasamos la mitad derecha al nuevo nodo
        for (int i = posMediana; i < this.orden - 1; i++) {
            nDes.keys.set(i - posMediana, current.keys.get(i));
            nDes.childs.set(i - posMediana + 1, current.childs.get(i + 1));
        }
        nDes.count = (this.orden - 1) - posMediana;
        current.count = posMediana;
        if (k <= this.orden / 2) {
            //insertamos en el nodo original
            putNode(current, cl, rd, k);
        } else {
            //insertamos en el nodo nuevo
            putNode(nDes, cl, rd, k - posMediana);
        }
        //sacamos la mediana para subirla
        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;
        return median;
    }
    //devuelve el contenido del arbol en orden con separadores
    public String toString() {
        if (isEmpty()) return "BTree is empty";
        return writeTree(this.root);
    }
    //recorre el arbol en orden y arma el string
    private String writeTree(BNode<E> current) {
        String s = "| ";
        for (int i = 0; i < current.count; i++) {
            if (current.childs.get(i) != null)
                s += writeTree(current.childs.get(i));
            s += current.keys.get(i).toString() + " ";
        }
        if (current.childs.get(current.count) != null)
            s += writeTree(current.childs.get(current.count));
        s += "| ";
        return s;
    }
    //busca un valor en el arbol
    public boolean search(E key) {
        return searchRec(this.root, key);
    }
    //busqueda recursiva
    private boolean searchRec(BNode<E> node, E key) {
        if (node == null) return false;
        //buscamos la posicion donde deberia estar
        int i = 0;
        while (i < node.count && key.compareTo(node.keys.get(i)) > 0) i++;
        if (i < node.count && key.compareTo(node.keys.get(i)) == 0)
            return true;
        //si no esta, bajamos al hijo correspondiente
        return searchRec(node.childs.get(i), key);
    }
    //devuelve el menor valor del arbol
    public E Min() {
        if (this.isEmpty()) return null;
        BNode<E> current = this.root;
        //vamos siempre al hijo izquierdo
        while (current.childs.get(0) != null) {
            current = current.childs.get(0);
        }
        return current.keys.get(0);
    }
    //devuelve el mayor valor del arbol
    public E Max() {
        if (this.isEmpty()) return null;
        BNode<E> current = this.root;
        //vamos siempre al hijo derecho
        while (current.childs.get(current.count) != null) {
            current = current.childs.get(current.count);
        }
        return current.keys.get(current.count - 1);
    }
    //busca el predecesor de un valor
    public E predecesor(E key) {
        return predecesorRec(this.root, key, null);
    }
    //busqueda del predecesor recursiva
    private E predecesorRec(BNode<E> node, E key, E lastLeft) {
        if (node == null) return lastLeft;
        int i = 0;
        while (i < node.count && key.compareTo(node.keys.get(i)) > 0) {
            lastLeft = node.keys.get(i); //posible predecesor
            i++;
        }
        if (i < node.count && key.compareTo(node.keys.get(i)) == 0) {
            //si hay hijo izquierdo, buscamos el mayor ahi
            if (node.childs.get(i) != null) {
                BNode<E> tmp = node.childs.get(i);
                while (tmp.childs.get(tmp.count) != null) {
                    tmp = tmp.childs.get(tmp.count);
                }
                return tmp.keys.get(tmp.count - 1);
            }
            return lastLeft;
        }
        return predecesorRec(node.childs.get(i), key, lastLeft);
    }
    //busca el sucesor de un valor
    public E sucesor(E key) {
        return sucesorRec(this.root, key, null);
    }
    //busqueda del sucesor recursiva
    private E sucesorRec(BNode<E> node, E key, E lastRight) {
        if (node == null) return lastRight;
        int i = 0;
        while (i < node.count && key.compareTo(node.keys.get(i)) > 0) {
            i++;
        }
        if (i < node.count && key.compareTo(node.keys.get(i)) == 0) {
            //si hay hijo derecho, buscamos el menor ahi
            if (node.childs.get(i + 1) != null) {
                BNode<E> tmp = node.childs.get(i + 1);
                while (tmp.childs.get(0) != null) {
                    tmp = tmp.childs.get(0);
                }
                return tmp.keys.get(0);
            }
            return lastRight;
        }
        if (i < node.count) lastRight = node.keys.get(i);
        return sucesorRec(node.childs.get(i), key, lastRight);
    }
    //elimina un valor del arbol si existe
    public void remove(E value) {
        delete(this.root, value);

        //si despues de eliminar la raiz queda vacia, bajamos un nivel
        if (this.root != null && this.root.count == 0) {
            this.root = this.root.childs.get(0);
        }
    }
    //elimina recursivamente un valor desde un nodo
    private void delete(BNode<E> current, E value) {
        int i = 0;
        //buscamos la posicion donde puede estar la clave
        while (i < current.count && value.compareTo(current.keys.get(i)) > 0) {
            i++;
        }
        if (i < current.count && value.compareTo(current.keys.get(i)) == 0) {
            //la clave esta en el nodo actual
            if (current.childs.get(i) == null) {
                //es hoja, se elimina directo
                for (int j = i; j < current.count - 1; j++) {
                    current.keys.set(j, current.keys.get(j + 1));
                }
                current.count--;
            } else {
                //si no es hoja, buscamos el sucesor para reemplazar
                BNode<E> aux = current.childs.get(i + 1);
                while (aux.childs.get(0) != null)
                    aux = aux.childs.get(0);
                E successor = aux.keys.get(0);
                current.keys.set(i, successor);
                delete(current.childs.get(i + 1), successor);
            }
        } else {
            //la clave esta en algun hijo
            if (current.childs.get(i) == null) {
                //no existe el valor
                System.out.println("El valor no existe en el arbol.");
                return;
            }
            //verificamos si el hijo tiene suficientes claves
            if (current.childs.get(i).count < (orden - 1) / 2 + 1) {
                BNode<E> left = (i > 0) ? current.childs.get(i - 1) : null;
                BNode<E> right = (i < current.count) ? current.childs.get(i + 1) : null;
                //intentamos tomar prestado de la izquierda
                if (left != null && left.count > (orden - 1) / 2) {
                    //rotamos desde la izquierda
                    BNode<E> child = current.childs.get(i);
                    for (int j = child.count - 1; j >= 0; j--) {
                        child.keys.set(j + 1, child.keys.get(j));
                    }
                    for (int j = child.count; j >= 0; j--) {
                        child.childs.set(j + 1, child.childs.get(j));
                    }
                    child.keys.set(0, current.keys.get(i - 1));
                    child.childs.set(0, left.childs.get(left.count));
                    child.count++;
                    current.keys.set(i - 1, left.keys.get(left.count - 1));
                    left.count--;
                }
                //intentamos tomar prestado de la derecha
                else if (right != null && right.count > (orden - 1) / 2) {
                    BNode<E> child = current.childs.get(i);
                    child.keys.set(child.count, current.keys.get(i));
                    child.childs.set(child.count + 1, right.childs.get(0));
                    child.count++;
                    current.keys.set(i, right.keys.get(0));
                    for (int j = 0; j < right.count - 1; j++) {
                        right.keys.set(j, right.keys.get(j + 1));
                    }
                    for (int j = 0; j < right.count; j++) {
                        right.childs.set(j, right.childs.get(j + 1));
                    }
                    right.count--;
                }
                //si no puede tomar prestado, se fusiona
                else {
                    if (left != null) {
                        FuzeNode(left, current.childs.get(i), current, i - 1);
                        delete(left, value);
                    } else if (right != null) {
                        FuzeNode(current.childs.get(i), right, current, i);
                        delete(current.childs.get(i), value);
                    }
                    return;
                }
            }
            //continuamos la recursion en el hijo adecuado
            delete(current.childs.get(i), value);
        }
    }
    //fusiona dos nodos cuando hay eliminacion y se necesita balancear
    private void FuzeNode(BNode<E> left, BNode<E> right, BNode<E> parent, int index) {
        //insertamos el separador del padre al final del hijo izquierdo
        left.keys.set(left.count, parent.keys.get(index));
        left.count++;
        //copiamos todas las claves y punteros del hijo derecho
        for (int i = 0; i < right.count; i++) {
            left.keys.set(left.count, right.keys.get(i));
            left.childs.set(left.count, right.childs.get(i));
            left.count++;
        }
        //copiamos el ultimo puntero del hijo derecho
        left.childs.set(left.count, right.childs.get(right.count));
        //movemos las claves y punteros del padre hacia la izquierda
        for (int i = index; i < parent.count - 1; i++) {
            parent.keys.set(i, parent.keys.get(i + 1));
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }
        //reducimos la cantidad de claves del padre
        parent.count--;
    }
}
