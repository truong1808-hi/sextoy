<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.toystore.model.DonHang, com.toystore.model.NguoiDung" %>

<html>
<head>
    <title>Chi tiết đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<%
    DonHang dh = (DonHang) request.getAttribute("donhang");
    NguoiDung nd = (NguoiDung) request.getAttribute("nguoidung");
%>

<h3>📄 Chi tiết đơn hàng: <%= dh.getMaDonHang() %></h3>

<!-- 🔹 Thông tin đơn hàng -->
<table class="table table-bordered mt-3">
    <tr><th>Mã đơn hàng</th><td><%= dh.getMaDonHang() %></td></tr>
    <tr><th>Mã người dùng</th><td><%= dh.getMaNguoiDung() %></td></tr>
    <tr><th>Tên sản phẩm</th><td><%= dh.getTenSanPham() %></td></tr>
    <tr><th>Số lượng</th><td><%= dh.getSoLuong() %></td></tr>
    <tr><th>Giá</th><td><%= dh.getGia() %></td></tr>
    <tr><th>Tổng tiền</th><td><%= dh.getTongTien() %></td></tr>
    <tr><th>Trạng thái</th><td><%= dh.getTrangThai() %></td></tr>
    <tr><th>Địa chỉ giao</th><td><%= dh.getDiaChi() %></td></tr>
    <tr><th>Số điện thoại giao</th><td><%= dh.getSoDienThoai() %></td></tr>
    <tr><th>Ngày đặt</th><td><%= dh.getNgayDat() %></td></tr>
</table>

<!-- 🔹 Thông tin khách hàng -->
<h4 class="mt-4">👤 Thông tin người mua</h4>
<table class="table table-bordered">
    <tr><th>Họ tên</th><td><%= nd != null ? nd.getHoTen() : "Không tìm thấy" %></td></tr>
    <tr><th>Email</th><td><%= nd != null ? nd.getEmail() : "" %></td></tr>
    <tr><th>Số điện thoại</th><td><%= nd != null ? nd.getSoDienThoai() : "" %></td></tr>
    <tr><th>Địa chỉ</th><td><%= nd != null ? nd.getDiaChi() : "" %></td></tr>
    <tr><th>Mã tài khoản</th><td><%= nd != null ? nd.getMaTaiKhoan() : "" %></td></tr>
</table>

<a href="donhang" class="btn btn-secondary">↩ Quay lại</a>
<a href="donhang?action=edit&id=<%= dh.getMaDonHang() %>" class="btn btn-warning">✏️ Sửa đơn hàng</a>

</body>
</html>
