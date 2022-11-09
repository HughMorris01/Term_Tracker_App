package wgu.assessments.scheduler.Database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wgu.assessments.scheduler.DAO.AssessmentDAO;
import wgu.assessments.scheduler.DAO.CourseDAO;
import wgu.assessments.scheduler.DAO.TermDAO;
import wgu.assessments.scheduler.Entity.Assessment;
import wgu.assessments.scheduler.Entity.Course;
import wgu.assessments.scheduler.Entity.Term;

public class Repository {
    private final TermDAO mTermDAO;
    private final CourseDAO mCourseDAO;
    private final AssessmentDAO mAssessmentDAO;
    private List<Term> mAllTermList;
    private List<Course> mAllCourseList;
    private List<Assessment> mAllAssessmentList;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        TrackerDatabaseBuilder db = TrackerDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
    }

    public void insertTerm(Term term) {
        dbExecutor.execute(()->{
            mTermDAO.insertTerm(term);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Term> getAllTerms(){
        dbExecutor.execute(()->{
            mAllTermList = mTermDAO.getAllTerms();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTermList;
    }
    public void updateTerm(Term term){
        dbExecutor.execute(()->{
            mTermDAO.updateTerm(term);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void deleteTerm(Term term){
        dbExecutor.execute(()->{
            mTermDAO.deleteTerm(term);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertCourse(Course course) {
        dbExecutor.execute(()->{
            mCourseDAO.insertCourse(course);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Course> getAllCourses(){
        dbExecutor.execute(()->{
            mAllCourseList = mCourseDAO.getAllCourses();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourseList;
    }
    public void updateCourse(Course course){
        dbExecutor.execute(()->{
            mCourseDAO.updateCourse(course);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void deleteCourse(Course course){
        dbExecutor.execute(()->{
            mCourseDAO.deleteCourse(course);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertAssessment(Assessment assessment) {
        dbExecutor.execute(()->{
            mAssessmentDAO.insertAssessment(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Assessment> getAllAssessments(){
        dbExecutor.execute(()->{
            mAllAssessmentList = mAssessmentDAO.getAllAssessments();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessmentList;
    }
    public void updateAssessment(Assessment assessment){
        dbExecutor.execute(()->{
            mAssessmentDAO.updateAssessment(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void deleteAssessment(Assessment assessment){
        dbExecutor.execute(()->{
            mAssessmentDAO.deleteAssessment(assessment);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
