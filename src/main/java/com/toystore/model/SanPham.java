package com.toystore.model;

import java.math.BigDecimal;

public class SanPham {
    private String maSanPham;
    private String maNCC;
    private String maDanhMuc;
    private String ten;
    private String moTa;
    private BigDecimal gia;
    private int soLuongTon;
    private String hinhAnh;

    // Getters & Setters
    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public String getMaNCC() { return maNCC; }
    public void setMaNCC(String maNCC) { this.maNCC = maNCC; }

    public String getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(String maDanhMuc) { this.maDanhMuc = maDanhMuc; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }

    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }

    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}
