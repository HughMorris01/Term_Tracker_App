package wgu.assessments.scheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Objects;

import wgu.assessments.scheduler.Database.Repository;
import wgu.assessments.scheduler.Entity.Term;
import wgu.assessments.scheduler.R;

public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Repository repo = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.termListRecyclerView);
        List<Term> termsList = repo.getAllTerms();
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(termsList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }

    public boolean onOptionsItemsSelected(MenuItem item){
        if (item.getItemId() == R.id.createTerm) {
            createNewTerm();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void createNewTerm(View view) {
        TermDetail.staticTermId = -1;
        TermDetail.staticTermName = null;
        TermDetail.staticTermStartDate = null;
        TermDetail.staticTermEndDate = null;
        Intent intent = new Intent(TermList.this, TermDetail.class);
        startActivity(intent);
    }

    public void createNewTerm() {
        TermDetail.staticTermId = -1;
        TermDetail.staticTermName = null;
        TermDetail.staticTermStartDate = null;
        TermDetail.staticTermEndDate = null;
        Intent intent = new Intent(TermList.this, TermDetail.class);
        startActivity(intent);
    }


}