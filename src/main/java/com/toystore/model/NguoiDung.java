package com.toystore.model;

public class NguoiDung {
    private String maNguoiDung;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String maTaiKhoan;

    // Constructors, Getters và Setters
    
    public NguoiDung() {
    }

    // Getters
    public String getMaNguoiDung() { return maNguoiDung; }
    public String getHoTen() { return hoTen; }
    public String getEmail() { return email; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getDiaChi() { return diaChi; }
    public String getMaTaiKhoan() { return maTaiKhoan; }

    // Setters
    public void setMaNguoiDung(String maNguoiDung) { this.maNguoiDung = maNguoiDung; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setEmail(String email) { this.email = email; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public void setMaTaiKhoan(String maTaiKhoan) { this.maTaiKhoan = maTaiKhoan; }
}