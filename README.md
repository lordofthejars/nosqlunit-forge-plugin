NoSQLUnit [NoSQLUnit](https://github.com/lordofthejars/nosql-unit) is a JUnit extension to make writing unit and integration tests of systems that use NoSQL backend easier.

Forge is a core framework and next-generation shell for tooling and automation at a command line level.

With NoSQLUnit Forge Plugin we can use Forge to create tests for NoSQL databases using NoSQLUnit.

With this plugin we can create three kind of tests depending on the lifecycle that is required:

* Embedded: typically used in unit testing which starts an embedded instance of required database (not supported by all engines).
* Managed: usually used during integration or high level tests, which starts a remote instance in the same computer where tests are run.
* Remote: which uses already run database instances, usually in remote computers.

In current version of plugin, they are supported next databases:

* MongoDB
* Neo4j
* Redis
* Cassandra
* HBase
* Infinispan
* CouchDB

When we execute the main command of this plugin, one JUnit test configured with NoSQLUnit features and one dataset file will be created.

Moreover the created test will contain one method for each public method of development class under test.

So let's start:

The main command is _nosqlunit_. Then the lifecycle, which can be _embedded_, _managed_ or _remote_. And finally depending on the chosen lifecycle the arguments.

The common arguments are:

* engine: we choose which database engine we want to use.
* databaseName: we set the name of the database under test.
* classname: we set the name of the test class created by the plugin.
* classUnderTest: full class name of the class we want to write a test.

## Embedded

There is no special arguments

## Managed

* path: home directory where NoSQL database is installed.

## Remote

* host: server adress.
* port: server port.

So for example a valid command will be:

> nosqlunit managed --engine MONGODB --path /opt/mongo --databaseName test --classname MyTest --classUnderTest com.example.MyClass.java

Of course play with tab to make your life easier.


