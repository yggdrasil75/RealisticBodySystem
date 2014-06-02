#include <File.au3>
#include <array.au3>
$array = _FileListToArrayRec ( @ScriptDir,"*.nif",1,1,0,0)
FileDelete(@ScriptDir & "\ListGeneratedMeshes.txt")
_FileWriteFromArray (@ScriptDir & "\ListGeneratedMeshes.txt", $array)
