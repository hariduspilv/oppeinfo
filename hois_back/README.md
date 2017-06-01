# HITSA OIS backend
* Spring boot

This project uses external library(hois-ws)

compile(':hois_ws')

Place the jar in 'libs' folder build with 'gradle build'

repositories {
	flatDir {
		dirs 'libs'
	}
	mavenCentral()
}