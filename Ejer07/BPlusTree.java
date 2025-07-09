package Ejercicios_Propuestos.Ejer07;

import java.util.LinkedList;

public class BPlusTree<E extends Comparable<E>> {
    private BPlusNode<E> root;
    private int orden;
    private boolean up;
    private BPlusNode<E> nDes;
    
    public BPlusTree(int orden) {
        this.orden = orden;
        this.root = new BPlusNode<>(orden, true); // iniciar como hoja vacía
    }

    public BPlusTree() {
        this.orden = 5; // puedes ajustar el valor por defecto si deseas
        this.root = new BPlusNode<>(orden, true);
    }
    //verifica si el árbol está vacío
    public boolean isEmpty() {
        return this.root == null;
    }
    //elimina todo el árbol (borra la referencia a la raíz)
    public void destroy() {
        this.root = null;
    }
    //inserta un nuevo dato al árbol B+
    public void insert(E cl) {
        up = false;
        E mediana;
        BPlusNode<E> pnew;
        //insertamos el dato y verificamos si hay subida
        mediana = push(this.root, cl);
        if (up) {
            //si sube una mediana, se crea nueva raíz
            pnew = new BPlusNode<E>(this.orden, false);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }
    //inserta recursivamente y propaga si es necesario
    private E push(BPlusNode<E> current, E cl) {
        int[] pos = new int[1];
        E mediana;
        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        }
        boolean existe = current.searchNode(cl, pos);
        if (existe && current.isLeaf) {
            System.out.println("Item duplicado\n");
            up = false;
            return null;
        }
        if (current.isLeaf) {
            if (current.nodeFull(this.orden - 1)) {
                mediana = divideLeaf(current, cl, pos[0]);
            } else {
                insertInLeaf(current, cl, pos[0]);
                up = false;
                return null;
            }
        } else {
            mediana = push(current.childs.get(pos[0]), cl);
            if (up) {
                if (current.nodeFull(this.orden - 1)) {
                    mediana = divideInternal(current, mediana, pos[0]);
                } else {
                    putNode(current, mediana, nDes, pos[0]);
                    up = false;
                }
            }
        }
        return mediana;
    }
    //inserta una clave en una hoja en la posición indicada
    private void insertInLeaf(BPlusNode<E> leaf, E cl, int k) {
        for (int i = leaf.count - 1; i >= k; i--) {
            leaf.keys.set(i + 1, leaf.keys.get(i));
        }
        leaf.keys.set(k, cl);
        leaf.count++;
    }
    //divide una hoja y retorna la clave que se subirá (copia, no se elimina)
    private E divideLeaf(BPlusNode<E> current, E cl, int k) {
        int posMediana = (orden + 1) / 2;
        BPlusNode<E> newLeaf = new BPlusNode<>(orden, true);
        //copiar mitad derecha a nueva hoja
        for (int i = posMediana, j = 0; i < orden - 1; i++, j++) {
            newLeaf.keys.set(j, current.keys.get(i));
            newLeaf.count++;
        }
        current.count = posMediana;
        //insertamos la nueva clave en la hoja correspondiente
        if (k < posMediana) {
            insertInLeaf(current, cl, k);
        } else {
            insertInLeaf(newLeaf, cl, k - posMediana);
        }
        //enlazamos las hojas
        newLeaf.next = current.next;
        current.next = newLeaf;
        up = true;
        nDes = newLeaf;
        //se copia la primera clave de la nueva hoja al padre
        return newLeaf.keys.get(0);
    }
    //divide un nodo interno y retorna la mediana que subirá
    private E divideInternal(BPlusNode<E> current, E cl, int k) {
        BPlusNode<E> rd = nDes;
        int posMediana = orden / 2;
        BPlusNode<E> newInternal = new BPlusNode<>(orden, false);
        //pasamos la mitad derecha al nuevo nodo interno
        for (int i = posMediana + 1, j = 0; i < orden - 1; i++, j++) {
            newInternal.keys.set(j, current.keys.get(i));
            newInternal.childs.set(j + 1, current.childs.get(i + 1));
            newInternal.count++;
        }
        newInternal.childs.set(0, current.childs.get(posMediana + 1));
        E median = current.keys.get(posMediana);
        current.count = posMediana;
        if (k <= posMediana) {
            putNode(current, cl, rd, k);
        } else {
            putNode(newInternal, cl, rd, k - posMediana - 1);
        }
        nDes = newInternal;
        up = true;
        return median;
    }
    //inserta un valor y su hijo derecho en el nodo actual
    private void putNode(BPlusNode<E> current, E cl, BPlusNode<E> rd, int k) {
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
    //imprimir el arbol 
    public String toString() {
        if (isEmpty()) return "B+ Tree is empty\n";
        StringBuilder sb = new StringBuilder();
        LinkedList<BPlusNode<E>> queue = new LinkedList<>();
        LinkedList<Integer> levels = new LinkedList<>();
        queue.add(root);
        levels.add(0);
        int currentLevel = -1;
        while (!queue.isEmpty()) {
            BPlusNode<E> node = queue.poll();
            int level = levels.poll();
            if (level != currentLevel) {
                currentLevel = level;
                sb.append("\nNivel ").append(level).append(": ");
            }
            sb.append("[");
            for (int i = 0; i < node.count; i++) {
                sb.append(node.keys.get(i));
                if (i < node.count - 1) sb.append(", ");
            }
            sb.append("] ");
            if (!node.isLeaf) {
                for (int i = 0; i <= node.count; i++) {
                    queue.add(node.childs.get(i));
                    levels.add(level + 1);
                }
            }
        }
        return sb.toString();
    }
    // Busca si un valor está presente en el árbol
    public boolean search(E key) {
        if (isEmpty()) return false;
        BPlusNode<E> node = root;
        while (!node.isLeaf) {
            int i = 0;
            while (i < node.count && key.compareTo(node.keys.get(i)) > 0) {
                i++;
            }
            node = node.childs.get(i);
        }
        // Buscar en la hoja
        for (int i = 0; i < node.count; i++) {
            if (node.keys.get(i).compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }
    //minimo dato del arbol
    public E Min() {
        if (isEmpty()) return null;
        BPlusNode<E> node = root;
        // ir al hijo más a la izquierda
        while (!node.isLeaf) {
            node = node.childs.get(0);
        }
        return node.keys.get(0);
    }
    //mayor dato del arbol
    public E Max() {
        if (isEmpty()) return null;
        BPlusNode<E> node = root;
        // ir al hijo más a la derecha
        while (!node.isLeaf) {
            node = node.childs.get(node.count);
        }
        return node.keys.get(node.count - 1);
    }
    // Busca el predecesor inmediato de una clave
    public E predecesor(E key) {
        if (isEmpty()) return null;
        BPlusNode<E> node = root;
        BPlusNode<E> prev = null;
        while (!node.isLeaf) {
            int i = 0;
            while (i < node.count && key.compareTo(node.keys.get(i)) > 0) {
                i++;
            }
            node = node.childs.get(i);
        }
        for (int i = node.count - 1; i >= 0; i--) {
            if (key.compareTo(node.keys.get(i)) > 0) {
                return node.keys.get(i);  // último menor que la clave
            }
        }
        // Si no hay predecesor en esta hoja, buscar en la anterior
        prev = findPrevLeaf(root, node);
        if (prev != null && prev.count > 0) {
            return prev.keys.get(prev.count - 1);
        }
        return null; // No hay predecesor
    }
    // Busca la hoja anterior enlazada a una hoja dada
    private BPlusNode<E> findPrevLeaf(BPlusNode<E> current, BPlusNode<E> target) {
        if (current == null || current.isLeaf) return null;
        for (int i = 0; i <= current.count; i++) {
            BPlusNode<E> child = current.childs.get(i);
            if (child == target) {
                if (i > 0) {
                    // Buscar la hoja más a la derecha del hermano anterior
                    BPlusNode<E> prev = current.childs.get(i - 1);
                    while (!prev.isLeaf) {
                        prev = prev.childs.get(prev.count);
                    }
                    return prev;
                }
            } else {
                BPlusNode<E> found = findPrevLeaf(child, target);
                if (found != null) return found;
            }
        }
        return null;
    }
    // Busca el sucesor inmediato de una clave
    public E sucesor(E key) {
        if (isEmpty()) return null;
        BPlusNode<E> node = root;
        while (!node.isLeaf) {
            int i = 0;
            while (i < node.count && key.compareTo(node.keys.get(i)) >= 0) {
                i++;
            }
            node = node.childs.get(i);
        }
        for (int i = 0; i < node.count; i++) {
            if (key.compareTo(node.keys.get(i)) < 0) {
                return node.keys.get(i);  // primer valor mayor que la clave
            }
        }
        // Si no hay sucesor en esta hoja, buscar en la siguiente
        if (node.next != null && node.next.count > 0) {
            return node.next.keys.get(0);
        }
        return null; // No hay sucesor
    }
    // Elimina una clave del árbol B+
    public void remove(E key) {
        if (isEmpty()) {
            System.out.println("Arbol vacío.");
            return;
        }
        delete(root, key);
        // Si la raíz se quedó sin claves y no es hoja, bajamos el árbol
        if (!root.isLeaf && root.count == 0) {
            root = root.childs.get(0);
        }
    }
    // Elimina recursivamente desde un nodo
    private void delete(BPlusNode<E> node, E key) {
        int i = 0;
        while (i < node.count && key.compareTo(node.keys.get(i)) > 0) {
            i++;
        }
        if (node.isLeaf) {
            // Buscar y eliminar en la hoja
            if (i < node.count && key.compareTo(node.keys.get(i)) == 0) {
                for (int j = i; j < node.count - 1; j++) {
                    node.keys.set(j, node.keys.get(j + 1));
                }
                node.keys.set(node.count - 1, null);
                node.count--;
            } else {
                System.out.println("Clave no encontrada en la hoja.");
            }
            return;
        }
        // Si no es hoja, bajar al hijo correspondiente
        BPlusNode<E> child = node.childs.get(i);
        delete(child, key);
        // Si el hijo quedó con menos claves de las mínimas, reestructurar
        int minKeys = (orden - 1) / 2;
        if (child.count < minKeys) {
            BPlusNode<E> left = (i > 0) ? node.childs.get(i - 1) : null;
            BPlusNode<E> right = (i < node.count) ? node.childs.get(i + 1) : null;
            // Redistribuir desde izquierda
            if (left != null && left.count > minKeys) {
                for (int j = child.count; j > 0; j--) {
                    child.keys.set(j, child.keys.get(j - 1));
                }
                child.keys.set(0, node.keys.get(i - 1));
                child.count++;
                node.keys.set(i - 1, left.keys.get(left.count - 1));
                left.count--;
            }
            // Redistribuir desde derecha
            else if (right != null && right.count > minKeys) {
                child.keys.set(child.count, node.keys.get(i));
                child.count++;
                node.keys.set(i, right.keys.get(0));
                for (int j = 0; j < right.count - 1; j++) {
                    right.keys.set(j, right.keys.get(j + 1));
                }
                right.count--;
            }
            // Fusión
            else {
                if (left != null) {
                    fuseNode(left, child, node, i - 1);
                } else if (right != null) {
                    fuseNode(child, right, node, i);
                }
            }
        }
    }
    //fusiona dos nodos cuando hay eliminacion y se necesita balancear
    private void fuseNode(BPlusNode<E> left, BPlusNode<E> right, BPlusNode<E> parent, int index) {
        // Copiar claves del nodo derecho al izquierdo
        for (int i = 0; i < right.count; i++) {
            left.keys.set(left.count + i, right.keys.get(i));
        }
        // Si no son hojas, también copiamos los hijos (internos)
        if (!left.isLeaf) {
            for (int i = 0; i <= right.count; i++) {
                left.childs.set(left.count + i, right.childs.get(i));
            }
        } else {
            // Si son hojas, conectamos el next
            left.next = right.next;
        }
        // Actualizar el número total de claves del nodo fusionado
        left.count += right.count;
        // Eliminar la clave guía del padre
        for (int i = index; i < parent.count - 1; i++) {
            parent.keys.set(i, parent.keys.get(i + 1));
            parent.childs.set(i + 1, parent.childs.get(i + 2));
        }
        // Limpiar el espacio sobrante
        parent.keys.set(parent.count - 1, null);
        parent.childs.set(parent.count, null);
        parent.count--;
    }
}
