package demo.hibernatesearch.dao;

import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ResumeDao {

	public void saveApplicant(User applicant);

	public void updateApplicant(User applicant);

	public User getApplicant(Long id);

	public void deleteApplicant(User applicant);

	public void saveResume(Resume resume);

	public void updateResume(Resume resume);

	public Resume getResume(Long id);

	public void deleteResume(Resume resume);

	public List<Resume> dbFindResumesForUser(String emailAddress);

	public List<Resume> seFindResumesForUser(String emailAddress);

	public int dbFindMatchCount(Date beginDate, Date endDate,
                                String... keywordsInSummary);

	public int seFindMatchCount(Date beginDate, Date endDate,
                                String... keywordsInSummary);

	public List<Resume> dbFindResumesWithPagination(int fetchCursor,
                                                    int fetchSize, Date beginDate, Date endDate,
                                                    String... keywordsInSummary);

	public List<Resume> seFindResumesWithPagination(int fetchCursor,
                                                    int fetchSize, Date beginDate, Date endDate,
                                                    String... keywordsInSummary);

	public List<Resume> seFindResumesWithDocHandler(Date beginDate,
                                                    Date endDate, String... keywordsInWordDoc);

	public Map<Resume, Float> seFindResumeProjectionsWithoutDatabaseAccess(
            Date beginDate, Date endDate, String... keywordsInSummary);

}
