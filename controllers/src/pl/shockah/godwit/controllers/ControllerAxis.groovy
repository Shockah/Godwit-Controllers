package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerAxis extends ControllerComponent {
	protected ControllerAxisState cachedState
	protected ControllerAxisState lastState

	ControllerAxis(Controller controller, String name) {
		super(controller, name)
		lastState = new ControllerAxisState(
				axis: this,
				value: 0f,
				minAbsValue: 0f,
				maxAbsValue: 0f
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
						button: this,
						isDown: isDown,
						didPress: !lastState.isDown && isDown,
						didRelease: lastState.isDown && !isDown
				)
			}
		}
	}
}