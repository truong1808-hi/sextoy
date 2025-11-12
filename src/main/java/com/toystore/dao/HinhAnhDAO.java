package com.toystore.dao;
import com.toystore.model.HinhAnh;
import java.sql.*; 
import java.util.ArrayList; 
import java.util.List;
public class HinhAnhDAO {
    Connection conn = null; PreparedStatement ps = null; ResultSet rs = null;
    
    public List<HinhAnh> getTop4DemoImagesByMaDM(String maDanhMuc) {
        List<HinhAnh> list = new ArrayList<>();
        // Truy vấn 4 ảnh từ HinhAnhSanPham, join qua SanPham để lọc theo MaDanhMuc
        String query = "SELECT ha.HinhAnhId, ha.HinhAnhUrl " +
                       "FROM HinhAnhSanPham ha " +
                       "JOIN SanPham sp ON ha.MaSanPham = sp.MaSanPham " +
                       "WHERE sp.MaDanhMuc = ? LIMIT 4";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, maDanhMuc);
            rs = ps.executeQuery();
            while (rs.next()) {
                HinhAnh ha = new HinhAnh();
                ha.setHinhAnhId(rs.getString("HinhAnhId"));
                ha.setHinhAnhUrl(rs.getString("HinhAnhUrl"));
                list.add(ha);
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }
}