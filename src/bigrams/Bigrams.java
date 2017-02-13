/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigrams;

import java.io.*;
import java.util.*;

/**
 *
 * @author Adam Leedy
 */
public class Bigrams {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        output(bigram(fileInput()));
        System.out.println("Successfully wrote to file!");
    }
    
     /**
     * fileInput reads in text from input.txt it then parses the text and puts 
     * it into an ArrayList and then returns the ArrayList of input. 
     * @return 
     */
    public static List<String> fileInput() throws IOException {
        List<String> input = new ArrayList<>();       
        Scanner reader;
        String read = "";
            File file = new File("bigrams.txt");
            reader = new Scanner(new FileInputStream(file));
            while(reader.hasNext()) {
                read=reader.next();
                input.add(read.toLowerCase());
            }
        return input; 
    }
    
    public static HashMap<String,Integer> bigram(List<String> text) {
        HashMap<String,Integer> map = new HashMap();
        for(int i = 0; i < text.size()-1; i++) {
          String bigram = text.get(i) + " " + text.get(i+1);
          if(map.containsKey(bigram)) {
              int value = map.get(bigram)+1;
              map.put(bigram, value);
          }
          else {
              map.put(bigram, 1);
          }
        }
        return map;     
    }
    
    public static void output(HashMap<String, Integer> map) throws IOException {
        Collection col = map.values();
        List list = new ArrayList(col);
        Collections.sort(list, Collections.reverseOrder());
        int cnt = 0;
        int i = 0;
        File file = new File("output.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        do {
            Iterator iterate = map.entrySet().iterator();
            if(i > 0) {
                while(list.get(i).equals(list.get(i+1))) {
                    i++;
                }
            }
            int match = (Integer)list.get(i);
            while(iterate.hasNext()) {
                Map.Entry set = (Map.Entry)iterate.next();
                if(set.getValue().equals(match)) {
                    String temp = set.getKey()+" - "+set.getValue()+" Instances";
                    output.write(temp);
                    output.newLine();
                    cnt++;
                }
                if(cnt == 20) {
                    break;
                }
            }
            i++;
        } while(cnt < 20);
        output.close();
    }
}