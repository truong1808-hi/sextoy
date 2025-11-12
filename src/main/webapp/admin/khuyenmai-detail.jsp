<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết khuyến mãi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #fafafa; }
        h3 { color: #d63384; }
        th { width: 25%; background-color: #f8f9fa; }
    </style>
</head>
<body class="container py-4">

<h3 class="mb-4">📋 Chi tiết khuyến mãi</h3>

<c:if test="${km != null}">
    <table class="table table-bordered shadow-sm bg-white">
        <tr><th>Mã khuyến mãi</th><td>${km.maKhuyenMai}</td></tr>
        <tr><th>Tên khuyến mãi</th><td>${km.tenKhuyenMai}</td></tr>
        <tr><th>Mô tả</th><td>${km.moTa}</td></tr>
        <tr><th>Mã sản phẩm</th><td>${km.maSanPham}</td></tr>
        <tr><th>Tên sản phẩm</th><td>${km.tenSanPham}</td></tr>
        <tr><th>Giá gốc</th><td>${km.giaGoc}</td></tr>
        <tr><th>% Giảm giá</th><td>${km.phanTramGiam}%</td></tr>
        <tr><th>Giá sau giảm</th><td class="text-success fw-bold">${km.giaSauGiam}</td></tr>
        <tr><th>Trạng thái</th><td>
            <span class="badge ${km.trangThai=='Đang áp dụng'?'bg-success':
                                 (km.trangThai=='Đã kết thúc'?'bg-danger':'bg-secondary')}">
                ${km.trangThai}
            </span>
        </td></tr>
        <tr><th>Ngày bắt đầu</th><td>${km.ngayBatDau}</td></tr>
        <tr><th>Ngày kết thúc</th><td>${km.ngayKetThuc}</td></tr>
    </table>
</c:if>

<c:if test="${km == null}">
    <div class="alert alert-warning">⚠️ Không tìm thấy thông tin khuyến mãi.</div>
</c:if>

<a href="khuyenmai" class="btn btn-secondary mt-3">⬅️ Quay lại</a>

</body>
</html>
