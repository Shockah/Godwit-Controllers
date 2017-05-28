package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerAxis extends ControllerComponent {
	protected ControllerAxisState cachedState
	protected ControllerAxisState lastState
	float deadzone

	ControllerAxis(Controller controller, String name, float deadzone = 0.1f) {
		super(controller, name)
		this.deadzone = deadzone
		lastState = new ControllerAxisState(
				this,
				0f,
				0f,
				0f
		)
	}

	@Override
	protected void onUpdate() {
		lastState = cachedState
		super.onUpdate()
	}

	@Override
	protected void postUpdate() {
		cachedState = state
		super.postUpdate()
	}

	protected float getValueAfterDeadzone(float value) {
		if (Math.abs(value) < Math.abs(deadzone))
			return 0f
		return (value - (Math.signum(value) * Math.abs(deadzone))) / (1 - Math.abs(deadzone)) as float
	}

	abstract ControllerAxisState getState()

	ControllerAxisState getLastState() {
		return lastState
	}

	ControllerButton asFakeButton(float threshold) {
		return new ControllerButton(controller, "${name} as Button") {
			@Override
			ControllerButtonState getState() {
				ControllerAxisState axisState = ControllerAxis.this.state
				boolean isDown = threshold > 0 ? (axisState.value >= threshold) : (axisState.value <= threshold)
				return new ControllerButtonState(
						this,
						isDown,
						!getLastState().isDown && isDown,
						getLastState().isDown && !isDown
				)
			}
		}
	}
}