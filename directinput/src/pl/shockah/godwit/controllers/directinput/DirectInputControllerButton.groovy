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

	DirectInputControllerButton(DirectInputController controller, int buttonCode) {
		super(controller)
		this.controller = controller
		this.buttonCode = buttonCode
	}

	@Override
	ControllerButtonState getState() {
		return new ControllerButtonState(
				button: this,
				isDown: controller.controller.getButton(buttonCode),
				didPress: press,
				didRelease: release
		)
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
		press = false
		release = false
	}
}