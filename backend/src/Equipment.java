public class Equipment {

    private int id;
    private String title;
    private String description;
    private String photo;
    private int totalQuantity;
    private int availableQuantity;
    private String startDate;
    private String endDate;
    private String category;
    private int ownerId;
    private String ownerName;

    public Equipment() {
    }

    public Equipment(
            String title,
            String description,
            String photo,
            int totalQuantity,
            String startDate,
            String endDate,
            String category,
            int ownerId) {

        this.title = title;
        this.description = description;
        this.photo = photo;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = totalQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.ownerId = ownerId;
    }

    public Equipment(
            int id,
            String title,
            String description,
            String photo,
            int totalQuantity,
            int availableQuantity,
            String startDate,
            String endDate,
            String category,
            int ownerId,
            String ownerName) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCategory() {
        return category;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }
}