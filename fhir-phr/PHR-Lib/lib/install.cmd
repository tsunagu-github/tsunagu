ECHO OFF
rem ***********************************************************
rem * KIS Java Toole Coreのインストール
rem * ---------------------------------------------------------
rem * JAVA_HOMEとMAVEN_HOMEを環境にあわせて実行してください
rem *   例）
rem *       SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_102
rem *       SET MAVEN_HOME=D:\ProgramFiles\NetBeans8.1\java\maven
rem * 
rem ***********************************************************
SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_111
SET MAVEN_HOME=F:\work2\tools\NetBeans 8.2\java\maven

SET LIB_ROOT=%~dp0
echo %LIB_ROOT%
"%MAVEN_HOME%\bin\mvn" install:install-file -Dfile=%LIB_ROOT%kjt.core-1.0.jar -DgroupId=kis.java.tools.core -DartifactId=kjtCore -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
