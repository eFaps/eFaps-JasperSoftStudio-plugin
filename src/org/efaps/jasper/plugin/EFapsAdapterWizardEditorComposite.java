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
package org.efaps.jasper.plugin;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.data.DataAdapterServiceUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignParameter;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.jaspersoft.studio.data.AWizardDataEditorComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.fields.IFieldsProvider;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.wizards.JSSWizard;

/**
 * This class contains the controls that are shown when selecting your data
 * adapter when the New Report Wizard. Conceptually this controls should allow
 * to configure some informations by the the data adapter during the wizard. For
 * example if the data adapter has JBDC type it allow to configure the query. In
 * this case we don't need to configure additional informations, since all the
 * configuration data is already inside the data adapter
 *
 * @author The eFaps Team
 *
 */
public class EFapsAdapterWizardEditorComposite
    extends AWizardDataEditorComposite
{

    /**
     * The descriptor of the data adapter
     */
    private final DataAdapterDescriptor dataAdapterDescriptor;

    /**
     * The page, can be used to extract informations from the wizard procedure
     */
    private final WizardPage page;

    /**
     * Constructor
     *
     * @param parent the parent composite
     * @param page the wizard page
     * @param dataAdapterDescriptor the data adapter descriptor
     */
    public EFapsAdapterWizardEditorComposite(final Composite parent,
                                             final WizardPage page,
                                             final DataAdapterDescriptor dataAdapterDescriptor)
    {
        super(parent, page);
        this.dataAdapterDescriptor = dataAdapterDescriptor;
        this.page = page;
        createCompositeContent();
    }

    protected void createCompositeContent()
    {
        setLayout(new GridLayout(2, false));
        final Image img = Activator.getDefault().getImage("images/no_image.png");
        final Label lblNewLabel = new Label(this, SWT.NONE);
        if (img != null) {
            lblNewLabel.setImage(img);
        }

        final Label lblPressNextTo = new Label(this, SWT.WRAP);
        lblPressNextTo.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false));
        lblPressNextTo.setText("This is the content created by your EqladapterWizardEditorComposite class and it has only the adapter icon image and this label. Press Next to continue");
    }

    /**
     * Return the current jasper reports configuration
     *
     * @return the configuration read from the wizard if it is available,
     *         otherwise the default one
     */
    public JasperReportsConfiguration getJasperReportsConfiguration()
    {
        if (this.page != null && this.page.getWizard() != null && this.page.getWizard() instanceof JSSWizard) {
            return ((JSSWizard) this.page.getWizard()).getConfig();
        }
        return JasperReportsConfiguration.getDefaultJRConfig();
    }

    /**
     * Return the fields.
     *
     * If the dataAdapterDescriptor implements IFieldsProvider, this interface
     * is used to get the fields automatically.
     *
     * This method is invoked on a thread which is not in the UI event thread,
     * so no UI update should be performed without using a proper async thread.
     *
     * return the result of IFieldsProvider.getFields() or an empty list of
     * JRField is the DataAdapterDescriptor does not implement the
     * IFieldsProvider interface.
     */
    public List<JRDesignField> readFields()
        throws Exception
    {

        if (this.dataAdapterDescriptor != null && this.dataAdapterDescriptor instanceof IFieldsProvider)
        {
            try {
                final DataAdapterService service = DataAdapterServiceUtil.getInstance(getJasperReportsConfiguration())
                                .getService(this.dataAdapterDescriptor.getDataAdapter());
                return ((IFieldsProvider) this.dataAdapterDescriptor).getFields(service, getJasperReportsConfiguration(),
                                null);

            } catch (final JRException ex)
            {
                // Cleanup of the error. JRException are a very low meaningful
                // exception when working
                // with data, what the user is interested into is the underline
                // error (i.e. an SQL error).
                // That's why we rise the real cause, if any instead of rising
                // the highlevel exception...
                if (ex.getCause() != null && ex.getCause() instanceof Exception)
                {
                    throw (Exception) ex.getCause();
                }
                throw ex;
            }
        }
        return new ArrayList<JRDesignField>();

    }

    /**
     * Return the parameter read from the selected data adapter... This
     * operation is run on a thread which is not in the UI event thread, so no
     * UI update should be performed without using a proper async thread.
     *
     * return a list of JRDesignParameter or null if no fields have been read
     */
    @Override
    public List<JRDesignParameter> readParameters()
        throws Exception
    {
        return null;
    }

    /**
     * The editor may be used to edit a query, in this case the wizard may be
     * interested in getting the query and its language.
     *
     * Subclasses should implement this method to return the proper query
     *
     * @return the query or null if this editor does not work with a language
     */
    @Override
    public String getQueryString()
    {
        return null;
    }

    /**
     * The editor may be used to edit a query, in this case the wizard may be
     * interested in getting the query and its language.
     *
     * Subclasses should implement this method to return the proper language
     *
     * @return the language or null if this editor does not work with a language
     */
    @Override
    public String getQueryLanguage()
    {
        return "eFaps";
    }
}
