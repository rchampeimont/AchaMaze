To build/run the project, you need Sun Java 6 or OpenJDK 6.
You can build it using either:
- eclipse (the directory is an eclipse project)
- ant (using the build.xml file, just enter "ant")

Then, to run it do:
cd bin
java org.almacha.achamaze.AchaMaze

To rebuild the javadoc:
javadoc org.almacha.achamaze -sourcepath src -d doc
