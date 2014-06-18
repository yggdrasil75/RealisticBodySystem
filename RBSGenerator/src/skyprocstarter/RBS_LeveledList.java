package skyprocstarter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.LVLI;
import skyproc.LeveledEntry;
import skyproc.LeveledRecord;
import skyproc.MajorRecord;
import skyproc.OTFT;
import skyproc.gui.SPProgressBarPlug;

public class RBS_LeveledList {

    public static Map<String, FormID> vanillaLVLIMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaLVLIMapKeyForm = new ConcurrentHashMap<>();
    public static Map<String, FormID> patchLVLIMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> patchLVLIMapKeyForm = new ConcurrentHashMap<>();
    public static List<LeveledEntry> inventoryList = new ArrayList<>();
    public static List<LVLI> leveledItemsWithArmorEntries = new ArrayList<>();
    private static int m_counter;
    private static LVLI m_targetlvl;

    RBS_LeveledList() {
    }

    public static void progressBar() {
        int max = SkyProcStarter.patch.getOutfits().numRecords();
        SPProgressBarPlug.setStatusNumbered(RBS_LeveledList.m_counter, max, "attaching new leveled lists to new outfits");
        RBS_LeveledList.m_counter++;
    }

    public void CheckItemsOfLeveledListsForPatching() {
        boolean patched;
        String ID;
        int list;
        List<ARMO> RBS_ArmorsID = new ArrayList<>();
        SPProgressBarPlug.setStatus("Filling leveledItemsWithArmorEntries");
        for (LVLI leveledItem : SkyProcStarter.merger.getLeveledItems()) {
            for (LeveledEntry test : leveledItem.getEntries()) {
                MajorRecord MJ = SkyProcStarter.merger.getArmors().get(test.getForm());
                if (MJ != null) {
                    RBS_LeveledList.leveledItemsWithArmorEntries.add(leveledItem);
                    break;
                }
            }
        }
        SPProgressBarPlug.setStatus("attaching new leveled lists to new outfits");
        for (int i = 1; i < RBS_Main.amountBodyTypes; i++) {
            ID = RBS_Randomize.createID(i);
            RBS_ArmorsID.clear();
            for (ARMO armor : SkyProcStarter.patch.getArmors()) {
                if (armor.getEDID().contains(ID)) {
                    RBS_ArmorsID.add(armor);
                }
            }
            for (LVLI leveledItem : RBS_LeveledList.leveledItemsWithArmorEntries) {
                for (LeveledEntry entry : leveledItem.getEntries()) {
                    patched = false;
                    ID = RBS_Randomize.createID(i);
                    MajorRecord MJ = SkyProcStarter.merger.getArmors().get(entry.getForm());
                    if (MJ != null) {
                        String itemName = MJ.getEDID();
                        for (ARMO patchedArmor : RBS_ArmorsID) {
                            if ((patchedArmor.getEDID().contains(itemName) && patchedArmor.getEDID().contains(ID)) && !patchedArmor.getEDID().contains("DUPLICATE")) {
                                if (!patched) {
                                    RBS_LeveledList.m_targetlvl = (LVLI) SkyProcStarter.patch.makeCopy(leveledItem, leveledItem.getEDID() + "RBS" + ID);
                                    RBS_LeveledList.patchLVLIMapKeyEDID.put(RBS_LeveledList.m_targetlvl.getEDID(), RBS_LeveledList.m_targetlvl.getForm());
                                    RBS_LeveledList.patchLVLIMapKeyForm.put(RBS_LeveledList.m_targetlvl.getForm(), RBS_LeveledList.m_targetlvl.getEDID());
                                    patched = true;
                                }
                                RBS_LeveledList.m_targetlvl.addEntry(patchedArmor.getForm(), entry.getLevel(), entry.getCount());
                                RBS_LeveledList.m_targetlvl.removeAllEntries(entry.getForm());
                            }
                        }
                    }
                }
                list = 0;
            }
        }
    }

    public void CreateNewLeveledLists(String folder) throws Exception {
        LVLI targetlvl = null;
        NumberFormat formatter = new DecimalFormat("000");

        LVLI LItemClothesAll = (LVLI) SkyProcStarter.merger.getLeveledItems().get(new FormID("106662Skyrim.esm"));
        for (int i = 1; i < RBS_Main.amountBodyTypes; i++) {
            boolean patched;
            for (OTFT o : SkyProcStarter.patch.getOutfits()) {
                String s = formatter.format(i);
                patched = false;
                if (o.getEDID().contains("RBS_F") && o.getEDID().contains(s)) {
                    if (!patched) {
                        String outfit = o.getEDID();
                        outfit = outfit.replace("standard", "");
                        outfit = outfit.replace("killerkeo", "");
                        outfit = outfit.replace("mak", "");
                        targetlvl = (LVLI) SkyProcStarter.patch.makeCopy(LItemClothesAll, "LItem" + outfit);
                        patched = true;
                    }
                    targetlvl.clearEntries();
                    List<FormID> inventoryList = o.getInventoryList();
                    for (int list = 0; list < inventoryList.size(); list++) {
                        targetlvl.addEntry(inventoryList.get(list), 1, 1);
                    }
                    o.clearInventoryItems();
                    o.addInventoryItem(targetlvl.getForm());
                    //           SkyProcStarter.patch.addRecord(o);
                }
            }
        }
    }

    public void LItemBootsAll(String folder) throws Exception {
        SPProgressBarPlug.setStatus("Adding foodwork to pool");
        NumberFormat formatter = new DecimalFormat("000");
        LVLI LItemClothesAll = (LVLI) SkyProcStarter.merger.getLeveledItems().get(new FormID("106662Skyrim.esm"));
        for (int bodies = 1; bodies < RBS_Main.amountBodyTypes; bodies++) {
            String s = formatter.format(bodies);
            LVLI targetllist = (LVLI) SkyProcStarter.patch.makeCopy(LItemClothesAll, "LItemBootsAllRBS" + s);
            targetllist.clearEntries();
            for (ARMO Armor : RBS_ARMO.ListVanillaArmors) {
                if (Armor.getEDID().toLowerCase().contains("shoes") || Armor.getEDID().toLowerCase().contains("boots")) {
                    targetllist.addEntry(Armor.getForm(), 1, 1);
                    targetllist.set(LeveledRecord.LVLFlag.UseAll, false);
                }
            }
        }
    }

    public void LItemHatsAll(String folder) throws Exception {
        SPProgressBarPlug.setStatus("Adding hats to pool");
        LVLI LItemClothesAll = (LVLI) SkyProcStarter.merger.getLeveledItems().get(new FormID("106662Skyrim.esm"));
        LVLI targetllist = (LVLI) SkyProcStarter.patch.makeCopy(LItemClothesAll, "LItemHatsAll");
        targetllist.clearEntries();
        for (ARMO Armor : SkyProcStarter.patch.getArmors()) {
            if (Armor.getEDID().toLowerCase().contains("clothes")) {
                if (Armor.getEDID().toLowerCase().contains("hat")) {
                    targetllist.addEntry(Armor.getForm(), 1, 1);
                    targetllist.set(LeveledRecord.LVLFlag.UseAll, false);
                }
            }
        }
    }

    public void LItemClothesAll(String folder) throws Exception {
        SPProgressBarPlug.setStatus("Adding clothes to pool");
        NumberFormat formatter = new DecimalFormat("000");
        LVLI LItemClothesAll = (LVLI) SkyProcStarter.merger.getLeveledItems().get(new FormID("106662Skyrim.esm"));
        OTFT FarmClothesRandom = (OTFT) SkyProcStarter.merger.getOutfits().get(new FormID("09D4B5Skyrim.esm"));

        for (int bodies = 1; bodies < RBS_Main.amountBodyTypes; bodies++) {
            String s = formatter.format(bodies);
            LVLI targetllist = (LVLI) SkyProcStarter.patch.makeCopy(LItemClothesAll, LItemClothesAll.getEDID() + "RBS_F" + s);
            List<LeveledEntry> listEntries = targetllist.getFlattenedEntries();
            targetllist.clearEntries();
            for (ARMO patchedArmor : SkyProcStarter.patch.getArmors()) {
                if (patchedArmor.getEDID().contains("Clothes") && patchedArmor.getEDID().contains(s)) {
                    targetllist.addEntry(patchedArmor.getForm(), 1, 1);
                }
            }
            targetllist.set(LeveledRecord.LVLFlag.UseAll, false);
            //    SkyProcStarter.patch.addRecord(targetllist);
            OTFT targetOutfit2 = (OTFT) SkyProcStarter.patch.makeCopy(FarmClothesRandom, "ClothesAllRBS_F" + s);
            targetOutfit2.clearInventoryItems();
            targetOutfit2.addInventoryItem(targetllist.getForm());
            for (LVLI llist2 : SkyProcStarter.patch.getLeveledItems()) {
                if (llist2.getEDID().equals("LItemBootsAllRBS" + s)) {
                    targetOutfit2.addInventoryItem(llist2.getForm());
                }
            }
        }
    }

    public void exchangeLeveledLists() {
    
        String ID;
        SPProgressBarPlug.setStatus("Exchange LeveledItems in LeveledList");
        for (int i = 1; i < RBS_Main.amountBodyTypes; i++) {
            ID = RBS_Randomize.createID(i);
            for (LVLI leveledItem : SkyProcStarter.patch.getLeveledItems()) {
                List <LeveledEntry> asdf = leveledItem.getEntries();
                 for (int list = 0; list < asdf.size(); list++) {
                    if (asdf.get(list).getForm() != null) {
                        MajorRecord MJ = SkyProcStarter.merger.getLeveledItems().get(asdf.get(list).getForm());
                        if (MJ != null) {
                            String itemName = MJ.getEDID() + "RBS" + ID;
                            MajorRecord MJNew = SkyProcStarter.patch.getLeveledItems().get(itemName);
                            if (MJNew != null) {
                                leveledItem.addEntry(MJNew.getForm(), asdf.get(list).getLevel(), asdf.get(list).getCount());
                                leveledItem.removeAllEntries(asdf.get(list).getForm());
                            }
                        }
                    }
                }
            }
        }
    }

    public void RemoveVanillaEntriesInLeveledLists() throws Exception {
        SPProgressBarPlug.setStatus("RemoveVanillaEntriesInLeveledLists");
        List<LeveledEntry> inventoryList;
        for (LVLI l : SkyProcStarter.patch.getLeveledItems()) {
            int count = 0;
            inventoryList = l.getEntries();
            for (int list = 0; list < inventoryList.size(); list++) {
                FormID test = inventoryList.get(list).getForm();
                ARMO sourceArmor = (ARMO) SkyProcStarter.patch.getArmors().get(inventoryList.get(list).getForm());
                if (sourceArmor != null) {
                    count++;
                }
            }

            if (count > 0) {
                List<LeveledEntry> tmp = new ArrayList<>(inventoryList);
                for (LeveledEntry le : tmp) {

                    if (SkyProcStarter.merger.getArmors().get(le.getForm()) != null) {
                        String sourceArmor = SkyProcStarter.merger.getArmors().get(le.getForm()).getEDID();
                        if (sourceArmor.contains("Cuirass") || sourceArmor.contains("Clothes")) {
                            l.removeAllEntries(le.getForm());
                        }
                    }
                }
            }
        }
    }
}
