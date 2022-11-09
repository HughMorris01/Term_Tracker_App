package wgu.assessments.scheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String assessmentName;
    private Date assessmentStartDate;
    private Date assessmentEndDate;
    private int courseId;


    private boolean isObjectiveAssessment;

    // Constructor
    public Assessment(int assessmentId, String assessmentName, Date assessmentStartDate, Date assessmentEndDate, Boolean isObjectiveAssessment, int courseId) {
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.courseId = courseId;
        this.isObjectiveAssessment = isObjectiveAssessment;
    }

    public Assessment(int tmpAssessmentId, String tmpAssessmentName, Date tmpAssessmentStartDate, Date tmpAssessmentEndDate, int tmpCourseId) {

    }

    // Getters and Setters
    public int getAssessmentId() {
        return assessmentId;
    }
    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }
    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Date getAssessmentStartDate() {
        return assessmentStartDate;
    }
    public void setAssessmentStartDate(Date assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    } public String getAssessmentStartDateString() {
        String assessmentStartDateString;
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        assessmentStartDateString = sdf.format(getAssessmentStartDate());
        return assessmentStartDateString;
    }

    public Date getAssessmentEndDate() {
        return assessmentEndDate;
    }
    public void setAssessmentEndDate(Date assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }
    public String getAssessmentEndDateString() {
        String assessmentEndDateString;
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        assessmentEndDateString = sdf.format(getAssessmentEndDate());
        return assessmentEndDateString;
    }

    public boolean isObjectiveAssessment() {
        return isObjectiveAssessment;
    }

    public void setIsObjectiveAssessment(boolean objectiveAssessment) {
        isObjectiveAssessment = objectiveAssessment;
    }

    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId=" + assessmentId +
                ", assessmentName='" + assessmentName + '\'' +
                ", assessmentStartDate=" + assessmentStartDate +
                ", assessmentEndDate=" + assessmentEndDate +
                ", courseId=" + courseId +
                '}';
    }
}
