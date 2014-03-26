package demo.hibernatesearch.service.impl;

import demo.hibernatesearch.dao.ResumeDao;
import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.service.ResumeManager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Declare POJO Spring Service Bean
 */
@Service("resumeManager")
public class ResumeManagerImpl implements ResumeManager {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ResumeDao resumeDao;

	public ResumeDao getResumeDao() {
		return resumeDao;
	}

	public void setResumeDao(ResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void saveApplicant(User applicant) {
		resumeDao.saveApplicant(applicant);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void updateApplicant(User applicant) {
		resumeDao.updateApplicant(applicant);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public User getApplicant(Long id) {
		return resumeDao.getApplicant(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void deleteApplicant(User applicant) {
		resumeDao.deleteApplicant(applicant);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void saveResume(Resume resume) {
		resumeDao.saveResume(resume);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void updateResume(Resume resume) {
		resumeDao.updateResume(resume);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public Resume getResume(Long id) {
		return resumeDao.getResume(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void deleteResume(Resume resume) {
		resumeDao.deleteResume(resume);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public List<Resume> dbFindResumesForUser(String emailAddress) {
		return resumeDao.dbFindResumesForUser(emailAddress);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public List<Resume> seFindResumesForUser(String emailAddress) {
		return resumeDao.seFindResumesForUser(emailAddress);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public int dbFindMatchCount(Date beginDate, Date endDate,
			String... keywordsInSummary) {
		return resumeDao
				.dbFindMatchCount(beginDate, endDate, keywordsInSummary);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public int seFindMatchCount(Date beginDate, Date endDate,
			String... keywordsInSummary) {
		return resumeDao
				.seFindMatchCount(beginDate, endDate, keywordsInSummary);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public List<Resume> dbFindResumesWithPagination(int fetchCursor,
			int fetchSize, Date beginDate, Date endDate,
			String... keywordsInSummary) {

		return resumeDao.dbFindResumesWithPagination(fetchCursor, fetchSize,
				beginDate, endDate, keywordsInSummary);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public List<Resume> seFindResumesWithPagination(int fetchCursor,
			int fetchSize, Date beginDate, Date endDate,
			String... keywordsInSummary) {

		return resumeDao.seFindResumesWithPagination(fetchCursor, fetchSize,
				beginDate, endDate, keywordsInSummary);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public List<Resume> seFindResumesWithDocHandler(Date beginDate,
			Date endDate, String... keywordsInWordDoc) {

		return resumeDao.seFindResumesWithDocHandler(beginDate, endDate,
				keywordsInWordDoc);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public Map<Resume, Float> seFindResumeProjectionsWithoutDatabaseAccess(
			Date beginDate, Date endDate, String... keywordsInSummary) {

		return resumeDao.seFindResumeProjectionsWithoutDatabaseAccess(
				beginDate, endDate, keywordsInSummary);
	}

}
