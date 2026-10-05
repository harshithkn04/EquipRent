import { Link } from "react-router-dom";

function EquipmentCard({ equipment }) {

  const isAvailable =
    equipment.availableQuantity > 0;

  return (

    <article className="equipment-card">

      {/* IMAGE */}

      <div className="equipment-image-wrapper">

        <img
          src={equipment.photo}
          alt={equipment.title}
        />

        <span className="category-badge">
          {equipment.category}
        </span>

      </div>


      {/* CONTENT */}

      <div className="equipment-card-content">

        <h3>
          {equipment.title}
        </h3>


        <p className="equipment-description">
          {equipment.description}
        </p>


        <div className="equipment-details">

          <p>
            <strong>Owner</strong>
            <span>{equipment.ownerName}</span>
          </p>

          <p>
            <strong>Available</strong>
            <span>
              {equipment.availableQuantity}
            </span>
          </p>

        </div>


        {/* ACTIONS */}

        <div className="equipment-actions">

          <Link
            to={`/equipment/${equipment.id}`}
            className="details-button"
          >
            View Details
          </Link>


          {isAvailable ? (

            <Link
              to={`/equipment/${equipment.id}`}
              className="rent-button-card"
            >
              Rent Equipment
            </Link>

          ) : (

            <button
              className="rent-button-card unavailable"
              disabled
            >
              Not Available
            </button>

          )}

        </div>

      </div>

    </article>

  );
}

export default EquipmentCard;