package pl.shockah.godwit.controllers.xinput

import com.ivan.xinput.enums.XInputAxis
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAxis
import pl.shockah.godwit.controllers.ControllerAxisState

@CompileStatic
class XInputControllerAxis extends ControllerAxis {
	final XInputController controller
	final XInputAxis axis

	XInputControllerAxis(XInputController controller, XInputAxis axis) {
		super(controller, Extensions.getName(axis))
		this.controller = controller
		this.axis = axis
	}

	@Override
	ControllerAxisState getState() {
		float value = controller.device.components.axes.get(axis)
		return new ControllerAxisState(
				axis: this,
				value: value,
				minAbsValue: value,
				maxAbsValue: value
		)
	}
}