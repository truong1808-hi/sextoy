package com.toystore.controller;

import com.toystore.dao.TaiKhoanDAO;
import com.toystore.model.TaiKhoan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import com.toystore.dao.NguoiDungDAO;
import com.toystore.model.NguoiDung;
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    // ==========================================================
    // === HÀM doPost ĐÃ ĐƯỢC VIẾT LẠI (CHỈNH SỬA) ===
    // ==========================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("login-email");
        String password = request.getParameter("login-password");

        TaiKhoanDAO dao = new TaiKhoanDAO();
        TaiKhoan account = dao.checkLogin(email, password);
        HttpSession session = request.getSession();

        if (account == null) {
            // Đăng nhập thất bại - Sai thông tin
            request.setAttribute("errorMessage", "Email hoặc mật khẩu không chính xác.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            
        } else if (account.getIsVerified() == 0) {
            // Đăng nhập thất bại - Chưa xác thực
            session.setAttribute("verifyMessage", "Tài khoản của bạn chưa được xác thực. Vui lòng nhập mã từ email của bạn.");
            session.setAttribute("verifyMessageType", "error");
            session.setAttribute("email_for_verification", email); // Gửi email để trang verify biết
            response.sendRedirect(request.getContextPath() + "/verify"); // Chuyển đến trang nhập mã
            
        } else {
            // Đăng nhập thành công
            session.setAttribute("acc", account);
            session.setMaxInactiveInterval(3600); 
            if (!"Admin".equals(account.getVaiTro())) {
                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
                // Dùng hàm DAO từ yêu cầu trước để lấy thông tin người dùng
                NguoiDung profile = nguoiDungDAO.getNguoiDungByMaTaiKhoan(account.getMaTaiKhoan());

                // Kiểm tra xem SĐT hoặc Địa chỉ có bị rỗng (NULL hoặc chuỗi trống) không
                if (profile != null && (profile.getSoDienThoai() == null || profile.getSoDienThoai().isEmpty() 
                                     || profile.getDiaChi() == null || profile.getDiaChi().isEmpty())) {
                    
                    // Nếu thông tin trống, đặt một thông báo toàn cục vào session
                    session.setAttribute("globalMessage", "Bạn chưa cập nhật thông tin cá nhân.");
                    session.setAttribute("globalMessageType", "warning"); // 'warning' (vàng) hoặc 'error' (đỏ)
                    // Cung cấp link để người dùng nhấp vào
                    session.setAttribute("globalMessageLink", request.getContextPath() + "/profile");
                }
            }
            if ("Admin".equals(account.getVaiTro())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        }
    }
}