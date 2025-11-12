package com.toystore.filter;
import com.toystore.model.TaiKhoan;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        TaiKhoan acc = (session != null) ? (TaiKhoan) session.getAttribute("acc") : null;

        if (acc == null) {
            // 1. Chưa đăng nhập -> Đẩy về trang login
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            // 2. Đã đăng nhập
            if (acc.getVaiTro().equals("Admin")) {
                // 2a. Nếu là Admin -> Cho đi tiếp
                chain.doFilter(request, response);
            } else {
                // 2b. Nếu là User -> Đuổi về trang chủ
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        }
    }
}