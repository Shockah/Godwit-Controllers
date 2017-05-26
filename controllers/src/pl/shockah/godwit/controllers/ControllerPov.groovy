package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerPov extends ControllerComponent {
	protected ControllerPovState cachedState
	protected ControllerPovState lastState

	ControllerPov(Controller controller, String name) {
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

	abstract ControllerPovState getState()

	ControllerPovState getLastState() {
		return lastState
	}
}