package wgu.assessments.scheduler.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.assessments.scheduler.Entity.Term;
import wgu.assessments.scheduler.R;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termListItem;
        private final TextView termListItem2;
        private final TextView termListItem3;

        private TermViewHolder(View itemView) {
            super(itemView);
            termListItem = itemView.findViewById(R.id.termListItem);
            termListItem2 = itemView.findViewById(R.id.termListItem2);
            termListItem3 = itemView.findViewById(R.id.termListItem3);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetail.class);
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("termTitle", current.getTermName());
                    intent.putExtra("termStartDate", current.getTermStartDateString());
                    intent.putExtra("termEndDate", current.getTermEndDateString());
                    context.startActivity(intent);
                }
            });
        }
    }


    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms != null) {
            Term current = mTerms.get(position);
            String termName = current.getTermName();
            holder.termListItem.setText(String.valueOf(termName));
            String termStartDate = current.getTermStartDateString();
            termStartDate = "Start: " + termStartDate;
            holder.termListItem2.setText(termStartDate);
            String termEndDate = current.getTermEndDateString();
            termEndDate = "End: " + termEndDate;
            holder.termListItem3.setText(termEndDate);
        }
        else {
            holder.termListItem.setText("No terms have been entered yet");
        }
    }

    public void setTerms(List<Term> termsList) {
        mTerms = termsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mTerms != null) {
            return mTerms.size();
        }
        else {
            return 0;
        }
    }
}
