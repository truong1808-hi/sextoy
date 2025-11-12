package com.toystore.controller;

import java.io.IOException;
import java.util.List;

import com.toystore.dao.DanhMucDAO;
import com.toystore.model.DanhMuc;
import com.toystore.model.SanPham;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/danhmuc")
public class AdminDanhMucServlet extends HttpServlet {
    private final DanhMucDAO dao = new DanhMucDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new" -> showNew(req, resp);
            case "edit" -> showEdit(req, resp);
            case "delete" -> delete(req, resp);
            case "detail" -> showDetail(req, resp);
            default -> showList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("insert".equals(action)) insert(req, resp);
        else if ("update".equals(action)) update(req, resp);
    }

    // 🔹 Danh sách + tìm kiếm
    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("q");
        List<DanhMuc> list = dao.search(keyword);
        req.setAttribute("list", list);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/admin/danhmuc-list.jsp").forward(req, resp);
    }

    // 🔹 Xem chi tiết danh mục + sản phẩm trong đó
    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    String id = req.getParameter("id");
    DanhMuc dm = dao.findById(id);
    List<SanPham> sanPhams = dao.getProductsByDanhMuc(id);

    req.setAttribute("danhmuc", dm);
    req.setAttribute("sanPhams", sanPhams);

    req.getRequestDispatcher("/admin/danhmuc-detail.jsp").forward(req, resp);
}


    // 🔹 Mở form thêm mới
    private void showNew(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/admin/danhmuc-form.jsp").forward(req, resp);
    }

    // 🔹 Mở form sửa
    private void showEdit(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    String id = req.getParameter("id");
    if (id == null || id.isEmpty()) {
        resp.sendRedirect("danhmuc");
        return;
    }

    DanhMuc dm = dao.findById(id);
    if (dm == null) {
        resp.sendRedirect("danhmuc?msg=notfound");
        return;
    }

    req.setAttribute("danhmuc", dm);
    req.getRequestDispatcher("/admin/danhmuc-form.jsp").forward(req, resp);
}


    // 🔹 Thêm mới
    private void insert(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        DanhMuc dm = new DanhMuc();
        dm.setMaDanhMuc(req.getParameter("maDanhMuc"));
        dm.setTenDanhMuc(req.getParameter("tenDanhMuc"));
        dm.setMoTa(req.getParameter("moTa"));

        boolean ok = dao.insert(dm);
        resp.sendRedirect("danhmuc?msg=" + (ok ? "added" : "fail"));
    }

    // 🔹 Cập nhật
    private void update(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        DanhMuc dm = new DanhMuc();
        dm.setMaDanhMuc(req.getParameter("maDanhMuc"));
        dm.setTenDanhMuc(req.getParameter("tenDanhMuc"));
        dm.setMoTa(req.getParameter("moTa"));

        boolean ok = dao.update(dm);
        resp.sendRedirect("danhmuc?msg=" + (ok ? "updated" : "fail"));
    }

    // 🔹 Xóa
    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String id = req.getParameter("id");
        dao.delete(id);
        resp.sendRedirect("danhmuc?msg=deleted");
    }
}
