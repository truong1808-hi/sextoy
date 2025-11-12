package com.toystore.controller;

import com.toystore.dao.TaiKhoanDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Chỉ cần hiển thị trang verify.jsp
        request.getRequestDispatcher("verify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email_for_verification");
        String code = request.getParameter("verification-code");

        if (email == null || email.isEmpty()) {
            // Lỗi: không biết xác thực cho ai
            session.setAttribute("verifyMessage", "Đã có lỗi xảy ra. Vui lòng thử đăng nhập lại để xác thực.");
            session.setAttribute("verifyMessageType", "error");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        TaiKhoanDAO dao = new TaiKhoanDAO();
        boolean isVerified = dao.verifyUser(email, code);
        
        if (isVerified) {
            // Xác thực thành công
            session.removeAttribute("email_for_verification"); // Xóa email khỏi session
            session.setAttribute("registerMessage", "Xác thực thành công! Vui lòng đăng nhập.");
            session.setAttribute("registerMessageType", "success");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Xác thực thất bại
            session.setAttribute("verifyMessage", "Mã xác thực không chính xác. Vui lòng thử lại.");
            session.setAttribute("verifyMessageType", "error");
            response.sendRedirect(request.getContextPath() + "/verify"); // Tải lại trang verify
        }
    }
}