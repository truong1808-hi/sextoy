<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.toystore.model.NguoiDung" %>
<%@ page import="com.toystore.model.DonHang" %>

<html>
<head>
    <title>Chi tiết người dùng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">

<h3>👤 Chi tiết người dùng</h3>

<%
    NguoiDung nd = (NguoiDung) request.getAttribute("nguoidung");
%>

<table class="table table-bordered">
<tr><th>Mã người dùng</th><td><%= nd.getMaNguoiDung() %></td></tr>
<tr><th>Họ tên</th><td><%= nd.getHoTen() %></td></tr>
<tr><th>Email</th><td><%= nd.getEmail() %></td></tr>
<tr><th>Số điện thoại</th><td><%= nd.getSoDienThoai() %></td></tr>
<tr><th>Địa chỉ</th><td><%= nd.getDiaChi() %></td></tr>
<tr><th>Mã tài khoản</th><td><%= nd.getMaTaiKhoan() %></td></tr>
</table>

<h5>📦 Danh sách đơn hàng</h5>
<table class="table table-bordered table-striped">
    <thead class="table-light">
        <tr>
            <th>Mã đơn</th>
            <th>Tên sản phẩm</th>
            <th>Số lượng</th>
            <th>Giá</th>
            <th>Tổng tiền</th>
            <th>Trạng thái</th>
            <th>Ngày đặt</th>
            <th>Địa chỉ giao</th>
            <th>SĐT</th>
        </tr>
    </thead>
    <tbody>
        <%
            List<com.toystore.model.DonHang> dhlist =
                (List<com.toystore.model.DonHang>) request.getAttribute("donhangList");

            if (dhlist != null && !dhlist.isEmpty()) {
                for (com.toystore.model.DonHang dh : dhlist) {
        %>
            <tr>
                <td><%=dh.getMaDonHang()%></td>
                <td><%=dh.getTenSanPham()%></td>
                <td><%=dh.getSoLuong()%></td>
                <td><%=dh.getGia()%></td>
                <td><%=dh.getTongTien()%></td>
                <td><%=dh.getTrangThai()%></td>
                <td><%=dh.getNgayDat()%></td>
                <td><%=dh.getDiaChi()%></td>
                <td><%=dh.getSoDienThoai()%></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="9" class="text-center text-muted">Người dùng chưa có đơn hàng</td></tr>
        <%
            }
        %>
    </tbody>
</table>

<a href="nguoidung" class="btn btn-secondary">↩ Quay lại</a>

</body>
</html>
