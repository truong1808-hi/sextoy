package com.toystore.controller;
import com.toystore.dao.DanhMucDAO;
import com.toystore.dao.HinhAnhDAO;
import com.toystore.model.DanhMuc;
import com.toystore.model.HinhAnh;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// (ĐÃ SỬA) Đã xóa bỏ ", """ ra khỏi urlPatterns.
// Mapping "" là nguyên nhân gây ra lỗi CSS.
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"}) 
public class HomeServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        DanhMucDAO danhMucDAO = new DanhMucDAO();
        HinhAnhDAO hinhAnhDAO = new HinhAnhDAO();

        // 1. Lấy các danh mục chính (Lego, Mô hình,...)
        List<DanhMuc> listDanhMuc = danhMucDAO.getHeaderCategories();

        // 2. Với mỗi danh mục, lấy 4 ảnh demo của nó
        for (DanhMuc dm : listDanhMuc) {
            List<HinhAnh> listAnh = hinhAnhDAO.getTop4DemoImagesByMaDM(dm.getMaDanhMuc());
            dm.setHinhAnhDemo(listAnh); // Gắn danh sách ảnh vào đối tượng DanhMuc
        }

        // 3. Đẩy danh sách DanhMuc (đã có ảnh) ra JSP
        request.setAttribute("listCategories", listDanhMuc);

        // 4. Chuyển tiếp đến trang /user/index.jsp
        request.getRequestDispatcher("/user/index.jsp").forward(request, response);
    }
}