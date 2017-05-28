package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
final class Controllers {
	protected static final Set<ControllerProvider> providers = new LinkedHashSet<>()

	static void register(ControllerProvider provider) {
		providers << provider
	}

	static void unregister(ControllerProvider provider) {
		providers.remove(provider)
	}

	static void update(Closure<Void> closure) {
		onUpdate()
		closure.run()
		postUpdate()
	}

	static void onUpdate() {
		providers.findAll { it.enabled }.each {
			it.onUpdate()
		}
	}

	static void postUpdate() {
		providers.findAll { it.enabled }.each {
			it.postUpdate()
		}
	}

	static List<Controller> getControllers() {
		return providers.findAll { it.enabled }.collect { it.controllers }.flatten() as List<Controller>
	}

	static List<Controller> getConnectedControllers() {
		return controllers.findAll { it.connected }
	}

	private Controllers() {
		throw new UnsupportedOperationException()
	}
}