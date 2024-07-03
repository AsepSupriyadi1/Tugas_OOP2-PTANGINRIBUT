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
public class ModelBarang {
    
    private String kodeBarang;
    private String namaBarang;
    private int jumlahBarang;
    private double hargaBeli;
    private double hargaJual;

    public ModelBarang(String kodeBarang, String namaBarang, int jumlahBarang, double hargaBeli, double hargaJual) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }
    
    
    
}
