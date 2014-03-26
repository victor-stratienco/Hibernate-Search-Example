package demo.hibernatesearch.dao;

import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import demo.hibernatesearch.model.Resume;
import demo.hibernatesearch.model.User;

/**
 * Spring 2.5 POJO Test Cases
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = "/WEB-INF/applicationContext*.xml")
@Transactional
public class ResumeDaoTest {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ResumeDao resumeDao;

	public ResumeDao getResumeDao() {
		return resumeDao;
	}

	public void setResumeDao(ResumeDao resumeDao) {
		this.resumeDao = resumeDao;
	}

	@Test
	@Rollback(false)
	public void testSaveApplicant() throws Exception {
		User applicant1 = new User();
		applicant1.setFirstName("Mike");
		applicant1.setLastName("Wilson");
		applicant1.setMiddleName("Lee");
		applicant1.setEmailAddress("mwilson@demohibernatesearch.com");

		Set<Resume> resumes = new HashSet<Resume>();
		applicant1.setResumes(resumes);

		Resume resume1 = new Resume();
		resume1.setApplicant(applicant1);
		resume1
				.setSummary("A Java developer with Struts, Webwork, JSF, and Wicket based web application development experience");
		resume1.setLastUpdated(new GregorianCalendar(2008, 1, 20).getTime());
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume1.doc");
		byte[] content = new byte[30720];
		is.read(content);
		is.close();
		resume1.setContent(content);
		resumes.add(resume1);

		Resume resume2 = new Resume();
		resume2.setApplicant(applicant1);
		resume2
				.setSummary("A Java developer with JavaME, J2ME application development experience.");
		resume2.setLastUpdated(new GregorianCalendar(2007, 6, 10).getTime());
		is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume2.doc");
		content = new byte[30720];
		is.read(content);
		is.close();
		resume2.setContent(content);
		resumes.add(resume2);

		// ************************************//
		User applicant2 = new User();
		applicant2.setFirstName("Steve");
		applicant2.setLastName("Grant");
		applicant2.setEmailAddress("sgrant@demohibernatesearch.com");

		resumes = new HashSet<Resume>();
		applicant2.setResumes(resumes);

		Resume resume3 = new Resume();
		resume3.setApplicant(applicant2);
		resume3
				.setSummary("A Java architect with web application design experience using, JSF, and Struts technologies.");
		resume3.setLastUpdated(new GregorianCalendar(2008, 2, 5).getTime());
		is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume3.doc");
		content = new byte[30720];
		is.read(content);
		is.close();
		resume3.setContent(content);
		resumes.add(resume3);

		// ************************************//
		User applicant3 = new User();
		applicant3.setFirstName("Peter");
		applicant3.setLastName("Fanning");
		applicant3.setEmailAddress("pfanning@demohibernatesearch.com");

		resumes = new HashSet<Resume>();
		applicant3.setResumes(resumes);

		Resume resume4 = new Resume();
		resume4.setApplicant(applicant3);
		resume4
				.setSummary("A Java web 2.0 programmer with DWR, ECHO, GWT, AJAX, RSS, ATOM, and RESTful web services development experience.");
		resume4.setLastUpdated(new GregorianCalendar(2006, 12, 30).getTime());
		is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume4.doc");
		content = new byte[30720];
		is.read(content);
		is.close();
		resume4.setContent(content);
		resumes.add(resume4);

		// ************************************//
		User applicant4 = new User();
		applicant4.setFirstName("Jason");
		applicant4.setLastName("Brunk");
		applicant4.setEmailAddress("jbrunk@demohibernatesearch.com");

		resumes = new HashSet<Resume>();
		applicant4.setResumes(resumes);

		Resume resume5 = new Resume();
		resume5.setApplicant(applicant4);
		resume5.setSummary("SOA enterprise architect");
		resume5.setLastUpdated(new GregorianCalendar(2006, 8, 3).getTime());
		is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume5.doc");
		content = new byte[30720];
		is.read(content);
		is.close();
		resume5.setContent(content);
		resumes.add(resume5);

		// ************************************//
		User applicant5 = new User();
		applicant5.setFirstName("Charlie");
		applicant5.setLastName("Smith");
		applicant5.setEmailAddress("csmith@demohibernatesearch.com");

		resumes = new HashSet<Resume>();
		applicant5.setResumes(resumes);

		Resume resume6 = new Resume();
		resume6.setApplicant(applicant5);
		resume6.setSummary("A .NET developer");
		resume6.setLastUpdated(new GregorianCalendar(2006, 5, 5).getTime());
		is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume6.doc");
		content = new byte[30720];
		is.read(content);
		is.close();
		resume6.setContent(content);
		resumes.add(resume6);

		// ************************************//
		User applicant6 = new User();
		applicant6.setFirstName("David");
		applicant6.setLastName("Lee");
		applicant6.setEmailAddress("dlee@demohibernatesearch.com");

		resumes = new HashSet<Resume>();
		applicant6.setResumes(resumes);

		Resume resume7 = new Resume();
		resume7.setApplicant(applicant6);
		resume7
				.setSummary("A Java developer with EJB, SEAM development experience.");
		resume7.setLastUpdated(new GregorianCalendar(2007, 10, 20).getTime());
		is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume7.doc");
		content = new byte[30720];
		is.read(content);
		is.close();
		resume7.setContent(content);
		resumes.add(resume7);

		Resume resume8 = new Resume();
		resume8.setApplicant(applicant6);
		resume8
				.setSummary("A Java developer with advanced web services development skills.");
		resume8.setLastUpdated(new GregorianCalendar(2008, 4, 10).getTime());
		is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume8.doc");
		content = new byte[30720];
		is.read(content);
		is.close();
		resume8.setContent(content);
		resumes.add(resume8);

		resumeDao.saveApplicant(applicant1);
		resumeDao.saveApplicant(applicant2);
		resumeDao.saveApplicant(applicant3);
		resumeDao.saveApplicant(applicant4);
		resumeDao.saveApplicant(applicant5);
		resumeDao.saveApplicant(applicant6);
		// deferred batch updates

		// setComplete(); // commit the transaction, rather than rollback by
		// default
	}

	@Test
	@Rollback(false)
	public void testDbFindResumesForUser() throws Exception {
		long t1 = System.currentTimeMillis();
		List<Resume> resumes = resumeDao
				.seFindResumesForUser("mwilson@demohibernatesearch.com");

		assertTrue(resumes.size() == 2);

		long t2 = System.currentTimeMillis();
		log.debug("testDbFindResumesForUser miliseconds- " + (t2 - t1));
	}

	@Test
	@Rollback(false)
	public void testSeFindResumesForUser() throws Exception {
		long t1 = System.currentTimeMillis();

		List<Resume> resumes = resumeDao
				.seFindResumesForUser("dlee@demohibernatesearch.com");
		assertTrue(resumes.size() == 2);

		long t2 = System.currentTimeMillis();
		log.debug("testSeFindResumesForUser miliseconds- " + (t2 - t1));

	}

	@Test
	@Rollback(false)
	public void testDbFindMatchCount() throws Exception {
		long t1 = System.currentTimeMillis();
		int matchCount = resumeDao.dbFindMatchCount(new GregorianCalendar(2006,
				1, 1).getTime(), new GregorianCalendar().getTime(), "java",
				"web");
		assertTrue(matchCount == 4);
		long t2 = System.currentTimeMillis();
		log.debug("testDbFindMatchCount miliseconds- " + (t2 - t1));
	}

	@Test
	@Rollback(false)
	public void testSeFindMatchCount() throws Exception {
		long t1 = System.currentTimeMillis();
		int matchCount = resumeDao.seFindMatchCount(new GregorianCalendar(2006,
				1, 1).getTime(), new GregorianCalendar().getTime(), "java",
				"web");
		assertTrue(matchCount == 4);
		long t2 = System.currentTimeMillis();
		log.debug("testSeFindMatchCount miliseconds- " + (t2 - t1));
	}

	@Test
	@Rollback(false)
	public void testDbFindResumesWithPagination() throws Exception {
		List<Resume> resumes = resumeDao.dbFindResumesWithPagination(0, 2,
				new GregorianCalendar(2006, 1, 1).getTime(),
				new GregorianCalendar().getTime(), "java", "web");
		assertTrue(resumes.size() == 2);

		for (Resume resume : resumes) {
			log.debug("testDbFindResumesWithPagination - "
					+ resume.getSummary());
			// resume.setSummary("updated to test the persistence state of the
			// entity!");
		}
	}

	@Test
	@Rollback(false)
	public void testSeFindResumesWithPagination() throws Exception {
		List<Resume> resumes = resumeDao.seFindResumesWithPagination(0, 2,
				new GregorianCalendar(2006, 1, 1).getTime(),
				new GregorianCalendar().getTime(), "java", "web");
		assertTrue(resumes.size() == 2);

		for (Resume resume : resumes) {
			log.debug("testSeFindResumesWithPagination - "
					+ resume.getSummary());
		}
	}

	@Test
	@Rollback(false)
	public void testSeFindResumesWithDocHandler() throws Exception {
		List<Resume> resumes = resumeDao.seFindResumesWithDocHandler(
				new GregorianCalendar(2006, 1, 1).getTime(),
				new GregorianCalendar().getTime(), "java", "web");

		assertTrue(resumes.size() == 5);

		for (Resume resume : resumes) {
			log.debug("testSeFindResumesWithDocHandler - "
					+ resume.getSummary());
		}
	}

	@Test
	@Rollback(false)
	public void testSeFindResumeProjectionsWithoutDatabaseAccess()
			throws Exception {

		long t1 = System.currentTimeMillis();
		Map<Resume, Float> resumes = resumeDao
				.seFindResumeProjectionsWithoutDatabaseAccess(
						new GregorianCalendar(2006, 1, 1).getTime(),
						new GregorianCalendar().getTime(), "java", "web");

		assertTrue(resumes.size() == 4);

		for (Resume resume : resumes.keySet()) {
			log.debug("testSeFindResumeProjectionsWithoutDatabaseAccess - "
					+ resumes.get(resume) + " : " + resume.getSummary());
		}

		long t2 = System.currentTimeMillis();
		log
				.debug("testSeFindResumeProjectionsWithoutDatabaseAccess miliseconds- "
						+ (t2 - t1));

	}

}
