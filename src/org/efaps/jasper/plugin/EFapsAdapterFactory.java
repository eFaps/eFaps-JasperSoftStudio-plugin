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

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;

import org.eclipse.swt.graphics.Image;
import org.efaps.jasper.data.EFapsDataAdapter;
import org.efaps.jasper.data.EFapsDataAdapterImpl;
import org.efaps.jasper.data.EFapsDataAdapterService;

import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterFactory;
import com.jaspersoft.studio.data.adapter.IDataAdapterCreator;

/**
 * This class provide information on the data adapter, like it's display name
 * and icon and has the capability to create it and its classes
 *
 * @author The eFaps Team
 *
 */
public class EFapsAdapterFactory
    implements DataAdapterFactory
{

    /**
     * Creates a new instance of the data adapter
     *
     * @return a not null instance of the data adapter
     */
    @Override
    public DataAdapterDescriptor createDataAdapter()
    {
        final EFapsAdapterDescriptor descriptor = new EFapsAdapterDescriptor();
        return descriptor;
    }

    /**
     * This method returns the class name of the DataAdapter implementation.
     * This is used from the code that must check if this connection factory is
     * the good one to instance the connection serialized with a specific class
     * name. Since due to the ClassLoading limitation JSS may not be able to
     * instance the class by its self, it looks for the appropriate registered
     * DataAdapterFactory
     *
     * @return the class name of the DataAdapter implementation created by this
     *         factory class
     */
    @Override
    public String getDataAdapterClassName()
    {
        return EFapsDataAdapterImpl.class.getName();
    }

    /**
     * This method provides the label of the data adapter type. I.e.: JDBC
     * connection.
     *
     * @return a not null and not empty string
     */
    @Override
    public String getLabel()
    {
        return "EFapsAdapter";
    }

    /**
     * This method provides a short description of the data adapter type. I.e.:
     * connection to a database using JDBC
     *
     * @return a not null string
     */
    @Override
    public String getDescription()
    {
        return "EFapsAdapter";
    }

    /**
     * This method provides an icon for this data adapter.
     *
     * @param size the size in pixel of the icon
     * @return the icon image, can be null if the image is not available
     */
    @Override
    public Image getIcon(final int size)
    {
        if (size == 16) {
            return Activator.getDefault().getImage("images/no_image.png");
        }
        return null;
    }

    /**
     * Return the service for the passed data adapter or null if the factory
     * dosen't know how to handle it
     *
     * @param dataAdapter the data adapter
     * @return the service for the data adapter or null
     */
    @Override
    public DataAdapterService createDataAdapterService(final DataAdapter dataAdapter)
    {
        if (dataAdapter instanceof EFapsDataAdapter)
            return new EFapsDataAdapterService(
                            DefaultJasperReportsContext.getInstance(),
                            (EFapsDataAdapter) dataAdapter);
        return null;
    }

    /**
     * Return a converter that can be used to build a JSS data adapter from an
     * iReport data adapter definition
     *
     * @return the class to build a Jaspersoft Studio data adapter from it
     *         iReport configuration. It can be null if this function is not
     *         provided
     */
    @Override
    public IDataAdapterCreator iReportConverter()
    {
        return null;
    }

    /**
     * Verifies if the current data adapter factory is deprecated.
     *
     * @return true if it was deprecated, false otherwise
     */
    @Override
    public boolean isDeprecated()
    {
        return false;
    }
}
