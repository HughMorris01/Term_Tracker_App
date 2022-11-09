package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

import wgu.assessments.scheduler.Database.Repository;
import wgu.assessments.scheduler.Entity.Assessment;
import wgu.assessments.scheduler.Entity.Course;
import wgu.assessments.scheduler.Entity.CourseStatus;
import wgu.assessments.scheduler.Entity.Term;
import wgu.assessments.scheduler.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fillDataBase();

    }

    public void toTermList(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
    }

    public void fillDataBase(){
        Repository repo = new Repository(getApplication());
        Date testDate0 = new Date(1982, 1, 13);
        Date testDate1 = new Date(1983, 4, 22);
        Term testTerm0 = new Term(1, "Spring Term", testDate0, testDate1);
        repo.insertTerm(testTerm0);
        Date testDate2 = new Date(2018, 11, 31);
        Date testDate3 = new Date(1954, 8, 10);
        Term testTerm1 = new Term(2, "Summer Term", testDate2, testDate3);
        repo.insertTerm(testTerm1);
        Date testDate4 = new Date(1982, 1, 13);
        Date testDate5 = new Date(1989, 4, 22);
        Term testTerm2 = new Term(3, "Fall Term", testDate4, testDate5);
        repo.insertTerm(testTerm2);
        Date testDate6 = new Date(1982, 1, 13);
        Date testDate7 = new Date(1983, 4, 22);
        Term testTerm3 = new Term(4, "Winter", testDate6, testDate7);
        repo.insertTerm(testTerm3);

        String status0 = "SCHEDULED";
        String status1 = "PROGRESSING";
        String status2 = "COMPLETED";
        String status3 = "DROPPED";
        String instructor0 = "Bob Johnson";
        String instructor1 = "Walt Clyde";
        String instructor2 = "Ali Macintosh";
        String instructor3 = "Sue Farrell";
        String instructor4 = "Cathy Leaky";
        String instructorEmail0 = "BJohnson@wgu.edu";
        String instructorEmail1 = "WClyde@wgu.edu";
        String instructorEmail2 = "AMacintosh@wgu.edu";
        String instructorEmail3 = "SFarrell@wgu.edu";
        String instructorEmail4 = "CLeaky@wgu.edu";
        String phoneNumber = "(555) 555-5555";
        Course course0 = new Course(1, "Writing 101", testDate0, testDate1, instructor0, instructorEmail0, phoneNumber, status0, 1);
        Course course1 = new Course(2, "Arithmetic 401", testDate0, testDate1, instructor0, instructorEmail0, phoneNumber, status1, 1);
        Course course2 = new Course(3, "Programming 196", testDate0, testDate1, instructor1, instructorEmail1, phoneNumber, status2, 1);
        Course course3 = new Course(4, "Home Economics", testDate0, testDate1, instructor1, instructorEmail1, phoneNumber, status3, 2);
        Course course4 = new Course(5, "Intro to Biology", testDate0, testDate1, instructor2, instructorEmail2, phoneNumber, status0, 2);
        Course course5 = new Course(6, "Football Terminology", testDate0, testDate1, instructor2, instructorEmail2, phoneNumber, status0, 2);
        Course course6 = new Course(7, "Free Time", testDate0, testDate1, instructor3, instructorEmail3, phoneNumber, status1, 3);
        Course course7 = new Course(8, "Calculus 355", testDate0, testDate1, instructor3, instructorEmail4, phoneNumber, status2, 3);
        Course course8 = new Course(9, "Debate 901", testDate0, testDate1, instructor4, instructorEmail4, phoneNumber, status3, 3);
        Course course9 = new Course(10, "Woodworking 201",testDate0, testDate1, instructor4, instructorEmail4, phoneNumber, status0, 3);
        repo.insertCourse(course0);
        repo.insertCourse(course1);
        repo.insertCourse(course2);
        repo.insertCourse(course3);
        repo.insertCourse(course4);
        repo.insertCourse(course5);
        repo.insertCourse(course6);
        repo.insertCourse(course7);
        repo.insertCourse(course8);
        repo.insertCourse(course9);

        /*Assessment assessment1 = new Assessment(1, "Sample Assessment1", testDate0, testDate1, true, 1);
        Assessment assessment2 = new Assessment(2, "Sample Assessment2", testDate2, testDate3, false, 1);
        Assessment assessment3 = new Assessment(3, "Sample Assessment3", testDate4, testDate1, true, 2);
        Assessment assessment4 = new Assessment(4, "Sample Assessment4", testDate5, testDate7, false, 2);
        Assessment assessment5 = new Assessment(5, "Sample Assessment5", testDate6, testDate3, false, 3);
        Assessment assessment6 = new Assessment(6, "Sample Assessment6", testDate0, testDate2, true, 3);
        repo.insertAssessment(assessment1);
        repo.insertAssessment(assessment2);
        repo.insertAssessment(assessment3);
        repo.insertAssessment(assessment4);
        repo.insertAssessment(assessment5);
        repo.insertAssessment(assessment6); */
    }
}