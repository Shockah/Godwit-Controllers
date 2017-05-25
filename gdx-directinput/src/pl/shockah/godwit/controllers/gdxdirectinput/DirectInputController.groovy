package pl.shockah.godwit.controllers.gdxdirectinput

import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.math.Vector3
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller

@CompileStatic
abstract class DirectInputController extends Controller implements ControllerListener {
	protected final com.badlogic.gdx.controllers.Controller controller
	protected boolean connected = true

	DirectInputController(com.badlogic.gdx.controllers.Controller controller) {
		this.controller = controller
		setupComponents()
	}

	abstract void setupComponents()

	@Override
	boolean isConnected() {
		return connected
	}

	@Override
	void connected(com.badlogic.gdx.controllers.Controller controller) {
		connected = true
	}

	@Override
	void disconnected(com.badlogic.gdx.controllers.Controller controller) {
		connected = false
	}

	@Override
	boolean buttonDown(com.badlogic.gdx.controllers.Controller controller, int buttonCode) {
		return false
	}

	@Override
	boolean buttonUp(com.badlogic.gdx.controllers.Controller controller, int buttonCode) {
		return false
	}

	@Override
	boolean axisMoved(com.badlogic.gdx.controllers.Controller controller, int axisCode, float value) {
		return false
	}

	@Override
	boolean povMoved(com.badlogic.gdx.controllers.Controller controller, int povCode, PovDirection value) {
		return false
	}

	@Override
	boolean xSliderMoved(com.badlogic.gdx.controllers.Controller controller, int sliderCode, boolean value) {
		return false
	}

	@Override
	boolean ySliderMoved(com.badlogic.gdx.controllers.Controller controller, int sliderCode, boolean value) {
		return false
	}

	@Override
	boolean accelerometerMoved(com.badlogic.gdx.controllers.Controller controller, int accelerometerCode, Vector3 value) {
		return false
	}
}