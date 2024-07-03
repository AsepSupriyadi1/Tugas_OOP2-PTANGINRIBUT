/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asepsupriyadi_utils;

import asepsupriyadi_model.ModelBarang;
import asepsupriyadi_model.ModelPenjualan;
import asepsupriyadi_model.ModelTmpBarang;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author TUF
 */
public class PDFGenerator {

    public static void generateSalesReport(String filePath, List<ModelPenjualan> penjualanList, String startDate, String endDate) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
        Paragraph title = new Paragraph("Laporan Penjualan - PT. Angin Ribut", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add date range
        String dateRange = "Tanggal Mulai: " + startDate + " s/d " + endDate;
        Paragraph dateRangeParagraph = new Paragraph(dateRange);
        dateRangeParagraph.setAlignment(Element.ALIGN_LEFT);
        dateRangeParagraph.setSpacingBefore(20);
        dateRangeParagraph.setSpacingAfter(3);

        document.add(dateRangeParagraph);

        // Add table
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Table header
        String[] headers = {"No Faktur", "Kd Barang", "Nama Barang", "Harga Satuan", "Qty", "Tgl Pembelian", "Total Pembelian"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        // Table data
        double totalKeseluruhan = 0.0;
        for (ModelPenjualan penjualan : penjualanList) {
            table.addCell(penjualan.getNoFaktur());
            table.addCell(penjualan.getKdBarang());
            table.addCell(penjualan.getNamaBarang());
            table.addCell(String.valueOf(penjualan.getHargaSatuan()));
            table.addCell(String.valueOf(penjualan.getQty()));
            table.addCell(penjualan.getTglPembelian());
            table.addCell(String.valueOf(penjualan.getTotalPembelian()));

            totalKeseluruhan += penjualan.getTotalPembelian();
        }

        // Add total row
        PdfPCell totalCell = new PdfPCell(new Phrase("Total Pendapatan"));
        totalCell.setColspan(6);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalCell);
        table.addCell(String.valueOf(totalKeseluruhan));

        document.add(table);
        document.close();
        writer.close();
    }

    public static void generateStockReport(String filePath, List<ModelBarang> barangList) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
        Paragraph title = new Paragraph("Laporan Stok Barang - PT. Angin Ribut", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add table
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Table header
        String[] headers = {"Kode Barang", "Nama Barang", "Harga Beli", "Harga Jual", "Stok"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        // Table data
        int totalStok = 0;
        for (ModelBarang barang : barangList) {
            table.addCell(barang.getKodeBarang());
            table.addCell(barang.getNamaBarang());
            table.addCell(String.valueOf(barang.getHargaBeli()));
            table.addCell(String.valueOf(barang.getHargaJual()));
            table.addCell(String.valueOf(barang.getJumlahBarang()));

            totalStok += barang.getJumlahBarang();
        }

        // Add total row
        PdfPCell totalCell = new PdfPCell(new Phrase("Total Stok Tersisa"));
        totalCell.setColspan(4);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalCell);
        table.addCell(String.valueOf(totalStok));

        document.add(table);
        document.close();
        writer.close();
    }

    public static void generateInvoice(String filePath, String noFaktur, String tanggal, List<ModelTmpBarang> barangList, double total, double tunai, double kembali) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24);
        Paragraph title = new Paragraph("INVOICE - PT ANGIN RIBUT", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(30);  // 30 margin below title
        document.add(title);

        // Add No FAKTUR and Tanggal
        Paragraph noFakturParagraph = new Paragraph("No FAKTUR: " + noFaktur);
        Paragraph tanggalParagraph = new Paragraph("Tanggal: " + tanggal);
        noFakturParagraph.setAlignment(Element.ALIGN_LEFT);
        tanggalParagraph.setAlignment(Element.ALIGN_LEFT);
        document.add(noFakturParagraph);
        document.add(tanggalParagraph);

        // Add some space before the table
        document.add(new Paragraph("\n"));

        // Add table
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Table header
        String[] headers = {"Nama Barang", "QTY", "HARGA BARANG", "TOTAL"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        // Table data
        for (ModelTmpBarang tmpBarang : barangList) {
            table.addCell(tmpBarang.getNamaBarang());
            table.addCell(String.valueOf(tmpBarang.getQty()));
            table.addCell(String.valueOf(tmpBarang.getHargaBarang()));
            table.addCell(String.valueOf(tmpBarang.getTotal()));
        }

        document.add(table);

        // Add TOTAL, TUNAI, and KEMBALI
        Paragraph totalParagraph = new Paragraph("TOTAL: " + total);
        Paragraph tunaiParagraph = new Paragraph("TUNAI: " + tunai);
        Paragraph kembaliParagraph = new Paragraph("KEMBALI: " + kembali);
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        tunaiParagraph.setAlignment(Element.ALIGN_RIGHT);
        kembaliParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalParagraph);
        document.add(tunaiParagraph);
        document.add(kembaliParagraph);

        document.close();
        writer.close();
    }

//    public static void main(String[] args) throws DocumentException, FileNotFoundException {
//        // Sample data
//        String noFaktur = "B0001";
//        Date tanggal = new Date();
//
//        List<ModelTmpBarang> barangList = new ArrayList<>();
//        barangList.add(new ModelTmpBarang("rog", 2, 1000, 2000));
//        barangList.add(new ModelTmpBarang("rog", 2, 1000, 2000));
//        barangList.add(new ModelTmpBarang("rog", 2, 1000, 2000));
//
//        double total = 4000;
//        double tunai = 5000;
//        double kembali = 1000;
//
//        String filePath = "C:\\Users\\TUF\\Documents\\Kuliah\\Object oriented programming 2\\hasil_generate_laporan\\invoice.pdf";
//        generateInvoice(filePath, noFaktur, tanggal, barangList, total, tunai, kembali);
//    }

}
