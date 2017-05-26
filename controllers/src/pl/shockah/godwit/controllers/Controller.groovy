package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class Controller {
	final List<ControllerPov> povs = new ArrayList<>()
	final List<ControllerAnalog> analogs = new ArrayList<>()
	final List<ControllerAxis> axes = new ArrayList<>()
	final List<ControllerButton> buttons = new ArrayList<>()
	final List<ControllerComponent> fakes = new ArrayList<>()

	List<ControllerComponent> getRealComponents() {
		return (povs as List<ControllerComponent>) + (analogs as List<ControllerComponent>) + (axes as List<ControllerComponent>) + (buttons as List<ControllerComponent>)
	}

	List<ControllerComponent> getComponents() {
		return realComponents + fakes
	}

	void onUpdate() {
		for (ControllerComponent component : components) {
			component.onUpdate()
		}
	}

	void postUpdate() {
		for (ControllerComponent component : components) {
			component.postUpdate()
		}
	}

	abstract boolean isConnected()
}