package com.toystore.controller;

import java.io.IOException;
import java.util.List;

import com.toystore.dao.DanhMucDAO;
import com.toystore.dao.NhaCungCapDAO;
import com.toystore.dao.SanPhamDAO;
import com.toystore.model.NhaCungCap;
import com.toystore.model.SanPham;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/nhacungcap")
public class AdminNhaCungCapServlet extends HttpServlet {
    private final NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    private final DanhMucDAO dmDAO = new DanhMucDAO();
    private final SanPhamDAO spDAO = new SanPhamDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add" -> showForm(req, res, "add");
            case "edit" -> showForm(req, res, "update");
            case "delete" -> delete(req, res);
            case "detail" -> showDetail(req, res);
            default -> showList(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            insert(req, res);
        } else if ("update".equals(action)) {
            update(req, res);
        } else {
            res.sendRedirect("nhacungcap");
        }
    }

    // 📌 Danh sách NCC (lọc theo danh mục)
    private void showList(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String maDanhMuc = req.getParameter("maDanhMuc");
        List<NhaCungCap> list = (maDanhMuc == null || maDanhMuc.isEmpty())
                ? nccDAO.getAll()
                : nccDAO.filterByDanhMuc(maDanhMuc);

        req.setAttribute("list", list);
        req.setAttribute("danhmucs", dmDAO.getAll());
        req.setAttribute("maDanhMuc", maDanhMuc);
        req.getRequestDispatcher("/admin/nhacungcap-list.jsp").forward(req, res);
    }

    // 📌 Hiển thị form thêm / sửa
    private void showForm(HttpServletRequest req, HttpServletResponse res, String action)
            throws ServletException, IOException {

        if ("update".equals(action)) {
            String id = req.getParameter("id");
            NhaCungCap ncc = nccDAO.findById(id);
            req.setAttribute("ncc", ncc);
        }

        req.setAttribute("action", action);
        req.setAttribute("danhmucs", dmDAO.getAll());
        req.getRequestDispatcher("/admin/nhacungcap-form.jsp").forward(req, res);
    }

    // 📌 Hiển thị chi tiết NCC + danh sách sản phẩm họ cung cấp
    private void showDetail(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        NhaCungCap ncc = nccDAO.findById(id);
        List<SanPham> sanPhams = spDAO.findByNhaCungCap(id);

        req.setAttribute("ncc", ncc);
        req.setAttribute("sanPhams", sanPhams);
        req.getRequestDispatcher("/admin/nhacungcap-detail.jsp").forward(req, res);
    }

    // 📌 Thêm NCC mới
    private void insert(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC(req.getParameter("MaNCC"));
        ncc.setTenNCC(req.getParameter("TenNCC"));
        ncc.setDiaChi(req.getParameter("DiaChi"));
        ncc.setSoDienThoai(req.getParameter("SoDienThoai"));
        ncc.setEmail(req.getParameter("Email"));
        ncc.setMoTa(req.getParameter("MoTa"));

        nccDAO.insert(ncc);
        res.sendRedirect("nhacungcap");
    }

    // 📌 Cập nhật NCC
    private void update(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC(req.getParameter("MaNCC"));
        ncc.setTenNCC(req.getParameter("TenNCC"));
        ncc.setDiaChi(req.getParameter("DiaChi"));
        ncc.setSoDienThoai(req.getParameter("SoDienThoai"));
        ncc.setEmail(req.getParameter("Email"));
        ncc.setMoTa(req.getParameter("MoTa"));

        nccDAO.update(ncc);
        res.sendRedirect("nhacungcap");
    }

    // 📌 Xóa NCC
    private void delete(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String id = req.getParameter("id");
        nccDAO.delete(id);
        res.sendRedirect("nhacungcap");
    }
}
