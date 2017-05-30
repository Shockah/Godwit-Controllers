package pl.shockah.godwit.controllers.xinput

import com.ivan.xinput.enums.XInputAxis
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAxis
import pl.shockah.godwit.controllers.ControllerAxisState

@CompileStatic
class XInputControllerAxis extends ControllerAxis {
	final XInputController controller
	final XInputAxis axis

	XInputControllerAxis(XInputController controller, XInputAxis axis, ControllerAxis.MappedRange mappedRange = null, float deadzone = 0.1f) {
		super(controller, Extensions.getName(axis), mappedRange, deadzone)
		this.controller = controller
		this.axis = axis
	}

	@Override
	ControllerAxisState getState() {
		float value = controller.device.components.axes.get(axis)
		return new ControllerAxisState(
				this,
				getMappedValue(value),
				getMappedValue(value),
				getMappedValue(value)
		)
	}
}