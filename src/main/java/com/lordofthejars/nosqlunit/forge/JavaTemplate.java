package com.lordofthejars.nosqlunit.forge;

import java.util.Map;

public interface JavaTemplate {

	String create(String template, Map<Object, Object> parameters);

}