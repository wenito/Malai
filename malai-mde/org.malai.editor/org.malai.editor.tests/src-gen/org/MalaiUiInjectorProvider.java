/*
* generated by Xtext
*/
package org;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;

public class MalaiUiInjectorProvider implements IInjectorProvider {
	
	public Injector getInjector() {
		return org.ui.internal.MalaiActivator.getInstance().getInjector("org.Malai");
	}
	
}
