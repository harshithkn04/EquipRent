import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {

  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [message, setMessage] = useState("");

  const handleLogin = (event) => {

    event.preventDefault();

    const formData = new URLSearchParams();

    formData.append("email", email);
    formData.append("password", password);

    fetch("http://localhost:8080/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: formData.toString()
    })
      .then(async (response) => {

        const result = await response.text();

        if (!response.ok) {
          setMessage(result);
          return;
        }

        // Example:
        // Login successful|1|Harshith|OWNER

        const parts = result.split("|");

        const userId = parts[1];
        const userName = parts[2];
        const role = parts[3];

        // Save logged-in user information
        localStorage.setItem("userId", userId);
        localStorage.setItem("userName", userName);
        localStorage.setItem("userRole", role);

        setMessage("Login successful!");

        // Go to home page
        navigate("/");
      })
      .catch((error) => {

        console.error("Login error:", error);

        setMessage("Unable to connect to server.");

      });
  };


  return (
    <main className="login-page">

      <div className="login-box">

        <h1>Login</h1>

        <form onSubmit={handleLogin}>

          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(event) =>
              setEmail(event.target.value)
            }
            required
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(event) =>
              setPassword(event.target.value)
            }
            required
          />

          <button type="submit">
            Login
          </button>

        </form>

        {message && (
          <p>{message}</p>
        )}

      </div>

    </main>
  );
}

export default Login;