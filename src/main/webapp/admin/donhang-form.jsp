<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.toystore.model.DonHang" %>

<html>
<head>
    <title>Sửa đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<%
    DonHang dh = (DonHang) request.getAttribute("donhang");
    // Tránh null pointer
    String maTK = (dh.getMaTaiKhoan() != null) ? dh.getMaTaiKhoan() : "";
%>

<h3>✏️ Cập nhật đơn hàng: <%= dh.getMaDonHang() %></h3>

<form action="donhang" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="maDonHang" value="<%= dh.getMaDonHang() %>">

    <!-- Hàng 1 -->
    <div class="row">
        <div class="col-md-6">
            <label>Mã người dùng</label>
            <input name="maNguoiDung" value="<%= dh.getMaNguoiDung() %>" class="form-control" required>
        </div>
        <div class="col-md-6">
            <label>Mã sản phẩm</label>
            <input name="maSanPham" value="<%= dh.getMaSanPham() %>" class="form-control" required>
        </div>
    </div>

    <!-- Hàng 2 -->
    <div class="row mt-3">
        <div class="col-md-6">
            <label>Tên sản phẩm</label>
            <input name="tenSanPham" value="<%= dh.getTenSanPham() %>" class="form-control" required>
        </div>
        <div class="col-md-3">
            <label>Số lượng</label>
            <input type="number" name="soLuong" value="<%= dh.getSoLuong() %>" class="form-control" required>
        </div>
        <div class="col-md-3">
            <label>Giá</label>
            <input type="number" name="gia" value="<%= dh.getGia() %>" class="form-control" required>
        </div>
    </div>

    <!-- Hàng 3 -->
    <div class="row mt-3">
        <div class="col-md-4">
            <label>Tổng tiền</label>
            <input type="number" name="tongTien" value="<%= dh.getTongTien() %>" class="form-control" required>
        </div>
        <div class="col-md-4">
            <label>Trạng thái</label>
            <select name="trangThai" class="form-select">
                <option <%= "Đang xử lý".equals(dh.getTrangThai()) ? "selected" : "" %>>Đang xử lý</option>
                <option <%= "Đang giao".equals(dh.getTrangThai()) ? "selected" : "" %>>Đang giao</option>
                <option <%= "Đã giao".equals(dh.getTrangThai()) ? "selected" : "" %>>Đã giao</option>
            </select>
        </div>
        <div class="col-md-4">
            <label>Mã tài khoản</label>
            <input name="maTaiKhoan" value="<%= maTK %>" class="form-control">
        </div>
    </div>

    <!-- Hàng 4 -->
    <div class="row mt-3">
        <div class="col-md-6">
            <label>Ngày đặt</label>
            <input type="datetime-local" name="ngayDat"
                   value="<%= dh.getNgayDat() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(dh.getNgayDat()) : "" %>"
                   class="form-control">
        </div>
        <div class="col-md-6">
            <label>Số điện thoại</label>
            <input name="soDienThoai" value="<%= dh.getSoDienThoai() %>" class="form-control">
        </div>
    </div>

    <!-- Hàng 5 -->
    <div class="row mt-3">
        <div class="col-md-12">
            <label>Địa chỉ giao</label>
            <input name="diaChiGiao" value="<%= dh.getDiaChi() %>" class="form-control">
        </div>
    </div>

    <div class="mt-4">
        <button class="btn btn-success">💾 Lưu thay đổi</button>
        <a href="donhang" class="btn btn-secondary">↩ Quay lại</a>
    </div>
</form>

</body>
</html>
