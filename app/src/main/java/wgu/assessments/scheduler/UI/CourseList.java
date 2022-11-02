package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import wgu.assessments.scheduler.R;

public class CourseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
    }

    public void toHomeScreen(View view) {
        Intent intent = new Intent(CourseList.this, MainActivity.class);
        startActivity(intent);
    }
}