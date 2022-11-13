package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

import wgu.assessments.scheduler.Database.Repository;
import wgu.assessments.scheduler.Entity.Assessment;
import wgu.assessments.scheduler.Entity.Course;
import wgu.assessments.scheduler.Entity.Term;
import wgu.assessments.scheduler.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toTermList(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
    }

    public void fillDataBase(View view) {
        {
            Repository repo = new Repository(getApplication());
            Date testDate0 = new Date(2022, 3, 1);
            Date testDate1 = new Date(2022, 5, 30);
            Term testTerm0 = new Term(1, "Spring Term", testDate0, testDate1);
            repo.insertTerm(testTerm0);
            Date testDate2 = new Date(2022, 6, 1);
            Date testDate3 = new Date(2022, 8, 30);
            Term testTerm1 = new Term(2, "Summer Term", testDate2, testDate3);
            repo.insertTerm(testTerm1);
            Date testDate4 = new Date(2022, 9, 1);
            Date testDate5 = new Date(2022, 11, 31);
            Term testTerm2 = new Term(3, "Fall Term", testDate4, testDate5);
            repo.insertTerm(testTerm2);
            Date testDate6 = new Date(2023, 0, 1);
            Date testDate7 = new Date(2023, 2, 31);
            Term testTerm3 = new Term(4, "Winter Term", testDate6, testDate7);
            repo.insertTerm(testTerm3);

            String status0 = "This is a course status note" +
                    "3 lines" +
                    "long";
            String status1 = "This is a course status note" +
                    "2 lines long";
            String status2 = "This isn't a long course note at all";
            String status3 = "blah blah blah";
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
            Course course0 = new Course(1, "Writing 101", 1, testDate0, testDate1, instructor0, instructorEmail0, phoneNumber, status0, 1);
            Course course1 = new Course(2, "Arithmetic 401", 2, testDate0, testDate1, instructor0, instructorEmail0, phoneNumber, status1, 1);
            Course course2 = new Course(3, "Programming 196", 3, testDate0, testDate1, instructor1, instructorEmail1, phoneNumber, status2, 1);
            Course course3 = new Course(4, "Home Economics", 0, testDate0, testDate1, instructor1, instructorEmail1, phoneNumber, status3, 2);
            Course course4 = new Course(5, "Intro to Biology", 1, testDate0, testDate1, instructor2, instructorEmail2, phoneNumber, status0, 2);
            Course course5 = new Course(6, "Football Terminology", 2, testDate0, testDate1, instructor2, instructorEmail2, phoneNumber, status0, 2);
            Course course6 = new Course(7, "Free Time", 3, testDate0, testDate1, instructor3, instructorEmail3, phoneNumber, status1, 3);
            Course course7 = new Course(8, "Calculus 355", 3, testDate0, testDate1, instructor3, instructorEmail4, phoneNumber, status2, 3);
            Course course8 = new Course(9, "Debate 901", 1, testDate0, testDate1, instructor4, instructorEmail4, phoneNumber, status3, 3);
            Course course9 = new Course(10, "Woodworking 201",2, testDate0, testDate1, instructor4, instructorEmail4, phoneNumber, status0, 3);
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

        Assessment assessment1 = new Assessment(1, "Assessment1", testDate0, testDate1, true, false, false,1);
        Assessment assessment2 = new Assessment(2, "Assessment2", testDate2, testDate3, false, true, false,1);
        Assessment assessment3 = new Assessment(3, "Assessment3", testDate4, testDate1, true, false, false,2);
        Assessment assessment4 = new Assessment(4, "Assessment4", testDate5, testDate7, false, true, false,2);
        Assessment assessment5 = new Assessment(5, "Assessment5", testDate6, testDate3, false, true, false,3);
        Assessment assessment6 = new Assessment(6, "Assessment6", testDate0, testDate2, true, false, false,3);
        repo.insertAssessment(assessment1);
        repo.insertAssessment(assessment2);
        repo.insertAssessment(assessment3);
        repo.insertAssessment(assessment4);
        repo.insertAssessment(assessment5);
        repo.insertAssessment(assessment6);
        }
    }
}