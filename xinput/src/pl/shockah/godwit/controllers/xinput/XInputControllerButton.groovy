package pl.shockah.godwit.controllers.xinput

import com.ivan.xinput.enums.XInputButton
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerButton
import pl.shockah.godwit.controllers.ControllerButtonState

@CompileStatic
class XInputControllerButton extends ControllerButton {
	final XInputController controller
	final XInputButton button
	protected boolean press
	protected boolean release

	XInputControllerButton(XInputController controller, XInputButton button) {
		super(controller, Extensions.getName(button))
		this.controller = controller
		this.button = button
	}

	@Override
	ControllerButtonState getState() {
		return new ControllerButtonState(
				button: this,
				isDown: Extensions.isDown(controller.device.components.buttons, button),
				didPress: press,
				didRelease: release
		)
	}
}