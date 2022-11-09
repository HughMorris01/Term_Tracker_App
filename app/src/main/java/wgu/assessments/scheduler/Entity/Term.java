package wgu.assessments.scheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termId;

    private String termName;
    private Date termStartDate;
    private Date termEndDate;

    public Term(int termId, String termName, Date termStartDate, Date termEndDate) {
        this.termId = termId;
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    // Getters and Setters
    public int getTermId() {
        return termId;
    }
    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }
    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStartDate() {
        return termStartDate;
    }
    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }
    public String getTermStartDateString() {
        String termStartDateString;
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        termStartDateString = sdf.format(getTermStartDate());
        return termStartDateString;
    }

    public Date getTermEndDate() {
        return termEndDate;
    }
    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }
    public String getTermEndDateString() {
        String termEndDateString;
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        termEndDateString = sdf.format(getTermEndDate());
        return termEndDateString;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termId=" + termId +
                ", termName='" + termName + '\'' +
                ", termStartDate=" + termStartDate +
                ", termEndDate=" + termEndDate +
                '}';
    }
}
