#include <Misc.au3>
#include <File.au3>
#include <bodyslide2.au3>
#include <create_presets.au3>
#include <mainFunctions.au3>
#include <_XMLDomWrapper.au3>
#include <GUIConstantsEx.au3>
#include <WindowsConstants.au3>
#include <EditConstants.au3>
#include <gui.au3>
global $MeshesPath = _PathFull (@ScriptDir&"\..\..\..\meshes")
global $BodySlidePath = _PathFull (@ScriptDir&"\..\..\tools\Bodyslide")
global $BodySlideSourcesPath = _PathFull (@ScriptDir&"\..\..\tools\Bodyslide\sources")
global $modPath = _PathFull (@ScriptDir&"\..\..\..\")
global $sourcesPath = _PathFull (@ScriptDir&"\..\..\..\RBSGenerator\sources")
global $hkxcmdPath = _PathFull (@ScriptDir&"\..\..\..\RBSGenerator\tools\")
global $SkyrimPath = _PathFull (@ScriptDir&"\..\..\..\..\")
global $amountBodyTypes = 30
global $ArmorNumber = 21
global $bodyTypeNumber = 1
global $bodyMultiLow = 0.65
global $bodyMultiHigh = 0.92
global $windowName = "Caliente's BodySlide"

_GUICreate()
_CreateLabel()
_GUIChangeText("Creating MeshesTemp folder")
DirCreate($BodySlidePath & "\MeshesTemp")
;FileCopy ($hkxcmdPath & "\hkxcmd.exe", $SkyrimPath ,1 )
_GUIChangeText("change Path to Skyrim in Bodyslide Config")
SetGameDataPath()
_GUIChangeText("Cleaning Folders and copying sources")
cleanBodySlideDirectory()
_GUIChangeText("Creating RBS_Group.xml for BodySlide")
createList();
_GUIChangeText("Waiting for user")
Switch MsgBox(3, "BodyCreator", "Do want to generate new bodyshapes? (previous will be overwritten)")
    Case 6
	  createPresetFiles()
    Case 7
        $var = "NO"
    Case 2
        Exit
	 EndSwitch
_GUIChangeText("Opening BodySlide")
global $pos = OpenBodySlide()
createBodies("killerkeo","keo","Killerkeos Skimpy Armor and Clothes",1)
createBodies("body","CalienteBodyAdvanced TBBP" ,"Naked Body",2)
createBodies("standard","CalArmor", "Calientes default Armors",3)
createBodies("standard","CalClothes", "Calientes default Clothes",4)
createBodies("ct77","CT77", "CT77s Skimpy Armor and Clothes",5)
createBodies("standard","wav-TBBP", "Wavions default Armor and Clothes",6)
WinClose ("Caliente's BodySlide")
_GUIChangeText("Creating list of generated meshes for RBS Patcher")
CreateListGeneratedMeshes()

