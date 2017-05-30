package pl.shockah.godwit.controllers.wiiusej

import com.badlogic.gdx.controllers.PovDirection
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerPov
import pl.shockah.godwit.controllers.ControllerPovState
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent

@CompileStatic
class WiimoteControllerPov extends ControllerPov implements WiimoteButtonsEventListener {
	final WiimoteController controller
	protected boolean left = false
	protected boolean right = false
	protected boolean up = false
	protected boolean down = false

	WiimoteControllerPov(WiimoteController controller) {
		super(controller, "POV")
		this.controller = controller
	}

	void onButtonsEvent(WiimoteButtonsEvent event) {
		left = event.buttonLeftPressed
		right = event.buttonRightPressed
		up = event.buttonUpPressed
		down = event.buttonDownPressed
	}

	@Override
	ControllerPovState getState() {
		return new ControllerPovState(
				this,
				PovDirection.getDirectionFromStates(left, right, up, down)
		)
	}
}