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
        }
    
        public void read_BLAST_File(String path) throws IOException {
            File directoryPath_BLAST = new File(path);
            try (Scanner scan_BLAST = new Scanner(directoryPath_BLAST)) {
                while (scan_BLAST.hasNextLine()) {
                    String[] array;
                    String[] split = scan_BLAST.nextLine().split("\t");
                    List<String> resultmap = new ArrayList<String>(Arrays.asList(split));
                    resultmap.remove(2);
                    array = resultmap.toArray(new String[0]);
                    System.out.println(Arrays.toString(array));
                }
            }
        }
}