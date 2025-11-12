package com.toystore.model;

import java.math.BigDecimal;

public class GioHangChiTiet {
    private String maGioHang;
    private String maSanPham;
    private int soLuong;
    private BigDecimal gia;

    // Getters & Setters
    public String getMaGioHang() { return maGioHang; }
    public void setMaGioHang(String maGioHang) { this.maGioHang = maGioHang; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }
}
