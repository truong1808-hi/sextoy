package com.toystore.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

// Bạn có thể giữ @WebFilter hoặc xóa nó đi vì chúng ta đã khai báo trong web.xml
@WebFilter(urlPatterns = {"/*"}) 
public class LanguageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // (SỬA LỖI) Chỉ chạy logic cho các trang, bỏ qua các tài nguyên tĩnh (css, js, images,...)
        if (!path.startsWith("/assets/")) {
            HttpSession session = req.getSession();

            String langParam = req.getParameter("lang");

            if (langParam != null && !langParam.isEmpty()) {
                Locale locale = new Locale(langParam);

                // Key cho JSTL 3.0 (Jakarta) phải bắt đầu bằng "jakarta."
                session.setAttribute("jakarta.servlet.jsp.jstl.fmt.locale.session", locale);
            }

            // Đặt ngôn ngữ mặc định nếu chưa có trong session
            if (session.getAttribute("jakarta.servlet.jsp.jstl.fmt.locale.session") == null) {
                // Đặt Tiếng Việt làm mặc định
                session.setAttribute("jakarta.servlet.jsp.jstl.fmt.locale.session", new Locale("vi"));
            }
        }

        chain.doFilter(request, response);
    }
}