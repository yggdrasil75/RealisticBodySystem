/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package skyprocstarter;

import skyproc.MajorRecord;
import skyproc.STAT;

/**
 *
 * @author test
 */
public class RBS_Statics {

    public void VFD() {
        for (STAT statics : SkyProcStarter.merger.getStatics()) {
            statics.set(MajorRecord.MajorFlags.VisibleWhenDistant, true);
            statics.set(MajorRecord.MajorFlags.valueOf("NeverFades"), true);
            SkyProcStarter.patch.addRecord(statics);
        }
    }
}
