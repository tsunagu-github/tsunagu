echo off

@Call :_ëΩèdãNìÆñhé~ %* 4>>"%~dpnx0"
@goto :eof
@:_ëΩèdãNìÆñhé~

echo ==========================
echo Javaé¿çs
echo ==========================

cd C:\Users\kis.o-note-022\Desktop\dynamic_batch

set CLASSPATH=.\;lib\commons-logging-1.1.1.jar;lib\animal-sniffer-annotations-1.17.jar;lib\aopalliance-1.0.jar;lib\spring-aop-4.0.1.RELEASE.jar;lib\spring-aspects-4.0.1.RELEASE.jar;lib\spring-beans-4.0.1.RELEASE.jar;lib\spring-boot-2.4.5.jar;lib\spring-boot-autoconfigure-2.4.5.jar;lib\spring-boot-starter-2.4.5.jar;lib\spring-boot-starter-jdbc-2.4.5.jar;lib\spring-boot-starter-logging-2.4.5.jar;lib\spring-context-4.0.1.RELEASE.jar;lib\spring-context-support-4.0.1.RELEASE.jar;lib\spring-core-4.0.1.RELEASE.jar;lib\spring-expression-4.0.1.RELEASE.jar;lib\spring-instrument-tomcat-4.0.1.RELEASE.jar;lib\spring-jdbc-4.0.1.RELEASE.jar;lib\SSMIX2storageMaker-1.0.jar;lib\CsvConverter-1.0.jar;lib\commons-beanutils-1.8.0.jar;lib\commons-codec-1.10.jar;lib\commons-compress-1.12.jar;lib\commons-csv-1.4.jar;lib\commons-digester-2.0.jar;lib\commons-fileupload-1.3.2.jar;lib\commons-io-2.0.1.jar;lib\commons-lang3-3.9.jar;lib\commons-text-1.7.jar;lib\checker-qual-2.8.1.jar;lib\classmate-1.1.0.jar;lib\error_prone_annotations-2.3.2.jar;lib\failureaccess-1.0.1.jar;lib\gson-2.7.jar;lib\guava-28.0-jre.jar;lib\hapi-fhir-base-4.2.0.jar;lib\hibernate-validator-5.2.4.Final.jar;lib\HikariCP-3.4.5.jar;lib\j2objc-annotations-1.3.jar;lib\jackson-annotations-2.4.0.jar;lib\jackson-core-2.4.4.jar;lib\jackson-databind-2.4.4.jar;lib\jakarta.annotation-api-1.3.5.jar;lib\javax.servlet.jsp.jstl-1.2.2.jar;lib\javax.servlet.jsp.jstl-api-1.2.1.jar;lib\jboss-logging-3.2.1.Final.jar;lib\jcl-over-slf4j-1.7.6.jar;lib\json-simple-1.1.jar;lib\jsp-api-2.1.jar;lib\jsr305-3.0.2.jar;lib\jstl-api-1.2.jar;lib\jul-to-slf4j-1.7.30.jar;lib\kjtCore-1.0.jar;lib\kotlin-runtime-1.0.4.jar;lib\kotlin-stdlib-1.0.4.jar;lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;lib\log4j-1.2.17.jar;lib\log4j-api-2.13.3.jar;lib\log4j-to-slf4j-2.13.3.jar;lib\logback-classic-1.2.3.jar;lib\logback-core-1.2.3.jar;lib\mysql-connector-java-5.1.39.jar;lib\org.hl7.fhir.r4-4.2.0.jar;lib\org.hl7.fhir.utilities-4.2.0.jar;lib\postgresql-9.4-1203-jdbc4.jar;lib\servlet-api-2.5.jar;lib\slf4j-api-1.7.6.jar;lib\slf4j-log4j12-1.7.21.jar;lib\snakeyaml-1.27.jar;lib\tiles-api-3.0.5.jar;lib\tiles-autotag-core-runtime-1.1.0.jar;lib\tiles-core-3.0.5.jar;lib\tiles-jsp-3.0.5.jar;lib\tiles-request-api-1.0.6.jar;lib\tiles-request-jsp-1.0.6.jar;lib\tiles-request-servlet-1.0.6.jar;lib\tiles-servlet-3.0.5.jar;lib\tiles-template-3.0.5.jar;lib\validation-api-1.1.0.Final.jar;lib\zip4j-1.3.2.jar;lib\spring-web-4.0.1.RELEASE.jar;lib\spring-webmvc-4.0.1.RELEASE.jar;lib\spring-webmvc-portlet-4.0.1.RELEASE.jar;lib\spring-websocket-4.0.1.RELEASE.jar;lib\spring-test-4.0.1.RELEASE.jar;lib\spring-tx-4.0.1.RELEASE.jar;lib\spring-orm-4.0.1.RELEASE.jar;lib\spring-oxm-4.0.1.RELEASE.jar;lib\spring-jms-4.0.1.RELEASE.jar;lib\spring-messaging-4.0.1.RELEASE.jar;lib\spring-instrument-4.0.1.RELEASE.jar;lib\httpclient-4.5.4.jar;lib\httpcore-4.4.7.jar

java -classpath PHR-Lib-1.0.jar;%CLASSPATH% phr.execute.AppAccessExecute 1234567-000001

pause
