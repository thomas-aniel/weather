# Weather Application

I Changed a lot of staff: packages, names, logic. Almost a complete rewrite. 

## Starting the Application

The application is a normal Spring Boot application, so it can be started using the application jar file.
The directory src/test/resources has an example application.yml that is used for integration tests and can be used as a starting point to
start the application. Just add the application.yml to the classpath.

I added `initialization-mode: always` to the application yaml, so that the database is always created. I also put H2 and Postgres
drivers into the deployed jar. Personally I would prefer to do without these things, but they make it easier.


## Notes
- the package name com.assignment is a bit strange, but I assume that it's for a fictitious assignment company, so I left as it is
- from the existing pom.xml I understand that the database used is Postgres. I wanted to use it for integration tests, as it would be better to
use an environment as similar as possible to production. However, I don't know if there is an easy way to provide Postgres to the build process
to the current build environment (is there an existing Postgres? Docker), so I used H2
- the flow is very simple. Aside from the happy flow, there only seem to be error conditions. So I only added a test for the case when
Open Weather fails in the integration tests
- nothing is said about the Java version, although the pom has a variable seeming to indicate 11. I use Java 14,
and ByteBuddy is complaining about the unsupported major number. So I changed the Spring version, hoping it's not a problem
- there is the requirement that the API key should be given from outside the application for security reasons. However, for testability reasons
at the the host of the Open Weather URL should also be configurable. So I decided to put the whole URL in the external configuration.
