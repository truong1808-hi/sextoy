<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Nhà Cung Cấp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <h3 class="text-primary mb-3">
        <i class="fa-solid fa-circle-info"></i> Chi tiết Nhà Cung Cấp
    </h3>

    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <p><strong>Mã NCC:</strong> ${ncc.maNCC}</p>
            <p><strong>Tên Nhà Cung Cấp:</strong> ${ncc.tenNCC}</p>
            <p><strong>Địa chỉ:</strong> ${ncc.diaChi}</p>
            <p><strong>Số điện thoại:</strong> ${ncc.soDienThoai}</p>
            <p><strong>Email:</strong> ${ncc.email}</p>
            <p><strong>Mô tả:</strong> ${ncc.moTa}</p>
        </div>
    </div>

    <h5 class="text-success mb-3"><i class="fa-solid fa-boxes-stacked"></i> Sản phẩm đang cung cấp</h5>
    <table class="table table-bordered table-hover bg-white shadow-sm align-middle">
        <thead class="table-info text-center">
            <tr>
                <th>Mã SP</th>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th>Số lượng tồn</th>
                <th>Danh mục</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sp" items="${sanPhams}">
                <tr>
                    <td>${sp.maSanPham}</td>
                    <td>${sp.ten}</td>
                    <td><fmt:formatNumber value="${sp.gia}" type="currency" currencySymbol="₫" /></td>
                    <td>${sp.soLuongTon}</td>
                    <td>${sp.maDanhMuc}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty sanPhams}">
                <tr><td colspan="5" class="text-center text-muted">Nhà cung cấp này chưa có sản phẩm</td></tr>
            </c:if>
        </tbody>
    </table>

    <a href="nhacungcap" class="btn btn-secondary mt-3">
        <i class="fa-solid fa-arrow-left"></i> Quay lại
    </a>
</div>
</body>
</html>
