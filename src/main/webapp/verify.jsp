<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />

<c:set var="currentLang" value="${not empty sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'] ? sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'] : 'vi'}" />

<!DOCTYPE html>
<html lang="${currentLang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác thực tài khoản | <fmt:message key="site.title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login_style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            display: flex; flex-direction: column; min-height: 100vh;
        }
        main {
            flex-grow: 1; display: flex; 
        }
    </style>
</head>
<body>

<header class="auth-header">
    <div class="container" style="display: flex; justify-content: space-between; align-items: center; height: 60px;">
        <div class="logo">
             <a href="${pageContext.request.contextPath}/home">
                <img src="${pageContext.request.contextPath}/assets/image/logo.png" alt="VT88Store Logo" style="height: 40px;">
                <span class="store-name" style="font-size: 1.5rem;"><fmt:message key="site.title" /></span> 
             </a>
        </div>
        <a href="${pageContext.request.contextPath}/home" class="home-link"><i class="fas fa-home"></i> <fmt:message key="nav.home" /></a>
    </div>
</header>

<main>
    <div class="auth-container">
        <div class="auth-box">
            
            <div id="verifySection" class="auth-section active">
                <h2>Xác thực tài khoản</h2>
                <p style="text-align: center; margin-bottom: 20px; color: var(--text-light);">
                    Một mã 6 chữ số đã được gửi đến 
                    <c:if test="${not empty sessionScope.email_for_verification}">
                        <strong>${sessionScope.email_for_verification}</strong>.
                    </c:if>
                    <c:if test="${empty sessionScope.email_for_verification}">
                        email của bạn.
                    </c:if>
                    <br>Vui lòng kiểm tra và nhập mã vào ô dưới đây.
                </p>

                <form class="auth-form" action="verify" method="POST">
                    <div class="form-group">
                        <label for="verification-code">Mã Xác Thực</label>
                        <input type="text" id="verification-code" name="verification-code" placeholder="Nhập mã 6 chữ số" required maxlength="6"
                               style="text-align: center; font-size: 1.5rem; letter-spacing: 5px;">
                    </div>

                    <%-- Hiển thị thông báo (ví dụ: Mã sai, Gửi lại mã,...) --%>
                    <c:if test="${not empty sessionScope.verifyMessage}">
                        <p class="auth-message ${sessionScope.verifyMessageType}">${sessionScope.verifyMessage}</p>
                        <c:remove var="verifyMessage" scope="session"/>
                        <c:remove var="verifyMessageType" scope="session"/>
                    </c:if>

                    <button type="submit" class="btn-primary">Xác Nhận</button>
                </form>
                <div class="auth-links">
                    <a href="${pageContext.request.contextPath}/login"><i class="fas fa-arrow-left"></i> Quay lại Đăng nhập</a>
                </div>
            </div>
        </div>
    </div>
</main>

<footer class="main-footer">
    <div class="container">
        <p>&copy; 2025 <fmt:message key="site.title" /> | <fmt:message key="site.description" /></p>
    </div>
</footer>

</body>
</html>