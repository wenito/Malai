/*
 * generated by Xtext
 */
package org.generator

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

//import com.google.inject.Injector

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class MalaiGenerator implements IGenerator {
	
	override void doGenerate(Resource resource, IFileSystemAccess fsa) {
	var URI srcURI = resource.URI
		var tgtURI = URI::createPlatformResourceURI(srcURI.toPlatformString(false).replaceAll("\\.malai","\\.xmi"),false);
		
		var xmiResource = resource.resourceSet.createResource(tgtURI)
		xmiResource.getContents().add(resource.getContents().get(0))
		xmiResource.save(null)
	}
}
