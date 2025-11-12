<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${action == 'update' ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm mới'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">
<div class="container py-4">
    <h3 class="mb-4 text-primary">
        <i class="fas fa-${action == 'update' ? 'edit' : 'plus'}"></i>
        ${action == 'update' ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm mới'}
    </h3>

    <form action="${pageContext.request.contextPath}/admin/sanpham/${action}" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label class="form-label">Mã sản phẩm</label>
            <input type="text" name="maSanPham" class="form-control" value="${sp.maSanPham}" ${action == 'update' ? 'readonly' : ''} required>
        </div>

        <div class="mb-3">
            <label class="form-label">Tên sản phẩm</label>
            <input type="text" name="ten" class="form-control" value="${sp.ten}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Giá</label>
            <input type="number" name="gia" class="form-control" value="${sp.gia}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Số lượng tồn</label>
            <input type="number" name="soLuongTon" class="form-control" value="${sp.soLuongTon}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Danh mục</label>
            <select name="maDanhMuc" class="form-select" required>
                <option value="">-- Chọn danh mục --</option>
                <c:forEach var="dm" items="${danhMucs}">
                    <option value="${dm.maDanhMuc}" ${dm.maDanhMuc == sp.maDanhMuc ? 'selected' : ''}>${dm.tenDanhMuc}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Nhà cung cấp</label>
            <select name="maNCC" class="form-select">
                <option value="">-- Chọn nhà cung cấp --</option>
                <c:forEach var="ncc" items="${nhaCungCaps}">
                    <option value="${ncc.maNCC}" ${ncc.maNCC == sp.maNCC ? 'selected' : ''}>${ncc.tenNCC}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Ảnh sản phẩm</label>
            <input type="file" name="fileImage" class="form-control" accept="image/*">
            <c:if test="${not empty sp.hinhAnh}">
                <img src="${pageContext.request.contextPath}/${sp.hinhAnh}" class="img-thumbnail mt-2" width="120"
                     onerror="this.src='${pageContext.request.contextPath}/assets/image/no-image.png'">
            </c:if>
        </div>

        <div class="mb-3">
            <label class="form-label">Mô tả</label>
            <textarea name="moTa" class="form-control" rows="3">${sp.moTa}</textarea>
        </div>

        <div class="text-end">
            <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu</button>
            <a href="${pageContext.request.contextPath}/admin/sanpham" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Quay lại
            </a>
        </div>
    </form>
</div>
</body>
</html>
