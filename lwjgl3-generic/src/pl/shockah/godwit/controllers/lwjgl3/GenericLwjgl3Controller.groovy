package pl.shockah.godwit.controllers.lwjgl3

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

import java.lang.reflect.Field

@CompileStatic
class GenericLwjgl3Controller extends Lwjgl3Controller {
	GenericLwjgl3Controller(Controller controller) {
		super(controller)
	}

	@Override
	protected void setupComponents() {
		Field axisStateField = Class.forName("com.badlogic.gdx.controllers.lwjgl3.Lwjgl3Controller").getDeclaredField("axisState")
		axisStateField.accessible = true
		boolean[] axisState = (boolean[])axisStateField.get(controller)

		Field buttonStateField = Class.forName("com.badlogic.gdx.controllers.lwjgl3.Lwjgl3Controller").getDeclaredField("buttonState")
		buttonStateField.accessible = true
		boolean[] buttonState = (boolean[])buttonStateField.get(controller)

		for (int i in 0..<buttonState.length) {
			registerButton(new Lwjgl3ControllerButton(this, "Button ${i + 1}" as String, i))
		}
		for (int i in 0..<axisState.length) {
			registerAxis(new Lwjgl3ControllerAxis(this, "Axis ${i + 1}" as String, i))
		}
	}
}