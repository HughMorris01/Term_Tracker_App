package wgu.assessments.scheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import wgu.assessments.scheduler.Entity.Term;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTerm(Term term);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query(" SELECT * FROM terms ORDER BY termId ASC")
    List<Term> getAllTerms();
}
