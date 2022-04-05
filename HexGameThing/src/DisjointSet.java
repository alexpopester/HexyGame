import java.util.Arrays;

public class DisjointSet {
    private static int size;
    private static int[] set;

    public DisjointSet(int size) {
        DisjointSet.size = size;
        set = new int[DisjointSet.size];
        Arrays.fill(set, -1);
    }
    public void union(int node1, int node2){
        int root1 = find(node1);
        int root2 = find(node2);
        if (root1 != root2) {
            unionize(root1, root2);
        }
    }
    // Method Assumptions:
//     root1 and root2 are not the same tree
//     negative number is size, positive number is parent
    private void unionize(int root1, int root2) {
        if (set[root1] == 2 || set[root2]==2){
            System.out.println("yup");
        }
        if (set[root2] < set[root1]) {
            // root2 is larger, because it is more negative
            set[root2] += set[root1];   // add the size from root1 to root2
            set[root1] = root2;       // Make root2 new root
        } else {
            // root1 is equal or larger
            set[root1] += set[root2];   // add the size from root2 to root1
            set[root2] = root1;       // Make root1 new root
        }
    }

    public int find(int node) {
        if (set[node] < 0) {
            return node;
        } else {
            set[node] = find(set[node]);
            return set[node];
        }
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (int i=0; i<set.length; i++){
            message.append("[").append(i).append("]-->{").append(set[i]).append("}\n");
        }
        return message.toString();
    }
}
