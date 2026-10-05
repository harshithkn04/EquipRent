const API_URL = "http://localhost:8080";


const registerForm =
    document.getElementById("registerForm");


if (registerForm) {

    registerForm.addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const body =
                new URLSearchParams();

            body.append(
                "name",
                document.getElementById("name").value
            );

            body.append(
                "email",
                document.getElementById("email").value
            );

            body.append(
                "password",
                document.getElementById("password").value
            );

            body.append(
                "role",
                document.getElementById("role").value
            );

            const response =
                await fetch(
                    `${API_URL}/api/register`,
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

            document.getElementById(
                "message"
            ).textContent = message;

            if (response.ok) {

                setTimeout(
                    () => {
                        window.location.href =
                            "login.html";
                    },
                    1000
                );
            }
        }
    );
}


const loginForm =
    document.getElementById("loginForm");


if (loginForm) {

    loginForm.addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const body =
                new URLSearchParams();

            body.append(
                "email",
                document.getElementById("email").value
            );

            body.append(
                "password",
                document.getElementById("password").value
            );

            const response =
                await fetch(
                    `${API_URL}/api/login`,
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

            if (response.ok) {

                const parts =
                    message.split("|");

                const user = {

                    id: parts[1],

                    name: parts[2],

                    role: parts[3]
                };

                localStorage.setItem(
                    "user",
                    JSON.stringify(user)
                );

                window.location.href =
                    "index.html";

            } else {

                document.getElementById(
                    "message"
                ).textContent = message;
            }
        }
    );
}