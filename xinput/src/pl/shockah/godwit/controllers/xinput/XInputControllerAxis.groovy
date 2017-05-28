package pl.shockah.godwit.controllers.xinput

import com.ivan.xinput.enums.XInputAxis
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAxis
import pl.shockah.godwit.controllers.ControllerAxisState

@CompileStatic
class XInputControllerAxis extends ControllerAxis {
	final XInputController controller
	final XInputAxis axis
	final boolean reversed

	XInputControllerAxis(XInputController controller, XInputAxis axis, boolean reversed = false) {
		super(controller, Extensions.getName(axis))
		this.controller = controller
		this.axis = axis
		this.reversed = reversed
	}

	private float getReverseModifier() {
		return reversed ? -1f : 1f
	}

	@Override
	ControllerAxisState getState() {
		float value = controller.device.components.axes.get(axis)
		return new ControllerAxisState(
				this,
				value * reverseModifier,
				value * reverseModifier,
				value * reverseModifier
		)
	}
}