package skyprocstarter;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import skyproc.ARMA;
import skyproc.FormID;
import skyproc.RACE;
import skyproc.SPGlobal;
import skyproc.genenums.Gender;
import skyproc.genenums.Perspective;
import skyproc.gui.SPProgressBarPlug;

public class RBS_Race {

    public static Map<String, FormID> patchRacesMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> patchRacesMapKeyForm = new ConcurrentHashMap<>();
    public static List<RACE> ListRBSRacesMerger = new ArrayList<>();
    public static Map<String, FormID> vanillaRacesMapKeyEDID = new ConcurrentHashMap<>();
    public static Map<FormID, String> vanillaRacesMapKeyForm = new ConcurrentHashMap<>();
    public static List<FormID> ListRBSRacesPatchFormID = new ArrayList<>();
    public static List<RACE> ListRBSRacesPatch = new ArrayList<>();

    RBS_Race() {
        for (RACE r : SkyProcStarter.merger.getRaces()) {
            if (r.getModel(Gender.MALE).toLowerCase().contains("actors\\character\\character assets")
                    || r.getModel(Gender.FEMALE).toLowerCase().contains("actors\\character\\character assets female")) {
                if (!r.getEDID().toLowerCase().contains("child")) {
                    if (r.getEDID().toLowerCase().contains("vampire") || r.getEDID().toLowerCase().contains("afflicted") || r.getEDID().toLowerCase().contains("astrid") || r.getEDID().toLowerCase().contains("invisible") || r.getEDID().toLowerCase().contains("elder") || r.getEDID().toLowerCase().contains("testrace") || r.getEDID().toLowerCase().contains("manakin")) {
                    } else {
                        ListRBSRacesMerger.add(r);
                        //r.setWornArmor(new FormID("000D64Skyrim.esm"));
                        //      r.setWornArmor(FormID.NULL);
                        //         SkyProcStarter.patch.addRecord(r);
                        vanillaRacesMapKeyEDID.put(r.getEDID(), r.getForm());
                        vanillaRacesMapKeyForm.put(r.getForm(), r.getEDID());
                    }
                }
            }
        }
    }

    public void CreateNewMaleRaces() throws Exception {
        SPProgressBarPlug.setStatus("creating new male races 1");
        // no skeletonbeast for now
        NumberFormat formatter = new DecimalFormat("000");
        for (int i = 1; i < RBS_Main.amountBodyTypesMale; i++) {
            for (RACE r : ListRBSRacesMerger) {
                String s = formatter.format(i);
                RACE copiedRace = (RACE) SkyProcStarter.patch.makeCopy(r, r.getEDID() + "RBS_M" + s);

                FormID copiedRaceID = new FormID(copiedRace.getForm());
                String copiedRaceEDID = copiedRace.getEDID();

                FormID RaceID = new FormID(r.getForm());
                String RaceEDID = copiedRace.getEDID();

                copiedRace.set(RACE.RACEFlags.Playable, false);
                copiedRace.setModel(Gender.MALE, "RBS\\Skeletons\\skeletonRBS_M" + s + ".nif");
                copiedRace.setWornArmor(FormID.NULL);
                SkyProcStarter.patch.addRecord(copiedRace);
            }
        }
    }

    public void CreateNewMaleRaces2() throws Exception {
        SPProgressBarPlug.setStatus("creating new male races 2");
        List<ARMA> ListAABeast = new ArrayList<>();
        List<String> ListMaleRacesEDID = new ArrayList<>();
        List<FormID> ListMaleRacesFormID = new ArrayList<>();
        RBS_ARMA.ListVanillaAA.stream().forEach((sourceAA) -> {
            ListAABeast.add(sourceAA);
        });

        for (RACE r : SkyProcStarter.patch.getRaces()) {
            if (r.getEDID().contains("RBS_M")) {
                ListMaleRacesEDID.add(r.getEDID());
                ListMaleRacesFormID.add(r.getForm());
            }
        }

        for (int listaa = 1; listaa < ListAABeast.size(); listaa++) {
            for (int list = 1; list < ListMaleRacesEDID.size(); list++) {
                ListAABeast.get(listaa).addAdditionalRace(ListMaleRacesFormID.get(list));
            }
            SkyProcStarter.patch.addRecord(ListAABeast.get(listaa));
        }
    }

    public void createForNewAnimations() throws Exception {

        SPProgressBarPlug.setStatus("Creating races");
        NumberFormat formatter = new DecimalFormat("00");
        NumberFormat formatter2 = new DecimalFormat("000");

        ListRBSRacesMerger.stream().filter((r) -> (r.getPhysicsModel(Gender.FEMALE).toString().toLowerCase().contains("defaultfemale.hkx"))).forEach((r) -> {
            for (int i = 1; i <= RBS_Animation.amountOfAnimationFolders; i++) {
                String s = formatter.format(i);
                String t = formatter2.format(i);
                RACE copiedRace = (RACE) SkyProcStarter.patch.makeCopy(r, r.getEDID() + "RBS_F" + t);
                copiedRace.setPhysicsModels(Gender.FEMALE, "Actors" + File.separator + "Character" + File.separator + "DefaultFemale" + ".h" + s);
                copiedRace.setMorphRace(r.getForm());
                copiedRace.setArmorRace(r.getForm());
            }
        });
    }

    public void AttachNPCsToRaces() throws Exception {
        SPProgressBarPlug.setStatus("Attaching all Female NPCs to new animation races");
        RBS_NPC.ListNPCFemale.stream().filter((n) -> (n.getWeight() < 60)).forEach((n) -> {
            String bodyID = RBS_Randomize.createRandomID(n.getEDID(), 1, RBS_Animation.amountOfAnimationFolders);
            String sourceName = SkyProcStarter.merger.getRaces().get(new FormID(n.getRace())).getEDID() + "RBS_F" + bodyID;
            for (RACE r : SkyProcStarter.patch.getRaces()) {
                if (r.getEDID().equals(sourceName)) {
                    n.setRace(r.getForm());
                }
            }
        });
    }

    public void AddToRacesHandsAndFeet(String folder) throws Exception {
        List<ARMA> ListRBSHandsAndFeet = new ArrayList<>();
        SPProgressBarPlug.setStatus("Adding entries for rRaces to hand and feet");
        RBS_ARMA.ListVanillaAA.stream().filter((a) -> (a.getEDID().contains("NakedHand") || a.getEDID().contains("NakedFeet") || a.getEDID().contains("NakedTail"))).filter((a) -> (a.getModelPath(Gender.MALE, Perspective.THIRD_PERSON).toLowerCase().contains("character\\character assets") || a.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase().contains("character\\character assets"))).filter((a) -> (!a.getEDID().toLowerCase().contains("child"))).forEach((a) -> {
            ListRBSHandsAndFeet.add(a);
        });
        ListRBSHandsAndFeet.stream().map((a) -> {
            for (RACE r : SkyProcStarter.patch.getRaces()) {
                a.addAdditionalRace(r.getForm());
            }
            return a;
        }).forEach((a) -> {
            SkyProcStarter.patch.addRecord(a);
        });
    }

    public void raceChangeFemaleSkeletons() throws Exception {

        ListRBSRacesMerger.stream().forEach((r) -> {
            try {
                if (r.getModel(Gender.FEMALE).contains("skeleton_female")) {
                    String ID = RBS_Randomize.toString(r.getFormStr(), 1, 6);
                    r.setModel(Gender.FEMALE, "RBS\\Skeletons\\skeleton_female_RBS" + ID + ".nif");
                    SkyProcStarter.patch.addRecord(r);
                }
            } catch (Exception ex) {
                SPGlobal.logException(ex);
            }
        });
    }
}
