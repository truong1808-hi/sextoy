<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
</head>

<body class="bg-light">
<div class="container py-5">
    <div class="card shadow-lg border-0 rounded-3">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <h4 class="mb-0"><i class="fas fa-box-open"></i> Chi tiết sản phẩm</h4>
            <a href="${pageContext.request.contextPath}/admin/sanpham" class="btn btn-light btn-sm">
                <i class="fas fa-arrow-left"></i> Quay lại
            </a>
        </div>

        <div class="card-body">
            <div class="row">
                <div class="col-md-5 text-center">
                    <img src="${pageContext.request.contextPath}/${sp.hinhAnh}" class="img-fluid rounded border shadow-sm"
                         style="max-height: 300px"
                         onerror="this.src='${pageContext.request.contextPath}/assets/image/no-image.png'">
                </div>

                <div class="col-md-7">
                    <table class="table table-borderless">
                        <tr><th>Mã sản phẩm:</th><td>${sp.maSanPham}</td></tr>
                        <tr><th>Tên sản phẩm:</th><td>${sp.ten}</td></tr>
                        <tr><th>Giá:</th><td><fmt:formatNumber value="${sp.gia}" type="currency" currencySymbol="₫"/></td></tr>
                        <tr><th>Số lượng tồn:</th><td>${sp.soLuongTon}</td></tr>
                        <tr><th>Danh mục:</th><td>${sp.maDanhMuc}</td></tr>
                        <tr><th>Nhà cung cấp:</th><td>${sp.maNCC}</td></tr>
                        <tr><th>Mô tả:</th><td><pre style="white-space: pre-wrap;">${sp.moTa}</pre></td></tr>
                    </table>
                </div>
            </div>
        </div>

        <div class="card-footer text-center">
            <a href="${pageContext.request.contextPath}/admin/sanpham/edit?ma=${sp.maSanPham}" class="btn btn-primary me-2"><i class="fas fa-edit"></i> Sửa</a>
            <a href="${pageContext.request.contextPath}/admin/sanpham/delete?ma=${sp.maSanPham}" class="btn btn-danger"
               onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này không?')">
               <i class="fas fa-trash"></i> Xóa
            </a>
        </div>
    </div>
</div>
</body>
</html>
