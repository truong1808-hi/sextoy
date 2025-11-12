package com.toystore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.toystore.model.KhuyenMai;

public class KhuyenMaiDAO {

    // ========================================================
    // 🔍 TÌM KIẾM & HIỂN THỊ DANH SÁCH KHUYẾN MÃI
    // ========================================================
    public List<KhuyenMai> search(String q, String trangThai) {
        List<KhuyenMai> list = new ArrayList<>();

        String sql = """
            SELECT 
                km.MaKhuyenMai, km.MaSanPham,
                sp.Ten AS TenSanPham,
                km.TenKhuyenMai, km.MoTa,
                km.PhanTramGiam, km.GiaGoc, km.GiaSauGiam,
                km.NgayBatDau, km.NgayKetThuc, km.TrangThai
            FROM KhuyenMai km
            JOIN SanPham sp ON km.MaSanPham = sp.MaSanPham
            WHERE (km.MaKhuyenMai LIKE ? OR sp.Ten LIKE ?)
        """;

        if (trangThai != null && !"all".equalsIgnoreCase(trangThai))
            sql += " AND km.TrangThai = ?";
        sql += " ORDER BY km.NgayBatDau DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + (q == null ? "" : q.trim()) + "%");
            ps.setString(2, "%" + (q == null ? "" : q.trim()) + "%");

            if (trangThai != null && !"all".equalsIgnoreCase(trangThai))
                ps.setString(3, trangThai);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi truy vấn danh sách khuyến mãi: " + e.getMessage());
        }
        return list;
    }

    // ========================================================
    // 🔎 TÌM THEO MÃ
    // ========================================================
    public KhuyenMai findById(String id) {
        String sql = """
            SELECT km.*, sp.Ten AS TenSanPham
            FROM KhuyenMai km
            JOIN SanPham sp ON km.MaSanPham = sp.MaSanPham
            WHERE km.MaKhuyenMai = ?
        """;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========================================================
    // ➕ THÊM KHUYẾN MÃI
    // ========================================================
    public void insert(KhuyenMai km) {
        String sql = """
            INSERT INTO KhuyenMai 
            (MaKhuyenMai, MaSanPham, TenKhuyenMai, MoTa, PhanTramGiam,
             GiaGoc, GiaSauGiam, NgayBatDau, NgayKetThuc, TrangThai)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, km.getMaKhuyenMai());
            ps.setString(2, km.getMaSanPham());
            ps.setString(3, km.getTenKhuyenMai());
            ps.setString(4, km.getMoTa());
            ps.setBigDecimal(5, km.getPhanTramGiam());
            ps.setBigDecimal(6, km.getGiaGoc());
            ps.setBigDecimal(7, km.getGiaSauGiam());
            ps.setTimestamp(8, km.getNgayBatDau());
            ps.setTimestamp(9, km.getNgayKetThuc());
            ps.setString(10, km.getTrangThai());

            ps.executeUpdate();
            System.out.println("✅ Đã thêm khuyến mãi: " + km.getMaKhuyenMai());

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========================================================
    // ✏️ CẬP NHẬT KHUYẾN MÃI
    // ========================================================
    public void update(KhuyenMai km) {
        String sql = """
            UPDATE KhuyenMai 
            SET MaSanPham=?, TenKhuyenMai=?, MoTa=?, PhanTramGiam=?,
                GiaGoc=?, GiaSauGiam=?, NgayBatDau=?, NgayKetThuc=?, TrangThai=?
            WHERE MaKhuyenMai=?
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, km.getMaSanPham());
            ps.setString(2, km.getTenKhuyenMai());
            ps.setString(3, km.getMoTa());
            ps.setBigDecimal(4, km.getPhanTramGiam());
            ps.setBigDecimal(5, km.getGiaGoc());
            ps.setBigDecimal(6, km.getGiaSauGiam());
            ps.setTimestamp(7, km.getNgayBatDau());
            ps.setTimestamp(8, km.getNgayKetThuc());
            ps.setString(9, km.getTrangThai());
            ps.setString(10, km.getMaKhuyenMai());

            ps.executeUpdate();
            System.out.println("✏️ Đã cập nhật khuyến mãi: " + km.getMaKhuyenMai());

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========================================================
    // ❌ XÓA
    // ========================================================
    public void delete(String id) {
        String sql = "DELETE FROM KhuyenMai WHERE MaKhuyenMai=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================================================
    // 🔄 CẬP NHẬT TRẠNG THÁI TỰ ĐỘNG
    // ========================================================
    public void capNhatTrangThai() {
        String sql1 = "UPDATE KhuyenMai SET TrangThai='Đang áp dụng' WHERE NOW() BETWEEN NgayBatDau AND NgayKetThuc";
        String sql2 = "UPDATE KhuyenMai SET TrangThai='Chưa bắt đầu' WHERE NOW() < NgayBatDau";
        String sql3 = "UPDATE KhuyenMai SET TrangThai='Đã kết thúc' WHERE NOW() > NgayKetThuc";
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(sql1);
            st.executeUpdate(sql2);
            st.executeUpdate(sql3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========================================================
    // 🧠 MAP THÔNG MINH — tự nhận alias hoặc cột gốc
    // ========================================================
    private KhuyenMai map(ResultSet rs) throws SQLException {
        KhuyenMai km = new KhuyenMai();
        km.setMaKhuyenMai(rs.getString("MaKhuyenMai"));
        km.setMaSanPham(rs.getString("MaSanPham"));

        // Nếu alias TenSanPham tồn tại thì lấy, không thì bỏ qua
        try {
            km.setTenSanPham(rs.getString("TenSanPham"));
        } catch (SQLException ignore) {
            km.setTenSanPham(null);
        }

        km.setTenKhuyenMai(rs.getString("TenKhuyenMai"));
        km.setMoTa(rs.getString("MoTa"));
        km.setPhanTramGiam(rs.getBigDecimal("PhanTramGiam"));
        km.setGiaGoc(rs.getBigDecimal("GiaGoc"));
        km.setGiaSauGiam(rs.getBigDecimal("GiaSauGiam"));
        km.setNgayBatDau(rs.getTimestamp("NgayBatDau"));
        km.setNgayKetThuc(rs.getTimestamp("NgayKetThuc"));
        km.setTrangThai(rs.getString("TrangThai"));
        return km;
    }
}
