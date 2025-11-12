package com.toystore.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.toystore.model.SanPham;
import com.toystore.model.ThongKe;


public class ThongKeDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // 🔹 Lấy danh sách thống kê theo ngày (tự động tổng hợp từ DonHang)
    public List<ThongKe> getThongKeTheoNgay() {
        List<ThongKe> list = new ArrayList<>();

        String sql = """
            SELECT 
                DATE(ngayDat) AS Ngay,
                COUNT(maDonHang) AS TongDon,
                SUM(soLuong) AS TongSPBan,
                SUM(tongTien) AS DoanhThuNgay
            FROM DonHang
            WHERE trangThai = 'Hoàn thành'
            GROUP BY DATE(ngayDat)
            ORDER BY Ngay DESC;
        """;

        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ThongKe tk = new ThongKe();
                tk.setMaThongKe("TK" + rs.getDate("Ngay").toString().replace("-", ""));
                tk.setNgay(rs.getDate("Ngay"));
                tk.setTongDon(rs.getInt("TongDon"));
                tk.setTongSanPhamBan(rs.getInt("TongSPBan"));
                tk.setTongDoanhThuNgay(rs.getBigDecimal("DoanhThuNgay"));
                tk.setTonKho(getTongTonKho());
                tk.setSpBanChay(getSanPhamBanChay());
                tk.setTongDoanhThuTuan(getDoanhThuTuan());
                tk.setTongDoanhThuThang(getDoanhThuThang());
                tk.setTongDoanhThuNam(getDoanhThuNam());
                list.add(tk);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔹 Tổng doanh thu toàn hệ thống
    public BigDecimal getTongDoanhThu() {
        String sql = "SELECT SUM(tongTien) FROM DonHang WHERE trangThai = 'Hoàn thành'";
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getBigDecimal(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // 🔹 Tổng tồn kho
    private int getTongTonKho() {
        String sql = "SELECT SUM(soLuongTon) FROM SanPham";
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 🔹 Sản phẩm bán chạy nhất
    private String getSanPhamBanChay() {
        String sql = """
            SELECT tenSanPham, SUM(soLuong) AS Tong
            FROM DonHang
            WHERE trangThai = 'Hoàn thành'
            GROUP BY tenSanPham
            ORDER BY Tong DESC
            LIMIT 1;
        """;
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getString("tenSanPham");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Không có dữ liệu";
    }

    // 🔹 Doanh thu tuần
    private BigDecimal getDoanhThuTuan() {
        String sql = """
            SELECT SUM(tongTien) FROM DonHang 
            WHERE trangThai = 'Hoàn thành' 
            AND YEARWEEK(ngayDat, 1) = YEARWEEK(CURDATE(), 1);
        """;
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getBigDecimal(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // 🔹 Doanh thu tháng
    private BigDecimal getDoanhThuThang() {
        String sql = """
            SELECT SUM(tongTien) FROM DonHang 
            WHERE trangThai = 'Hoàn thành'
            AND MONTH(ngayDat) = MONTH(CURDATE())
            AND YEAR(ngayDat) = YEAR(CURDATE());
        """;
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getBigDecimal(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // 🔹 Doanh thu năm
    private BigDecimal getDoanhThuNam() {
        String sql = """
            SELECT SUM(tongTien) FROM DonHang 
            WHERE trangThai = 'Hoàn thành'
            AND YEAR(ngayDat) = YEAR(CURDATE());
        """;
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getBigDecimal(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // 🔹 Sản phẩm sắp hết hàng (SL < 10)
    public List<SanPham> getSanPhamSapHet() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT MaSanPham, Ten, Gia, SoLuongTon, HinhAnh FROM SanPham WHERE SoLuongTon < 10";
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getString("MaSanPham"));
                sp.setTen(rs.getString("Ten"));
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
    public List<ThongKe> getAllThongKe() {
    List<ThongKe> list = new ArrayList<>();
    String sql = "SELECT * FROM ThongKe ORDER BY Ngay DESC";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            ThongKe tk = new ThongKe();
            tk.setMaThongKe(rs.getString("MaThongKe"));
            tk.setNgay(rs.getDate("Ngay"));
            tk.setTongDon(rs.getInt("TongDon"));
            tk.setTongSanPhamBan(rs.getInt("TongSanPhamBan"));
            tk.setTongDoanhThuNgay(rs.getBigDecimal("TongDoanhThuNgay"));
            tk.setTonKho(rs.getInt("TonKho"));
            tk.setSpBanChay(rs.getString("SPBanChay"));
            tk.setTongDoanhThuTuan(rs.getBigDecimal("TongDoanhThuTuan"));
            tk.setTongDoanhThuThang(rs.getBigDecimal("TongDoanhThuThang"));
            tk.setTongDoanhThuNam(rs.getBigDecimal("TongDoanhThuNam"));
            list.add(tk);
        }
    } catch (Exception e) { e.printStackTrace(); }
    return list;
}

}
