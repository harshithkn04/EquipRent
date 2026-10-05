import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function Navbar() {

  const navigate = useNavigate();

  const [userId, setUserId] = useState(
    localStorage.getItem("userId")
  );

  const [role, setRole] = useState(
    localStorage.getItem("userRole")
  );


  useEffect(() => {

    const updateUser = () => {

      setUserId(
        localStorage.getItem("userId")
      );

      setRole(
        localStorage.getItem("userRole")
      );
    };

    window.addEventListener(
      "storage",
      updateUser
    );

    return () => {
      window.removeEventListener(
        "storage",
        updateUser
      );
    };

  }, []);


  const handleLogout = () => {

    localStorage.removeItem("userId");
    localStorage.removeItem("userName");
    localStorage.removeItem("userRole");

    setUserId(null);
    setRole(null);

    navigate("/");

  };


  return (

    <nav>

      <Link to="/">
        <h2>EquipRent</h2>
      </Link>


      <div>

        <Link to="/">
          Home
        </Link>


        {!userId && (
          <>
            <Link to="/login">
              Login
            </Link>

            <Link to="/register">
              Register
            </Link>
          </>
        )}


        {userId && role === "OWNER" && (
          <>
            <Link to="/owner-dashboard">
              Dashboard
            </Link>

            <Link to="/rental-tracking">
              Rental Tracking
            </Link>
          </>
        )}


        {userId && (
          <button
            className="logout-button"
            onClick={handleLogout}
          >
            Logout
          </button>
        )}

      </div>

    </nav>
  );
}

export default Navbar;