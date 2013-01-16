package com.lordofthejars.nosqlunit.forge;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.forge.parser.JavaParser;
import org.jboss.forge.parser.java.Field;
import org.jboss.forge.parser.java.JavaClass;
import org.jboss.forge.parser.java.JavaSource;
import org.jboss.forge.parser.java.Method;
import org.jboss.forge.parser.java.impl.JavaClassImpl;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.facets.DependencyFacet;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.project.facets.ResourceFacet;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.resources.java.JavaResource;
import org.jboss.forge.shell.events.PickupResource;
import org.jboss.forge.test.AbstractShellTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class WhenNoSqlUnitForgePluginIsInstalled extends AbstractShellTest {

	private static final String MANAGER_CLASS = "package com.test;\n" + "\n" + "public class MyManagerClass {\n" + "\n"
			+ "	private int age; \n public void myOperation() {\n" + "		\n" + "	}\n" + "	\n" + "}";

	
	@Inject
	private Event<PickupResource> pickup;

	@Deployment
	public static JavaArchive getDeployment() {
		// The deployment method is where you must add references to your
		// classes, packages, and
		// configuration files, via Arquillian.
		return AbstractShellTest.getDeployment().addPackages(true, NoSqlUnitPlugin.class.getPackage());
	}

	@Test
	public void test_for_required_embedded_engine_should_be_created() throws Exception {
		// Create a new barebones Java project
		Project p = initializeJavaProject();
		createManagerClass(p);

		queueInputLines("MONGODB", "test", "MyTest", "com.test.MyManagerClass");
		getShell().execute("nosqlunit embedded");


		DependencyBuilder dependency = DependencyBuilder
	               .create("com.lordofthejars:nosqlunit-mongodb");
		
		assertThat(p.getFacet(DependencyFacet.class).hasDirectDependency(dependency), is(true));

		JavaSourceFacet javaFacet = p.getFacet(JavaSourceFacet.class);
		
		JavaResource testJavaResource = javaFacet.getTestJavaResource("com.test.MyTest");
		assertThat(testJavaResource.getName(), is("MyTest.java"));
		
		JavaSource<?> testJavaSource = testJavaResource.getJavaSource();
		
		JavaClassImpl javaClassImpl = (JavaClassImpl) testJavaSource;
		
		Method<JavaClass> methodUnderTest = javaClassImpl.getMethod("myOperation");
		
		assertThat(methodUnderTest.getName(), is("myOperation"));
		assertThat(methodUnderTest.getAnnotation(Test.class), notNullValue());
		
		assertThat(javaClassImpl.getAnnotation("UsingDataSet"), notNullValue());
		
		Field<JavaClass> inMemoryMongoDb = javaClassImpl.getField("IN_MEMORY_MONGO_DB");
		
		assertThat(inMemoryMongoDb.getType(), is("InMemoryMongoDb"));
		assertThat(inMemoryMongoDb.getAnnotation(ClassRule.class), notNullValue());
		
		Field<JavaClass> embeddedMongoDb = javaClassImpl.getField("embeddedMongoDbRule");
		assertThat(embeddedMongoDb.getType(), is("MongoDbRule"));
		assertThat(embeddedMongoDb.getAnnotation(Rule.class), notNullValue());
		
		ResourceFacet resources = p.getFacet(ResourceFacet.class);

		FileResource<?> resource = (FileResource<?>) resources.getTestResourceFolder().getChild("com/test/"+DatabaseEnum.MONGODB.getDatasetName());
		assertThat(resource.exists(), is(true));
	}

	@Test
	public void test_for_required_managed_engine_should_be_created() throws Exception {
		// Create a new barebones Java project
		Project p = initializeJavaProject();
		createManagerClass(p);

		queueInputLines("MONGODB", "/opt/mongodb", "test", "MyTest", "com.test.MyManagerClass");
		getShell().execute("nosqlunit managed");


		DependencyBuilder dependency = DependencyBuilder
	               .create("com.lordofthejars:nosqlunit-mongodb");
		
		assertThat(p.getFacet(DependencyFacet.class).hasDirectDependency(dependency), is(true));

		JavaSourceFacet javaFacet = p.getFacet(JavaSourceFacet.class);
		
		JavaResource testJavaResource = javaFacet.getTestJavaResource("com.test.MyTest");
		assertThat(testJavaResource.getName(), is("MyTest.java"));
		
		JavaSource<?> testJavaSource = testJavaResource.getJavaSource();
		
		JavaClassImpl javaClassImpl = (JavaClassImpl) testJavaSource;
		
		Method<JavaClass> methodUnderTest = javaClassImpl.getMethod("myOperation");
		
		assertThat(methodUnderTest.getName(), is("myOperation"));
		assertThat(methodUnderTest.getAnnotation(Test.class), notNullValue());
		
		assertThat(javaClassImpl.getAnnotation("UsingDataSet"), notNullValue());
		
		Field<JavaClass> managedMongoDb = javaClassImpl.getField("MANAGED_MONGO_DB");
		
		assertThat(managedMongoDb.getType(), is("ManagedMongoDb"));
		assertThat(managedMongoDb.getAnnotation(ClassRule.class), notNullValue());
		
		Field<JavaClass> embeddedMongoDb = javaClassImpl.getField("managedMongoDbRule");
		assertThat(embeddedMongoDb.getType(), is("MongoDbRule"));
		assertThat(embeddedMongoDb.getAnnotation(Rule.class), notNullValue());
		
		ResourceFacet resources = p.getFacet(ResourceFacet.class);
		
		FileResource<?> resource = (FileResource<?>) resources.getTestResourceFolder().getChild("com/test/"+DatabaseEnum.MONGODB.getDatasetName());
		assertThat(resource.exists(), is(true));
		
	}
	
	@Test
	public void test_for_required_remote_engine_should_be_created() throws Exception {
		// Create a new barebones Java project
		Project p = initializeJavaProject();
		createManagerClass(p);

		queueInputLines("MONGODB", "localhost", "200", "test", "MyTest", "com.test.MyManagerClass");
		getShell().execute("nosqlunit remote");


		DependencyBuilder dependency = DependencyBuilder
	               .create("com.lordofthejars:nosqlunit-mongodb");
		
		assertThat(p.getFacet(DependencyFacet.class).hasDirectDependency(dependency), is(true));

		JavaSourceFacet javaFacet = p.getFacet(JavaSourceFacet.class);
		
		JavaResource testJavaResource = javaFacet.getTestJavaResource("com.test.MyTest");
		assertThat(testJavaResource.getName(), is("MyTest.java"));
		
		JavaSource<?> testJavaSource = testJavaResource.getJavaSource();
		
		JavaClassImpl javaClassImpl = (JavaClassImpl) testJavaSource;
		
		Method<JavaClass> methodUnderTest = javaClassImpl.getMethod("myOperation");
		
		assertThat(methodUnderTest.getName(), is("myOperation"));
		assertThat(methodUnderTest.getAnnotation(Test.class), notNullValue());
		
		assertThat(javaClassImpl.getAnnotation("UsingDataSet"), notNullValue());
		
		Field<JavaClass> embeddedMongoDb = javaClassImpl.getField("remoteMongoDbRule");
		assertThat(embeddedMongoDb.getType(), is("MongoDbRule"));
		assertThat(embeddedMongoDb.getAnnotation(Rule.class), notNullValue());
		
		ResourceFacet resources = p.getFacet(ResourceFacet.class);
		
		FileResource<?> resource = (FileResource<?>) resources.getTestResourceFolder().getChild("com/test/"+DatabaseEnum.MONGODB.getDatasetName());
		assertThat(resource.exists(), is(true));
		
	}
	
	private void createManagerClass(Project project) throws FileNotFoundException {
		JavaSourceFacet java = project.getFacet(JavaSourceFacet.class);

		JavaClass testClass = JavaParser.parse(JavaClass.class, MANAGER_CLASS);
		java.saveJavaSource(testClass);

		pickup.fire(new PickupResource(java.getJavaResource(testClass)));
	}

}
