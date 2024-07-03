/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asepsupriyadi_frame;

import asepsupriyadi_database.koneksi_asepsupriyadi;
import asepsupriyadi_model.ModelBarang;
import asepsupriyadi_model.ModelPenjualan;
import asepsupriyadi_model.ModelTmpBarang;
import asepsupriyadi_utils.PDFGenerator;
import asepsupriyadi_utils.SessionManager;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TUF
 */
public class form_penjualan_asepsupriyadi1 extends javax.swing.JFrame {

    public long total;
    public long bayar;
    public long kembali;
    public Statement st;
    Connection cn = koneksi_asepsupriyadi.getKoneksi();
    private DefaultTableModel model;

    /**
     * Creates new form form_pegawai_asepsupriyadi
     */
    public form_penjualan_asepsupriyadi1() {
        initComponents();
        model = new DefaultTableModel();
        table_penjualan.setModel(model);
        model.addColumn("ID");
        model.addColumn("kode barang");
        model.addColumn("nama barang");
        model.addColumn("harga satuan");
        model.addColumn("jumlah beli");
        model.addColumn("harga");
        loadData();
        nofaktur();
        tampilpilih();
    }

    public void FilterHuruf(KeyEvent a) {
        if (Character.isDigit(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "masukan huruf saja!", "peringatan",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void FilterAngka(KeyEvent a) {
        if (Character.isAlphabetic(a.getKeyChar())) {
            a.consume();
            JOptionPane.showMessageDialog(null, "masukan angka saja!", "peringatan",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public final void loadData() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Connection c = koneksi_asepsupriyadi.getKoneksi();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM tb_tmp_jual";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                Object[] o = new Object[6];
                o[0] = r.getString("id_tmp");
                o[1] = r.getString("kd_barang");
                o[2] = r.getString("nama_barang");
                o[3] = r.getString("harga_satuan");
                o[4] = r.getString("jumlah_beli");
                o[5] = r.getString("harga");
                model.addRow(o);
            }

            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("TERJADI KESALAHAN PADA LOAD METHOD");
        }
    }

    private void tampilpilih() {
        try {
            Connection c = koneksi_asepsupriyadi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT nama_barang FROM tb_barang WHERE jumlah_barang !='0'";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                selector_namaBarang.addItem(r.getString("nama_barang"));
            }
            r.last();
            int jumlahData = r.getRow();
            System.out.println("Jumlah data barang : " + jumlahData);
            r.first();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("TERJADI KESALAHAN PADA TAMPIL PILIH METHOD");
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void nofaktur() {
        try {
            Connection c = koneksi_asepsupriyadi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM tb_penjualan ORDER by no_faktur desc";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String noFaktur = r.getString("no_faktur").substring(1);
                String AN = "" + (Integer.parseInt(noFaktur) + 1);
                String Nol = "";
                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }
                txt_noFaktur.setText("F" + Nol + AN);
            } else {
                txt_noFaktur.setText("F0001");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("TERJADI KESALAHAN PADA NO FAKTUR METHOD");
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void generateInvoice() {
        List<ModelTmpBarang> barangList = new ArrayList<>();

        int rowCount = model.getRowCount();
        Double totalBayar = 0.0;
        String noFaktur = txt_noFaktur.getText();

        for (int i = 0; i < rowCount; i++) {
            String namaBarang = model.getValueAt(i, 2).toString();
            Double hargaSatuan = Double.parseDouble(model.getValueAt(i, 3).toString());
            int jumlahBeli = Integer.parseInt(model.getValueAt(i, 4).toString());
            Double totalHarga = Double.parseDouble(model.getValueAt(i, 5).toString());
            totalBayar += totalHarga;
            barangList.add(new ModelTmpBarang(namaBarang, jumlahBeli, hargaSatuan, totalHarga));
        }

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy-HH-mm");

        String formattedDate = dateFormat.format(date).toUpperCase();

        String namaFile = "invoice_pt-angin-ribut_" + formattedDate + ".pdf";
        String filePath = "C:\\Users\\TUF\\Documents\\Kuliah\\Object oriented programming 2\\hasil_generate_laporan\\" + namaFile;

        try {
            if (barangList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tidak ada data tersedia !", "PT Angin Ribut", JOptionPane.INFORMATION_MESSAGE);
            } else {
                PDFGenerator.generateInvoice(filePath, noFaktur, formattedDate, barangList, total, bayar, kembali);
                JOptionPane.showMessageDialog(null, "Laporan berhasil dibuat !", "PT Angin Ribut", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan !", "PT Angin Ribut", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(frame_laporan_penjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btn_kembali = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_noFaktur = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_hitung = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_penjualan = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        label_jumlah = new javax.swing.JLabel();
        btn_total = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txt_kembalian = new javax.swing.JTextField();
        txt_bayar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btn_cetak = new javax.swing.JButton();
        btn_completeTransaction = new javax.swing.JButton();
        selector_namaBarang = new javax.swing.JComboBox<>();
        jumlah = new javax.swing.JTextField();
        txt_kodeBarang = new javax.swing.JTextField();
        txt_hargaSatuan = new javax.swing.JTextField();
        txt_jumlahJual = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        txt_fakturSelesai = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 153));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Penjualan");

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("No. Faktur");

        txt_noFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_noFakturActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama barang");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kode barang");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Harga satuan");

        btn_tambah.setBackground(new java.awt.Color(204, 204, 204));
        btn_tambah.setText("TAMBAH");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahbtn_loginActionPerformed(evt);
            }
        });

        btn_hitung.setBackground(new java.awt.Color(204, 204, 204));
        btn_hitung.setText("HITUNG");
        btn_hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hitungbtn_loginActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Jumlah jual");

        table_penjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "kode barang", "nama barang", "harga satuan", "jumlah beli", "harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table_penjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_penjualanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table_penjualan);

        jPanel3.setBackground(new java.awt.Color(81, 81, 81));

        label_jumlah.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        label_jumlah.setForeground(new java.awt.Color(255, 255, 255));
        label_jumlah.setText("Rp. ");

        btn_total.setText("TOTAL");
        btn_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_totalActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bayar");

        txt_kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kembalianActionPerformed(evt);
            }
        });
        txt_kembalian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_kembalianKeyTyped(evt);
            }
        });

        txt_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bayarActionPerformed(evt);
            }
        });
        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Kembalian");

        btn_cetak.setText("CETAK");
        btn_cetak.setEnabled(false);
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });

        btn_completeTransaction.setText("SELESAI TRANSAKSI");
        btn_completeTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_completeTransactionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)
                            .addComponent(label_jumlah)
                            .addComponent(btn_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_completeTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(103, 103, 103)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(24, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(label_jumlah)
                .addGap(18, 18, 18)
                .addComponent(btn_total, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addComponent(btn_completeTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(144, 144, 144)
                    .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(259, Short.MAX_VALUE)))
        );

        selector_namaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selector_namaBarangActionPerformed(evt);
            }
        });

        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });

        txt_jumlahJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jumlahJualKeyTyped(evt);
            }
        });

        txt_fakturSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fakturSelesaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btn_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txt_total)
                                                .addGap(12, 12, 12)))
                                        .addComponent(btn_hitung, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txt_jumlahJual, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_hargaSatuan, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_noFaktur, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(selector_namaBarang, javax.swing.GroupLayout.Alignment.LEADING, 0, 221, Short.MAX_VALUE)
                                            .addComponent(txt_kodeBarang, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_fakturSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(57, 57, 57)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_jumlahJual, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn_hitung, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txt_noFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(selector_namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txt_kodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_fakturSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_hargaSatuan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(112, 112, 112)))
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(359, 359, 359)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 889, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_hitungbtn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hitungbtn_loginActionPerformed
        // TODO add your handling code here:
        if (txt_noFaktur.getText().equals("")
                || txt_kodeBarang.getText().equals("")
                || selector_namaBarang.getSelectedItem().equals("")
                || txt_hargaSatuan.getText().equals("")
                || txt_jumlahJual.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "PT.Angin Ribut", JOptionPane.INFORMATION_MESSAGE);

        } else {
            String a = txt_jumlahJual.getText();
            int aa = Integer.parseInt(a);
            String b = jumlah.getText();
            int bb = Integer.parseInt(b);
            if (aa > bb) {
                JOptionPane.showMessageDialog(null, "jumlah melebihi stok", "PT. Angin Ribut", JOptionPane.INFORMATION_MESSAGE);
                txt_jumlahJual.setText("");
            } else {
                if (txt_jumlahJual.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "ISI JUMLAH BELI !");
                } else {
                    int jumlah, harga, total;
                    jumlah = Integer.parseInt(txt_jumlahJual.getText().toString());
                    harga = Integer.parseInt(txt_hargaSatuan.getText().toString());
                    total = jumlah * harga;
                    txt_total.setText(Integer.toString(total));
                }
            }
        }
    }//GEN-LAST:event_btn_hitungbtn_loginActionPerformed

    private void btn_tambahbtn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahbtn_loginActionPerformed
        // TODO add your handling code here:
        if (txt_noFaktur.getText().equals("")
                || txt_kodeBarang.getText().equals("")
                || selector_namaBarang.getSelectedItem().equals("")
                || txt_hargaSatuan.getText().equals("")
                || txt_jumlahJual.getText().equals("")
                || txt_total.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "Aplikasi Penjualan", JOptionPane.INFORMATION_MESSAGE);

        } else {
            String kdbarangg = txt_kodeBarang.getText();
            String pilihbarangg = (String) selector_namaBarang.getSelectedItem();
            String hsatuann = txt_hargaSatuan.getText();
            String tjumlahh = txt_jumlahJual.getText();
            String totall = txt_total.getText();

            try {
                Connection c = koneksi_asepsupriyadi.getKoneksi();

                String sql = "INSERT INTO tb_tmp_jual VALUES (?, ?, ?, ?, ?, ?)";

                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, null);
                p.setString(2, kdbarangg);
                p.setString(3, pilihbarangg);
                p.setString(4, hsatuann);
                p.setString(5, tjumlahh);
                p.setString(6, totall);

                p.executeUpdate();
                p.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Terjadi Error pada saat menambah item pembelian");
            } finally {
                nofaktur();
                txt_kodeBarang.setText("");
                selector_namaBarang.setSelectedItem("");
                txt_hargaSatuan.setText("");
                txt_jumlahJual.setText("");
                txt_total.setText("");

                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Aplikasi Penjualan", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            }
        }
    }//GEN-LAST:event_btn_tambahbtn_loginActionPerformed

    private void txt_noFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_noFakturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_noFakturActionPerformed

    private void btn_kembalibtn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembalibtn_loginActionPerformed
        fmenu_asepsupriyadi fb = new fmenu_asepsupriyadi();
        fb.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_kembalibtn_loginActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_cetakActionPerformed

    private void btn_completeTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_completeTransactionActionPerformed
        // TODO add your handling code here:
        if (txt_bayar.getText().equals("") || txt_kembalian.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "Aplikasi Penjualan",
                    JOptionPane.INFORMATION_MESSAGE);

        } else {
            String a = txt_kembalian.getText();
            int ab = Integer.parseInt(String.valueOf(txt_kembalian.getText()));
            if (ab < 0) {
                JOptionPane.showMessageDialog(null, "Uang anda kurang", "Aplikasi Penjualan",
                        JOptionPane.INFORMATION_MESSAGE);
                txt_bayar.setText("");
                txt_kembalian.setText("");
            } else {
                try {
                    Connection c = koneksi_asepsupriyadi.getKoneksi();
                    Statement s = c.createStatement();
                    String sql = "SELECT * FROM tb_tmp_jual";
                    ResultSet r = s.executeQuery(sql);
                    while (r.next()) {
                        long millis = System.currentTimeMillis();
                        java.sql.Date date = new java.sql.Date(millis);
                        System.out.println(date);
                        String tgl = date.toString();
                        String sqla = "INSERT INTO tb_penjualan "
                                + "(no_faktur, kd_barang, nama_barang, hsatuan, jumlah_jual, harga, bayar, kembalian, tgl_penjualan)"
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement p = c.prepareStatement(sqla);
                        p.setString(1, txt_noFaktur.getText());
                        p.setString(2, r.getString("kd_barang"));
                        p.setString(3, r.getString("nama_barang"));
                        p.setString(4, r.getString("harga_satuan"));
                        p.setString(5, r.getString("jumlah_beli"));
                        p.setString(6, r.getString("harga"));
                        p.setString(7, txt_bayar.getText());
                        p.setString(8, txt_kembalian.getText());
                        p.setString(9, tgl);

                        p.executeUpdate();
                        p.close();

                    }
                    r.close();
                    s.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Terjadi Error pada saat menyelesaikan transaksi");
                } finally {
                    try {
                        generateInvoice();
                        String sqla = "TRUNCATE tb_tmp_jual";
                        java.sql.Connection conn = (Connection) koneksi_asepsupriyadi.getKoneksi();
                        java.sql.PreparedStatement pst = conn.prepareStatement(sqla);
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "TRANSAKSI SELESAI", "Aplikasi Penjualan",
                                JOptionPane.INFORMATION_MESSAGE);
                        loadData();
                        txt_fakturSelesai.setText(txt_noFaktur.getText());
                        txt_bayar.setText("");
                        txt_kembalian.setText("");
                        label_jumlah.setText("");
                        nofaktur();
                        btn_cetak.setEnabled(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage());
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_completeTransactionActionPerformed

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahActionPerformed

    private void selector_namaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selector_namaBarangActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (selector_namaBarang.getSelectedItem().equals("pilih barang")) {
            txt_kodeBarang.setText("");
            txt_hargaSatuan.setText("");
        } else {
            try {
                Connection c = koneksi_asepsupriyadi.getKoneksi();
                Statement s = c.createStatement();
                String sql = "SELECT kd_barang, jumlah_barang FROM tb_barang WHERE nama_barang ='"
                        + selector_namaBarang.getSelectedItem() + "'";
                ResultSet r = s.executeQuery(sql);
                while (r.next()) {
                    txt_kodeBarang.setText(r.getString("kd_barang"));
                    jumlah.setText(r.getString("jumlah_barang"));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                Connection c = koneksi_asepsupriyadi.getKoneksi();
                Statement s = c.createStatement();
                String sql = "SELECT harga_jual FROM tb_barang WHERE nama_barang ='"
                        + selector_namaBarang.getSelectedItem() + "'";
                ResultSet r = s.executeQuery(sql);
                while (r.next()) {
                    txt_hargaSatuan.setText(r.getString("harga_jual"));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_selector_namaBarangActionPerformed

    private void btn_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_totalActionPerformed
        // TODO add your handling code here:
        try {
            Connection c = koneksi_asepsupriyadi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT SUM(`harga`) AS total FROM tb_tmp_jual";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                label_jumlah.setText(r.getString("" + "total"));
            }

            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Terjadi Error pada saat menghitung total");
        }

    }//GEN-LAST:event_btn_totalActionPerformed

    private void txt_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bayarActionPerformed

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        // TODO add your handling code here:
        bayar = Integer.parseInt(String.valueOf(txt_bayar.getText()));
        total = Integer.parseInt(String.valueOf(label_jumlah.getText()));
        kembali = bayar - total;

        txt_kembalian.setText(Long.toString(kembali));
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void table_penjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_penjualanMouseClicked
        // TODO add your handling code here:
        int jawaban;
        if ((jawaban = JOptionPane.showConfirmDialog(null, "Yakin batal?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION)) == 0) {
            try {

                int i = table_penjualan.getSelectedRow();
                if (i == -1) {
                    return;
                }
                String id = (String) model.getValueAt(i, 0);

                st = cn.createStatement();
                st.executeUpdate("delete from tb_tmp_jual where id_tmp = '" + id + "'");

                nofaktur();
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_table_penjualanMouseClicked

    private void txt_fakturSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fakturSelesaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fakturSelesaiActionPerformed

    private void txt_kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kembalianActionPerformed

    private void txt_kembalianKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kembalianKeyTyped
        // TODO add your handling code here:
        FilterAngka(evt);
    }//GEN-LAST:event_txt_kembalianKeyTyped

    private void txt_jumlahJualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahJualKeyTyped
        // TODO add your handling code here:
        FilterAngka(evt);
    }//GEN-LAST:event_txt_jumlahJualKeyTyped

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
            java.util.logging.Logger.getLogger(form_penjualan_asepsupriyadi1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_penjualan_asepsupriyadi1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_penjualan_asepsupriyadi1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_penjualan_asepsupriyadi1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (!SessionManager.isLoggedIn()) {
                    form_login_asepsupriyadi fb = new form_login_asepsupriyadi();
                    fb.setVisible(true);
                } else {
                    new form_penjualan_asepsupriyadi1().setVisible(true);
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Gender;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_completeTransaction;
    private javax.swing.JButton btn_hitung;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_total;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jumlah;
    private javax.swing.JLabel label_jumlah;
    private javax.swing.JComboBox<String> selector_namaBarang;
    private javax.swing.JTable table_penjualan;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_fakturSelesai;
    private javax.swing.JTextField txt_hargaSatuan;
    private javax.swing.JTextField txt_jumlahJual;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_kodeBarang;
    private javax.swing.JTextField txt_noFaktur;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
