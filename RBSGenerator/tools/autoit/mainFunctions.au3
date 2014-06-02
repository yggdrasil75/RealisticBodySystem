Func OpenCK()
   Run("S:\skyrim\CreationKit.exe","S:\skyrim\")
   $win = "Creation Kit"
   WinActivate ( $win)
   sleep (50)
   Opt("WinTitleMatchMode", 2)
   return WinGetPos($win)
EndFunc

Func Randomize ($min,$max,$code=0)
   if $code > 0 then SRandom($code)
   return floor (Random($min,$max))
EndFunc

Hotkeyset("{DEL}", "schliessen")
Func schliessen ()
   Exit
EndFunc

HotKeySet("{F9}", "_pause")
Func _pause()
    Do
        Sleep(100)
    Until _IsPressed("79") ; F10
EndFunc

Func GetWindowRelativePos($win)
   WinActivate ( $win)
 sleep (50)
   Opt("WinTitleMatchMode", 2)
return WinGetPos($win)
EndFunc

func SearchObjectActor ($searchName) 
   WinActivate ("Object Window")
   $pos = GetWindowRelativePos("Object Window")
   MouseClick("left", $pos[0] + 40, $pos[1] + 200,1,1)
   sleep (50)
   Send ("{HOME}")
   sleep (50)
   Send("{+}")
   sleep (50)
   Send("{down}")
   sleep (50)
   Send("+{TAB}")
   sleep (100)
   send ($searchName)
   sleep (500)
EndFunc

func SearchObject ($searchName) 
   WinActivate ("Object Window")
   $pos = GetWindowRelativePos("Object Window")
   MouseClick("left", $pos[0] + 40, $pos[1] + 200,1,1)
   sleep (50)
   Send ("{END}")
   sleep (50)
   Send("+{TAB}")
   sleep (50)
   send ($searchName)
   sleep (50)
EndFunc

func SearchOutfit ($searchName) 
   WinActivate ("Object Window")
   $pos = GetWindowRelativePos("Object Window")
   MouseClick("left", $pos[0] + 40, $pos[1] + 200,1,1)
   sleep (50)
   Send("{HOME}")
   sleep (50)
   Send ("{o}")
   sleep (50)
   Send("+{TAB}")
   sleep (50)
   send ($searchName)
   sleep (50)
EndFunc

func SearchLeveledList ($searchName) 
   WinActivate ("Object Window")
   $pos = GetWindowRelativePos("Object Window")
   MouseClick("left", $pos[0] + 40, $pos[1] + 200,1,1)
   sleep (50)
   Send("{HOME}")
   sleep (50)
   Send ("{l 2}")
   sleep (50)
   Send("+{TAB}")
   sleep (50)
   send ($searchName)
   sleep (50)
EndFunc

func SearchObjectAA ($searchName) 
   WinActivate ("Object Window")
   $pos = GetWindowRelativePos("Object Window")
   MouseClick("left", $pos[0] + 40, $pos[1] + 200,1,1)
   sleep (50)
   Send ("{HOME}")
   sleep (50)
   Send("{a 4}")
   sleep (50)
   Send("+{TAB}")
   sleep (50)
   send ($searchName)
   sleep (500)
EndFunc

Func WriteEntry($amount,$value,$mark=False)
   if $amount > 0 then send ("{TAB " & $amount & "}")
   if $mark Then
	  send("+{end}")
   endif
   sleep (0)
   send ($value)
   sleep (10)
EndFunc
