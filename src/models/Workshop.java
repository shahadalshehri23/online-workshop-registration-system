package models;

public class Workshop {
    private int workshopId;
    private String title;
    private String description;
    private int capacity;
    private int registeredCount;
    private int trainerId;

    public Workshop() {
    }

    public Workshop(int workshopId, String title, String description, int capacity, int registeredCount, int trainerId) {
        this.workshopId = workshopId;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.registeredCount = registeredCount;
        this.trainerId = trainerId;
    }

    public int getWorkshopId() { return workshopId; }
    public void setWorkshopId(int workshopId) { this.workshopId = workshopId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getRegisteredCount() { return registeredCount; }
    public void setRegisteredCount(int registeredCount) { this.registeredCount = registeredCount; }

    public int getTrainerId() { return trainerId; }
    public void setTrainerId(int trainerId) { this.trainerId = trainerId; }

    @Override
    public String toString() {
        return "Workshop{workshopId=" + workshopId + ", title='" + title + "', capacity=" + capacity + ", registeredCount=" + registeredCount + "}";
    }
}
