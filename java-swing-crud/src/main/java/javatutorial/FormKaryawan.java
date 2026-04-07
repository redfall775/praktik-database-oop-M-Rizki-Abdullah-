package javatutorial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FormKaryawan extends JFrame {

    private JTextField txtNip, txtNama, txtTempLhr, txtTgl, txtBln, txtThn, txtJabatan;
    private JButton btnInsert, btnUpdate, btnDelete, btnClose;
    private JTable TblKar;
    private DefaultTableModel defTab;

    public FormKaryawan() {
        setTitle("Form Karyawan - CRUD Hibernate Java Swing");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        fillTable(false);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(210, 206, 188));
        background.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(background, BorderLayout.CENTER);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(245, 242, 233));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(178, 172, 147), 2),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        background.add(card, BorderLayout.CENTER);

        // Panel Input
        JPanel panelInput = new JPanel(new GridBagLayout());
        panelInput.setBackground(new Color(245, 242, 233));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelInput.add(new JLabel("NIP"), gbc);
        gbc.gridx = 1; txtNip = new JTextField(18); panelInput.add(txtNip, gbc);
        txtNip.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) fillTable(true);
            }
        });

        gbc.gridx = 0; gbc.gridy = 1; panelInput.add(new JLabel("Nama"), gbc);
        gbc.gridx = 1; txtNama = new JTextField(22); panelInput.add(txtNama, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelInput.add(new JLabel("Temp Lhr"), gbc);
        gbc.gridx = 1; txtTempLhr = new JTextField(22); panelInput.add(txtTempLhr, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelInput.add(new JLabel("Tgl Lhr"), gbc);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.setBackground(new Color(245, 242, 233));
        txtTgl = new JTextField(2); txtBln = new JTextField(2); txtThn = new JTextField(4);
        datePanel.add(txtTgl); datePanel.add(new JLabel("-")); datePanel.add(txtBln);
        datePanel.add(new JLabel("-")); datePanel.add(txtThn);
        gbc.gridx = 1; panelInput.add(datePanel, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelInput.add(new JLabel("Jabatan"), gbc);
        gbc.gridx = 1; txtJabatan = new JTextField(22); panelInput.add(txtJabatan, gbc);

        // Panel Button
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        panelBtn.setBackground(new Color(245, 242, 233));
        btnInsert = new JButton("Insert");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClose = new JButton("Close");

        panelBtn.add(btnInsert);
        panelBtn.add(btnUpdate);
        panelBtn.add(btnDelete);
        panelBtn.add(btnClose);

        // Table
        TblKar = new JTable();
        TblKar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = TblKar.getSelectedRow();
                if (row >= 0) {
                    txtNip.setText(TblKar.getValueAt(row, 0).toString());
                    txtNama.setText(TblKar.getValueAt(row, 1).toString());
                    txtTempLhr.setText(TblKar.getValueAt(row, 2).toString());
                    String tgllhr = parseTanggalDariTabel(TblKar.getValueAt(row, 3).toString());
                    if (tgllhr.length() >= 10) {
                        txtThn.setText(tgllhr.substring(0, 4));
                        txtBln.setText(tgllhr.substring(5, 7));
                        txtTgl.setText(tgllhr.substring(8, 10));
                    }
                    txtJabatan.setText(TblKar.getValueAt(row, 4).toString());
                }
            }
        });
        JScrollPane scroll = new JScrollPane(TblKar);
        scroll.setPreferredSize(new Dimension(0, 220));

        card.add(panelInput);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(panelBtn);
        card.add(Box.createRigidArea(new Dimension(0, 14)));
        card.add(scroll);

        // Event
        btnInsert.addActionListener(e -> insertData());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());
        btnClose.addActionListener(e -> dispose());
    }

    private void fillTable(boolean filter) {
        Object[] colHeader = {"NIP", "Nama", "Temp Lahir", "Tgl Lahir", "Jabatan"};
        defTab = new DefaultTableModel(null, colHeader);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<CKaryawan> list;
            if (filter) {
                list = session.createQuery("from CKaryawan where nip = :nip", CKaryawan.class)
                        .setParameter("nip", txtNip.getText())
                        .list();
            } else {
                list = session.createQuery("from CKaryawan", CKaryawan.class).list();
            }

            for (CKaryawan ck : list) {
                Object[] row = {ck.getNip(), ck.getNama(), ck.getTempatLahir(), 
                                formatTanggalUntukTabel(ck.getTglLahir()), ck.getJabatan()};
                defTab.addRow(row);
            }
            TblKar.setModel(defTab);
        }
    }

    private void insertData() {
        CKaryawan ck = new CKaryawan();
        ck.setNip(txtNip.getText());
        ck.setNama(txtNama.getText());
        ck.setTempatLahir(txtTempLhr.getText());
        ck.setTglLahir(formatTanggalLahir());
        ck.setJabatan(txtJabatan.getText());

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(ck);
            tx.commit();
            JOptionPane.showMessageDialog(this, "Data Tersimpan!");
            fillTable(false);
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            CKaryawan ck = session.get(CKaryawan.class, txtNip.getText());
            if (ck != null) {
                ck.setNama(txtNama.getText());
                ck.setTempatLahir(txtTempLhr.getText());
                ck.setTglLahir(formatTanggalLahir());
                ck.setJabatan(txtJabatan.getText());
                session.merge(ck);
                tx.commit();
                JOptionPane.showMessageDialog(this, "Data Terupdate!");
                fillTable(false);
            }
        }
    }

    private void deleteData() {
        int confirm = JOptionPane.showConfirmDialog(this, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction tx = session.beginTransaction();
                CKaryawan ck = session.get(CKaryawan.class, txtNip.getText());
                if (ck != null) {
                    session.remove(ck);
                    tx.commit();
                    JOptionPane.showMessageDialog(this, "Data Terhapus!");
                    fillTable(false);
                    clearForm();
                }
            }
        }
    }

    private void clearForm() {
        txtNip.setText(""); txtNama.setText(""); txtTempLhr.setText("");
        txtTgl.setText(""); txtBln.setText(""); txtThn.setText(""); txtJabatan.setText("");
    }

    private String formatTanggalLahir() {
        return txtThn.getText() + "-" + txtBln.getText() + "-" + txtTgl.getText();
    }

    private String formatTanggalUntukTabel(String tgl) {
        if (tgl == null || tgl.length() < 10) return tgl;
        return tgl.substring(8, 10) + "-" + tgl.substring(5, 7) + "-" + tgl.substring(0, 4);
    }

    private String parseTanggalDariTabel(String tgl) {
        if (tgl == null || tgl.length() < 10) return tgl;
        return tgl.substring(6, 10) + "-" + tgl.substring(3, 5) + "-" + tgl.substring(0, 2);
    }
}