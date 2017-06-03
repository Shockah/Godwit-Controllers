package pl.shockah.godwit.controllers.lwjgl3

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAnalog
import pl.shockah.godwit.controllers.ControllerAxis
import pl.shockah.godwit.controllers.ControllerPov

@CompileStatic
class X360Lwjgl3OSXController extends Lwjgl3Controller {
	final Map<Button, Lwjgl3ControllerButton> buttonMap = new HashMap<>()
	final Map<Axis, Lwjgl3ControllerAxis> axisMap = new HashMap<>()

	X360Lwjgl3OSXController(Controller controller) {
		super(controller)
	}

	@Override
	protected void setupComponents() {
		registerPov(ControllerPov.fakePovFromButtons(
				new Lwjgl3ControllerButton(this, "POV West", 2),
				new Lwjgl3ControllerButton(this, "POV East", 3),
				new Lwjgl3ControllerButton(this, "POV North", 0),
				new Lwjgl3ControllerButton(this, "POV South", 1),
				"POV"
		))

		for (Button button : Button.values()) {
			Lwjgl3ControllerButton controllerButton = new Lwjgl3ControllerButton(this, button.name, button.code)
			buttonMap[button] = controllerButton
			super.buttonMap[button.code] = controllerButton
			registerButton(controllerButton)
		}

		for (Axis axis : Axis.values()) {
			Lwjgl3ControllerAxis controllerAxis = new Lwjgl3ControllerAxis(this, axis.name, axis.code)
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
		A(11), B(12), X(13), Y(14),
		LeftShoulder(8, "Left Button"), RightShoulder(9, "Right Button"),
		Back(5), Start(4),
		LeftStick(6, "Left Stick"), RightStick(7, "Right Stick"),
		Guide(10)

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
			return name ? name : name()
		}
	}

	static enum Axis {
		LeftX(0), LeftY(1),
		RightX(2), RightY(3),
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
	}
}