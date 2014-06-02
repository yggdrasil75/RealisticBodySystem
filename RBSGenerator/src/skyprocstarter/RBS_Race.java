package skyprocstarter;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import skyproc.ARMA;
import skyproc.FormID;
import skyproc.NPC_;
import skyproc.RACE;
import skyproc.SPGlobal;
import skyproc.genenums.Gender;
import skyproc.genenums.Perspective;
import skyproc.gui.SPProgressBarPlug;

public class RBS_Race {

    public static Map<String, FormID> patchRacesMapKeyEDID = new HashMap<>();
    public static Map<FormID, String> patchRacesMapKeyForm = new HashMap<>();
    public static ArrayList<RACE> ListRBSRacesMerger = new ArrayList<>();
    public static Map<String, FormID> vanillaRacesMapKeyEDID = new HashMap<>();
    public static Map<FormID, String> vanillaRacesMapKeyForm = new HashMap<>();
    public static ArrayList<FormID> ListRBSRacesPatchFormID = new ArrayList<>();
    public static ArrayList<RACE> ListRBSRacesPatch = new ArrayList<>();
    public static int amountFilesHKX;

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
        ArrayList<ARMA> ListAABeast = new ArrayList<>();
        ArrayList<String> ListMaleRacesEDID = new ArrayList<>();
        ArrayList<FormID> ListMaleRacesFormID = new ArrayList<>();
        for (ARMA sourceAA : RBS_ARMA.ListVanillaAA) {
            //if (sourceAA.getEDID().contains("Argonian") || sourceAA.getEDID().contains("Khajiit")) {
            ListAABeast.add(sourceAA);
            // }
        }

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
        String path1 = SPGlobal.pathToData
                + "meshes"
                + File.separator
                + "Actors"
                + File.separator
                + "Character"
                + File.separator
                + "characters female"
                + File.separator;

        String path2 = SPGlobal.pathToData
                + "meshes"
                + File.separator
                + "Actors"
                + File.separator
                + "Character"
                + File.separator;

        RBS_Race.amountFilesHKX = RBS_File.countFiles(path2,".hkx", "defaultfemale.h");
       
        for (RACE r : ListRBSRacesMerger) {
               if (r.getPhysicsModels(Gender.FEMALE).toLowerCase().contains("defaultfemale.hkx")) {
                for (int i = 1; i < RBS_Race.amountFilesHKX + 1; i++) {
                    String s = formatter.format(i);
                    String t = formatter2.format(i);
                    RACE copiedRace = (RACE) SkyProcStarter.patch.makeCopy(r, r.getEDID() + "RBS_F" + t);
                    copiedRace.setPhysicsModels(Gender.FEMALE, "Actors" + File.separator + "Character" + File.separator + "DefaultFemale" + ".h" + s);
                    copiedRace.setMorphRace(r.getForm());
                    copiedRace.setArmorRace(r.getForm());
                }
            }
        }
    }

    public void AttachNPCsToRaces() throws Exception {
        SPProgressBarPlug.setStatus("Attaching all Female NPCs to new animation races");
        for (NPC_ n : RBS_NPC.ListNPCFemale) {
            if (n.getWeight()<60) {
            String bodyID = RBS_Randomize.createRandomID(n.getEDID(), 1, RBS_Race.amountFilesHKX);
            String sourceName = SkyProcStarter.merger.getRaces().get(new FormID(n.getRace())).getEDID() + "RBS_F" + bodyID;
            for (RACE r : SkyProcStarter.patch.getRaces()) {
                if (r.getEDID().equals(sourceName)) {
                    n.setRace(r.getForm());
                }
            }
            }
        }
    }

    public void AddToRacesHandsAndFeet(String folder) throws Exception {
        ArrayList<ARMA> ListRBSHandsAndFeet = new ArrayList<>();
        SPProgressBarPlug.setStatus("Adding entries for rRaces to hand and feet");
        for (ARMA a : RBS_ARMA.ListVanillaAA) {
            if (a.getEDID().contains("NakedHand") || a.getEDID().contains("NakedFeet") || a.getEDID().contains("NakedTail")) {
                if (a.getModelPath(Gender.MALE, Perspective.THIRD_PERSON).toLowerCase().contains("character\\character assets") || a.getModelPath(Gender.FEMALE, Perspective.THIRD_PERSON).toLowerCase().contains("character\\character assets")) {
                    if (!a.getEDID().toLowerCase().contains("child")) {
                        ListRBSHandsAndFeet.add(a);
                    }
                }
            }
        }
        for (ARMA a : ListRBSHandsAndFeet) {
            for (RACE r : SkyProcStarter.patch.getRaces()) {
                a.addAdditionalRace(r.getForm());
            }
            SkyProcStarter.patch.addRecord(a);
        }
    }

    public void raceChangeFemaleSkeletons() throws Exception {

        for (RACE r : ListRBSRacesMerger) {
            try {
                if (r.getModel(Gender.FEMALE).contains("skeleton_female")) {
                    String ID = RBS_Randomize.toString(r.getFormStr(), 1, 6);
                    r.setModel(Gender.FEMALE, "RBS\\Skeletons\\skeleton_female_RBS" + ID + ".nif");
                    SkyProcStarter.patch.addRecord(r);
                }
            } catch (Exception ex) {
                SPGlobal.logException(ex);
            }
        }
    }
}