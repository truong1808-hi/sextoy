package com.toystore.controller;

// Đảm bảo import 2 thư viện này
import com.toystore.service.EmailService; 
import java.util.Random; 

import com.toystore.dao.TaiKhoanDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    // ==========================================================
    // === HÀM doPost ĐÃ ĐƯỢC VIẾT LẠI (CHỈNH SỬA) ===
    // ==========================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("register-email");
        String password = request.getParameter("register-password");
        String confirmPassword = request.getParameter("register-confirm-password");

        HttpSession session = request.getSession();
        
        // 1. Kiểm tra mật khẩu khớp
        if (!password.equals(confirmPassword)) {
            session.setAttribute("registerMessage", "Mật khẩu không khớp. Vui lòng thử lại.");
            session.setAttribute("registerMessageType", "error");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        TaiKhoanDAO dao = new TaiKhoanDAO();
        try {
            // 2. Kiểm tra email đã tồn tại chưa
            if (dao.emailExists(email)) {
                session.setAttribute("registerMessage", "Email này đã được sử dụng.");
                session.setAttribute("registerMessageType", "error");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // 3. TẠO MÃ XÁC THỰC
            String verificationCode = String.format("%06d", new java.util.Random().nextInt(999999));
            
            // 4. Thực hiện đăng ký (lưu tài khoản chưa xác thực và mã code)
            String username = email.split("@")[0];
            boolean isSuccess = dao.registerUser(username, email, password, verificationCode);

            if (isSuccess) {
                // 5. Gửi Email chứa mã
                EmailService.sendVerificationEmail(email, verificationCode);
                
                // 6. Lưu email vào session để trang verify.jsp biết là ai
                session.setAttribute("email_for_verification", email);
                
                // 7. Chuyển hướng đến trang nhập mã
                response.sendRedirect(request.getContextPath() + "/verify"); // Chuyển đến Servlet xác thực
            } else {
                throw new Exception("Đăng ký thất bại do lỗi hệ thống.");
            }
        } catch (Exception e) {
            session.setAttribute("registerMessage", "Đăng ký thất bại! " + e.getMessage());
            session.setAttribute("registerMessageType", "error");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}