package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerAxis extends ControllerComponent {
	protected ControllerAxisState cachedState
	protected ControllerAxisState lastState

	ControllerAxis(Controller controller) {
		super(controller)
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

	abstract ControllerAxisState getState()

	ControllerAxisState getLastState() {
		return lastState
	}
}