Func addSetSlider ($name,$valueSmall,$valueBig,$file)
   FileWriteLine($file, "<SetSlider name=""" & $name & """ size=""small"" value=""" & $valueSmall & """ />")
   FileWriteLine($file, "<SetSlider name=""" & $name & """ size=""big"" value=""" & $valueBig & """ />")
EndFunc

Func createPresetFiles()
   $bodyName = "RBS" & StringFormat ("%03d", 1)
   ;Local $file = FileOpen("SliderPresets\" & $bodyName & ".xml", 2)
   Local $file = FileOpen($BodySlidePath & "\SliderPresets\RBS_Presets.xml", 2)
   FileWriteLine($file, "<SliderPresets>")
   For $i = 0 To $amountBodyTypes step 1
   $tmp =  "RBS" & StringFormat ("%03d", $i)
   FileWriteLine($file, "<Preset name="""&$tmp&""" set=""RBS"">")
   FileWriteLine($file, "<Group name=""RBS"" />")
   FileWriteLine($file, "<SetSlider name=""(zap)Panty"" size=""big"" value=""100"" />")
	FileWriteLine($file, "<SetSlider name=""(zap)Panty"" size=""small"" value=""100"" />")
	FileWriteLine($file, "<SetSlider name=""(zap)Bra"" size=""big"" value=""100"" />")
	FileWriteLine($file, "<SetSlider name=""(zap)Bra"" size=""small"" value=""100"" />")
   $bigTorsoLow = Randomize(-50*$bodyMultiLow,0*$bodyMultiHigh)
   $bigTorsoHeigh = Randomize(50*$bodyMultiLow,420*$bodyMultiHigh)
   $breastSSHLow = Randomize(20*$bodyMultiLow,30*$bodyMultiHigh)
   $breastLow = Randomize(-20*$bodyMultiLow,20*$bodyMultiHigh)
   $breastsSmallLow = Randomize(-30*$bodyMultiLow,50*$bodyMultiHigh)
   $breastsSHLow = Randomize(00*$bodyMultiLow,30*$bodyMultiHigh)
   $breastsFantasyLow =  Randomize(-20*$bodyMultiLow,20*$bodyMultiHigh)
   $doubleMelonLow = Randomize(-20*$bodyMultiLow,20*$bodyMultiHigh)
   $breastFlatnessLow = Randomize(-10*$bodyMultiLow,60*$bodyMultiHigh)
   $multi = 1 + ($bigTorsoHeigh/180)
   $breastGravityLow = Randomize(-20*$bodyMultiLow,30*$bodyMultiHigh)
   addSetSlider("(zap)Panty", 0, 0, $file)
   addSetSlider("(zap)Skirt", 0, 0, $file)
   addSetSlider("(zap)Leggings", 0, 0, $file)
   addSetSlider("(zap)Bra", 0, 0, $file)
   addSetSlider("BigTorso", $bigTorsoLow, $bigTorsoHeigh, $file)
   addSetSlider("Hips", Randomize(0*$bodyMultiLow,30*$bodyMultiHigh), Randomize(10*$bodyMultiLow , 50*$bodyMultiHigh * $multi),$file)
   addSetSlider("ShoulderWidth", Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh), Randomize(-30*$bodyMultiLow, 40*$bodyMultiHigh* $multi),$file)
   addSetSlider("ChubbyLegs", Randomize(0*$bodyMultiLow,0*$bodyMultiHigh), Randomize(10 *$bodyMultiLow * $multi ,80 *$bodyMultiHigh * $multi),$file)
   addSetSlider("BreastsSSH", $breastSSHLow, Randomize($breastSSHLow, ($breastSSHLow+10) * $multi),$file)

   addSetSlider("Breasts", $breastLow, Randomize($breastLow+40*$bodyMultiLow,150*$bodyMultiHigh),$file)
   addSetSlider("BreastsSmall", $breastsSmallLow, Randomize($breastsSmallLow,120*$bodyMultiHigh),$file)
   addSetSlider("BreastsSH", $breastsSHLow, Randomize($breastsSHLow,40*$bodyMultiHigh),$file)

   addSetSlider("BreastsFantasy", $breastsFantasyLow , Randomize($breastsFantasyLow,30*$bodyMultiHigh),$file)
   addSetSlider("DoubleMelon", $doubleMelonLow, Randomize($doubleMelonLow,$doubleMelonLow+1),$file)
   addSetSlider("BreastFlatness", $breastFlatnessLow , Randomize(0,$breastFlatnessLow -20*$bodyMultiHigh),$file)

   addSetSlider("BreastGravity", $breastGravityLow , Randomize($breastGravityLow ,60*$bodyMultiHigh),$file)

   addSetSlider("Arms", Randomize(-20*$bodyMultiLow,0*$bodyMultiHigh), Randomize(00*$bodyMultiLow,70*$bodyMultiHigh),$file)
   addSetSlider("ChubbyArms", Randomize(-20*$bodyMultiLow,0*$bodyMultiHigh), Randomize(00*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("ShoulderSmooth", Randomize(-100*$bodyMultiLow,50*$bodyMultiHigh), Randomize(-150*$bodyMultiLow,150*$bodyMultiHigh),$file)



   addSetSlider("BreastCleavage", Randomize(-20*$bodyMultiLow,20*$bodyMultiHigh), Randomize(-20*$bodyMultiLow,50*$bodyMultiHigh),$file)


   addSetSlider("BreastPerkiness", Randomize(-50*$bodyMultiLow,35*$bodyMultiHigh), Randomize(-20*$bodyMultiLow,100*$bodyMultiHigh),$file)
   addSetSlider("BreastWidth", Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("NippleDistance", Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("NipplePerkiness", Randomize(0*$bodyMultiLow,150*$bodyMultiHigh), Randomize(0*$bodyMultiLow,150*$bodyMultiHigh),$file)
   addSetSlider("NippleLength", Randomize(-10*$bodyMultiLow,20*$bodyMultiHigh), Randomize(-10*$bodyMultiLow,20*$bodyMultiHigh),$file)
   addSetSlider("NippleSize", Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("NippleUp", Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("NippleDown", Randomize(0*$bodyMultiLow,0*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("NippleTip", Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("Waist", Randomize(-120*$bodyMultiLow,20*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,200*$bodyMultiHigh),$file)
   addSetSlider("WideWaistLine", Randomize(0*$bodyMultiLow,0*$bodyMultiHigh), Randomize(0*$bodyMultiLow,0*$bodyMultiHigh),$file)
   addSetSlider("ChubbyWaist", Randomize(-10*$bodyMultiLow,10*$bodyMultiHigh), Randomize(-10*$bodyMultiLow,100*$bodyMultiHigh),$file)
   addSetSlider("Belly", Randomize(0*$bodyMultiLow,20*$bodyMultiHigh), Randomize(0*$bodyMultiLow,100*$bodyMultiHigh),$file)
   addSetSlider("BigBelly", Randomize(-40*$bodyMultiLow,0*$bodyMultiHigh), Randomize(-40*$bodyMultiLow,100*$bodyMultiHigh),$file)
   addSetSlider("TummyTuck", Randomize(0*$bodyMultiLow,0*$bodyMultiHigh), Randomize(0*$bodyMultiLow,0*$bodyMultiHigh),$file)
   addSetSlider("Back", Randomize(-50*$bodyMultiLow,0*$bodyMultiHigh), Randomize(-80*$bodyMultiLow,150*$bodyMultiHigh),$file)
   addSetSlider("Hipbone", Randomize(-50*$bodyMultiLow,50*$bodyMultiHigh), Randomize(20*$bodyMultiLow,100*$bodyMultiHigh),$file)

   addSetSlider("ButtCrack", Randomize(-20*$bodyMultiLow,5*$bodyMultiHigh), Randomize(-20*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("Butt", Randomize(-50*$bodyMultiLow,30*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,120*$bodyMultiHigh),$file)
   addSetSlider("ButtSmall", Randomize(-50*$bodyMultiLow,30*$bodyMultiHigh), Randomize(0*$bodyMultiLow,20*$bodyMultiHigh),$file)
   addSetSlider("ButtShape2", Randomize(-50*$bodyMultiLow,20*$bodyMultiHigh), Randomize(-50*$bodyMultiLow,100*$bodyMultiHigh),$file)
   addSetSlider("BigButt", Randomize(-100*$bodyMultiLow,-20*$bodyMultiHigh), Randomize(0*$bodyMultiLow,50*$bodyMultiHigh),$file)
   addSetSlider("ChubbyButt", Randomize(-100*$bodyMultiLow,-20*$bodyMultiHigh), Randomize(0*$bodyMultiLow,100*$bodyMultiHigh),$file)
   addSetSlider("AppleCheeks", Randomize(0*$bodyMultiLow,0*$bodyMultiHigh), Randomize(0*$bodyMultiLow,0*$bodyMultiHigh),$file)
   addSetSlider("RoundAss" ,Randomize(0*$bodyMultiLow,0*$bodyMultiHigh), Randomize(0*$bodyMultiLow,0*$bodyMultiHigh),$file)
   addSetSlider("Groin", Randomize(0*$bodyMultiLow,20*$bodyMultiHigh), Randomize(0*$bodyMultiLow,100*$bodyMultiHigh),$file)
   addSetSlider("SlimThighs", Randomize(-30 * $bodyMultiLow, 0 * $bodyMultiHigh), Randomize(-30 *$bodyMultiLow ,0 *$bodyMultiHigh) ,$file)
   addSetSlider("Thighs", Randomize(-30*$bodyMultiLow,0*$bodyMultiHigh), Randomize(0*$bodyMultiLow,40*$bodyMultiHigh),$file)

   addSetSlider("Legs", Randomize(-30*$bodyMultiLow,0*$bodyMultiHigh), Randomize(20*$bodyMultiLow,60*$bodyMultiHigh),$file)
   addSetSlider("KneeHeight", Randomize(-30*$bodyMultiLow,0*$bodyMultiHigh), Randomize(0*$bodyMultiLow,0*$bodyMultiHigh),$file)
   addSetSlider("CalfSize", Randomize(-100*$bodyMultiLow,0*$bodyMultiHigh), Randomize(0*$bodyMultiLow,150*$bodyMultiHigh),$file)
   addSetSlider("CalfSmooth", Randomize(50*$bodyMultiLow,100*$bodyMultiHigh), Randomize(50*$bodyMultiLow,100*$bodyMultiHigh),$file)
   addSetSlider("Ankles", 100, 100,$file)
FileWriteLine($file, "</Preset>")
Next
   FileWriteLine($file, "</SliderPresets>")
   FileClose ($file)

EndFunc
