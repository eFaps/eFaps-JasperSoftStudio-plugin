/*******************************************************************************
 * Copyright (C) 2005 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com.
 * 
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.efaps.jasper.eql;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.wb.swt.ResourceManager;

/**
 * This class provides the style information for the MongoDB query text (JSON based) line being drawn.
 * <p>
 * NOTE: Re-used code and idea from JavaViewer SWT Example. 
 * 
 * @see MongoDBScanner
 *
 */
public class EqlLineStyler implements LineStyleListener {

	
	public EqlLineStyler(){
		
	}


	@Override
	public void lineGetStyle(LineStyleEvent event) {
		
	}
}
