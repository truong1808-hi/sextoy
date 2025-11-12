package com.toystore.controller;
import java.io.IOException;
import java.util.List;

import com.toystore.dao.DanhMucDAO;
import com.toystore.dao.HinhAnhDAO;
import com.toystore.model.DanhMuc;
import com.toystore.model.HinhAnh;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin/dashboard"})
public class AdminServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // --- BẮT ĐẦU CODE TẢI HEADER ---
        DanhMucDAO danhMucDAO = new DanhMucDAO();
        HinhAnhDAO hinhAnhDAO = new HinhAnhDAO();
        List<DanhMuc> listDanhMuc = danhMucDAO.getHeaderCategories();
        for (DanhMuc dm : listDanhMuc) {
            List<HinhAnh> listAnh = hinhAnhDAO.getTop4DemoImagesByMaDM(dm.getMaDanhMuc());
            dm.setHinhAnhDemo(listAnh);
        }
        request.setAttribute("listCategories", listDanhMuc);
        // --- KẾT THÚC CODE TẢI HEADER ---
        
        
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}