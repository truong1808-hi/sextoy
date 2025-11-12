package com.toystore.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toystore.model.SanPham;

public class SanPhamDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // ✅ Lấy tất cả sản phẩm
    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham ORDER BY MaSanPham ASC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(rs, ps, conn);
        }
        return list;
    }
    // ✅ Lấy danh sách sản phẩm theo mã nhà cung cấp
public List<SanPham> findByNhaCungCap(String maNCC) {
    List<SanPham> list = new ArrayList<>();
    String sql = "SELECT * FROM SanPham WHERE MaNCC = ? ORDER BY MaSanPham ASC";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, maNCC);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs)); // dùng lại hàm map(ResultSet) bạn đã có
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    // ✅ Tìm sản phẩm theo mã
    public SanPham findById(String ma) {
        SanPham sp = null;
        String sql = "SELECT * FROM SanPham WHERE MaSanPham=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            rs = ps.executeQuery();
            if (rs.next()) sp = map(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(rs, ps, conn);
        }
        return sp;
    }

    // ✅ Tìm kiếm & lọc sản phẩm
    public List<SanPham> search(String keyword, String maDanhMuc) {
        List<SanPham> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM SanPham WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (Ten LIKE ? OR MaSanPham LIKE ?)");
        }
        if (maDanhMuc != null && !maDanhMuc.trim().isEmpty()) {
            sql.append(" AND MaDanhMuc = ?");
        }

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql.toString());
            int i = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(i++, "%" + keyword + "%");
                ps.setString(i++, "%" + keyword + "%");
            }
            if (maDanhMuc != null && !maDanhMuc.trim().isEmpty()) {
                ps.setString(i++, maDanhMuc);
            }

            rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(rs, ps, conn);
        }
        return list;
    }

    // ✅ Thêm mới
    public boolean insert(SanPham sp) {
        String sql = "INSERT INTO SanPham (MaSanPham, Ten, MoTa, Gia, SoLuongTon, MaDanhMuc, MaNCC, HinhAnh) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, sp.getMaSanPham());
            ps.setString(2, sp.getTen());
            ps.setString(3, sp.getMoTa());
            ps.setBigDecimal(4, sp.getGia() != null ? sp.getGia() : BigDecimal.ZERO);
            ps.setInt(5, sp.getSoLuongTon());
            ps.setString(6, sp.getMaDanhMuc());
            ps.setString(7, sp.getMaNCC());
            ps.setString(8, sp.getHinhAnh());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(null, ps, conn);
        }
        return false;
    }

    // ✅ Cập nhật
    // ✅ Cập nhật sản phẩm – giữ nguyên ảnh cũ nếu không chọn ảnh mới
public boolean update(SanPham sp) {
    String sql = """
        UPDATE SanPham
        SET Ten=?, MoTa=?, Gia=?, SoLuongTon=?, MaDanhMuc=?, MaNCC=?,
            HinhAnh = COALESCE(NULLIF(?, ''), HinhAnh)
        WHERE MaSanPham=?
    """;

    try {
        conn = DBContext.getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, sp.getTen());
        ps.setString(2, sp.getMoTa());
        ps.setBigDecimal(3, sp.getGia());
        ps.setInt(4, sp.getSoLuongTon());
        ps.setString(5, sp.getMaDanhMuc());
        ps.setString(6, sp.getMaNCC());
        ps.setString(7, sp.getHinhAnh()); // Nếu chuỗi rỗng thì giữ nguyên ảnh cũ
        ps.setString(8, sp.getMaSanPham());
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        DBContext.close(null, ps, conn);
    }
    return false;
}


    // ✅ Xóa
    public boolean delete(String ma) {
        String sql = "DELETE FROM SanPham WHERE MaSanPham=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(null, ps, conn);
        }
        return false;
    }

    // ✅ Map ResultSet → Model
    private SanPham map(ResultSet rs) throws SQLException {
        SanPham sp = new SanPham();
        sp.setMaSanPham(rs.getString("MaSanPham"));
        sp.setTen(rs.getString("Ten"));
        sp.setMoTa(rs.getString("MoTa"));
        sp.setGia(rs.getBigDecimal("Gia"));
        sp.setSoLuongTon(rs.getInt("SoLuongTon"));
        sp.setMaDanhMuc(rs.getString("MaDanhMuc"));
        sp.setMaNCC(rs.getString("MaNCC"));
        sp.setHinhAnh(rs.getString("HinhAnh"));
        return sp;
    }
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM SanPham";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
