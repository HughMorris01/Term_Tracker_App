package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import wgu.assessments.scheduler.Database.Repository;
import wgu.assessments.scheduler.Entity.Assessment;
import wgu.assessments.scheduler.R;

public class AssessmentDetail extends AppCompatActivity {

    EditText editAssessmentName;
    TextView editAssessmentStartDate;
    TextView editAssessmentEndDate;
    RadioGroup radioGroup;
    RadioButton objectiveAssessmentRadioButton;
    RadioButton performanceAssessmentRadioButton;
    CheckBox setCourseStartAlertCheckBox;
    CheckBox setCourseEndAlertCheckBox;

    int assessmentId;
    String assessmentName;
    String assessmentStartDate;
    String assessmentEndDate;
    Boolean isObjectiveAssessment;
    Boolean isPerformanceAssessment;
    Boolean isAlertSet;
    int courseId;
    public static int assessmentCounter = 0;

    DatePickerDialog.OnDateSetListener startDateDialogListener;
    DatePickerDialog.OnDateSetListener endDateDialogListener;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();
    String dateFormat="MM/dd/yy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentStartDate = getIntent().getStringExtra("assessmentStartDate");
        assessmentEndDate = getIntent().getStringExtra("assessmentEndDate");
        isObjectiveAssessment = getIntent().getBooleanExtra("isObjectiveAssessment", false);
        isPerformanceAssessment = getIntent().getBooleanExtra("isPerformanceAssessment", false);
        isAlertSet = getIntent().getBooleanExtra("isAlertSet", false);
        courseId = getIntent().getIntExtra("courseId", -1);

        editAssessmentName = findViewById(R.id.editAssessmentName);
        editAssessmentStartDate = findViewById(R.id.editAssessmentStartDate);
        editAssessmentEndDate = findViewById(R.id.editAssessmentEndDate);
        radioGroup = findViewById(R.id.radioGroup);
        objectiveAssessmentRadioButton = findViewById(R.id.objectiveAssessmentRadioButton);
        performanceAssessmentRadioButton = findViewById(R.id.performanceAssessmentRadioButton);
        setCourseStartAlertCheckBox = findViewById(R.id.setCourseStartAlert);
        setCourseEndAlertCheckBox = findViewById(R.id.setCourseEndAlert);

        editAssessmentName.setText(assessmentName);
        editAssessmentStartDate.setText(assessmentStartDate);
        editAssessmentEndDate.setText(assessmentEndDate);
        if (isObjectiveAssessment) { objectiveAssessmentRadioButton.setChecked(true); }
        else if (isPerformanceAssessment) { performanceAssessmentRadioButton.setChecked(true); }
        //if (isAlertSet) { alertCheckBox.setChecked(true); }

        editAssessmentStartDate.setOnClickListener(view -> {
            String dateString = editAssessmentStartDate.getText().toString();
            try {
                startDateCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(dateString)));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            new DatePickerDialog(AssessmentDetail.this, startDateDialogListener, startDateCalendar.get(Calendar.YEAR),
                    startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        startDateDialogListener = (view, year, month, day) -> {
            startDateCalendar.set(Calendar.YEAR, year);
            startDateCalendar.set(Calendar.MONTH, month);
            startDateCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateStartDateLabel();
        };
        editAssessmentEndDate.setOnClickListener(view -> {
            String dateString = editAssessmentEndDate.getText().toString();
            try {
                endDateCalendar.setTime(Objects.requireNonNull(simpleDateFormat.parse(dateString)));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            new DatePickerDialog(AssessmentDetail.this, endDateDialogListener, endDateCalendar.get(Calendar.YEAR),
                    endDateCalendar.get(Calendar.MONTH), endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        endDateDialogListener = (view, year, month, day) -> {
            endDateCalendar.set(Calendar.YEAR, year);
            endDateCalendar.set(Calendar.MONTH, month);
            endDateCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateEndDateLabel();
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment_detail, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemsSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.saveAssessment:
                onSave();
                return true;
            case R.id.deleteAssessment:
                onDelete();
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
        editAssessmentStartDate.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel(){
        editAssessmentEndDate.setText(simpleDateFormat.format(endDateCalendar.getTime()));
    }

    public void onSave(View view) {
        Repository repository = new Repository(getApplication());
        String dateFormat="MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Assessment assessment;
        if(assessmentId == -1) {
            assessmentCounter++;
            int newAssessmentId;
            if (repository.getAllAssessments().size() != 0) {
                newAssessmentId = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentId() + 1;
            }
            else {newAssessmentId = 1;}
            try {
                String assessmentName = editAssessmentName.getText().toString();
                Date startDate = simpleDateFormat.parse(editAssessmentStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editAssessmentEndDate.getText().toString());
                if (objectiveAssessmentRadioButton.isChecked()) {
                    isObjectiveAssessment = true;
                    isPerformanceAssessment = false;
                }
                else if (performanceAssessmentRadioButton.isChecked()) {
                    isPerformanceAssessment = true;
                    isObjectiveAssessment = false;
                }
                else {
                    Toast.makeText(AssessmentDetail.this, "The type of assessment must be chosen", Toast.LENGTH_LONG).show();
                    return;
                }
                if(setCourseStartAlertCheckBox.isChecked()){
                    isAlertSet = true;
                }
                assessment = new Assessment(newAssessmentId, assessmentName, startDate, endDate,
                        isObjectiveAssessment, isPerformanceAssessment, isAlertSet, courseId);
                repository.insertAssessment(assessment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String assessmentName = editAssessmentName.getText().toString();
                Date startDate = simpleDateFormat.parse(editAssessmentStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editAssessmentEndDate.getText().toString());
                if (objectiveAssessmentRadioButton.isChecked()) {
                    isObjectiveAssessment = true;
                    isPerformanceAssessment = false;
                }
                else if (performanceAssessmentRadioButton.isChecked()) {
                    isPerformanceAssessment = true;
                    isObjectiveAssessment = false;
                }
                else {
                    Toast.makeText(AssessmentDetail.this, "The type of assessment must be chosen", Toast.LENGTH_LONG).show();
                    return;
                }
                isAlertSet = setCourseStartAlertCheckBox.isChecked();
                assessment = new Assessment(assessmentId, assessmentName, startDate, endDate,
                                 isObjectiveAssessment, isPerformanceAssessment, isAlertSet, courseId);
                repository.updateAssessment(assessment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(AssessmentDetail.this, CourseDetail.class);
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
        Assessment assessment;
        if(assessmentId == -1) {
            assessmentCounter++;
            int newAssessmentId;
            if (repository.getAllAssessments().size() != 0) {
                newAssessmentId = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentId() + 1;
            }
            else {newAssessmentId = 1;}
            try {
                String assessmentName = editAssessmentName.getText().toString();
                Date startDate = simpleDateFormat.parse(editAssessmentStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editAssessmentEndDate.getText().toString());
                if (objectiveAssessmentRadioButton.isChecked()) {
                    isObjectiveAssessment = true;
                    isPerformanceAssessment = false;
                }
                else if (performanceAssessmentRadioButton.isChecked()) {
                    isPerformanceAssessment = true;
                    isObjectiveAssessment = false;
                }
                else {
                    Toast.makeText(AssessmentDetail.this, "The type of assessment must be chosen", Toast.LENGTH_LONG).show();
                    return;
                }
                if(setCourseStartAlertCheckBox.isChecked()){
                    isAlertSet = true;
                }
                assessment = new Assessment(newAssessmentId, assessmentName, startDate, endDate,
                        isObjectiveAssessment, isPerformanceAssessment, isAlertSet, courseId);
                repository.insertAssessment(assessment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                String assessmentName = editAssessmentName.getText().toString();
                Date startDate = simpleDateFormat.parse(editAssessmentStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editAssessmentEndDate.getText().toString());
                if (objectiveAssessmentRadioButton.isChecked()) {
                    isObjectiveAssessment = true;
                    isPerformanceAssessment = false;
                }
                else if (performanceAssessmentRadioButton.isChecked()) {
                    isPerformanceAssessment = true;
                    isObjectiveAssessment = false;
                }
                else {
                    Toast.makeText(AssessmentDetail.this, "The type of assessment must be chosen", Toast.LENGTH_LONG).show();
                    return;
                }
                //isAlertSet = alertCheckBox.isChecked();
                assessment = new Assessment(assessmentId, assessmentName, startDate, endDate,
                        isObjectiveAssessment, isPerformanceAssessment, isAlertSet, courseId);
                repository.updateAssessment(assessment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(AssessmentDetail.this, CourseDetail.class);
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

        Assessment assessment = null;
        if(assessmentId == -1) {
            Toast.makeText(AssessmentDetail.this, "Assessment has not yet been saved/created, nothing to delete",Toast.LENGTH_LONG).show();
            return;
        }
        for(Assessment tempAssessment : repository.getAllAssessments()) {
            if(tempAssessment.getAssessmentId() == assessmentId){
                assessment = tempAssessment;
                break;
            }
        }
        repository.deleteAssessment(assessment);
        assert assessment != null;
        Toast.makeText(AssessmentDetail.this, assessment.getAssessmentName() + " has been successfully deleted", Toast.LENGTH_LONG).show();
        assessmentCounter--;
        Intent intent = new Intent(AssessmentDetail.this, CourseDetail.class);
        startActivity(intent);
    }

    private void setStartNotification() {
        String startDateString = editAssessmentStartDate.getText().toString();
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(startDateString);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assert startDate != null;
        long trigger = startDate.getTime();
        Intent intent = new Intent(AssessmentDetail.this, AppReceiver.class);
        intent.putExtra("key", editAssessmentName.getText().toString() + " starts today!");
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(AssessmentDetail.this, MainActivity.numAlert++, intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pendingIntent);
    }

    private void setEndNotification() {
        String endDateString = editAssessmentEndDate.getText().toString();
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        assert endDate != null;
        long trigger2 = endDate.getTime();
        Intent intent2 = new Intent(AssessmentDetail.this, AppReceiver.class);
        intent2.putExtra("key", assessmentName + " ends today!");
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent2 = PendingIntent.getBroadcast(AssessmentDetail.this, MainActivity.numAlert++, intent2,0);
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, pendingIntent2);
    }
}