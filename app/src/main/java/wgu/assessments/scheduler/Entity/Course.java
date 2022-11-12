package wgu.assessments.scheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseName;
    private int courseStatusPosition;
    private Date courseStartDate;
    private Date courseEndDate;
    private String courseInstructorName;
    private String courseInstructorPhone;
    private String courseInstructorEmail;
    private String courseNote;
    private int termId;

    // Constructor
    public Course(int courseId, String courseName, int courseStatusPosition,
                  Date courseStartDate, Date courseEndDate, String courseInstructorName,
                  String courseInstructorPhone, String courseInstructorEmail, String courseNote,
                   int termId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseStatusPosition = courseStatusPosition;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorPhone = courseInstructorPhone;
        this.courseInstructorEmail = courseInstructorEmail;
        this.courseNote = courseNote;
        this.termId = termId;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }
    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }
    public String getCourseStartDateString() {
        String courseStartDateString;
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        courseStartDateString = sdf.format(getCourseStartDate());
        return courseStartDateString;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }
    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }
    public String getCourseEndDateString() {
        String courseEndDateString;
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        courseEndDateString = sdf.format(getCourseEndDate());
        return courseEndDateString;
    }

    public String getCourseInstructorName() {
        return courseInstructorName;
    }
    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public String getCourseInstructorPhone() {
        return courseInstructorPhone;
    }
    public void setCourseInstructorPhone(String courseInstructorPhone) {
        this.courseInstructorPhone = courseInstructorPhone;
    }

    public String getCourseInstructorEmail() {
        return courseInstructorEmail;
    }
    public void setCourseInstructorEmail(String courseInstructorEmail) {
        this.courseInstructorEmail = courseInstructorEmail;
    }

    public int getCourseStatusPosition() {
        return courseStatusPosition;
    }
    public void setCourseStatusPosition(int courseStatusPosition) {
        this.courseStatusPosition = courseStatusPosition;
    }

    public String getCourseNote() {
        return courseNote;
    }
    public void setCourseNote(String courseNote) {
        this.courseNote = courseNote;
    }

    public int getTermId() {
        return termId;
    }
    public void setTermId(int termId) {
        this.termId = termId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseStartDate=" + courseStartDate +
                ", courseEndDate=" + courseEndDate +
                ", courseInstructorName='" + courseInstructorName + '\'' +
                ", courseInstructorPhone='" + courseInstructorPhone + '\'' +
                ", courseInstructorEmail='" + courseInstructorEmail + '\'' +
                ", courseNote=" + courseNote +
                ", termId=" + termId +
                '}';
    }
}
