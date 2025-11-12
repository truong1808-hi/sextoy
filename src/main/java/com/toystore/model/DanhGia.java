package com.toystore.model;

import java.sql.Timestamp;

public class DanhGia {
    private String maDanhGia;
    private String maSanPham;
    private String maNguoiDung;
    private int diemDanhGia;
    private String noiDungDanhGia;
    private Timestamp ngayDanhGia;
    private String maTaiKhoan;

    // Getters & Setters
    public String getMaDanhGia() { return maDanhGia; }
    public void setMaDanhGia(String maDanhGia) { this.maDanhGia = maDanhGia; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public String getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(String maNguoiDung) { this.maNguoiDung = maNguoiDung; }

    public int getDiemDanhGia() { return diemDanhGia; }
    public void setDiemDanhGia(int diemDanhGia) { this.diemDanhGia = diemDanhGia; }

    public String getNoiDungDanhGia() { return noiDungDanhGia; }
    public void setNoiDungDanhGia(String noiDungDanhGia) { this.noiDungDanhGia = noiDungDanhGia; }

    public Timestamp getNgayDanhGia() { return ngayDanhGia; }
    public void setNgayDanhGia(Timestamp ngayDanhGia) { this.ngayDanhGia = ngayDanhGia; }

    public String getMaTaiKhoan() { return maTaiKhoan; }
    public void setMaTaiKhoan(String maTaiKhoan) { this.maTaiKhoan = maTaiKhoan; }
}
