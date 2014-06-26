package skyprocstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.LVLI;
import skyproc.LeveledEntry;
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

    public void CreateNewOutfits(String folder) throws Exception {
        SPProgressBarPlug.setStatus("creating new outfits " + folder);
        List<MajorRecord> entriesToBeAdded = new ArrayList<>();
        List<FormID> entriesToBeDeleted = new ArrayList<>();
        MajorRecord MJ;
        for (String ID : SkyProcStarter.amountBodyTypes) {
            for (OTFT outfit : SkyProcStarter.merger.getOutfits()) {
                for (FormID outfitEntry : outfit.getInventoryList()) {
                    
                    MJ = SkyProcStarter.merger.getArmors().get(outfitEntry);
                    if (MJ == null) { // Vanilla armor not found ? 
                        MJ = SkyProcStarter.merger.getLeveledItems().get(outfitEntry); //try leveledItem
                    }
                    if (MJ != null) {
                        MajorRecord RBSEntry = SkyProcStarter.patch.getMajor(MJ.getEDID() + "RBS_F" + folder + ID);
                        if (RBSEntry != null) {
                            entriesToBeAdded.add(RBSEntry);
                            entriesToBeDeleted.add(outfitEntry);
                            if (RBSEntry.getEDID().contains("Clothes")) {
                                RBS_Outfit.clothes.add(RBS_Outfit.m_patchOutfit);
                            }
                            if (RBSEntry.getEDID().contains("Armor")) {
                                RBS_Outfit.armors.add(RBS_Outfit.m_patchOutfit);
                            }
                        }
                    }
                }
                if (entriesToBeAdded.size() > 0) {
                    OTFT newOutfit = (OTFT) SkyProcStarter.patch.makeCopy(outfit, outfit.getEDID() + "RBS_F" + folder + ID);
                    entriesToBeDeleted.stream().forEach((Form) -> {
                        newOutfit.removeInventoryItem(Form);
                    });
                    entriesToBeDeleted.clear();
                    entriesToBeAdded.stream().forEach((Form) -> {
                        newOutfit.addInventoryItem(Form.getForm());
                    });
                    entriesToBeAdded.clear();
                }
            }
        }
    }
    /*
     for (OTFT asdf : SkyProcStarter.patch.getOutfits()) {
  
     OTFT targetlvl = (OTFT) SkyProcStarter.patch.makeCopy(asdf, asdf.getEDID().replace("001", ID));
     for (FormID outfitEntry : asdf.getInventoryList()) {
     String OutfitEDID = RBS_ARMO.patchArmorsMapKeyForm.get(outfitEntry);
     if (OutfitEDID != null) {
     FormID tutu = RBS_ARMO.patchArmorsMapKeyEDID.get(tusi.replace("001", ID));
     targetlvl.removeAllEntries(entry.getForm());
     targetlvl.addEntry(tutu, 1, 1);
     }
     }
     }
     }

     }
     */

    public void CreateNewOutfits_old(String folder) throws Exception {
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
