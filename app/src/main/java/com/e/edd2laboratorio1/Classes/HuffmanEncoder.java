package com.e.edd2laboratorio1.Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder {
    private static final int ALPHABET_SIZE = 256;
    public HuffmanEncodeResult compressedData = new HuffmanEncodeResult("", null);
    Map<String,Integer> dataTable = new HashMap<String,Integer>();
    String[][] resultados = new String[256][2];
    static String encodedData = "";
    String encode = "";

    private static void BuildTable(Node root, String s, Map<Character, String> table) {

        if (!root.isLeaf()) {
            BuildTable(root.left, s+'0',table);
            BuildTable(root.right, s+'1',table);
        }else{
            table.put(root.character, s);
        }
    }

    private static String generateEncodedData(String data, Map<Character,String> table){

        final StringBuilder builder = new StringBuilder();
        for(final char character : data.toCharArray()){
            builder.append(table.get(character));
        }
        return builder.toString();
    }

    private static Map<Character, String> BuildTreeTable(final Node root){

        final Map<Character, String> table = new HashMap<>();
        BuildTable(root,"",table);

        return table;
    }

    public static Node BuildHuffmanTree(int[] freq){

        final PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        for (char i = 0; i < ALPHABET_SIZE; i++) {
            if(freq[i] > 0){
                priorityQueue.add(new Node(i, freq[i],null,null));
            }
        }

        if (priorityQueue.size() == 1) {
            priorityQueue.add(new Node('\0',1,null,null));
        }

        while(priorityQueue.size() > 1){
            final Node left = priorityQueue.poll();
            final Node right = priorityQueue.poll();
            final Node parent = new Node('\0',left.frequency +
                    right.frequency, left, right);
            priorityQueue.add(parent);
        }

        return priorityQueue.poll();
    }

    public static int[] BuildFrecuencyTable(final String data){

        final int[] freq = new int[ALPHABET_SIZE];
        for (final char character : data.toCharArray()) {
            freq[character]++;
        }
        return freq;
    }

    public HuffmanEncodeResult compress(final String data){
        final int[] frq = BuildFrecuencyTable(data);
        final Node root = BuildHuffmanTree(frq);
        final Map<Character, String> table = BuildTreeTable(root);
        encodedData = generateEncodedData(data,table);
        //dataTable = GenerateFile(frq);

        return new HuffmanEncodeResult(encodedData,root);
    }

    //Insert code for decompression


    static class HuffmanEncodeResult{

        final Node root;
        final String encodedData;

        public HuffmanEncodeResult(final String encodedData,
                                   final Node root) {
            this.encodedData = encodedData;
            this.root = root;
        }

        public Node getRoot(){
            return this.root;
        }

        public String GetEncodedData(){
            return this.encodedData;
        }

    }

}
