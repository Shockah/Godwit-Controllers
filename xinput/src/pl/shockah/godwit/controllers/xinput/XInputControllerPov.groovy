package pl.shockah.godwit.controllers.xinput

import com.badlogic.gdx.controllers.PovDirection
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerPov
import pl.shockah.godwit.controllers.ControllerPovState

@CompileStatic
class XInputControllerPov extends ControllerPov {
	final XInputController controller

	XInputControllerPov(XInputController controller) {
		super(controller, "POV")
		this.controller = controller
	}

	@Override
	ControllerPovState getState() {
		boolean l = controller.device.components.buttons.left
		boolean r = controller.device.components.buttons.right
		boolean u = controller.device.components.buttons.up
		boolean d = controller.device.components.buttons.down

		return new ControllerPovState(
				pov: this,
				direction: PovDirection.getDirectionFromStates(l, r, u, d)
		)
	}
}