import org.graphstream.graph.implementations.MultiGraph;
import org.w3c.dom.Node;
import org.graphstream.graph.implementations.AbstractNode;
import org.graphstream.graph.implementations.AbstractGraph;

public class BPlusVisualizer<E extends Comparable<E>> extends BPlusTree<E> {
    private MultiGraph graph;
    private int idCounter;

    public BPlusVisualizer(int orden) {
        super(orden);
        System.setProperty("org.graphstream.ui", "swing");
        graph = new MultiGraph("Árbol B+");
        graph.setAttribute("ui.stylesheet", """
    node {
        shape: box;
        size-mode: fit;
        padding: 6px;
        fill-color: #A2D2FF;
        stroke-mode: plain;
        stroke-color: black;
        text-alignment: center;
        text-size: 14px;
    }
    edge {
        arrow-shape: none;
        fill-color: gray;
        size: 2px;
    }
""");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        idCounter = 0;
    }

    public void displayTree() {
        graph.clear();
        idCounter = 0;
        if (!isEmpty()) {
            buildGraph(this.root, null, 0);
        }
        System.setProperty("org.graphstream.ui", "swing");
        graph.display();
    }

    private String buildGraph(BPlusNode<E> node, String parentId, int level) {
    String nodeId = "N" + (idCounter++);
    StringBuilder label = new StringBuilder();
    for (int i = 0; i < node.count; i++) {
        label.append(node.keys.get(i));
        if (i < node.count - 1) label.append(" | ");
    }

    graph.addNode(nodeId);
    graph.getNode(nodeId).setAttribute("ui.label", label.toString());
    graph.getNode(nodeId).setAttribute("ui.style", "shape: box; fill-color: #A2D2FF; size-mode: fit; padding: 8px; text-size: 16px; stroke-mode: plain; stroke-color: black;");


    if (parentId != null) {
        graph.addEdge(parentId + "-" + nodeId, parentId, nodeId, true);
    }

    if (!node.isLeaf) {
        for (int i = 0; i <= node.count; i++) {
            if (node.childs.get(i) != null) {
                buildGraph(node.childs.get(i), nodeId, level + 1);
            }
        }
    }

    return nodeId;
}

}

