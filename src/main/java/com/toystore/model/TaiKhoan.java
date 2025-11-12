package com.toystore.model;

import java.sql.Timestamp;

public class TaiKhoan {
    private String maTaiKhoan;
    private String tenDangNhap; // Sẽ lưu Email
    private String matKhau;
    private String vaiTro;
    private String verificationCode;
    private int isVerified; // Dùng int (0 hoặc 1) cho TINYINT
    private String resetToken;
    private Timestamp resetTokenExpiry;

    // Constructors, Getters và Setters
    
    public TaiKhoan() {
    }

    // Getters
    public String getMaTaiKhoan() { return maTaiKhoan; }
    public String getTenDangNhap() { return tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public String getVaiTro() { return vaiTro; }
    public String getVerificationCode() { return verificationCode; }
    public int getIsVerified() { return isVerified; }
    public String getResetToken() { return resetToken; }
    public Timestamp getResetTokenExpiry() { return resetTokenExpiry; }

    // Setters
    public void setMaTaiKhoan(String maTaiKhoan) { this.maTaiKhoan = maTaiKhoan; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
    public void setIsVerified(int isVerified) { this.isVerified = isVerified; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    public void setResetTokenExpiry(Timestamp resetTokenExpiry) { this.resetTokenExpiry = resetTokenExpiry; }
}