package com.toystore.controller;

import com.toystore.dao.KhuyenMaiDAO;
import com.toystore.dao.SanPhamDAO;
import com.toystore.model.KhuyenMai;
import com.toystore.model.SanPham;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/admin/khuyenmai")
public class AdminKhuyenMaiServlet extends HttpServlet {
    private final KhuyenMaiDAO kmDAO = new KhuyenMaiDAO();
    private final SanPhamDAO spDAO = new SanPhamDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        kmDAO.capNhatTrangThai(); // ✅ Cập nhật trạng thái tự động

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add" -> showForm(req, resp, new KhuyenMai(), false);
            case "edit" -> editForm(req, resp);
            case "delete" -> delete(req, resp);
            case "detail" -> detail(req, resp);
            default -> list(req, resp);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String q = req.getParameter("q");
        String trangThai = req.getParameter("trangThai");
        List<KhuyenMai> list = kmDAO.search(q, trangThai);
        req.setAttribute("list", list);
        req.getRequestDispatcher("/admin/khuyenmai-list.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, KhuyenMai km, boolean edit)
            throws ServletException, IOException {
        req.setAttribute("km", km);
        req.setAttribute("isEdit", edit);
        req.getRequestDispatcher("/admin/khuyenmai-form.jsp").forward(req, resp);
    }

    private void editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        KhuyenMai km = kmDAO.findById(id);
        if (km == null) {
            resp.sendRedirect("khuyenmai");
            return;
        }
        showForm(req, resp, km, true);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        kmDAO.delete(req.getParameter("id"));
        resp.sendRedirect("khuyenmai");
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        KhuyenMai km = kmDAO.findById(id);

        if (km != null) {
            SanPham sp = spDAO.findById(km.getMaSanPham());
            if (sp != null) {
                km.setTenSanPham(sp.getTen());
                km.setGiaGoc(sp.getGia());
                BigDecimal giaSau = sp.getGia()
                        .subtract(sp.getGia().multiply(km.getPhanTramGiam()).divide(BigDecimal.valueOf(100)));
                km.setGiaSauGiam(giaSau);
            }
        }

        req.setAttribute("km", km);
        req.getRequestDispatcher("/admin/khuyenmai-detail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");

        String maKM = req.getParameter("maKhuyenMai").trim();
        String maSP = req.getParameter("maSanPham").trim();
        String isEdit = req.getParameter("isEdit");

        SanPham sp = spDAO.findById(maSP);
        if (sp == null) {
            req.setAttribute("error", "❌ Mã sản phẩm không tồn tại!");
            showForm(req, resp, new KhuyenMai(), false);
            return;
        }

        BigDecimal phanTram = new BigDecimal(req.getParameter("phanTramGiam"));
        BigDecimal giaGoc = sp.getGia();
        BigDecimal giaSau = giaGoc.subtract(giaGoc.multiply(phanTram).divide(BigDecimal.valueOf(100)));

        KhuyenMai km = new KhuyenMai();
        km.setMaKhuyenMai(maKM);
        km.setMaSanPham(maSP);
        km.setTenSanPham(sp.getTen());
        km.setTenKhuyenMai(req.getParameter("tenKhuyenMai"));
        km.setMoTa(req.getParameter("moTa"));
        km.setPhanTramGiam(phanTram);
        km.setGiaGoc(giaGoc);
        km.setGiaSauGiam(giaSau);
        km.setNgayBatDau(Timestamp.valueOf(req.getParameter("ngayBatDau") + " 00:00:00"));
        km.setNgayKetThuc(Timestamp.valueOf(req.getParameter("ngayKetThuc") + " 23:59:59"));
        km.setTrangThai(req.getParameter("trangThai") != null ? req.getParameter("trangThai") : "Đang áp dụng");

        if ("true".equals(isEdit)) {
            kmDAO.update(km);
        } else {
            if (kmDAO.findById(maKM) != null) {
                req.setAttribute("error", "⚠️ Mã khuyến mãi đã tồn tại!");
                showForm(req, resp, km, false);
                return;
            }
            kmDAO.insert(km);
        }
        resp.sendRedirect("khuyenmai");
    }
}
