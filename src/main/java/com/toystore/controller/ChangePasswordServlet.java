package com.toystore.controller;

import com.toystore.dao.TaiKhoanDAO;
import com.toystore.model.TaiKhoan;
import com.toystore.service.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/change-password"})
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        TaiKhoan acc = (TaiKhoan) session.getAttribute("acc");
        String action = request.getParameter("action");

        // Bắt buộc đăng nhập
        if (acc == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        TaiKhoanDAO dao = new TaiKhoanDAO();
        String maTaiKhoan = acc.getMaTaiKhoan();
        
        // Bước 1: Yêu cầu gửi mã (Dựa trên MK cũ)
        if ("request_code".equals(action)) {
            String oldPassword = request.getParameter("oldPassword");
            
            // Kiểm tra mật khẩu cũ
            if (dao.checkOldPassword(maTaiKhoan, oldPassword)) {
                // Mật khẩu cũ đúng -> Tạo mã, lấy email, lưu mã, gửi mail
                String code = String.format("%06d", new java.util.Random().nextInt(999999));
                String email = dao.getEmailByMaTaiKhoan(maTaiKhoan);
                
                dao.setVerificationCode(maTaiKhoan, code);
                EmailService.sendChangePasswordCodeEmail(email, code); // Gọi hàm service mới
                
                session.setAttribute("passwordMessage", "Mã xác nhận đã được gửi đến email của bạn.");
                session.setAttribute("passwordMessageType", "success");
            } else {
                // Mật khẩu cũ sai
                session.setAttribute("passwordMessage", "Mật khẩu cũ không chính xác.");
                session.setAttribute("passwordMessageType", "error");
            }
        } 
        
        // Bước 2: Xác nhận đổi mật khẩu (Dựa trên mã)
        else if ("change_password".equals(action)) {
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            String code = request.getParameter("verificationCode");

            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("passwordMessage", "Mật khẩu mới không khớp.");
                session.setAttribute("passwordMessageType", "error");
            } else {
                // Cập nhật MK mới nếu mã xác nhận đúng
                boolean isSuccess = dao.updatePasswordWithCode(maTaiKhoan, newPassword, code);
                if (isSuccess) {
                    session.setAttribute("passwordMessage", "Đổi mật khẩu thành công!");
                    session.setAttribute("passwordMessageType", "success");
                } else {
                    session.setAttribute("passwordMessage", "Mã xác nhận không chính xác hoặc đã hết hạn.");
                    session.setAttribute("passwordMessageType", "error");
                }
            }
        }

        // Luôn quay lại trang profile
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}