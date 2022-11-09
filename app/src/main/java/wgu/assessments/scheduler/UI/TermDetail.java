package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import wgu.assessments.scheduler.Database.Repository;
import wgu.assessments.scheduler.Entity.Course;
import wgu.assessments.scheduler.Entity.Term;
import wgu.assessments.scheduler.R;

public class TermDetail extends AppCompatActivity {
    EditText editTermTitle;
    TextView editTermStartDate;
    TextView editTermEndDate;
    String termTitle;
    String termStartDate;
    String termEndDate;

    public String getTermStartDate() {
        return termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public int getTermId() {
        return termId;
    }

    int termId;
    DatePickerDialog.OnDateSetListener endDateDialogListener;
    DatePickerDialog.OnDateSetListener startDateDialogListener;
    final Calendar endDateCalendar = Calendar.getInstance();
    final Calendar startDateCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        editTermTitle = findViewById(R.id.editTermTitle);
        editTermStartDate = findViewById(R.id.editTermStartDate);
        editTermEndDate = findViewById(R.id.editTermEndDate);
        termTitle = getIntent().getStringExtra("termTitle");
        termStartDate = getIntent().getStringExtra("termStartDate");
        termEndDate = getIntent().getStringExtra("termEndDate");
        termId = getIntent().getIntExtra("termId", -1);
        editTermTitle.setText(termTitle);
        editTermStartDate.setText(termStartDate);
        editTermEndDate.setText(termEndDate);

        editTermStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = editTermStartDate.getText().toString();
                try {
                    String dateFormat="MM/dd/yy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                    startDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, startDateDialogListener, startDateCalendar.get(Calendar.YEAR),
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
        editTermEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = editTermEndDate.getText().toString();
                try {
                    String dateFormat="MM/dd/yy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                    endDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
                new DatePickerDialog(TermDetail.this, endDateDialogListener, endDateCalendar.get(Calendar.YEAR),
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
        RecyclerView recyclerView = findViewById(R.id.courseListRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> allCourses = repository.getAllCourses();
        List<Course> associatedCourses = new ArrayList<>();
        for (Course c : allCourses) {
            if (c.getTermId() == termId) associatedCourses.add(c);
        }
        courseAdapter.setCourses(associatedCourses);
    }

    private void updateStartDateLabel(){
        String dateFormat="MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        editTermStartDate.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel(){
        String dateFormat="MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        editTermEndDate.setText(simpleDateFormat.format(endDateCalendar.getTime()));
    }

    public void addNewCourse(View view) {
        Intent intent = new Intent(TermDetail.this, CourseDetail.class);
        intent.putExtra("termId", this.getTermId());
        intent.putExtra("termStartDate", this.getTermStartDate());
        intent.putExtra("termEndDate", this.getTermEndDate());
        startActivity(intent);
    }

    public void onSave(View view) {
        Repository repository = new Repository(getApplication());
        String dateFormat="MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Term term;
        if(termId == -1) {
            int newId = repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermId()+1;

            try {
                Date startDate = simpleDateFormat.parse(editTermStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editTermEndDate.getText().toString());
                term = new Term(newId, editTermTitle.getText().toString(), startDate, endDate);
                repository.insertTerm(term);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Date startDate = simpleDateFormat.parse(editTermStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editTermEndDate.getText().toString());
                term = new Term(termId, editTermTitle.getText().toString(), startDate, endDate);
                repository.updateTerm(term);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(TermDetail.this, TermList.class);
        startActivity(intent);
    }
}