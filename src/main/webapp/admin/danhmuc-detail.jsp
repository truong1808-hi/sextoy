<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Chi tiết danh mục</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" />
</head>
<body class="p-4">


<h3>📦 Chi tiết danh mục: ${danhmuc.tenDanhMuc}</h3>
<p><b>Mã:</b> ${danhmuc.maDanhMuc}</p>
<p><b>Mô tả:</b> ${danhmuc.moTa}</p>

<h5>🧸 Sản phẩm thuộc danh mục này:</h5>
<table class="table table-bordered">
    <thead>
        <tr>
            <th>Mã</th>
            <th>Tên</th>
            <th>Giá</th>
            <th>Tồn</th>
            <th>Hình</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="sp" items="${sanPhams}">
            <tr>
                <td>${sp.maSanPham}</td>
                <td>${sp.ten}</td>
                <td>${sp.gia}</td>
                <td>${sp.soLuongTon}</td>
                <td>
                    <img src="${pageContext.request.contextPath}/${sp.hinhAnh}" 
                         width="70" height="70">
                </td>
            </tr>
        </c:forEach>

        <c:if test="${empty sanPhams}">
            <tr><td colspan="5" class="text-center text-muted">Không có sản phẩm nào trong danh mục này.</td></tr>
        </c:if>
    </tbody>
</table>

<a href="danhmuc" class="btn btn-secondary">↩ Quay lại</a>


<a href="danhmuc" class="btn btn-secondary">↩ Quay lại</a>
</body>
</html>
