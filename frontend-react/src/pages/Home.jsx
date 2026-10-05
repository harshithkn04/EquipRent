import { useEffect, useState } from "react";
import EquipmentCard from "../components/EquipmentCard";
import FilterBar from "../components/FilterBar";

function Home() {

  const [equipmentList, setEquipmentList] = useState([]);
  const [loading, setLoading] = useState(true);

  const [searchText, setSearchText] = useState("");
  const [category, setCategory] = useState("");
  const [owner, setOwner] = useState("");

  useEffect(() => {

    fetch("http://localhost:8080/api/equipment")

      .then((response) => response.json())

      .then((data) => {
        setEquipmentList(data);
        setLoading(false);
      })

      .catch((error) => {

        console.error(
          "Error fetching equipment:",
          error
        );

        setLoading(false);
      });

  }, []);


  const filteredEquipment = equipmentList.filter(
    (equipment) => {

      const title =
        equipment.title || "";

      const equipmentCategory =
        equipment.category || "";

      const ownerName =
        equipment.ownerName || "";


      const matchesSearch =
        title
          .toLowerCase()
          .includes(
            searchText.toLowerCase()
          );


      const matchesCategory =
        category === "" ||
        equipmentCategory === category;


      const matchesOwner =
        ownerName
          .toLowerCase()
          .includes(
            owner.toLowerCase()
          );


      return (
        matchesSearch &&
        matchesCategory &&
        matchesOwner
      );
    }
  );


  return (

    <main>

      {/* ================================
          HERO
      ================================= */}

      <section className="home-hero">

        <h1>
          Rent Equipment
          <span>
            From People Near You
          </span>
        </h1>

        <p>
          Find cameras, tools, camping gear and more.
          Rent what you need from people around you.
        </p>

      </section>


      {/* ================================
          EXPLORE / SEARCH
      ================================= */}

      <section className="explore-section">

        <p className="explore-label">
          Explore
        </p>

        <h2>
          Find the right equipment
        </h2>

        <p className="explore-description">
          Search by equipment, category or owner.
        </p>

        <FilterBar
          searchText={searchText}
          setSearchText={setSearchText}

          category={category}
          setCategory={setCategory}

          owner={owner}
          setOwner={setOwner}
        />

      </section>


      {/* ================================
          EQUIPMENT
      ================================= */}

      <section className="equipment-section">

        <div className="equipment-section-header">

          <h2>
            Available Equipment
          </h2>

          {!loading && (
            <p className="equipment-count">
              {filteredEquipment.length} equipment{" "}
              {filteredEquipment.length === 1
                ? "item"
                : "items"}{" "}
              available
            </p>
          )}

        </div>


        {/* Loading */}

        {loading ? (

          <p>
            Loading equipment...
          </p>

        ) : filteredEquipment.length === 0 ? (

          <p>
            No equipment found.
          </p>

        ) : (

          <div className="equipment-grid">

            {filteredEquipment.map(
              (equipment) => (

                <EquipmentCard
                  key={equipment.id}
                  equipment={equipment}
                />

              )
            )}

          </div>

        )}

      </section>

    </main>
  );
}

export default Home;