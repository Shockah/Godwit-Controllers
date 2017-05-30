package pl.shockah.godwit.controllers.wiiusej

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import wiiusej.Wiimote
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent
import wiiusej.wiiusejevents.physicalevents.IREvent
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent
import wiiusej.wiiusejevents.utils.WiimoteListener
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent

@CompileStatic
class WiimoteController extends Controller implements WiimoteListener {
	final Wiimote wiimote
	protected Float batteryLevel

	protected WiimoteControllerPov pov
	protected Map<WiimoteButton, WiimoteControllerButton> buttonMap = new HashMap<>()

	WiimoteController(Wiimote wiimote) {
		this.wiimote = wiimote

		registerPov(pov = new WiimoteControllerPov(this))

		for (WiimoteButton button : WiimoteButton.values()) {
			WiimoteControllerButton controllerButton = new WiimoteControllerButton(this, button)
			buttonMap[button] = controllerButton
			registerButton(controllerButton)
		}
	}

	@Override
	boolean isConnected() {
		return false
	}

	@Override
	Float getBatteryStatus() {
		return batteryLevel
	}

	@Override
	void onButtonsEvent(WiimoteButtonsEvent wiimoteButtonsEvent) {
		for (WiimoteButtonsEventListener listener : ((buttonMap.values() as List<WiimoteButtonsEventListener>) + [pov])) {
			listener.onButtonsEvent(wiimoteButtonsEvent)
		}
	}

	@Override
	void onIrEvent(IREvent irEvent) {
	}

	@Override
	void onMotionSensingEvent(MotionSensingEvent motionSensingEvent) {
	}

	@Override
	void onExpansionEvent(ExpansionEvent expansionEvent) {
	}

	@Override
	void onStatusEvent(StatusEvent statusEvent) {
		batteryLevel = statusEvent.batteryLevel
	}

	@Override
	void onDisconnectionEvent(DisconnectionEvent disconnectionEvent) {
	}

	@Override
	void onNunchukInsertedEvent(NunchukInsertedEvent nunchukInsertedEvent) {
	}

	@Override
	void onNunchukRemovedEvent(NunchukRemovedEvent nunchukRemovedEvent) {
	}

	@Override
	void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent guitarHeroInsertedEvent) {
	}

	@Override
	void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent guitarHeroRemovedEvent) {
	}

	@Override
	void onClassicControllerInsertedEvent(ClassicControllerInsertedEvent classicControllerInsertedEvent) {
	}

	@Override
	void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent classicControllerRemovedEvent) {
	}
}