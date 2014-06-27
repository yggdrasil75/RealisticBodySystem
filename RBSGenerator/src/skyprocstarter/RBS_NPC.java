/*
 * To change RBS_NPC template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.HDPT;

import skyproc.MajorRecord;
import skyproc.NPC_;
import skyproc.OTFT;
import skyproc.VTYP;
import skyproc.RACE;
import skyproc.SPDatabase;
import skyproc.genenums.Skill;
import skyproc.gui.SPProgressBarPlug;

public class RBS_NPC {

    public static List<NPC_> ListNPC = new ArrayList<>();
    public static List<String> ListNPCName = new ArrayList<>();
    public static List<NPC_> ListNPCFemale = new ArrayList<>();
    public static List<NPC_> ListNPCFemaleUnique = new ArrayList<>();
    public static List<NPC_> ListNPCFemaleNotUnique = new ArrayList<>();
    public static List<NPC_> ListNPCMale = new ArrayList<>();
    public static List<NPC_> ListNPCMaleUnique = new ArrayList<>();
    public static List<NPC_> ListNPCMaleNotUnique = new ArrayList<>();
    public static List<NPC_> changedNPCs = new ArrayList();
    public static Map<FormID, String> voiceTypeMaleMap = new ConcurrentHashMap<>();
    public static Map<FormID, String> voiceTypeFemaleMap = new ConcurrentHashMap<>();
    public static Map<FormID, String> headPartMaleMap = new ConcurrentHashMap<>();
    public static Map<FormID, String> headPartFemaleMap = new ConcurrentHashMap<>();
    public static List<FormID> ListNPCFemalePatched = new ArrayList<>();
    public static int mn2 = 1;
    public static int mc2 = 1;
    public static int ms2 = 1;

    RBS_NPC() {
        FormID playerID = new FormID("000007Skyrim.esm");
        SkyProcStarter.merger.getNPCs().removeRecord(playerID);
        boolean hasFemaleHeadPart;
        for (VTYP VoiceTypes : SkyProcStarter.merger.getVoiceTypes()) {
            if (VoiceTypes.getEDID().toLowerCase().contains("female") && !VoiceTypes.getEDID().toLowerCase().contains("child")) {
                voiceTypeFemaleMap.put(VoiceTypes.getForm(), VoiceTypes.getEDID());
            } else if (VoiceTypes.getEDID().toLowerCase().contains("male") && !VoiceTypes.getEDID().toLowerCase().contains("child")) {
                voiceTypeMaleMap.put(VoiceTypes.getForm(), VoiceTypes.getEDID());
            }
        }

        for (HDPT headpart : SkyProcStarter.merger.getHeadParts()) {
            if (headpart.getEDID().toLowerCase().contains("female") && !headpart.getEDID().toLowerCase().contains("child")) {
                headPartFemaleMap.put(headpart.getForm(), headpart.getEDID());
            }
        }

        for (NPC_ n : SkyProcStarter.merger.getNPCs()) {
            if (voiceTypeMaleMap.containsKey(n.getVoiceType())) {
                ListNPCMale.add(n);
            }
            ListNPC.add(n);
            hasFemaleHeadPart = false;
            for (FormID headpartsOfNPC : n.getHeadParts()) {
                if (headPartFemaleMap.containsKey(headpartsOfNPC)) {
                    hasFemaleHeadPart = true;
                }
            }
            if (hasFemaleHeadPart || voiceTypeFemaleMap.containsKey(n.getVoiceType()) || n.get(NPC_.NPCFlag.Female)) {
                ListNPCFemale.add(n);
                ListNPC.add(n);
            }
        }

        SkyProcStarter.merger.getNPCs()
                .getRecords().clear();
        SkyProcStarter.merger.getNPCs()
                .getRecords().addAll(ListNPCFemale);
        SkyProcStarter.merger.getNPCs()
                .getRecords().trimToSize();
    }

    public void changeFemale() throws Exception {
        int counter = 1;
        int max = ListNPCFemale.size();
        for (NPC_ NPCIterator : ListNPCFemale) {
            String ID = RBS_Randomize.createRandomID(NPCIterator.getName());
            SPProgressBarPlug.setStatusNumbered(counter, max, "processing changes for females");
            if (!"No FormID".equals(NPCIterator.getDefaultOutfit().getFormStr())) {
                OTFT vanillaOutfit = (OTFT) SPDatabase.getMajor(NPCIterator.getDefaultOutfit());
                //MajorRecord vanillaOutfit = SPDatabase.getMajor(NPCIterator.getDefaultOutfit());
                setDefaultOutfit(NPCIterator, vanillaOutfit, ID);
                addVanillaOutfitToInventory(NPCIterator, vanillaOutfit);
                NPCIterator.setSkin(SkyProcStarter.patch.getArmors().get("SkinNakedRBS_F" + ID).getForm());
                // String NPCRace = SkyProcStarter.merger.getRaces().get(NPCIterator.getRace()).getEDID();
                NPCIterator = setSpeedMult(NPCIterator);
                NPCIterator = changeHeight(NPCIterator);
                if (SkyProcStarter.save.getBool(YourSaveFile.Settings.CHANGE_WEIGHT_BY_JOB_ON)) {
                    NPCIterator = changeWeight(NPCIterator, vanillaOutfit.getEDID());
                }
            }
            SkyProcStarter.patch.addRecord(NPCIterator);
            ListNPCFemalePatched.add(NPCIterator.getForm());
            counter++;
        }
    }

    public void changeFemaleOld(String folder) throws Exception {
        /*
         int counter = 0;
         boolean patchOutfits;
         int max = SkyProcStarter.merger.getNPCs().getRecords().size();
         RBS_NPC.m_folder = folder;
         for (NPC_ n : SkyProcStarter.merger.getNPCs().getRecords()) {
         RBS_NPC.m_npc = n;
         if ("No FormID".equals(n.getDefaultOutfit().getFormStr())) {
         patchOutfits = false;
         } else {
         FormID id = RBS_NPC.m_npc.getDefaultOutfit();
         RBS_NPC.m_sourceOutfit = SPDatabase.getMajor(id);
         RBS_NPC.m_outfitName = RBS_NPC.m_sourceOutfit.getEDID();
         patchOutfits = true;
         }
         RBS_NPC.m_raceName = (SkyProcStarter.merger.getRaces().get(n.getRace())).getEDID();
         SPProgressBarPlug.setStatusNumbered(counter, max, "processing changes for females");
         counter++;
         // if (!RBS_NPC.m_npc.getRace().getFormStr().equals("000019Skyrim.esm")) {
         RBS_NPC.m_RBSNumber = RBS_Randomize.createRandomID(RBS_NPC.m_npc.getName());
         //        changeWeightByJob();
         //   changeHeight();
         if (patchOutfits) {
         setDefaultOutfit();
         }
         RBS_NPC.m_npc.setSkin(SkyProcStarter.patch.getArmors().get("SkinNakedRBS_F" + RBS_NPC.m_RBSNumber).getForm());
         //changeSkin();
         //  }
         //}

         if (RBS_NPC.m_npc.getRace().getFormStr().equals("013740Skyrim.esm")) {
         RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 0, 5));
         }

         this.setSpeedMult(n);
         n = RBS_NPC.m_npc;
         SkyProcStarter.patch.addRecord(n);
         ListNPCFemalePatched.add(n.getForm());
         RBS_NPC.m_npc = null;
         RBS_NPC.m_raceName = null;
         }
         RBS_NPC.m_folder = null;
         */
    }

    public void addVanillaOutfitToInventory(NPC_ NPCIterator, MajorRecord vanillaOutfit) {
        if (vanillaOutfit != null) {
            for (OTFT mergerOutfit : SkyProcStarter.merger.getOutfits()) {
                if (vanillaOutfit.getForm().equals(mergerOutfit.getForm())) {
                    List<FormID> listInventory = mergerOutfit.getInventoryList();
                    listInventory.stream().forEach((listInventory1) -> {
                        NPCIterator.addItem(listInventory1, 1);
                    });
                }
            }
        }
    }

    public void femalize() {
        for (NPC_ n : ListNPCMaleNotUnique) {
            n.set(NPC_.NPCFlag.Female, true);
            SkyProcStarter.patch.addRecord(n);
        }
    }

    private NPC_ setSpeedMult(NPC_ NPCIterator) {
        int min;
        int max;
        if (SkyProcStarter.merger.getRaces().get(NPCIterator.getRace()).getEDID().toLowerCase().contains("eldar")) {
            min = 40;
            max = 70;
        } else {
            min = 70;
            max = 100;
        }
        if (NPCIterator.getWeight() > 80) {
            min = 60;
            max = 80;
        }
        if (NPCIterator.getWeight() > 90) {
            min = 50;
            max = 70;
        }
        NPCIterator.set(NPC_.NPCStat.SPEED_MULT, RBS_Randomize.toInt(NPCIterator.getEDID() + NPCIterator.getFormStr(), min, max));
        return (NPCIterator);
    }

    private NPC_ changeHeight(NPC_ NPCIterator) {
        float tmp = RBS_Randomize.toFloat(NPCIterator.getName() + NPCIterator.getFormStr(), 1, 1500) + 8500;
        float randomheight = tmp / 10000;
        NPCIterator.setHeight(randomheight);
        return (NPCIterator);
    }

    private NPC_ changeWeight(NPC_ NPCIterator, String outfitName) {
        int min = 25;
        int max = 95;

        if (NPCIterator.getRace().getFormStr().equals("013740Skyrim.esm")) {// if female is an argonian make her slim
            min = 0;
            max = 15;
        } else {
            if (outfitName.contains("Beggar")) {
                min = 10;
                max = 45;
            } else if (outfitName.contains("Barkeep")) {
                min = 40;
                max = 100;
            } else if (outfitName.contains("Chef")) {
                min = 60;
                max = 100;
            } else if (outfitName.contains("Mage")) {
                min = 45;
                max = 90;
            } else if (outfitName.contains("Wench")) {
                min = 45;
                max = 75;
            } else if (outfitName.contains("Hunter")) {
                min = 45;
                max = 80;
            } else if (outfitName.contains("Farm")) {
                min = 45;
                max = 80;
            } else if (outfitName.contains("Merchant")) {
                min = 40;
                max = 100;
            } else if (outfitName.contains("Miner")) {
                min = 10;
                max = 40;
            } else if (outfitName.contains("Prisoner")) {
                min = 10;
                max = 35;
            }
            if (NPCIterator.get(NPC_.NPCFlag.OppositeGenderAnims)) { // if female has male animations make her fat.
                min = 50;
                max = 100;
            }
        }
        NPCIterator.setWeight(RBS_Randomize.toInt(NPCIterator.getFormStr(), min, max));
        return NPCIterator;
    }

    private void setDefaultOutfit(NPC_ NPCIterator, OTFT outfit, String ID) {
        String outfitName = outfit.getEDID();
        List<OTFT> ListOfAllArmorsWithID = new ArrayList<>();
        MajorRecord MJ;
        String test;
        if (NPCIterator.getEDID().equals("Breya")) {
            String tast = "";
        }
        MJ = SkyProcStarter.patch.getOutfits().get(outfitName + "RBS_F" + "standard" + ID);
        if (MJ == null) {
            MJ = SkyProcStarter.patch.getOutfits().get(outfitName + "RBS_F" + "ct77" + ID);
            if (MJ == null) {
                MJ = SkyProcStarter.patch.getOutfits().get(outfitName + "RBS_F" + "killerkeo" + ID);
                if (MJ == null) {
                }
            }
        }

        //if (RBS_NPC.m_npc.getEDID().equals("Delphine") || RBS_NPC.m_npc.getEDID().equals("dunPOIWitchAnise") || RBS_NPC.m_raceName.toLowerCase().contains("elder")) {
        //} else {
        if (MJ != null) {
            NPCIterator.setDefaultOutfit(MJ.getForm());
        } else {
            for (FormID entry : outfit.getInventoryList()) {
                test = RBS_ARMO.vanillaArmorsMapKeyForm.get(entry);
                if (test == null) {
                    test = RBS_LeveledList.vanillaLLMapKeyForm.get(entry);
                }
                if (test != null) {
                    if (test.contains("Clothes")) {
                        for (OTFT armor : RBS_Outfit.clothes) {
                            if (armor.getEDID().contains(ID)) {
                                ListOfAllArmorsWithID.add(armor);
                            }
                        }
                        outfit = ListOfAllArmorsWithID.get(RBS_Randomize.toInt(NPCIterator.getEDID(), 1, ListOfAllArmorsWithID.size()));
                        if (outfit != null) {
                            NPCIterator.setDefaultOutfit(outfit.getForm());
                        }
                    }
                    if (test.contains("Armor")) {
                        for (OTFT armor : RBS_Outfit.armors) {
                            String asdfs = armor.getEDID();
                            if (armor.getEDID().contains(ID)) {
                                ListOfAllArmorsWithID.add(armor);
                            }
                        }
                        outfit = ListOfAllArmorsWithID.get(RBS_Randomize.toInt(NPCIterator.getEDID(), 1, ListOfAllArmorsWithID.size()));
                        if (outfit != null) {
                            NPCIterator.setDefaultOutfit(outfit.getForm());
                        }
                    }
                    ListOfAllArmorsWithID.clear();
                }
            }
        }
    }
    //ScriptRef RBStest = new ScriptRef("RBStest");
    // QUST questtest = NiftyFunc.makeScriptQuest(SPGlobal.getGlobalPatch(), RBStest);

    public void changeMale(String folder) throws Exception {
        /*
         for (NPC_ n : ListNPCMale) {
         boolean changed = false;
         FormID id = new FormID("000019Skyrim.esm");
         if (!RBS_NPC.m_npc.get(NPC_.NPCFlag.Female) && !RBS_NPC.m_npc.getRace().equals(id)) {
         FormID playerID = new FormID("000007Skyrim.esm");
         if (!RBS_NPC.m_npc.getForm().equals(playerID)) {
         for (RACE r : RBS_Race.ListRBSRacesMerger) {
         if (RBS_NPC.m_npc.getRace().equals(r.getForm())) {
         if (r.getEDID().toLowerCase().contains("argon") || r.getEDID().toLowerCase().contains("khaj")) {
         //
         } else {
         String ID = RBS_Randomize.createRandomID(RBS_NPC.m_npc.getName());
         for (RACE r2 : SkyProcStarter.patch.getRaces()) {
         if (r2.getEDID().equals(r.getEDID() + "RBS_M" + ID)) {
         RBS_NPC.m_npc.setRace(r2.getForm());
         SkyProcStarter.patch.addRecord(RBS_NPC.m_npc);
         }
         }
         }
         }
         }
         }
         }
         }
         */
    }

    public void ClothesAllToNPCs(String folder) throws Exception {
        SPProgressBarPlug.setStatus("Attaching clothes pool to NPCs");
        String bodyID = "";
        for (NPC_ n : ListNPCFemale) {

            ARMO skin = (ARMO) SkyProcStarter.patch.getArmors().get(new FormID(n.getSkin()));
            if (skin != null && skin.getEDID().contains("SkinNakedRBS_Fstandard")) {
                String[] splitResult = skin.getEDID().split("SkinNakedRBS_Fstandard");
                String temp = splitResult[1];
                String[] splitResult2 = temp.split("Texture");
                bodyID = splitResult2[0];
            }
            if (!bodyID.equals("")) {
                OTFT outfit = (OTFT) SkyProcStarter.patch.getOutfits().get(new FormID(n.getDefaultOutfit()));
                if (outfit != null && outfit.getEDID().toLowerCase().contains("clothes")) {
                    if (outfit.getForm().equals(n.getDefaultOutfit())) {
                        for (OTFT patchOutfit : SkyProcStarter.patch.getOutfits()) {
                            if (patchOutfit.getEDID().equals("ClothesAllRBS_F" + bodyID)) {
                                n.setDefaultOutfit(patchOutfit.getForm());
                                SkyProcStarter.patch.addRecord(n);
                            }
                        }
                    }
                }
            }
        }
    }

    public void AttachSkinNakedtoNPCsMale(String folder) throws Exception {
        SPProgressBarPlug.setStatus("Attaching new SkinNaked to males");
        int normalmap = 1;
        int colormap = 1;
        int specularmap = 1;
        List<FormID> rlist = new ArrayList<>();
        List<ARMO> listAA = new ArrayList<>();
        List<FormID> listAAFormID = new ArrayList<>();
        List<String> listAAEDID = new ArrayList<>();
        for (RACE rHuman : SkyProcStarter.patch.getRaces()) {
            if (rHuman.getEDID().toLowerCase().contains("nord") || rHuman.getEDID().toLowerCase().contains("redguard") || rHuman.getEDID().toLowerCase().contains("imperial") || rHuman.getEDID().toLowerCase().contains("breton") || rHuman.getEDID().toLowerCase().contains("elf")) {
                rlist.add(rHuman.getForm());
            }
        }
        for (ARMO armor : SkyProcStarter.patch.getArmors()) {
            if (armor.getEDID().contains("SkinNakedRBS_M_TEXT")) {
                listAA.add(armor);
                listAAFormID.add(armor.getForm());
                listAAEDID.add(armor.getEDID());
            }
        }
        for (NPC_ n : SkyProcStarter.merger.getNPCs()) {
            if (!n.get(NPC_.NPCFlag.Female)) {
                if (n != null) {
                    int normalmapmin = 0;
                    int normalmapmax = 3;
                    if (rlist.contains(n.getRace())) {
                        for (ARMO armor : listAA) {
                            normalmap = RBS_Randomize.toInt(n.getName() + "fdgf", normalmapmin, normalmapmax) + 1;
                            colormap = RBS_Randomize.toInt(n.getName() + "dfg", 0, mc2) + 1;
                            specularmap = RBS_Randomize.toInt(n.getName() + "asdf", 0, ms2) + 1;
                            if (armor.getEDID().equals("SkinNakedRBS_M_TEXT" + normalmap + "_" + colormap + "_" + specularmap)) {
                                n.setSkin(armor.getForm());
                                SkyProcStarter.patch.addRecord(n);
                            } else {
                                n.setSkin(new FormID("000D64Skyrim.esm"));
                                SkyProcStarter.patch.addRecord(n);
                            }
                        }
                    }
                }
            }
        }
    }

    public void AttachSkinNakedtoNPCsMale2(String folder) throws Exception {

        String bodyID = "";
        int counter = 1;
        int normalmap = 1;
        int colormap = 1;
        int specularmap = 1;
        List<RACE> rlist = new ArrayList<>();
        List<ARMO> listAA = new ArrayList<>();
        List<FormID> listAAFormID = new ArrayList<>();
        List<String> listAAEDID = new ArrayList<>();
        for (RACE rHuman : SkyProcStarter.patch.getRaces()) {
            if (rHuman.getEDID().toLowerCase().contains("nord") || rHuman.getEDID().toLowerCase().contains("redguard") || rHuman.getEDID().toLowerCase().contains("imperial") || rHuman.getEDID().toLowerCase().contains("breton") || rHuman.getEDID().toLowerCase().contains("elf")) {
                rlist.add(rHuman);

            }
        }
        for (ARMO armor : SkyProcStarter.patch.getArmors()) {
            if (armor.getEDID().contains("SkinNakedRBS_M_TEXT")) {
                listAA.add(armor);
                listAAFormID.add(armor.getForm());
                listAAEDID.add(armor.getEDID());
            }
        }
        for (NPC_ n : SkyProcStarter.patch.getNPCs()) {
            if (!n.get(NPC_.NPCFlag.Female)) {
                if (n != null) {
                    int normalmapmin = 0;
                    int normalmapmax = 3;
                    for (RACE r : rlist) {
                        if (r.getForm().equals(n.getRace())) {
                            for (ARMO armor : listAA) {
                                normalmap = RBS_Randomize.toInt(n.getName() + "fdgf", normalmapmin, normalmapmax) + 1;
                                colormap = RBS_Randomize.toInt(n.getName() + "dfg", 0, mc2) + 1;
                                specularmap = RBS_Randomize.toInt(n.getName() + "asdf", 0, ms2) + 1;
                                if (armor.getEDID().equals("SkinNakedRBS_M_TEXT" + normalmap + "_" + colormap + "_" + specularmap)) {
                                    n.setSkin(armor.getForm());
                                    SkyProcStarter.patch.addRecord(n);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void AttachSkinNakedDiversifiedTexturestoNPCsFemale(String folder) throws Exception {
        SPProgressBarPlug.setStatus("Attach new SkinNaked to females (gathering");

        List<FormID> ListSkinNakedRBS_FFormID = new ArrayList<>();
        List<String> ListSkinNakedRBS_FEDID = new ArrayList<>();
        for (ARMO armor : SkyProcStarter.patch.getArmors()) {
            if (armor.getEDID().contains("SkinNakedRBS_F" + folder) || armor.getEDID().contains("SkinNakedBeastRBS_F" + folder)) {
                ListSkinNakedRBS_FFormID.add(armor.getForm());
                ListSkinNakedRBS_FEDID.add(armor.getEDID());
            }
        }
        int counter = 1;
        SPProgressBarPlug.setStatus("Attach new SkinNaked to females (patching");
        for (NPC_ n : RBS_NPC.ListNPCFemale) {

            int normalmapmin = 1;
            int normalmapmax = 1;
            double power = n.get(Skill.HEAVYARMOR) * 3
                    + n.get(Skill.TWOHANDED) * 4
                    + n.get(Skill.LIGHTARMOR) * 1.5
                    + n.get(Skill.ONEHANDED) * 1.5
                    + n.get(Skill.BLOCK) * 1.5
                    + n.get(Skill.MARKSMAN) * 1
                    + n.get(Skill.SMITHING) * 2
                    + n.get(Skill.BLOCK) * 2;
            int x = (int) (power + 0.5d);
            if (x > 0 && x < 301) {
                normalmapmin = 0;
                normalmapmax = 2;
            } else if (x > 300 && x < 401) {
                normalmapmin = 1;
                normalmapmax = 3;
            } else if (x > 400 && x < 501) {
                normalmapmin = 2;
                normalmapmax = 4;
            } else if (x > 500 && x < 601) {
                normalmapmin = 3;
                normalmapmax = 5;
            } else if (x > 600 && x < 701) {
                normalmapmin = 4;
                normalmapmax = 6;
            } else if (x > 900 && x < 901) {
                normalmapmin = 4;
                normalmapmax = 7;
            } else if (x > 900) {
                normalmapmin = 5;
                normalmapmax = 7;
            } else {
                normalmapmin = 0;
                normalmapmax = 3;
            }
            for (int list = 0; list < ListSkinNakedRBS_FFormID.size(); list++) {
                int normalmap = RBS_Randomize.toInt(n.getName() + "fdgf", normalmapmin, normalmapmax) + 1;
                //int colormap = RBS_Randomize.toInt(n.getName() + "dfg", 0, RBS_Texture.c2 - 1) + 1;
                //int specularmap = RBS_Randomize.toInt(n.getName() + "asdf", 0, RBS_Texture.s2) + 1;
                //int detailmap = RBS_Randomize.toInt(n.getName() + "asderwf", 0, d2) + 1;
                String s = RBS_Randomize.createRandomID(n.getName() + "asdfderwf");
                if (n.getRace().equals(RBS_Race.vanillaRacesMapKeyEDID.get("ArgonianRace")) || n.getRace().equals(RBS_Race.vanillaRacesMapKeyEDID.get("KhajiitRace"))) {
                    if (ListSkinNakedRBS_FEDID.get(list).equals("SkinNakedBeastRBS_F" + folder + s)) {
                        n.setSkin(ListSkinNakedRBS_FFormID.get(list));
                        SkyProcStarter.patch.addRecord(n);

                    }
                } else {
                    if (ListSkinNakedRBS_FEDID.get(list).equals("SkinNakedRBS_Fstandard" + s + "Texture" + normalmap + "_0_0_0")) {
                        //if (ListSkinNakedRBS_FEDID.get(list).equals("SkinNakedRBS_Fstandard" + s + "Texture" + normalmap + "_" + colormap + "_1_" + specularmap)) {
                        n.setSkin(ListSkinNakedRBS_FFormID.get(list));
                        SkyProcStarter.patch.addRecord(n);
                    }
                }
            }
        }
    }
}
