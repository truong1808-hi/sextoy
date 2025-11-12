document.addEventListener('DOMContentLoaded', function () {
    // Slider cho banner chính
    const slides = document.querySelectorAll('.hero-slider .slide');
    const nextBtn = document.getElementById('nextSlide');
    const prevBtn = document.getElementById('prevSlide');
    const dotsContainer = document.getElementById('sliderDots');

    if (slides.length > 0) {
        let currentSlide = 0;
        let slideInterval;

        // Tạo các chấm điều hướng
        slides.forEach((_, index) => {
            const dot = document.createElement('button');
            dot.classList.add('dot');
            if (index === 0) dot.classList.add('active');
            dot.addEventListener('click', () => {
                goToSlide(index);
                resetInterval();
            });
            dotsContainer.appendChild(dot);
        });

        const dots = document.querySelectorAll('.slider-dots .dot');

        function goToSlide(n) {
            slides[currentSlide].classList.remove('active');
            dots[currentSlide].classList.remove('active');
            currentSlide = (n + slides.length) % slides.length;
            slides[currentSlide].classList.add('active');
            dots[currentSlide].classList.add('active');
        }

        function nextSlide() {
            goToSlide(currentSlide + 1);
        }

        function prevSlide() {
            goToSlide(currentSlide - 1);
        }

        function startInterval() {
            slideInterval = setInterval(nextSlide, 5000); // Tự động chuyển ảnh sau 5 giây
        }

        function resetInterval() {
            clearInterval(slideInterval);
            startInterval();
        }

        if (nextBtn && prevBtn) {
            nextBtn.addEventListener('click', () => {
                nextSlide();
                resetInterval();
            });

            prevBtn.addEventListener('click', () => {
                prevSlide();
                resetInterval();
            });
        }

        startInterval();
    }
});