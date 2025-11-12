<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>

<html>
<head>
    <title>Chi tiết thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<%
    Map<String, Object> data = (Map<String, Object>) request.getAttribute("data");
%>

<h3>📄 Chi tiết thanh toán: <%= data.get("MaThanhToan") %></h3>

<h5 class="mt-3 text-primary">💳 Thông tin thanh toán</h5>
<table class="table table-bordered">
    <tr><th>Mã thanh toán</th><td><%= data.get("MaThanhToan") %></td></tr>
    <tr><th>Mã đơn hàng</th><td><a href="donhang?action=detail&id=<%= data.get("MaDonHang") %>"><%= data.get("MaDonHang") %></a></td></tr>
    <tr><th>Hình thức</th><td><%= data.get("HinhThuc") %></td></tr>
    <tr><th>Số tiền</th><td><%= data.get("SoTien") %></td></tr>
    <tr><th>Trạng thái</th><td><%= data.get("TrangThai") %></td></tr>
    <tr><th>Mã giao dịch</th><td><%= data.get("MaGiaoDich") %></td></tr>
    <tr><th>Mã tài khoản</th><td><%= data.get("MaTaiKhoan") %></td></tr>
</table>

<h5 class="mt-4 text-success">📦 Thông tin sản phẩm</h5>
<table class="table table-bordered">
    <tr><th>Tên sản phẩm</th><td><%= data.get("TenSanPham") %></td></tr>
    <tr><th>Số lượng</th><td><%= data.get("SoLuong") %></td></tr>
    <tr><th>Giá</th><td><%= data.get("Gia") %></td></tr>
    <tr><th>Tổng tiền</th><td><%= data.get("TongTien") %></td></tr>
    <tr><th>Trạng thái giao hàng</th><td><%= data.get("TrangThaiDH") %></td></tr>
</table>

<a href="thanhtoan" class="btn btn-secondary">↩ Quay lại</a>
<a href="thanhtoan?action=edit&id=<%= data.get("MaThanhToan") %>" class="btn btn-warning">✏️ Sửa</a>

</body>
</html>
