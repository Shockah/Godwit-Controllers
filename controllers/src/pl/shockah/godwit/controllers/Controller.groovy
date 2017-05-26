package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class Controller {
	final Map<String, ControllerPov> povs = new HashMap<>()
	final Map<String, ControllerAnalog> analogs = new HashMap<>()
	final Map<String, ControllerAxis> axes = new HashMap<>()
	final Map<String, ControllerButton> buttons = new HashMap<>()
	final Map<String, ControllerComponent> fakes = new HashMap<>()

	List<ControllerComponent> getRealComponents() {
		return (povs.values() as List<ControllerComponent>) + (analogs.values() as List<ControllerComponent>) + (axes.values() as List<ControllerComponent>) + (buttons.values() as List<ControllerComponent>)
	}

	List<ControllerComponent> getComponents() {
		return realComponents + fakes.values()
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

	abstract Float getBatteryStatus()

	protected void registerPov(ControllerPov pov) {
		povs[pov.name] = pov
	}

	protected void registerAnalog(ControllerAnalog analog) {
		analogs[analog.name] = analog
	}

	protected void registerAxis(ControllerAxis axis) {
		axes[axis.name] = axis
	}

	protected void registerButton(ControllerButton button) {
		buttons[button.name] = button
	}

	protected void registerFake(ControllerComponent component) {
		fakes[component.name] = component
	}
}