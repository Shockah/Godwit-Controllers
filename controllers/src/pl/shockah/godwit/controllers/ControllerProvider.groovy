package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerProvider {
	boolean enabled = true

	abstract boolean isAvailable()

	abstract List<Controller> getControllers()

	final List<Controller> getConnectedControllers() {
		return controllers.findAll { it.connected }
	}

	protected void onRegister() {
	}

	protected void onUnregister() {
	}

	protected void onUpdate() {
		for (Controller controller : controllers) {
			controller.onUpdate()
		}
	}

	protected void postUpdate() {
		for (Controller controller : controllers) {
			controller.postUpdate()
		}
	}
}