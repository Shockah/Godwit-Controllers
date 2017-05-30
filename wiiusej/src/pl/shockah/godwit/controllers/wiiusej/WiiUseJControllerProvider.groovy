package pl.shockah.godwit.controllers.wiiusej

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerProvider
import wiiusej.WiiUseApiManager
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
class WiiUseJControllerProvider extends ControllerProvider implements WiimoteListener {
	final int wiimoteLimit
	protected Map<Wiimote, WiimoteController> nativeToAbstractMap = new LinkedHashMap<>()

	WiiUseJControllerProvider(int wiimoteLimit = 4) {
		this.wiimoteLimit = wiimoteLimit
	}

	@Override
	protected void onRegister() {
		super.onRegister()

		new Thread() {
			@Override
			void run() {
				for (Wiimote wiimote : WiiUseApiManager.getWiimotes(wiimoteLimit, false)) {
					nativeToAbstractMap[wiimote] = new WiimoteController(wiimote)
				}
			}
		}.start()
	}

	@Override
	protected void onUnregister() {
		super.onUnregister()
		nativeToAbstractMap.clear()
		WiiUseApiManager.shutdown()
	}

	@Override
	boolean isAvailable() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win")) {
			return true
		} else if (osName.contains("mac") || osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
			return true
		}
		return false
	}

	@Override
	List<Controller> getControllers() {
		return null
	}

	@Override
	void onButtonsEvent(WiimoteButtonsEvent wiimoteButtonsEvent) {
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