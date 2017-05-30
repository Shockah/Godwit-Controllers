package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerAxis extends ControllerComponent {
	protected ControllerAxisState cachedState
	protected ControllerAxisState lastState
	MappedRange mappedRange
	float deadzone


	ControllerAxis(Controller controller, String name, MappedRange mappedRange = null, float deadzone = 0.1f) {
		super(controller, name)
		this.mappedRange = mappedRange ? mappedRange : new MappedRange()
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

	private float getDeadzoneMappedValue(float value) {
		int sign = Math.signum(value) as int
		value = Math.abs(value)
		if (value < deadzone)
			return 0f
		return ((value - deadzone) / (1f - deadzone) * sign) as float
	}

	protected float getMappedValue(float value) {
		return mappedRange.map(getDeadzoneMappedValue(value))
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

	@CompileStatic
	static final class MappedRange {
		final float min
		final float max

		MappedRange(float min = -1f, float max = 1f) {
			this.min = min
			this.max = max
		}

		float map(float value) {
			float f = (value + 1f) * 0.5f
			return min + (max - min) * f
		}

		MappedRange getReversed() {
			return new MappedRange(max, min)
		}
	}
}