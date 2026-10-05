function FilterBar({
  searchText,
  setSearchText,
  category,
  setCategory,
  owner,
  setOwner
}) {
  return (
    <div className="filter-bar">

      <div className="filter-input-wrapper">
        <span className="filter-icon">⌕</span>

        <input
          type="text"
          placeholder="Search equipment..."
          value={searchText}
          onChange={(event) =>
            setSearchText(event.target.value)
          }
        />
      </div>


      <select
        value={category}
        onChange={(event) =>
          setCategory(event.target.value)
        }
      >
        <option value="">
          All Categories
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


      <div className="filter-input-wrapper">
        <span className="filter-icon">⌕</span>

        <input
          type="text"
          placeholder="Search by owner..."
          value={owner}
          onChange={(event) =>
            setOwner(event.target.value)
          }
        />
      </div>

    </div>
  );
}

export default FilterBar;