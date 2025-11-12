<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>📦 Quản lý đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h3 class="mb-3">📦 Danh sách đơn hàng</h3>

<!-- 🔔 Thông báo -->
<c:if test="${param.msg != null}">
    <div class="alert 
        ${param.msg == 'updated' ? 'alert-success' : 
          (param.msg == 'fail' ? 'alert-danger' : 
          (param.msg == 'notfound' ? 'alert-warning' : 'alert-info'))}">
        ${param.msg == 'updated' ? '✅ Cập nhật đơn hàng thành công!' :
          (param.msg == 'fail' ? '❌ Cập nhật thất bại!' :
          (param.msg == 'notfound' ? '⚠️ Không tìm thấy đơn hàng!' : ''))}
    </div>
</c:if>

<!-- 🔍 Form tìm kiếm & lọc -->
<form method="get" class="row mb-3">
    <div class="col-md-4">
        <input type="text" name="q" value="${q}" class="form-control"
               placeholder="Tìm theo mã, người dùng hoặc sản phẩm...">
    </div>
    <div class="col-md-3">
        <select name="trangThai" class="form-select">
            <option value="">-- Tất cả trạng thái --</option>
            <option value="Đang xử lý" ${"Đang xử lý" == trangThai ? "selected" : ""}>Đang xử lý</option>
            <option value="Đang giao" ${"Đang giao" == trangThai ? "selected" : ""}>Đang giao</option>
            <option value="Đã giao" ${"Đã giao" == trangThai ? "selected" : ""}>Đã giao</option>
        </select>
    </div>
    <div class="col-md-2">
        <button class="btn btn-primary w-100">🔍 Lọc</button>
    </div>
    <div class="col-md-3 text-end">
        <!-- ✅ Đúng đường dẫn: chỉ cần donhang?action -->
        <a href="donhang?action=exportExcel" class="btn btn-success me-2">
            📗 Xuất Excel
        </a>
        <a href="donhang?action=exportPdf" class="btn btn-danger">
            📕 Xuất PDF
        </a>
    </div>
</form>

<!-- 📋 Bảng danh sách -->
<table class="table table-bordered table-hover align-middle">
    <thead class="table-light text-center">
        <tr>
            <th>Mã ĐH</th>
            <th>Mã người dùng</th>
            <th>Sản phẩm</th>
            <th>Tổng tiền</th>
            <th>Trạng thái</th>
            <th>Ngày đặt</th>
            <th width="180">Hành động</th>
        </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty list}">
            <c:forEach var="dh" items="${list}">
                <tr>
                    <td>${dh.maDonHang}</td>
                    <td>${dh.maNguoiDung}</td>
                    <td>${dh.tenSanPham}</td>
                    <td>${dh.tongTien}</td>
                    <td class="text-center">
                        <span class="badge 
                            ${dh.trangThai == 'Đã giao' ? 'bg-success' :
                              (dh.trangThai == 'Đang giao' ? 'bg-info' : 'bg-warning text-dark')}">
                            ${dh.trangThai}
                        </span>
                    </td>
                    <td>${dh.ngayDat}</td>
                    <td class="text-center">
                        <a href="donhang?action=detail&id=${dh.maDonHang}" 
                           class="btn btn-sm btn-info text-white">👁 Xem</a>
                        <a href="donhang?action=edit&id=${dh.maDonHang}" 
                           class="btn btn-sm btn-warning">✏️ Sửa</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="7" class="text-center text-muted">
                    Không có đơn hàng nào phù hợp
                </td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

</body>
</html>
