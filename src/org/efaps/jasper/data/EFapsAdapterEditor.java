/*
 * Copyright 2003 - 2014 The eFaps Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Revision:        $Rev$
 * Last Changed:    $Date$
 * Last Changed By: $Author$
 */
package org.efaps.jasper.data;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import net.sf.jasperreports.engine.JasperReportsContext;

import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterEditor;

/**
 * Return the editor controls to modify data adapter. They
 * are also shown when the data adapter is generated for the
 * first time to allow to configure it.
 *
 * @author Orlandin Marco
 *
 */
public class EFapsAdapterEditor implements DataAdapterEditor {

    /**
     * Container with the controls for the editing of the data adapter
     */
    protected EFapsAdapterComposite composite = null;

    /**
     * This method allows to provide a UI component to edit the data adapter. The WizardPage reference is convenient for
     * calling specific methods from WizardPage class like setMessage() method but this is not mandatory.
     *
     * @param parent the parent container
     * @param style the style of the container
     * @param wizardPage  can be null
     * @return composite the composite with the controls
     */
    @Override
    public ADataAdapterComposite getComposite(Composite parent, int style, WizardPage wizardPage, JasperReportsContext jrContext) {
        if (composite == null)
            composite = new EFapsAdapterComposite(parent, style, jrContext);
        return composite;
    }

    /**
     * This method is called when the user completes to edit the datasource or when a datasource test is required.
     *
     * @return a data adapter descriptor with inside the modified data adapter. It can be the same instance get in input
     * with setDataAdapter or a new  one.
     */
    @Override
    public DataAdapterDescriptor getDataAdapter() {
        return composite.getDataAdapter();
    }

    /**
     * Set the DataAdapter to edit. Actually it is a copy of the original DataAdapter. It can be modified by the user
     * interface.
     * The copy of an DataAdapter is done instancing a new class of the same type and loading the properties stored by the
     * first object
     *
     * @param dataAdapter DataAdapter to edit
     */
    @Override
    public void setDataAdapter(DataAdapterDescriptor dataAdapter) {
        if (dataAdapter instanceof EFapsAdapterDescriptor)
            composite.setDataAdapter((EFapsAdapterDescriptor) dataAdapter);
    }

    /**
     * This method returns the help context ID for the composite returned by getComposite() to show the help information
     *
     * @return String context ID, i.e:
     *         As possible default, the context id "com.jaspersoft.studio.doc.dataAdapters_wizard_list" can be return.
     *
     */
    @Override
    public String getHelpContextId() {
        return composite.getHelpContextId();
    }
}
