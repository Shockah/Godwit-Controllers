package pl.shockah.godwit.controllers.wiiusej

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent

@CompileStatic
@PackageScope
interface WiimoteButtonsEventListener {
	void onButtonsEvent(WiimoteButtonsEvent event)
}