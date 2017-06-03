package pl.shockah.godwit.controllers.lwjgl3

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerPov
import pl.shockah.godwit.controllers.ControllerPovState

@CompileStatic
class Lwjgl3ControllerPov extends ControllerPov {
	final Lwjgl3Controller controller
	final int povCode

	Lwjgl3ControllerPov(Lwjgl3Controller controller, String name, int povCode) {
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