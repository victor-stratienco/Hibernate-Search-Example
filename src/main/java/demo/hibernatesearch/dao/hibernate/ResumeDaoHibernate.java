package demo.hibernatesearch.dao.hibernate;

import demo.hibernatesearch.dao.ResumeDao;
import demo.hibernatesearch.model.User;
import demo.hibernatesearch.model.Resume;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.hibernate.Session;
import org.hibernate.search.FullTextFilter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;

import static org.hibernate.search.jpa.Search.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;

/**
 * Declare POJO Spring Component DAO
 */
@Repository("resumeDao")
public class ResumeDaoHibernate implements ResumeDao {

	protected final Log log = LogFactory.getLog(getClass());

	JpaTemplate jpaTemplate;

	@Autowired
	public ResumeDaoHibernate(EntityManagerFactory entityManagerFactory) {
		this.jpaTemplate = new JpaTemplate(entityManagerFactory);
	}

	public JpaTemplate getJpaTemplate() {
		return jpaTemplate;
	}

	public void setJpaTemplate(JpaTemplate jpaTemplate) {
		this.jpaTemplate = jpaTemplate;
	}

	/**
	 * Auto Indexing
	 */
	public void saveApplicant(User applicant) {
		getJpaTemplate().persist(applicant);
	}

	/**
	 * Auto Indexing
	 */
	public void updateApplicant(User applicant) {
		getJpaTemplate().refresh(applicant);
	}

	/**
	 * Index Read
	 */
	public User getApplicant(Long id) {
		return getJpaTemplate().find(User.class, id);
	}

	/**
	 * Auto Indexing
	 */
	public void deleteApplicant(User applicant) {
		getJpaTemplate().remove(applicant);
	}

	/**
	 * Auto Indexing
	 */
	public void saveResume(Resume resume) {
		getJpaTemplate().persist(resume);
	}

	/**
	 * Auto Indexing
	 */
	public void updateResume(Resume resume) {
		getJpaTemplate().refresh(resume);
	}

	/**
	 * Index Read
	 */
	public Resume getResume(Long id) {
		return getJpaTemplate().find(Resume.class, id);
	}

	/**
	 * Auto Indexing
	 */
	public void deleteResume(Resume resume) {
		getJpaTemplate().remove(resume);
	}

	/**
	 * JPA query, no index search
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> dbFindResumesForUser(String emailAddress) {
		return (List<Resume>) getJpaTemplate()
				.find(
						"from Resume resume left join fetch applicant where resume.applicant.emailAddress='"
								+ emailAddress + "'");
	}

	/**
	 * Index search followed by JPA query
	 * 
	 * getJpaTemplate().getEntityManager() appears returning null
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> seFindResumesForUser(final String emailAddress) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				TermQuery tq = new TermQuery(new Term("applicant.emailAddress",
						emailAddress));

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						tq, Resume.class);
				return fq.getResultList();
			}
		});
		return (List<Resume>) results;
	}

	/**
	 * JPA query, no index search
	 */
	public int dbFindMatchCount(final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				StringBuilder jpaql = new StringBuilder(
						"select count(resume) from Resume resume join resume.applicant where ");

				for (int i = 0; i < keywordsInSummary.length; i++) {
					jpaql.append("resume.summary like '%"
							+ keywordsInSummary[i] + "%' ");
					if (i < keywordsInSummary.length - 1)
						jpaql.append(" and ");
				}

				Session session = ((Session) em.getDelegate());

				session.enableFilter("rangeFilter").setParameter("beginDate",
						beginDate).setParameter("endDate", endDate);

				return ((Long) getJpaTemplate().find(jpaql.toString())
						.iterator().next()).intValue();
			}
		});
		return (Integer) results;
	}

	/**
	 * only index search, no JPA query
	 */
	public int seFindMatchCount(final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				BooleanQuery bq = new BooleanQuery();

				for (String q : keywordsInSummary) {
					TermQuery tq = new TermQuery(new Term("summary", q));
					bq.add(new BooleanClause(tq, BooleanClause.Occur.MUST));
				}

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, Resume.class);

				FullTextFilter ff = fq.enableFullTextFilter("rangeFilter");
				ff.setParameter("fieldName", "lastUpdated");
				ff.setParameter("lowerTerm", DateTools.dateToString(beginDate,
						DateTools.Resolution.DAY));
				ff.setParameter("upperTerm", DateTools.dateToString(endDate,
						DateTools.Resolution.DAY));
				ff.setParameter("includeLower", true);
				ff.setParameter("includeUpper", true);

				return fq.getResultSize();
			}
		});
		return (Integer) results;
	}

	/**
	 * only JPA query without index search, pagination enabled
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> dbFindResumesWithPagination(final int fetchCursor,
			final int fetchSize, final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				StringBuilder jpaql = new StringBuilder(
						"from Resume resume join fetch resume.applicant where ");

				for (int i = 0; i < keywordsInSummary.length; i++) {
					jpaql.append("resume.summary like '%"
							+ keywordsInSummary[i] + "%' ");
					if (i < keywordsInSummary.length - 1)
						jpaql.append(" and ");
				}

				Session session = ((Session) em.getDelegate());

				session.enableFilter("rangeFilter").setParameter("beginDate",
						beginDate).setParameter("endDate", endDate);

				Query query = em.createQuery(jpaql.toString());

				query.setFirstResult(fetchCursor);
				query.setMaxResults(fetchSize);

				return (List<Resume>) query.getResultList();
			}
		});
		return (List<Resume>) results;
	}

	/**
	 * index search followed by JPA query
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> seFindResumesWithPagination(final int fetchCursor,
			final int fetchSize, final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				BooleanQuery bq = new BooleanQuery();

				for (String q : keywordsInSummary) {
					TermQuery tq = new TermQuery(new Term("summary", q));
					bq.add(new BooleanClause(tq, BooleanClause.Occur.MUST));
				}

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, Resume.class);

				FullTextFilter ff = fq.enableFullTextFilter("rangeFilter");
				ff.setParameter("fieldName", "lastUpdated");
				ff.setParameter("lowerTerm", DateTools.dateToString(beginDate,
						DateTools.Resolution.DAY));
				ff.setParameter("upperTerm", DateTools.dateToString(endDate,
						DateTools.Resolution.DAY));
				ff.setParameter("includeLower", true);
				ff.setParameter("includeUpper", true);

				fq.setFirstResult(fetchCursor);
				fq.setMaxResults(fetchSize);

				return (List<Resume>) fq.getResultList();
			}
		});
		return (List<Resume>) results;
	}

	/**
	 * index search followed by JPA query, indexing with WordDocHandlerBridge
	 */
	@SuppressWarnings("unchecked")
	public List<Resume> seFindResumesWithDocHandler(final Date beginDate,
			final Date endDate, final String... keywordsInWordDoc) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				BooleanQuery bq = new BooleanQuery();

				for (String q : keywordsInWordDoc) {
					TermQuery tq = new TermQuery(new Term("resume", q));
					bq.add(new BooleanClause(tq, BooleanClause.Occur.MUST));
				}

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, Resume.class);

				FullTextFilter ff = fq.enableFullTextFilter("rangeFilter");
				ff.setParameter("fieldName", "lastUpdated");
				ff.setParameter("lowerTerm", DateTools.dateToString(beginDate,
						DateTools.Resolution.DAY));
				ff.setParameter("upperTerm", DateTools.dateToString(endDate,
						DateTools.Resolution.DAY));
				ff.setParameter("includeLower", true);
				ff.setParameter("includeUpper", true);

				return (List<Resume>) fq.getResultList();
			}
		});
		return (List<Resume>) results;
	}

	/**
	 * By using projection, all search results are returned from indexes, no
	 * database access is required.
	 */
	@SuppressWarnings("unchecked")
	public Map<Resume, Float> seFindResumeProjectionsWithoutDatabaseAccess(
			final Date beginDate, final Date endDate,
			final String... keywordsInSummary) {
		Object results = getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {

				FullTextEntityManager fullTextEntityManager = createFullTextEntityManager(em);

				BooleanQuery bq = new BooleanQuery();

				for (String q : keywordsInSummary) {
					TermQuery tq = new TermQuery(new Term("summary", q));
					bq.add(new BooleanClause(tq, BooleanClause.Occur.MUST));
				}

				FullTextQuery fq = fullTextEntityManager.createFullTextQuery(
						bq, Resume.class);

				FullTextFilter ff = fq.enableFullTextFilter("rangeFilter");
				ff.setParameter("fieldName", "lastUpdated");
				ff.setParameter("lowerTerm", DateTools.dateToString(beginDate,
						DateTools.Resolution.DAY));
				ff.setParameter("upperTerm", DateTools.dateToString(endDate,
						DateTools.Resolution.DAY));
				ff.setParameter("includeLower", true);
				ff.setParameter("includeUpper", true);

				fq.setProjection(FullTextQuery.SCORE, "id",
						"summary",
						// "lastUpdated",
						"applicant.id", "applicant.firstName",
						"applicant.lastName", "applicant.middleName",
						"applicant.emailAddress");

				Map<Resume, Float> resumes = new HashMap<Resume, Float>();

				for (Object[] result : (List<Object[]>) fq.getResultList()) {
					Resume resume = new Resume();
					User applicant = new User();
					resume.setApplicant(applicant);

					resume.setId((Long) result[1]);
					resume.setSummary((String) result[2]);
					// resume.setLastUpdated((Date) result[3]);
					/**
					 * Can't project Date field marked with annotation
					 * DateBridge
					 */
					/** WordDoc content is left blank. */
					applicant.setId((Long) result[3]);
					applicant.setFirstName((String) result[4]);
					applicant.setLastName((String) result[5]);
					applicant.setMiddleName((String) result[6]);
					applicant.setEmailAddress((String) result[7]);

					resumes.put(resume, (Float) result[0]);
				}
				return resumes;
			}
		});
		return (Map<Resume, Float>) results;
	}

}
