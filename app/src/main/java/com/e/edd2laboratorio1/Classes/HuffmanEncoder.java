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
/*
    public String decompress(final HuffmanEncodeResult result ){
        final StringBuilder resultBuilder  = new StringBuilder();
        Node current = result.getRoot();
        int i = 0;
        while( i < result.GetEncodedData().length()){

            while(!current.isLeaf()){
                char bit = result.GetEncodedData().charAt(i);
                if(bit == '1'){
                    current = current.right;
                }else if (bit == '0'){
                    current = current.left;
                }else{
                    throw new IllegalArgumentException("Invalid bit" + bit );
                }
                i++;
            }
            resultBuilder.append(current.character);
            current = result.getRoot();
        }

        return resultBuilder.toString();

    }

    public Map<String,Integer> GenerateFile(int[] frq) {

        Map<String,Integer> table = new HashMap<String,Integer>();

        try {
            String line = "";
            File file = new File("C:\\MEIA\\huff.txt");
            FileWriter Escribir = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(Escribir);
            bw.write(encodedData +  System.getProperty( "line.separator" ));
            for (int i = 0; i < 256; i++) {
                if(frq[i] != 0) {

                    char var = (char)i;
                    table.put(String.valueOf(var), frq[i]);
                    line = "";
                    line =  String.valueOf(var) + "," + frq[i];
                    bw.write(line + System.getProperty( "line.separator" ));
                }
            }
            bw.close();
            Escribir.close();
        }
        catch(IOException ex)
        {

        }
        return table;
    }

    public HuffmanEncodeResult ReadFile(File file) {
        try {
            Scanner inputFile = new Scanner(file);
            int i = 0, j =0, cont = 0;

            setMatrix();
            while(inputFile.hasNext()) {
                if(cont == 0) {
                    encode = inputFile.nextLine();
                    cont++;
                }
                else {
                    String linea = inputFile.nextLine();
                    String[] splited = linea.split(",");
                    resultados[i][j] = String.valueOf(splited[0]);
                    j++;
                    resultados[i][j] = String.valueOf(splited[1]);
                    i++;
                    j=0;
                }
            }
        }
        catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo", "Error",WIDTH);
        }
        final int[] frq = RebuildTable(resultados);
        final Node root = BuildHuffmanTree(frq);
        final Map<Character,String> table = BuildTreeTable(root);
        return new HuffmanEncodeResult(encode,root);
    }

    private int[] RebuildTable(String[][] readTable) {
        final int[] freq = new int[ALPHABET_SIZE];

        for (int i = 0; i < 256; i++) {
            char character = readTable[i][0].charAt(0);
            int ascii = (int)character;
            if (ascii > 0) {
                freq[ascii] = Integer.valueOf(readTable[i][1]);
            }
            else {
                freq[i] = 0;
            }

        }
        return freq;
    }

    private void setMatrix() {
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 2; j++) {
                resultados[i][j] = String.valueOf(0);
            }
        }
    }

*/
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
