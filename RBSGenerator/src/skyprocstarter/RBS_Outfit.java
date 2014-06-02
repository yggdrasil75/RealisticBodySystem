package skyprocstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.LVLI;
import skyproc.MajorRecord;
import skyproc.OTFT;
import skyproc.gui.SPProgressBarPlug;

public class RBS_Outfit {

    public static ArrayList<OTFT> clothes = new ArrayList<>();
    public static ArrayList<OTFT> armors = new ArrayList<>();
    public static Map<String, FormID> patchOutfitsMapKeyEDID = new HashMap<>();
    public static Map<FormID, String> patchOutfitsMapKeyForm = new HashMap<>();
    public static Map<String, FormID> vanillaOutfitsMapKeyEDID = new HashMap<>();
    public static Map<FormID, String> vanillaOutfitsMapKeyForm = new HashMap<>();
    private static OTFT m_patchOutfit;

    public void CreateNewOutfits(String folder) throws Exception {
        MajorRecord MJOld;
        MajorRecord MJNew;

        SPProgressBarPlug.setStatus("creating new outfits " + folder);
        for (int i = 1; i < RBS_Main.amountBodyTypes; i++) {
            String bodyID = RBS_Randomize.createID(i);
            for (OTFT outfit : SkyProcStarter.merger.getOutfits()) {
                boolean patched = false;
                ArrayList<FormID> outfitList = outfit.getInventoryList();
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

    public void exchangeLeveledLists() throws Exception {
        for (OTFT outfit : SkyProcStarter.patch.getOutfits()) {
            ArrayList<FormID> outfitList = outfit.getInventoryList();
            for (int list = 0; list < outfitList.size(); list++) {
                MajorRecord MJOld = SkyProcStarter.merger.getLeveledItems().get(outfitList.get(list));
                if (MJOld != null) {
                    String[] ID = outfit.getEDID().split("RBS_Fstandard");
                    String patchedEDID = MJOld.getEDID() + "RBS" + ID[1];
                    MajorRecord MJNew = SkyProcStarter.patch.getLeveledItems().get(patchedEDID);
                    if (MJNew != null) {
                        outfit.addInventoryItem(MJNew.getForm());
                        outfit.removeInventoryItem(MJOld.getForm());
                        SkyProcStarter.patch.addRecord(outfit);
                    }
                }
            }
        }
    }
}