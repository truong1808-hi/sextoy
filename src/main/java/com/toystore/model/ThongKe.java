package com.toystore.model;

import java.math.BigDecimal;
import java.sql.Date;

public class ThongKe {
    private String maThongKe;
    private Date ngay;
    private int tongDon;
    private int tongSanPhamBan;
    private BigDecimal tongDoanhThuNgay;
    private int tonKho;
    private String spBanChay;
    private BigDecimal tongDoanhThuTuan;
    private BigDecimal tongDoanhThuThang;
    private BigDecimal tongDoanhThuNam;

    // Getter & Setter
    public String getMaThongKe() { return maThongKe; }
    public void setMaThongKe(String maThongKe) { this.maThongKe = maThongKe; }

    public Date getNgay() { return ngay; }
    public void setNgay(Date ngay) { this.ngay = ngay; }

    public int getTongDon() { return tongDon; }
    public void setTongDon(int tongDon) { this.tongDon = tongDon; }

    public int getTongSanPhamBan() { return tongSanPhamBan; }
    public void setTongSanPhamBan(int tongSanPhamBan) { this.tongSanPhamBan = tongSanPhamBan; }

    public BigDecimal getTongDoanhThuNgay() { return tongDoanhThuNgay; }
    public void setTongDoanhThuNgay(BigDecimal tongDoanhThuNgay) { this.tongDoanhThuNgay = tongDoanhThuNgay; }

    public int getTonKho() { return tonKho; }
    public void setTonKho(int tonKho) { this.tonKho = tonKho; }

    public String getSpBanChay() { return spBanChay; }
    public void setSpBanChay(String spBanChay) { this.spBanChay = spBanChay; }

    public BigDecimal getTongDoanhThuTuan() { return tongDoanhThuTuan; }
    public void setTongDoanhThuTuan(BigDecimal tongDoanhThuTuan) { this.tongDoanhThuTuan = tongDoanhThuTuan; }

    public BigDecimal getTongDoanhThuThang() { return tongDoanhThuThang; }
    public void setTongDoanhThuThang(BigDecimal tongDoanhThuThang) { this.tongDoanhThuThang = tongDoanhThuThang; }

    public BigDecimal getTongDoanhThuNam() { return tongDoanhThuNam; }
    public void setTongDoanhThuNam(BigDecimal tongDoanhThuNam) { this.tongDoanhThuNam = tongDoanhThuNam; }
}
