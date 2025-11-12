<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Đảm bảo sử dụng URI JSTL mới nhất (Jakarta EE 10) --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />

<c:set var="currentLang" value="${not empty sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'] ? sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'] : 'vi'}" />
<html lang="${currentLang}">
<head>
    <meta charset="UTF-8">
    <title>Thông tin cá nhân | <fmt:message key="site.title" /></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login_style.css">
    
    <style>
        /* CSS tùy chỉnh cho trang profile */
        .profile-container {
            display: flex;
            flex-wrap: wrap; /* Cho phép xuống dòng trên di động */
            gap: 30px;
            max-width: 1100px;
            margin: 30px auto;
            padding: 20px;
        }
        .profile-box {
            background-color: var(--bg-white);
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            flex: 1; /* Hai cột bằng nhau */
            min-width: 320px; /* Độ rộng tối thiểu trước khi xuống dòng */
        }
        .profile-box h2 {
            text-align: left; /* Ghi đè h2 chung */
            font-size: 1.8rem;
            color: var(--text-dark);
            margin-bottom: 25px;
            border-bottom: 2px solid var(--primary-color);
            padding-bottom: 10px;
        }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; font-weight: 500; margin-bottom: 5px; font-size: 0.9rem; }
        .form-group input {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 1rem;
            box-sizing: border-box; /* Quan trọng */
        }
        .form-group input[type="date"] { color: var(--text-dark); }
        .form-group input:disabled {
            background-color: #f5f5f5;
            color: #777;
        }
        .btn-primary, .btn-secondary { 
            width: 100%; 
            padding: 12px; 
            font-size: 1.1rem;
            box-sizing: border-box; /* Quan trọng */
        }
        .btn-secondary {
            background-color: var(--bg-light);
            color: var(--text-dark) !important;
            border-color: #ddd;
        }
        .btn-secondary:hover {
            background-color: #e0e0e0;
            border-color: #ccc;
        }
        
        /* CSS cho thông báo */
        .message {
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-weight: 500;
            font-size: 0.95rem;
            border: 1px solid transparent;
        }
        .message.success { 
            background-color: #d4edda; 
            color: #155724; 
            border-color: #c3e6cb;
        }
        .message.error { 
            background-color: #f8d7da; 
            color: #721c24; 
            border-color: #f5c6cb;
        }
    </style>
</head>
<body>

    <jsp:include page="/common/header.jsp" />

    <main>
        <div class="profile-container">
            
            <div class="profile-box">
                <h2>Thông tin cá nhân</h2>

                <c:if test="${not empty sessionScope.profileMessage}">
                    <p class="message ${sessionScope.profileMessageType}">${sessionScope.profileMessage}</p>
                    <c:remove var="profileMessage" scope="session"/>
                    <c:remove var="profileMessageType" scope="session"/>
                </c:if>

                <form action="profile" method="POST">
                    <input type="hidden" name="maNguoiDung" value="${profile.maNguoiDung}">
                    
                    <div class="form-group">
                        <label for="email">Email (Không thể thay đổi)</label>
                        <input type="email" id="email" value="${profile.email}" disabled>
                    </div>
                    
                    <div class="form-group">
                        <label for="hoTen">Họ và Tên</label>
                        <input type="text" id="hoTen" name="hoTen" value="${profile.hoTen}" placeholder="Nhập họ và tên của bạn">
                    </div>
                    
                    <%-- (ĐÃ XÓA) Form Group Ngày sinh --%>
                    
                    <div class="form-group">
                        <label for="soDienThoai">Số điện thoại</label>
                        <input type="tel" id="soDienThoai" name="soDienThoai" value="${profile.soDienThoai}" placeholder="Nhập số điện thoại">
                    </div>
                    
                    <div class="form-group">
                        <label for="diaChi">Địa chỉ</label>
                        <input type="text" id="diaChi" name="diaChi" value="${profile.diaChi}" placeholder="Nhập địa chỉ của bạn">
                    </div>
                    
                    <button type="submit" class="btn-primary">Cập nhật thông tin</button>
                </form>
            </div>

            <div class="profile-box">
                <h2>Thay đổi mật khẩu</h2>

                <c:if test="${not empty sessionScope.passwordMessage}">
                    <p class="message ${sessionScope.passwordMessageType}">${sessionScope.passwordMessage}</p>
                    <c:remove var="passwordMessage" scope="session"/>
                    <c:remove var="passwordMessageType" scope="session"/>
                </c:if>

                <form action="change-password" method="POST" style="border-bottom: 1px solid #eee; padding-bottom: 20px; margin-bottom: 20px;">
                    <input type="hidden" name="action" value="request_code">
                    <div class="form-group">
                        <label for="oldPassword">Nhập mật khẩu cũ</label>
                        <input type="password" id="oldPassword" name="oldPassword" required>
                    </div>
                    <button type="submit" class="btn-secondary">Gửi mã xác nhận qua Email</button>
                </form>

                <form action="change-password" method="POST">
                    <input type="hidden" name="action" value="change_password">
                    <div class="form-group">
                        <label for="verificationCode">Mã xác nhận (Từ Email)</label>
                        <input type="text" id="verificationCode" name="verificationCode" placeholder="Nhập mã 6 số" required>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">Mật khẩu mới</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Xác nhận mật khẩu mới</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required>
                    </div>
                    <button type="submit" class="btn-primary">Thay đổi mật khẩu</button>
                </form>
                
            </div>
        </div>
    </main>

    <jsp:include page="/common/footer.jsp" />

</body>
</html>