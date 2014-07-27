package skyprocstarter;

import java.util.HashSet;
import skyproc.ARMO;
import skyproc.FormID;
import skyproc.LVLI;
import skyproc.OTFT;

public class RBS_CleanUnusedData {

    public HashSet<ARMO> usedArmors = new HashSet();
    public HashSet<LVLI> usedLeveledItemsList = new HashSet();
    public HashSet<OTFT> usedOutfits = new HashSet();

    public void checkForOutfit(FormID entry) {
        if (SkyProcStarter.patch.getOutfits().get(entry) != null) {
            usedOutfits.add((OTFT) SkyProcStarter.patch.getOutfits().get(entry));
        }
    }

    public void checkForArmor(FormID entry) {
        if (SkyProcStarter.patch.getArmors().get(entry) != null) {
            usedArmors.add((ARMO) SkyProcStarter.patch.getArmors().get(entry));
        }
    }

    public void checkRecursivForLeveledItem(FormID entry) {
        HashSet<FormID> usedLeveledItems = new HashSet();
        if (SkyProcStarter.patch.getLeveledItems().get(entry) != null) {
            checkForOutfit(entry);
            checkForArmor(entry);
            if (SkyProcStarter.patch.getLeveledItems().get(entry) != null) {
                usedLeveledItems.add(entry);
                usedLeveledItemsList.add((LVLI) SkyProcStarter.patch.getLeveledItems().get(entry));
            }
        }
        if (usedLeveledItems.size() > 0) {
            for (FormID newEntry : usedLeveledItems) {
                checkRecursivForLeveledItem(newEntry);
            }
        }

    }

    public void CheckOutfits() {
        for (OTFT outfit : SkyProcStarter.patch.getOutfits()) {
            for (FormID entry : outfit.getInventoryList()) {
                checkForOutfit(entry);
                checkForArmor(entry);
                checkRecursivForLeveledItem(entry);
            }
        }

        SkyProcStarter.patch.getArmors().clear();
        usedArmors.stream().forEach((armor) -> {
            SkyProcStarter.patch.getArmors().addRecord(armor);
        });

        SkyProcStarter.patch.getOutfits().clear();
        usedOutfits.stream().forEach((outfit) -> {
            SkyProcStarter.patch.getOutfits().addRecord(outfit);
        });
        SkyProcStarter.patch.getLeveledItems().clear();
        usedLeveledItemsList.stream().forEach((leveledItem) -> {
            SkyProcStarter.patch.getLeveledItems().addRecord(leveledItem);
        });

    }

    /**
     * public void cleanUnusedData() { for (NPC_ n :
     * SkyProcStarter.patch.getNPCs().getRecords()) { if
     * (!SkyProcStarter.usedDefaultOutfits.contains(SkyProcStarter.patch.getOutfits().get(n.getDefaultOutfit())))
     * { if (SkyProcStarter.patch.getOutfits().contains(n.getDefaultOutfit())) {
     * SkyProcStarter.usedDefaultOutfits.add(SkyProcStarter.patch.getOutfits().get(n.getDefaultOutfit()));
     * } }
     *
     * if (SkyProcStarter.patch.getArmors().contains(n.getSkin())) { if
     * (!SkyProcStarter.usedArmors.contains(SkyProcStarter.patch.getArmors().get(n.getSkin())))
     * { usedArmors.add(SkyProcStarter.patch.getArmors().get(n.getSkin())); } }
     * }
     *
     * SkyProcStarter.patch.getOutfits().clear(); for (MajorRecord Outfit :
     * SkyProcStarter.usedDefaultOutfits) {
     * SkyProcStarter.patch.addRecord(Outfit); }
     *
     * for (OTFT Outfit : SkyProcStarter.patch.getOutfits().getRecords()) { for
     * (FormID ID : Outfit.getInventoryList()) { if
     * (SkyProcStarter.patch.getLeveledItems().contains(ID)) { if
     * (!SkyProcStarter.usedLeveledItems.contains(SkyProcStarter.patch.getLeveledItems().get(ID)))
     * {
     * SkyProcStarter.usedLeveledItems.add(SkyProcStarter.patch.getLeveledItems().get(ID));
     * }
     *
     * }
     * if (SkyProcStarter.patch.getArmors().contains(ID)) { if
     * (!SkyProcStarter.usedArmors.contains(SkyProcStarter.patch.getArmors().get(ID)))
     * {
     * SkyProcStarter.usedArmors.add(SkyProcStarter.patch.getArmors().get(ID));
     * } } } }
     *
     * for (LVLI leveledItem : SkyProcStarter.patch.getLeveledItems()) { for
     * (LeveledEntry entry : leveledItem.getEntries()) { if
     * (SkyProcStarter.patch.getArmors().contains(entry.getForm())) { if
     * (!SkyProcStarter.usedArmors.contains(SkyProcStarter.patch.getArmors().get(entry.getForm())))
     * {
     * SkyProcStarter.usedArmors.add(SkyProcStarter.patch.getArmors().get(entry.getForm()));
     * } } } }
     *
     * SkyProcStarter.patch.getLeveledItems().clear(); for (MajorRecord
     * LeveledItem : SkyProcStarter.usedLeveledItems) {
     * SkyProcStarter.patch.addRecord(LeveledItem); }
     *
     * SkyProcStarter.patch.getArmors().clear();
     *
     * for (MajorRecord Armor : SkyProcStarter.usedArmors) {
     *
     * SkyProcStarter.patch.addRecord(Armor); // armors ist wohl wenigstens
     * einmal null, wo kommt das her, steckt das in den leveled lists oder in
     * armor?
     *
     * }
     *
     * for (ARMO armor : SkyProcStarter.patch.getArmors()) { for (FormID ID :
     * armor.getArmatures()) { if (SkyProcStarter.patch.getArmatures().get(ID)
     * != null) { if
     * (!SkyProcStarter.usedArmatures.contains(SkyProcStarter.patch.getArmatures().get(ID)))
     * {
     * SkyProcStarter.usedArmatures.add(SkyProcStarter.patch.getArmatures().get(ID));
     * } } } } SkyProcStarter.patch.getArmatures().clear(); for (MajorRecord
     * armature : SkyProcStarter.usedArmatures) { if (armature != null) {
     * SkyProcStarter.patch.addRecord(armature); } else {
     * JOptionPane.showMessageDialog(null, "null" + armature.getEDID()); } } /*
     * for (NPC_ n : SkyProcStarter.patch.getNPCs()) { if (n.getSkin() == null)
     * { JOptionPane.showMessageDialog(null, n.getEDID() + " ist null"); } else
     * { if (SkyProcStarter.patch.getArmors().get(n.getSkin()) == null) { if
     * (SkyProcStarter.patch.getOutfits().get(n.getDefaultOutfit()) != null) {
     * JOptionPane.showMessageDialog(null,
     * SkyProcStarter.patch.getOutfits().get(n.getDefaultOutfit()).getEDID()); }
     * n.setSkin(SkyProcStarter.merger.getArmors().get("SkinNaked").getForm());
     * SkyProcStarter.patch.addRecord(n); } } }
     *
     * for (NPC_ n : SkyProcStarter.patch.getNPCs()) { if (n.getSkin() == null)
     * { JOptionPane.showMessageDialog(null, n.getEDID() + " ist null"); } else
     * { if (SkyProcStarter.patch.getArmors().get(n.getSkin()) == null &&
     * SkyProcStarter.merger.getArmors().get(n.getSkin()) == null) {
     *
     * JOptionPane.showMessageDialog(null, n.getEDID() + n.getSkin() + " hatt
     * keine Armor"); } } }
     */
}
