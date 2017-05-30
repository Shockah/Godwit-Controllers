package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAnalog
import pl.shockah.godwit.controllers.ControllerAxis
import pl.shockah.godwit.controllers.ControllerPov

@CompileStatic
class X360DirectInputWindowsController extends DirectInputController {
	final Map<Button, DirectInputControllerButton> buttonMap = new HashMap<>()
	final Map<Axis, DirectInputControllerAxis> axisMap = new HashMap<>()

	X360DirectInputWindowsController(Controller controller) {
		super(controller)
	}

	@Override
	protected void setupComponents() {
		registerPov(ControllerPov.fakePovFromButtons(
				new DirectInputControllerButton(this, "POV West", 13),
				new DirectInputControllerButton(this, "POV East", 11),
				new DirectInputControllerButton(this, "POV North", 10),
				new DirectInputControllerButton(this, "POV South", 12),
				"POV"
		))

		for (Button button : Button.values()) {
			DirectInputControllerButton controllerButton = new DirectInputControllerButton(this, button.name, button.code)
			buttonMap[button] = controllerButton
			super.buttonMap[button.code] = controllerButton
			registerButton(controllerButton)
		}

		for (Axis axis : Axis.values()) {
			DirectInputControllerAxis controllerAxis = new DirectInputControllerAxis(this, axis.name, axis.code)
			if (axis.reversed)
				controllerAxis.mappedRange = controllerAxis.mappedRange.reversed
			axisMap[axis] = controllerAxis
			super.axisMap[axis.code] = controllerAxis
		}

		registerAnalog(new ControllerAnalog(axisMap[Axis.LeftX], axisMap[Axis.LeftY], "Left Analog"))
		registerAnalog(new ControllerAnalog(axisMap[Axis.RightX], axisMap[Axis.RightY], "Right Analog"))

		axisMap[Axis.LeftTrigger].mappedRange = new ControllerAxis.MappedRange(0f, 1f)
		registerAxis(axisMap[Axis.LeftTrigger])

		axisMap[Axis.RightTrigger].mappedRange = new ControllerAxis.MappedRange(0f, 1f)
		registerAxis(axisMap[Axis.RightTrigger])
	}

	static enum Button {
		A(8), B(7), X(6), Y(5),
		LeftShoulder(4, "Left Button"), RightShoulder(3, "Right Button"),
		Back(2), Start(1),
		LeftStick(0, "Left Stick"), RightStick(9, "Right Stick")

		private final int code
		private final String name

		Button(int code, String name = null) {
			this.code = code
			this.name = name
		}

		int getCode() {
			return code
		}

		String getName() {
			return name ?: name()
		}
	}

	static enum Axis {
		LeftX(0), LeftY(1, null, true),
		RightX(2), RightY(3, null, true),
		LeftTrigger(4, "Left Trigger"),
		RightTrigger(5, "Right Trigger")

		private final int code
		private final String name
		private final boolean reversed

		Axis(int code, String name = null, boolean reversed = false) {
			this.code = code
			this.name = name
			this.reversed = reversed
		}

		int getCode() {
			return code
		}

		String getName() {
			return name ? name : name()
		}

		boolean isReversed() {
			return reversed
		}
	}
}