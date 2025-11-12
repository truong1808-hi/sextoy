<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? "Cập nhật khuyến mãi" : "Thêm khuyến mãi mới"}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: #f9f9f9; }
        h3 { color: #d63384; }
    </style>
</head>
<body class="container py-4">

<h3 class="mb-4">${isEdit ? "✏️ Cập nhật khuyến mãi" : "➕ Thêm khuyến mãi mới"}</h3>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<form method="post" action="khuyenmai">
    <input type="hidden" name="isEdit" value="${isEdit}"/>

    <div class="row mb-3">
        <div class="col-md-4">
            <label class="form-label">Mã khuyến mãi</label>
            <input type="text" name="maKhuyenMai" class="form-control" 
                   value="${km.maKhuyenMai}" required ${isEdit ? "readonly" : ""}>
        </div>
        <div class="col-md-4">
            <label class="form-label">Mã sản phẩm</label>
            <input type="text" name="maSanPham" class="form-control" 
                   value="${km.maSanPham}" required>
        </div>
        <div class="col-md-4">
            <label class="form-label">% Giảm giá</label>
            <input type="number" step="0.01" min="0" max="100"
                   name="phanTramGiam" class="form-control"
                   value="${km.phanTramGiam}" required>
        </div>
    </div>

    <div class="mb-3">
        <label class="form-label">Tên khuyến mãi</label>
        <input type="text" name="tenKhuyenMai" class="form-control"
               value="${km.tenKhuyenMai}" required>
    </div>

    <div class="mb-3">
        <label class="form-label">Mô tả</label>
        <textarea name="moTa" class="form-control" rows="3">${km.moTa}</textarea>
    </div>

    <div class="row mb-3">
        <div class="col-md-6">
            <label class="form-label">Ngày bắt đầu</label>
            <input type="date" name="ngayBatDau" class="form-control"
                   value="${km.ngayBatDau != null ? km.ngayBatDau.toString().substring(0,10) : ''}" required>
        </div>
        <div class="col-md-6">
            <label class="form-label">Ngày kết thúc</label>
            <input type="date" name="ngayKetThuc" class="form-control"
                   value="${km.ngayKetThuc != null ? km.ngayKetThuc.toString().substring(0,10) : ''}" required>
        </div>
    </div>

    <div class="mb-3">
        <label class="form-label">Trạng thái</label>
        <select name="trangThai" class="form-select">
            <option value="Đang áp dụng" ${km.trangThai=='Đang áp dụng'?'selected':''}>Đang áp dụng</option>
            <option value="Chưa bắt đầu" ${km.trangThai=='Chưa bắt đầu'?'selected':''}>Chưa bắt đầu</option>
            <option value="Đã kết thúc" ${km.trangThai=='Đã kết thúc'?'selected':''}>Đã kết thúc</option>
        </select>
    </div>

    <div class="mt-4">
        <button type="submit" class="btn btn-success">💾 Lưu thay đổi</button>
        <a href="khuyenmai" class="btn btn-secondary">⬅️ Quay lại</a>
    </div>
</form>


</body>
</html>
