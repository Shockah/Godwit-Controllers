package pl.shockah.godwit.controllers.lwjgl3

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerButton
import pl.shockah.godwit.controllers.ControllerButtonState

@CompileStatic
class Lwjgl3ControllerButton extends ControllerButton {
	final Lwjgl3Controller controller
	final int buttonCode
	protected boolean press
	protected boolean release

	Lwjgl3ControllerButton(Lwjgl3Controller controller, String name, int buttonCode) {
		super(controller, name)
		this.controller = controller
		this.buttonCode = buttonCode
	}

	@Override
	ControllerButtonState getState() {
		return new ControllerButtonState(
				this,
				controller.controller.getButton(buttonCode),
				press,
				release
		)
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
		press = false
		release = false
	}
}