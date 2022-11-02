package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import wgu.assessments.scheduler.R;

public class TermDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
    }

    public void toTermList(View view) {
        Intent intent = new Intent(TermDetail.this, TermList.class);
        startActivity(intent);
    }
}