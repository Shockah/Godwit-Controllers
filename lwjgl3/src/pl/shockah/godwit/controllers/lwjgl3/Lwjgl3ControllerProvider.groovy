package pl.shockah.godwit.controllers.lwjgl3

import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.math.Vector3
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerProvider

@CompileStatic
class Lwjgl3ControllerProvider extends ControllerProvider implements ControllerListener {
	protected final Map<com.badlogic.gdx.controllers.Controller, Lwjgl3Controller> nativeToAbstractMap = new LinkedHashMap<>()
	protected final Set<Lwjgl3ControllerImplementationProvider> implementationProviders = new LinkedHashSet<>()

	@Override
	boolean isAvailable() {
		return true
	}

	void register(Lwjgl3ControllerImplementationProvider implementationProvider) {
		implementationProviders << implementationProvider
	}

	void unregister(Lwjgl3ControllerImplementationProvider implementationProvider) {
		implementationProviders.remove(implementationProvider)
	}

	@Override
	List<Controller> getControllers() {
		return nativeToAbstractMap.values() as List<Controller>
	}

	@Override
	protected void onRegister() {
		super.onRegister()
		Controllers.addListener(this)
		for (com.badlogic.gdx.controllers.Controller controller : Controllers.controllers) {
			connected(controller)
		}
	}

	@Override
	protected void onUnregister() {
		super.onUnregister()
		Controllers.removeListener(this)
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
		for (Lwjgl3Controller abstractController : nativeToAbstractMap.values()) {
			abstractController.onUpdate()
		}
	}

	@Override
	protected void postUpdate() {
		super.postUpdate()
		for (Lwjgl3Controller abstractController : nativeToAbstractMap.values()) {
			abstractController.postUpdate()
		}
	}

	@Override
	void connected(com.badlogic.gdx.controllers.Controller controller) {
		Lwjgl3Controller abstractController = nativeToAbstractMap[controller]
		if (!abstractController) {
			for (Lwjgl3ControllerImplementationProvider implementationProvider : implementationProviders) {
				abstractController = implementationProvider.provide(controller)
				if (abstractController) {
					nativeToAbstractMap[controller] = abstractController
					break
				}
			}
		}
		if (abstractController) {
			abstractController.onUpdate()
			abstractController.postUpdate()
		}
	}

	@Override
	void disconnected(com.badlogic.gdx.controllers.Controller controller) {
		Lwjgl3Controller abstractController = nativeToAbstractMap[controller]
		if (abstractController) {
			abstractController.onUpdate()
			abstractController.postUpdate()
			nativeToAbstractMap.remove(controller)
		}
	}

	@Override
	boolean buttonDown(com.badlogic.gdx.controllers.Controller controller, int buttonCode) {
		return true
	}

	@Override
	boolean buttonUp(com.badlogic.gdx.controllers.Controller controller, int buttonCode) {
		return true
	}

	@Override
	boolean axisMoved(com.badlogic.gdx.controllers.Controller controller, int axisCode, float value) {
		return true
	}

	@Override
	boolean povMoved(com.badlogic.gdx.controllers.Controller controller, int povCode, PovDirection value) {
		return true
	}

	@Override
	boolean xSliderMoved(com.badlogic.gdx.controllers.Controller controller, int sliderCode, boolean value) {
		return true
	}

	@Override
	boolean ySliderMoved(com.badlogic.gdx.controllers.Controller controller, int sliderCode, boolean value) {
		return true
	}

	@Override
	boolean accelerometerMoved(com.badlogic.gdx.controllers.Controller controller, int accelerometerCode, Vector3 value) {
		return true
	}
}