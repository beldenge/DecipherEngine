rem
rem Copyright 2015 George Belden
rem 
rem This file is part of ZodiacEngine.
rem 
rem ZodiacEngine is free software: you can redistribute it and/or modify it under
rem the terms of the GNU General Public License as published by the Free Software
rem Foundation, either version 3 of the License, or (at your option) any later
rem version.
rem 
rem ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
rem ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
rem FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
rem details.
rem 
rem You should have received a copy of the GNU General Public License along with
rem ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
rem

set APP_DIR=C:\deploy
set CONFIG_DIR=%APP_DIR%\config
set LIB_DIR=%APP_DIR%\lib
set APP_CLASSPATH=%CONFIG_DIR%;ZodiacEngine-1.0-SNAPSHOT.jar;%LIB_DIR%\org.springframework.beans-3.0.6.RELEASE.jar;%LIB_DIR%\log4j-1.2.16.jar;%LIB_DIR%\org.springframework.context-3.0.6.RELEASE.jar;%LIB_DIR%\org.springframework.core-3.0.6.RELEASE.jar;%LIB_DIR%\commons-logging-1.1.1.jar;%LIB_DIR%\org.springframework.asm-3.0.6.RELEASE.jar;%LIB_DIR%\org.springframework.expression-3.0.6.RELEASE.jar;%LIB_DIR%\hibernate3.jar;%LIB_DIR%\org.springframework.orm-3.0.6.RELEASE.jar;%LIB_DIR%\org.springframework.transaction-3.0.6.RELEASE.jar;%LIB_DIR%\hibernate-jpa-2.0-api-1.0.1.Final.jar;%LIB_DIR%\jta-1.1.jar;%LIB_DIR%\org.springframework.jdbc-3.0.6.RELEASE.jar;%LIB_DIR%\commons-dbcp-1.4.jar;%LIB_DIR%\commons-pool-1.5.6.jar;%LIB_DIR%\SentenceBuilder-1.0-SNAPSHOT.jar;%LIB_DIR%\dom4j-1.6.1.jar;%LIB_DIR%\slf4j-api-1.6.1.jar;%LIB_DIR%\commons-collections-3.1.jar;%LIB_DIR%\javassist-3.12.0.GA.jar;%LIB_DIR%\postgresql-9.1-901.jdbc4.jar;%LIB_DIR%\antlr-2.7.6.jar;%LIB_DIR%\CFGReader-1.0-SNAPSHOT.jar
set APP_NAME=com.ciphertool.zodiacengine.CipherSolutionEngine
java -Xms512m -Xmx1280m -cp %APP_CLASSPATH% %APP_NAME%