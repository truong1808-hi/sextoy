package com.toystore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toystore.model.DonHang;
import com.toystore.model.NguoiDung;

public class DonHangDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    
    public List<DonHang> findByNguoiDung(String maNguoiDung) {
        List<DonHang> list = new ArrayList<>();
        String sql = """
            SELECT MaDonHang, MaNguoiDung, MaSanPham, TenSanPham, SoLuong, Gia, TongTien,
                   TrangThai, DiaChiGiao, SoDienThoai, NgayDat
            FROM DonHang
            WHERE MaNguoiDung = ?
            ORDER BY NgayDat DESC
        """;
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNguoiDung);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean hasPendingOrders(String maTaiKhoan) {
        String sql = "SELECT COUNT(*) FROM DonHang WHERE MaTaiKhoan=? AND TrangThai IN ('Đang xử lý','Đang giao')";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, maTaiKhoan);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(rs, ps, conn); }
        return false;
    }

    private DonHang map(ResultSet rs) throws SQLException {
        DonHang dh = new DonHang();
        dh.setMaDonHang(rs.getString("MaDonHang"));
        dh.setMaNguoiDung(rs.getString("MaNguoiDung"));
        dh.setMaSanPham(rs.getString("MaSanPham"));
        dh.setTenSanPham(rs.getString("TenSanPham"));
        dh.setSoLuong(rs.getInt("SoLuong"));
        dh.setGia(rs.getBigDecimal("Gia"));
        dh.setTongTien(rs.getBigDecimal("TongTien"));
        dh.setTrangThai(rs.getString("TrangThai"));
        dh.setDiaChi(rs.getString("DiaChiGiao"));
        dh.setSoDienThoai(rs.getString("SoDienThoai"));
        dh.setNgayDat(rs.getTimestamp("NgayDat"));
        String maTK = rs.getString("MaTaiKhoan");
            if (maTK == null || maTK.isEmpty()) {
    
                try (PreparedStatement ps2 = conn.prepareStatement("SELECT MaTaiKhoan FROM NguoiDung WHERE MaNguoiDung=?")) {
                    ps2.setString(1, rs.getString("MaNguoiDung"));
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) maTK = rs2.getString("MaTaiKhoan");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            dh.setMaTaiKhoan(maTK);


             return dh;
    }

    
    public List<DonHang> getAll() {
        List<DonHang> list = new ArrayList<>();
        String sql = "SELECT * FROM DonHang ORDER BY NgayDat DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ✅ Tìm đơn hàng theo ID
    public DonHang findById(String maDonHang) {
        String sql = "SELECT * FROM DonHang WHERE MaDonHang=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDonHang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public List<DonHang> search(String keyword, String trangThai) {
    List<DonHang> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM DonHang WHERE 1=1");

    if (keyword != null && !keyword.isEmpty())
        sql.append(" AND (MaDonHang LIKE ? OR MaNguoiDung LIKE ? OR TenSanPham LIKE ?)");
    if (trangThai != null && !trangThai.isEmpty() && !"Tất cả".equalsIgnoreCase(trangThai))
        sql.append(" AND TrangThai = ?");

    sql.append(" ORDER BY NgayDat DESC");

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        int i = 1;
        if (keyword != null && !keyword.isEmpty()) {
            ps.setString(i++, "%" + keyword + "%");
            ps.setString(i++, "%" + keyword + "%");
            ps.setString(i++, "%" + keyword + "%");
        }
        if (trangThai != null && !trangThai.isEmpty() && !"Tất cả".equalsIgnoreCase(trangThai))
            ps.setString(i++, trangThai);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) list.add(map(rs));
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    public boolean update(DonHang dh) {
    String sql = """
        UPDATE DonHang
        SET MaNguoiDung=?, MaSanPham=?, TenSanPham=?, SoLuong=?, Gia=?, TongTien=?, 
            TrangThai=?, DiaChiGiao=?, SoDienThoai=?, NgayDat=?
        WHERE MaDonHang=?
    """;
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, dh.getMaNguoiDung());
        ps.setString(2, dh.getMaSanPham());
        ps.setString(3, dh.getTenSanPham());
        ps.setInt(4, dh.getSoLuong());
        ps.setBigDecimal(5, dh.getGia());
        ps.setBigDecimal(6, dh.getTongTien());
        ps.setString(7, dh.getTrangThai());
        ps.setString(8, dh.getDiaChi());
        ps.setString(9, dh.getSoDienThoai());
        ps.setTimestamp(10, new java.sql.Timestamp(dh.getNgayDat().getTime()));
        ps.setString(11, dh.getMaDonHang());

        int rows = ps.executeUpdate();

        // ✅ Đồng bộ trạng thái sang bảng ThanhToan
        String sql2 = "UPDATE ThanhToan SET TrangThai=? WHERE MaDonHang=?";
        try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps2.setString(1, mapDonHangToThanhToanStatus(dh.getTrangThai()));
            ps2.setString(2, dh.getMaDonHang());
            ps2.executeUpdate();
        }

        return rows > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

// ✅ Mapping logic: “Đang giao” -> “Đang chờ”, “Đã giao” -> “Hoàn tất”, ...
private String mapDonHangToThanhToanStatus(String trangThaiDonHang) {
    return switch (trangThaiDonHang) {
        case "Đang xử lý" -> "Đang chờ";
        case "Đang giao" -> "Đang chờ";
        case "Đã giao" -> "Hoàn tất";
        case "Hoàn tiền" -> "Hoàn tiền";
        default -> "Đang chờ";
    };
}
    // ✅ Đồng bộ thông tin người dùng vào bảng DonHang
public boolean updateNguoiDungInfo(NguoiDung nd) {
    String sql = """
        UPDATE DonHang
        SET DiaChiGiao = ?, SoDienThoai = ?, MaTaiKhoan = ?
        WHERE MaNguoiDung = ?
    """;
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, nd.getDiaChi());
        ps.setString(2, nd.getSoDienThoai());
        ps.setString(3, nd.getMaTaiKhoan());
        ps.setString(4, nd.getMaNguoiDung());

        int rows = ps.executeUpdate();
        System.out.println("🟢 Đồng bộ " + rows + " đơn hàng của người dùng " + nd.getMaNguoiDung());
        return rows > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public List<DonHang> getDaGiao() {
    List<DonHang> list = new ArrayList<>();
    String sql = "SELECT * FROM DonHang WHERE TrangThai = 'Đã giao' ORDER BY NgayDat DESC";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) list.add(map(rs));
    } catch (Exception e) { e.printStackTrace(); }
    return list;
}



}
