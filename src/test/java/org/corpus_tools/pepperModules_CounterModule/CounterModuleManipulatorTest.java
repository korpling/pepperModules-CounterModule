package org.corpus_tools.pepperModules_CounterModule;

import java.util.List;
import org.corpus_tools.pepperModules_CounterModule.CounterModuleManipulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.corpus_tools.pepper.testFramework.PepperManipulatorTest;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.samples.SampleGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * This is a dummy implementation of a JUnit test for testing the
 * {@link CounterModuleManipulator} class. Feel free to adapt and enhance this test
 * class for real tests to check the work of your manipulator. If you are not
 * confirm with JUnit, please have a look at <a
 * href="http://www.vogella.com/tutorials/JUnit/article.html">
 * http://www.vogella.com/tutorials/JUnit/article.html</a>. <br/>
 * Please note, that the test class is derived from
 * {@link PepperManipulatorTest}. The usage of this class should simplfy your
 * work and allows you to test only your single manipulator in the Pepper
 * environment.
 * 
 * @author Amir_Zeldes
 */
public class CounterModuleManipulatorTest extends PepperManipulatorTest {
	/**
	 * This method is called by the JUnit environment each time before a test
	 * case starts. So each time a method annotated with @Test is called. This
	 * enables, that each method could run in its own environment being not
	 * influenced by before or after running test cases.
	 */
	@Before
	public void setUp() {
		setFixture(new CounterModuleManipulator());
	}

	/**
	 * This is a test to check the correct work of our dummy implementation.
	 * This test is supposed to show the usage of JUnit and to give some
	 * impressions how to check simple things of the created salt model. <br/>
	 * You can create as many test cases as you like, just create further
	 * methods having the annotation "@Test". Note that it is very helpful, to
	 * give them self explaining names and a short JavaDoc explaining their
	 * purpose. This could help very much, when searching for bugs or extending
	 * the tests. <br/>
	 * In our case, we just test, if correct number of corpora and documents was
	 * created, if all corpora have got a meta-annotation and if each
	 * document-structure contains the right number of nodes and relations.
	 */
	@Test
	public void test_DummyImplementation() {
		// create a sample corpus, the class SampleGenerator provides a bunch of
		// helpful methods to create sample documents and corpora
		getFixture().setSaltProject(SampleGenerator.createSaltProject());
		// starts the Pepper framework and the conversion process
		start();

                
                for (SCorpus sCorpus : getFixture().getSaltProject().getCorpusGraphs().get(0).getCorpora()) {
                        List<SDocument> docs = sCorpus.getGraph().getDocuments();
                        for (SDocument doc : docs){
                            // Check meta annotation was generated correctly
                            int tokCount = doc.getDocumentGraph().getTokens().size();
                            assertNotNull(doc.getMetaAnnotation("tok_count"));
                            assertEquals(Integer.toString(tokCount), doc.getMetaAnnotation("tok_count").getValue());
                            
                            // Check that first token is annotated with '1'
                            List<SToken> toks = doc.getDocumentGraph().getSortedTokenByText();
                            SToken first = toks.get(0);
                            assertNotNull(first.getAnnotation("default_ns","tok_num"));
                            assertEquals(first.getAnnotation("default_ns", "tok_num").getValue_STEXT(), "1");
                            
                        }
		}
                
	}

	// TODO add further tests for any test cases you can think of and which are
	// necessary
}
