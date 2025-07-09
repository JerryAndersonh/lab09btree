import java.util.*;

public class BNode<E extends Comparable<E>> {
    protected ArrayList<E> keys;
    protected ArrayList<BNode<E>> childs;
    protected int count;

    public BNode(int n) {
        this.count = 0;
        this.keys = new ArrayList<>();
        this.childs = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            this.keys.add(null);
        }
        for (int i = 0; i < n; i++) {
            this.childs.add(null);
        }
    }

    public boolean nodeEmpty() {
        return count == 0;
    }

    public boolean searchNode(E key, int[] pos) {
        int i = 0;
        while (i < count && keys.get(i).compareTo(key) < 0) {
            i++;
        }
        pos[0] = i;
        return i < count && keys.get(i).compareTo(key) == 0;
    }

    public boolean nodeFull(int maxKeys) {
        return count == maxKeys;
    }
}
