<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý danh mục</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" />
</head>
<body class="p-4">

<h2>📁 Danh sách danh mục</h2>

<form class="d-flex mb-3" method="get">
    <input class="form-control me-2" type="text" name="q" value="${keyword}" placeholder="Tìm theo mã hoặc tên...">
    <button class="btn btn-primary">🔍 Tìm</button>
    <a href="danhmuc?action=new" class="btn btn-success ms-2">+ Thêm mới</a>
</form>

<table class="table table-bordered table-hover">
    <thead class="table-light">
        <tr>
            <th>Mã danh mục</th>
            <th>Tên danh mục</th>
            <th>Mô tả</th>
            <th>Thao tác</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="dm" items="${list}">
        <tr>
            <td>${dm.maDanhMuc}</td>
            <td>${dm.tenDanhMuc}</td>
            <td>${dm.moTa}</td>
            <td>
                <a href="danhmuc?action=detail&id=${dm.maDanhMuc}" class="btn btn-info btn-sm">👁 Chi tiết</a>
                <a href="danhmuc?action=edit&id=${dm.maDanhMuc}" class="btn btn-warning btn-sm">✏️ Sửa</a>
                <a href="danhmuc?action=delete&id=${dm.maDanhMuc}" class="btn btn-danger btn-sm"
                   onclick="return confirm('Bạn có chắc muốn xóa danh mục này?');">🗑 Xóa</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
