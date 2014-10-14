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
package org.efaps.jasper.data;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.engine.JasperReportsContext;

import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.secret.DataAdaptersSecretsProvider;
import com.jaspersoft.studio.swt.widgets.WSecretText;

/**
 * Inside this class are defined the controls shown when the 
 * adapter is created on edited from Jaspersoft Studio. 
 * This controls can be used the configure the data adapter.
 * With the example data adapter it provide the controls to define
 * the number of record, the number of value for each record, and
 * the range between every value is generated
 * 
 * @author Orlandin Marco
 *
 */
public class EFapsAdapterComposite extends ADataAdapterComposite {
	
	
	private WSecretText passwordField;
	/**
	 * The descriptor of the data adapter
	 */
	private EFapsAdapterDescriptor dataAdapterDescriptor;

	private Text userNameField;

	private Text urlField;

	/**
	 * Construct the class and initialize the controls
	 * 
	 * @param parent the parent of the controls
	 * @param style the style for the controls
	 * @param context the current JasperReportsContext
	 */
	public EFapsAdapterComposite(Composite parent, int style, JasperReportsContext context) {
		super(parent, style,context);
		initComponents();
	}

	/**
	 * Create the controls
	 */
	private void initComponents() {
		setLayout(new GridLayout(2, false));
		Label url = new Label(this, SWT.NONE);
		url.setText("URL");
		url.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,1, 1));
		
		urlField = new Text(this, SWT.BORDER);
		urlField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1, 1));
		
		Label userName = new Label(this, SWT.NONE);
		userName.setText("User Name");
		userName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,1, 1));
		
		userNameField = new Text(this, SWT.BORDER);
		userNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1, 1));
		
		Label pswd = new Label(this, SWT.NONE);
		pswd.setText("Password");
		pswd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,1, 1));
		
		passwordField = new WSecretText(this, SWT.BORDER | SWT.PASSWORD);
		passwordField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	
	/**
	 * Return the current data adapter descriptor, if it is not available
	 * it is created
	 */
	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDescriptor == null) {
			dataAdapterDescriptor = new EFapsAdapterDescriptor();
		}
		return dataAdapterDescriptor;
	}

	/**
	 * Set the data adapter descriptor from outside and bind the created controls to it
	 */
	@Override
	public void setDataAdapter(DataAdapterDescriptor dataAdapterDescriptor) {
		this.dataAdapterDescriptor = (EFapsAdapterDescriptor) dataAdapterDescriptor;
		EFapsDataAdapter dataAdapter = (EFapsDataAdapter) dataAdapterDescriptor.getDataAdapter();
		if (!passwordField.isWidgetConfigured()) {
			passwordField.loadSecret(DataAdaptersSecretsProvider.SECRET_NODE_ID, dataAdapter.getPassword());
		}
		bindWidgets(dataAdapter);
	}
	
	/**
	 * Bind the data adapter to the widgets to have that every change on the
	 * spinner widgets is reflected on the data adapter
	 * 
	 * @param dataAdapter the data adapter
	 */
	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		bindingContext.bindValue(SWTObservables.observeText(urlField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "url"));
		bindingContext.bindValue(SWTObservables.observeText(userNameField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "userName"));
		bindingContext.bindValue(SWTObservables.observeText(passwordField, SWT.Modify), PojoObservables.observeValue(dataAdapter, "password")); 
	}
	
	@Override
	public void performAdditionalUpdates() {
		if (JaspersoftStudioPlugin.shouldUseSecureStorage()) {
			passwordField.persistSecret();
			// update the "password" replacing it with the UUID key saved in secure
			// preferences
			EFapsDataAdapter dataAdapter = (EFapsDataAdapter) getDataAdapter().getDataAdapter();
			dataAdapter.setPassword(passwordField.getUUIDKey());
		}
	}
}