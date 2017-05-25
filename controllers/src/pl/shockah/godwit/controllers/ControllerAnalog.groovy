package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic
import pl.shockah.godwit.geom.Vec2

@CompileStatic
class ControllerAnalog extends ControllerComponent {
	protected ControllerAnalogState cachedState
	protected ControllerAnalogState lastState

	final ControllerAxis x
	final ControllerAxis y

	ControllerAnalog(ControllerAxis x, ControllerAxis y) {
		super(x.controller)
		this.x = x
		this.y = y
		assert x.controller == y.controller
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
		lastState = cachedState
	}

	@Override
	protected void postUpdate() {
		super.postUpdate()
		cachedState = state
	}

	ControllerAnalogState getState() {
		return new ControllerAnalogState(
				analog: this,
				value: new Vec2(x.state.value, y.state.value),
				minAbsValue: new Vec2(x.state.minAbsValue, y.state.minAbsValue),
				maxAbsValue: new Vec2(x.state.maxAbsValue, y.state.maxAbsValue)
		)
	}
}