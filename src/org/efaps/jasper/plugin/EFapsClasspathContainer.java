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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.Bundle;
import org.efaps.jasper.plugin.Activator;

public class EFapsClasspathContainer implements IClasspathContainer {
    public final static Path ID = new Path("org.efaps.jasper.eFaps_CONTAINER"); 
    
    private IPath _path;

    public EFapsClasspathContainer(IPath path, IJavaProject project) {
        _path = path;
    }

    public IClasspathEntry[] getClasspathEntries() {
        List<IClasspathEntry> entryList = new ArrayList<IClasspathEntry>();

        Bundle bundle = Activator.getDefault().getBundle();
        Enumeration<URL> urls = bundle.findEntries("lib/", "*.jar", true);
        if (urls != null) {
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                try {
                    URL fileURL = FileLocator.toFileURL(url);
                    URI uri = new URI(fileURL.getProtocol(), fileURL.getUserInfo(), fileURL.getHost(), fileURL.getPort(), fileURL.getPath(), fileURL.getQuery(), null);
                    // fileURL.toURI();
                    Path binpath = new Path(new File(uri).getAbsolutePath());
                    Path srcpath = binpath;
                    entryList.add(JavaCore.newLibraryEntry(binpath, srcpath, new Path("/"))); 
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // convert the list to an array and return it
        return entryList.toArray(new IClasspathEntry[entryList.size()]);
    }

    public String getDescription() {
        return "eFaps Library"; 
    }

    public int getKind() {
        return IClasspathContainer.K_APPLICATION;
    }

    public IPath getPath() {
        return _path;
    }
}
