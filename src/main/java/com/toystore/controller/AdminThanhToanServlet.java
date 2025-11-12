package com.toystore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.toystore.dao.ThanhToanDAO;
import com.toystore.model.ThanhToan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/thanhtoan")
public class AdminThanhToanServlet extends HttpServlet {
    private final ThanhToanDAO dao = new ThanhToanDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "detail" -> showDetail(req, resp);
            case "edit" -> showEdit(req, resp);
            default -> showList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("update".equals(action)) save(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String q = req.getParameter("q");
        String trangThai = req.getParameter("trangThai");
        String hinhThuc = req.getParameter("hinhThuc");

        List<ThanhToan> list = dao.search(q, trangThai, hinhThuc);
        req.setAttribute("list", list);
        req.setAttribute("q", q);
        req.setAttribute("trangThai", trangThai == null ? "Tất cả" : trangThai);
        req.setAttribute("hinhThuc", hinhThuc == null ? "Tất cả" : hinhThuc);

        req.getRequestDispatcher("/admin/thanhtoan-list.jsp").forward(req, resp);
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Map<String, Object> data = dao.findWithDonHang(id);
        req.setAttribute("data", data);
        req.getRequestDispatcher("/admin/thanhtoan-detail.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        ThanhToan tt = dao.getAll().stream()
                .filter(x -> x.getMaThanhToan().equals(id))
                .findFirst().orElse(null);
        req.setAttribute("thanhtoan", tt);
        req.getRequestDispatcher("/admin/thanhtoan-form.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ThanhToan tt = new ThanhToan();
        tt.setMaThanhToan(req.getParameter("maThanhToan"));
        tt.setMaDonHang(req.getParameter("maDonHang"));
        tt.setHinhThuc(req.getParameter("hinhThuc"));
        tt.setSoTien(new BigDecimal(req.getParameter("soTien")));
        tt.setTrangThai(req.getParameter("trangThai"));
        tt.setMaGiaoDich(req.getParameter("maGiaoDich"));
        tt.setMaTaiKhoan(req.getParameter("maTaiKhoan"));

        boolean ok = dao.update(tt);
        resp.sendRedirect("thanhtoan?msg=" + (ok ? "success" : "fail"));
    }
}
