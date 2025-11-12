package com.toystore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DonHang {
    private String maDonHang;
    private String maNguoiDung;
    private String maSanPham;
    private String tenSanPham;
    private int soLuong;
    private BigDecimal gia;
    private BigDecimal tongTien;
    private String trangThai;
    private String diaChi;
    private String soDienThoai;
    private Timestamp ngayDat;
    private String maTaiKhoan;

    // Dành cho hiển thị thông tin người dùng trong admin
    private String hoTenNguoiDung;

    // ===== Getter & Setter =====
    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Timestamp getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Timestamp ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getHoTenNguoiDung() {
        return hoTenNguoiDung;
    }

    public void setHoTenNguoiDung(String hoTenNguoiDung) {
        this.hoTenNguoiDung = hoTenNguoiDung;
    }
}
