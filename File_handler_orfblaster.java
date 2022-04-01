// auteurs:Marco Veninga, Stef van Breemen, Martine Rijploeg, Danae Dekkers
//klas: bin2b, groep 6
// datum: 31MRT2022

import java.io. *;
import java.util.*;

/**
 * Read fasta file
 *
 */

public class File_handler_orfblaster {

    File directoryPath_BLAST = new File("test.tsv");
    HashMap<String, String> resultMap_ORF;
    ArrayList<String[]> resultMap_BLAST;


    
    /** 
     * @param path
     * @throws FileNotFoundException
     */
    public void read_ORF_File(String path) throws FileNotFoundException {
        File directoryPath_ORF = new File(path);
        try (Scanner scan_ORF = new Scanner(directoryPath_ORF)) {
            resultMap_ORF = new HashMap<>();
            while (scan_ORF.hasNextLine()) {
                String[] keys = scan_ORF.nextLine().split("\n");
                String[] values = scan_ORF.nextLine().split("\n");
                for (int i = 0; i < keys.length; i++) {
                    String Sequence = values[i];
                    System.out.println(Sequence);
                    System.out.println("\n");
                    resultMap_ORF.put(keys[i], values[i]);
                }
            }
        }
    }


    // public static void read_BLAST_File() throws IOException {
    //     try (Scanner scan_BLAST = new Scanner(directoryPath_BLAST)) {
    //         ArrayList<String[]> resultMap_BLAST = new ArrayList<String[]>();
    //         while (scan_BLAST.hasNextLine()) {
    //             String[] split = scan_BLAST.nextLine().split("\t");
    //             resultMap_BLAST.add(split);
    //         }
    //         // for (int i = 0; i < resultMap_BLAST.size(); i++) {
    //         //     System.out.println(Arrays.toString(resultMap_BLAST.get(i)));
    //         // }    
    //     }
    // }
}