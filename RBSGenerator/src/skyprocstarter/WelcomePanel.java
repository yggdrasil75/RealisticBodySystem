/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import lev.gui.LTextPane;
import skyproc.gui.SPMainMenuPanel;
import skyproc.gui.SPSettingPanel;

/**
 *
 * @author Justin Swanson
 */
public class WelcomePanel extends SPSettingPanel {

    LTextPane introText;

    public WelcomePanel(SPMainMenuPanel parent_) {
	super(parent_, SkyProcStarter.myPatchName, SkyProcStarter.headerColor);
    }

    @Override
    protected void initialize() {
	super.initialize();

	introText = new LTextPane(settingsPanel.getWidth() - 40, 400, SkyProcStarter.settingsColor);
	introText.setText("This SkyProc patcher is doing all the magic. "
		+ "It creates the RBS.esp in your data Folder"
                	+ "The only thing you have to do is clicking on the Patch button."
                                + "Now wait until the patcher is done and the esp file is generated."
                                + "Thanx to Leviathan1753 for creating such a fantastic tool/library!"
                                + "Realistic Body System v.1.2"
                );
	introText.setEditable(false);
	introText.setFont(SkyProcStarter.settingsFont);
	introText.setCentered();
	setPlacement(introText);
	Add(introText);

	alignRight();
    }
}
