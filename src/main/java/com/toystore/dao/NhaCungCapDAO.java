package com.toystore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toystore.model.NhaCungCap;

public class NhaCungCapDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<NhaCungCap> getAll() {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";
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

    public NhaCungCap findById(String id) {
        String sql = "SELECT * FROM NhaCungCap WHERE MaNCC=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(rs, ps, conn);
        }
        return null;
    }

    public boolean insert(NhaCungCap ncc) {
        String sql = "INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SoDienThoai, Email, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getMaNCC());
            ps.setString(2, ncc.getTenNCC());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getSoDienThoai());
            ps.setString(5, ncc.getEmail());
            ps.setString(6, ncc.getMoTa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(null, ps, conn);
        }
        return false;
    }

    public boolean update(NhaCungCap ncc) {
        String sql = """
            UPDATE NhaCungCap SET TenNCC=?, DiaChi=?, SoDienThoai=?, Email=?, MoTa=? WHERE MaNCC=?
        """;
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSoDienThoai());
            ps.setString(4, ncc.getEmail());
            ps.setString(5, ncc.getMoTa());
            ps.setString(6, ncc.getMaNCC());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(null, ps, conn);
        }
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM NhaCungCap WHERE MaNCC=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(null, ps, conn);
        }
        return false;
    }

    // ✅ Lọc theo danh mục — chỉ lấy NCC có sản phẩm thuộc danh mục đó
    public List<NhaCungCap> filterByDanhMuc(String maDanhMuc) {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = """
            SELECT DISTINCT n.* FROM NhaCungCap n
            JOIN SanPham s ON n.MaNCC = s.MaNCC
            WHERE s.MaDanhMuc = ?
        """;
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, maDanhMuc);
            rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBContext.close(rs, ps, conn);
        }
        return list;
    }

    private NhaCungCap map(ResultSet rs) throws SQLException {
        NhaCungCap n = new NhaCungCap();
        n.setMaNCC(rs.getString("MaNCC"));
        n.setTenNCC(rs.getString("TenNCC"));
        n.setDiaChi(rs.getString("DiaChi"));
        n.setSoDienThoai(rs.getString("SoDienThoai"));
        n.setEmail(rs.getString("Email"));
        n.setMoTa(rs.getString("MoTa"));
        return n;
    }
}
