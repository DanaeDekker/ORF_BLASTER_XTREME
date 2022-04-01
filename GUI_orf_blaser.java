// auteurs:Marco Veninga, Stef van Breemen, Martine Rijploeg, Danae Dekkers
//klas: bin2b, groep 6
// datum: 31MRT2022

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.*;


public class GUI_orf_blaser extends JFrame implements ActionListener{

    private File file;
    private File_handler_orfblaster  c_file = new File_handler_orfblaster();
    private JButton openButton, orfipy_button, blast_button;
    private JFileChooser file_chooser;
    private JTextField namefield, orf_min, orf_max, file_name, expect_value;
    private JTextArea orffield;
    private JScrollPane orfpanelscroll;
    private JComboBox t_table, orf_mode, matrix, word_size, database;
    private JLabel orf_min_label, orf_max_label, ignore_case_but, file_name_results, word_size_label, expect_label;
    private JCheckBox ignore_case;
    private String[] menu_list = {"Select translation table", "1 Standard", "2 Vertebrate Mitochondrial", "3 Yeast Mitochondrial", "4 Mold Mitochondrial; Protozoan Mitochondrial; Coelenterate Mitochondrial; Mycoplasma; Spiroplasma",
            "5 Invertebrate Mitochondrial", "6 Ciliate Nuclear; Dasycladacean Nuclear; Hexamita Nuclear", "7 Echinoderm Mitochondrial; Flatworm Mitochondrial",
            "8 Euplotid Nuclear", "9 Bacterial, Archaeal and Plant Plastid", "10 Alternative Yeast Nuclear", "11 Ascidian Mitochondrial", "12 Alternative Flatworm Mitochondrial",
            "13 Chlorophycean Mitochondrial", "14 Trematode Mitochondrial", "15 Scenedesmus obliquus Mitochondrial Code", "16 Thraustochytrium mitochondrial code",
            "17 Pterobranchia Mitochondrial", "18 Candidate Division SR1 and Gracilibacteria", "19 Pachysolen tannophilus Nuclear Code", "20 Karyorelict Nuclear",
            "21 Condylostoma Nuclear", "22 Mesodinium Nuclear", "23 Peritrich Nuclear"};
    private String[] tables = {"", "--table 1", "--table 2", "--table 3", "--table 4", "--table 5", "--table 6", "--table 9", "--table 10", "--table 11", "--table 12", "--table 13", "--table 14", "--table 16",
            "--table 21", "--table 22", "--table 23", "--table 24", "--table 25", "--table 26", "--table 27", "--table 28", "--table 29", "--table 30"};



    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exceptionopen_file(
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }


        GUI_orf_blaser frame = new GUI_orf_blaser();
        frame.createGUI();
        frame.pack();
        frame.setVisible(true);
        frame.pack();
    }

    public void createGUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setBackground(Color.lightGray);
        window.setLayout(new GridBagLayout());
        GridBagConstraints gridcon = new GridBagConstraints();
        gridcon.fill = GridBagConstraints.HORIZONTAL;

        namefield = new JTextField(25);
        gridcon.gridx =  0;
        gridcon.gridy  = 0;
        gridcon.anchor = GridBagConstraints.CENTER;
        gridcon.insets = new Insets(10,10,10,10);
        window.add(namefield, gridcon);

        openButton = new JButton("Select file");
        openButton.setPreferredSize(new Dimension(100,25));
        gridcon.gridx = 1;
        gridcon.gridy = 0;
        gridcon.anchor = GridBagConstraints.CENTER;
        gridcon.insets = new Insets(10,10,10,10);
        window.add(openButton, gridcon);
        openButton.addActionListener(this);

        t_table = new JComboBox(menu_list);
        t_table.setSelectedIndex(0);
        gridcon.gridx = 0;
        gridcon.gridy = 1;
        gridcon.gridwidth = 1;
        gridcon.insets = new Insets(0,10,10,10);
        window.add(t_table, gridcon);


        orf_min_label = new JLabel("Min ORF lenght");
        gridcon.gridx = 0;
        gridcon.gridy = 2;
        gridcon.gridwidth = 1;
        gridcon.anchor = GridBagConstraints.LINE_START;
        gridcon.insets = new Insets(0,10,0,50);
        window.add(orf_min_label, gridcon);

        gridcon.fill = GridBagConstraints.HORIZONTAL;
        orf_min = new JTextField(10);
        orf_min.setBackground(Color.WHITE);
        gridcon.gridx = 0;
        gridcon.gridy = 2;
        gridcon.anchor = GridBagConstraints.CENTER;
        gridcon.insets = new Insets(0,150,0,500);
        window.add(orf_min, gridcon);

        orf_max_label = new JLabel("Max ORF lenght");
        gridcon.gridx = 0;
        gridcon.gridy = 2;
        gridcon.gridwidth = 1;
        gridcon.anchor = GridBagConstraints.LINE_START;
        gridcon.insets = new Insets(0, 325,0,50);
        window.add(orf_max_label, gridcon);

        gridcon.fill = GridBagConstraints.HORIZONTAL;
        orf_max = new JTextField(10);
        orf_max.setSize(30,10);
        orf_max.setBackground(Color.WHITE);
        gridcon.gridx = 0;
        gridcon.gridy = 2;
        gridcon.anchor = GridBagConstraints.CENTER;
        gridcon.insets = new Insets(0,450,0,150);
        window.add(orf_max, gridcon);

        ignore_case_but =  new JLabel("Ignore case");
        gridcon.gridx = 0;
        gridcon.gridy = 2;
        gridcon.gridwidth = 1;
        gridcon.anchor = GridBagConstraints.LINE_END;
        gridcon.insets = new Insets(0,700,0,10);
        window.add(ignore_case_but, gridcon);

        ignore_case =  new JCheckBox();
        ignore_case.setSelected(false);
        ignore_case.setBackground(Color.lightGray);
        gridcon.gridx = 1;
        gridcon.gridy = 2;
        gridcon.anchor =  GridBagConstraints.CENTER;
        gridcon.insets = new Insets(0,0,0,10);
        window.add(ignore_case, gridcon);


        String[] orf_menu_list = {"Select modus", "Start to stop", "--Partial-3", "--partial-5", "--between-stops"};
        orf_mode = new JComboBox(orf_menu_list);
        orf_mode.setSelectedIndex(0);
        gridcon.gridx = 0;
        gridcon.gridy = 3;
        gridcon.gridwidth = 1;
        gridcon.insets = new Insets(10,10,10,500);
        window.add(orf_mode, gridcon);

        orfipy_button = new JButton("Find ORFs");
        gridcon.gridx = 0;
        gridcon.gridy = 3;
        gridcon.anchor = GridBagConstraints.CENTER;
        gridcon.insets = new Insets(10,300 ,10,10);
        window.add(orfipy_button, gridcon);
        orfipy_button.addActionListener(this);

        file_name_results =  new JLabel("Blast results file name");
        gridcon.gridx = 0;
        gridcon.gridy = 4;
        gridcon.insets = new Insets(0,10,0,0);
        window.add(file_name_results, gridcon);

        file_name = new JTextField();
        file_name.setBackground(Color.white);
        gridcon.gridx = 0;
        gridcon.gridy = 4;
        gridcon.insets = new Insets(0,200,0,300);
        window.add(file_name, gridcon);

        String[] matrix_menu =  {"Select score matrix","PAM30","PAM70", "PAM250", "BLOSUM80", "BLOSUM62", "BLOSUM45", "BLOSUM50", "BLOSUM90"};
        matrix =  new JComboBox<>(matrix_menu);
        matrix.setSelectedIndex(0);
        gridcon.gridx = 0;
        gridcon.gridy = 4;
        gridcon.insets =  new Insets(0,500,0,10);
        window.add(matrix, gridcon);

        String[] database_menu = {"Select database", "Swissprot"};
        database = new JComboBox<>(database_menu);
        database.setSelectedIndex(0);
        gridcon.gridx = 0;
        gridcon.gridy = 5;
        gridcon.insets = new Insets(10,10,10,600);
        window.add(database, gridcon);

        word_size_label =  new JLabel("Word size");
        gridcon.gridx = 0;
        gridcon.gridy = 5;
        gridcon.insets = new Insets(0,200,0,0);
        window.add(word_size_label, gridcon);

        String[] word_size_menu = {"2", "3", "6"};
        word_size =  new JComboBox<>(word_size_menu);
        word_size.setSelectedIndex(2);
        gridcon.gridx = 0;
        gridcon.gridy = 5;
        gridcon.insets = new Insets(0,300,0,400);
        window.add(word_size, gridcon);

        expect_label = new JLabel("Except value threshold");
        gridcon.gridx = 0;
        gridcon.gridy = 5;
        gridcon.insets =  new Insets(0,400,0,200);
        window.add(expect_label, gridcon);

        expect_value = new JTextField();
        expect_value.setBackground(Color.white);
        gridcon.gridx = 0;
        gridcon.gridy = 5;
        gridcon.insets = new Insets(0,600,0,10);
        window.add(expect_value, gridcon);

        blast_button = new JButton("BLAST!");
        gridcon.gridx = 1;
        gridcon.gridy = 5;
        gridcon.anchor = GridBagConstraints.CENTER;
        gridcon.insets = new Insets(10,10,10,10);
        window.add(blast_button, gridcon);
        blast_button.addActionListener(this);

        orffield = new JTextArea();
        orffield.setFont(orffield.getFont().deriveFont(20f)); // increase font size
        orffield.setBackground(Color.white);
        orffield.setText("");
        orfpanelscroll = new JScrollPane(orffield);
        orfpanelscroll.setPreferredSize(new Dimension(300, 400));
        // makes sure that there is a vertical scroll balk even when empty
        orfpanelscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gridcon.gridx = 0;
        gridcon.gridy = 6;
        gridcon.gridwidth = 2;
        window.add(orfpanelscroll, gridcon);
    }

    public void get_path(){
        int reply;
        file_chooser = new JFileChooser();
        reply = file_chooser.showOpenDialog(this);
        if (reply == 0) {
            file = file_chooser.getSelectedFile();
            namefield.setText(file.getAbsolutePath());
        }
    }


    public void use_orfipy(){
        String path =  namefield.getText();
        if(path.equals("")){
            JOptionPane.showMessageDialog(null, "No document selected");
        }   else {
            String modus = (String) orf_mode.getSelectedItem();
            String max_length = "--max " + orf_max.getText();
            String min_length =  "--min " + orf_min.getText();
            String ignore_case_value;
            String table_num;

            if(modus.equals("Start to stop")){
                modus = "";}

            HashMap<String, String> t_table_map =  new HashMap<String, String>();
            for(int i = 0;i < menu_list.length; i++)
                t_table_map.put(menu_list[i], tables[i]);

            if(t_table.getSelectedItem().equals("Select translation table")){
                table_num = "";
            } else{
                table_num = t_table_map.get(t_table.getSelectedItem());
            }

            if(min_length.equals("--min ")){
                min_length = " ";}

            if(max_length.equals("--max ")){
                max_length = " ";}

            if (ignore_case.isSelected()){
                ignore_case_value = "--ignore-case";
            }   else {
                ignore_case_value = "";
            }
            String orfipy_command = "cd $(dirname " + path + ") && orfipy --pep outputorfipy.fa " + " " + table_num+ " " + min_length + " " + " " + max_length + " " + ignore_case_value + " " + "--outdir results " + path;
            use_command(orfipy_command);
        }
    }


    /**
     * @param command
     */
    public void use_command(String command){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", command);
        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void use_blast(){
        String file = file_name.getText();
        if(file.equals("")){
            file = "output.tsv";}
        else{file = file + ".tsv";}

        String word = String.valueOf(word_size.getSelectedItem());

        String evalue = expect_value.getText();
        if(evalue.equals("")){
            evalue = "0.05";}

        String matrices = (String) matrix.getSelectedItem();
        if(matrices.equals("Select score matrix")){
            matrices = "BLOSUM62";
        }

        String data = (String) database.getSelectedItem();
        if(data.equals("Select database")){
            data = "Swissprot";}
        else {data = data.toLowerCase();}

        for (Entry<String, String> entry: c_file.resultMap_ORF.entrySet()){
            System.out.println(entry);
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("python3", "blaster.py", file, entry.getKey(), entry.getValue(), data, matrices, evalue, word);
                Process process = processBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String lines=null;
                while((lines=reader.readLine())!=null){
                    System.out.println(lines);
                }

                while((lines=readers.readLine())!=null){
                    System.out.println(lines);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(openButton)){
            get_path();
        } else if(e.getSource().equals(orfipy_button)){
            use_orfipy();
            try {
                c_file.read_ORF_File(file.getParent() +"/results/outputorfipy.fa");
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (e.getSource().equals(blast_button)){
            use_blast();
        }


    }
}