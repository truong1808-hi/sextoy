package com.toystore.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;

import com.toystore.dao.DanhMucDAO;
import com.toystore.dao.NhaCungCapDAO;
import com.toystore.dao.SanPhamDAO;
import com.toystore.model.DanhMuc;
import com.toystore.model.NhaCungCap;
import com.toystore.model.SanPham;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/admin/sanpham/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class AdminSanPhamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final SanPhamDAO sanPhamDAO = new SanPhamDAO();
    private final DanhMucDAO danhMucDAO = new DanhMucDAO();
    private final NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();

    // ---------------------------------------
    // 🔹 Xử lý GET
    // ---------------------------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            listSanPham(request, response);
        } else if (path.equals("/add")) {
            showAddForm(request, response);
        } else if (path.equals("/edit")) {
            showEditForm(request, response);
        } else if (path.equals("/detail")) {
            showDetail(request, response);
        } else if (path.equals("/delete")) {
            deleteSanPham(request, response);
        } else {
            listSanPham(request, response);
        }
    }

    // ---------------------------------------
    // 🔹 Xử lý POST
    // ---------------------------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getPathInfo();
        if (path == null) path = "";

        switch (path) {
            case "/save" -> insertSanPham(request, response);
            case "/update" -> updateSanPham(request, response);
            default -> listSanPham(request, response);
        }
    }

    // ---------------------------------------
    // ➕ Form thêm
    // ---------------------------------------
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("danhMucs", danhMucDAO.getAll());
        request.setAttribute("nhaCungCaps", nhaCungCapDAO.getAll());
        request.setAttribute("action", "save");
        request.getRequestDispatcher("/admin/sanpham-form.jsp").forward(request, response);
    }

    // ---------------------------------------
    // ✏️ Form sửa
    // ---------------------------------------
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ma = request.getParameter("ma");
        SanPham sp = sanPhamDAO.findById(ma);

        request.setAttribute("sp", sp);
        request.setAttribute("danhMucs", danhMucDAO.getAll());
        request.setAttribute("nhaCungCaps", nhaCungCapDAO.getAll());
        request.setAttribute("action", "update");

        request.getRequestDispatcher("/admin/sanpham-form.jsp").forward(request, response);
    }

    // ---------------------------------------
    // 👁️‍🗨️ Chi tiết sản phẩm
    // ---------------------------------------
    private void showDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ma = request.getParameter("ma");
        SanPham sp = sanPhamDAO.findById(ma);

        request.setAttribute("sp", sp);
        request.getRequestDispatcher("/admin/sanpham-detail.jsp").forward(request, response);
    }

    // ---------------------------------------
    // ✅ Thêm sản phẩm
    // ---------------------------------------
    private void insertSanPham(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            SanPham sp = extractSanPhamFromRequest(request);
            boolean success = sanPhamDAO.insert(sp);
            request.setAttribute("message", success ? "✅ Thêm sản phẩm thành công!" : "❌ Thêm thất bại!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "⚠️ Lỗi thêm sản phẩm: " + e.getMessage());
        }
        listSanPham(request, response);
    }

    // ---------------------------------------
    // 🔁 Cập nhật sản phẩm
    // ---------------------------------------
    private void updateSanPham(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            SanPham sp = extractSanPhamFromRequest(request);
            boolean success = sanPhamDAO.update(sp);
            request.setAttribute("message", success ? "✅ Cập nhật thành công!" : "❌ Cập nhật thất bại!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "⚠️ Lỗi cập nhật: " + e.getMessage());
        }
        listSanPham(request, response);
    }

    // ---------------------------------------
    // 🗑️ Xóa sản phẩm
    // ---------------------------------------
    private void deleteSanPham(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ma = request.getParameter("ma");
        boolean success = sanPhamDAO.delete(ma);
        request.setAttribute("message", success ? "🗑️ Xóa thành công!" : "⚠️ Không thể xóa sản phẩm!");
        listSanPham(request, response);
    }

    // ---------------------------------------
    // 🧰 Hàm phụ: Lấy dữ liệu từ form + lưu ảnh
    // ---------------------------------------
    private SanPham extractSanPhamFromRequest(HttpServletRequest request)
            throws IOException, ServletException {

        SanPham sp = new SanPham();
        sp.setMaSanPham(request.getParameter("maSanPham"));
        sp.setTen(request.getParameter("ten"));
        sp.setGia(new BigDecimal(request.getParameter("gia")));
        sp.setSoLuongTon(Integer.parseInt(request.getParameter("soLuongTon")));
        sp.setMaDanhMuc(request.getParameter("maDanhMuc"));
        sp.setMaNCC(request.getParameter("maNCC"));
        sp.setMoTa(request.getParameter("moTa"));

        Part filePart = request.getPart("fileImage");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // 🟢 Đường dẫn thật đến thư mục ảnh trong webapp
            String uploadPath = getServletContext().getRealPath("") + "assets/image/";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // 🟢 Ghi file
            filePart.write(uploadPath + fileName);

            // 🟢 Lưu đường dẫn vào DB (tính từ context root)
            sp.setHinhAnh("assets/image/" + fileName);
        } else {
            sp.setHinhAnh(request.getParameter("hinhAnh")); // giữ ảnh cũ
        }

        return sp;
    }

    // ---------------------------------------
    // 📋 Danh sách + Tìm kiếm + Lọc danh mục
    // ---------------------------------------
    private void listSanPham(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("q");
        String maDanhMuc = request.getParameter("maDanhMuc");

        List<SanPham> list;
        if ((keyword != null && !keyword.trim().isEmpty()) ||
            (maDanhMuc != null && !maDanhMuc.trim().isEmpty())) {
            list = sanPhamDAO.search(keyword, maDanhMuc);
        } else {
            list = sanPhamDAO.getAll();
        }

        request.setAttribute("listSanPham", list);
        request.setAttribute("danhMucs", danhMucDAO.getAll());
        request.setAttribute("keyword", keyword);
        request.setAttribute("maDanhMuc", maDanhMuc);

        request.getRequestDispatcher("/admin/sanpham.jsp").forward(request, response);
    }
}
