package models;

import java.util.Date;

public class Registration {
    private int registrationId;
    private int userId;
    private int workshopId;
    private Date registrationDate;
    private String status;

    public Registration() {
    }

    public Registration(int registrationId, int userId, int workshopId, Date registrationDate, String status) {
        this.registrationId = registrationId;
        this.userId = userId;
        this.workshopId = workshopId;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getWorkshopId() { return workshopId; }
    public void setWorkshopId(int workshopId) { this.workshopId = workshopId; }

    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Registration{registrationId=" + registrationId + ", userId=" + userId + ", workshopId=" + workshopId + ", status='" + status + "'}";
    }
}
