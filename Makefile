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

example01: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example01
example02: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example02
example03: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example03
example04: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example04
example05: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example05
example06: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example06
example07: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example07
example08: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example08
example09: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example09
example10: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example10
example11: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example11
example12: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example12
example13: all
	scala -Djava.library.path=$(LIBRARY_PATH) -classpath $(CLASSPATH) scalascenegraph.examples.Example13

resources:
	mkdir -p target/classes;\
	(cd src ; tar cf - `find -not -name "*.scala" -type f -print`) | (cd $(TARGETDIR) ; tar xf -)
