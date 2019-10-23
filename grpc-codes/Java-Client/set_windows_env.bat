@echo off
goto check_Permissions

:check_Permissions
    echo Administrative permissions required. Detecting permissions...

    net session >nul 2>&1
    if %errorLevel% == 0 (
        echo Success: Administrative permissions confirmed.
	setx -m JAVA_HOME "C:\Program Files\Java\jdk1.8.0_221"
	setx -m MAVEN_HOME "C:\opt\apache-maven-3.6.2"
	setx -m PATH "%PATH%;C:\opt\apache-maven-3.6.2\bin"
    ) else (
        echo Failure: Current permissions inadequate.
    )

    pause >nul