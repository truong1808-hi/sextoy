package com.toystore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.toystore.model.DanhMuc;
import com.toystore.model.SanPham;

public class DanhMucDAO {

    // 🔹 Lấy tất cả danh mục (hoặc tìm kiếm theo tên / mã)
    public List<DanhMuc> search(String keyword) {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhMuc WHERE MaDanhMuc LIKE ? OR TenDanhMuc LIKE ? ORDER BY MaDanhMuc ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + (keyword == null ? "" : keyword.trim()) + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DanhMuc dm = new DanhMuc();
                dm.setMaDanhMuc(rs.getString("MaDanhMuc"));
                dm.setTenDanhMuc(rs.getString("TenDanhMuc"));
                dm.setMoTa(rs.getString("MoTa"));
                list.add(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔹 Tìm danh mục theo mã
    public DanhMuc findById(String id) {
        DanhMuc dm = null;
        String sql = "SELECT * FROM DanhMuc WHERE MaDanhMuc=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dm = new DanhMuc();
                dm.setMaDanhMuc(rs.getString("MaDanhMuc"));
                dm.setTenDanhMuc(rs.getString("TenDanhMuc"));
                dm.setMoTa(rs.getString("MoTa"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dm;
    }

    // 🔹 Lấy sản phẩm theo mã danh mục
    public List<SanPham> getProductsByDanhMuc(String maDanhMuc) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT MaSanPham, MaNCC, MaDanhMuc, Ten, MoTa, Gia, SoLuongTon, HinhAnh "
                   + "FROM SanPham WHERE MaDanhMuc = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maDanhMuc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getString("MaSanPham"));
                sp.setMaNCC(rs.getString("MaNCC"));
                sp.setMaDanhMuc(rs.getString("MaDanhMuc"));
                sp.setTen(rs.getString("Ten"));
                sp.setMoTa(rs.getString("MoTa"));
                sp.setGia(rs.getBigDecimal("Gia"));
                sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                sp.setHinhAnh(rs.getString("HinhAnh"));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    // 🔹 Thêm danh mục
    public boolean insert(DanhMuc dm) {
        String sql = "INSERT INTO DanhMuc (MaDanhMuc, TenDanhMuc, MoTa) VALUES (?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dm.getMaDanhMuc());
            ps.setString(2, dm.getTenDanhMuc());
            ps.setString(3, dm.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Cập nhật danh mục
    public boolean update(DanhMuc dm) {
        String sql = "UPDATE DanhMuc SET TenDanhMuc=?, MoTa=? WHERE MaDanhMuc=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dm.getTenDanhMuc());
            ps.setString(2, dm.getMoTa());
            ps.setString(3, dm.getMaDanhMuc());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Xóa danh mục
    public boolean delete(String id) {
        String sql = "DELETE FROM DanhMuc WHERE MaDanhMuc=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // 🟢 Giữ lại cho các servlet cũ
public List<DanhMuc> getAll() {
    List<DanhMuc> list = new ArrayList<>();
    String sql = "SELECT * FROM DanhMuc ORDER BY MaDanhMuc ASC";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            DanhMuc dm = new DanhMuc();
            dm.setMaDanhMuc(rs.getString("MaDanhMuc"));
            dm.setTenDanhMuc(rs.getString("TenDanhMuc"));
            dm.setMoTa(rs.getString("MoTa"));
            list.add(dm);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

// 🟢 Dành cho trang Home hoặc AdminServlet (header)
public List<DanhMuc> getHeaderCategories() {
    List<DanhMuc> list = new ArrayList<>();
    String sql = "SELECT * FROM DanhMuc ORDER BY TenDanhMuc ASC";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            DanhMuc dm = new DanhMuc();
            dm.setMaDanhMuc(rs.getString("MaDanhMuc"));
            dm.setTenDanhMuc(rs.getString("TenDanhMuc"));
            dm.setMoTa(rs.getString("MoTa"));
            list.add(dm);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
