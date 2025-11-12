<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý khuyến mãi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f9f9f9; }
        h3 { color: #d63384; }
    </style>
</head>
<body class="container py-4">

<h3 class="mb-4">📢 Quản lý khuyến mãi</h3>

<form method="get" action="khuyenmai" class="row g-2 mb-4">
    <div class="col-md-4">
        <input type="text" name="q" class="form-control" placeholder="Tìm theo tên hoặc mã..."
               value="${param.q}">
    </div>
    <div class="col-md-3">
        <select name="trangThai" class="form-select">
            <option value="all">-- Tất cả trạng thái --</option>
            <option value="Đang áp dụng" ${param.trangThai=='Đang áp dụng'?'selected':''}>Đang áp dụng</option>
            <option value="Chưa bắt đầu" ${param.trangThai=='Chưa bắt đầu'?'selected':''}>Chưa bắt đầu</option>
            <option value="Đã kết thúc" ${param.trangThai=='Đã kết thúc'?'selected':''}>Đã kết thúc</option>
        </select>
    </div>
    <div class="col-md-2">
        <button class="btn btn-primary w-100">🔍 Tìm kiếm</button>
    </div>
    <div class="col-md-3 text-end">
        <a href="khuyenmai?action=add" class="btn btn-success w-100">➕ Thêm khuyến mãi</a>
    </div>
</form>

<table class="table table-bordered align-middle text-center shadow-sm bg-white">
    <thead class="table-light">
    <tr>
        <th>Mã KM</th>
        <th>Mã SP</th>
        <th>Tên SP</th>
        <th>% Giảm</th>
        <th>Trạng thái</th>
        <th>Ngày bắt đầu</th>
        <th>Ngày kết thúc</th>
        <th>Thao tác</th>
    </tr>
</thead>
<tbody>
    <c:forEach var="km" items="${list}">
        <tr>
            <td>${km.maKhuyenMai}</td>
            <td>${km.maSanPham}</td>
            <td>${km.tenSanPham}</td>
            <td>${km.phanTramGiam}%</td>
            <td>
                <span class="badge 
                    ${km.trangThai=='Đang áp dụng'?'bg-success':
                      (km.trangThai=='Đã kết thúc'?'bg-danger':'bg-secondary')}">
                    ${km.trangThai}
                </span>
            </td>
            <td>${km.ngayBatDau}</td>
            <td>${km.ngayKetThuc}</td>
            <td>
                <a href="khuyenmai?action=detail&id=${km.maKhuyenMai}" class="btn btn-sm btn-info text-white">👁</a>
                <a href="khuyenmai?action=edit&id=${km.maKhuyenMai}" class="btn btn-sm btn-warning">✏️</a>
                <a href="khuyenmai?action=delete&id=${km.maKhuyenMai}"
                   class="btn btn-sm btn-danger"
                   onclick="return confirm('Xóa khuyến mãi này?')">🗑</a>
            </td>
        </tr>
    </c:forEach>

    <c:if test="${empty list}">
        <tr><td colspan="8" class="text-center text-muted">Không có dữ liệu</td></tr>
    </c:if>
</tbody>

</table>

</body>
</html>
