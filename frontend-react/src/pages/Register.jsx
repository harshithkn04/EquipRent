import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {

  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("RENTER");

  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);


  const handleRegister = async (event) => {

    event.preventDefault();

    setLoading(true);
    setMessage("Creating account...");


    const formData = new URLSearchParams();

    formData.append("name", name);
    formData.append("email", email);
    formData.append("password", password);
    formData.append("role", role);


    try {

      const response = await fetch(
        "http://localhost:8080/api/register",
        {
          method: "POST",

          headers: {
            "Content-Type":
              "application/x-www-form-urlencoded"
          },

          body: formData.toString()
        }
      );


      const result = await response.text();


      if (response.ok) {

        setMessage(
          "Registration successful! Redirecting to login..."
        );


        setTimeout(() => {
          navigate("/login");
        }, 1200);

      } else {

        setMessage(result);

      }

    } catch (error) {

      console.error(
        "Registration error:",
        error
      );

      setMessage(
        "Unable to connect to server."
      );

    } finally {

      setLoading(false);

    }
  };


  return (

    <main className="register-page">

      <div className="register-box">

        <h1>
          Create Account
        </h1>

        <p className="register-subtitle">
          Join EquipRent and start renting equipment.
        </p>


        <form
          onSubmit={handleRegister}
        >

          <input
            type="text"
            placeholder="Full Name"
            value={name}
            onChange={(event) =>
              setName(event.target.value)
            }
            required
          />


          <input
            type="email"
            placeholder="Email Address"
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


          <select
            value={role}
            onChange={(event) =>
              setRole(event.target.value)
            }
          >

            <option value="RENTER">
              Renter
            </option>

            <option value="OWNER">
              Equipment Owner
            </option>

          </select>


          <button
            type="submit"
            disabled={loading}
          >

            {loading
              ? "Creating Account..."
              : "Create Account"}

          </button>

        </form>


        {message && (

          <p className="register-message">
            {message}
          </p>

        )}


        <p className="login-link">

          Already have an account?{" "}

          <span
            onClick={() => navigate("/login")}
          >
            Login
          </span>

        </p>

      </div>

    </main>

  );
}

export default Register;