

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import static javax.swing.BoxLayout.X_AXIS;

public class Main {

    public static void main(String[] args) {
        JFrame j = new JFrame();
        Main m = new Main();
        String pathDefault = args.length>0? args[0]:"";

        JTextField waluta = new JTextField(10);
        waluta.setToolTipText("Podaj ilość waluty");

        JTextField kurs = new JTextField(10);

        JTextField result = new JTextField(10);
        result.setEditable(false);

        JButton jButton = new JButton("Policzyć");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valWal = waluta.getText();
                String valKurs = kurs.getText();
                if (valWal == null || valKurs == null) {
                    System.out.println("nic nie robimy");
                    return;
                }
                Integer iWaluta = 0;
                Double iKurs = 0d;
                try {
                    iWaluta = Integer.parseInt(valWal);
                    iKurs = Double.parseDouble(valKurs);
                } catch (NumberFormatException e1) {
                    iKurs = 0d;
                    System.out.println("Mamy błąd w podaniu waluty lub kursa");
                }
                 DecimalFormat decimalFormat = new DecimalFormat();
                decimalFormat.setMaximumFractionDigits(2);
                result.setText("Suma: "+ decimalFormat.format(iWaluta*iKurs));
            }
        });

        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        j.setBounds(100, 200, 400, 400);
        j.setTitle("Kantor");
        j.setLayout(new BoxLayout(j.getContentPane(), X_AXIS));

        JComboBox jCombo = m.getComboBox(kurs, null);
        jCombo.getModel()

        JButton plik = new JButton("Waluta");
        plik.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                FileFilter filterr = new FileNameExtensionFilter("Plik txt","txt");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setFileFilter(filterr);
                fileChooser.setCurrentDirectory(new File(pathDefault));
                JFrame frame = new JFrame();
                frame.setSize(200, 300);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    System.out.println();
                    List<Waluta> walutaList = m.getDataFromFile(fileChooser.getSelectedFile().getAbsoluteFile().getAbsolutePath());
                    ComboBoxModel model = new DefaultComboBoxModel(walutaList.toArray(new Waluta[walutaList.size()]));
                    jCombo.setModel(model);
                }

            }
        });
        j.add(plik);

        jCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jCombo.getSelectedIndex();
                result.setText("");
                Waluta w = (Waluta) jCombo.getModel().getElementAt(i);
                kurs.setText(String.valueOf(w.getKurs()));
            }
        });
        j.add(jCombo);
        j.add(waluta);
        j.add(kurs);
        j.add(jButton);
        result.setText("Suma: ");
        j.add(result);

        j.setVisible(true);
        j.pack();
    }

    private JComboBox<Waluta> getComboBox(JTextField field, String fileName) {
        List<Waluta> walutaList = getDataFromFile(fileName);
        if (!walutaList.isEmpty()) {
            field.setText(String.valueOf(walutaList.get(0).getKurs()));
            return new JComboBox<Waluta>(walutaList.toArray(new Waluta[walutaList.size()]));
        }
        return new JComboBox<Waluta>();
    }

    public List<Waluta> getDataFromFile(String fileName) {
        BufferedReader reader;
        List<Waluta> walutaList = new ArrayList();
        if (fileName == null) {
            return walutaList;
        }
        try {
            reader = new BufferedReader(new FileReader(fileName));
//                    "C:\\Users\\nauczyciel\\Documents\\ProjektyJava\\desktop\\src\\plik.txt"
            String line = reader.readLine();
            while (line != null) {
                walutaList.add(new Waluta(line.substring(0, line.indexOf("|")), Double.parseDouble(line.substring(line.indexOf("|")+1))));
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Plik nie znaleziono w systemie : "+e.getMessage());
            System.exit(0);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return walutaList;
    }
}
