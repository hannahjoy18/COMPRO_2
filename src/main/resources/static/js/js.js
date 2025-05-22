window.addEventListener('load', function () {
    document.querySelectorAll('.menu li').forEach(menu => {
        menu.addEventListener('click', () => {
            location.href = menu.getAttribute('data-url');
        });
    });
});