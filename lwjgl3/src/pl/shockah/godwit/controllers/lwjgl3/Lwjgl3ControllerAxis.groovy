package pl.shockah.godwit.controllers.lwjgl3

import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAxis
import pl.shockah.godwit.controllers.ControllerAxisState

@CompileStatic
class Lwjgl3ControllerAxis extends ControllerAxis {
	final Lwjgl3Controller controller
	final int axisCode
	protected Float minAbsValue = null
	protected Float maxAbsValue = null

	Lwjgl3ControllerAxis(Lwjgl3Controller controller, String name, int axisCode, ControllerAxis.MappedRange mappedRange = null, float deadzone = 0.1f) {
		super(controller, name, mappedRange, deadzone)
		this.controller = controller
		this.axisCode = axisCode
	}

	@Override
	ControllerAxisState getState() {
		float value = controller.controller.getAxis(axisCode)
		return new ControllerAxisState(
				this,
				getMappedValue(value),
				getMappedValue(minAbsValue ? minAbsValue : value),
				getMappedValue(maxAbsValue ? maxAbsValue : value)
		)
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
		minAbsValue = null
		maxAbsValue = null
	}
}