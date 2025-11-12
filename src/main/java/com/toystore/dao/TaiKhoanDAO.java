package com.toystore.dao;

import com.toystore.model.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TaiKhoanDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    /**
     * HÀM KIỂM TRA ĐĂNG NHẬP
     * Logic: Tìm TaiKhoan bằng TenDangNhap (Email) và MatKhau
     */
    public TaiKhoan checkLogin(String email, String password) {
        // TenDangNhap trong DB chính là Email
        String query = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password); // LƯU Ý: Nên mã hóa (hash) mật khẩu trong thực tế
            rs = ps.executeQuery();
            if (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setMaTaiKhoan(rs.getString("MaTaiKhoan"));
                tk.setTenDangNhap(rs.getString("TenDangNhap"));
                tk.setVaiTro(rs.getString("VaiTro"));
                tk.setIsVerified(rs.getInt("is_verified"));
                return tk;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    /**
     * HÀM KIỂM TRA EMAIL TỒN TẠI
     * Logic: Tìm trong bảng TaiKhoan (vì TenDangNhap = Email)
     */
    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM TaiKhoan WHERE TenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    /**
     * HÀM ĐĂNG KÝ TÀI KHOẢN MỚI (SỬ DỤNG TRANSACTION)
     * Logic: 1. Tắt auto-commit
     * 2. Tạo MaTaiKhoan, MaNguoiDung
     * 3. INSERT vào bảng TaiKhoan (dùng email làm TenDangNhap)
     * 4. INSERT vào bảng NguoiDung (dùng email làm Email)
     * 5. Commit giao dịch (Nếu lỗi thì rollback)
     */
    public boolean registerUser(String username, String email, String password, String verificationCode) {
        //
        String insertTaiKhoanQuery = "INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, VaiTro, verification_code, is_verified) VALUES (?, ?, ?, 'User', ?, 0)";
        String insertNguoiDungQuery = "INSERT INTO NguoiDung (MaNguoiDung, HoTen, Email, MaTaiKhoan) VALUES (?, ?, ?, ?)";

        // Tạo ID ngẫu nhiên (hoặc bạn có thể dùng logic đếm)
        String newMaTaiKhoan = "TK" + (System.currentTimeMillis() % 100000);
        String newMaNguoiDung = "ND" + (System.currentTimeMillis() % 100000);

        try {
            conn = DBContext.getConnection();
            if (conn == null) {
                throw new SQLException("Không thể kết nối đến CSDL.");
            }
            
            // Bắt đầu Transaction
            conn.setAutoCommit(false);

            // 1. Insert vào TaiKhoan (TenDangNhap là email)
            ps = conn.prepareStatement(insertTaiKhoanQuery);
            ps.setString(1, newMaTaiKhoan);
            ps.setString(2, email); // Logic mới: TenDangNhap là email
            ps.setString(3, password); // Nên hash mật khẩu
            ps.setString(4, verificationCode);
            int tkResult = ps.executeUpdate();

            // 2. Insert vào NguoiDung (HoTen là username, Email là email)
            ps = conn.prepareStatement(insertNguoiDungQuery);
            ps.setString(1, newMaNguoiDung);
            ps.setString(2, username); // Lấy username từ form
            ps.setString(3, email);
            ps.setString(4, newMaTaiKhoan);
            int ndResult = ps.executeUpdate();

            // 3. Commit
            if (tkResult > 0 && ndResult > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // Rollback nếu có lỗi
            } catch (SQLException se) {
                se.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); // Trả lại auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeResources();
        }
    }

    /**
     * HÀM XÁC THỰC TÀI KHOẢN
     * Logic: UPDATE bảng TaiKhoan dựa trên TenDangNhap (Email)
     */
    public boolean verifyUser(String email, String code) {
        //
        String query = "UPDATE TaiKhoan " +
                       "SET is_verified = 1, verification_code = NULL " +
                       "WHERE TenDangNhap = ? AND verification_code = ? AND is_verified = 0";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, code);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }
    
    /**
     * HÀM CẬP NHẬT TOKEN QUÊN MẬT KHẨU
     * Logic: UPDATE bảng TaiKhoan dựa trên TenDangNhap (Email)
     */
    public void updateResetToken(String email, String token, Timestamp expiryDate) {
        //
        String query = "UPDATE TaiKhoan " +
                       "SET reset_token = ?, reset_token_expiry = ? " +
                       "WHERE TenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, token);
            ps.setTimestamp(2, expiryDate);
            ps.setString(3, email);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }
    /**
     * (HÀM MỚI) Kiểm tra mật khẩu cũ có đúng không
     */
    public boolean checkOldPassword(String maTaiKhoan, String oldPassword) {
        String query = "SELECT COUNT(*) FROM TaiKhoan WHERE MaTaiKhoan = ? AND MatKhau = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, maTaiKhoan);
            ps.setString(2, oldPassword); // TODO: Nên hash mật khẩu
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    /**
     * (HÀM MỚI) Lưu mã xác nhận đổi mật khẩu (tái sử dụng cột verification_code)
     */
    public boolean setVerificationCode(String maTaiKhoan, String code) {
        String query = "UPDATE TaiKhoan SET verification_code = ? WHERE MaTaiKhoan = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, code);
            ps.setString(2, maTaiKhoan);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }

    /**
     * (HÀM MỚI) Cập nhật mật khẩu mới nếu mã xác nhận đúng
     */
    public boolean updatePasswordWithCode(String maTaiKhoan, String newPassword, String code) {
        String query = "UPDATE TaiKhoan SET MatKhau = ?, verification_code = NULL " +
                       "WHERE MaTaiKhoan = ? AND verification_code = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, newPassword); // TODO: Nên hash mật khẩu
            ps.setString(2, maTaiKhoan);
            ps.setString(3, code);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return false;
    }
    
    /**
     * (HÀM MỚI) Lấy Email bằng MaTaiKhoan (để gửi mail)
     */
    public String getEmailByMaTaiKhoan(String maTaiKhoan) {
        // TenDangNhap chính là Email
        String query = "SELECT TenDangNhap FROM TaiKhoan WHERE MaTaiKhoan = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, maTaiKhoan);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TenDangNhap");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    // Hàm tiện ích để đóng kết nối
    private void closeResources() {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }
}