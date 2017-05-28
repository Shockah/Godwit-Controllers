package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic
import pl.shockah.util.WeakValueMap

@CompileStatic
abstract class ControllerComponent {
	final Controller controller
	final String name
	final WeakValueMap<String, ControllerComponent> fakes = new WeakValueMap<>(new HashMap<>())

	ControllerComponent(Controller controller, String name) {
		this.controller = controller
		this.name = name
	}

	protected void onUpdate() {
		for (ControllerComponent fake : fakes.values()) {
			fake.onUpdate()
		}
	}

	protected void postUpdate() {
		for (ControllerComponent fake : fakes.values()) {
			fake.postUpdate()
		}
	}

	void registerFake(ControllerComponent component) {
		fakes[component.name] = component
	}

	void unregisterFake(ControllerComponent component) {
		fakes.remove(component.name)
	}
}