package scalascenegraph.ui.browser

import javax.swing._

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
        clippingVolumePanelLayout.setAutoCreateGaps(true)
        clippingVolumePanelLayout.setAutoCreateContainerGaps(true)
        clippingVolumePanelLayout.setHorizontalGroup(
            clippingVolumePanelLayout.createSequentialGroup.
                addGroup(clippingVolumePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).
                    addComponent(leftLabel).
                    addComponent(rightLabel)))
        clippingVolumePanelLayout.setVerticalGroup(
            clippingVolumePanelLayout.createSequentialGroup.
                addGroup(clippingVolumePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                    addComponent(leftLabel)).
                addGroup(clippingVolumePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                    addComponent(rightLabel)))
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