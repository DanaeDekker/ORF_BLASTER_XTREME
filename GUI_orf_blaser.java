package applicatie1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GUI_orf_blaser extends JFrame implements ActionListener{


    private JButton openButton, orfipy_button, blast_button;
    private JFileChooser fileChooser;
    private JTextField nameField, orf_min, orf_max, file_name, expect_value;
    private JPanel orfpanel, filepanel, orfipypanel;
    private JTextArea orffield;
    private JScrollPane orfpanelscroll;
    private JComboBox t_table, orf_mode, matrix, word_size, database;
    private JLabel orf_min_label, orf_max_label, ignore_case_but, file_name_results, word_size_label, expect_label;
    private JCheckBox ignore_case;
    private List<String> menulist;
  
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
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
           
        nameField = new JTextField(25);
        gridcon.gridx =  0;
        gridcon.gridy  = 0;
        gridcon.anchor = GridBagConstraints.CENTER;
        gridcon.insets = new Insets(10,10,10,10);
        window.add(nameField, gridcon);

        openButton = new JButton("open");
        openButton.setPreferredSize(new Dimension(100,25));
        gridcon.gridx = 1;
        gridcon.gridy = 0;
        gridcon.anchor = GridBagConstraints.CENTER;
        gridcon.insets = new Insets(10,10,10,10);
        window.add(openButton, gridcon);
        openButton.addActionListener(this);

        try {
            menulist = Files.readAllLines(Paths.get("menuoptions.txt"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String[] menu_list = menulist.toArray(new String[0]);

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

    
        String[] orf_menu_list = {"--Partial-3", "--partial-5", "--between-stops"};
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

        String[] word_size_menu = {"2", "3",  "6"};
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


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }
}