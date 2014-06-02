#include <Misc.au3>
#include <File.au3>
#include <bodyslide2.au3>
#include <create_presets.au3>
#include <mainFunctions.au3>
#include <_XMLDomWrapper.au3>


; TODO TExture path anpassen für alle rassen torso Hands an Feet. Schwarze haut bug fixen
;generateAAMeshesTBBPList()
;global $skyrimPath="c:\spiele\skyrim\Data\"

global $MeshesPath = _PathFull (@ScriptDir&"\..\..\..\meshes")
global $BodySlidePath = _PathFull (@ScriptDir&"\..\..\tools\Bodyslide")
global $BodySlideSourcesPath = _PathFull (@ScriptDir&"\..\..\tools\Bodyslide\sources")
SetGameDataPath()
;global $MeshesPath = "S:\Mod Organizer\mods\RBS\meshes"
;DirRemove($MeshesPath,1)
global $amountBodyTypes = 30
global $ArmorNumber = 21
global $bodyTypeNumber = 1
global $bodyMultiLow = 0.7
global $bodyMultiHigh = 0.6
global $windowName = "Caliente's BodySlide"
;PrepareCK(1)
;global $arr_races = StringSplit("ArgonianRace,BretonRace,DarkElfRace,HighElfRace,ImperialRace,KhajiitRace,NordRace,OrcRace,RedguardRace,WoodElfRace", ",")
global $arr_races = StringSplit("BretonRace,DarkElfRace,HighElfRace,ImperialRace,NordRace,OrcRace,RedguardRace,WoodElfRace", ",") ; only human
;ChangeSkeleton()
;cleanBodySlideDirectory()
;createList();
;createPresetFiles()
;Dircopy ("SliderPresets", $bodyslidePath & "SliderPresets",1)
global $pos = OpenBodySlide()
;createBodies("body","CalienteBodyAdvanced TBBP")

createBodies("standard","TBBP")
createBodies("ct77","CT77")
WinClose ("Caliente's BodySlide")



;ControlSend("Caliente", "", "[CLASS:Edit; INSTANCE:2]", "TBBP")



;Dircopy ("SliderPresets", "ct77\SliderPresets",1)
;Dircopy ("SliderPresets", "bodies\SliderPresets",1)
;Dircopy ("SliderPresets", "killerkeo\SliderPresets",1)

#cs

global $pathToExe = $skyrimPath & "RBS\BodySlide\ct77\BodySlide.exe"
global $path = $skyrimPath & "RBS\BodySlide\ct77\"
global $folder = "ct77";
CreateBodies(2)
sleep(5000)

global $pathToExe = $skyrimPath & "RBS\BodySlide\vanilla\BodySlide.exe"
global $path = $skyrimPath & "RBS\BodySlide\vanilla\"
global $folder = "standard";
CreateBodies(2)
sleep(5000)

global $pathToExe = $skyrimPath & "RBS\BodySlide\killerkeo\BodySlide.exe"
global $path = "$skyrimPath & "RBS\BodySlide\killerkeo\"
global $folder = "killerkeo";
CreateBodies(2)
sleep(5000)

global $pathToExe = "c:\spiele\skyrim\Data\RBS\BodySlide\bodies\BodySlide.exe"
global $path = "c:\spiele\skyrim\Data\RBS\BodySlide\bodies\"
global $folder = "body";
CreateBodies(2)

for $copynum = 1 to $amountBodyTypes STEP 1
  $bodyName = "RBS" & StringFormat ("%03d",  $copynum)

 FileCopy ($skyrimPath & "RBS\HandsAndFeet\*.nif",$skyrimPath & "meshes\RBS\female\" & $bodyName &"\body\actors\character\character assets\",0)
 FileCopy ($skyrimPath & "RBS\HandsAndFeet\*.nif",$skyrimPath & "meshes\RBS\female\" & $bodyName &"\standard\actors\character\character assets\",0)
 FileCopy ($skyrimPath & "RBS\HandsAndFeet\*.nif",$skyrimPath & "meshes\RBS\female\" & $bodyName &"\ct77\actors\character\character assets\",0)
 FileCopy ($skyrimPath & "RBS\HandsAndFeet\*.nif",$skyrimPath & "meshes\RBS\female\" & $bodyName &"\killerkeo\actors\character\character assets\",0)
;  FileCopy ("S:\skyrim\data\meshes\actors\character\character assets\femaleha*.nif","S:\skyrim\data\meshes\RBS\female\" & $bodyName &"\standard\actors\character\character assets\",1)
;  FileCopy ("S:\skyrim\data\meshes\actors\character\character assets\femaletailargonian.nif","S:\skyrim\data\meshes\RBS\female\" & $bodyName &"\standard\actors\character\character assets\",1)
;  FileCopy ("S:\skyrim\data\meshes\actors\character\character assets\femaletailkhajiit.nif","S:\skyrim\data\meshes\RBS\female\" & $bodyName &"\standard\actors\character\character assets\",1)
;  FileCopy ("S:\skyrim\data\meshes\actors\character\character assets\*.nif","S:\skyrim\data\meshes\RBS\female\" & $bodyName &"\skimpy\actors\character\character assets\",0)
;  FileCopy ("S:\skyrim\data\meshes\actors\character\character assets\femaleha*.nif","S:\skyrim\data\meshes\RBS\female\" & $bodyName &"\skimpy\actors\character\character assets\",1)
;  FileCopy ("S:\skyrim\data\meshes\actors\character\character assets\femaletailargonian.nif","S:\skyrim\data\meshes\RBS\female\" & $bodyName &"\skimpy\actors\character\character assets\",1)
;  FileCopy ("S:\skyrim\data\meshes\actors\character\character assets\femaletailkhajiit.nif","S:\skyrim\data\meshes\RBS\female\" & $bodyName &"\skimpy\actors\character\character assets\",1)
#ce