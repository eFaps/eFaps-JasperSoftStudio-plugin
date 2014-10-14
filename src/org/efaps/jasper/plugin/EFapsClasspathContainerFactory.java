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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jasperreports.eclipse.classpath.container.IClasspathContainerFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class EFapsClasspathContainerFactory
    implements IClasspathContainerFactory
{

    @Override
    public void createJRClasspathContainer(final IProgressMonitor monitor,
                                           final List<IClasspathEntry> centries,
                                           final IJavaProject javaProject)
        throws JavaModelException
    {
        final EFapsClasspathContainer classpathContainer = new EFapsClasspathContainer(
                        null, javaProject);
        JavaCore.setClasspathContainer(EFapsClasspathContainer.ID,
                        new IJavaProject[] { javaProject },
                        new IClasspathContainer[] { classpathContainer }, monitor);
        centries.add(JavaCore.newContainerEntry(EFapsClasspathContainer.ID,
                        true));
        javaProject.setRawClasspath(centries.toArray(new IClasspathEntry[centries.size()]),  monitor);
    }

    @Override
    public Set<Path> isRemovable()
    {
        final Set<Path> set = new HashSet<Path>();
        set.add(EFapsClasspathContainer.ID);
        return set;
    }

}
