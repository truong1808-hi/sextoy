<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Nhà Cung Cấp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-4">
    <h3 class="text-primary mb-3"><i class="fa-solid fa-truck"></i> Quản lý Nhà Cung Cấp</h3>

    <!-- Bộ lọc danh mục -->
    <form method="get" class="row g-3 mb-3">
        <div class="col-md-4">
            <label class="form-label fw-bold">Lọc theo danh mục</label>
            <select name="maDanhMuc" class="form-select" onchange="this.form.submit()">
                <option value="">-- Tất cả danh mục --</option>
                <c:forEach var="dm" items="${danhmucs}">
                    <option value="${dm.maDanhMuc}" ${dm.maDanhMuc == maDanhMuc ? 'selected' : ''}>
                        ${dm.tenDanhMuc}
                    </option>
                </c:forEach>
            </select>
        </div>
    </form>

    <!-- Nút thêm mới -->
    <div class="mb-3">
        <a href="nhacungcap?action=add" class="btn btn-success">
            <i class="fa-solid fa-plus"></i> Thêm Nhà Cung Cấp
        </a>
    </div>

    <!-- Bảng danh sách -->
    <table class="table table-bordered table-hover align-middle bg-white shadow-sm">
        <thead class="table-primary text-center">
            <tr>
                <th>Mã NCC</th>
                <th>Tên Nhà Cung Cấp</th>
                <th>Địa Chỉ</th>
                <th>SĐT</th>
                <th>Email</th>
                <th>Mô tả</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="ncc" items="${list}">
            <tr>
                <td>${ncc.maNCC}</td>
                <td>${ncc.tenNCC}</td>
                <td>${ncc.diaChi}</td>
                <td>${ncc.soDienThoai}</td>
                <td>${ncc.email}</td>
                <td>${ncc.moTa}</td>
                <td class="text-center">
                    <a href="nhacungcap?action=detail&id=${ncc.maNCC}" class="btn btn-sm btn-info text-white">
                        <i class="fa-solid fa-eye"></i>
                    </a>
                    <a href="nhacungcap?action=edit&id=${ncc.maNCC}" class="btn btn-sm btn-warning">
                        <i class="fa-solid fa-pen-to-square"></i>
                    </a>
                    <a href="nhacungcap?action=delete&id=${ncc.maNCC}" class="btn btn-sm btn-danger"
                       onclick="return confirm('Bạn có chắc muốn xóa nhà cung cấp này?');">
                        <i class="fa-solid fa-trash"></i>
                    </a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty list}">
            <tr><td colspan="7" class="text-center text-muted">Không có dữ liệu</td></tr>
        </c:if>
        </tbody>
    </table>
</div>

</body>
</html>
