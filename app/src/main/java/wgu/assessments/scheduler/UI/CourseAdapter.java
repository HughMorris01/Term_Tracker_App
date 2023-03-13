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

import wgu.assessments.scheduler.Entity.Course;
import wgu.assessments.scheduler.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView1;
        private final TextView courseItemView2;
        private final TextView courseItemView3;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView1 = itemView.findViewById(R.id.courseListItem);
            courseItemView2 = itemView.findViewById(R.id.courseListItem2);
            courseItemView3 = itemView.findViewById(R.id.courseListItem3);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseId", (current.getCourseId()));
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStatusPosition", current.getCourseStatusPosition());
                    intent.putExtra("courseStartDate", current.getCourseStartDateString());
                    intent.putExtra("courseEndDate", current.getCourseEndDateString());
                    intent.putExtra("courseInstructorName", current.getCourseInstructorName());
                    intent.putExtra("courseInstructorPhone", current.getCourseInstructorPhone());
                    intent.putExtra("courseInstructorEmail", current.getCourseInstructorEmail());
                    intent.putExtra("courseNote", current.getCourseNote());
                    intent.putExtra("termId", (current.getTermId()));
                    context.startActivity(intent);
                }
            });
        }
    }


    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null) {
            Course current = mCourses.get(position);
            String courseName = current.getCourseName();
            holder.courseItemView1.setText(courseName);
            String courseStartDate = current.getCourseStartDateString();
            courseStartDate = "Start: " + courseStartDate;
            holder.courseItemView2.setText(courseStartDate);
            String courseEndDate = current.getCourseEndDateString();
            courseEndDate = "End: " + courseEndDate;
            holder.courseItemView3.setText(courseEndDate);
        }
        else {
            holder.courseItemView1.setText("");
        }
    }

    public void setCourses(List<Course> courseList) {
        mCourses = courseList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses != null) {
            return mCourses.size();
        }
        else {
            return 0;
        }
    }
}
