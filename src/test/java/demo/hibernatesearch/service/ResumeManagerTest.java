package demo.hibernatesearch.service;

import static junit.framework.Assert.assertTrue;

import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class ResumeManagerTest {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ResumeManager resumeManager;

	public ResumeManager getResumeManager() {
		return resumeManager;
	}

	public void setResumeManager(ResumeManager resumeManager) {
		this.resumeManager = resumeManager;
	}

	@Test
	@Rollback(false)
	public void testSaveApplicant() throws Exception {
		User applicant7 = new User();
		applicant7.setFirstName("Stanley");
		applicant7.setLastName("Churn");
		applicant7.setEmailAddress("schurn@demohibernatesearch.com");

		Set<Resume> resumes = new HashSet<Resume>();
		applicant7.setResumes(resumes);

		Resume resume9 = new Resume();
		resume9.setApplicant(applicant7);
		resume9
				.setSummary("A Perl PHP developer with 10 years web development experience");
		resume9.setLastUpdated(new GregorianCalendar(2008, 1, 6).getTime());
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("resume9.doc");
		byte[] content = new byte[30720];
		is.read(content);
		is.close();
		resume9.setContent(content);
		resumes.add(resume9);

		resumeManager.saveApplicant(applicant7);
	}

	@Test
	@Rollback(false)
	public void testSeFindResumesWithDocHandler() throws Exception {

		List<Resume> resumes = resumeManager.seFindResumesWithDocHandler(
				new GregorianCalendar(2006, 1, 1).getTime(),
				new GregorianCalendar().getTime(), "perl", "web");

		assertTrue(resumes.size() == 1);

		for (Resume resume : resumes) {
			log.debug("testSeFindResumesWithDocHandler - "
					+ resume.getSummary());
		}
	}

}
