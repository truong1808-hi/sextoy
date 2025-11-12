<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.toystore.model.ThanhToan" %>

<html>
<head>
    <title>Danh sách thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

<h3>💳 Danh sách thanh toán</h3>

<form class="row g-2 mb-3" method="get" action="thanhtoan">
    <div class="col-md-3">
        <input name="q" value="<%= request.getAttribute("q") != null ? request.getAttribute("q") : "" %>"
               class="form-control" placeholder="🔍 Tìm theo mã TT / đơn hàng / giao dịch...">
    </div>
    <div class="col-md-3">
        <select name="trangThai" class="form-select">
            <option>Tất cả</option>
            <option <%= "Đang chờ".equals(request.getAttribute("trangThai")) ? "selected" : "" %>>Đang chờ</option>
            <option <%= "Hoàn tất".equals(request.getAttribute("trangThai")) ? "selected" : "" %>>Hoàn tất</option>
            <option <%= "Hoàn tiền".equals(request.getAttribute("trangThai")) ? "selected" : "" %>>Hoàn tiền</option>
        </select>
    </div>
    <div class="col-md-3">
        <select name="hinhThuc" class="form-select">
            <option>Tất cả</option>
            <option <%= "COD".equals(request.getAttribute("hinhThuc")) ? "selected" : "" %>>COD</option>
            <option <%= "Momo".equals(request.getAttribute("hinhThuc")) ? "selected" : "" %>>Momo</option>
            <option <%= "Banking".equals(request.getAttribute("hinhThuc")) ? "selected" : "" %>>Banking</option>
        </select>
    </div>
    <div class="col-md-2">
        <button class="btn btn-primary w-100">Lọc</button>
    </div>
</form>

<table class="table table-bordered text-center align-middle">
    <thead class="table-light">
        <tr>
            <th>Mã TT</th>
            <th>Mã đơn hàng</th>
            <th>Hình thức</th>
            <th>Số tiền</th>
            <th>Trạng thái</th>
            <th>Mã giao dịch</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
    <%
        List<ThanhToan> list = (List<ThanhToan>) request.getAttribute("list");
        if (list != null && !list.isEmpty()) {
            for (ThanhToan tt : list) {
    %>
        <tr>
            <td><%= tt.getMaThanhToan() %></td>
            <td><%= tt.getMaDonHang() %></td>
            <td><%= tt.getHinhThuc() %></td>
            <td><%= tt.getSoTien() %></td>
            <td><%= tt.getTrangThai() %></td>
            <td><%= tt.getMaGiaoDich() %></td>
            <td>
                <a href="thanhtoan?action=detail&id=<%= tt.getMaThanhToan() %>" class="btn btn-info btn-sm">Chi tiết</a>
                <a href="thanhtoan?action=edit&id=<%= tt.getMaThanhToan() %>" class="btn btn-warning btn-sm">Sửa</a>
            </td>
        </tr>
    <%
            }
        } else {
    %>
        <tr><td colspan="7" class="text-muted">Không có kết quả</td></tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
