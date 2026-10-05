import { useEffect, useState } from "react";

function OwnerDashboard() {

  const [equipment, setEquipment] = useState([]);
  const [loading, setLoading] = useState(true);

  // Add form fields
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [photo, setPhoto] = useState("");
  const [quantity, setQuantity] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [category, setCategory] = useState("");

  const [message, setMessage] = useState("");

  // Edit mode
  const [editingId, setEditingId] = useState(null);

  const ownerId = localStorage.getItem("userId");
  const ownerName = localStorage.getItem("userName");


  // =====================================================
  // LOAD EQUIPMENT
  // =====================================================

  const loadEquipment = () => {

    fetch("http://localhost:8080/api/equipment")

      .then((response) => response.json())

      .then((data) => {

        const myEquipment = data.filter(
          (item) =>
            item.ownerId === Number(ownerId)
        );

        setEquipment(myEquipment);
        setLoading(false);

      })

      .catch((error) => {

        console.error(
          "Error loading equipment:",
          error
        );

        setLoading(false);

      });
  };


  useEffect(() => {

    loadEquipment();

  }, []);


  // =====================================================
  // ADD EQUIPMENT
  // =====================================================

  const handleAddEquipment = (event) => {

    event.preventDefault();

    setMessage("Adding equipment...");

    const formData =
      new URLSearchParams();

    formData.append(
      "title",
      title
    );

    formData.append(
      "description",
      description
    );

    formData.append(
      "photo",
      photo
    );

    formData.append(
      "quantity",
      quantity
    );

    formData.append(
      "startDate",
      startDate
    );

    formData.append(
      "endDate",
      endDate
    );

    formData.append(
      "category",
      category
    );

    formData.append(
      "ownerId",
      ownerId
    );


    fetch(
      "http://localhost:8080/api/equipment",
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
            "Equipment added successfully!"
          );

          clearForm();

          loadEquipment();

        } else {

          setMessage(result);

        }

      })

      .catch((error) => {

        console.error(
          "Error adding equipment:",
          error
        );

        setMessage(
          "Failed to add equipment."
        );

      });
  };


  // =====================================================
  // START EDIT
  // =====================================================

  const handleEdit = (item) => {

    setEditingId(item.id);

    setTitle(item.title);
    setDescription(item.description);
    setPhoto(item.photo);
    setQuantity(item.totalQuantity);
    setStartDate(item.startDate);
    setEndDate(item.endDate);
    setCategory(item.category);

    setMessage(
      "Editing " + item.title
    );

    // Scroll to form
    window.scrollTo({
      top: 0,
      behavior: "smooth"
    });
  };


  // =====================================================
  // UPDATE EQUIPMENT
  // =====================================================

  const handleUpdateEquipment = (event) => {

    event.preventDefault();

    setMessage(
      "Updating equipment..."
    );

    const formData =
      new URLSearchParams();

    formData.append(
      "id",
      editingId
    );

    formData.append(
      "title",
      title
    );

    formData.append(
      "description",
      description
    );

    formData.append(
      "photo",
      photo
    );

    formData.append(
      "quantity",
      quantity
    );

    formData.append(
      "startDate",
      startDate
    );

    formData.append(
      "endDate",
      endDate
    );

    formData.append(
      "category",
      category
    );

    formData.append(
      "ownerId",
      ownerId
    );


    fetch(
      "http://localhost:8080/api/equipment",
      {
        method: "PUT",

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
            "Equipment updated successfully!"
          );

          clearForm();

          setEditingId(null);

          loadEquipment();

        } else {

          setMessage(result);

        }

      })

      .catch((error) => {

        console.error(
          "Error updating equipment:",
          error
        );

        setMessage(
          "Failed to update equipment."
        );

      });
  };


  // =====================================================
  // DELETE EQUIPMENT
  // =====================================================

  const handleDelete = (item) => {

    const confirmed =
      window.confirm(
        `Are you sure you want to delete "${item.title}"?`
      );

    if (!confirmed) {
      return;
    }


    const formData =
      new URLSearchParams();

    formData.append(
      "id",
      item.id
    );

    formData.append(
      "ownerId",
      ownerId
    );


    setMessage(
      "Deleting equipment..."
    );


    fetch(
      "http://localhost:8080/api/equipment",
      {
        method: "DELETE",

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
            "Equipment deleted successfully!"
          );

          loadEquipment();

        } else {

          setMessage(result);

        }

      })

      .catch((error) => {

        console.error(
          "Error deleting equipment:",
          error
        );

        setMessage(
          "Failed to delete equipment."
        );

      });
  };


  // =====================================================
  // CLEAR FORM
  // =====================================================

  const clearForm = () => {

    setTitle("");
    setDescription("");
    setPhoto("");
    setQuantity("");
    setStartDate("");
    setEndDate("");
    setCategory("");
  };


  // =====================================================
  // CANCEL EDIT
  // =====================================================

  const handleCancelEdit = () => {

    setEditingId(null);

    clearForm();

    setMessage("");
  };


  // =====================================================
  // LOGIN CHECK
  // =====================================================

  if (!ownerId) {

    return (

      <main className="dashboard-page">

        <h2>
          Please login first.
        </h2>

      </main>
    );
  }


  // =====================================================
  // LOADING
  // =====================================================

  if (loading) {

    return (

      <main className="dashboard-page">

        <h2>
          Loading...
        </h2>

      </main>
    );
  }


  // =====================================================
  // UI
  // =====================================================

  return (

    <main className="dashboard-page">


      {/* ================= HEADER ================= */}

      <div className="dashboard-header">

        <h1>
          Owner Dashboard
        </h1>

        <p>
          Welcome, {ownerName}
        </p>

      </div>


      {/* ================= STATISTICS ================= */}

      <div className="dashboard-stats">

        <div className="stat-card">

          <h3>
            Total Equipment
          </h3>

          <p>
            {equipment.length}
          </p>

        </div>


        <div className="stat-card">

          <h3>
            Available Items
          </h3>

          <p>

            {
              equipment.filter(
                (item) =>
                  item.availableQuantity > 0
              ).length
            }

          </p>

        </div>

      </div>


      {/* ================= ADD / EDIT FORM ================= */}

      <section className="add-equipment-section">

        <h2>

          {editingId
            ? "Edit Equipment"
            : "Add New Equipment"}

        </h2>


        <form

          className="equipment-form"

          onSubmit={
            editingId
              ? handleUpdateEquipment
              : handleAddEquipment
          }

        >


          {/* TITLE */}

          <input

            type="text"

            placeholder="Equipment title"

            value={title}

            onChange={(event) =>
              setTitle(
                event.target.value
              )
            }

            required

          />


          {/* DESCRIPTION */}

          <textarea

            placeholder="Equipment description"

            value={description}

            onChange={(event) =>
              setDescription(
                event.target.value
              )
            }

            required

          />


          {/* PHOTO */}

          <input

            type="url"

            placeholder="Equipment photo URL"

            value={photo}

            onChange={(event) =>
              setPhoto(
                event.target.value
              )
            }

            required

          />


          {/* QUANTITY */}

          <input

            type="number"

            placeholder="Quantity"

            min="1"

            value={quantity}

            onChange={(event) =>
              setQuantity(
                event.target.value
              )
            }

            required

          />


          {/* START DATE */}

          <label>
            Rental Start Date
          </label>

          <input

            type="date"

            value={startDate}

            onChange={(event) =>
              setStartDate(
                event.target.value
              )
            }

            required

          />


          {/* END DATE */}

          <label>
            Rental End Date
          </label>

          <input

            type="date"

            value={endDate}

            onChange={(event) =>
              setEndDate(
                event.target.value
              )
            }

            required

          />


          {/* CATEGORY */}

          <select

            value={category}

            onChange={(event) =>
              setCategory(
                event.target.value
              )
            }

            required

          >

            <option value="">
              Select Category
            </option>

            <option value="Tools">
              Tools
            </option>

            <option value="Cameras">
              Cameras
            </option>

            <option value="Camping Gear">
              Camping Gear
            </option>

            <option value="Party Supplies">
              Party Supplies
            </option>

            <option value="Outdoor Gear">
              Outdoor Gear
            </option>

            <option value="Electronics">
              Electronics
            </option>

          </select>


          {/* SUBMIT BUTTON */}

          <button type="submit">

            {editingId
              ? "Update Equipment"
              : "Add Equipment"}

          </button>


          {/* CANCEL EDIT */}

          {editingId && (

            <button

              type="button"

              onClick={
                handleCancelEdit
              }

            >

              Cancel Edit

            </button>

          )}

        </form>


        {/* MESSAGE */}

        {message && (

          <p className="form-message">

            {message}

          </p>

        )}

      </section>


      {/* ================= MY EQUIPMENT ================= */}

      <section>

        <h2>
          My Equipment
        </h2>


        {equipment.length === 0 ? (

          <p>
            You have not listed any equipment yet.
          </p>

        ) : (

          <div className="owner-equipment-grid">

            {equipment.map((item) => (

              <div

                className="owner-equipment-card"

                key={item.id}

              >


                {/* IMAGE */}

                <img

                  src={item.photo}

                  alt={item.title}

                />


                {/* INFORMATION */}

                <div className="owner-equipment-info">

                  <h3>
                    {item.title}
                  </h3>


                  <p>
                    {item.description}
                  </p>


                  <p>

                    <strong>
                      Category:
                    </strong>{" "}

                    {item.category}

                  </p>


                  <p>

                    <strong>
                      Total:
                    </strong>{" "}

                    {item.totalQuantity}

                  </p>


                  <p>

                    <strong>
                      Available:
                    </strong>{" "}

                    {item.availableQuantity}

                  </p>


                  <p>

                    <strong>
                      Rental period:
                    </strong>{" "}

                    {item.startDate}

                    {" "}to{" "}

                    {item.endDate}

                  </p>


                  {/* ACTION BUTTONS */}

                  <div className="equipment-actions">

                    <button

                      type="button"

                      onClick={() =>
                        handleEdit(item)
                      }

                    >

                      Edit

                    </button>


                    <button

                      type="button"

                      onClick={() =>
                        handleDelete(item)
                      }

                    >

                      Delete

                    </button>

                  </div>

                </div>

              </div>

            ))}

          </div>

        )}

      </section>

    </main>
  );
}

export default OwnerDashboard;