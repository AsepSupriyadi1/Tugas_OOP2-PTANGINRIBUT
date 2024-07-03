/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asepsupriyadi_model;

import java.util.Date;

/**
 *
 * @author TUF
 */
public class ModelPenjualan {

    private String noFaktur;
    private String kdBarang;
    private String namaBarang;
    private double hargaSatuan;
    private int qty;
    private double totalPembelian;
    private String tglPembelian;

    // Constructor, getters, and setters
    public ModelPenjualan(String noFaktur, String kdBarang, String namaBarang, double hargaSatuan, int qty, double totalPembelian, String tglPembelian) {
        this.noFaktur = noFaktur;
        this.kdBarang = kdBarang;
        this.namaBarang = namaBarang;
        this.hargaSatuan = hargaSatuan;
        this.qty = qty;
        this.totalPembelian = totalPembelian;
        this.tglPembelian = tglPembelian;
    }

    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
    }

    public String getKdBarang() {
        return kdBarang;
    }

    public void setKdBarang(String kdBarang) {
        this.kdBarang = kdBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalPembelian() {
        return totalPembelian;
    }

    public void setTotalPembelian(double totalPembelian) {
        this.totalPembelian = totalPembelian;
    }

    public String getTglPembelian() {
        return tglPembelian;
    }

    public void setTglPembelian(String tglPembelian) {
        this.tglPembelian = tglPembelian;
    }


}
