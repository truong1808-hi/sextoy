<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Báo cáo doanh thu & thống kê</title>

    <!-- Bootstrap + Chart.js -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <style>
        body {
            background: #f8fafc;
            font-family: "Segoe UI", sans-serif;
        }
        .dashboard-card {
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 3px 15px rgba(0,0,0,0.1);
            padding: 25px 30px;
            margin: 30px auto;
            max-width: 1300px;
        }
        h2 {
            text-align: center;
            font-weight: 700;
            color: #0d6efd;
            margin-bottom: 15px;
        }
        .total-revenue {
            text-align: right;
            font-weight: bold;
            font-size: 18px;
            color: #f39c12;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        th, td {
            padding: 10px 12px;
            border: 1px solid #dee2e6;
            text-align: center;
        }
        th {
            background-color: #0d6efd;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9fafb;
        }
        .section-title {
            color: #dc3545;
            font-weight: 600;
            margin-top: 30px;
            margin-bottom: 10px;
        }
        .stock-low {
            color: red;
            font-weight: bold;
        }
        .chart-container {
            display: flex;
            justify-content: space-between;
            gap: 20px;
            flex-wrap: wrap;
            margin-top: 25px;
        }
        .chart-container canvas {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            padding: 10px;
            width: 48%;
            height: 350px;
        }
    </style>
</head>

<body>
<div class="dashboard-card">
    <h2>📊 Báo cáo doanh thu & thống kê</h2>
    <div class="total-revenue">💰 Tổng doanh thu: ${tongDoanhThu} VND</div>

    <table>
        <thead>
            <tr>
                <th>Mã TK</th>
                <th>Ngày</th>
                <th>Tổng đơn</th>
                <th>Tổng SP bán</th>
                <th>Doanh thu ngày</th>
                <th>Tồn kho</th>
                <th>SP bán chạy</th>
                <th>Doanh thu tuần</th>
                <th>Doanh thu tháng</th>
                <th>Doanh thu năm</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="tk" items="${listThongKe}">
                <tr>
                    <td>${tk.maThongKe}</td>
                    <td>${tk.ngay}</td>
                    <td>${tk.tongDon}</td>
                    <td>${tk.tongSanPhamBan}</td>
                    <td>${tk.tongDoanhThuNgay}</td>
                    <td>${tk.tonKho}</td>
                    <td>${tk.spBanChay}</td>
                    <td>${tk.tongDoanhThuTuan}</td>
                    <td>${tk.tongDoanhThuThang}</td>
                    <td>${tk.tongDoanhThuNam}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="chart-container">
        <canvas id="barChart"></canvas>
        <canvas id="lineChart"></canvas>
    </div>

    <div class="section-title">⚠️ Sản phẩm sắp hết hàng (SL &lt; 10)</div>
    <table>
        <thead>
            <tr>
                <th>Mã SP</th>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th>Số lượng tồn</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sp" items="${sanPhamHet}">
                <tr>
                    <td>${sp.maSanPham}</td>
                    <td>${sp.ten}</td>
                    <td>${sp.gia}</td>
                    <td class="stock-low">${sp.soLuongTon}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<script>
const labels = [
    <c:forEach var="tk" items="${listThongKe}" varStatus="s">
        "${tk.ngay}"<c:if test="${!s.last}">,</c:if>
    </c:forEach>
];
const data = [
    <c:forEach var="tk" items="${listThongKe}" varStatus="s">
        ${tk.tongDoanhThuNgay}<c:if test="${!s.last}">,</c:if>
    </c:forEach>
];

// Cột
new Chart(document.getElementById('barChart'), {
    type: 'bar',
    data: {
        labels: labels,
        datasets: [{
            label: 'Doanh thu theo ngày (VND)',
            data: data,
            backgroundColor: '#0d6efd',
            borderRadius: 8
        }]
    },
    options: { scales: { y: { beginAtZero: true } } }
});

// Đường
new Chart(document.getElementById('lineChart'), {
    type: 'line',
    data: {
        labels: labels,
        datasets: [{
            label: 'Doanh thu theo tháng (VND)',
            data: data,
            borderColor: '#198754',
            backgroundColor: '#198754',
            tension: 0.3,
            fill: false
        }]
    },
    options: { scales: { y: { beginAtZero: true } } }
});
</script>
</body>
</html>
