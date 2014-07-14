package skyprocstarter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lev.gui.LCheckBox;
import lev.gui.LComboBox;
import skyproc.SPGlobal;

import skyproc.gui.SPMainMenuPanel;
import skyproc.gui.SPSettingPanel;
import skyproc.gui.SUMGUI;

public class OtherSettingsPanel extends SPSettingPanel {

    public enum Body {

        Naked,
        NakedBBP,
        NakedTBBP,
        Undies,
        LayerBikini
    }
    LCheckBox importOnStartup;
    LCheckBox changeBodies;
    LCheckBox killerKeoClothes;
    LCheckBox makClothes;
    LCheckBox diversifiedAnimations;
    LCheckBox checkForNewAnimations;
    LCheckBox correctNPCSpell;
    LCheckBox hairDeployment;
    LCheckBox poolOfClothes;
    LCheckBox changeWeight;
    LCheckBox textureDeployment;
    LCheckBox changeMale;
    LCheckBox noModSupport;
    LCheckBox fixedRandomness;
    LComboBox language;
    LComboBox nakedBodyOptions;

    public OtherSettingsPanel(SPMainMenuPanel parent_) {
        super(parent_, "Other Settings", SkyProcStarter.headerColor);
    }

    @Override
    protected void initialize() {
        super.initialize();

        nakedBodyOptions = new LComboBox("Naked body options", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        nakedBodyOptions.setSize(260, 60);
        /*  for (Enum e : Body.values()) {
         nakedBodyOptions.addItem(e);
         }
         */
        nakedBodyOptions.addItem("Naked");
        nakedBodyOptions.addItem("Naked BBP");
        nakedBodyOptions.addItem("Naked TBBP");
        nakedBodyOptions.addItem("Undies");
        nakedBodyOptions.addItem("Layer Bikini");

        nakedBodyOptions.tie(YourSaveFile.Settings.BODY, SkyProcStarter.save, SUMGUI.helpPanel, true);
        setPlacement(nakedBodyOptions);
        AddSetting(nakedBodyOptions);

        noModSupport = new LCheckBox("Disable mod support", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        noModSupport.tie(YourSaveFile.Settings.DISABLE_MOD_SUPPORT_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        noModSupport.setOffset(2);
        noModSupport.addShadow();
        setPlacement(noModSupport);
        AddSetting(noModSupport);

        fixedRandomness = new LCheckBox("Set randomness to fixed", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        fixedRandomness.tie(YourSaveFile.Settings.FIXED_RANDOMNESS_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        fixedRandomness.setOffset(2);
        fixedRandomness.addShadow();
        setPlacement(fixedRandomness);
        AddSetting(fixedRandomness);

        changeBodies = new LCheckBox("Change Body Shapes", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        changeBodies.tie(YourSaveFile.Settings.CHANGE_BODIES_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        changeBodies.setOffset(2);
        changeBodies.addShadow();
        setPlacement(changeBodies);
        AddSetting(changeBodies);

        killerKeoClothes = new LCheckBox("Add KillerKeo armors and clothes", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        killerKeoClothes.tie(YourSaveFile.Settings.KILLERKEO_CLOTHES_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        killerKeoClothes.setOffset(2);
        killerKeoClothes.addShadow();
        setPlacement(killerKeoClothes);
        AddSetting(killerKeoClothes);

        makClothes = new LCheckBox("Add MAK armors", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        makClothes.tie(YourSaveFile.Settings.MAK_CLOTHES_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        makClothes.setOffset(2);
        makClothes.addShadow();
        setPlacement(makClothes);
        AddSetting(makClothes);

        diversifiedAnimations = new LCheckBox("Diversified animations", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        diversifiedAnimations.tie(YourSaveFile.Settings.DIVERSIFIED_ANIMATIONS_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        diversifiedAnimations.setOffset(2);
        diversifiedAnimations.addShadow();
        diversifiedAnimations.setVisible(false);
        setPlacement(diversifiedAnimations);
        AddSetting(diversifiedAnimations);

        checkForNewAnimations = new LCheckBox("Check for new animations", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        checkForNewAnimations.tie(YourSaveFile.Settings.CHECK_FOR_NEW_ANIMATIONS_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        checkForNewAnimations.setOffset(2);
        checkForNewAnimations.addShadow();
        checkForNewAnimations.setVisible(false);
        setPlacement(checkForNewAnimations);
        AddSetting(checkForNewAnimations);
        
        poolOfClothes = new LCheckBox("Use clothes pool for NPCs", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        poolOfClothes.tie(YourSaveFile.Settings.POOL_OF_CLOTHES_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        poolOfClothes.setOffset(2);
        poolOfClothes.addShadow();
        setPlacement(poolOfClothes);
        AddSetting(poolOfClothes);

        correctNPCSpell = new LCheckBox("Correct NPCs neck seams", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        correctNPCSpell.tie(YourSaveFile.Settings.CORRECT_NPC_SPELL_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        correctNPCSpell.setOffset(2);
        correctNPCSpell.addShadow();
        setPlacement(correctNPCSpell);
        AddSetting(correctNPCSpell);

        hairDeployment = new LCheckBox("Use hair variation", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        hairDeployment.tie(YourSaveFile.Settings.HAIR_DEPLOYMENT_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        hairDeployment.setOffset(2);
        hairDeployment.addShadow();
        setPlacement(hairDeployment);
        AddSetting(hairDeployment);

        changeWeight = new LCheckBox("Change weight by job", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        changeWeight.tie(YourSaveFile.Settings.CHANGE_WEIGHT_BY_JOB_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        changeWeight.setOffset(2);
        changeWeight.addShadow();
        setPlacement(changeWeight);
        AddSetting(changeWeight);

        textureDeployment = new LCheckBox("Use texture variation", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        textureDeployment.tie(YourSaveFile.Settings.TEXTURE_DEPLOYMENT_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        textureDeployment.setOffset(2);
        textureDeployment.addShadow();
        setPlacement(textureDeployment);
        AddSetting(textureDeployment);

        changeMale = new LCheckBox("Change males also", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        changeMale.tie(YourSaveFile.Settings.CHANGE_MALE_ON, SkyProcStarter.save, SUMGUI.helpPanel, true);
        changeMale.setOffset(2);
        changeMale.addShadow();
        setPlacement(changeMale);
        AddSetting(changeMale);

        importOnStartup = new LCheckBox("Import mods on startup", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        importOnStartup.tie(YourSaveFile.Settings.IMPORT_AT_START, SkyProcStarter.save, SUMGUI.helpPanel, true);
        importOnStartup.setOffset(2);
        importOnStartup.addShadow();
        setPlacement(importOnStartup);
        AddSetting(importOnStartup);

        language = new LComboBox("Language", SkyProcStarter.settingsFont, SkyProcStarter.settingsColor);
        language.setSize(260, 60);

        for (Enum e : SPGlobal.Language.values()) {
            language.addItem(e);
        }
        language.tie(YourSaveFile.Settings.LANGUAGE, SkyProcStarter.save, SUMGUI.helpPanel, true);
        setPlacement(language);
        AddSetting(language);
        language.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SPGlobal.language = SPGlobal.Language.values()[SkyProcStarter.save.getInt(YourSaveFile.Settings.LANGUAGE)];
                SPGlobal.logMain("Other Settings Panel", "Language: " + SPGlobal.language);
            }
        });
        alignRight();
    }
}
