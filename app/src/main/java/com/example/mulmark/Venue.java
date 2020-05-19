package com.example.mulmark;

class Venue {
    private double venueLat;
    private double venueLong;
    private String venueId;

    public Venue(){

    }

    public Venue(double venueLat, double venueLong) {
        this.venueLat = venueLat;
        this.venueLong = venueLong;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public double getVenueLat() {
        return venueLat;
    }

    public void setVenueLat(double venueLat) {
        this.venueLat = venueLat;
    }

    public double getVenueLong() {
        return venueLong;
    }

    public void setVenueLong(double venueLong) {
        this.venueLong = venueLong;
    }

}
