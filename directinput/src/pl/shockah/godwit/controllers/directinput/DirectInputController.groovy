package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.math.Vector3
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller

@CompileStatic
abstract class DirectInputController extends Controller implements ControllerListener {
	protected final com.badlogic.gdx.controllers.Controller controller
	protected boolean connected = true

	protected Map<Integer, DirectInputControllerButton> buttonMap = new HashMap<>()
	protected Map<Integer, DirectInputControllerAxis> axisMap = new HashMap<>()

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
	Float getBatteryStatus() {
		return null
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
	}

	@Override
	protected void postUpdate() {
		super.postUpdate()
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
		DirectInputControllerButton button = buttonMap[buttonCode]
		if (button)
			button.press = true
		return false
	}

	@Override
	boolean buttonUp(com.badlogic.gdx.controllers.Controller controller, int buttonCode) {
		DirectInputControllerButton button = buttonMap[buttonCode]
		if (button)
			button.release = true
		return false
	}

	@Override
	boolean axisMoved(com.badlogic.gdx.controllers.Controller controller, int axisCode, float value) {
		DirectInputControllerAxis axis = axisMap[axisCode]
		if (axis) {
			if (axis.minAbsValue) {
				if (Math.abs(value) < Math.abs(axis.minAbsValue))
					axis.minAbsValue = value
				if (Math.abs(value) > Math.abs(axis.maxAbsValue))
					axis.maxAbsValue = value
			} else {
				axis.minAbsValue = value
				axis.maxAbsValue = value
			}
		}
		return false
	}

	@Override
	boolean povMoved(com.badlogic.gdx.controllers.Controller controller, int povCode, PovDirection value) {
		return false
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