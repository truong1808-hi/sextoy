<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.toystore.model.DanhMuc" %>
<html>
<head>
    <title>Cập nhật danh mục</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<%@ page import="com.toystore.model.DanhMuc" %>
<%
    DanhMuc dm = (DanhMuc) request.getAttribute("danhmuc");
    boolean isEdit = dm != null;
%>

<h3><%= isEdit ? "✏ Cập nhật danh mục" : "➕ Thêm danh mục mới" %></h3>

<form action="danhmuc" method="post">
    <input type="hidden" name="action" value="<%= isEdit ? "update" : "insert" %>">

    <div class="mb-3">
        <label>Mã danh mục</label>
        <input type="text" class="form-control" name="maDanhMuc"
               value="<%= isEdit ? dm.getMaDanhMuc() : "" %>"
               <%= isEdit ? "readonly" : "" %>>
    </div>

    <div class="mb-3">
        <label>Tên danh mục</label>
        <input type="text" class="form-control" name="tenDanhMuc"
               value="<%= isEdit ? dm.getTenDanhMuc() : "" %>">
    </div>

    <div class="mb-3">
        <label>Mô tả</label>
        <textarea class="form-control" name="moTa"><%= isEdit ? dm.getMoTa() : "" %></textarea>
    </div>

    <button type="submit" class="btn btn-success">💾 Lưu thay đổi</button>
    <a href="danhmuc" class="btn btn-secondary">↩ Quay lại</a>
</form>


</body>
</html>
