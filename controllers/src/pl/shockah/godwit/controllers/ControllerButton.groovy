package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerButton extends ControllerComponent {
	protected ControllerButtonState cachedState
	protected ControllerButtonState lastState

	ControllerButton(Controller controller, String name) {
		super(controller, name)
		lastState = new ControllerButtonState(
				button: this,
				isDown: false,
				didPress: false,
				didRelease: false
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

	abstract ControllerButtonState getState()

	ControllerButtonState getLastState() {
		return lastState
	}

	ControllerAxis asFakeAxis(float releasedValue = 0f, float pressedValue = 1f) {
		return new ControllerAxis(controller, "${name} as Axis") {
			@Override
			ControllerAxisState getState() {
				ControllerButtonState buttonState = ControllerButton.this.state
				return new ControllerAxisState(
						axis: this,
						value: buttonState.isDown ? pressedValue : releasedValue,
						minAbsValue: buttonState.didPress ? pressedValue : (buttonState.isDown ? pressedValue : releasedValue),
						maxAbsValue: buttonState.didRelease ? releasedValue : (buttonState.isDown ? pressedValue : releasedValue)
				)
			}
		}
	}
}