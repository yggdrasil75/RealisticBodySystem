/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import skyproc.SkyProcSave;

/**
 *
 * @author Justin Swanson
 */
public class YourSaveFile extends SkyProcSave {

    @Override
    protected void initSettings() {
        //  The Setting,	    The default value,	    Whether or not it changing means a new patch should be made
        Add(Settings.IMPORT_AT_START, true, true);
        Add(Settings.LANGUAGE, 0, false); // 0 is the default value because English is index 0 on the language enum.
        Add(Settings.CHANGE_BODIES_ON, false, true);
        Add(Settings.KILLERKEO_CLOTHES_ON, true, true);
        Add(Settings.MAK_CLOTHES_ON, true, true);
        Add(Settings.DIVERSIFIED_ANIMATIONS_ON, false, true);
        Add(Settings.POOL_OF_CLOTHES_ON, false, true);
        Add(Settings.CORRECT_NPC_SPELL_ON, false, true);
        Add(Settings.HAIR_DEPLOYMENT_ON, true, true);
        Add(Settings.CHANGE_WEIGHT_BY_JOB_ON, false, true);
        Add(Settings.TEXTURE_DEPLOYMENT_ON, false, true);
        Add(Settings.CHANGE_MALE_ON, false, true);
        Add(Settings.DISABLE_MOD_SUPPORT_ON, false, true);
        Add(Settings.FIXED_RANDOMNESS_ON, true, true);
        Add(Settings.BODY,0,false);
    }

    @Override
    protected void initHelp() {

        helpInfo.put(Settings.IMPORT_AT_START,
                "If enabled, the program will begin importing your mods when the program starts.\n\n"
                + "If turned off, the program will wait until it is necessary before importing.\n\n"
                + "NOTE: This setting will not take effect until the next time the program is run.\n\n"
                + "Benefits:\n"
                + "- Faster patching when you close the program.\n"
                + "- More information displayed in GUI, as it will have access to the records from your mods."
                + "\n\n"
                + "Downsides:\n"
                + "- Having this on might make the GUI respond sluggishly while it processes in the "
                + "background.");

        helpInfo.put(Settings.LANGUAGE,
                "You can set your language here.  This will make SkyProc import strings files of that language.\n\n"
                + "NOTE:  You must restart the program for this to take effect.");


        helpInfo.put(Settings.BODY,
                "Choose which naked Body should be used, BBP needs BBP skeleton and animations installed, the same with TBBP, Layer Bikini has missing Textures, will try to fix it. ");
        helpInfo.put(Settings.FIXED_RANDOMNESS_ON,
                "If checked RBS processing on a random are fixed. If unchecked everytime you run the patcher everything RBS touches is changed.");
        helpInfo.put(Settings.OTHER_SETTINGS,
                "These are other settings related to this patcher program.");
        helpInfo.put(Settings.KILLERKEO_CLOTHES_ON,
                "add KillerKeo Armor to variation");
        helpInfo.put(Settings.MAK_CLOTHES_ON,
                "add Mak Armor to variation");
        helpInfo.put(Settings.POOL_OF_CLOTHES_ON,
                "All Clothes are put into a pool. NPCs will take one out of it by random and wear it");
        helpInfo.put(Settings.DIVERSIFIED_ANIMATIONS_ON,
                "All Females that are touched by RBS using different sit, idle and walk animations now");
        helpInfo.put(Settings.CORRECT_NPC_SPELL_ON,
                "If checked a spell is performed ingame trying to correct neck seams.");
        helpInfo.put(Settings.HAIR_DEPLOYMENT_ON,
                "If you have hair mods installed, RBS will deploy hair form the mods to female NPCs.");
        helpInfo.put(Settings.CHANGE_WEIGHT_BY_JOB_ON,
                "Weight is processed by Skills. Beggars are thinner, Barkeepers fatter and so on...");
        helpInfo.put(Settings.TEXTURE_DEPLOYMENT_ON,
                "Females get more muscles if strength skills are higher. Together with freckles, veigns and other texture variants, there are more than 500 different texture combinations. Males get different bodyhair textures, This consumes much memory!");
        helpInfo.put(Settings.CHANGE_MALE_ON,
                "Males don´t have new meshes, but their skeletons have added scale bones that scale parts of their body");
        helpInfo.put(Settings.DISABLE_MOD_SUPPORT_ON,
                "Only Skyrim.esm is processed. Should make using RBS for those which having problems with mods installed");
    }

    // Note that some settings just have help info, and no actual values in
    // initSettings().
    public enum Settings {
        IMPORT_AT_START,
        LANGUAGE,
        CHANGE_BODIES_ON,
        KILLERKEO_CLOTHES_ON,
        MAK_CLOTHES_ON,
        POOL_OF_CLOTHES_ON,
        DIVERSIFIED_ANIMATIONS_ON,
        CORRECT_NPC_SPELL_ON,
        HAIR_DEPLOYMENT_ON,
        CHANGE_WEIGHT_BY_JOB_ON,
        TEXTURE_DEPLOYMENT_ON,
        CHANGE_MALE_ON,
        BODY,
        DISABLE_MOD_SUPPORT_ON,
        FIXED_RANDOMNESS_ON,
        OTHER_SETTINGS;
    }

}
