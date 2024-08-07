/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asepsupriyadi_frame;

import asepsupriyadi_database.koneksi_asepsupriyadi;
import asepsupriyadi_utils.AES;
import asepsupriyadi_utils.SessionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TUF
 */
public class form_pegawai_asepsupriyadi extends javax.swing.JFrame {

    /**
     * Creates new form form_pegawai_asepsupriyadi
     */
    private DefaultTableModel model;

    public form_pegawai_asepsupriyadi() {
        initComponents();
        model = new DefaultTableModel();
        table_pegawai.setModel(model);
        model.addColumn("username");
        model.addColumn("jenis_kelamin");
        model.addColumn("email");
        model.addColumn("no_telp");
        model.addColumn("agama");
        model.addColumn("alamat");
        model.addColumn("password");
        loadData();
    }

    public final void loadData() {
        btn_tambah.setEnabled(true);
        btn_hapus.setEnabled(false);
        btn_edit.setEnabled(false);
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            Connection c = koneksi_asepsupriyadi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM tb_akun";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                Object[] o = new Object[7];
                o[0] = r.getString("username");
                o[1] = r.getString("jenis_kelamin");
                o[2] = r.getString("email");
                o[3] = r.getString("no_telp");
                o[4] = r.getString("agama");
                o[5] = r.getString("alamat");
                o[6] = r.getString("password");
                model.addRow(o);
            }
            r.close();
            s.close();
            System.out.println("Load data berhasil");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Terjadi kesalahan saat load data !");
            JOptionPane.showMessageDialog(null, "terjadi kesalahan");
        }
    }

    private String getSelectedGender() {
        String selected = null;
        if (btn_radioPria.isSelected()) {
            selected = "Pria";
        } else if (btn_radioWanita.isSelected()) {
            selected = "Wanita";
        }

        return selected;
    }

    private void resetField() {
        txt_username.setText("");
        txt_password.setText("");
        txt_email.setText("");
        txt_noTelp.setText("");

        btn_radioPria.setSelected(false);
        btn_radioWanita.setSelected(false);

        selector_agama.setSelectedItem("");
        textarea_alamat.setText("");
    }

    // Metode untuk mencari indeks dari nilai tertentu dalam JComboBox
    private int findAgamaIndex(String value) {
        for (int i = 0; i < selector_agama.getItemCount(); i++) {
            if (selector_agama.getItemAt(i).equals(value)) {
                return i;
            }
        }
        return -1; // Jika nilai tidak ditemukan
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Gender = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btn_kembali = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_pegawai = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txt_noTelp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        selector_agama = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea_alamat = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        btn_radioPria = new javax.swing.JRadioButton();
        btn_radioWanita = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        txt_email = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 153));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Pengolahan data pegawai");

        btn_kembali.setText("BACK");
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembalibtn_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(756, Short.MAX_VALUE)
                    .addComponent(btn_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(38, 38, 38)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel15)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(35, Short.MAX_VALUE)
                    .addComponent(btn_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16)))
        );

        table_pegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "username", "jenis_kelamin", "email", "no_telp", "agama", "alamat", "password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_pegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_pegawaiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_pegawai);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Username");

        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Password");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("No. Telp");

        txt_noTelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noTelpActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Agama");

        selector_agama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Islam", "Kristem", "Budha", "Hindu", " " }));

        textarea_alamat.setColumns(20);
        textarea_alamat.setRows(5);
        jScrollPane1.setViewportView(textarea_alamat);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Alamat");

        Gender.add(btn_radioPria);
        btn_radioPria.setText("Laki Laki");

        Gender.add(btn_radioWanita);
        btn_radioWanita.setText("Perempuan");
        btn_radioWanita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_radioWanitaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jenis Kelamin");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Email");

        btn_tambah.setBackground(new java.awt.Color(204, 204, 204));
        btn_tambah.setText("TAMBAH");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahbtn_loginActionPerformed(evt);
            }
        });

        btn_edit.setBackground(new java.awt.Color(204, 204, 204));
        btn_edit.setText("UBAH");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editbtn_loginActionPerformed(evt);
            }
        });

        btn_hapus.setBackground(new java.awt.Color(204, 204, 204));
        btn_hapus.setText("HAPUS");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusbtn_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(852, 852, 852)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                        .addComponent(txt_username))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_radioPria)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_radioWanita))
                                    .addComponent(txt_email))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(29, 29, 29)
                                        .addComponent(txt_noTelp))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5))
                                        .addGap(42, 42, 42)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(selector_agama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel4))
                                    .addComponent(txt_noTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selector_agama, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btn_radioPria)
                                .addComponent(btn_radioWanita))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(286, 286, 286))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahbtn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahbtn_loginActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        System.out.println(txt_username.getText());
        System.out.println(txt_password.getText());
        System.out.println(txt_email.getText());
        System.out.println(txt_noTelp.getText());
        System.out.println(textarea_alamat.getText());
        System.out.println(getSelectedGender());
        System.out.println(selector_agama.getSelectedItem().equals(""));

        String reqGender = getSelectedGender();
        String reqUsername = txt_username.getText();
        String reqPassword = txt_password.getText();
        String reqEmail = txt_email.getText();
        String reqTelp = txt_noTelp.getText();
        String reqAgama = (String) selector_agama.getSelectedItem();
        String reqAlamat = textarea_alamat.getText();

        if (reqGender == null
                || reqUsername.equals("") || reqPassword.equals("")
                || reqEmail.equals("") || reqTelp.equals("")
                || reqAgama.equals("") || reqAlamat.equals("")) {
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "elektronik berkah", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                Connection c = koneksi_asepsupriyadi.getKoneksi();
                String sql = "INSERT INTO tb_akun (username, password, jenis_kelamin, email, no_telp, agama, alamat) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, reqUsername);
                p.setString(2, AES.encrypt(reqPassword));
                p.setString(3, reqGender);
                p.setString(4, reqEmail);
                p.setString(5, reqTelp);
                p.setString(6, reqAgama);
                p.setString(7, reqAlamat);
                p.executeUpdate();
                p.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Terjadi kesalahan pada method tambah !");
                JOptionPane.showMessageDialog(null, "terjadi kesalahan");
            } finally {
                loadData();
                resetField();
                JOptionPane
                        .showMessageDialog(null, "Data berhasil tersimpan", "elekronik berkah", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_tambahbtn_loginActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_noTelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noTelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noTelpActionPerformed

    private void btn_radioWanitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_radioWanitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_radioWanitaActionPerformed

    private void btn_kembalibtn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembalibtn_loginActionPerformed
        fmenu_asepsupriyadi fb = new fmenu_asepsupriyadi();
        fb.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_kembalibtn_loginActionPerformed

    private void btn_editbtn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editbtn_loginActionPerformed
        // TODO add your handling code here:
        System.out.println("EDITT DATAAAAAAAAAAAAAAA");

        String reqGender = getSelectedGender();
        String reqUsername = txt_username.getText();
        String reqPassword = txt_password.getText();
        String reqEmail = txt_email.getText();
        String reqTelp = txt_noTelp.getText();
        String reqAgama = (String) selector_agama.getSelectedItem();
        String reqAlamat = textarea_alamat.getText();

        System.out.println(txt_username.getText());
        System.out.println(txt_password.getText());
        System.out.println(txt_email.getText());
        System.out.println(txt_noTelp.getText());
        System.out.println(textarea_alamat.getText());
        System.out.println(getSelectedGender());
        System.out.println(selector_agama.getSelectedItem().equals(""));

        if (reqGender == null
                || reqUsername.equals("") || reqPassword.equals("")
                || reqEmail.equals("") || reqTelp.equals("")
                || reqAgama.equals("") || reqAlamat.equals("")) {
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "elektronik berkah", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int i = table_pegawai.getSelectedRow();
            if (i == -1) {
                return;
            }
            String user = (String) model.getValueAt(i, 0);
            try {
                System.out.println("NYARI KONEKSI");
                Connection c = koneksi_asepsupriyadi.getKoneksi();
                System.out.println("MANGGIL QUERY");
                String sql = "UPDATE tb_akun SET username = '" + reqUsername + "', password='" + AES.encrypt(reqPassword) + "', jenis_kelamin='" + reqGender
                        + "', email='" + reqEmail + "', no_telp='" + reqTelp + "', agama='" + reqAgama
                        + "', alamat='" + reqAlamat + "' WHERE username ='" + txt_username.getText() + "'";
                PreparedStatement p = c.prepareStatement(sql);

                System.out.println(sql);

                System.out.println("BERES KONEKSI");
                p.executeUpdate();
                p.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Terjadi kesalahan pada method edit !");
                JOptionPane.showMessageDialog(null, "terjadi kesalahan");
            } finally {
                loadData();
                resetField();
                btn_tambah.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Data berhasil diubah", "Elektronik Berkah", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_editbtn_loginActionPerformed

    private void btn_hapusbtn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusbtn_loginActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "delete from tb_akun where username='" + txt_username.getText() + "'";
            java.sql.Connection conn = (Connection) koneksi_asepsupriyadi.getKoneksi();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "berhasil di hapus");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Terjadi kesalahan pada method hapus !");
            JOptionPane.showMessageDialog(null, "terjadi kesalahan");
        }
        loadData();
        resetField();
    }//GEN-LAST:event_btn_hapusbtn_loginActionPerformed

    private void table_pegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_pegawaiMouseClicked
        // TODO add your handling code here:
        btn_tambah.setEnabled(false);
        btn_edit.setEnabled(true);
        btn_hapus.setEnabled(true);
        int i = table_pegawai.getSelectedRow();
        if (i == -1) {
            return;
        }
        String username = (String) model.getValueAt(i, 0);
        txt_username.setText(username);

        txt_username.setEnabled(false);

        String password = (String) model.getValueAt(i, 6);
        txt_password.setText(AES.decrypt(password));

        String jenisKelamin = (String) model.getValueAt(i, 1);

        if (jenisKelamin.equals("Pria")) {
            btn_radioPria.setSelected(true);
        } else if (jenisKelamin.equals("Wanita")) {
            btn_radioWanita.setSelected(true);
        }

        String email = (String) model.getValueAt(i, 2);
        txt_email.setText(email);

        String noTelp = (String) model.getValueAt(i, 3);
        txt_noTelp.setText(noTelp);

        String agama = (String) model.getValueAt(i, 4);
        int indexAgama = findAgamaIndex(agama);
        selector_agama.setSelectedIndex(indexAgama);

        String alamat = (String) model.getValueAt(i, 5);
        textarea_alamat.setText(alamat);
    }//GEN-LAST:event_table_pegawaiMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(form_pegawai_asepsupriyadi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_pegawai_asepsupriyadi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_pegawai_asepsupriyadi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_pegawai_asepsupriyadi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (!SessionManager.isLoggedIn()) {
                    form_login_asepsupriyadi fb = new form_login_asepsupriyadi();
                    fb.setVisible(true);
                } else {
                    new form_pegawai_asepsupriyadi().setVisible(true);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Gender;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JRadioButton btn_radioPria;
    private javax.swing.JRadioButton btn_radioWanita;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> selector_agama;
    private javax.swing.JTable table_pegawai;
    private javax.swing.JTextArea textarea_alamat;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_noTelp;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
