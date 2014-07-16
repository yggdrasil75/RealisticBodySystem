package skyprocstarter;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import lev.gui.LSaveFile;
import skyproc.GRUP_TYPE;
import skyproc.Mod;
import skyproc.ModListing;
import skyproc.SPGlobal;
import skyproc.gui.SPMainMenuPanel;
import skyproc.gui.SUM;
import skyproc.gui.SUMGUI;
import skyprocstarter.YourSaveFile.Settings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lev.Ln;
import skyproc.ARMA;
import skyproc.MajorRecord;
import skyproc.NiftyFunc;

import skyproc.gui.SPProgressBarPlug;

public class SkyProcStarter implements SUM {

    /* The important functions to change are:
     *  - getStandardMenu(), where you set up the GUI
     *  - Change the import requests to be the records you're interested in.
     *  - runChangesToPatch(), where you put all the processing code and add
     *    records to the output patch.
     */

    /* The types of records you want your patcher to import
     * At the moment, it imports NPC_ and LVLN records.  Change this to
     * customize the import to what you need.
     */
    GRUP_TYPE[] importRequests = new GRUP_TYPE[]{
        GRUP_TYPE.NPC_,
        GRUP_TYPE.RACE,
        GRUP_TYPE.ARMA,
        GRUP_TYPE.ARMO,
        GRUP_TYPE.OTFT,
        GRUP_TYPE.TXST,
        GRUP_TYPE.VTYP,
        GRUP_TYPE.HDPT,
        GRUP_TYPE.LVLI
    };
    
    public static String myPatchName = "RBS.esp";
    public static Mod merger;
    public static Mod patch;
    public ArrayList<ModListing> requiredMods = new ArrayList<>();
    public static String authorName = "VadersApp";
    public static String version = "1.0";
    public static Color headerColor = new Color(66, 181, 184);  // Teal
    public static Color settingsColor = new Color(72, 179, 58);  // Green
    public static Font settingsFont = new Font("Serif", Font.BOLD, 15);
    public static LSaveFile save = new YourSaveFile();
    public static String path;
    public static String canonicalPath;
    public static String pathSources;

    public static String pathNewAnimationsSource;
    public static String pathHKX;
    public static String pathSkyrim;
    public static String pathToHKXcmd;

    public static String pathToCharactersFemale;
    public static String pathToCharacter;
    public static List<String> amountBodyTypes = new ArrayList<>();
    public static ArrayList<MajorRecord> usedDefaultOutfits = new ArrayList<>();
    public static ArrayList<MajorRecord> usedLeveledItems = new ArrayList<>();
    public static ArrayList<MajorRecord> usedArmors = new ArrayList<>();
    public static ArrayList<MajorRecord> usedArmatures = new ArrayList<>();
    public static int megsOfMem = 1024;
    public static ArrayList<String> meshesGroup = new ArrayList<>(3);

    public static void main(String[] args) {
        try {

            ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));
            if (handleArgs(arguments)) {
                SPGlobal.closeDebug();
                return;
            }
        } catch (IOException | InterruptedException e) {
        }

        save.init();
        try {
            
            SPGlobal.createGlobalLog();
            SUMGUI.open(new SkyProcStarter(), args);
        } catch (Exception e) {
            // If a major error happens, print it everywhere and display a message box.
            System.err.println(e.toString());
            SPGlobal.logException(e);
            JOptionPane.showMessageDialog(null, "There was an exception thrown during program execution: '" + e + "'  Check the debug logs or contact the author.");
            SPGlobal.closeDebug();
        }
    }

    static boolean handleArgs(ArrayList<String> arguments) throws IOException, InterruptedException {
        Ln.toUpper(arguments);
        if (arguments.contains("-NETBEANS:1")) {
            SkyProcStarter.path = new File("").getCanonicalPath() + File.separator;
        } else {
            SkyProcStarter.path = SPGlobal.pathToData;
        }
        String nonew = "-NONEW";

        if (!arguments.contains(nonew)) {
// Less than .85 * max memory desired
            if (Runtime.getRuntime().maxMemory() < 1200 * 0.85 * 1200 * 1024) {
                NiftyFunc.allocateMoreMemory("100m", 1200 + "m", "RBS.jar", nonew);
            }
        }

        return false;
    }

    @Override
    public String getName() {
        return myPatchName;
    }

    // This function labels any record types that you "multiply".
    // For example, if you took all the armors in a mod list and made 3 copies,
    // you would put ARMO here.
    // This is to help monitor/prevent issues where multiple SkyProc patchers
    // multiply the same record type to yeild a huge number of records.
    @Override
    public GRUP_TYPE[] dangerousRecordReport() {
        // None
        return new GRUP_TYPE[0];
    }

    @Override
    public GRUP_TYPE[] importRequests() {
        return importRequests;
    }

    @Override
    public boolean importAtStart() {
        return false;
    }

    @Override
    public boolean hasStandardMenu() {
        return true;
    }

    // This is where you add panels to the main menu.
    // First create custom panel classes (as shown by YourFirstSettingsPanel),
    // Then add them here.
    @Override
    public SPMainMenuPanel getStandardMenu() {
        SPMainMenuPanel settingsMenu = new SPMainMenuPanel(getHeaderColor());
        settingsMenu.setWelcomePanel(new WelcomePanel(settingsMenu));
        settingsMenu.addMenu(new OtherSettingsPanel(settingsMenu), false, save, Settings.OTHER_SETTINGS);
        return settingsMenu;
    }

    // Usually false unless you want to make your own GUI
    @Override
    public boolean hasCustomMenu() {
        return false;
    }

    @Override
    public JFrame openCustomMenu() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasLogo() {
        return false;
    }

    @Override
    public URL getLogo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasSave() {
        return true;
    }

    @Override
    public LSaveFile getSave() {
        return save;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public ModListing getListing() {
        return new ModListing(getName(), false);
    }

    @Override
    public Mod getExportPatch() {
        Mod out = new Mod(getListing());
        out.setAuthor(authorName);
        return out;
    }

    @Override
    public Color getHeaderColor() {
        return headerColor;
    }

    // Add any custom checks to determine if a patch is needed.
    // On Automatic Variants, this function would check if any new packages were
    // added or removed.
    @Override
    public boolean needsPatching() {
        return false;
    }

    // This function runs when the program opens to "set things up"
    // It runs right after the save file is loaded, and before the GUI is displayed
    @Override
    public void onStart() throws Exception {
        if (save.getBool(Settings.DISABLE_MOD_SUPPORT_ON)) {
            SPGlobal.addModToWhiteList(new ModListing("Skyrim.esm"));
        }
        SPGlobal.addModToSkip(new ModListing("EMCompViljaSkyrim.esp"));
    }

    // This function runs right as the program is about to close.
    @Override
    public void onExit(boolean patchWasGenerated) throws Exception {
    }

    @Override
    public String description() {
        String text = "";
        return text;
    }

    @Override
    public ArrayList<ModListing> requiredMods() {
        return requiredMods;
    }

    @Override
    public void runChangesToPatch() throws Exception {
        
        for (int i = 1; i <= RBS_Main.amountBodyTypes; i++) {
            SkyProcStarter.amountBodyTypes.add(RBS_Randomize.createID(i));
        }
        SkyProcStarter.patch = SPGlobal.getGlobalPatch();
        SkyProcStarter.merger = new Mod(getName() + "Merger", false);
        SkyProcStarter.merger.addAsOverrides(SPGlobal.getDB());
        SkyProcStarter.pathSkyrim = new File("..\\..\\..\\").getCanonicalPath() + File.separator;
        SkyProcStarter.canonicalPath = new File(path).getCanonicalPath() + File.separator;
        SkyProcStarter.pathSources = SkyProcStarter.canonicalPath + "RBSGenerator" + File.separator + "sources" + File.separator;
        SkyProcStarter.pathToCharacter = SkyProcStarter.canonicalPath + "meshes" + File.separator + "Actors" + File.separator + "Character" + File.separator;
        SkyProcStarter.pathNewAnimationsSource = SkyProcStarter.pathToCharacter + "RBS" + File.separator + "animations" + File.separator;
        SkyProcStarter.pathToHKXcmd = SkyProcStarter.canonicalPath + "RBSGenerator" + File.separator + "tools" + File.separator + "hkxcmd.exe";
        SkyProcStarter.pathToHKXcmd = SkyProcStarter.pathSkyrim + "hkxcmd.exe";
        SkyProcStarter.pathToCharactersFemale = SkyProcStarter.pathToCharacter + "characters female" + File.separator;

        RBS_Race rbs_race = new RBS_Race();
        RBS_ARMA rbs_arma = new RBS_ARMA();
        RBS_ARMO rbs_armo = new RBS_ARMO();
        RBS_NPC rbs_npc = new RBS_NPC();
        RBS_Texture rbs_texture = new RBS_Texture();
        RBS_Outfit rbs_outfit = new RBS_Outfit();
        RBS_LeveledList rbs_leveledList = new RBS_LeveledList();
        RBS_Quest rbs_quest = new RBS_Quest();
        RBS_File rbs_file = new RBS_File();
        RBS_Main rbs_main = new RBS_Main();
        RBS_Hair rbs_hair = new RBS_Hair();

        RBS_Statics rbs_statics = new RBS_Statics();
        RBS_CleanUnusedData rbs_cleanUnusedData = new RBS_CleanUnusedData();
        long startall = System.currentTimeMillis();
        long start;

        SkyProcStarter.merger.getSpells().clear();
        SkyProcStarter.merger.getAlchemy().clear();
        SkyProcStarter.merger.getAlchemy().clear();
        SPProgressBarPlug.reset();

        start = System.currentTimeMillis();
        //RBS_File.GetFolderContents();

        if (save.getBool(Settings.DIVERSIFIED_ANIMATIONS_ON)) {
            RBS_Animation rbs_animation = new RBS_Animation();
            rbs_race.createForNewAnimations();
        }
        if (save.getBool(Settings.CHANGE_BODIES_ON)) {
            SkyProcStarter.meshesGroup.add("standard");

            if (save.getBool(Settings.KILLERKEO_CLOTHES_ON)) {
                SkyProcStarter.meshesGroup.add("killerkeo");
            }

            if (save.getBool(Settings.MAK_CLOTHES_ON)) {
                SkyProcStarter.meshesGroup.add("ct77");
            }
            
            for (String meshesfolder : meshesGroup) {
                rbs_arma.CreateNewAA(meshesfolder, save.getStr(Settings.BODY));
            }
            rbs_arma.changeNakedTorso();
            //rbs_arma.addModRacesToAA(); //not working as it should
            rbs_armo.changeSkinNaked();
            
            
            if (save.getBool(Settings.TEXTURE_DEPLOYMENT_ON)) {
                rbs_texture.CreateTextureSetsSkinBodyFemale_1RBS();
            }

            rbs_texture.textures();

            for (ARMA AA : patch.getArmatures()) {
                RBS_ARMA.patchAAMapKeyEDID.put(AA.getEDID(), AA.getForm());
                RBS_ARMA.patchAAMapKeyForm.put(AA.getForm(), AA.getEDID());
            }

            for (String meshesfolder : meshesGroup) {
                rbs_armo.CreateNewArmor(meshesfolder);
            }

            rbs_leveledList.checkItemsOfLeveledListsForPatching();

            if (save.getBool(Settings.POOL_OF_CLOTHES_ON)) {
                rbs_leveledList.LItemBootsAll("standard");
                rbs_leveledList.LItemHatsAll("standard");
                rbs_leveledList.LItemClothesAll("standard");
            }

            for (String meshesfolder : meshesGroup) {
                rbs_outfit.CreateNewOutfits(meshesfolder);
            }
            //rbs_npc.femalize();
            rbs_npc.changeFemale();
        }

        if (SkyProcStarter.save.getBool(YourSaveFile.Settings.HAIR_DEPLOYMENT_ON)) {
            rbs_hair.changeHairAllFemales();
        }

        if (save.getBool(Settings.DIVERSIFIED_ANIMATIONS_ON)) {
            rbs_race.AttachNPCsToRaces();
        }

        if (save.getBool(Settings.TEXTURE_DEPLOYMENT_ON)) {
            rbs_texture.CreateAANakedTorsoAlternativeTexturesFemales();
            rbs_texture.CreateSkinNakedAlternativeTexturesFemale();
            rbs_npc.AttachSkinNakedDiversifiedTexturestoNPCsFemale("standard");
        }

        if (save.getBool(Settings.CHANGE_MALE_ON)) {
            if (save.getBool(Settings.TEXTURE_DEPLOYMENT_ON)) {
                rbs_texture.CreateTextureSetsSkinBodyMale_1RBS();
            }
            rbs_race.CreateNewMaleRaces();
            rbs_race.CreateNewMaleRaces2();
            rbs_npc.changeMale("standard");
            if (save.getBool(Settings.TEXTURE_DEPLOYMENT_ON)) {
                rbs_texture.CreateAANakedTorsoAlternativeTexturesMales();
                rbs_texture.CreateSkinNakedAlternativeTexturesMale();
                rbs_npc.AttachSkinNakedtoNPCsMale("standard");
            }
        }

        if (save.getBool(Settings.POOL_OF_CLOTHES_ON)) {
            rbs_npc.ClothesAllToNPCs("standard");
        }

        //AddToRacesHandsAndFeet("standard");
        if (save.getBool(Settings.CORRECT_NPC_SPELL_ON)) {
            //   AddNeckFixSpellToRaces();
            rbs_quest.addQuest();
        }
        //  rbs_leveledList.RemoveVanillaEntriesInLeveledLists();

        rbs_leveledList.exchangeLeveledLists();
        //  rbs_leveledList.RemoveVanillaEntriesInLeveledLists();
        //   rbs_cleanUnusedData.cleanUnusedData();
    }
}
