package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    public static int staticTermId;
    public static String staticTermName;
    public static String staticTermStartDate;
    public static String staticTermEndDate;

    EditText editTermName;
    TextView editTermStartDate;
    TextView editTermEndDate;

    int termId;
    String termName;
    String termStartDate;
    String termEndDate;

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
        termId = getIntent().getIntExtra("termId", -1);
        if (termId == -1 ) {
            termId = staticTermId;
            termName = staticTermName;
            termStartDate = staticTermStartDate;
            termEndDate = staticTermEndDate;
        } else {
            staticTermId = termId;
            termName = getIntent().getStringExtra("termTitle");
            staticTermName = termName;
            termStartDate = getIntent().getStringExtra("termStartDate");
            staticTermStartDate = termStartDate;
            termEndDate = getIntent().getStringExtra("termEndDate");
            staticTermEndDate = termEndDate;
        }
        editTermName = findViewById(R.id.editTermName);
        editTermStartDate = findViewById(R.id.editTermStartDate);
        editTermEndDate = findViewById(R.id.editTermEndDate);
        editTermName.setText(termName);
        editTermStartDate.setText(termStartDate);
        editTermEndDate.setText(termEndDate);

        editTermStartDate.setOnClickListener(view -> {
            String dateString = editTermStartDate.getText().toString();
            try {
                String dateFormat="MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                startDateCalendar.setTime(simpleDateFormat.parse(dateString));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            new DatePickerDialog(TermDetail.this, startDateDialogListener, startDateCalendar.get(Calendar.YEAR),
                    startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        startDateDialogListener = (view, year, month, day) -> {
            startDateCalendar.set(Calendar.YEAR, year);
            startDateCalendar.set(Calendar.MONTH, month);
            startDateCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateStartDateLabel();
        };
        editTermEndDate.setOnClickListener(view -> {
            String dateString = editTermEndDate.getText().toString();
            try {
                String dateFormat="MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                endDateCalendar.setTime(simpleDateFormat.parse(dateString));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            new DatePickerDialog(TermDetail.this, endDateDialogListener, endDateCalendar.get(Calendar.YEAR),
                    endDateCalendar.get(Calendar.MONTH), endDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        endDateDialogListener = (view, year, month, day) -> {
            endDateCalendar.set(Calendar.YEAR, year);
            endDateCalendar.set(Calendar.MONTH, month);
            endDateCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateEndDateLabel();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_detail, menu);
        return true;
    }

    public boolean onOptionsItemsSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.saveTerm:
                onSave();
                return true;
            case R.id.deleteTerm:
                onDelete();
                return true;
            case R.id.addCourse:
                addNewCourse();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        if(termId == -1) {
            Toast.makeText(TermDetail.this, "Courses cannot be added until a term is saved", Toast.LENGTH_LONG).show();
            return;
        }
        CourseDetail.staticCourseId = -1;
        CourseDetail.staticCourseName = null;
        CourseDetail.staticCourseStatusPosition = -1;
        CourseDetail.staticCourseStartDate = null;
        CourseDetail.staticCourseEndDate = null;
        CourseDetail.staticCourseInstructorName = null;
        CourseDetail.staticCourseInstructorPhone = null;
        CourseDetail.staticCourseInstructorEmail = null;
        CourseDetail.staticCourseNote = null;
        CourseDetail.staticTermId = termId;
        Intent intent = new Intent(TermDetail.this, CourseDetail.class);
        startActivity(intent);
    }

    public void addNewCourse() {
        if(termId == -1) {
            Toast.makeText(TermDetail.this, "Courses cannot be added until a term is saved", Toast.LENGTH_LONG).show();
            return;
        }
        CourseDetail.staticCourseId = -1;
        CourseDetail.staticCourseName = null;
        CourseDetail.staticCourseStatusPosition = -1;
        CourseDetail.staticCourseStartDate = null;
        CourseDetail.staticCourseEndDate = null;
        CourseDetail.staticCourseInstructorName = null;
        CourseDetail.staticCourseInstructorPhone = null;
        CourseDetail.staticCourseInstructorEmail = null;
        CourseDetail.staticCourseNote = null;
        CourseDetail.staticTermId = termId;
        Intent intent = new Intent(TermDetail.this, CourseDetail.class);
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
                term = new Term(newId, editTermName.getText().toString(), startDate, endDate);
                repository.insertTerm(term);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Date startDate = simpleDateFormat.parse(editTermStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editTermEndDate.getText().toString());
                term = new Term(termId, editTermName.getText().toString(), startDate, endDate);
                repository.updateTerm(term);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(TermDetail.this, TermList.class);
        startActivity(intent);
    }

    public void onSave() {
        Repository repository = new Repository(getApplication());
        String dateFormat="MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Term term;
        if(termId == -1) {
            int newId = repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermId()+1;

            try {
                Date startDate = simpleDateFormat.parse(editTermStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editTermEndDate.getText().toString());
                term = new Term(newId, editTermName.getText().toString(), startDate, endDate);
                repository.insertTerm(term);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Date startDate = simpleDateFormat.parse(editTermStartDate.getText().toString());
                Date endDate = simpleDateFormat.parse(editTermEndDate.getText().toString());
                term = new Term(termId, editTermName.getText().toString(), startDate, endDate);
                repository.updateTerm(term);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(TermDetail.this, TermList.class);
        startActivity(intent);
    }

    public void onDelete() {
        Repository repository = new Repository(getApplication());

        Term term = null;
        if(termId == -1) {
            Toast.makeText(TermDetail.this, "Term has not yet been saved/created, nothing to delete",Toast.LENGTH_LONG).show();
            return;
        }
        for(Term tempTerm : repository.getAllTerms()) {
            if(tempTerm.getTermId() == termId){
                term = tempTerm;
                break;
            }
        }
        int numCourses = 0;
        for (Course course : repository.getAllCourses()) {
            assert term != null;
            if(course.getTermId() == term.getTermId()) {
                ++numCourses;
            }
        }
        if (numCourses == 0) {
            repository.deleteTerm(term);
            assert term != null;
            Toast.makeText(TermDetail.this, term.getTermName() + " has been successfully deleted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TermDetail.this, TermList.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(TermDetail.this, term.getTermName() + " has courses assigned to it and cannot be deleted.", Toast.LENGTH_LONG).show();
        }
    }
}