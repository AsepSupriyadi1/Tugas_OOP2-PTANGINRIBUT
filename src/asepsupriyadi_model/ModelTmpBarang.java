/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asepsupriyadi_model;

/**
 *
 * @author TUF
 */
public class ModelTmpBarang {

    private String namaBarang;
    private int qty;
    private double hargaBarang;
    private double total;

    public ModelTmpBarang(String namaBarang, int qty, double hargaBarang, double total) {
        this.namaBarang = namaBarang;
        this.qty = qty;
        this.hargaBarang = hargaBarang;
        this.total = total;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
