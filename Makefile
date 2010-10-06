ifeq "$(OS)" "Windows_NT"
CLASSPATH="lib\gluegen-rt.jar;lib\jogl.all.jar;lib\nativewindow.all.jar;lib\newt.all.jar"
TARGETDIR="target\classes"
else
CLASSPATH="lib/gluegen-rt.jar:lib/jogl.all.jar:lib/nativewindow.all.jar:lib/newt.all.jar"
TARGETDIR="target/classes"
endif

LD_LIBRARY_PATH="lib"
FILES=`find src -name *.scala`

all: resources
	scalac -classpath $(CLASSPATH) -d ${TARGETDIR}  $(FILES)

clean:
	rm -rf target; find . -name "*~" -exec rm {} \;

runExample01:
	LD_LIBRARY_PATH=$(LD_LIBRARY_PATH);\
	scala -classpath ${TARGETDIR}:${CLASSPATH}:etc scalascenegraph.examples.RunExample scalascenegraph.examples.Example01

resources:
	mkdir -p target/classes;\
	(cd src ; tar cf - `find -not -name "*.scala" -type f -print`) | (cd $(TARGETDIR) ; tar xf -)
