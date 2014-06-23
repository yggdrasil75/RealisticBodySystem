
package skyprocstarter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import skyproc.FLST;
import skyproc.FormID;
import skyproc.ModListing;
import skyproc.NPC_;
import skyproc.NiftyFunc;
import skyproc.QUST;
import skyproc.RecordShrinkArray;
import skyproc.SPEL;
import skyproc.SPImporter;
import skyproc.SPImporter.DirtyParsingIterator;
import skyproc.ScriptRef;
import skyproc.gui.SPProgressBarPlug;

public class RBS_Quest {
 
    public void addQuest() throws Exception {
        
        List<FormID> achrFormList = new ArrayList<>();
        List<ModListing> ModList;
        FormID[] form;
        SPProgressBarPlug.setStatus("Getting ACHRs");
        FLST formList = new FLST("form1");
        Set<FormID> formSetACHR = new HashSet<>();
        Set<FormID> formSetNPC = new HashSet<>();
        ModList = SPImporter.getActiveModList();
        for (int i = 0; i < ModList.size(); i++) {
            DirtyParsingIterator ACHRIter = SPImporter.getSubRecordsInGRUPs(ModList.get(i), "ACHR", "CELL");
            while (ACHRIter.hasNext()) {
                RecordShrinkArray nextACHR = ACHRIter.next();
                nextACHR.skip(4); // Major record flags
                FormID ACHRID = nextACHR.extractFormID(ACHRIter.activeMod());
                FormID Name = nextACHR.extractFormID("NAME", ACHRIter.activeMod());
                if (RBS_NPC.ListNPCFemalePatched.contains(Name)) {
                    achrFormList.add(ACHRID);
                }
            }
        }
        FormID[] formArray = achrFormList.toArray(new FormID[0]);
        formList.addAll(formSetACHR);
        // creating the RBSCorrect Script and Quest
        ScriptRef RBSCorrectNPCsScript = new ScriptRef("RBSCorrectNPCsScript");
        RBSCorrectNPCsScript.setProperty("RBSFormList", formArray);
        //RBSCorrectNPCsScript.setProperty("RBSFormList", formList.getForm());
        QUST RBSCorrectNPCsQuest;
        
        RBSCorrectNPCsQuest = NiftyFunc.makeScriptQuest(RBSCorrectNPCsScript);
        RBSCorrectNPCsQuest.setName("RBSCorrectNPCsQuest");
        
        //creating the SkyUI Script and Quest
        ScriptRef RBSSkyUIScript = new ScriptRef("RBSSkyUIScript");
        RBSSkyUIScript.setProperty("RBSCorrectNPCsProperty",RBSCorrectNPCsQuest.getForm());
        QUST RBSSkyUIQuest = NiftyFunc.makeScriptQuest(RBSSkyUIScript);
        // here should be something like
        // RBSSkyUIQuest.setAlias(ID=0,Name="PlayerAlias",ALFR(FormIDofPlayer))
        // The player alias has to be attached to the Quest that displays the SkyUI menu of RBS, this way it will always run.
        // it is part of the tutorial on: https://github.com/schlangster/skyui/wiki/MCM-Quickstart
        
        ScriptRef script = new ScriptRef("RBSSkyUIInitSpell");
        script.setProperty("RBSSkyUIQuestProperty", RBSSkyUIQuest.getForm());
        for (NPC_ n : SkyProcStarter.merger.getNPCs()) {
            if (n.getEDID().equals("Player")) {
                n.addSpell(NiftyFunc.genScriptAttachingSpel(script, "RBSSkyUIInitSpell").getForm());
                SkyProcStarter.patch.addRecord(n);
            }
        }
        
    }

    public void addQuestnew() throws Exception {
        SPProgressBarPlug.setStatus("Shitting in the hole");
        ScriptRef RBSCorrectNPCs = new ScriptRef("RBSNPCCorrection");
        SPEL CorrectNPCs = NiftyFunc.genScriptAttachingSpel(RBSCorrectNPCs, "RBSNPCCorrection");
    }

}
