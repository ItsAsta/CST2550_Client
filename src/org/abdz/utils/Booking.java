package org.abdz.utils;

public class Booking {
    private int bookingId;
    private int trainerId;
    private int clientId;
    private String dateTime;
    private int duration;
    private String clientFirstName;
    private String clientLastName;
    private String clientDob;
    private String trainerFirstName;
    private String trainerLastName;

    public Booking(int bookingId, int trainerId, int clientId, String dateTime,
                   int duration, String clientFirstName, String clientLastName, String clientDob,
                   String trainerFirstName, String trainerLastName) {
        this.bookingId = bookingId;
        this.trainerId = trainerId;
        this.clientId = clientId;
        this.dateTime = dateTime;
        this.duration = duration;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.clientDob = clientDob;
        this.trainerFirstName = trainerFirstName;
        this.trainerLastName = trainerLastName;
    }


    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientDob() {
        return clientDob;
    }

    public void setClientDob(String clientDob) {
        this.clientDob = clientDob;
    }

    public String getTrainerFirstName() {
        return trainerFirstName;
    }

    public void setTrainerFirstName(String trainerFirstName) {
        this.trainerFirstName = trainerFirstName;
    }

    public String getTrainerLastName() {
        return trainerLastName;
    }

    public void setTrainerLastName(String trainerLastName) {
        this.trainerLastName = trainerLastName;
    }
}
