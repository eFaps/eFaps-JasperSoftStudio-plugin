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
import java.util.Arrays;
import java.util.List;

import net.sf.jasperreports.engine.design.JRDesignQuery;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.jaspersoft.studio.data.designer.QueryDesigner;
import com.jaspersoft.studio.wizards.ContextHelpIDs;

/**
 * Query designer for PL/SQL language, that simply provides syntax coloring
 * support.
 *
 */
public class EqlQueryDesigner
    extends QueryDesigner
{

    /* Text area where enter the query */
    protected StyledText queryTextArea;
    private final EqlLineStyler lineStyler = new EqlLineStyler();

    public Control createControl(final Composite parent)
    {
        control = (StyledText) super.createControl(parent);
        control.addLineStyleListener(this.lineStyler);
        return control;
    }

    protected void queryTextAreaModified()
    {
        // keep the query info updated
        ((JRDesignQuery) jDataset.getQuery()).setText(this.queryTextArea.getText());
    }

    @Override
    public String getContextHelpId()
    {
        return ContextHelpIDs.PREFIX.concat("query_eFaps");
    }
}
