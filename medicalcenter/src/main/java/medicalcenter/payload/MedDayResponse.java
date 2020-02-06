package medicalcenter.payload;

public class MedDayResponse {

    private int id;

    private String user;

    private String daySelected;

    private String medication;

    private String intervalDaily;

    private String istaken;

    public MedDayResponse(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDay() {
        return daySelected;
    }

    public void setDay(String day) {
        this.daySelected = day;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getInterval() {
        return intervalDaily;
    }

    public void setInterval(String interval) {
        this.intervalDaily = interval;
    }

    public String getIsTaken() {
        return istaken;
    }

    public void setIsTaken(String isTaken) {
        this.istaken = isTaken;
    }
}
