<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - ToyStore</title>

    <!-- Bootstrap + Icon -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">

    <style>
        /* Toàn bộ khung */
        body {
            margin: 0;
            background: #f4f6f9;
            font-family: "Segoe UI", sans-serif;
            display: flex;
            height: 100vh;
            overflow: hidden;
        }

        /* Sidebar trái */
        .sidebar {
            width: 250px;
            background: #0d6efd;
            color: white;
            display: flex;
            flex-direction: column;
            padding: 20px 0;
            box-shadow: 3px 0 10px rgba(0, 0, 0, 0.2);
        }

        .sidebar h2 {
            text-align: center;
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 25px;
            color: #fff;
            letter-spacing: 1px;
        }

        .sidebar a {
            display: block;
            color: #e0e7ff;
            text-decoration: none;
            padding: 12px 25px;
            transition: 0.3s;
            font-weight: 500;
        }

        .sidebar a:hover, .sidebar a.active {
            background: #1e40af;
            color: #fff;
            padding-left: 30px;
        }

        .sidebar a i {
            margin-right: 10px;
        }

        /* Khu vực nội dung */
        .main-content {
            flex: 1;
            display: flex;
            flex-direction: column;
            height: 100vh;
        }

        /* Thanh top */
        .topbar {
            background: #fff;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .topbar h3 {
            font-weight: 700;
            color: #0d6efd;
            margin: 0;
        }

        .btn-logout {
            border: 1px solid #dc3545;
            color: #dc3545;
            font-weight: 500;
            border-radius: 6px;
            padding: 5px 12px;
            transition: 0.3s;
        }

        .btn-logout:hover {
            background: #dc3545;
            color: #fff;
        }

        /* Nội dung chính */
        .content {
            padding: 25px 40px;
            overflow-y: auto;
        }

        .card {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.05);
            padding: 25px;
            margin-bottom: 20px;
        }

        .card h4 {
            font-weight: 600;
            color: #333;
            margin-bottom: 10px;
        }

        footer {
            text-align: center;
            padding: 10px;
            background: #f1f5f9;
            font-size: 14px;
            color: #64748b;
        }
    </style>
</head>

<body>
    <!-- Sidebar -->
    <div class="sidebar">
        <h2><i class="fa-solid fa-store"></i> TOYSTORE</h2>
        <a href="/toystore/admin/dashboard" class="active"><i class="fa-solid fa-chart-line"></i> Dashboard</a>
        <a href="/toystore/admin/danhmuc"><i class="fa-solid fa-list"></i> Danh mục</a>
        <a href="/toystore/admin/sanpham"><i class="fa-solid fa-box"></i> Sản phẩm</a>
        <a href="/toystore/admin/nhacungcap"><i class="fa-solid fa-truck"></i> Nhà cung cấp</a>
        <a href="/toystore/admin/nguoidung"><i class="fa-solid fa-user"></i> Người dùng</a>
        <a href="/toystore/admin/donhang"><i class="fa-solid fa-receipt"></i> Đơn hàng</a>
        <a href="/toystore/admin/thanhtoan"><i class="fa-solid fa-credit-card"></i> Thanh toán</a>
        <a href="/toystore/admin/khuyenmai"><i class="fa-solid fa-gift"></i> Khuyến mãi</a>
        <a href="/toystore/admin/thongke"><i class="fa-solid fa-chart-column"></i> Thống kê</a>
    </div>

    <!-- Nội dung chính -->
    <div class="main-content">
        <div class="topbar">
            <h3>TOYSTORE Admin</h3>
            <a href="/toystore/logout" class="btn btn-logout">
                <i class="fa-solid fa-right-from-bracket"></i> Đăng xuất
            </a>
        </div>

        <div class="content">
            <div class="card">
                <h4>👋 Chào mừng bạn quay lại trang quản trị!</h4>
                <p>Hãy chọn một mục trong thanh menu bên trái để bắt đầu quản lý cửa hàng.</p>
            </div>

            <div class="card">
                <h4>📦 Thống kê nhanh hôm nay</h4>
                <ul>
                    <li>🧾 Tổng đơn hàng: <strong>120</strong></li>
                    <li>💸 Doanh thu: <strong>45,000,000 VND</strong></li>
                    <li>👥 Khách hàng mới: <strong>8</strong></li>
                </ul>
            </div>
        </div>

        <footer>
            © 2025 TOYSTORE Admin Dashboard | Designed by GPT
        </footer>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
