package Blok_7;
import java.io. *;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Read fasta file
 *
 */

public class File_handler_orfblaster {

    static File directoryPath = new File("src/Blok_7/deel_d.fa");

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
    }

    public static void readFile() throws FileNotFoundException {
        Scanner sc = new Scanner(directoryPath);
        HashMap<String, String> resultMap = new HashMap<>();
        while (sc.hasNextLine()) {
            String[] keys = sc.nextLine().split("\n");
            String[] values = sc.nextLine().split("\n");
            for (int i = 0; i < keys.length; i++) {
                resultMap.put(keys[i] + "\n", values[i]);
            }
        }
        for (Map.Entry<String, String> map : resultMap.entrySet()) {
            System.out.println(map);
        }

    }
}

