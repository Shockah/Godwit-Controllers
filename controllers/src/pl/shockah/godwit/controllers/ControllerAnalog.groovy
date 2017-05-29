package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic
import pl.shockah.godwit.geom.Vec2

@CompileStatic
class ControllerAnalog extends ControllerComponent {
	protected ControllerAnalogState cachedState
	protected ControllerAnalogState lastState

	final ControllerAxis x
	final ControllerAxis y

	ControllerAnalog(ControllerAxis x, ControllerAxis y, String name) {
		super(x.controller, name)
		this.x = x
		this.y = y
		assert x.controller == y.controller
		lastState = new ControllerAnalogState(
				this,
				new Vec2(),
				new Vec2(),
				new Vec2()
		)
	}

	@Override
	protected void onUpdate() {
		x.onUpdate()
		y.onUpdate()
		lastState = cachedState
		super.onUpdate()
	}

	@Override
	protected void postUpdate() {
		x.postUpdate()
		y.postUpdate()
		cachedState = state
		super.postUpdate()
	}

	ControllerAnalogState getState() {
		return new ControllerAnalogState(
				this,
				new Vec2(x.state.value, y.state.value),
				new Vec2(x.state.minAbsValue, y.state.minAbsValue),
				new Vec2(x.state.maxAbsValue, y.state.maxAbsValue)
		)
	}

	ControllerPov asFakePov(float threshold) {
		return ControllerPov.fakePovFromButtons(
				x.asFakeButton(-threshold),
				x.asFakeButton(threshold),
				y.asFakeButton(-threshold),
				y.asFakeButton(threshold),
				"${name} as POV"
		)
	}
}