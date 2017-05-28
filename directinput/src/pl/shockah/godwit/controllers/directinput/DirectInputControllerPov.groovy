package pl.shockah.godwit.controllers.directinput

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerPov
import pl.shockah.godwit.controllers.ControllerPovState

@CompileStatic
class DirectInputControllerPov extends ControllerPov {
	final DirectInputController controller
	final int povCode

	DirectInputControllerPov(DirectInputController controller, String name, int povCode) {
		super(controller, name)
		this.controller = controller
		this.povCode = povCode
	}

	@Override
	ControllerPovState getState() {
		return new ControllerPovState(
				this,
				controller.controller.getPov(povCode)
		)
	}
}