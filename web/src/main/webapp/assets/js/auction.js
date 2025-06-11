let ws;
let reconnectInterval = 1000;

function connectWebSocket() {
    ws = new WebSocket("ws://localhost:8080/plexusbid/bidsocket");

    ws.onopen = () => {
        console.log("WebSocket connected");
        reconnectInterval = 1000;
    };

    ws.onclose = (e) => {
        console.log(`WebSocket closed. Reconnecting in ${reconnectInterval / 1000}s...`);
        setTimeout(connectWebSocket, reconnectInterval);
        reconnectInterval = Math.min(reconnectInterval * 2, 30000); // Cap at 30s
    };

    ws.onerror = (error) => {
        console.error("WebSocket Error:", error);
    };

    ws.onmessage = function (event) {
        const data = JSON.parse(event.data);

        if (data.type === "newBid") {
            handleNewBid(data);
        }

        if (data.type === "newItem") {
            handleNewItem(data);
        }
    };
}

function sanitizeHTML(str) {
    const div = document.createElement('div');
    div.textContent = str;
    return div.innerHTML;
}

function handleNewBid(data) {
    // Update bid table
    const tbody = document.querySelector("#bids-container table tbody");
    if (!tbody) return;

    // Prevent duplicate rows
    if ([...tbody.children].some(row =>
        row.children[0].textContent == data.itemId &&
        row.children[1].textContent == data.bidderName &&
        row.children[2].textContent == data.email &&
        row.children[3].textContent == `$${Number(data.amount).toFixed(2)}`
    )) {
        return;
    }

    // Remove "No bids yet." row if it exists
    const noBidsRow = tbody.querySelector("tr td[colspan='4']");
    if (noBidsRow) {
        noBidsRow.parentElement.remove();
    }

    // Add new bid row
    const newRow = document.createElement("tr");
    newRow.innerHTML = `
        <td>${sanitizeHTML(data.itemId)}</td>
        <td>${sanitizeHTML(data.bidderName)}</td>
        <td>${sanitizeHTML(data.email)}</td>
        <td>$${Number(data.amount).toFixed(2)}</td>
    `;
    tbody.prepend(newRow);

    // Update current bid display
    const currentBidElement = document.querySelector(`#current-bid-${data.itemId}`);
    if (currentBidElement) {
        currentBidElement.textContent = `$${Number(data.amount).toFixed(2)}`;
    }
}

function handleNewItem(data) {
    const container = document.getElementById("items-container");
    if (!container) return;
    const newCard = document.createElement("div");
    newCard.className = "card mb-3 p-2";
    newCard.innerHTML = `
        <div class="card-body">
            <p class="Inter-Bold mb-1"><strong>${sanitizeHTML(data.name)}</strong></p>
            <p class="Inter-Bold mb-1">${sanitizeHTML(data.description)}</p>
            <p class="Inter-Bold mb-1">Current Bid: <strong id="current-bid-${data.id}">$${Number(data.startingPrice).toFixed(2)}</strong></p>
            <p class="Inter-Bold mb-1">
                Expires At: <span class="expires-at" data-expires="${sanitizeHTML(data.expiresAt)}" data-itemid="${sanitizeHTML(data.id)}"></span>
            </p>
            <div class="col-12">
                <div class="row bid-form" id="bid-form-${sanitizeHTML(data.id)}">
                    <form action="place-bid" method="post">
                        <input type="hidden" name="itemId" value="${sanitizeHTML(data.id)}"/>
                        <div class="col-12">
                            <input type="number" name="amount" step="0.01" required
                                   class="input-border form-control" placeholder="Enter bid amount">
                        </div>
                        <div class="col-12 mt-3 mb-0">
                            <button type="submit" class="btn btn-primary btn-block w-100">Place Bid</button>
                        </div>
                    </form>
                </div>
                <strong id="closed-msg-${sanitizeHTML(data.id)}" class="text-danger mt-3 d-block d-none">Bidding Closed ❌</strong>
            </div>
        </div>
    `;
    container.prepend(newCard);
}

connectWebSocket();

// Client-side bid validation (optional, enhances UX)
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('form[action="place-bid"]').forEach(form => {
        form.addEventListener('submit', (e) => {
            const amountInput = form.querySelector('input[name="amount"]');
            const currentBidElement = form.closest('.card').querySelector('strong[id^="current-bid-"]');
            if (currentBidElement) {
                const currentBid = parseFloat(currentBidElement.textContent.replace('$', ''));
                if (parseFloat(amountInput.value) <= currentBid) {
                    e.preventDefault();
                    alert('Bid must be higher than current amount!');
                }
            }
        });
    });
});
