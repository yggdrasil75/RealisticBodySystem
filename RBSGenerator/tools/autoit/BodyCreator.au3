#include <Misc.au3>
#include <File.au3>
#include <bodyslide2.au3>
#include <create_presets.au3>
#include <mainFunctions.au3>
#include <_XMLDomWrapper.au3>

global $MeshesPath = _PathFull (@ScriptDir&"\..\..\..\meshes")
global $BodySlidePath = _PathFull (@ScriptDir&"\..\..\tools\Bodyslide")
global $BodySlideSourcesPath = _PathFull (@ScriptDir&"\..\..\tools\Bodyslide\sources")
global $modPath = _PathFull (@ScriptDir&"\..\..\..\")
global $sourcesPath = _PathFull (@ScriptDir&"\..\..\..\RBSGenerator\sources")
global $hkxcmdPath = _PathFull (@ScriptDir&"\..\..\..\RBSGenerator\tools\")
global $SkyrimPath = _PathFull (@ScriptDir&"\..\..\..\")
global $amountBodyTypes = 30
global $ArmorNumber = 21
global $bodyTypeNumber = 1
global $bodyMultiLow = 0.65
global $bodyMultiHigh = 0.92
global $windowName = "Caliente's BodySlide"

DirCreate($BodySlidePath & "\MeshesTemp")
FileCopy ($hkxcmdPath & "\hkxcmd.exe", $SkyrimPath ,1 )
SetGameDataPath()
cleanBodySlideDirectory()
createList();
Switch MsgBox(3, "BodyCreator", "Do want to generate new bodyshapes? (previous will be overwritten)")
    Case 6
	  createPresetFiles()
    Case 7
        $var = "NO"
    Case 2
        Exit
	 EndSwitch
global $pos = OpenBodySlide()
createBodies("killerkeo","keo")
createBodies("body","CalienteBodyAdvanced TBBP")
createBodies("standard","CalArmor")
createBodies("standard","CalClothes")
createBodies("ct77","CT77")
createBodies("standard","wav-TBBP")
WinClose ("Caliente's BodySlide")
CreateListGeneratedMeshes()

