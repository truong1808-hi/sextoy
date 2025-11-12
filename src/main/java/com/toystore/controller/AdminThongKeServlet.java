package com.toystore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.toystore.dao.ThongKeDAO;
import com.toystore.model.SanPham;
import com.toystore.model.ThongKe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/thongke")
public class AdminThongKeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ThongKeDAO dao = new ThongKeDAO();

        // ✅ Lấy dữ liệu từ bảng ThongKe (đã có TK01, TK02)
        List<ThongKe> listThongKe = dao.getAllThongKe();

        // ✅ Tính tổng doanh thu toàn hệ thống
        BigDecimal tongDoanhThu = dao.getTongDoanhThu();

        // ✅ Lấy danh sách sản phẩm sắp hết hàng
        List<SanPham> sanPhamHet = dao.getSanPhamSapHet();

        // ✅ Gửi sang JSP
        request.setAttribute("listThongKe", listThongKe);
        request.setAttribute("tongDoanhThu", tongDoanhThu);
        request.setAttribute("sanPhamHet", sanPhamHet);

        request.getRequestDispatcher("/admin/thongke.jsp").forward(request, response);
    }
}
