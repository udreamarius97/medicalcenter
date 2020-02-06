package medicalcenter.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrescriptionResponse2 {
    private String patientUsername;

    private Date startDate;

    private Date endDate;

    private List<MedsPresc> medsPrescs=new ArrayList<>();

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public List<MedsPresc> getMedsPrescs() {
        return medsPrescs;
    }

    public void setMedsPrescs(List<MedsPresc> medsPrescs) {
        this.medsPrescs = medsPrescs;
    }

    public void add(MedsPresc mp){
        this.medsPrescs.add(mp);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
