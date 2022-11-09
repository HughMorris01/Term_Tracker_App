package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import wgu.assessments.scheduler.R;

public class AssessmentDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
    }

    public void saveAssessment(View view) {
        Intent intent = new Intent(AssessmentDetail.this, CourseDetail.class);
        startActivity(intent);
    }
}