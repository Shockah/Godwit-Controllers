package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAnalog

@CompileStatic
class X360DirectInputController extends DirectInputController {
	final Map<Button, DirectInputControllerButton> buttonMap = new HashMap<>()
	final Map<Axis, DirectInputControllerAxis> axisMap = new HashMap<>()

	X360DirectInputController(Controller controller) {
		super(controller)
	}

	@Override
	void setupComponents() {
		registerPov(new DirectInputControllerPov(this, "POV", 0))

		for (Button button : Button.values()) {
			DirectInputControllerButton controllerButton = new DirectInputControllerButton(this, button.name, button.code)
			buttonMap[button] = controllerButton
			super.buttonMap[button.code] = controllerButton
			registerButton(controllerButton)
		}

		for (Axis axis : Axis.values()) {
			DirectInputControllerAxis controllerAxis = new DirectInputControllerAxis(this, axis.name, axis.code)
			axisMap[axis] = controllerAxis
			super.axisMap[axis.code] = controllerAxis
		}

		registerAnalog(new ControllerAnalog(axisMap[Axis.LeftX], axisMap[Axis.LeftY], "Left Analog"))
		registerAnalog(new ControllerAnalog(axisMap[Axis.RightX], axisMap[Axis.RightY], "Right Analog"))

		registerAxis(axisMap[Axis.Triggers])
	}

	static enum Button {
		A(0), B(1), X(2), Y(3),
		LeftShoulder(4, "Left Button"), RightShoulder(5, "Right Button"),
		Back(6), Start(7),
		LeftStick(8, "Left Stick"), RightStick(9, "Right Stick")

		private final int code
		private final String name

		Button(int code, String name = null) {
			this.code = code
		}

		int getCode() {
			return code
		}

		String getName() {
			return name ? name : name()
		}
	}

	static enum Axis {
		LeftX(1), LeftY(0),
		RightX(3), RightY(2),
		Triggers(4, null, true) // 0f -> -1f is L, 0f -> 1f is R

		private final int code
		private final String name
		private final boolean reversed

		Axis(int code, String name = null, boolean reversed = false) {
			this.code = code
			this.reversed = reversed
		}

		int getCode() {
			return code
		}

		String getName() {
			return name ? name : name()
		}
	}
}