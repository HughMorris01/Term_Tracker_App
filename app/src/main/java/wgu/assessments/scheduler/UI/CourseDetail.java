package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import wgu.assessments.scheduler.Database.Repository;
import wgu.assessments.scheduler.Entity.Assessment;
import wgu.assessments.scheduler.Entity.Course;
import wgu.assessments.scheduler.R;

public class CourseDetail extends AppCompatActivity {
    public static int staticCourseId;
    public static String staticCourseName;
    public static int staticCourseStatusPosition;
    public static String staticCourseStartDate;
    public static String staticCourseEndDate;
    public static String staticCourseInstructorName;
    public static String staticCourseInstructorPhone;
    public static String staticCourseInstructorEmail;
    public static String staticCourseNote;
    public static int staticTermId;
    public static Boolean isNoteVisible = false;

    EditText editCourseName;
    Spinner courseStatusSpinner;
    TextView editCourseStartDate;
    TextView editCourseEndDate;
    EditText editCourseInstructorName;
    EditText editCourseInstructorPhone;
    EditText editCourseInstructorEmail;
    EditText editCourseNote;
    CheckBox setCourseStartAlertCheckBox;
    CheckBox setCourseEndAlertCheckBox;

    int courseId;
    String courseName;
    int courseStatusPosition;
    String courseStartDate;
    String courseEndDate;
    String courseInstructorName;
    String courseInstructorPhone;
    String courseInstructorEmail;
    String courseNote;
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        courseId = getIntent().getIntExtra("courseId", -1);
        if (courseId == -1) {
            courseId = staticCourseId;
            courseName = staticCourseName;
            courseStatusPosition = staticCourseStatusPosition;
            courseStartDate = staticCourseStartDate;
            courseEndDate = staticCourseEndDate;
            courseInstructorName = staticCourseInstructorName;
            courseInstructorPhone = staticCourseInstructorPhone;
            courseInstructorEmail = staticCourseInstructorEmail;
            courseNote = staticCourseNote;
            termId = staticTermId;
        } else {
            staticCourseId = courseId;
            courseName = getIntent().getStringExtra("courseName");
            staticCourseName = courseName;
            courseStatusPosition = getIntent().getIntExtra("courseStatusPosition", -1);
            staticCourseStatusPosition = courseStatusPosition;
            courseStartDate = getIntent().getStringExtra("courseStartDate");
            staticCourseStartDate = courseStartDate;
            courseEndDate = getIntent().getStringExtra("courseEndDate");
            staticCourseEndDate = courseEndDate;
            courseInstructorName = getIntent().getStringExtra("courseInstructorName");
            staticCourseInstructorName = courseInstructorName;
            courseInstructorPhone = getIntent().getStringExtra("courseInstructorPhone");
            staticCourseInstructorPhone = courseInstructorPhone;
            courseInstructorEmail = getIntent().getStringExtra("courseInstructorEmail");
            staticCourseInstructorEmail = courseInstructorEmail;
            courseNote = getIntent().getStringExtra("courseNote");
            staticCourseNote = courseNote;
            termId = getIntent().getIntExtra("termId", -1);
            staticTermId = termId;
        }

        editCourseName = findViewById(R.id.editCourseName);
        courseStatusSpinner = findViewById(R.id.courseStatusSpinner);
        editCourseStartDate = findViewById(R.id.editCourseStartDate);
        editCourseEndDate = findViewById(R.id.editCourseEndDate);
        editCourseInstructorName = findViewById(R.id.editInstructorName);
        editCourseInstructorPhone = findViewById(R.id.editInstructorPhone);
        editCourseInstructorEmail = findViewById(R.id.editInstructorEmail);
        editCourseNote = findViewById(R.id.editCourseNote);
        setCourseStartAlertCheckBox = findViewById(R.id.setCourseStartAlert);
        setCourseEndAlertCheckBox = findViewById(R.id.setCourseEndAlert);

        editCourseName.setText(courseName);
        editCourseStartDate.setText(courseStartDate);
        editCourseEndDate.setText(courseEndDate);
        editCourseInstructorName.setText(courseInstructorName);
        editCourseInstructorPhone.setText(courseInstructorPhone);
        editCourseInstructorEmail.setText(courseInstructorEmail);
        editCourseNote.setText(courseNote);
        isNoteVisible = false;

        ArrayAdapter<CharSequence> courseStatusSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.course_status_spinner, android.R.layout.simple_spinner_item);
        courseStatusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseStatusSpinner.setAdapter(courseStatusSpinnerAdapter);
        courseStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(CourseDetail.this, courseStatusSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                courseStatusPosition = courseStatusSpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        courseStatusSpinner.setSelection(courseStatusPosition);
        editCourseStartDate.setOnClickListener(view -> {
            String dateString = editCourseStartDate.getText().toString();
            try {
                startDateCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(dateString)));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            new DatePickerDialog(CourseDetail.this, startDateDialogListener, startDateCalendar.get(Calendar.YEAR),
                    startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        startDateDialogListener = (view, year, month, day) -> {
            startDateCalendar.set(Calendar.YEAR, year);
            startDateCalendar.set(Calendar.MONTH, month);
            startDateCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateStartDateLabel();
        };
        editCourseEndDate.setOnClickListener(view -> {
            String dateString = editCourseEndDate.getText().toString();
            try {
                endDateCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(dateString)));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            new DatePickerDialog(CourseDetail.this, endDateDialogListener, endDateCalendar.get(Calendar.YEAR),
                    endDateCalendar.get(Calendar.MONTH), endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        endDateDialogListener = (view, year, month, day) -> {
            endDateCalendar.set(Calendar.YEAR, year);
            endDateCalendar.set(Calendar.MONTH, month);
            endDateCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateEndDateLabel();
        };

        Repository repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> assessmentList = repository.getAllAssessments();
        List<Assessment> associatedAssessments = new ArrayList<>();
        for (Assessment a : assessmentList) {
            if (a.getCourseId() == courseId) associatedAssessments.add(a);
        }
        assessmentAdapter.setAssessments(associatedAssessments);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course_detail, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemsSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveCourse:
                onSave();
                return true;
            case R.id.deleteCourse:
                onDelete();
                return true;
            case R.id.addAssessment:
                addNewAssessment();
                return true;
            case R.id.shareNote:
                shareNote();
                return true;
            case R.id.startAlert:
                setStartNotification();
                return true;
            case R.id.endAlert:
                setEndNotification();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStartDateLabel(){
        editCourseStartDate.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel(){
        editCourseEndDate.setText(simpleDateFormat.format(endDateCalendar.getTime()));
    }

    public void addNewAssessment(View view) {
        if(courseId == -1) {
            Toast.makeText(CourseDetail.this, "Assessments cannot be added until a course is saved", Toast.LENGTH_LONG).show();
            return;
        }
        if(AssessmentDetail.assessmentCounter >= 5) {
            Toast.makeText(CourseDetail.this, "Maximum number of assessments have been assigned to this course. Please delete an assessment to create a new one.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(CourseDetail.this, AssessmentDetail.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void addNewAssessment() {
        if(courseId == -1) {
            Toast.makeText(CourseDetail.this, "Assessments cannot be added until a course is saved", Toast.LENGTH_LONG).show();
            return;
        }
        if(AssessmentDetail.assessmentCounter >= 5) {
            Toast.makeText(CourseDetail.this, "Maximum number of assessments have been assigned to this course. Please delete an assessment to create a new one.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(CourseDetail.this, AssessmentDetail.class);
        intent.putExtra("courseId", courseId);
        startActivity(intent);
    }

    public void onSave(View view) {
        Repository repository = new Repository(getApplication());
        String dateFormat="MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Course course;
        if(courseId == -1) {
            int newCourseId;
            if (repository.getAllCourses().size() != 0) {
                newCourseId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId() + 1;
            }
            else {newCourseId = 1;}
            try {
                String courseName = editCourseName.getText().toString();
                int courseStatusPosition = courseStatusSpinner.getSelectedItemPosition();
                Date startDate = simpleDateFormat.parse(editCourseStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editCourseEndDate.getText().toString());
                String courseInstructor = editCourseInstructorName.getText().toString();
                String courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                String courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                String courseNote = editCourseNote.getText().toString();

                course = new Course(newCourseId, courseName, courseStatusPosition, startDate, endDate, courseInstructor, courseInstructorPhone, courseInstructorEmail, courseNote, termId);
                repository.insertCourse(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String courseName = editCourseName.getText().toString();
                int courseStatusPosition = courseStatusSpinner.getSelectedItemPosition();
                Date startDate = simpleDateFormat.parse(editCourseStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editCourseEndDate.getText().toString());
                String courseInstructor = editCourseInstructorName.getText().toString();
                String courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                String courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                String courseNote = editCourseNote.getText().toString();

                course = new Course(this.courseId, courseName, courseStatusPosition, startDate, endDate, courseInstructor, courseInstructorPhone, courseInstructorEmail, courseNote, this.termId);
                repository.updateCourse(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(CourseDetail.this, TermDetail.class);
        if (setCourseStartAlertCheckBox.isChecked()){
            setStartNotification();
        }
        if (setCourseEndAlertCheckBox.isChecked()){
            setEndNotification();
        }
        startActivity(intent);
    }

    public void onSave() {
        Repository repository = new Repository(getApplication());
        String dateFormat="MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Course course;
        if(courseId == -1) {
            int newCourseId;
            if (repository.getAllCourses().size() != 0) {
                newCourseId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId() + 1;
            }
            else {newCourseId = 1;}
            try {
                String courseName = editCourseName.getText().toString();
                int courseStatusPosition = courseStatusSpinner.getSelectedItemPosition();
                Date startDate = simpleDateFormat.parse(editCourseStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editCourseEndDate.getText().toString());
                String courseInstructor = editCourseInstructorName.getText().toString();
                String courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                String courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                String courseNote = editCourseNote.getText().toString();

                course = new Course(newCourseId, courseName, courseStatusPosition, startDate, endDate, courseInstructor, courseInstructorPhone, courseInstructorEmail, courseNote, termId);
                repository.insertCourse(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String courseName = editCourseName.getText().toString();
                int courseStatusPosition = courseStatusSpinner.getSelectedItemPosition();
                Date startDate = simpleDateFormat.parse(editCourseStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editCourseEndDate.getText().toString());
                String courseInstructor = editCourseInstructorName.getText().toString();
                String courseInstructorPhone = editCourseInstructorPhone.getText().toString();
                String courseInstructorEmail = editCourseInstructorEmail.getText().toString();
                String courseNote = editCourseNote.getText().toString();

                course = new Course(this.courseId, courseName, courseStatusPosition, startDate, endDate, courseInstructor, courseInstructorPhone, courseInstructorEmail, courseNote, this.termId);
                repository.updateCourse(course);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(CourseDetail.this, TermDetail.class);
        if (setCourseStartAlertCheckBox.isChecked()){
            setStartNotification();
        }
        if (setCourseEndAlertCheckBox.isChecked()){
            setEndNotification();
        }
        startActivity(intent);
    }

    public void onDelete() {
        Repository repository = new Repository(getApplication());

        Course course = null;
        if(courseId == -1) {
            Toast.makeText(CourseDetail.this, "Course has not yet been saved/created, nothing to delete",Toast.LENGTH_LONG).show();
            return;
        }
        for(Course tempCourse : repository.getAllCourses()) {
            if(tempCourse.getCourseId() == courseId){
                course = tempCourse;
                break;
            }
        }
        int numAssessments = 0;
        for (Assessment assessment : repository.getAllAssessments()) {
            assert course != null;
            if(assessment.getCourseId() == course.getCourseId()) {
                ++numAssessments;
            }
        }
        if (numAssessments == 0) {
            repository.deleteCourse(course);
            assert course != null;
            Toast.makeText(CourseDetail.this, course.getCourseName() + " has been successfully deleted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(CourseDetail.this, course.getCourseName() + " has assessments assigned to it and cannot be deleted.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(CourseDetail.this, TermDetail.class);
        startActivity(intent);
    }

    public void shareNote() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "This is the shared note");
        sendIntent.putExtra(Intent.EXTRA_TEXT, editCourseNote.getText().toString());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void setStartNotification() {
        String startDateString = editCourseStartDate.getText().toString();
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(startDateString);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assert startDate != null;
        long trigger = startDate.getTime();
        Intent intent = new Intent(CourseDetail.this, AppReceiver.class);
        intent.putExtra("key", editCourseName.getText().toString() + " starts today!");
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseDetail.this, MainActivity.numAlert++, intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);
    }

    public void setEndNotification() {
        String endDateString = editCourseEndDate.getText().toString();
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assert endDate != null;
        long trigger2 = endDate.getTime();
        Intent intent2 = new Intent(CourseDetail.this, AppReceiver.class);
        intent2.putExtra("key", courseName + " ends today!");
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent2 = PendingIntent.getBroadcast(CourseDetail.this, MainActivity.numAlert++, intent2,0);
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, pendingIntent2);
    }

    public void displayNote(View view) {
        if(!isNoteVisible) {
            editCourseNote.setVisibility(View.VISIBLE);
            isNoteVisible = true;
        }
        else {
            editCourseNote.setVisibility(View.INVISIBLE);
            isNoteVisible = false;
        }
    }
}