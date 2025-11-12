package com.toystore.controller;

import com.toystore.dao.NguoiDungDAO;
import com.toystore.model.NguoiDung;
import com.toystore.model.TaiKhoan;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    // Hiển thị trang profile.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        TaiKhoan acc = (TaiKhoan) session.getAttribute("acc");

        if (acc == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        NguoiDungDAO dao = new NguoiDungDAO();
        NguoiDung profile = dao.getNguoiDungByMaTaiKhoan(acc.getMaTaiKhoan());
        
        if (profile == null) {
            profile = new NguoiDung();
            profile.setEmail(acc.getTenDangNhap()); 
            profile.setMaTaiKhoan(acc.getMaTaiKhoan());
            profile.setHoTen(acc.getTenDangNhap().split("@")[0]);
        }
        
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    // Xử lý cập nhật thông tin cá nhân
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        TaiKhoan acc = (TaiKhoan) session.getAttribute("acc");

        if (acc == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Lấy thông tin từ form
            String maNguoiDung = request.getParameter("maNguoiDung");
            String hoTen = request.getParameter("hoTen");
            String soDienThoai = request.getParameter("soDienThoai");
            String diaChi = request.getParameter("diaChi");

            NguoiDungDAO dao = new NguoiDungDAO();
            NguoiDung profile = dao.getNguoiDungByMaTaiKhoan(acc.getMaTaiKhoan());
            
            if (profile == null) {
                 profile = new NguoiDung();
                 profile.setMaNguoiDung(maNguoiDung); 
            }

            // Cập nhật thông tin
            profile.setHoTen(hoTen);
            profile.setSoDienThoai(soDienThoai);
            profile.setDiaChi(diaChi);
            profile.setMaNguoiDung(request.getParameter("maNguoiDung")); 

            boolean isSuccess = dao.updateNguoiDung(profile);

            if (isSuccess) {
                session.setAttribute("profileMessage", "Cập nhật thông tin thành công!");
                session.setAttribute("profileMessageType", "success");
                
                session.removeAttribute("globalMessage");
                session.removeAttribute("globalMessageType");
                session.removeAttribute("globalMessageLink");
            } else {
                throw new Exception("Cập nhật thất bại. Mã người dùng có thể không đúng.");
            }

        } catch (Exception e) {
            session.setAttribute("profileMessage", "Đã có lỗi xảy ra: " + e.getMessage());
            session.setAttribute("profileMessageType", "error");
        }
        
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}