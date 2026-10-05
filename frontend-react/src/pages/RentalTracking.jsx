import { useEffect, useState } from "react";

function RentalTracking() {

  const [rentals, setRentals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");

  const ownerId = localStorage.getItem("userId");


  // Load rental tracking information
  const loadRentals = () => {

    if (!ownerId) {
      setLoading(false);
      return;
    }

    fetch(
      `http://localhost:8080/api/rental-tracking?ownerId=${ownerId}`
    )

      .then((response) => {

        if (!response.ok) {
          throw new Error("Failed to load rentals");
        }

        return response.json();

      })

      .then((data) => {

        setRentals(data);
        setLoading(false);

      })

      .catch((error) => {

        console.error(
          "Error loading rental tracking:",
          error
        );

        setMessage(
          "Failed to load rental tracking."
        );

        setLoading(false);

      });
  };


  useEffect(() => {

    loadRentals();

  }, [ownerId]);


  // Update rental tracking
  const updateRental = (rental) => {

    const formData = new URLSearchParams();

    formData.append(
      "rentalId",
      rental.id
    );

    formData.append(
      "pickupStatus",
      rental.pickupStatus
    );

    formData.append(
      "returnStatus",
      rental.returnStatus
    );

    formData.append(
      "condition",
      rental.condition
    );

    formData.append(
      "damageReport",
      rental.damageReport
    );


    fetch(
      "http://localhost:8080/api/rental-tracking",
      {

        method: "POST",

        headers: {
          "Content-Type":
            "application/x-www-form-urlencoded"
        },

        body: formData.toString()

      }
    )

      .then(async (response) => {

        const result =
          await response.text();

        if (response.ok) {

          setMessage(
            "Rental tracking updated successfully!"
          );

          loadRentals();

        } else {

          setMessage(result);

        }

      })

      .catch((error) => {

        console.error(
          "Error updating rental:",
          error
        );

        setMessage(
          "Failed to update rental."
        );

      });
  };


  // Change a field inside rental
  const handleChange = (
    rentalId,
    field,
    value
  ) => {

    setRentals((previousRentals) =>

      previousRentals.map((rental) =>

        rental.id === rentalId
          ? {
              ...rental,
              [field]: value
            }
          : rental

      )

    );
  };


  if (!ownerId) {

    return (
      <main className="rental-tracking-page">

        <h2>
          Please login first.
        </h2>

      </main>
    );
  }


  if (loading) {

    return (
      <main className="rental-tracking-page">

        <h2>
          Loading rental tracking...
        </h2>

      </main>
    );
  }


  return (

    <main className="rental-tracking-page">

      <div className="rental-tracking-header">

        <h1>
          Rental Tracking
        </h1>

        <p>
          Manage rentals of your equipment
        </p>

      </div>


      {message && (

        <p className="tracking-message">
          {message}
        </p>

      )}


      {rentals.length === 0 ? (

        <div className="no-rentals">

          <h2>
            No rentals found
          </h2>

          <p>
            Nobody has rented your equipment yet.
          </p>

        </div>

      ) : (

        <div className="rental-tracking-table-wrapper">

          <table className="rental-tracking-table">

            <thead>

              <tr>

                <th>
                  Equipment
                </th>

                <th>
                  Renter
                </th>

                <th>
                  Quantity
                </th>

                <th>
                  Rental Date
                </th>

                <th>
                  Status
                </th>

                <th>
                  Pickup
                </th>

                <th>
                  Return
                </th>

                <th>
                  Condition
                </th>

                <th>
                  Damage Report
                </th>

                <th>
                  Action
                </th>

              </tr>

            </thead>


            <tbody>

              {rentals.map((rental) => (

                <tr key={rental.id}>

                  <td>
                    {rental.equipment}
                  </td>

                  <td>
                    {rental.renter}
                  </td>

                  <td>
                    {rental.quantity}
                  </td>

                  <td>
                    {rental.rentalDate}
                  </td>

                  <td>
                    <span
                      className={
                        rental.status === "ACTIVE"
                          ? "status-active"
                          : "status-completed"
                      }
                    >
                      {rental.status}
                    </span>
                  </td>


                  <td>

                    <select
                      value={
                        rental.pickupStatus || ""
                      }

                      onChange={(event) =>
                        handleChange(
                          rental.id,
                          "pickupStatus",
                          event.target.value
                        )
                      }
                    >

                      <option value="">
                        Select
                      </option>

                      <option value="PENDING">
                        Pending
                      </option>

                      <option value="PICKED_UP">
                        Picked Up
                      </option>

                    </select>

                  </td>


                  <td>

                    <select
                      value={
                        rental.returnStatus || ""
                      }

                      onChange={(event) =>
                        handleChange(
                          rental.id,
                          "returnStatus",
                          event.target.value
                        )
                      }
                    >

                      <option value="">
                        Select
                      </option>

                      <option value="PENDING">
                        Pending
                      </option>

                      <option value="RETURNED">
                        Returned
                      </option>

                    </select>

                  </td>


                  <td>

                    <select
                      value={
                        rental.condition || ""
                      }

                      onChange={(event) =>
                        handleChange(
                          rental.id,
                          "condition",
                          event.target.value
                        )
                      }
                    >

                      <option value="">
                        Select
                      </option>

                      <option value="GOOD">
                        Good
                      </option>

                      <option value="DAMAGED">
                        Damaged
                      </option>

                    </select>

                  </td>


                  <td>

                    <input
                      type="text"
                      placeholder="No damage"
                      value={
                        rental.damageReport || ""
                      }

                      onChange={(event) =>
                        handleChange(
                          rental.id,
                          "damageReport",
                          event.target.value
                        )
                      }
                    />

                  </td>


                  <td>

                    <button
                      className="update-rental-button"

                      onClick={() =>
                        updateRental(rental)
                      }
                    >
                      Update
                    </button>

                  </td>

                </tr>

              ))}

            </tbody>

          </table>

        </div>

      )}

    </main>
  );
}

export default RentalTracking;