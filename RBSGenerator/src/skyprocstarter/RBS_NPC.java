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
import skyproc.ARMO;
import skyproc.FormID;

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
    public static Map<FormID, String> VoiceTypeMap = new ConcurrentHashMap<>();
    public static List<FormID> ListNPCFemalePatched = new ArrayList<>();
    public static int mn2 = 1;
    public static int mc2 = 1;
    public static int ms2 = 1;
    private static NPC_ m_npc;
    private static String m_RBSNumber;
    private static String m_folder;
    private static String m_raceName;
    private static String m_outfitName;
    private static MajorRecord m_sourceOutfit;

    RBS_NPC() {
        FormID playerID = new FormID("000007Skyrim.esm");
        for (VTYP VoiceTypes : SkyProcStarter.merger.getVoiceTypes()) {
            VoiceTypeMap.put(VoiceTypes.getForm(), VoiceTypes.getEDID());
        }
        for (NPC_ n : SkyProcStarter.merger.getNPCs()) {
            for (RACE ListRBSRacesMerger : RBS_Race.ListRBSRacesMerger) {
                if (!n.getForm().equals(playerID) && n.getRace().equals(ListRBSRacesMerger.getForm())) {
                    //if (VoiceTypeMap.get(n.getVoiceType()).toLowerCase().contains("Female") || n.getForm() == playerID) {
                    if (!n.getForm().equals(playerID) || n.get(NPC_.NPCFlag.Female)) {
                        ListNPCFemale.add(n);
                    }

                    if (!n.get(NPC_.NPCFlag.Female)) {
                        //if (VoiceTypeMap.get(n.getVoiceType()).toLowerCase().contains("Male")) {
                        ListNPCMale.add(n);
                    }
                    if (n.get(NPC_.NPCFlag.Unique)) {
                        ListNPCMaleUnique.add(n);
                    } else {
                        ListNPCMaleNotUnique.add(n);
                    }
                }
                ListNPC.add(n);
            }
        }
    }

    public void changeFemale(String folder) throws Exception {
        int counter = 0;
        boolean patchOutfits;
        int max = ListNPCFemale.size();
        RBS_NPC.m_folder = folder;
        for (NPC_ n : ListNPCFemale) {
            
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
            changeWeightByJob();
            changeHeight();
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

            this.setSpeedMult();
            n = RBS_NPC.m_npc;
            SkyProcStarter.patch.addRecord(n);
            ListNPCFemalePatched.add(n.getForm());
            RBS_NPC.m_npc = null;
            RBS_NPC.m_raceName = null;
        }
        RBS_NPC.m_folder = null;
    }

    public void femalize() {
        for (NPC_ n : ListNPCMaleNotUnique) {
            n.set(NPC_.NPCFlag.Female, true);
            SkyProcStarter.patch.addRecord(n);
        }
    }

    private void setSpeedMult() {
        if (SkyProcStarter.merger.getRaces().get(RBS_NPC.m_npc.getRace()).getEDID().toLowerCase().contains("eldar")) {
            RBS_NPC.m_npc.set(NPC_.NPCStat.SPEED_MULT, RBS_Randomize.toInt(RBS_NPC.m_npc.getEDID() + RBS_NPC.m_npc.getFormStr(), 40, 70));
        } else {
            RBS_NPC.m_npc.set(NPC_.NPCStat.SPEED_MULT, RBS_Randomize.toInt(RBS_NPC.m_npc.getEDID() + RBS_NPC.m_npc.getFormStr(), 70, 100));
        }
        if (RBS_NPC.m_npc.getWeight() > 80) {
            RBS_NPC.m_npc.set(NPC_.NPCStat.SPEED_MULT, RBS_Randomize.toInt(RBS_NPC.m_npc.getEDID() + RBS_NPC.m_npc.getFormStr(), 60, 80));
        }
        if (RBS_NPC.m_npc.getWeight() > 90) {
            RBS_NPC.m_npc.set(NPC_.NPCStat.SPEED_MULT, RBS_Randomize.toInt(RBS_NPC.m_npc.getEDID() + RBS_NPC.m_npc.getFormStr(), 50, 70));
        }
    }

    private void changeHeight() {
        float tmp = RBS_Randomize.toFloat(RBS_NPC.m_npc.getName() + RBS_NPC.m_npc.getFormStr(), 1, 1500) + 8500;
        float randomheight = tmp / 10000;
        RBS_NPC.m_npc.setHeight(randomheight);
    }

    private void changeWeightByJob() {

        if (SkyProcStarter.save.getBool(YourSaveFile.Settings.CHANGE_WEIGHT_BY_JOB_ON)) {
            RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 5, 95));
        }

        for (OTFT mergerOutfit : SkyProcStarter.merger.getOutfits()) {
            if (RBS_NPC.m_sourceOutfit != null) {
                if (RBS_NPC.m_sourceOutfit.getForm().equals(mergerOutfit.getForm())) {
                    List<FormID> listInventory = mergerOutfit.getInventoryList();
                    for (FormID listInventory1 : listInventory) {
                        FormID item = new FormID(listInventory1);
                        RBS_NPC.m_npc.addItem(item, 1);
                    }
                }
            }
        }
        if (RBS_NPC.m_sourceOutfit != null) {

            if (SkyProcStarter.save.getBool(YourSaveFile.Settings.CHANGE_WEIGHT_BY_JOB_ON)) {
                RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 35, 75));

                if (RBS_NPC.m_outfitName.contains("Beggar")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 10, 45));
                }
                if (RBS_NPC.m_outfitName.contains("Barkeep")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 40, 100));
                }
                if (RBS_NPC.m_outfitName.contains("Chef")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 60, 100));
                }
                if (RBS_NPC.m_outfitName.contains("Mage")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 45, 90));
                }
                if (RBS_NPC.m_outfitName.contains("Wench")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 45, 75));
                }

                if (RBS_NPC.m_outfitName.contains("Hunter")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 45, 80));
                }

                if (RBS_NPC.m_outfitName.contains("Farm")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 45, 80));
                }

                if (RBS_NPC.m_outfitName.contains("Merchant")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 40, 100));
                }

                if (RBS_NPC.m_outfitName.contains("Miner")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 10, 40));
                }

                if (RBS_NPC.m_outfitName.contains("Prisoner")) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 10, 35));
                }

                if (RBS_NPC.m_npc.get(NPC_.NPCFlag.OppositeGenderAnims)) {
                    RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 50, 100));
                }
                RBS_NPC.m_npc.setWeight(RBS_Randomize.toInt(RBS_NPC.m_npc.getFormStr(), 25, 95));
            }
        }
    }

    private void setDefaultOutfit() {
        MajorRecord MJ;
        MJ = SkyProcStarter.patch.getOutfits().get(RBS_NPC.m_outfitName + "RBS_F" + "standard" + RBS_NPC.m_RBSNumber);
        if (MJ == null) {
            MJ = SkyProcStarter.patch.getOutfits().get(RBS_NPC.m_outfitName + "RBS_F" + "ct77" + RBS_NPC.m_RBSNumber);
            if (MJ == null) {
                MJ = SkyProcStarter.patch.getOutfits().get(RBS_NPC.m_outfitName + "RBS_F" + "killerkeo" + RBS_NPC.m_RBSNumber);
                if (MJ == null) {
                }
            }
        }

        String TargetOutfit = RBS_NPC.m_outfitName + "RBS_F" + "killerkeo" + RBS_NPC.m_RBSNumber;
        if (RBS_NPC.m_npc != null) {
            if (RBS_NPC.m_npc.getEDID().equals("Delphine") || RBS_NPC.m_npc.getEDID().equals("dunPOIWitchAnise") || RBS_NPC.m_raceName.toLowerCase().contains("elder")) {
            } else {
                if (MJ != null) {
                    RBS_NPC.m_npc.setDefaultOutfit(MJ.getForm());
                } else {
                    if (RBS_NPC.m_outfitName.contains("Clothes") && RBS_NPC.m_outfitName.contains(m_RBSNumber)) {
                        OTFT Outfit = RBS_Outfit.clothes.get(RBS_Randomize.toInt(RBS_NPC.m_npc.getEDID(), 1, RBS_Outfit.clothes.size()));
                        RBS_NPC.m_npc.setDefaultOutfit(Outfit.getForm());
                    }
                    if (RBS_NPC.m_outfitName.contains("Armor") && RBS_NPC.m_outfitName.contains(m_RBSNumber)) {
                        OTFT Outfit = RBS_Outfit.armors.get(RBS_Randomize.toInt(RBS_NPC.m_npc.getEDID(), 1, RBS_Outfit.armors.size()));
                        RBS_NPC.m_npc.setDefaultOutfit(Outfit.getForm());
                    }
                }
            }
        }
    }

    //ScriptRef RBStest = new ScriptRef("RBStest");
    // QUST questtest = NiftyFunc.makeScriptQuest(SPGlobal.getGlobalPatch(), RBStest);

    public void changeMale(String folder) throws Exception {
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
