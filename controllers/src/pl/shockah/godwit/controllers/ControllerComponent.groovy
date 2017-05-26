package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerComponent {
	final Controller controller
	final String name

	ControllerComponent(Controller controller, String name) {
		this.controller = controller
		this.name = name
	}

	protected void onUpdate() {
	}

	protected void postUpdate() {
	}
}