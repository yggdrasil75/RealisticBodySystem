package skyprocstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.LVLI;
import skyproc.MajorRecord;
import skyproc.OTFT;
import skyproc.gui.SPProgressBarPlug;

public class RBS_Outfit {

    public static List<OTFT> clothes = new ArrayList<>();
    public static List<OTFT> armors = new ArrayList<>();
    public static Map<String, FormID> patchOutfitsMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> patchOutfitsMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> vanillaOutfitsMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaOutfitsMapKeyForm = new ConcurrentHashMap<>();
    private static OTFT m_patchOutfit;

    public void CreateNewOutfits2(String folder) throws Exception {
        /*
        for (OTFT outfit : SkyProcStarter.merger.getOutfits()) {
            for (FormID outfitList : outfit.getInventoryList()) {
                MajorRecord MJ = SkyProcStarter.merger.getArmors().get(outfitList);
                if (MJ != null) {
                    
                } else {
                    MajorRecord MJ2 = SkyProcStarter.merger.getLeveledItems().get(outfitList);
                    if (MJ != null) {
                        // ist leveledItem
                    }
                }
            }

            if (MJ != null) { //it has to be checked because not every Entry is Type ARMO
                for (String meshesGroup : SkyProcStarter.meshesGroup) { //iterate through meshesGroup (different Folders)
                    MJNew = SkyProcStarter.patch.getArmors().get(MJ.getEDID() + "RBS_F" + meshesGroup + ID); //checking if RBS_Armor exists in patch
                    if (MJNew != null) { // if exists add Armor to List and old vanilla Armor to another list
                        RBSArmorsToBeAdded.add(MJNew);
                        vanillaArmorsToBeDeleted.add(MJ);
                    }
                }
            }

            if (RBSArmorsToBeAdded.size() > 0) {
                RBS_LeveledList.m_targetlvl = (LVLI) SkyProcStarter.patch.makeCopy(leveledItem, leveledItem.getEDID() + "RBS" + ID);
                vanillaArmorsToBeDeleted.stream().forEach((Armor) -> {
                    RBS_LeveledList.m_targetlvl.removeAllEntries(Armor.getForm());
                });
                vanillaArmorsToBeDeleted.clear();
                RBSArmorsToBeAdded.stream().forEach((Armor) -> {
                    RBS_LeveledList.m_targetlvl.addEntry(Armor.getForm(), 1, 1);
                });
                RBSArmorsToBeAdded.clear();
            }
        }
    }
}
*/
}
    public void CreateNewOutfits(String folder) throws Exception {
        MajorRecord MJOld;
        MajorRecord MJNew;

        SPProgressBarPlug.setStatus("creating new outfits " + folder);
        for (int i = 1; i <= RBS_Main.amountBodyTypes; i++) {
            String bodyID = RBS_Randomize.createID(i);
            for (OTFT outfit : SkyProcStarter.merger.getOutfits()) {
                boolean patched = false;
                List<FormID> outfitList = outfit.getInventoryList();
                for (int list = 0; list < outfitList.size(); list++) {
                    MJOld = SkyProcStarter.merger.getArmors().get(outfitList.get(list));
                    if (MJOld != null) {
                        MJNew = SkyProcStarter.patch.getArmors().get(MJOld.getEDID() + "RBS_F" + folder + bodyID);
                        if (MJNew != null) {
                            if (!patched) {
                                RBS_Outfit.m_patchOutfit = (OTFT) SkyProcStarter.patch.makeCopy(outfit, outfit.getEDID() + "RBS_F" + folder + bodyID);
                                patched = true;
                            }

                            if (RBS_Outfit.m_patchOutfit.getEDID().contains("Clothes")) {
                                RBS_Outfit.clothes.add(RBS_Outfit.m_patchOutfit);
                            }
                            if (RBS_Outfit.m_patchOutfit.getEDID().contains("Armor")) {
                                RBS_Outfit.armors.add(RBS_Outfit.m_patchOutfit);
                            }

                            RBS_Outfit.m_patchOutfit.addInventoryItem(MJNew.getForm());
                            RBS_Outfit.m_patchOutfit.removeInventoryItem(MJOld.getForm());
                            SkyProcStarter.patch.addRecord(RBS_Outfit.m_patchOutfit);
                        }
                    }
                    MJOld = SkyProcStarter.merger.getLeveledItems().get(outfitList.get(list));
                    if (MJOld != null) {
                        String patchedName = MJOld.getEDID() + "RBS" + bodyID;
                        MJNew = SkyProcStarter.patch.getLeveledItems().get(patchedName);
                        if (MJNew != null) {
                            if (!patched) {
                                RBS_Outfit.m_patchOutfit = (OTFT) SkyProcStarter.patch.makeCopy(outfit, outfit.getEDID() + "RBS_F" + folder + bodyID);
                                patched = true;
                            }
                            RBS_Outfit.m_patchOutfit.addInventoryItem(MJNew.getForm());
                            RBS_Outfit.m_patchOutfit.removeInventoryItem(MJOld.getForm());
                            SkyProcStarter.patch.addRecord(RBS_Outfit.m_patchOutfit);
                        }
                    }
                }
                RBS_Outfit.m_patchOutfit = null;
            }
        }
    }
}
