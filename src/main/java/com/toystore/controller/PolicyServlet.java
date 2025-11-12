package com.toystore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PolicyServlet", urlPatterns = {"/privacy", "/terms"})
public class PolicyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        // Chuyển tiếp đến tệp JSP tương ứng dựa trên URL
        if ("/privacy".equals(path)) {
            request.getRequestDispatcher("/chinhsachbaomat.jsp").forward(request, response);
        } else if ("/terms".equals(path)) {
            request.getRequestDispatcher("/dieukhoansudung.jsp").forward(request, response);
        } else {
            // Xử lý trường hợp không mong muốn
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}