package pl.shockah.godwit.controllers.directinput

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAxis
import pl.shockah.godwit.controllers.ControllerAxisState

@CompileStatic
class DirectInputControllerAxis extends ControllerAxis {
	final DirectInputController controller
	final int axisCode
	protected Float minAbsValue = null
	protected Float maxAbsValue = null

	DirectInputControllerAxis(DirectInputController controller, int axisCode) {
		super(controller)
		this.controller = controller
		this.axisCode = axisCode
	}

	@Override
	ControllerAxisState getState() {
		float value = controller.controller.getAxis(axisCode)
		return new ControllerAxisState(
				axis: this,
				value: value,
				minAbsValue: minAbsValue ? minAbsValue : value,
				maxAbsValue: maxAbsValue ? maxAbsValue : value
		)
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
		minAbsValue = null
		maxAbsValue = null
	}
}