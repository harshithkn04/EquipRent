import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

function EquipmentDetails() {

  const { id } = useParams();

  const [equipment, setEquipment] = useState(null);
  const [loading, setLoading] = useState(true);
  const [rentMessage, setRentMessage] = useState("");
  const [quantity, setQuantity] = useState(1);
  const [rentLoading, setRentLoading] = useState(false);

  const renterId = localStorage.getItem("userId");


  // Load equipment details
  const loadEquipment = () => {

    fetch("http://localhost:8080/api/equipment")

      .then((response) => {

        if (!response.ok) {
          throw new Error("Failed to load equipment");
        }

        return response.json();
      })

      .then((data) => {

        const selectedEquipment = data.find(
          (item) => item.id === Number(id)
        );

        setEquipment(selectedEquipment);
        setLoading(false);

      })

      .catch((error) => {

        console.error(
          "Error fetching equipment:",
          error
        );

        setLoading(false);
      });
  };


  // Load equipment when page opens
  useEffect(() => {

    loadEquipment();

  }, [id]);


  // Rent equipment
  const handleRent = () => {

    // Check login
    if (!renterId) {

      setRentMessage(
        "Please login before renting equipment."
      );

      return;
    }


    // Check quantity
    if (quantity <= 0) {

      setRentMessage(
        "Quantity must be greater than zero."
      );

      return;
    }


    if (quantity > equipment.availableQuantity) {

      setRentMessage(
        "Not enough equipment available."
      );

      return;
    }


    setRentLoading(true);
    setRentMessage("Processing rental...");


    const formData =
      new URLSearchParams();


    formData.append(
      "equipmentId",
      equipment.id
    );


    formData.append(
      "renterId",
      renterId
    );


    formData.append(
      "quantity",
      quantity
    );


    fetch(
      "http://localhost:8080/api/rent",
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

        const message =
          await response.text();


        if (response.ok) {

          setRentMessage(
            "Rental successful!"
          );

          // Reset quantity
          setQuantity(1);

          // Reload equipment
          loadEquipment();

        } else {

          setRentMessage(message);

        }

      })

      .catch((error) => {

        console.error(
          "Rental error:",
          error
        );

        setRentMessage(
          "Rental failed. Please try again."
        );

      })

      .finally(() => {

        setRentLoading(false);

      });
  };


  // Loading
  if (loading) {

    return (
      <main className="details-page">

        <p>
          Loading equipment...
        </p>

      </main>
    );
  }


  // Not found
  if (!equipment) {

    return (
      <main className="details-page">

        <p>
          Equipment not found.
        </p>

      </main>
    );
  }


  return (

    <main className="details-page">

      {/* Equipment Image */}

      <img
        src={equipment.photo}
        alt={equipment.title}
        className="details-image"
      />


      {/* Equipment Information */}

      <div className="details-info">

        <h1>
          {equipment.title}
        </h1>


        <p>
          {equipment.description}
        </p>


        <p>
          <strong>
            Category:
          </strong>{" "}
          {equipment.category}
        </p>


        <p>
          <strong>
            Owner:
          </strong>{" "}
          {equipment.ownerName}
        </p>


        <p>
          <strong>
            Total Quantity:
          </strong>{" "}
          {equipment.totalQuantity}
        </p>


        <p>
          <strong>
            Available Quantity:
          </strong>{" "}
          {equipment.availableQuantity}
        </p>


        <p>
          <strong>
            Available From:
          </strong>{" "}
          {equipment.startDate}
        </p>


        <p>
          <strong>
            Available Until:
          </strong>{" "}
          {equipment.endDate}
        </p>


        {/* Rental Section */}

        {equipment.availableQuantity > 0 ? (

          <div className="rental-section">

            <label>
              <strong>
                Quantity:
              </strong>
            </label>


            <select
              value={quantity}
              onChange={(event) =>
                setQuantity(
                  Number(event.target.value)
                )
              }
              disabled={rentLoading}
            >

              {Array.from(
                {
                  length:
                    equipment.availableQuantity
                },
                (_, index) => (

                  <option
                    key={index + 1}
                    value={index + 1}
                  >
                    {index + 1}
                  </option>

                )
              )}

            </select>


            <button
              className="rent-button"
              onClick={handleRent}
              disabled={rentLoading}
            >

              {rentLoading
                ? "Processing..."
                : "Rent Equipment"}

            </button>

          </div>

        ) : (

          <p className="not-available">
            Currently not available
          </p>

        )}


        {/* Rental Message */}

        {rentMessage && (

          <p className="rent-message">
            {rentMessage}
          </p>

        )}

      </div>

    </main>
  );
}

export default EquipmentDetails;