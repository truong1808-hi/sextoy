<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
</head>

<body class="bg-light">
<div class="container py-4">

    <h2 class="text-center mb-4 text-primary">
        <i class="fas fa-box"></i> Danh sách sản phẩm
    </h2>

    <c:if test="${not empty message}">
        <div class="alert alert-info text-center">${message}</div>
    </c:if>

    <!-- 🔍 Tìm kiếm + Lọc -->
    <form action="${pageContext.request.contextPath}/admin/sanpham" method="get" class="row g-2 mb-4 align-items-end">
        <div class="col-md-4">
            <label class="form-label">Tìm theo tên hoặc mã</label>
            <input type="text" name="q" class="form-control" value="${keyword}" placeholder="Nhập tên hoặc mã...">
        </div>
        <div class="col-md-4">
            <label class="form-label">Lọc theo danh mục</label>
            <select name="maDanhMuc" class="form-select">
                <option value="">-- Tất cả danh mục --</option>
                <c:forEach var="dm" items="${danhMucs}">
                    <option value="${dm.maDanhMuc}" ${dm.maDanhMuc == maDanhMuc ? 'selected' : ''}>${dm.tenDanhMuc}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-4 text-end">
            <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
            <a href="${pageContext.request.contextPath}/admin/sanpham/add" class="btn btn-success"><i class="fas fa-plus"></i> Thêm mới</a>
        </div>
    </form>

    <!-- 📋 Bảng danh sách -->
    <div class="table-responsive">
        <table class="table table-bordered table-hover align-middle bg-white shadow-sm">
            <thead class="table-dark text-center">
                <tr>
                    <th>Mã</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Danh mục</th>
                    <th>Ảnh</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="sp" items="${listSanPham}">
                <tr>
                    <td class="text-center">${sp.maSanPham}</td>
                    <td>${sp.ten}</td>
                    <td class="text-end"><fmt:formatNumber value="${sp.gia}" type="currency" currencySymbol="₫"/></td>
                    <td class="text-center">${sp.soLuongTon}</td>
                    <td class="text-center">${sp.maDanhMuc}</td>
                    <td class="text-center">
                        <img src="${pageContext.request.contextPath}/${sp.hinhAnh}"
                            width="60" height="60" class="img-thumbnail"
                            onerror="this.src='${pageContext.request.contextPath}/assets/image/no-image.png'">

                    </td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/admin/sanpham/edit?ma=${sp.maSanPham}" class="btn btn-sm btn-primary"><i class="fas fa-edit"></i></a>
                        <a href="${pageContext.request.contextPath}/admin/sanpham/delete?ma=${sp.maSanPham}" onclick="return confirm('Xóa sản phẩm này?')" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></a>
                        <a href="${pageContext.request.contextPath}/admin/sanpham/detail?ma=${sp.maSanPham}" class="btn btn-sm btn-info text-white"><i class="fas fa-eye"></i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
