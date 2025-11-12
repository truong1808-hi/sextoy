package com.toystore.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toystore.model.NguoiDung;

public class NguoiDungDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // ✅ Lấy tất cả người dùng
    public List<NguoiDung> getAll() {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung ORDER BY MaNguoiDung ASC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(rs, ps, conn); }
        return list;
    }

    // ✅ Tìm kiếm theo mã / tên / email
    public List<NguoiDung> search(String keyword) {
        List<NguoiDung> list = new ArrayList<>();
        String sql = "SELECT * FROM NguoiDung WHERE MaNguoiDung LIKE ? OR HoTen LIKE ? OR Email LIKE ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 3; i++) ps.setString(i, "%" + keyword + "%");
            rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(rs, ps, conn); }
        return list;
    }

    // ✅ Tìm người dùng theo ID
    public NguoiDung findById(String id) {
        NguoiDung nd = null;
        String sql = "SELECT * FROM NguoiDung WHERE MaNguoiDung=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) nd = map(rs);
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(rs, ps, conn); }
        return nd;
    }

    // ✅ Thêm mới người dùng
    public boolean insert(NguoiDung nd) {
        String sql = "INSERT INTO NguoiDung (MaNguoiDung, HoTen, Email, SoDienThoai, DiaChi, MaTaiKhoan) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nd.getMaNguoiDung());
            ps.setString(2, nd.getHoTen());
            ps.setString(3, nd.getEmail());
            ps.setString(4, nd.getSoDienThoai());
            ps.setString(5, nd.getDiaChi());
            ps.setString(6, nd.getMaTaiKhoan());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(null, ps, conn); }
        return false;
    }

    // ✅ Cập nhật người dùng
    public boolean update(NguoiDung nd) {
        String sql = """
            UPDATE NguoiDung
            SET HoTen=?, Email=?, SoDienThoai=?, DiaChi=?, MaTaiKhoan=?
            WHERE MaNguoiDung=?
        """;
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nd.getHoTen());
            ps.setString(2, nd.getEmail());
            ps.setString(3, nd.getSoDienThoai());
            ps.setString(4, nd.getDiaChi());
            ps.setString(5, nd.getMaTaiKhoan());
            ps.setString(6, nd.getMaNguoiDung());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(null, ps, conn); }
        return false;
    }

    // ✅ Xóa người dùng
    public boolean delete(String id) {
        String sql = "DELETE FROM NguoiDung WHERE MaNguoiDung=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(null, ps, conn); }
        return false;
    }

    // ✅ Tìm người dùng theo mã tài khoản (dùng cho profile, login)
    public NguoiDung getNguoiDungByMaTaiKhoan(String maTK) {
        NguoiDung nd = null;
        String sql = "SELECT * FROM NguoiDung WHERE MaTaiKhoan = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, maTK);
            rs = ps.executeQuery();
            if (rs.next()) nd = map(rs);
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(rs, ps, conn); }
        return nd;
    }

    // ✅ Cập nhật thông tin người dùng theo tài khoản
    public boolean updateNguoiDung(NguoiDung nd) {
        String sql = "UPDATE NguoiDung SET HoTen=?, Email=?, SoDienThoai=?, DiaChi=? WHERE MaTaiKhoan=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nd.getHoTen());
            ps.setString(2, nd.getEmail());
            ps.setString(3, nd.getSoDienThoai());
            ps.setString(4, nd.getDiaChi());
            ps.setString(5, nd.getMaTaiKhoan());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        finally { DBContext.close(null, ps, conn); }
        return false;
    }

    // ✅ Ánh xạ ResultSet → Model
    private NguoiDung map(ResultSet rs) throws SQLException {
        NguoiDung nd = new NguoiDung();
        nd.setMaNguoiDung(rs.getString("MaNguoiDung"));
        nd.setHoTen(rs.getString("HoTen"));
        nd.setEmail(rs.getString("Email"));
        nd.setSoDienThoai(rs.getString("SoDienThoai"));
        nd.setDiaChi(rs.getString("DiaChi"));
        nd.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
        return nd;
    }
}
