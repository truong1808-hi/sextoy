package com.toystore.controller;

import java.io.IOException;
import java.util.List;

import com.toystore.dao.DonHangDAO;
import com.toystore.dao.NguoiDungDAO;
import com.toystore.model.DonHang;
import com.toystore.model.NguoiDung;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/nguoidung")
public class AdminNguoiDungServlet extends HttpServlet {
    private final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
    private final DonHangDAO donHangDAO = new DonHangDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "edit" -> showEdit(req, resp);
            case "detail" -> showDetail(req, resp);
            case "delete" -> delete(req, resp);
            default -> showList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if ("insert".equals(action) || "update".equals(action)) {
            save(req, resp, action);
        }
    }

    // 🧾 Danh sách người dùng
    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = req.getParameter("q");
        List<NguoiDung> list = (keyword == null || keyword.isEmpty())
                ? nguoiDungDAO.getAll()
                : nguoiDungDAO.search(keyword);

        req.setAttribute("list", list);
        req.getRequestDispatcher("/admin/nguoidung-list.jsp").forward(req, resp);
    }

    // ✏️ Hiển thị form chỉnh sửa / thêm mới
    private void showEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        NguoiDung nd = null;

        if (id != null && !id.isEmpty()) {
            nd = nguoiDungDAO.findById(id);
        }

        req.setAttribute("nguoidung", nd);
        req.getRequestDispatcher("/admin/nguoidung-form.jsp").forward(req, resp);
    }

    // 👁️ Hiển thị chi tiết người dùng và đơn hàng
    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id"); // ví dụ ND08
        NguoiDung nd = nguoiDungDAO.findById(id);
        List<DonHang> donhangList = donHangDAO.findByNguoiDung(id);

        System.out.println("✅ DEBUG: ND=" + id + " có " + donhangList.size() + " đơn hàng");

        req.setAttribute("nguoidung", nd);
        req.setAttribute("donhangList", donhangList);
        req.getRequestDispatcher("/admin/nguoidung-detail.jsp").forward(req, resp);
    }

    // 💾 Lưu (insert hoặc update)
    private void save(HttpServletRequest req, HttpServletResponse resp, String action)
        throws IOException {
    NguoiDung nd = new NguoiDung();
    nd.setMaNguoiDung(req.getParameter("maNguoiDung"));
    nd.setHoTen(req.getParameter("hoTen"));
    nd.setEmail(req.getParameter("email"));
    nd.setSoDienThoai(req.getParameter("soDienThoai"));
    nd.setDiaChi(req.getParameter("diaChi"));
    nd.setMaTaiKhoan(req.getParameter("maTaiKhoan"));

    boolean success = false;

    if ("insert".equals(action)) {
        success = nguoiDungDAO.insert(nd);
        System.out.println("🟢 INSERT người dùng: " + nd.getMaNguoiDung() + " → " + success);

    } else if ("update".equals(action)) {
        success = nguoiDungDAO.update(nd);
        System.out.println("🟡 UPDATE người dùng: " + nd.getMaNguoiDung() + " → " + success);

        // 🟢 Nếu update thành công → cập nhật thông tin vào đơn hàng
        if (success) {
            DonHangDAO donHangDAO = new DonHangDAO();
            boolean sync = donHangDAO.updateNguoiDungInfo(nd);
            System.out.println("🔄 Đồng bộ đơn hàng của " + nd.getMaNguoiDung() + " → " + sync);
        }
    }

    // 🔔 Điều hướng kèm thông báo
    resp.sendRedirect(req.getContextPath() + "/admin/nguoidung?msg=" + (success ? "success" : "fail"));
}


   


    // ❌ Xóa người dùng
    private void delete(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {
    String id = req.getParameter("id");

    // 1️⃣ Kiểm tra người dùng có tồn tại không
    NguoiDung nd = nguoiDungDAO.findById(id);
    if (nd == null) {
        System.out.println("⚠️ Không tìm thấy người dùng: " + id);
        resp.sendRedirect(req.getContextPath() + "/admin/nguoidung?error=notfound");
        return;
    }

    // 2️⃣ Kiểm tra người dùng có đơn hàng đang giao / xử lý không
    boolean hasOrders = donHangDAO.hasPendingOrders(nd.getMaTaiKhoan());
    if (hasOrders) {
        System.out.println(" Người dùng " + id + " có đơn hàng đang xử lý/giao -> không xóa được");
        resp.sendRedirect(req.getContextPath() + "/admin/nguoidung?error=pending");
        return;
    }

    // 3️⃣ Thực hiện xóa
    boolean deleted = nguoiDungDAO.delete(id);
    System.out.println(" DELETE người dùng: " + id + " → " + deleted);

    // 4️⃣ Thông báo kết quả
    resp.sendRedirect(req.getContextPath() + "/admin/nguoidung?msg=" + (deleted ? "deleted" : "fail"));
}

}
