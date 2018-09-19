package com.e.edd2laboratorio1.Classes;

public class Node implements Comparable<Node> {

    public final char character;
    public final int frequency;
    public final Node left;
    public final Node right;

    public Node(final char character,
                final int frequency,
                final Node left,
                final Node right){
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    boolean isLeaf(){
        if(left == null && right == null){
            return true;
        }return false;
    }

    @Override
    public int compareTo(Node o) {
        final int frequencyComparator = Integer.compare(this.frequency, o.frequency);
        if(frequencyComparator != 0){
            return frequencyComparator;
        }
        return Integer.compare(this.character, o.character);
    }
}
