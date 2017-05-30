package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.desktop.ois.OisJoystick
import groovy.transform.CompileStatic

import java.lang.reflect.Field

@CompileStatic
class GenericOisDirectInputController extends DirectInputController {
	GenericOisDirectInputController(Controller controller) {
		super(controller)
	}

	@Override
	protected void setupComponents() {
		Field joystickField = Class.forName("com.badlogic.gdx.controllers.desktop.OisControllers\$OisController").getField("joystick")
		joystickField.accessible = true
		OisJoystick joystick = (OisJoystick)joystickField.get(controller)

		for (int i in 0..<joystick.povCount) {
			String name = joystick.povCount == 1 ? "POV" : "POV ${i + 1}"
			registerPov(new DirectInputControllerPov(this, name, i))
		}
		for (int i in 0..<joystick.buttonCount) {
			registerButton(new DirectInputControllerButton(this, "Button ${i + 1}" as String, i))
		}
		for (int i in 0..<joystick.axisCount) {
			registerAxis(new DirectInputControllerAxis(this, "Axis ${i + 1}" as String, i))
		}
	}
}