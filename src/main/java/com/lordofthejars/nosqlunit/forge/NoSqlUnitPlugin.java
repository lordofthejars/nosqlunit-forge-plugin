package com.lordofthejars.nosqlunit.forge;

import static ch.lambdaj.Lambda.convert;
import static ch.lambdaj.Lambda.filter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.forge.parser.JavaParser;
import org.jboss.forge.parser.java.JavaClass;
import org.jboss.forge.parser.java.JavaInterface;
import org.jboss.forge.parser.java.JavaSource;
import org.jboss.forge.parser.java.impl.JavaClassImpl;
import org.jboss.forge.parser.java.impl.JavaInterfaceImpl;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.dependencies.Dependency;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.dependencies.ScopeType;
import org.jboss.forge.project.facets.DependencyFacet;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.project.facets.ResourceFacet;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.resources.java.JavaResource;
import org.jboss.forge.shell.PromptType;
import org.jboss.forge.shell.Shell;
import org.jboss.forge.shell.events.PickupResource;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.Command;
import org.jboss.forge.shell.plugins.Help;
import org.jboss.forge.shell.plugins.Option;
import org.jboss.forge.shell.plugins.PipeOut;
import org.jboss.forge.shell.plugins.Plugin;
import org.jboss.forge.shell.plugins.RequiresFacet;

import ch.lambdaj.function.convert.Converter;

import com.lordofthejars.nosqlunit.forge.commandcompleter.DatabaseCommandCompleter;

@Alias("nosqlunit")
@RequiresFacet(JavaSourceFacet.class)
@Help("A plugin that helps creating NoSQLUnit tests")
public class NoSqlUnitPlugin implements Plugin {

	private static final String EMBEDDED_MODE = "embedded";
	private static final String MANAGED_MODE = "managed";
	private static final String REMOTE_MODE = "remote";

	private TestCreator testCreator = new JavaTestCreator();

	@Inject
	private Project project;

	@Inject
	private Shell shell;

	@Inject
	private Event<PickupResource> pickup;

	@Command(value = "embedded")
	public void createUnitTest(PipeOut out,
			@Option(name = "engine", required = true, completer = DatabaseCommandCompleter.class) String database,
			@Option(name = "databaseName", required = true) String databaseName,
			@Option(name = "classname", required = true, help="Test class name") String classname,
			@Option(name = "classUnderTest", required = true, type = PromptType.JAVA_CLASS, help="Class under test") JavaResource classUnderTest)
			throws FileNotFoundException, ClassNotFoundException {

		DatabaseEnum databaseEnum = databaseEnum(database);

		String javaTestSource = createTestJavaClass(databaseEnum, classname, classUnderTest, EMBEDDED_MODE,
				databaseName, embeddedParameters(classname, classUnderTest, databaseName));

		createTestClass(databaseEnum, javaTestSource);
		createDatasetFile(databaseEnum, classUnderTest);

	}

	private void createDatasetFile(DatabaseEnum databaseEnum, JavaResource javaResource) throws FileNotFoundException {

		final String testPackage = javaResource.getJavaSource().getPackage();
		
		ResourceFacet resources = project.getFacet(ResourceFacet.class);

		FileResource<?> resource = (FileResource<?>) resources.getTestResourceFolder().getChild(datasetLocation(databaseEnum, testPackage));
		
		shell.print(resource.toString());
		
		if (!resource.exists()) {
			InputStream datasetStream = NoSqlUnitPlugin.class.getResourceAsStream("/dataset/"+databaseEnum.getDatasetName());
			resource.setContents(datasetStream);
		}
	}

	private String datasetLocation(DatabaseEnum databaseEnum, final String testPackage) {
		return testPackage.replace('.', '/')+"/"+databaseEnum.getDatasetName();
	}

	private void createTestClass(DatabaseEnum databaseEnum, String javaTestSource) throws FileNotFoundException {
		setupDependency(databaseEnum);
		JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

		JavaClass testClass = JavaParser.parse(JavaClass.class, javaTestSource);
		java.saveTestJavaSource(testClass);

		pickup.fire(new PickupResource(java.getTestJavaResource(testClass)));
	}

	@Command(value = "managed")
	public void createManagedTest(PipeOut out,
			@Option(name = "engine", required = true, completer = DatabaseCommandCompleter.class) String database,
			@Option(name = "path", required = true, help="Full path to installation directory") String path,
			@Option(name = "databaseName", required = true) String databaseName,
			@Option(name = "classname", required = true, help="Test class name") String classname,
			@Option(name = "classUnderTest", required = true, type = PromptType.JAVA_CLASS, help="Class under test") JavaResource classUnderTest)
			throws FileNotFoundException, ClassNotFoundException {

		DatabaseEnum databaseEnum = databaseEnum(database);

		String javaTestSource = createTestJavaClass(databaseEnum, classname, classUnderTest, MANAGED_MODE,
				databaseName, managedParameters(classname, classUnderTest, databaseName, path));

		createTestClass(databaseEnum, javaTestSource);
		createDatasetFile(databaseEnum, classUnderTest);
	}

	@Command(value = "remote")
	public void createRemoteTest(PipeOut out,
			@Option(name = "engine", required = true, completer = DatabaseCommandCompleter.class) String database,
			@Option(name = "host", required = true) String host, @Option(name = "port", required = true) int port,
			@Option(name = "databaseName", required = true) String databaseName,
			@Option(name = "classname", required = true, help="Test class name") String classname,
			@Option(name = "classUnderTest", required = true, type = PromptType.JAVA_CLASS, help="Class under test") JavaResource classUnderTest)
			throws FileNotFoundException, ClassNotFoundException {

		DatabaseEnum databaseEnum = databaseEnum(database);

		String javaTestSource = createTestJavaClass(databaseEnum, classname, classUnderTest, REMOTE_MODE, databaseName,
				remoteParameters(classname, classUnderTest, databaseName, host, port));

		createTestClass(databaseEnum, javaTestSource);
		createDatasetFile(databaseEnum, classUnderTest);

	}

	private String createTestJavaClass(DatabaseEnum databaseEnum, String classname, JavaResource classUnderTest,
			String mode, String databaseName, Map<Object, Object> parameters) throws FileNotFoundException,
			ClassNotFoundException {
		return testCreator.createTestClass(databaseEnum, mode, parameters);
	}

	private Map<Object, Object> remoteParameters(String classname, JavaResource classUnderTest, String databaseName,
			String host, int port) throws FileNotFoundException {
		JavaSource<?> javaSource = classUnderTest.getJavaSource();

		List<String> testMethodNames = getTestMethodNames(javaSource);
		String packageName = javaSource.getPackage();

		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("package", packageName);
		parameters.put("classname", classname);
		parameters.put("methods", testMethodNames);
		parameters.put("database", databaseName);
		parameters.put("host", host);
		parameters.put("port", port);

		return parameters;
	}

	private Map<Object, Object> managedParameters(String classname, JavaResource classUnderTest, String databaseName,
			String path) throws FileNotFoundException {
		JavaSource<?> javaSource = classUnderTest.getJavaSource();

		List<String> testMethodNames = getTestMethodNames(javaSource);
		String packageName = javaSource.getPackage();

		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("package", packageName);
		parameters.put("classname", classname);
		parameters.put("methods", testMethodNames);
		parameters.put("database", databaseName);
		parameters.put("path", path);

		return parameters;
	}

	private Map<Object, Object> embeddedParameters(String classname, JavaResource classUnderTest, String databaseName)
			throws FileNotFoundException {
		JavaSource<?> javaSource = classUnderTest.getJavaSource();

		List<String> testMethodNames = getTestMethodNames(javaSource);
		String packageName = javaSource.getPackage();

		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("package", packageName);
		parameters.put("classname", classname);
		parameters.put("methods", testMethodNames);
		parameters.put("database", databaseName);
		return parameters;
	}

	private DatabaseEnum databaseEnum(String database) {
		return DatabaseEnum.valueOf(DatabaseEnum.class, database);
	}

	private void setupDependency(DatabaseEnum databaseEnum) {
		addJUnitDependency();
		addNoSqlUnitConfiguredDependency(databaseEnum);
	}

	private void addJUnitDependency() {
		Dependency junitDependency = DependencyBuilder.create().setGroupId("junit")
				.setArtifactId("junit").setScopeType(ScopeType.TEST);
		
		DependencyFacet dependencyFacet = project.getFacet(DependencyFacet.class);

		if (!dependencyFacet.hasEffectiveDependency(junitDependency)) {
			List<Dependency> versions = dependencyFacet.resolveAvailableVersions(junitDependency);
			dependencyFacet.addDirectDependency(DependencyUtil.getLatestNonSnapshotVersion(versions));
		}
		
	}
	
	private void addNoSqlUnitConfiguredDependency(DatabaseEnum databaseEnum) {
		Dependency nosqlDependency = DependencyBuilder.create().setGroupId("com.lordofthejars")
				.setArtifactId(databaseEnum.getArtifact()).setScopeType(ScopeType.TEST);

		DependencyFacet dependencyFacet = project.getFacet(DependencyFacet.class);

		if (!dependencyFacet.hasEffectiveDependency(nosqlDependency)) {

			List<Dependency> versions = dependencyFacet.resolveAvailableVersions(nosqlDependency);
			dependencyFacet.addDirectDependency(DependencyUtil.getLatestNonSnapshotVersion(versions));
		}
	}

	private List<String> getTestMethodNames(JavaSource<?> javaSource) {

		if (javaSource instanceof JavaClassImpl) {

			JavaClassImpl javaClassImpl = (JavaClassImpl) javaSource;
			List<org.jboss.forge.parser.java.Method<JavaClass>> classPublicMethods = getClassPublicMethods(javaClassImpl
					.getMethods());

			return convert(classPublicMethods, new Converter<org.jboss.forge.parser.java.Method<JavaClass>, String>() {

				@Override
				public String convert(org.jboss.forge.parser.java.Method<JavaClass> method) {
					return method.getName();
				}
			});

		} else {
			if (javaSource instanceof JavaInterfaceImpl) {
				JavaInterfaceImpl javaInterfaceImpl = (JavaInterfaceImpl) javaSource;
				List<org.jboss.forge.parser.java.Method<JavaInterface>> interfacePublicMethods = getInterfacePublicMethods(javaInterfaceImpl
						.getMethods());

				return convert(interfacePublicMethods,
						new Converter<org.jboss.forge.parser.java.Method<JavaInterface>, String>() {

							@Override
							public String convert(org.jboss.forge.parser.java.Method<JavaInterface> method) {
								return method.getName();
							}
						});
			} else {
				return new ArrayList<String>();
			}
		}

	}

	private List<org.jboss.forge.parser.java.Method<JavaClass>> getClassPublicMethods(
			List<org.jboss.forge.parser.java.Method<JavaClass>> methods) {
		return filter(new PublicMethodMatcher(), methods);
	}

	private List<org.jboss.forge.parser.java.Method<JavaInterface>> getInterfacePublicMethods(
			List<org.jboss.forge.parser.java.Method<JavaInterface>> methods) {
		return filter(new PublicMethodMatcher(), methods);
	}

	private class PublicMethodMatcher extends TypeSafeMatcher<org.jboss.forge.parser.java.Method<JavaClass>> {

		@Override
		public void describeTo(Description description) {

		}

		@Override
		public boolean matchesSafely(org.jboss.forge.parser.java.Method<JavaClass> item) {
			return item.isPublic();
		}

	}

}
