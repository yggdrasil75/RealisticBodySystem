Func OpenBodySlide()
   Run($BodySlidePath,@ScriptDir)
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

Func CreateBodies($folder, $filter, $counter=1)

   $failed = false
   ControlClick("Caliente", "", "[CLASS:wxWindowNR; INSTANCE:8]", "left",2)
   ControlClick("Caliente", "", "[CLASS:Edit; INSTANCE:2]", "left",2)
   Send($filter)
   ControlClick("Caliente", "", "[CLASS:ComboBox; INSTANCE:2]", "left",2)
   send ("{home}")
   sleep(2000)
   For $AmountBodies = $counter to $amountBodyTypes Step 1
	  DirRemove(@ScriptDir & "\meshes",1)
	  sleep(400)
	  ControlClick("Caliente", "", "[CLASS:ComboBox; INSTANCE:2]", "left",2)
	  sleep(400)
	  if ($failed) Then
		 $counter = $counter -1
		 else
			send ("{down}")
	  endif
	  sleep(1000)
	  ControlClick("Caliente", "", "[CLASS:Button; INSTANCE:3]", "left",1)
	  WinWait("Batch")
	  ControlClick("Batch", "", "[CLASS:Button; INSTANCE:1]", "left",1)
	  $bodyName = "RBS" & StringFormat ("%03d", $AmountBodies)
	  if ($folder = "body") Then
		 $targetPathActor = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\actors\character\character assets";
		 DirRemove($targetPathActor,1)
		 DirCreate($targetPathActor)
	  else
		 $targetPathClothes = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\clothes";
		 $targetPathArmor = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\armor";
		 $targetPathDLC01 = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\dlc01";
		 $targetPathDLC02 = $MeshesPath & "\RBS\female\"& $bodyName &"\" & $folder & "\dlc02";

		 DirRemove($targetPathClothes,1)
		 DirRemove($targetPathArmor,1)
		 DirRemove($targetPathDLC01,1)
		 DirRemove($targetPathDLC02,1)

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
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & @ScriptDir &"\meshes\actors\character\character assets"" """ & $targetPathActor &""" /e /s /v /c /Y")
	  else
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & @ScriptDir &"\meshes\clothes"" """ & $targetPathClothes & """ /e /s /v /c /Y")
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & @ScriptDir &"\meshes\armor"" """ & $targetPathArmor & """ /e /s /v /c /Y")
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & @ScriptDir &"\meshes\dlc01"" """ & $targetPathDLC01 & """ /e /s /v /c /Y")
		 RunWait(@ComSpec & " /C"&' '&"xcopy """ & @ScriptDir &"\meshes\dlc02"" """ & $targetPathDLC02 & """ /e /s /v /c /Y")
	  endif
   Next
EndFunc

func createList()
   ;Local $FileList = _FileListToArray(@ScriptDir & "\ShapeData")
   Local $FileList = _FileListToArray(@ScriptDir & "\SliderSets")
   If @error = 1 Then
	  MsgBox(0, "", "Keine Ordner gefunden.")
	  Exit
   EndIf
   If @error = 4 Then
	  MsgBox(0, "", @ScriptDir & "\ShapeData")
	  Exit
   EndIf
   Local $file = FileOpen(@ScriptDir & "\SliderGroups\RBS_Group.xml", 2)

   FileWriteLine($file, "<?xml version=""1.0"" encoding=""utf-8""?>")
   FileWriteLine($file, "<SliderGroups>")
   FileWriteLine($file, "<Group name=""RBS"">")
   for $i = 1 to $FileList[0]
	  $StringFile = FileRead(@ScriptDir & "\SliderSets\" & $FileList[$i])
	  if (StringInStr ($FileList[$i],"CT77")) then
		 FileDelete(@ScriptDir & "\SliderSets\" & $FileList[$i])
		 $StringFileNew = StringRegExpReplace ( $StringFile, "(?i)<OutputFile>CT77(.*?)</OutputFile>", "<OutputFile>$1</OutputFile>")
		 FileWrite(@ScriptDir & "\SliderSets\" & $FileList[$i],$StringFileNew)
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
   _XMLFileOpen("Config.xml")
   _XMLUpdateField("BodySlideConfig/GameDataPath",  @ScriptDir & "\")
EndFunc

Func cleanBodySlideDirectory()

   Local $sDrive = "", $sDir = "", $sFilename = "", $sExtension = ""
   Local $aPathSplit = _PathSplit(@ScriptFullPath, $sDrive, $sDir, $sFilename, $sExtension)
   RunWait(@ComSpec & " /c"&' '& $sDrive &": & cd """& @ScriptDir & "\sources\ShapeData\"" & FOR /d %a in (wav-TBBP*) DO XCOPY ""%a"" """ & @ScriptDir & "\ShapeData\%a""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '& $sDrive &": & cd """& @ScriptDir & "\sources\ShapeData\"" & FOR /d %a in (CT77*) DO XCOPY ""%a"" """ & @ScriptDir & "\ShapeData\%a""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '& $sDrive &": & cd """& @ScriptDir & "\sources\ShapeData\"" & FOR /d %a in (CB++*) DO XCOPY ""%a"" """ & @ScriptDir & "\ShapeData\%a""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& @ScriptDir & "\sources\ShapeData\CalienteBody"" """ & @ScriptDir &"\ShapeData\CalienteBody""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& @ScriptDir & "\sources\ShapeData\Caliente Undies"" """ & @ScriptDir &"\ShapeData\Caliente Undies""  /E /I /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& @ScriptDir & "\sources\ShapeData\CalienteNeverNude"" """ & @ScriptDir &"\ShapeData\CalienteNeverNude""  /E /I /Y")
   ;RunWait(@ComSpec & " /c"&' '&"xcopy """& $bodyslidePath & "sources\SliderGroups\OCR-All_TBBP.xml"" " & $bodyslidePath &"""SliderGroups\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& @ScriptDir & "\sources\SliderGroups\CBAdvancedGroups.xml"" """ & @ScriptDir &"\SliderGroups\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& @ScriptDir & "\sources\SliderSets\wav-TBBP*.*"" """ & @ScriptDir &"\SliderSets\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& @ScriptDir & "\sources\SliderSets\CB++*.*"" """ & @ScriptDir & "\SliderSets\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& @ScriptDir & "\sources\SliderSets\CT77*.*"" """ & @ScriptDir & "\SliderSets\"" /e /s /v /c /Y")
   RunWait(@ComSpec & " /c"&' '&"xcopy """& @ScriptDir & "\sources\SliderSets\CalienteSets.xml"" """ & @ScriptDir &"\SliderSets\"" /e /s /v /c /Y")
EndFunc