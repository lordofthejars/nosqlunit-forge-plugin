package com.lordofthejars.nosqlunit.forge;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityJavaTemplate implements JavaTemplate {

	static {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        Velocity.init(properties);
    }
	
	@Override
	public String create(String template, Map<Object, Object> parameters) {
		
		StringWriter stringWriter = new StringWriter();
		Velocity.mergeTemplate(template, "UTF-8", new VelocityContext(parameters), stringWriter);
		return stringWriter.toString();
	
	}
	
}
