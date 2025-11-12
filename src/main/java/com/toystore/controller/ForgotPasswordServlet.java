package com.toystore.controller;

import com.toystore.dao.TaiKhoanDAO;
import com.toystore.service.EmailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("forgot-email");
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

        // (SỬA LỖI) Tối ưu hóa logic, chỉ gửi email nếu email tồn tại
        if (taiKhoanDAO.emailExists(email)) {
            // Tạo token và thời gian hết hạn (ví dụ: 1 giờ)
            String token = UUID.randomUUID().toString();
            Timestamp expiryDate = new Timestamp(System.currentTimeMillis() + 3600 * 1000); // 1 giờ

            // Lưu token vào CSDL
            taiKhoanDAO.updateResetToken(email, token, expiryDate);
            // Gửi email
            EmailService.sendResetPasswordEmail(email, token);
        }
        // Luôn hiển thị thông báo chung để bảo mật, tránh tiết lộ email nào có trong hệ thống
        HttpSession session = request.getSession();
        session.setAttribute("forgotPasswordMessage", "Nếu email tồn tại trong hệ thống, một liên kết đặt lại mật khẩu đã được gửi.");
        session.setAttribute("forgotPasswordMessageType", "success");

        response.sendRedirect(request.getContextPath() + "/login");
    }
}