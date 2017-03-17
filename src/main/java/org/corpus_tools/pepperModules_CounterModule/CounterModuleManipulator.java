package org.corpus_tools.pepperModules_CounterModule;

import java.util.List;

import org.corpus_tools.pepper.common.DOCUMENT_STATUS;
import org.corpus_tools.pepper.common.PepperConfiguration;
import org.corpus_tools.pepper.impl.PepperManipulatorImpl;
import org.corpus_tools.pepper.impl.PepperMapperImpl;
import org.corpus_tools.pepper.modules.PepperManipulator;
import org.corpus_tools.pepper.modules.PepperMapper;
import org.corpus_tools.pepper.modules.PepperModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.exceptions.PepperModuleNotReadyException;
import org.corpus_tools.salt.SaltFactory;
import org.corpus_tools.salt.common.SCorpus;
import org.corpus_tools.salt.common.SDocument;
import org.corpus_tools.salt.common.SToken;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.core.GraphTraverseHandler;
import org.corpus_tools.salt.core.SAnnotation;
import org.corpus_tools.salt.core.SGraph.GRAPH_TRAVERSE_TYPE;
import org.corpus_tools.salt.core.SNode;
import org.corpus_tools.salt.core.SRelation;
import org.corpus_tools.salt.graph.Identifier;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.component.annotations.Component;

/**
 * This is a {@link PepperManipulator} which can add an annotation to each token
 * corresponding to its position in the document. It can also add a meta-annotation
 * containing the amount of tokens in the document.
 * 
 * @author Amir_Zeldes
 */
@Component(name = "CounterModuleManipulatorComponent", factory = "PepperManipulatorComponentFactory")
public class CounterModuleManipulator extends PepperManipulatorImpl {

    public CounterModuleManipulator() {
		super();
		setName("Counter");
		setSupplierContact(URI.createURI(PepperConfiguration.EMAIL));
		setSupplierHomepage(URI.createURI(PepperConfiguration.HOMEPAGE));
		setDesc("This manipulator adds the token number to each token and can add the token count as metadata");
	}

	/**
	 * @param Identifier
	 *            {@link Identifier} of the {@link SCorpus} or {@link SDocument}
	 *            to be processed.
	 * @return {@link PepperMapper} object to do the mapping task for object
	 *         connected to given {@link Identifier}
	 */
	public PepperMapper createPepperMapper(Identifier Identifier) {
		CounterModuleMapper mapper = new CounterModuleMapper();
		return (mapper);
	}


	public static class CounterModuleMapper extends PepperMapperImpl implements GraphTraverseHandler {

                @Override
		public DOCUMENT_STATUS mapSCorpus() {
			return (DOCUMENT_STATUS.COMPLETED);
		}

		@Override
		public DOCUMENT_STATUS mapSDocument() {

                        //CounterModuleManipulatorProperties properties = (CounterModuleManipulatorProperties) this.getProperties();                    
                        
                        // set up module properties
                        String annoNS = (String) getProperties().getProperties().getProperty(CounterModuleManipulatorProperties.ANNONS, "default_ns");
                        String annoName = (String) getProperties().getProperties().getProperty(CounterModuleManipulatorProperties.ANNONAME, "tok_num");
                        String metaAnnoNS = (String) getProperties().getProperties().getProperty(CounterModuleManipulatorProperties.METAANNONS, null);
                        String metaAnnoName = (String) getProperties().getProperties().getProperty(CounterModuleManipulatorProperties.METAANNONAME, "tok_count");
                        boolean noMeta = Boolean.valueOf(getProperties().getProperties().getProperty(CounterModuleManipulatorProperties.NOMETACOUNT));
                        boolean noNumbering = Boolean.valueOf(getProperties().getProperties().getProperty(CounterModuleManipulatorProperties.NOTOKENANNO));

                            
                            // get span children and case as list of SToken
                            int counter = 0;
                            List<SToken> tokens = getDocument().getDocumentGraph().getSortedTokenByText();
                            for (SNode token: tokens){
                                counter++;
                                if (!noNumbering){
                                    SAnnotation anno = SaltFactory.createSAnnotation();
                                    anno.setName(annoName);
                                    anno.setNamespace(annoNS);
                                    anno.setValue(Integer.toString(counter));
                                    token.addAnnotation(anno);
                                }
        
                            }
                                                       
                            
                            if (!noMeta){
                            
                                int tokCount = tokens.size();
                                getDocument().createMetaAnnotation(metaAnnoNS, metaAnnoName, Integer.toString(tokCount));
                                                                
                            }
                                   
                        

			return (DOCUMENT_STATUS.COMPLETED);
		}

		/**
		 * This method is called for each node in document-structure, as long as
		 * {@link #checkConstraint(GRAPH_TRAVERSE_TYPE, String, SRelation, SNode, long)}
		 * returns true for this node. <br/>
		 */
		@Override
		public void nodeReached(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SNode currNode, SRelation sRelation, SNode fromNode, long order) {

		}

		/**
		 * This method is called on the way back, in depth first mode it is
		 * called for a node after all the nodes belonging to its subtree have
		 * been visited. <br/>
		 * In our dummy implementation, this method is not used.
		 */
		@Override
		public void nodeLeft(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SNode currNode, SRelation edge, SNode fromNode, long order) {
		}

		/**
		 * With this method you can decide if a node is supposed to be visited
		 * by methods
		 * {@link #nodeReached(GRAPH_TRAVERSE_TYPE, String, SNode, SRelation, SNode, long)}
		 * and
		 * {@link #nodeLeft(GRAPH_TRAVERSE_TYPE, String, SNode, SRelation, SNode, long)}
		 * . In our dummy implementation for instance we do not need to visit
		 * the nodes {@link STextualDS}.
		 */
		@Override
		public boolean checkConstraint(GRAPH_TRAVERSE_TYPE traversalType, String traversalId, SRelation edge, SNode currNode, long order) {
			if (currNode instanceof STextualDS) {
				return (false);
			} else {
				return (true);
			}
		}
	}

	/**
	 * This method is called by the pepper framework after initializing this
	 * object and directly before start processing. Initializing means setting
	 * properties {@link PepperModuleProperties}, setting temporary files,
	 * resources etc. . returns false or throws an exception in case of
	 * {@link PepperModule} instance is not ready for any reason.
	 * 
	 * @return false, {@link PepperModule} instance is not ready for any reason,
	 *         true, else.
	 */
	@Override
	public boolean isReadyToStart() throws PepperModuleNotReadyException {
		// TODO make some initializations if necessary
		//return (super.isReadyToStart());
                return true;
	}
        
        
       
}
