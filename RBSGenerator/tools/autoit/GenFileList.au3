
Func CreateListGeneratedMeshes()
   $array = _FileListToArrayRec ($MeshesPath,"*.nif",1,1,0,0)
   FileDelete(@ScriptDir & "\ListGeneratedMeshes.txt")
   _FileWriteFromArray (@ScriptDir & "\ListGeneratedMeshes.txt", $array)
EndFunc