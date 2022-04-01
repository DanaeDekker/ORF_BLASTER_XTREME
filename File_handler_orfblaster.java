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
    LinkedHashMap<String, String> resultMap_ORF;
    ArrayList<String[]> resultMap_BLAST;


    /** 
     * @param path
     * @throws FileNotFoundException
     */
    public void read_ORF_File(String path) throws FileNotFoundException {
        File directoryPath_ORF = new File(path);
        resultMap_ORF = new LinkedHashMap<String, String>();
        try {
                    Scanner scanner = new Scanner(directoryPath_ORF);
                    String header = "";
                    String sequence = "";
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.startsWith(">")) {
                            if (!header.equals("")) {
                                System.out.println(header);
                                System.out.println(sequence);;
                                resultMap_ORF.put(header, sequence);
                            }
                            header = line.substring(1);
                            sequence = "";
                        } else {
                            sequence += line;
                        }
                    }
                    resultMap_ORF.put(header, sequence);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

        // try (Scanner scan_ORF = new Scanner(directoryPath_ORF)) {
        //     resultMap_ORF = new LinkedHashMap<>();
        //     while (scan_ORF.hasNextLine()) {
        //         String[] keys = scan_ORF.nextLine().split("\n");
        //         String[] values = scan_ORF.nextLine().split("\n");
        //         for (int i = 0; i < keys.length; i++) {
        //             System.out.println(keys[i]);
        //             String Sequence = values[i];
        //             System.out.print(Sequence);
        //             System.out.print("\n");
        //             resultMap_ORF.put(keys[i], values[i]);
        //         }
        //     }
        // }
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