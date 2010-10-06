ifeq "$(OS)" "Windows_NT"
CLASSPATH="lib\gluegen-rt.jar;lib\jogl.all.jar;lib\nativewindow.all.jar;lib\newt.all.jar"
else
CLASSPATH="lib/gluegen-rt.jar:lib/jogl.all.jar:lib/nativewindow.all.jar:lib/newt.all.jar"
endif

FILES=`find src -name *.scala`

all:
	mkdir -p target/classes; scalac -classpath $(CLASSPATH) -d "target\classes" $(FILES)

clean:
	rm -rf target; find . -name "*~" -exec rm {} \;

