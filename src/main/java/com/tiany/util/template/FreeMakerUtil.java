package com.tiany.util.template;

import com.tiany.util.io.StreamUtil;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

public class FreeMakerUtil {
	
	private static final int MAX_SIZE = 1024*1024*10;//10M
	
	private static final String ENCODING = "UTF-8";
	
	public static String getInstance(File templateFile,Map<String, Object> paramMap) {  
        try {  
            //创建一个合适的Configration对象  
            Configuration configuration = new Configuration();  
            configuration.setDirectoryForTemplateLoading(templateFile.getParentFile());  
            configuration.setObjectWrapper(new DefaultObjectWrapper());  
            configuration.setDefaultEncoding(ENCODING);   //这个一定要设置，不然在生成的页面中 会乱码  
            //获取或创建一个模版。  
            Template template = configuration.getTemplate(templateFile.getName());  
           
            ByteArrayOutputStream baos = new ByteArrayOutputStream(MAX_SIZE);
            Writer writer  = new OutputStreamWriter(baos,ENCODING);  
            template.process(paramMap, writer);  
             
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            return StreamUtil.streamToString(bais);
        }  catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e.getMessage());
        }
    }  
	
}
