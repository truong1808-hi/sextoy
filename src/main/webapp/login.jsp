<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- (SỬA LỖI) Cập nhật URI cho JSTL Core và FMT để tương thích Jakarta EE --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setBundle basename="messages" />

<c:set var="currentLang" value="${not empty sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'] ? sessionScope['jakarta.servlet.jsp.jstl.fmt.locale.session'] : 'vi'}" />

<!DOCTYPE html>
<html lang="${currentLang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="account.login" /> |
<fmt:message key="site.title" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login_style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            display: flex;
flex-direction: column;
            min-height: 100vh;
        }
        main {
            flex-grow: 1;
display: flex; /* Thêm để căn giữa auth-container */
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
            <div class="auth-switch">
                <a href="#" id="showLogin" class="active"><fmt:message key="account.login" /></a>
            
    <a href="#" id="showRegister"><fmt:message key="account.register" /></a>
            </div>

            <div id="loginSection" class="auth-section active">
                <h2><fmt:message key="account.login" /></h2>
                <form class="auth-form" action="login" method="POST">
          
          <div class="form-group">
                        <label for="login-email"><fmt:message key="form.email" /></label>
                        <input type="email" id="login-email" name="login-email" placeholder="<fmt:message key="form.email_placeholder" />" required>
                    </div>
           
         <div class="form-group">
                        <label for="login-password"><fmt:message key="form.password" /></label>
                        <input type="password" id="login-password" name="login-password" placeholder="<fmt:message key="form.password_placeholder" />" required>
                    </div>

            
        <c:if test="${not empty errorMessage}">
                        <p class="auth-message error">${errorMessage}</p>
                    </c:if>
                    
                    <c:if test="${not empty 
sessionScope.registerMessage}">
                        <p class="auth-message ${sessionScope.registerMessageType}">${sessionScope.registerMessage}</p>
                        <c:remove var="registerMessage" scope="session"/>
                        <c:remove var="registerMessageType" scope="session"/>
                    </c:if>

  
                  <button type="submit" class="btn-primary"><fmt:message key="account.login_button" /></button>
                </form>
                <div class="auth-links">
                    <a href="#" id="showForgotPassword"><fmt:message key="account.forgot_password" /></a>
                </div>
     
       </div>

            <div id="registerSection" class="auth-section">
                <h2><fmt:message key="account.register" /></h2>
                <form class="auth-form" action="register" method="POST">
                    <div class="form-group">
   
                     <label for="register-email"><fmt:message key="form.email" /></label>
                        <input type="email" id="register-email" name="register-email" placeholder="<fmt:message key="form.email_placeholder" />" required>
                    </div>
                    <div class="form-group">
    
                    <label for="register-password"><fmt:message key="form.password" /></label>
                        <input type="password" id="register-password" name="register-password" placeholder="<fmt:message key="form.password_placeholder" />" required>
                    </div>
                    <div class="form-group">
     
                   <label for="register-confirm-password"><fmt:message key="form.confirm_password" /></label>
                        <input type="password" id="register-confirm-password" name="register-confirm-password" placeholder="<fmt:message key="form.confirm_password_placeholder" />" required>
                    </div>
                    <%-- (SỬA LỖI) Đọc thông báo từ 
sessionScope để nhất quán với Servlet --%>
                    <c:if test="${not empty sessionScope.registerMessage}">
                        <p class="auth-message ${sessionScope.registerMessageType}">${sessionScope.registerMessage}</p>
                        <c:remove var="registerMessage" scope="session"/>
                   
     <c:remove var="registerMessageType" scope="session"/>
                    </c:if>
                    <button type="submit" class="btn-primary"><fmt:message key="account.register_button" /></button>
                </form>
            </div>

                      <div id="forgotPasswordSection" class="auth-section">
                <h2><fmt:message key="account.forgot_password" /></h2>
                <form class="auth-form" action="forgot-password" method="POST">
                    <div class="form-group">
                        <label for="forgot-email"><fmt:message key="form.email" /></label>
   
                     <input type="email" id="forgot-email" name="forgot-email" placeholder="<fmt:message key="form.email_placeholder" />" required>
                    </div>
                    <%-- (SỬA LỖI) Hiển thị và xóa thông báo từ session để tránh lặp --%>
                  
  <c:if test="${not empty sessionScope.forgotPasswordMessage}">
                        <p class="auth-message ${sessionScope.forgotPasswordMessageType}">${sessionScope.forgotPasswordMessage}</p>
                        <c:remove var="forgotPasswordMessage" scope="session"/>
                        <c:remove var="forgotPasswordMessageType" scope="session"/>
                 
   </c:if>
                    <button type="submit" class="btn-primary"><fmt:message key="account.reset_password_button" /></button>
                </form>
                <div class="auth-links">
                    <a href="#" id="backToLogin"><i class="fas fa-arrow-left"></i> <fmt:message key="account.login" /></a>
             
   </div>
            </div>
        </div>
    </div>
</main>

<footer class="main-footer">
    <div class="container">
        <p>&copy;
2025 <fmt:message key="site.title" /> | <fmt:message key="site.description" /></p>
    </div>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/login.js"></script>
</body>
</html>