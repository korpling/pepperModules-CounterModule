/*
 * Copyright 2017 GU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.corpus_tools.pepperModules_CounterModule;
import org.corpus_tools.pepper.modules.PepperModuleProperties;
import org.corpus_tools.pepper.modules.PepperModuleProperty;
/**
 *
 * @author Amir Zeldes
 */
public class CounterModuleManipulatorProperties extends PepperModuleProperties {
    

	public static final String PREFIX = "Counter.";


        public final static String ANNONS = PREFIX + "annoNamespace";
	public final static String ANNONAME = PREFIX + "annoName";
        public final static String METAANNONS = PREFIX + "metaAnnoNamespace";
	public final static String METAANNONAME = PREFIX + "metaAnnoName";
	public final static String NOMETACOUNT = PREFIX + "noCountAsMeta";
        public final static String NOTOKENANNO = PREFIX + "noTokenNumbers";

        
	public CounterModuleManipulatorProperties() {
            
		this.addProperty(new PepperModuleProperty<String>(ANNONS, String.class, "Specifies a namespace to add to token number annotations.", "default_ns", false));
		this.addProperty(new PepperModuleProperty<String>(ANNONAME, String.class, "Specifies the annotation name for token numbers.", "tok_num", false));
		this.addProperty(new PepperModuleProperty<String>(METAANNONS, String.class, "Specifies a namespace to add to token count meta annotation.", null, false));
		this.addProperty(new PepperModuleProperty<String>(METAANNONAME, String.class, "Specifies the annotation name for token count meta annotation.", "tok_count", false));
		this.addProperty(new PepperModuleProperty<Boolean>(NOMETACOUNT, Boolean.class, "Whether to leave out the metadatum with the count of tokens to each document.", false, false));
		this.addProperty(new PepperModuleProperty<Boolean>(NOTOKENANNO, Boolean.class, "If true, then tokens are not numbered (for cases where you only want metadata added).", false, false));
        }

}
