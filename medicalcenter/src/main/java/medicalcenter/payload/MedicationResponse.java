package medicalcenter.payload;

public class MedicationResponse {
    private String name;

    private String sideEfects;

    private String dosage;

    public MedicationResponse(){

    }

    public MedicationResponse(String name, String sideEfects, String dosage) {
        this.name = name;
        this.sideEfects = sideEfects;
        this.dosage = dosage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSideEfects() {
        return sideEfects;
    }

    public void setSideEfects(String sideEfects) {
        this.sideEfects = sideEfects;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
