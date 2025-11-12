<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.toystore.model.ThanhToan" %>

<html>
<head>
    <title>Sửa thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<%
    ThanhToan tt = (ThanhToan) request.getAttribute("thanhtoan");
%>

<h3>✏️ Cập nhật thanh toán: <%= tt.getMaThanhToan() %></h3>

<form action="thanhtoan" method="post" class="mt-3">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="maThanhToan" value="<%= tt.getMaThanhToan() %>">

    <div class="row mb-3">
        <div class="col-md-6">
            <label>Mã đơn hàng</label>
            <input name="maDonHang" value="<%= tt.getMaDonHang() %>" class="form-control" required>
        </div>
        <div class="col-md-6">
            <label>Hình thức thanh toán</label>
            <select name="hinhThuc" class="form-select">
                <option <%= "COD".equals(tt.getHinhThuc()) ? "selected" : "" %>>COD</option>
                <option <%= "Momo".equals(tt.getHinhThuc()) ? "selected" : "" %>>Momo</option>
                <option <%= "Banking".equals(tt.getHinhThuc()) ? "selected" : "" %>>Banking</option>
            </select>
        </div>
    </div>

    <div class="row mb-3">
        <div class="col-md-4">
            <label>Số tiền</label>
            <input type="number" step="0.01" name="soTien" value="<%= tt.getSoTien() %>" class="form-control" required>
        </div>
        <div class="col-md-4">
            <label>Trạng thái</label>
            <select name="trangThai" class="form-select">
                <option <%= "Đang chờ".equals(tt.getTrangThai()) ? "selected" : "" %>>Đang chờ</option>
                <option <%= "Hoàn tất".equals(tt.getTrangThai()) ? "selected" : "" %>>Hoàn tất</option>
                <option <%= "Hoàn tiền".equals(tt.getTrangThai()) ? "selected" : "" %>>Hoàn tiền</option>
            </select>
        </div>
        <div class="col-md-4">
            <label>Mã giao dịch</label>
            <input name="maGiaoDich" value="<%= tt.getMaGiaoDich() %>" class="form-control">
        </div>
    </div>

    <div class="row mb-3">
        <div class="col-md-6">
            <label>Mã tài khoản</label>
            <input name="maTaiKhoan" value="<%= tt.getMaTaiKhoan() %>" class="form-control">
        </div>
    </div>

    <div class="mt-4">
        <button class="btn btn-success">💾 Lưu thay đổi</button>
        <a href="thanhtoan" class="btn btn-secondary">↩ Quay lại</a>
    </div>
</form>

</body>
</html>
