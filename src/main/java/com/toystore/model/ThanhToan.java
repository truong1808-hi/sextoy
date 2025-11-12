package com.toystore.model;

import java.math.BigDecimal;

public class ThanhToan {
    private String maThanhToan;
    private String maDonHang;
    private String hinhThuc;
    private BigDecimal soTien;
    private String trangThai;
    private String maGiaoDich;
    private String maTaiKhoan;

    // Getters & Setters
    public String getMaThanhToan() { return maThanhToan; }
    public void setMaThanhToan(String maThanhToan) { this.maThanhToan = maThanhToan; }

    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }

    public String getHinhThuc() { return hinhThuc; }
    public void setHinhThuc(String hinhThuc) { this.hinhThuc = hinhThuc; }

    public BigDecimal getSoTien() { return soTien; }
    public void setSoTien(BigDecimal soTien) { this.soTien = soTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getMaGiaoDich() { return maGiaoDich; }
    public void setMaGiaoDich(String maGiaoDich) { this.maGiaoDich = maGiaoDich; }

    public String getMaTaiKhoan() { return maTaiKhoan; }
    public void setMaTaiKhoan(String maTaiKhoan) { this.maTaiKhoan = maTaiKhoan; }
}
