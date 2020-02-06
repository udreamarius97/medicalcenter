package medicalcenter.payload;

public class MedsPresc {

    private String numeMed;

    private String perioadaTratament;

    public MedsPresc(){

    }
    public MedsPresc(String numeMed, String perioadaTratament) {
        this.numeMed = numeMed;
        this.perioadaTratament = perioadaTratament;
    }

    public String getNumeMed() {
        return numeMed;
    }

    public void setNumeMed(String numeMed) {
        this.numeMed = numeMed;
    }

    public String getPerioadaTratament() {
        return perioadaTratament;
    }

    public void setPerioadaTratament(String perioadaTratament) {
        this.perioadaTratament = perioadaTratament;
    }
}
