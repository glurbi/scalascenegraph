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
	fsc -deprecation -unchecked -make:transitivenocp -dependencyfile $(TARGETDIR)/.scala_dependencies -classpath $(CLASSPATH) -d $(TARGETDIR)  $(FILES)

clean:
	rm -rf target; find . -name "*~" -exec rm {} \;

runExample01: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example01
runExample02: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example02
runExample03: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example03
runExample04: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example04
runExample05: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example05
runExample06: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example06
runExample07: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example07
runExample08: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example08
runExample09: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example09
runExample10: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example10
runExample11: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example11
runExample12: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example12
runExample13: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.RunExample scalascenegraph.examples.Example13

resources:
	mkdir -p target/classes;\
	(cd src ; tar cf - `find -not -name "*.scala" -type f -print`) | (cd $(TARGETDIR) ; tar xf -)
