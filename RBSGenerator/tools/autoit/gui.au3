Func _GUICreate()
$left = (@DesktopWidth)-300;
$top =0
   global $hGUI= GUICreate("Body Creator Progress Windows", 300, 50, $left, $top, BitOr($WS_BORDER, $WS_POPUP), BitOR($WS_EX_TOPMOST, $WS_EX_TOOLWINDOW))

   GUISetState (@SW_SHOW)
EndFunc

Func _CreateLabel()
   GUICtrlCreateLabel("RBS BodyCreator", 5, 5, 300, 25)
   global $label = GUICtrlCreateLabel("Line 1 Cell 1", 5, 25, 300, 25)
EndFunc

Func _GUIChangeText($text)
   GUICtrlSetData ($label, $text)
EndFunc

Func _GUIClose()
   GUIDelete($hGUI);
EndFunc

