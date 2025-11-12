document.addEventListener('DOMContentLoaded', function() {
    const showLoginBtn = document.getElementById('showLogin');
    const showRegisterBtn = document.getElementById('showRegister');
    const showForgotPasswordBtn = document.getElementById('showForgotPassword');
    const backToLoginBtn = document.getElementById('backToLogin');

    const loginSection = document.getElementById('loginSection');
    const registerSection = document.getElementById('registerSection');
    const forgotPasswordSection = document.getElementById('forgotPasswordSection');

    function showSection(sectionToShow) {
        // Ẩn tất cả các section
        loginSection.classList.remove('active');
        registerSection.classList.remove('active');
        forgotPasswordSection.classList.remove('active');

        // Bỏ active tất cả các nút switch
        showLoginBtn.classList.remove('active');
        showRegisterBtn.classList.remove('active');

        // Hiển thị section được chọn
        sectionToShow.classList.add('active');
    }

    showLoginBtn.addEventListener('click', function(e) {
        e.preventDefault();
        showSection(loginSection);
        this.classList.add('active');
    });

    showRegisterBtn.addEventListener('click', function(e) {
        e.preventDefault();
        showSection(registerSection);
        this.classList.add('active');
    });

    showForgotPasswordBtn.addEventListener('click', function(e) {
        e.preventDefault();
        showSection(forgotPasswordSection);
    });

    backToLoginBtn.addEventListener('click', function(e) {
        e.preventDefault();
        showSection(loginSection);
        showLoginBtn.classList.add('active');
    });

    // Logic để hiển thị tab đăng ký nếu có lỗi đăng ký
    if (document.querySelector('#registerSection p[style*="color"]')) {
        showRegisterBtn.click();
    }
});