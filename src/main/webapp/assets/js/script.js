document.addEventListener('DOMContentLoaded', function() {
    
    // --- KHAI BÁO CÁC PHẦN TỬ CẦN TƯƠNG TÁC ---
    const contactWidget = document.getElementById('contactWidget');
    const contactToggleBtn = document.getElementById('contactToggleBtn');
    const cartToggle = document.getElementById('cartToggle');
    const miniCart = document.getElementById('mini-cart');
    const modalOverlay = document.getElementById('languageModal'); 
    const openModalBtn = document.getElementById('open-lang-modal');
    
    // Phần tử Ngôn ngữ
    const langRadioButtons = document.querySelectorAll('input[name="language-selector"]'); 
    const langFlag = document.querySelector('.flag-icon-header');
    
    // Phần tử Menu & Bộ lọc
    const mainNavDropdowns = document.querySelectorAll('.main-nav .dropdown'); 

    // Phần tử Bộ lọc Giá
    const minPriceInput = document.getElementById('min-price-filter');
    const maxPriceInput = document.getElementById('max-price-filter');

    
    // ===========================================
    // === HÀM ĐÓNG TẤT CẢ TƯƠNG TÁC ===
    // ===========================================
    function closeAllInteractiveElements(excludeElement = null) {
        // Đóng Mini-Cart
        if (miniCart && excludeElement !== miniCart && excludeElement !== cartToggle) {
            miniCart.classList.remove('active');
        }
        // Đóng Contact Widget
        if (contactWidget && excludeElement !== contactWidget && excludeElement !== contactToggleBtn) {
            contactWidget.classList.remove('active');
        }
        // Đóng Modal Ngôn ngữ
        if (modalOverlay && excludeElement !== modalOverlay) {
            modalOverlay.classList.remove('active');
        }
        // Đóng Dropdown Menu chính
        mainNavDropdowns.forEach(d => {
            if (d !== excludeElement) {
                d.classList.remove('open'); 
            }
        });
        
        // Logic đóng Filter Sidebar (nếu được sử dụng)
        const filterOverlay = document.getElementById('filterOverlay'); 
        if (filterOverlay && excludeElement !== filterOverlay) {
            filterOverlay.classList.remove('active');
            document.body.style.overflow = '';
        }
    }


    // ===========================================
    // === 1. LOGIC NÚT LIÊN HỆ NỔI ===
    // ===========================================
    if (contactToggleBtn && contactWidget) {
        contactToggleBtn.addEventListener('click', function(e) {
            e.preventDefault();
            closeAllInteractiveElements(contactWidget);
            contactWidget.classList.toggle('active');
        });

        document.addEventListener('click', function(e) {
            const isClickInsideContact = contactWidget.contains(e.target) || contactToggleBtn.contains(e.target);
            
            if (contactWidget.classList.contains('active') && !isClickInsideContact) {
                contactWidget.classList.remove('active');
            }
        });
    }
    
    // ===========================================
    // === 2. LOGIC MODAL NGÔN NGỮ (ĐÃ SỬA) ===
    // ===========================================
    
    // Mở Modal Ngôn ngữ
    if (openModalBtn && modalOverlay) {
        openModalBtn.addEventListener('click', function(e) {
            e.preventDefault();
            closeAllInteractiveElements(modalOverlay);
            modalOverlay.classList.add('active');
        });

        // Đóng modal khi click ra ngoài lớp phủ
        modalOverlay.addEventListener('click', function(e) {
            if (e.target === modalOverlay) {
                modalOverlay.classList.remove('active');
            }
        });

        // (ĐÃ SỬA) Xử lý chuyển ngôn ngữ
        langRadioButtons.forEach(radio => {
            radio.addEventListener('change', function() {
                const targetLangCode = this.value;
                
                // Lấy URL hiện tại
                let currentUrl = new URL(window.location.href);
                
                // Đặt tham số 'lang' mới
                currentUrl.searchParams.set("lang", targetLangCode);
                
                // Tải lại trang với URL mới
                window.location.href = currentUrl.toString();
            });
        });
    }

    // ===========================================
    // === 3. LOGIC MINI-CART ===
    // ===========================================
    if (cartToggle && miniCart) {
        cartToggle.addEventListener('click', function(e) {
            e.preventDefault(); 
            closeAllInteractiveElements(miniCart);
            miniCart.classList.toggle('active');
        });

        document.addEventListener('click', function(e) {
            const isClickInside = miniCart.contains(e.target) || cartToggle.contains(e.target);
            
            if (miniCart.classList.contains('active') && !isClickInside) {
                miniCart.classList.remove('active');
            }
        });
    }  

    // ===========================================
    // === 4. LOGIC DROPDOWN MENU CHÍNH ===
    // ===========================================
    mainNavDropdowns.forEach(dropdown => {
        const toggleLink = dropdown.querySelector('a');
        
        toggleLink.addEventListener('click', function(e) {
            // Chỉ chặn link mặc định trên màn hình nhỏ để mở dropdown
            if (window.innerWidth < 1024) { 
                e.preventDefault();
                closeAllInteractiveElements(dropdown);
                dropdown.classList.toggle('open');
            }
        });
    });
    
    // ===========================================
    // === 5. LOGIC BỘ LỌC GIÁ (MỚI) ===
    // ===========================================
    if (minPriceInput && maxPriceInput) {
        
        const step = 10000;

        const ensureMaxGreaterThanMin = (event) => {
            let minVal = parseInt(minPriceInput.value) || 0;
            let maxVal = parseInt(maxPriceInput.value) || 0;

            if (event.target === minPriceInput) {
                if (minVal > maxVal && maxVal !== 0) {
                    maxPriceInput.value = minVal;
                }
            } else if (event.target === maxPriceInput) {
                if (maxVal < minVal) {
                    minPriceInput.value = maxVal;
                }
            }
            
            if (event.type === 'change') {
                if (minPriceInput.value < 0) minPriceInput.value = 0;
                if (maxPriceInput.value < 0) maxPriceInput.value = 0;
                
                let currentMin = parseInt(minPriceInput.value);
                minPriceInput.value = Math.round(currentMin / step) * step;
                
                let currentMax = parseInt(maxPriceInput.value);
                maxPriceInput.value = Math.round(currentMax / step) * step;
            }
        };

        minPriceInput.addEventListener('change', ensureMaxGreaterThanMin);
        maxPriceInput.addEventListener('change', ensureMaxGreaterThanMin);
        
        const applyFilterButton = document.querySelector('.price-range-input .btn-apply');
        if (applyFilterButton) {
            applyFilterButton.addEventListener('click', () => {
                const finalMin = parseInt(minPriceInput.value) || 0;
                const finalMax = parseInt(maxPriceInput.value) || Infinity;
                
                console.log(`Đã áp dụng lọc giá tùy chỉnh: Từ ${finalMin} đến ${finalMax}`);
                // Thêm code để gửi yêu cầu lọc sản phẩm ở đây
            });
        }
    }
});