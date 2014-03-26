package demo.hibernatesearch.dao;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = "/WEB-INF/applicationContext*.xml")
@Transactional
public class ResumeDaoTest {

    @Autowired
    private ResumeDao resumeDao;

    //...

    @Test
    @Rollback(false)
    public void testSeFindResumesWithDocHandler() throws Exception {
        List<Resume> resumes = resumeDao.seFindResumesWithDocHandler(
                new GregorianCalendar(2006, 1, 1).getTime(),
                new GregorianCalendar().getTime(), "java", "web");

        assertTrue(resumes.size() == 5);
    }

    //...
}

