package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wgu.assessments.scheduler.Database.Repository;
import wgu.assessments.scheduler.Entity.Assessment;
import wgu.assessments.scheduler.Entity.Course;
import wgu.assessments.scheduler.Entity.CourseStatus;
import wgu.assessments.scheduler.Entity.Term;
import wgu.assessments.scheduler.R;

public class CourseDetail extends AppCompatActivity {
    EditText editCourseName;
    TextView editCourseStartDate;
    TextView editCourseEndDate;
    EditText editCourseInstructorName;
    EditText editCourseInstructorPhone;
    EditText editCourseInstructorEmail;
    EditText editCourseStatus;
    int courseId;
    String courseName;
    String courseStartDate;
    String courseEndDate;
    String courseInstructorName;
    String courseInstructorPhone;
    String courseInstructorEmail;
    String courseStatus;
    int termId;
    DatePickerDialog.OnDateSetListener startDateDialogListener;
    DatePickerDialog.OnDateSetListener endDateDialogListener;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();
    String dateFormat="MM/dd/yy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        editCourseName = findViewById(R.id.editCourseName);
        editCourseStartDate = findViewById(R.id.editCourseStartDate);
        editCourseEndDate = findViewById(R.id.editCourseEndDate);
        editCourseStatus = findViewById(R.id.editCourseStatus);
        editCourseInstructorName = findViewById(R.id.editInstructorName);
        editCourseInstructorPhone = findViewById(R.id.editInstructorPhone);
        editCourseInstructorEmail = findViewById(R.id.editInstructorEmail);
        courseId = getIntent().getIntExtra("courseId", -1);
        courseName = getIntent().getStringExtra("courseName");
        courseStartDate = getIntent().getStringExtra("courseStartDate");
        courseEndDate = getIntent().getStringExtra("courseEndDate");
        courseInstructorName = getIntent().getStringExtra("courseInstructorName");
        courseInstructorPhone = getIntent().getStringExtra("courseInstructorPhone");
        courseInstructorEmail = getIntent().getStringExtra("courseInstructorEmail");
        courseStatus = getIntent().getStringExtra("courseStatus");
        termId = getIntent().getIntExtra("termId", -1);
        editCourseName.setText(courseName);
        editCourseStartDate.setText(courseStartDate);
        editCourseEndDate.setText(courseEndDate);
        editCourseInstructorName.setText(courseInstructorName);
        editCourseInstructorPhone.setText(courseInstructorPhone);
        editCourseInstructorEmail.setText(courseInstructorEmail);
        editCourseStatus.setText(courseStatus);

        editCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = editCourseStartDate.getText().toString();
                try {
                    startDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, startDateDialogListener, startDateCalendar.get(Calendar.YEAR),
                        endDateCalendar.get(Calendar.MONDAY), endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDateDialogListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day){
                startDateCalendar.set(Calendar.YEAR, year);
                startDateCalendar.set(Calendar.MONTH, month);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateStartDateLabel();
            }
        };
        editCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = editCourseEndDate.getText().toString();
                try {
                    endDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, endDateDialogListener, endDateCalendar.get(Calendar.YEAR),
                        endDateCalendar.get(Calendar.MONDAY), endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDateDialogListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day){
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, month);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateEndDateLabel();
            }
        };

        Repository repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);
        List<Assessment> assessmentList = repository.getAllAssessments();
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(assessmentList);
    }

    private void updateStartDateLabel(){
        editCourseStartDate.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel(){
        editCourseEndDate.setText(simpleDateFormat.format(endDateCalendar.getTime()));
    }

    public void addNewAssessment(View view) {
        Intent intent = new Intent(CourseDetail.this, AssessmentDetail.class);
        startActivity(intent);
    }

    public void onSave(View view) {
        Repository repository = new Repository(getApplication());
        String dateFormat="MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Course course;
        if(courseId == -1) {
            try {
                int newCourseId = repository.getAllCourses().get(repository.getAllCourses().size()-1).getCourseId()+1;
                String courseName = editCourseName.getText().toString();
                Date startDate = simpleDateFormat.parse(editCourseStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editCourseEndDate.getText().toString());
                String courseInstructor = editCourseInstructorName.getText().toString();
                String courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                String courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                String courseStatus = editCourseStatus.getText().toString();

                course = new Course(newCourseId, courseName, startDate, endDate, courseInstructor, courseInstructorPhone, courseInstructorEmail, courseStatus, this.termId);
                repository.insertCourse(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String courseName = editCourseName.getText().toString();
                Date startDate = simpleDateFormat.parse(editCourseStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editCourseEndDate.getText().toString());
                String courseInstructor = editCourseInstructorName.getText().toString();
                String courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                String courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                String courseStatus = editCourseStatus.getText().toString();

                course = new Course(courseId, courseName, startDate, endDate, courseInstructor, courseInstructorPhone, courseInstructorEmail, courseStatus, this.termId);;
                repository.updateCourse(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(CourseDetail.this, TermDetail.class);
        startActivity(intent);
    }
}