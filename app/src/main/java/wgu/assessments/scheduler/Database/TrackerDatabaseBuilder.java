package wgu.assessments.scheduler.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import wgu.assessments.scheduler.DAO.AssessmentDAO;
import wgu.assessments.scheduler.DAO.CourseDAO;
import wgu.assessments.scheduler.DAO.TermDAO;
import wgu.assessments.scheduler.Entity.Assessment;
import wgu.assessments.scheduler.Entity.Course;
import wgu.assessments.scheduler.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 10, exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class TrackerDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile TrackerDatabaseBuilder INSTANCE;

    static TrackerDatabaseBuilder getDatabase(Context context) {
        if(INSTANCE == null) {
            synchronized (TrackerDatabaseBuilder.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TrackerDatabaseBuilder.class, "myTrackerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
