import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import Home from "./pages/Home";
import EquipmentDetails from "./pages/EquipmentDetails";
import Login from "./pages/Login";
import OwnerDashboard from "./pages/OwnerDashboard";
import RentalTracking from "./pages/RentalTracking";
import Register from "./pages/Register";

function App() {

  return (
    <BrowserRouter>

      <Navbar />

      <Routes>

        <Route
          path="/"
          element={<Home />}
        />

        <Route
          path="/equipment/:id"
          element={<EquipmentDetails />}
        />

        <Route
          path="/login"
          element={<Login />}
        />

        <Route
          path="/owner-dashboard"
          element={<OwnerDashboard />}
        />

        <Route
          path="/rental-tracking"
          element={<RentalTracking />}
        />

        <Route
          path="/register"
          element={<Register />}
        />

      </Routes>

    </BrowserRouter>
  );
}

export default App;