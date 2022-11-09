package wgu.assessments.scheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wgu.assessments.scheduler.Entity.Assessment;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAssessment(Assessment assessment);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query(" SELECT * FROM assessments ORDER BY assessmentId ASC")
    List<Assessment> getAllAssessments();
}

