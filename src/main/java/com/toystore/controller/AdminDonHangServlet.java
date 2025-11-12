package com.toystore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.toystore.dao.DonHangDAO;
import com.toystore.model.DonHang;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/donhang")
public class AdminDonHangServlet extends HttpServlet {
    private final DonHangDAO donHangDAO = new DonHangDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "edit" -> showEdit(req, resp);
            case "detail" -> showDetail(req, resp);
            case "exportExcel" -> exportExcel(resp);
            case "exportPdf" -> exportPdf(resp);
            default -> showList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("update".equalsIgnoreCase(action)) {
            save(req, resp);
        }
    }

    // 🔹 Hiển thị danh sách đơn hàng
    private void showList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String q = req.getParameter("q");
        String trangThai = req.getParameter("trangThai");

        List<DonHang> list = donHangDAO.search(q, trangThai);
        req.setAttribute("list", list);
        req.setAttribute("q", q);
        req.setAttribute("trangThai", trangThai);
        req.getRequestDispatcher("/admin/donhang-list.jsp").forward(req, resp);
    }

    // 🔹 Xem chi tiết đơn hàng
    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        DonHang dh = donHangDAO.findById(id);

        if (dh == null) {
            resp.sendRedirect("donhang?msg=notfound");
            return;
        }

        com.toystore.dao.NguoiDungDAO nguoiDungDAO = new com.toystore.dao.NguoiDungDAO();
        com.toystore.model.NguoiDung nguoiDung = nguoiDungDAO.findById(dh.getMaNguoiDung());

        req.setAttribute("donhang", dh);
        req.setAttribute("nguoidung", nguoiDung);
        req.getRequestDispatcher("/admin/donhang-detail.jsp").forward(req, resp);
    }

    // 🔹 Form sửa đơn hàng
    private void showEdit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        DonHang dh = donHangDAO.findById(id);

        if (dh == null) {
            resp.sendRedirect("donhang?msg=notfound");
            return;
        }

        req.setAttribute("donhang", dh);
        req.getRequestDispatcher("/admin/donhang-form.jsp").forward(req, resp);
    }

    // 🔹 Cập nhật đơn hàng
    private void save(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            DonHang dh = new DonHang();
            dh.setMaDonHang(req.getParameter("maDonHang"));
            dh.setMaNguoiDung(req.getParameter("maNguoiDung"));
            dh.setMaSanPham(req.getParameter("maSanPham"));
            dh.setTenSanPham(req.getParameter("tenSanPham"));
            dh.setSoLuong(Integer.parseInt(req.getParameter("soLuong")));
            dh.setGia(new BigDecimal(req.getParameter("gia")));
            dh.setTongTien(new BigDecimal(req.getParameter("tongTien")));
            dh.setTrangThai(req.getParameter("trangThai"));

            String ngayDatStr = req.getParameter("ngayDat");
            if (ngayDatStr != null && !ngayDatStr.isEmpty()) {
                dh.setNgayDat(java.sql.Timestamp.valueOf(ngayDatStr.replace("T", " ") + ":00"));
            }

            dh.setDiaChi(req.getParameter("diaChiGiao"));
            dh.setSoDienThoai(req.getParameter("soDienThoai"));
            dh.setMaTaiKhoan(req.getParameter("maTaiKhoan"));

            boolean ok = donHangDAO.update(dh);
            resp.sendRedirect("donhang?msg=" + (ok ? "updated" : "fail"));
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("donhang?msg=error");
        }
    }

    // 🔸 Xuất danh sách ĐÃ GIAO ra Excel
    private void exportExcel(HttpServletResponse resp) throws IOException {
        List<DonHang> list = donHangDAO.getDaGiao();

        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=DonHang_DaGiao.xlsx");

        try (org.apache.poi.ss.usermodel.Workbook wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet("Đơn hàng đã giao");
            String[] headers = {"Mã ĐH", "Người dùng", "Sản phẩm", "Số lượng", "Tổng tiền", "Ngày đặt", "Địa chỉ"};

            org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++)
                header.createCell(i).setCellValue(headers[i]);

            int rowNum = 1;
            for (DonHang dh : list) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(dh.getMaDonHang());
                row.createCell(1).setCellValue(dh.getMaNguoiDung());
                row.createCell(2).setCellValue(dh.getTenSanPham());
                row.createCell(3).setCellValue(dh.getSoLuong());
                row.createCell(4).setCellValue(dh.getTongTien().doubleValue());
                row.createCell(5).setCellValue(String.valueOf(dh.getNgayDat()));
                row.createCell(6).setCellValue(dh.getDiaChi());
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
            wb.write(resp.getOutputStream());
        }
    }

    // 🔸 Xuất danh sách ĐÃ GIAO ra PDF
    private void exportPdf(HttpServletResponse resp) throws IOException {
        List<DonHang> list = donHangDAO.getDaGiao();

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=DonHang_DaGiao.pdf");

        try {
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            com.itextpdf.text.pdf.PdfWriter.getInstance(doc, resp.getOutputStream());
            doc.open();
            doc.add(new com.itextpdf.text.Paragraph("📦 DANH SÁCH ĐƠN HÀNG ĐÃ GIAO\n\n"));

            com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(6);
            table.addCell("Ma DH");
            table.addCell("Nguoi dung");
            table.addCell("San pham");
            table.addCell("So luong");
            table.addCell("Tong tien");
            table.addCell("Ngay dat");

            for (DonHang dh : list) {
                table.addCell(dh.getMaDonHang());
                table.addCell(dh.getMaNguoiDung());
                table.addCell(dh.getTenSanPham());
                table.addCell(String.valueOf(dh.getSoLuong()));
                table.addCell(String.valueOf(dh.getTongTien()));
                table.addCell(String.valueOf(dh.getNgayDat()));
            }

            doc.add(table);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
