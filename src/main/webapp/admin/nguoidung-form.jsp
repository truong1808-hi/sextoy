<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.toystore.model.NguoiDung" %>

<html>
<head>
    <title>Cập nhật người dùng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">

<h3>📝 Cập nhật người dùng</h3>

<%
    NguoiDung nd = (NguoiDung) request.getAttribute("nguoidung");
    boolean isNew = (nd == null);
    if (nd == null) {
        nd = new NguoiDung(); // tạo rỗng tránh NullPointerException
    }
%>

<form action="nguoidung" method="post">
    <input type="hidden" name="action" value="<%= isNew ? "insert" : "update" %>">

    <div class="row">
        <div class="col-md-6">
            <label>Mã ND</label>
            <input name="maNguoiDung" value="<%= nd.getMaNguoiDung() != null ? nd.getMaNguoiDung() : "" %>"
                   class="form-control" <%= isNew ? "" : "readonly" %> required>
        </div>
        <div class="col-md-6">
            <label>Họ tên</label>
            <input name="hoTen" value="<%= nd.getHoTen() != null ? nd.getHoTen() : "" %>"
                   class="form-control" required>
        </div>
    </div>

    <div class="row mt-3">
        <div class="col-md-6">
            <label>Email</label>
            <input type="email" name="email" value="<%= nd.getEmail() != null ? nd.getEmail() : "" %>"
                   class="form-control" required>
        </div>
        <div class="col-md-6">
            <label>Số điện thoại</label>
            <input name="soDienThoai" value="<%= nd.getSoDienThoai() != null ? nd.getSoDienThoai() : "" %>"
                   class="form-control">
        </div>
    </div>

    <div class="row mt-3">
        <div class="col-md-6">
            <label>Địa chỉ</label>
            <input name="diaChi" value="<%= nd.getDiaChi() != null ? nd.getDiaChi() : "" %>"
                   class="form-control">
        </div>
        <div class="col-md-6">
            <label>Mã tài khoản</label>
            <input name="maTaiKhoan" value="<%= nd.getMaTaiKhoan() != null ? nd.getMaTaiKhoan() : "" %>"
                   class="form-control">
        </div>
    </div>

    <div class="mt-4">
        <button type="submit" class="btn btn-success">💾 Lưu thay đổi</button>
        <a href="nguoidung" class="btn btn-secondary">↩ Quay lại</a>
    </div>
</form>

</body>
</html>
