Func OpenBodySlide()
   Run($BodySlidePath &"\bodyslide.exe",$BodySlidePath)
   sleep(3000)
   WinSetState ( "Caliente", "", @SW_MAXIMIZE )
   sleep (1000)
   WinActivate ($windowName)
 ;  sleep (200)
  ; WinSetState("BodySlide", "", @SW_MAXIMIZE)
   sleep (100)
   Opt("WinTitleMatchMode", 2)
   return WinGetPos($windowName)
EndFunc

Func SelectGroupFilter($preset)
   MouseClick("left", $pos[0] + 50, $pos[1] + 60,1,1)
   Send("{down " & $preset & "}")
   send ("{ENTER}")
EndFunc

Func SelectSliderset()
   MouseClick("left", $pos[0] + 50, $pos[1] + 120,1,1)
   Send("{down}")
   send ("{ENTER}")
EndFunc

Func ClickBuild()
	  MouseClick("left", $pos[0] + 50, $pos[1] + 258,1,1)
	  WinWait("Build Multiple Groups...")
	  send ("{down}")
	  send ("{space}")
      send ("{ENTER}")
EndFunc

Func _2WinWait ($FirstTitle,$SecondTitle,$FirstText = "" ,$SecondText = "" )
    If $FirstTitle = "" Or $SecondTitle = "" Then
        Return 0
    Else
        Do
        Until WinExists ($FirstTitle,$FirstText) Or WinExists ($SecondTitle,$SecondText)
    EndIf
EndFunc

Func CreateBodies($folder, $filter, $text, $step, $counter=1)
   _GUIChangeText("Starting BodySlide Functions")
   $failed = false
   ControlClick("Caliente", "", "[CLASS:wxWindowNR; INSTANCE:8]", "left",2)
   ControlClick("Caliente", "", "[CLASS:Edit; INSTANCE:2]", "left",2)
   Send($filter)
   ControlClick("Caliente", "", "[CLASS:ComboBox; INSTANCE:2]", "left",2)
   send ("{home}")
   sleep(2000)
   For $AmountBodies = $counter to $amountBodyTypes Step 1
	  _GUIChangeText("Step " & $step & "/6 " & " - Creating " & $text &  $AmountBodies & "/" & $amountBodyTypes )
	  DirRemove($BodySlidePath & "\meshes",1)
	  sleep(400)
	  ControlClick("Caliente", "", "[CLASS:ComboBox; INSTANCE:2]", "left",2)
	  sleep(400)
	  if ($failed) Then
		 $counter = $counter -1
		 else
			send ("{down}")
	  endif
	  sleep(1000)
	  ControlClick("Caliente", "", "[CLASS:Button; TEXT:Batch Build...;]", "left",1)
	  WinWait("Batch")
	  ControlClick("Batch", "", "[CLASS:Button; INSTANCE:1]", "left",1)
	  $bodyName = "RBS" & StringFormat ("%03d", $AmountBodies)
	  if ($folder = "body") Then
		 $targetPathActor = $MeshesPath & "\actors\character\character assets\RBS\female\" & $bodyName;
		 DirRemove($targetPathActor,1)
		 DirCreate($targetPathActor)
	  else
		 $targetPathClothes = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\clothes";
		 $targetPathArmor = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\armor";
		 $targetPathDLC01 = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\dlc01";
		 $targetPathDLC02 = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\dlc02";

;		 DirRemove($targetPathClothes,1)
;		 DirRemove($targetPathArmor,1)
;		 DirRemove($targetPathDLC01,1)
;		 DirRemove($targetPathDLC02,1)

		 DirCreate($targetPathClothes)
		 DirCreate($targetPathArmor)
		 DirCreate($targetPathDLC01)
		 DirCreate($targetPathDLC02)
	  endif

	  Do
		 $var1 = 0
		 If  WinGetState("Complete","")    Then
			$var1 = 1
			$failed = false
			sleep(1000)
			ControlClick("Complete", "", "[CLASS:Button; INSTANCE:1]", "left",1)

		 ElseIf  WinGetState("Failed","")   Then
			$var1 = 1
			$failed = true
			sleep(1000)
			ControlClick("Failed", "", "[CLASS:Button; INSTANCE:1]", "left",1)
			sleep(1000)
		 EndIf
	  Until $var1 = 1

	  if ($folder = "body") Then
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & $BodySlidePath &"\meshes\actors\character\character assets"" """ & $targetPathActor &""" /e /s /v /c /Y")
	  else
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & $BodySlidePath &"\meshes\clothes"" """ & $targetPathClothes & """ /e /s /v /c /Y")
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & $BodySlidePath &"\meshes\armor"" """ & $targetPathArmor & """ /e /s /v /c /Y")
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & $BodySlidePath &"\meshes\dlc01"" """ & $targetPathDLC01 & """ /e /s /v /c /Y")
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & $BodySlidePath &"\meshes\dlc02"" """ & $targetPathDLC02 & """ /e /s /v /c /Y")
	  endif
   Next
EndFunc

func createList()
   ;Local $FileList = _FileListToArray(@ScriptDir & "\ShapeData")
   Local $FileList = _FileListToArray($BodySlideSourcesPath & "\SliderSets")

   If @error = 1 Then
	  MsgBox(0, "", "Keine Ordner gefunden.")
	  Exit
   EndIf
   If @error = 4 Then
	  MsgBox(0, "", $BodySlidePath & "\ShapeData")
	  Exit
   EndIf
   Local $file = FileOpen($BodySlidePath & "\SliderGroups\RBS_Group.xml", 2)
   FileWriteLine($file, "<?xml version=""1.0"" encoding=""utf-8""?>")
   FileWriteLine($file, "<SliderGroups>")
   FileWriteLine($file, "<Group name=""RBS"">")
   for $i = 1 to $FileList[0]
	  $StringFile = FileRead($BodySlideSourcesPath & "\SliderSets\" & $FileList[$i])
	  if (StringInStr ($FileList[$i],"CT77")) then
		 FileDelete($BodySlidePath & "\SliderSets\" & $FileList[$i])
		 $StringFileNew = StringRegExpReplace ( $StringFile, "(?i)<OutputFile>CT77(.*?)</OutputFile>", "<OutputFile>$1</OutputFile>")
		 FileWrite($BodySlidePath & "\SliderSets\" & $FileList[$i],$StringFileNew)
		 $arrayNodes = StringRegExp($StringFileNew, '(?i)SliderSet name="(.*?)">',3)
	  else
		 $arrayNodes = StringRegExp($StringFile, '(?i)SliderSet name="(.*?)">',3)
	  endif
	  for $z = 0 to Ubound($arrayNodes)-1
		 FileWriteLine($file, "<Member name=""" & $arrayNodes[$z] & """ />")
	  next
   next
   FileWriteLine($file, "</Group>")
   FileWriteLine($file, "</SliderGroups>")
   FileClose ($file)
EndFunc

Func SetGameDataPath()
   _XMLFileOpen($BodySlidePath & "\Config.xml")
   _XMLUpdateField("BodySlideConfig/GameDataPath", _PathFull (@ScriptDir&"\..\..\tools\Bodyslide") & "\")
EndFunc

Func cleanBodySlideDirectory()
   Local $sDrive = "", $sDir = "", $sFilename = "", $sExtension = ""
   Local $aPathSplit = _PathSplit(@ScriptFullPath, $sDrive, $sDir, $sFilename, $sExtension)

   RunWait(@ComSpec & " /c"&' '& $sDrive &" & cd """& $BodySlideSourcesPath & "\ShapeData\"" & FOR /d %a in (wav-TBBP*) DO XCOPY ""%a"" """ & $bodySlidePath & "\ShapeData\%a""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '& $sDrive &" & cd """& $BodySlideSourcesPath & "\ShapeData\"" & FOR /d %a in (CT77*) DO XCOPY ""%a"" """ & $bodySlidePath & "\ShapeData\%a""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '& $sDrive &" & cd """& $BodySlideSourcesPath & "\ShapeData\"" & FOR /d %a in (CB++*) DO XCOPY ""%a"" """ & $bodySlidePath & "\ShapeData\%a""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '& $sDrive &" & cd """& $BodySlideSourcesPath & "\ShapeData\"" & FOR /d %a in (cal*) DO XCOPY ""%a"" """ & $bodySlidePath & "\ShapeData\%a""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\ShapeData\CalienteBody"" """ & $bodySlidePath &"\ShapeData\CalienteBody""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\ShapeData\killerkeo"" """ & $bodySlidePath &"\ShapeData\killerkeo""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\ShapeData\Caliente Undies"" """ & $bodySlidePath &"\ShapeData\Caliente Undies""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\ShapeData\CalienteNeverNude"" """ & $bodySlidePath &"\ShapeData\CalienteNeverNude""  /E /I /Y")
   ;RunWait(@ComSpec & " /c"&' '&"xcopy """& $bodyslidePath & "sources\SliderGroups\OCR-All_TBBP.xml"" " & $bodyslidePath &"""SliderGroups\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\SliderGroups\CBAdvancedGroups.xml"" """ & $bodySlidePath &"\SliderGroups\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\SliderSets\wav-TBBP*.*"" """ & $bodySlidePath &"\SliderSets\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\SliderSets\CB++*.*"" """ & $bodySlidePath & "\SliderSets\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\SliderSets\CT77*.*"" """ & $bodySlidePath & "\SliderSets\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& $BodySlideSourcesPath & "\SliderSets\CalienteSets.xml"" """ & $bodySlidePath &"\SliderSets\"" /e /s /v /c /Y")
EndFunc

Func CreateListGeneratedMeshes()
   $array = _FileListToArrayRec ($MeshesPath,"*.nif",1,1,0,2)
   For $i = 0 To UBound($array) - 1
	  $array[$i] = StringLower (StringReplace($array[$i], $modPath, ""))

   Next
   ;FileDelete(@ScriptDir & "\ListGeneratedMeshes.txt")
   _FileWriteFromArray ($SourcesPath & "\ListGeneratedMeshes.txt", $array)
EndFunc
