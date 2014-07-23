package skyprocstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.LVLI;
import skyproc.LeveledEntry;
import skyproc.MajorRecord;
import skyproc.OTFT;
import skyproc.gui.SPProgressBarPlug;
import static skyprocstarter.RBS_LeveledList.vanillaLLMapKeyEDID;

public class RBS_Outfit {

    public static Set<OTFT> clothes = new HashSet<>();
    public static Set<OTFT> armors = new HashSet<>();
    public static Set<FormID> VanillaClothes = new HashSet<>();
    public static Set<FormID> VanillaArmors = new HashSet<>();
    public static Map<String, FormID> patchOutfitsArmorMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> patchOutfitsArmorMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> patchOutfitsClothesMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> patchOutfitsClothesMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> vanillaOutfitsMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaOutfitsMapKeyForm = new ConcurrentHashMap<>();
    private static OTFT m_patchOutfit;

    RBS_Outfit() {
        for (OTFT outfit : SkyProcStarter.merger.getOutfits()) {
            for (FormID outfitEntry : outfit.getInventoryList()) {
                String vanillaArmorName = RBS_ARMO.vanillaArmorsMapKeyForm.get(outfitEntry);
                if (vanillaArmorName != null) {
                    if (vanillaArmorName.contains("Clothes")) {
                        RBS_Outfit.VanillaClothes.add(outfit.getForm());
                    }
                    if (vanillaArmorName.contains("Armor")) {
                        RBS_Outfit.VanillaArmors.add(outfit.getForm());
                    }
                }
            }
        }
    }

    public void CreateNewOutfits(String folder) throws Exception {
        SPProgressBarPlug.setStatus("creating new outfits " + folder);
        List<FormID> entriesToBeAdded = new ArrayList<>();
        List<FormID> entriesToBeDeleted = new ArrayList<>();
        String ArmorType = "";
        for (String ID : SkyProcStarter.amountBodyTypesString) {
            for (OTFT outfit : SkyProcStarter.merger.getOutfits()) {
                for (FormID outfitEntry : outfit.getInventoryList()) {
                    String vanillaArmorName = RBS_ARMO.vanillaArmorsMapKeyForm.get(outfitEntry);
                    if (vanillaArmorName != null) {
                        FormID FormPatched = RBS_ARMO.patchArmorsMapKeyEDID.get(vanillaArmorName + "RBS_F" + folder + ID);
                        if (FormPatched != null) {
                            entriesToBeAdded.add(FormPatched);
                            entriesToBeDeleted.add(outfitEntry);
                            if (vanillaArmorName.contains("Clothes")) {

                                ArmorType = "clothes";
                            }
                            if (vanillaArmorName.contains("Armor")) {

                                ArmorType = "armor";
                            }
                        }
                    }
                    String vanillaLLName = RBS_LeveledList.vanillaLLMapKeyForm.get(outfitEntry);
                    if (vanillaLLName != null) {
                        FormID FormPatched = RBS_LeveledList.vanillaLLMapKeyEDID.get(vanillaLLName + "RBS_F" + folder + ID);
                        if (FormPatched != null) {
                            entriesToBeAdded.add(FormPatched);
                            entriesToBeDeleted.add(outfitEntry);
                        }
                        if (vanillaLLName.contains("Clothes")) {

                            ArmorType = "clothes";
                        }
                        if (vanillaLLName.contains("Armors")) {

                            ArmorType = "armor";
                        }
                    }
                }

                if (entriesToBeAdded.size() > 0) {
                    OTFT newOutfit = (OTFT) SkyProcStarter.patch.makeCopy(outfit, outfit.getEDID() + "RBS_F" + folder + ID);
                    if (ArmorType.equals("clothes")) {
                        RBS_Outfit.clothes.add(newOutfit);
                        patchOutfitsClothesMapKeyEDID.put(newOutfit.getEDID(), newOutfit.getForm());
                        patchOutfitsClothesMapKeyForm.put(newOutfit.getForm(), newOutfit.getEDID());
                    }

                    if (ArmorType.equals("armor")) {
                        RBS_Outfit.armors.add(newOutfit);
                        patchOutfitsArmorMapKeyEDID.put(newOutfit.getEDID(), newOutfit.getForm());
                        patchOutfitsArmorMapKeyForm.put(newOutfit.getForm(), newOutfit.getEDID());
                    }

                    entriesToBeDeleted.stream().forEach((Form) -> {
                        newOutfit.removeInventoryItem(Form);
                    });
                    entriesToBeDeleted.clear();
                    entriesToBeAdded.stream().forEach((Form) -> {
                        newOutfit.addInventoryItem(Form);
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
        for (int i = 1; i <= SkyProcStarter.amountBodyTypesFemale; i++) {
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
