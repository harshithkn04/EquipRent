const API_URL = "http://localhost:8080";

async function loadEquipment() {

    const search =
        document.getElementById("search").value;

    const category =
        document.getElementById("category").value;

    const owner =
        document.getElementById("owner").value;

    const params = new URLSearchParams();

    if (search) {
        params.append("search", search);
    }

    if (category) {
        params.append("category", category);
    }

    if (owner) {
        params.append("owner", owner);
    }

    const response =
        await fetch(
            `${API_URL}/api/equipment?${params}`
        );

    const equipment =
        await response.json();

    displayEquipment(equipment);
}


function displayEquipment(equipment) {

    const container =
        document.getElementById(
            "equipmentContainer"
        );

    container.innerHTML = "";

    if (equipment.length === 0) {

        container.innerHTML =
            "<p>No equipment found.</p>";

        return;
    }

    equipment.forEach(item => {

        const card =
            document.createElement("div");

        card.className = "card";

        const availability =
            item.availableQuantity > 0
                ? `<span class="available">
                     ${item.availableQuantity} available
                   </span>`
                : `<span class="unavailable">
                     Not available
                   </span>`;

        card.innerHTML = `

            <img
                src="${item.photo}"
                alt="${item.title}"
            >

            <div class="card-content">

                <h3>
                    ${item.title}
                </h3>

                <p>
                    ${item.description}
                </p>

                <p>
                    <strong>Category:</strong>
                    ${item.category}
                </p>

                <p>
                    <strong>Owner:</strong>
                    ${item.ownerName}
                </p>

                <p>
                    ${availability}
                </p>

                <button
                    onclick="viewDetails(${item.id})">
                    View Details
                </button>

                <button
                    onclick="rentEquipment(${item.id})"
                    ${item.availableQuantity === 0
                        ? "disabled"
                        : ""}>
                    Rent Equipment
                </button>

            </div>
        `;

        container.appendChild(card);
    });
}


async function rentEquipment(equipmentId) {

    const user =
        JSON.parse(
            localStorage.getItem("user")
        );

    if (!user) {

        alert("Please login first.");

        window.location.href =
            "login.html";

        return;
    }

    if (user.role !== "RENTER") {

        alert(
            "Only renters can rent equipment."
        );

        return;
    }

    const quantity =
        prompt(
            "Enter quantity to rent:",
            "1"
        );

    if (!quantity) {
        return;
    }

    const body =
        new URLSearchParams();

    body.append(
        "equipmentId",
        equipmentId
    );

    body.append(
        "renterId",
        user.id
    );

    body.append(
        "quantity",
        quantity
    );

    const response =
        await fetch(
            `${API_URL}/api/rent`,
            {
                method: "POST",
                headers: {
                    "Content-Type":
                        "application/x-www-form-urlencoded"
                },
                body: body
            }
        );

    const message =
        await response.text();

    alert(message);

    loadEquipment();
}


function logout() {

    localStorage.removeItem("user");

    window.location.href =
        "login.html";
}


function showLoggedUser() {

    const user =
        JSON.parse(
            localStorage.getItem("user")
        );

    const element =
        document.getElementById(
            "loggedUser"
        );

    if (user && element) {

        element.textContent =
            `${user.name} (${user.role})`;
    }
}


showLoggedUser();
loadEquipment();

function viewDetails(id) {

    window.location.href =
        `equipment-details.html?id=${id}`;
}