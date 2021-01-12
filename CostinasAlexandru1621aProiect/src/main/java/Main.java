import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static JFrame frame;
    private static JLabel modelLabel;
    private static JTextField tModel;
    private static JLabel combustibilLabel;
    private static JTextField tCombustibil;
    private static JLabel anLabel;
    private static JTextField tAn;
    private static JLabel numarLabel;
    private static JTextField tNumar;
    private static JButton save;
    private static JButton update;
    private static JButton modify;
    private static JButton delete;
    private static JList jList;
    private static DefaultListModel listModel = new DefaultListModel();

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(700, 500));
        JPanel container = new JPanel();
        frame.add(container);
        JToolBar toolBar = new JToolBar();

        toolBarComponents(toolBar);
        addNewObjects(container);
        showAllObjects(container);

        frame.add(toolBar, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
    }

    private static void toolBarComponents(JToolBar toolBar) {
        JButton addNewObject = new JButton("Add new object");
        addNewObject.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                jList.setVisible(false);

                modify.setVisible(false);
                delete.setVisible(false);

                setVisibilityForAddNewObject(true);
                System.out.println("mouse clicked");
            }
        });
        toolBar.add(addNewObject);

        JButton listAllObjects = new JButton("List objects");
        listAllObjects.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                jList.setVisible(true);

                modify.setVisible(true);
                delete.setVisible(true);
                update.setVisible(false);
                setVisibilityForAddNewObject(false);
                System.out.println("mouse clicked");
            }
        });

        toolBar.add(listAllObjects);

        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Masina> masini = new ArrayList<>();
                for (int i = 0; i < listModel.size(); i++)
                    masini.add((Masina) listModel.get(i));
                FileUtility.writeToFile(masini);
                System.exit(0);
            }
        });
        toolBar.add(exit);
    }

    private static void showAllObjects(JPanel container) {
        jList = new JList(listModel);
        List<Masina> masini = FileUtility.readFromFile();
        for (Masina masina : masini)
            listModel.addElement(masina);
        container.add(jList);
        jList.setCellRenderer(createListRenderer());
        jList.setVisible(false);

        modify = new JButton("Modifica");
        container.add(modify);
        modify.setVisible(false);
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Masina masina = (Masina) listModel.get(jList.getSelectedIndex());
                update.setVisible(true);
                setVisibilityForAddNewObject(true);
                jList.setVisible(false);
                modify.setVisible(false);
                delete.setVisible(false);
                save.setVisible(false);
                tModel.setText(masina.getModel());
                tCombustibil.setText(masina.getCombustibil());
                tNumar.setText(masina.getNumar());
                tAn.setText(masina.getAn() + "");
            }
        });
        delete = new JButton("Sterge");
        container.add(delete);
        delete.setVisible(false);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jList.getSelectedIndex() != -1)
                    listModel.remove(jList.getSelectedIndex());
            }
        });
    }

    private static void addNewObjects(JPanel container) {

        modelLabel = new JLabel("Model masina:");
        container.add(modelLabel);

        tModel = new JTextField(20);
        container.add(tModel);

        combustibilLabel = new JLabel("Tip carburant:");
        container.add(combustibilLabel);

        tCombustibil = new JTextField(20);
        container.add(tCombustibil);

        numarLabel = new JLabel("Nr. inmatriculare:");
        container.add(numarLabel);

        tNumar = new JTextField(20);
        container.add(tNumar);

        anLabel = new JLabel("An fabricatie:");
        container.add(anLabel);

        tAn = new JTextField(20);
        container.add(tAn);

        save = new JButton("Save");
        container.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String model = tModel.getText();
                String combustibil = tCombustibil.getText();
                String numar = tNumar.getText();
                int an = Integer.parseInt(tAn.getText());
                Masina masina = new Masina(model, combustibil, numar, an);
                listModel.addElement(masina);
                tModel.setText("");
                tCombustibil.setText("");
                tNumar.setText("");
                tAn.setText("");
                JOptionPane.showMessageDialog(null, "O noua masina a fost inregistrata");
            }
        });

        update = new JButton("Actualizeaza");
        container.add(update);
        update.setVisible(false);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String model = tModel.getText();
                String combustibil = tCombustibil.getText();
                String numar = tNumar.getText();
                int an = Integer.parseInt(tAn.getText());
                Masina masina = new Masina(model, combustibil, numar, an);
                listModel.set(jList.getSelectedIndex(), masina);
                tModel.setText("");
                tCombustibil.setText("");
                tNumar.setText("");
                tAn.setText("");
                JOptionPane.showMessageDialog(null, "Masina a fost modificata cu succes");

            }
        });

    }

    private static void setVisibilityForAddNewObject(boolean b) {
        modelLabel.setVisible(b);
        tModel.setVisible(b);
        combustibilLabel.setVisible(b);
        tCombustibil.setVisible(b);
        numarLabel.setVisible(b);
        tNumar.setVisible(b);
        anLabel.setVisible(b);
        tAn.setVisible(b);
        save.setVisible(b);
    }

    private static ListCellRenderer<? super Masina> createListRenderer() {
        return new DefaultListCellRenderer() {
            private Color background = new Color(0, 100, 255, 15);
            private Color defaultBackground = (Color) UIManager.get("List.background");

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    Masina masina = (Masina) value;
                    label.setText(masina.getModel() + "/" + masina.getCombustibil() + "/" + masina.getNumar() + "/" + masina.getAn());
                    if (!isSelected) {
                        label.setBackground(index % 2 == 8 ? background : defaultBackground);
                    }
                }
                return c;
            }
        };
    }
}

