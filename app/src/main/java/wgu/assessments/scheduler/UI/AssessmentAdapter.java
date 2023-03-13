package wgu.assessments.scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.assessments.scheduler.Entity.Assessment;
import wgu.assessments.scheduler.R;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;
        private final TextView assessmentItemView2;
        private final TextView assessmentItemView3;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentListItem);
            assessmentItemView2 = itemView.findViewById(R.id.assessmentListItem2);
            assessmentItemView3 = itemView.findViewById(R.id.assessmentListItem3);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentStartDate", current.getAssessmentStartDateString());
                    intent.putExtra("assessmentEndDate", current.getAssessmentEndDateString());
                    intent.putExtra("isObjectiveAssessment", current.getIsObjectiveAssessment());
                    intent.putExtra("isPerformanceAssessment", current.getIsPerformanceAssessment());
                    intent.putExtra("isAlertSet", current.getIsAlertSet());
                    intent.putExtra("courseId", current.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String assessmentName = current.getAssessmentName();
            holder.assessmentItemView.setText(assessmentName);
            String assessmentStartDateString = current.getAssessmentStartDateString();
            assessmentStartDateString = "Start: " + assessmentStartDateString;
            holder.assessmentItemView2.setText(assessmentStartDateString);
            String assessmentEndDateString = current.getAssessmentEndDateString();
            assessmentEndDateString = "End: " + assessmentEndDateString;
            holder.assessmentItemView3.setText(assessmentEndDateString);
        }
        else {
            holder.assessmentItemView.setText("No terms entered");
        }
    }

    public void setAssessments(List<Assessment> assessmentList) {
        mAssessments = assessmentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mAssessments != null) {
            return mAssessments.size();
        }
        else {
            return 0;
        }
    }
}
