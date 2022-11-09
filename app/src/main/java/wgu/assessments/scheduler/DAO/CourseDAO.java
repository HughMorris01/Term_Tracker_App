package wgu.assessments.scheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import wgu.assessments.scheduler.Entity.Course;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCourse(Course course);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query(" SELECT * FROM courses ORDER BY courseId ASC")
    List<Course> getAllCourses();

    //@Query(" SELECT * FROM courses WHERE courseId = 1 ORDER BY courseId ASC")
    //List<Course> getSelectedCourses(int courseId);
}
