package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerButton extends ControllerComponent {
	protected ControllerButtonState cachedState
	protected ControllerButtonState lastState

	ControllerButton(Controller controller, String name) {
		super(controller, name)
		lastState = new ControllerButtonState(
				this,
				false,
				false,
				false
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
						this,
						buttonState.isDown ? pressedValue : releasedValue,
						buttonState.didPress ? pressedValue : (buttonState.isDown ? pressedValue : releasedValue),
						buttonState.didRelease ? releasedValue : (buttonState.isDown ? pressedValue : releasedValue)
				)
			}
		}
	}
}