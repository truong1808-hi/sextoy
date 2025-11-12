package com.toystore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toystore.model.ThanhToan;

public class ThanhToanDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // ✅ Lấy tất cả
    public List<ThanhToan> getAll() {
        List<ThanhToan> list = new ArrayList<>();
        String sql = "SELECT * FROM ThanhToan ORDER BY MaThanhToan ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ✅ Tìm kiếm + lọc theo trạng thái + hình thức
    public List<ThanhToan> search(String keyword, String trangThai, String hinhThuc) {
        List<ThanhToan> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ThanhToan WHERE 1=1");

        if (keyword != null && !keyword.isEmpty())
            sql.append(" AND (MaThanhToan LIKE ? OR MaDonHang LIKE ? OR MaGiaoDich LIKE ?)");

        if (trangThai != null && !"Tất cả".equals(trangThai))
            sql.append(" AND TrangThai=?");

        if (hinhThuc != null && !"Tất cả".equals(hinhThuc))
            sql.append(" AND HinhThuc=?");

        sql.append(" ORDER BY MaThanhToan ASC");

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(idx++, "%" + keyword + "%");
                ps.setString(idx++, "%" + keyword + "%");
                ps.setString(idx++, "%" + keyword + "%");
            }
            if (trangThai != null && !"Tất cả".equals(trangThai))
                ps.setString(idx++, trangThai);
            if (hinhThuc != null && !"Tất cả".equals(hinhThuc))
                ps.setString(idx++, hinhThuc);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    // ✅ Lấy chi tiết thanh toán + thông tin sản phẩm
    public Map<String, Object> findWithDonHang(String maThanhToan) {
        String sql = """
            SELECT 
                tt.*, 
                dh.TenSanPham, dh.SoLuong, dh.Gia, dh.TongTien, dh.TrangThai AS TrangThaiDH
            FROM ThanhToan tt
            JOIN DonHang dh ON tt.MaDonHang = dh.MaDonHang
            WHERE tt.MaThanhToan = ?
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maThanhToan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("MaThanhToan", rs.getString("MaThanhToan"));
                data.put("MaDonHang", rs.getString("MaDonHang"));
                data.put("HinhThuc", rs.getString("HinhThuc"));
                data.put("SoTien", rs.getBigDecimal("SoTien"));
                data.put("TrangThai", rs.getString("TrangThai"));
                data.put("MaGiaoDich", rs.getString("MaGiaoDich"));
                data.put("MaTaiKhoan", rs.getString("MaTaiKhoan"));
                // sản phẩm
                data.put("TenSanPham", rs.getString("TenSanPham"));
                data.put("SoLuong", rs.getInt("SoLuong"));
                data.put("Gia", rs.getBigDecimal("Gia"));
                data.put("TongTien", rs.getBigDecimal("TongTien"));
                data.put("TrangThaiDH", rs.getString("TrangThaiDH"));
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(ThanhToan tt) {
    String sql = """
        UPDATE ThanhToan
        SET MaDonHang=?, HinhThuc=?, SoTien=?, TrangThai=?, MaGiaoDich=?, MaTaiKhoan=?
        WHERE MaThanhToan=?
    """;
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, tt.getMaDonHang());
        ps.setString(2, tt.getHinhThuc());
        ps.setBigDecimal(3, tt.getSoTien());
        ps.setString(4, tt.getTrangThai());
        ps.setString(5, tt.getMaGiaoDich());
        ps.setString(6, tt.getMaTaiKhoan());
        ps.setString(7, tt.getMaThanhToan());
        int rows = ps.executeUpdate();

        // ✅ Đồng bộ trạng thái sang DonHang
        String sql2 = "UPDATE DonHang SET TrangThai=? WHERE MaDonHang=?";
        try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps2.setString(1, mapThanhToanToDonHangStatus(tt.getTrangThai()));
            ps2.setString(2, tt.getMaDonHang());
            ps2.executeUpdate();
        }

        return rows > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

// ✅ Mapping logic ngược lại
private String mapThanhToanToDonHangStatus(String trangThaiThanhToan) {
    return switch (trangThaiThanhToan) {
        case "Hoàn tất" -> "Đã giao";
        case "Đang chờ" -> "Đang giao";
        case "Hoàn tiền" -> "Hoàn tiền";
        default -> "Đang xử lý";
    };
}


    // ✅ Ánh xạ
    private ThanhToan map(ResultSet rs) throws SQLException {
        ThanhToan tt = new ThanhToan();
        tt.setMaThanhToan(rs.getString("MaThanhToan"));
        tt.setMaDonHang(rs.getString("MaDonHang"));
        tt.setHinhThuc(rs.getString("HinhThuc"));
        tt.setSoTien(rs.getBigDecimal("SoTien"));
        tt.setTrangThai(rs.getString("TrangThai"));
        tt.setMaGiaoDich(rs.getString("MaGiaoDich"));
        tt.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
        return tt;
    }
}
