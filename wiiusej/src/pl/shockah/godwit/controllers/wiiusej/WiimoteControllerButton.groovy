package pl.shockah.godwit.controllers.wiiusej

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerButton
import pl.shockah.godwit.controllers.ControllerButtonState
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent

@CompileStatic
class WiimoteControllerButton extends ControllerButton implements WiimoteButtonsEventListener {
	final WiimoteController controller
	final WiimoteButton button
	protected boolean down
	protected boolean press
	protected boolean release

	WiimoteControllerButton(WiimoteController controller, WiimoteButton button) {
		super(controller, button.name)
		this.controller = controller
		this.button = button
	}

	void onButtonsEvent(WiimoteButtonsEvent event) {
		down = button.isDown(event)
		press = button.isPressed(event)
		release = button.isReleased(event)
	}

	@Override
	ControllerButtonState getState() {
		return new ControllerButtonState(
				this,
				down,
				press,
				release
		)
	}
}