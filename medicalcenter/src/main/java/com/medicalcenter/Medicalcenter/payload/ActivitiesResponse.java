package com.medicalcenter.Medicalcenter.payload;

public class ActivitiesResponse {
    private int id;
    private String startDate;
    private String endDate;
    private String activity;
    private String behavior;

    public ActivitiesResponse(){

    }

    public ActivitiesResponse(int id, String startDate, String endDate, String activity, String behavior) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activity = activity;
        this.behavior = behavior;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }
}
