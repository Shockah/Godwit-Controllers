package pl.shockah.godwit.controllers.directinput

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerButton
import pl.shockah.godwit.controllers.ControllerButtonState

@CompileStatic
class DirectInputControllerButton extends ControllerButton {
	final DirectInputController controller
	final int buttonCode
	protected boolean press
	protected boolean release

	DirectInputControllerButton(DirectInputController controller, String name, int buttonCode) {
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