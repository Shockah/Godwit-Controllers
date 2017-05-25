package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerComponent {
	final Controller controller

	ControllerComponent(Controller controller) {
		this.controller = controller
	}

	protected void onUpdate() {
	}

	protected void postUpdate() {
	}
}