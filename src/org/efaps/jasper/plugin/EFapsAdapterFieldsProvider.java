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
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignField;

import java.util.Map;
import java.util.HashMap;

import com.jaspersoft.studio.data.fields.IFieldsProvider;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

/**
 * Field provider for the data adapter, take the data adapter and return the
 * number of fields returned by it
 *
 * @author The eFaps Team
 *
 */
public class EFapsAdapterFieldsProvider
    implements IFieldsProvider
{

    /**
     * Returns the fields that are available from the adapter. The provider can
     * use the passed in report to extract some additional configuration
     * information such as report properties. In this case the data adapter is
     * executed to get the data source and by looking at the result set the
     * number of field is calculated
     *
     * @param dataAdapterService the service of the data adapter, can used to
     *            get the result dataset from the data adapter
     * @param jasperReportsConfiguration the configuration of the current report
     * @param dataset the current dataset, in some cases it can be null
     * @return a not null list of the field provided by the datasource with the
     *         current configuration
     * @throws UnsupportedOperationException is the method is not supported
     * @throws JRException if an error occurs.
     */
    @Override
    public List<JRDesignField> getFields(final DataAdapterService dataAdapterService,
                                         final JasperReportsConfiguration jasperReportsConfiguration,
                                         final JRDataset dataset)
        throws JRException, UnsupportedOperationException
    {

        final List<JRDesignField> designFields = new ArrayList<JRDesignField>();
        final Map<String, Object> runParameters = new HashMap<String, Object>();
        dataAdapterService.contributeParameters(runParameters);
        final JRMapCollectionDataSource result = (JRMapCollectionDataSource) runParameters
                        .get(JRParameter.REPORT_DATA_SOURCE);

        if (result.getRecordCount() > 0) {
            final Map<String, ?> record = result.getData().iterator().next();
            for (final String colName : record.keySet()) {
                final JRDesignField field = new JRDesignField();
                field.setName(colName);
                field.setValueClass(Integer.class);
                designFields.add(field);
            }
        }

        return designFields;
    }

    /**
     * Return if the data adapter supports the operation to get automatically
     * the fields with the current configuration
     *
     * @param jConfig the current configuration
     * @return true if the fields providing is supported, false otherwise
     */
    @Override
    public boolean supportsGetFieldsOperation(final JasperReportsConfiguration jConfig)
    {
        return true;
    }

}
