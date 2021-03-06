package com.dgwave.car.repo;

import java.io.File;

import org.apache.maven.model.locator.ModelLocator;
import org.codehaus.plexus.component.annotations.Component;

import com.dgwave.car.common.CeylonUtil;

/**
 * This class locates the Ceylon module model from various sources.
 * 
 * @author Akber Choudhry
 */
@Component(role = ModelLocator.class)
public class CeylonModelLocator implements ModelLocator {

    /**
     * Implementing Maven API to locate the project model.
     * 
     * @param folder The folder in which to look
     * @return File The file that represents the POM. It may be a Ceylon properties or .module file
     */
    public File locatePom(final File folder) {
        if (folder != null && folder.exists() && folder.isDirectory()) {
            String moduleVersion = folder.getParentFile().getName();
            File[] list = folder.listFiles();
            File[] four = new File[CeylonUtil.NUM_CEYLON_JAVA_DEP_TYPES];

            for (File file : list) {
                if (file.isFile()) {
                    if ("module.xml".equals(file.getName())) {
                        four[0] = file;
                    } else if (file.getName().endsWith("-" + moduleVersion + ".module")) {
                        four[1] = file;
                    } else if ("module.properties".equals(file.getName())) {
                        four[2] = file;
                    } else if (file.getName().endsWith("-" + moduleVersion + ".car")) {
                        four[3] = file;
                    }
                }
            }

            for (File file : four) {
                if (file != null) {
                    return file;
                }
            }
        }

        return new File(folder, "pom.xml");
    }
}
