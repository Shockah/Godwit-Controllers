package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerButton extends ControllerComponent {
	protected ControllerButtonState cachedState
	protected ControllerButtonState lastState

	ControllerButton(Controller controller, String name) {
		super(controller, name)
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

	abstract ControllerButtonState getState()

	ControllerButtonState getLastState() {
		return lastState
	}
}