<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${action == 'update' ? 'Cập nhật' : 'Thêm'} Nhà Cung Cấp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <h3 class="text-primary mb-3">
        <i class="fa-solid ${action == 'update' ? 'fa-pen-to-square' : 'fa-plus'}"></i>
        ${action == 'update' ? 'Cập nhật' : 'Thêm'} Nhà Cung Cấp
    </h3>

    <form action="nhacungcap" method="post" class="card shadow-sm p-4 bg-white">
        <input type="hidden" name="action" value="${action}"/>

        <div class="mb-3">
            <label class="form-label">Mã NCC</label>
            <input type="text" name="MaNCC" class="form-control"
                   value="${ncc.maNCC}" ${action == 'update' ? 'readonly' : ''} required/>
        </div>

        <div class="mb-3">
            <label class="form-label">Tên Nhà Cung Cấp</label>
            <input type="text" name="TenNCC" class="form-control" value="${ncc.tenNCC}" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Danh mục</label>
            <select name="MaDanhMuc" class="form-select" required>
                <option value="">-- Chọn danh mục --</option>
                    <c:forEach var="dm" items="${danhmucs}">
                <option value="${dm.maDanhMuc}" ${dm.maDanhMuc == ncc.maDanhMuc ? 'selected' : ''}>
                    ${dm.tenDanhMuc}
                </option>
                    </c:forEach>
            </select>
        </div>


        <div class="mb-3">
            <label class="form-label">Địa chỉ</label>
            <input type="text" name="DiaChi" class="form-control" value="${ncc.diaChi}"/>
        </div>

        <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input type="text" name="SoDienThoai" class="form-control" value="${ncc.soDienThoai}"/>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="Email" class="form-control" value="${ncc.email}"/>
        </div>

        <div class="mb-3">
            <label class="form-label">Mô tả</label>
            <textarea name="MoTa" class="form-control" rows="3">${ncc.moTa}</textarea>
        </div>

        <div class="text-end">
            <button type="submit" class="btn btn-primary">
                <i class="fa-solid fa-save"></i> Lưu
            </button>
            <a href="nhacungcap" class="btn btn-secondary">
                <i class="fa-solid fa-arrow-left"></i> Quay lại
            </a>
        </div>
    </form>
</div>
</body>
</html>
