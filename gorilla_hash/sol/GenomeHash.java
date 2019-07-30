// run with:
// javac GenomeHash.java && time java -Xmx6g GenomeHash ../bigdata.txt

import edu.princeton.cs.algs4.*;
import java.util.*;
import java.math.*;
import java.util.regex.*;

public class GenomeHash {

    private static Hashtable<String,String> genomes;
    private static Hashtable<String,int[]> profiles;
    private static final int k = 20;
    private static final int d = 100000000;

    public GenomeHash(){
        genomes = new Hashtable<>();
        profiles = new Hashtable<>();
    }

    public static HashSet<String> buildkGrams(String genome){
        HashSet<String> kGrams = new HashSet<>();
        for (int i=0; i<genome.length(); i++){
            if ((i+k) < (genome.length() - k)){
                String gram = genome.substring(i,i+k);
                kGrams.add(gram);
            }
        }        
        String last = genome.substring(genome.length()-k);
        kGrams.add(last);
        return kGrams;
    }

    public static int hash(String str) {
        return ((str.hashCode() & 0x7fffffff) % d);
    }

    public static int[] calculateProfile(HashSet<String> kGrams){
        int[] profile = new int[d];
        for (String gram : kGrams){
            int hashed = hash(gram);
            profile[hashed]++;
        }
        return profile;
    }

    public static double calculateVectorLength(int[] profile){
        int length = 0;
        for (int i=0; i<profile.length; i++){
            length += (profile[i] * profile[i]);
        }
        double result = Math.sqrt(length);
        return result;
    }

    public static int calculateDotProduct(int[] left, int[] right){
        int dotProduct = 0;
        for(int i=0; (i < left.length) && (i < right.length); i++){
            dotProduct += left[i] * right[i];
        }
        return dotProduct;
    }

    public static void main(String[] args){
        GenomeHash genomeHash = new GenomeHash();

        StringBuilder stringBuilder = new StringBuilder("");
        In inputFile = new In(args[0]);
        String genome = "";
        String species = "";
        String previousSpecies = "";

        while (inputFile.hasNextLine()){
            String readLine = inputFile.readLine();
            
            if (readLine.startsWith(">")){
                Pattern pattern = Pattern.compile("\\>([\\D]+)\\ .");
                Matcher m = pattern.matcher(readLine);
                m.find();
                String extracted_species = m.group(1);
                previousSpecies = species;
                species = extracted_species;

                if (stringBuilder.length() != 0){
                    genome = stringBuilder.toString();
                    genomes.put(previousSpecies, genome);
                    stringBuilder = new StringBuilder();
                }
            } else {
                stringBuilder.append(readLine);
            }
        } 
        genome = stringBuilder.toString();
        genomes.put(species, genome);

        // calculate profiles for each species
        for (String key : genomes.keySet()){
            HashSet<String> kGram = buildkGrams(genomes.get(key));
            int[] profile = calculateProfile(kGram);
            profiles.put(key,profile);
        }

        // compare species
        for (String outerKey : profiles.keySet()){
            System.err.println();
            for (String innerKey : profiles.keySet()){
                int[] left = profiles.get(outerKey);
                int[] right = profiles.get(innerKey);
                float likeness = (float)(calculateDotProduct(left,right)) / (float)(calculateVectorLength(left) * calculateVectorLength(right));
                System.err.println(String.format("Comparing %s with %s likeness: %f", outerKey, innerKey, likeness));
            }
        }

    // tests
    System.err.println(calculateDotProduct(new int[] {0,1}, new int[] {0,1})); // returns 1
    System.err.println(calculateDotProduct(new int[] {0,1}, new int[] {0,2})); // returns 2
    System.err.println(calculateDotProduct(new int[] {0,1}, new int[] {1, 0})); // returns 0
    System.err.println(calculateDotProduct(new int[] {0,1}, new int[] {0, -1})); // returns -1
    System.err.println(calculateDotProduct(new int[] {0,1,1,1,0}, new int[] {0,1,1,0,0})); // returns 2
    System.err.println(calculateVectorLength(new int[] {0,0,0,1})); // returns 1.0
    System.err.println(calculateVectorLength(new int[] {0,0,1,0,1,01,1})); // returns 2.0
    }
}