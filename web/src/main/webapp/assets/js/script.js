function pad(num) {
    return num < 10 ? "0" + num : num;
}

function updateCountdowns() {
    const now = new Date();
    document.querySelectorAll('.expires-at').forEach(function (span) {
        const expiresAtStr = span.getAttribute('data-expires');
        if (!expiresAtStr) return;
        // Parse the ISO date string
        const expiresAt = new Date(expiresAtStr);
        const diff = expiresAt - now;
        if (diff > 0) {
            const hours = Math.floor(diff / (1000 * 60 * 60));
            const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((diff % (1000 * 60)) / 1000);
            span.textContent = pad(hours) + ":" + pad(minutes) + ":" + pad(seconds) + " left";
        } else {
            span.textContent = "Expired";
            // Optionally, you can add a class or further UI change here
        }
    });
}

// Initial call and update every second
updateCountdowns();
setInterval(updateCountdowns, 1000);

