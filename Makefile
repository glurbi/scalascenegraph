ifeq "$(OS)" "Windows_NT"
CLASSPATH="lib\gluegen-rt.jar;lib\jogl.all.jar;lib\nativewindow.all.jar;lib\newt.all.jar;etc;target\classes"
TARGETDIR="target\classes"
else
CLASSPATH="lib/gluegen-rt.jar:lib/jogl.all.jar:lib/nativewindow.all.jar:lib/newt.all.jar:etc:target/classes"
TARGETDIR="target/classes"
endif

LIBRARY_PATH="lib"
FILES=`find src -name *.scala`

all: resources
	fsc -make:transitivenocp -dependencyfile $(TARGETDIR)/.scala_dependencies -classpath $(CLASSPATH) -d $(TARGETDIR)  $(FILES)

clean:
	rm -rf target; find . -name "*~" -exec rm {} \;

runExample01:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example01
runExample02:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example02
runExample03:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example03
runExample04:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example04
runExample05:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example05
runExample06:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example06
runExample07:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example07
runExample08:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example08
runExample09:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example09
runExample10:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example10
runExample11:
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example11

resources:
	mkdir -p target/classes;\
	(cd src ; tar cf - `find -not -name "*.scala" -type f -print`) | (cd $(TARGETDIR) ; tar xf -)
