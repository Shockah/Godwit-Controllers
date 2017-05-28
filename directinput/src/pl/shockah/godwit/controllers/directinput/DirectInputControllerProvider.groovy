package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.math.Vector3
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerProvider

@CompileStatic
class DirectInputControllerProvider extends ControllerProvider implements ControllerListener {
	protected final Map<com.badlogic.gdx.controllers.Controller, DirectInputController> nativeToAbstractMap = new LinkedHashMap<>()
	protected final Set<DirectInputControllerImplementationProvider> implementationProviders = new LinkedHashSet<>()

	DirectInputControllerProvider() {
		Controllers.addListener(this)
	}

	@Override
	boolean isAvailable() {
		return true
	}

	void register(DirectInputControllerImplementationProvider implementationProvider) {
		implementationProviders << implementationProvider
	}

	void unregister(DirectInputControllerImplementationProvider implementationProvider) {
		implementationProviders.remove(implementationProvider)
	}

	@Override
	List<Controller> getControllers() {
		return nativeToAbstractMap.values() as List<Controller>
	}

	@Override
	void onUpdate() {
		for (DirectInputController abstractController : nativeToAbstractMap.values()) {
			abstractController.onUpdate()
		}
	}

	@Override
	void postUpdate() {
		for (DirectInputController abstractController : nativeToAbstractMap.values()) {
			abstractController.postUpdate()
		}
	}

	@Override
	void connected(com.badlogic.gdx.controllers.Controller controller) {
		DirectInputController abstractController = nativeToAbstractMap[controller]
		if (!abstractController) {
			for (DirectInputControllerImplementationProvider implementationProvider : implementationProviders) {
				abstractController = implementationProvider.provide(controller)
				if (abstractController) {
					nativeToAbstractMap[controller] = abstractController
					break
				}
			}
		}
		abstractController.onUpdate()
		abstractController.postUpdate()
	}

	@Override
	void disconnected(com.badlogic.gdx.controllers.Controller controller) {
		DirectInputController abstractController = nativeToAbstractMap[controller]
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