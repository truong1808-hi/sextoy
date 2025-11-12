package com.toystore.model;

import java.util.List;

public class DanhMuc {
    private String maDanhMuc;
    private String tenDanhMuc;
    private String moTa;

    // 🔹 Danh sách ảnh demo cho danh mục (không lưu DB)
    private List<HinhAnh> hinhAnhDemo;

    // ✅ Getters & Setters
    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public List<HinhAnh> getHinhAnhDemo() {
        return hinhAnhDemo;
    }

    public void setHinhAnhDemo(List<HinhAnh> hinhAnhDemo) {
        this.hinhAnhDemo = hinhAnhDemo;
    }
}
