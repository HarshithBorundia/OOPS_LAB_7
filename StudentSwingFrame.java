import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StudentSwingFrame extends JFrame {
    int selectedCollection = 0;
    int row;
    String selectedKey = "";
    JTable jt;
    DefaultTableModel model;
    ArrayList<Student> myList;
    HashMap<String, Student> myMap;
    TreeMap<String, Student> treeMap; // Use TreeMap instead of Map for ordering
    PriorityQueue<Student> pq;
    LinkedList<Student> ll;

    // Declare instance variables for text fields and combo box
    JTextField idTF;
    JTextField nameTF;
    JTextField emailTF;
    JComboBox<Student.Major> majorBox;
    JTextField keyTF;

    public StudentSwingFrame() {
        this.setTitle("");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        idTF = new JTextField();
        idTF.setEnabled(false);
        nameTF = new JTextField();
        nameTF.setEnabled(false);
        emailTF = new JTextField();
        emailTF.setEnabled(false);
        majorBox = new JComboBox<>(Student.Major.values());
        majorBox.addItem(Student.Major.CS); // Default selection
        majorBox.setEnabled(false);
        keyTF = new JTextField();
        keyTF.setEnabled(false);

        JButton addBtn = new JButton("add");
        addBtn.setEnabled(false);
        JButton editBtn = new JButton("edit");
        editBtn.setEnabled(false);
        JButton delBtn = new JButton("del");
        delBtn.setEnabled(false);

        JComboBox<String> sel = new JComboBox<>();
        sel.addItem("Select a collection");
        sel.addItem("ArrayList");
        sel.addItem("HashMap");
        sel.addItem("TreeMap");
        sel.addItem("PriorityQueue");
        sel.addItem("LinkedList");
        sel.setBounds(30, 30, 240, 30);
        this.add(sel);
        sel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                selectedCollection = sel.getSelectedIndex();
            }
        });

        JButton freezeOption = new JButton("Freeze Collection");
        freezeOption.setBounds(30, 80, 240, 30);
        this.add(freezeOption);

        freezeOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                switch (selectedCollection) {
                    case 1:
                        myList = new ArrayList<>();
                        break;
                    case 2:
                        myMap = new HashMap<>();
                        break;
                    case 3:
                        treeMap = new TreeMap<>();
                        break;
                    case 4:
                        pq = new PriorityQueue<>();
                        break;
                    case 5:
                        ll = new LinkedList<>();
                        break;
                }
                sel.setEnabled(false);
                idTF.setEnabled(true);
                nameTF.setEnabled(true);
                emailTF.setEnabled(true);
                majorBox.setEnabled(true);
                if ((selectedCollection == 2) || (selectedCollection == 3)) {
                    keyTF.setEnabled(true);
                }
                addBtn.setEnabled(true);
                editBtn.setEnabled(true);
                delBtn.setEnabled(true);
            }
        });

        JLabel keyLbl = new JLabel("Key: ");
        JLabel idLbl = new JLabel("Id: ");
        JLabel nameLbl = new JLabel("Name: ");
        JLabel emailLbl = new JLabel("Email: ");
        JLabel majorLbl = new JLabel("Major: ");
        keyLbl.setBounds(30, 130, 50, 30);
        keyTF.setBounds(90, 130, 180, 30);
        idLbl.setBounds(30, 180, 50, 30);
        idTF.setBounds(90, 180, 180, 30);
        nameLbl.setBounds(30, 230, 50, 30);
        nameTF.setBounds(90, 230, 180, 30);
        emailLbl.setBounds(30, 280, 50, 30);
        emailTF.setBounds(90, 280, 180, 30);
        majorLbl.setBounds(30, 330, 50, 30);
        majorBox.setBounds(90, 330, 180, 30);
        addBtn.setBounds(30, 380, 74, 30);
        editBtn.setBounds(113, 380, 74, 30);
        delBtn.setBounds(196, 380, 74, 30);

        this.add(keyLbl);
        this.add(keyTF);
        this.add(idLbl);
        this.add(idTF);
        this.add(nameLbl);
        this.add(nameTF);
        this.add(emailLbl);
        this.add(emailTF);
        this.add(majorLbl);
        this.add(majorBox);
        this.add(addBtn);
        this.add(editBtn);
        this.add(delBtn);

        model = new DefaultTableModel();
        model.addColumn("Key");
        model.addColumn("Id");
        model.addColumn("Name");
        model.addColumn("Major");
        model.addColumn("Email");
        jt = new JTable(model);

        jt.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                row = jt.rowAtPoint(evt.getPoint());

                if (row >= 0) {
                    selectedKey = (String) jt.getValueAt(row, 0);
                    Student student = findStudent(selectedKey);
                    if (student != null) {
                        idTF.setText(student.iD);
                        nameTF.setText(student.name);
                        majorBox.setSelectedItem(student.major);
                        emailTF.setText(student.email);
                    }
                }
            }
        });

        jt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                row = jt.getSelectedRow();
                if (row >= 0) {
                    selectedKey = (String) jt.getValueAt(row, 0);
                    Student student = findStudent(selectedKey);
                    if (student != null) {
                        idTF.setText(student.iD);
                        nameTF.setText(student.name);
                        majorBox.setSelectedItem(student.major);
                        emailTF.setText(student.email);
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Student student = new Student(idTF.getText(), nameTF.getText(), (Student.Major) majorBox.getSelectedItem(), emailTF.getText());
                
                // Check if email starts with 'f' or 'h' before adding
                if (student.email.toLowerCase().startsWith("f") || student.email.toLowerCase().startsWith("h")) {
                    if (selectedCollection == 1) {
                        myList.add(student);
                        model.addRow(new Object[]{myList.size() - 1 + "", student.iD, student.name, student.major, student.email});
                    } else if (selectedCollection == 2) {
                        myMap.put(keyTF.getText(), student);
                        updateTable(myMap);
                    } else if (selectedCollection == 3) {
                        treeMap.put(keyTF.getText(), student);
                        updateTable(treeMap);
                    } else if (selectedCollection == 4) {
                        pq.add(student);
                        updateTable(pq);
                    } else if (selectedCollection == 5) {
                        ll.add(student);
                        updateTable(ll);
                    }
                }
            }
        });

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Student student = new Student(idTF.getText(), nameTF.getText(), (Student.Major) majorBox.getSelectedItem(), emailTF.getText());
                if (selectedCollection == 1) {
                    myList.set(row, student);
                    updateRow(student);
                } else if (selectedCollection == 2) {
                    myMap.put(keyTF.getText(), student);
                    updateTable(myMap);
                } else if (selectedCollection == 3) {
                    treeMap.put(keyTF.getText(), student);
                    updateTable(treeMap);
                } else if (selectedCollection == 4) {
                    // PriorityQueue does not support direct modification, hence need to remove and re-add
                    pq.removeIf(s -> s.iD.equals(idTF.getText()));
                    pq.add(student);
                    updateTable(pq);
                } else if (selectedCollection == 5) {
                    ll.set(row, student);
                    updateRow(student);
                }
            }
        });

        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (selectedCollection == 1) {
                    myList.remove(row);
                    model.removeRow(row);
                } else if (selectedCollection == 2) {
                    myMap.remove(keyTF.getText());
                    updateTable(myMap);
                } else if (selectedCollection == 3) {
                    treeMap.remove(keyTF.getText());
                    updateTable(treeMap);
                } else if (selectedCollection == 4) {
                    pq.removeIf(s -> s.iD.equals(idTF.getText()));
                    updateTable(pq);
                } else if (selectedCollection == 5) {
                    ll.remove(row);
                    model.removeRow(row);
                }
                clearFields();
            }
        });

        jt.setBounds(20, 20, 300, 200);
        JScrollPane js = new JScrollPane(jt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setBounds(300, 30, 340, 370);
        this.add(js);

        this.setBounds(40, 40, 660, 440);
        this.setVisible(true);
    }

    private void updateRow(Student student) {
        model.setValueAt(student.iD, row, 1);
        model.setValueAt(student.name, row, 2);
        model.setValueAt(student.major, row, 3);
        model.setValueAt(student.email, row, 4);
    }

    private void updateTable(HashMap<String, Student> map) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        for (String key : map.keySet()) {
            Student student = map.get(key);
            model.addRow(new Object[]{key, student.iD, student.name, student.major, student.email});
        }
    }

    private void updateTable(TreeMap<String, Student> map) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        for (String key : map.keySet()) {
            Student student = map.get(key);
            model.addRow(new Object[]{key, student.iD, student.name, student.major, student.email});
        }
    }

    private void updateTable(PriorityQueue<Student> pq) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        int index = 0;
        for (Student student : pq) {
            model.addRow(new Object[]{index + "", student.iD, student.name, student.major, student.email});
            index++;
        }
    }

    private void updateTable(LinkedList<Student> ll) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        int index = 0;
        for (Student student : ll) {
            model.addRow(new Object[]{index + "", student.iD, student.name, student.major, student.email});
            index++;
        }
    }

    private Student findStudent(String key) {
        if (selectedCollection == 2 && myMap.containsKey(key)) {
            return myMap.get(key);
        } else if (selectedCollection == 3 && treeMap.containsKey(key)) {
            return treeMap.get(key);
        }
        return null;
    }

    private void clearFields() {
        keyTF.setText("");
        idTF.setText("");
        nameTF.setText("");
        emailTF.setText("");
        majorBox.setSelectedIndex(0); // Reset to default selection
    }

}
