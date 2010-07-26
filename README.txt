Jar dependencies:
    newt.all.jar
    gluegen-rt.jar
    jogl.all.jar
    nativewindow.all.jar

Native dependencies must be in the java.library.path

For jogl-2.0-linux-i586:
    libjogl_es2.so
    libnativewindow_x11.so
    libjogl_gl2es12.so
    libnewt.so
    libgluegen-rt.so
    libjogl_gl2.so
    libjogl_cg.so
    libnativewindow_awt.so
    libjogl_es1.so
    libnativewindow_jvm.so
    
For jogl-2.0-windows-i586:
    gluegen-rt.dll
    jogl_cg.dll
    jogl_es1.dll
    jogl_es2.dll
    jogl_gl2.dll
    jogl_gl2es12.dll
    nativewindow_awt.dll
    nativewindow_jvm.dll
    newt.dll    
    
NB: flickering during live resizing of the GLCanvas. This is caused by the AWT's repainting the background of the Canvas and can not be overridden on a per-Canvas basis, for example when subclassing Canvas into GLCanvas. The repainting of the background of canvases can be disabled by specifying the system property -Dsun.awt.noerasebackground=true.
Seen on Windows and Ubuntu Linux.
