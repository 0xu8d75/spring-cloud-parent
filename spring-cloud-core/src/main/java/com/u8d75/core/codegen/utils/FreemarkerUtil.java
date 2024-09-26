package com.u8d75.core.codegen.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * FreemarkerUtil
 */
public class FreemarkerUtil {

    private static final String SLASH = "/";

    /**
     * getTemplate
     *
     * @param name name
     * @return Template
     */
    public Template getTemplate(String name) {
        Template temp = null;
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setClassForTemplateLoading(super.getClass(), "/template");
            temp = cfg.getTemplate(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * print
     *
     * @param name    name
     * @param rootMap rootMap
     */
    public void print(String name, Map<String, Object> rootMap) {
        try {
            Template temp = getTemplate(name);
            temp.process(rootMap, new PrintWriter(System.out)); //NOSONAR
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * fprint
     *
     * @param name    name
     * @param rootMap rootMap
     * @param path    path
     * @param outFile outFile
     */
    public void fprint(String name, Map<String, Object> rootMap, String path, String outFile) {
        File fileDir = new File(path + SLASH);
        if (!(fileDir.exists())) {
            fileDir.mkdirs();
        }
        File file = new File(path + SLASH + outFile);
        try (FileWriter out = new FileWriter(file)) {
            Template template = getTemplate(name);
            template.process(rootMap, out);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }

}
