package scalascenegraph.ui.browser

import java.awt.event._
import javax.swing._

import scalascenegraph.core._

class BrowserControlFrame(val browser: Browser) extends JFrame {
    
    def init {
        // Ideally, Matisse plugin in Netbeans would do an excellent job at generating this code,
        // but I can't get mixed Java/Scala projects to work... Anyway, that's the occasion to learn
        // about the mysterious GroupLayout!
        
        val projectionPanel = new JPanel
        val projectionPanelLayout = new GroupLayout(projectionPanel)
        val projectionButtonGroup = new ButtonGroup
        val perspectiveButton = new JRadioButton("Perspective")
        val parallelButton = new JRadioButton("Parallel")
        perspectiveButton.setSelected(true)
        projectionButtonGroup.add(perspectiveButton)
        projectionButtonGroup.add(parallelButton)
        projectionPanelLayout.setAutoCreateGaps(true)
        projectionPanelLayout.setAutoCreateContainerGaps(true)
        projectionPanelLayout.setHorizontalGroup(
            projectionPanelLayout.createSequentialGroup.
                addComponent(perspectiveButton).
                addComponent(parallelButton))
        projectionPanelLayout.setVerticalGroup(
            projectionPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(perspectiveButton).
                addComponent(parallelButton))
        projectionPanel.setLayout(projectionPanelLayout)
        projectionPanel.setBorder(BorderFactory.createTitledBorder("Projection"))
        
        val clippingVolumePanel = new JPanel
        val clippingVolumePanelLayout = new GroupLayout(clippingVolumePanel)
        val leftLabel = new JLabel("left:")
        val rightLabel = new JLabel("right:")
        val leftSpinner = new JSpinner(new SpinnerNumberModel(-5.0, -100.0, 100.0, 0.1))
        val rightSpinner = new JSpinner(new SpinnerNumberModel(5.0, -100.0, 100.0, 0.1))
        clippingVolumePanelLayout.setAutoCreateGaps(true)
        clippingVolumePanelLayout.setAutoCreateContainerGaps(true)
        clippingVolumePanelLayout.setHorizontalGroup(
            clippingVolumePanelLayout.createSequentialGroup.
                addGroup(clippingVolumePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).
                    addComponent(leftLabel).
                    addComponent(rightLabel)).
                addGroup(clippingVolumePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).
                    addComponent(leftSpinner).
                    addComponent(rightSpinner)))
        clippingVolumePanelLayout.setVerticalGroup(
            clippingVolumePanelLayout.createSequentialGroup.
                addGroup(clippingVolumePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                    addComponent(leftLabel).
                    addComponent(leftSpinner)).
                addGroup(clippingVolumePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                    addComponent(rightLabel).
                    addComponent(rightSpinner)))
        clippingVolumePanel.setLayout(clippingVolumePanelLayout)
        clippingVolumePanel.setBorder(BorderFactory.createTitledBorder("Clipping Volume"))
        
        val controlPanel = new JPanel
        val controlPanelLayout = new GroupLayout(controlPanel)
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup.
                addComponent(projectionPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE).
                addComponent(clippingVolumePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE))
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createSequentialGroup.
                addComponent(projectionPanel).
                addComponent(clippingVolumePanel))
        controlPanel.setLayout(controlPanelLayout)
        getContentPane.add(controlPanel)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        setResizable(false)
        pack
        
        perspectiveButton.addActionListener(new ActionListener {
            def actionPerformed(e: ActionEvent) {
                perspectiveButtonActionPerformed(e)
            }
        })
        
        parallelButton.addActionListener(new ActionListener {
            def actionPerformed(e: ActionEvent) {
                parallelButtonActionPerformed(e)
            }
        })
    }
    
    private def perspectiveButtonActionPerformed(e: ActionEvent) {
        val oldCamera = browser.getCamera
        val newCamera = new PerspectiveCamera(oldCamera.clippingVolume)
        browser.setCamera(newCamera)
    }
    
    private def parallelButtonActionPerformed(e: ActionEvent) {
        val oldCamera = browser.getCamera
        val newCamera = new ParallelCamera(oldCamera.clippingVolume)
        browser.setCamera(newCamera)
    }
    
    init
}

object BrowserControlFrame {
    def main(args: Array[String]) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        val f = new BrowserControlFrame(null)
        f.setVisible(true)
    }
}
