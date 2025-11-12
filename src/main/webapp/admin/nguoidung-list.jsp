
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.toystore.model.NguoiDung" %>

<html>
<head>
    <title>Danh sách người dùng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h3>👥 Danh sách người dùng</h3>

<form method="get" action="nguoidung" class="mb-3 d-flex">
    <input type="text" name="q" class="form-control me-2" placeholder="Nhập mã, tên hoặc email...">
    <button class="btn btn-success">🔍 Tìm kiếm</button>
</form>

<table class="table table-bordered table-hover">
<thead class="table-primary">
<tr>
    <th>Mã ND</th><th>Họ tên</th><th>Email</th><th>SĐT</th><th>Địa chỉ</th><th>Tài khoản</th><th>Hành động</th>
</tr>
</thead>
<tbody>
<%
    List<NguoiDung> list = (List<NguoiDung>) request.getAttribute("list");
    if (list != null && !list.isEmpty()) {
        for (NguoiDung nd : list) {
%>
<tr>
    <td><%= nd.getMaNguoiDung() %></td>
    <td><%= nd.getHoTen() %></td>
    <td><%= nd.getEmail() %></td>
    <td><%= nd.getSoDienThoai() %></td>
    <td><%= nd.getDiaChi() %></td>
    <td><%= nd.getMaTaiKhoan() %></td>
    <td>
        <a href="nguoidung?action=detail&id=<%= nd.getMaNguoiDung() %>" class="btn btn-info btn-sm">Chi tiết</a>
        <a href="nguoidung?action=edit&id=<%= nd.getMaNguoiDung() %>" class="btn btn-warning btn-sm">Sửa</a>
        <a href="nguoidung?action=delete&id=<%= nd.getMaNguoiDung() %>" 
           class="btn btn-danger btn-sm" onclick="return confirm('Xóa người dùng này?')">Xóa</a>
    </td>
</tr>
<%
        }
    } else {
%>
<tr>
    <td colspan="7" class="text-center text-muted">Không có người dùng nào</td>
</tr>
<%
    }
%>
</tbody>
<%
    String msg = request.getParameter("msg");
    String error = request.getParameter("error");

    if ("success".equals(msg)) {
%>
    <div class="alert alert-success alert-dismissible fade show mt-2" role="alert">
        ✅ Cập nhật người dùng thành công!
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
<%
    } else if ("fail".equals(msg)) {
%>
    <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
        ❌ Thao tác thất bại. Vui lòng thử lại.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
<%
    } else if ("deleted".equals(msg)) {
%>
    <div class="alert alert-warning alert-dismissible fade show mt-2" role="alert">
        🗑️ Xóa người dùng thành công.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
<%
    } else if ("pending".equals(error)) {
%>
    <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
        ⚠️ Không thể xóa vì người dùng đang có đơn hàng đang giao / xử lý.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
<%
    } else if ("notfound".equals(error)) {
%>
    <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert">
        ❌ Không tìm thấy người dùng.
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
<%
    }
%>


</table>
</body>
</html>
