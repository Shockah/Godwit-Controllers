package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.ControllerAnalog

@CompileStatic
class X360DirectInputController extends DirectInputController {
	X360DirectInputController(Controller controller) {
		super(controller)
	}

	@Override
	void setupComponents() {
		povs << new DirectInputControllerPov(this, 0)

		for (Button button : Button.values()) {
			buttonMap[button.code] = new DirectInputControllerButton(this, button.code)
			buttons << buttonMap[button.code]
		}

		for (Axis axis : Axis.values()) {
			axisMap[axis.code] = new DirectInputControllerAxis(this, axis.code)
		}

		analogs << new ControllerAnalog(axisMap[Axis.LeftX.code], axisMap[Axis.LeftY.code])
		analogs << new ControllerAnalog(axisMap[Axis.RightX.code], axisMap[Axis.RightY.code])

		axes << new DirectInputControllerAxis(this, Axis.Triggers.code)
	}

	static enum Button {
		A(0), B(1), X(2), Y(3),
		LeftShoulder(4), RightShoulder(5),
		Back(6), Start(7),
		LeftStick(8), RightStick(9)

		private final int code

		Button(int code) {
			this.code = code
		}

		int getCode() {
			return code
		}
	}

	static enum Axis {
		LeftX(1), LeftY(0),
		RightX(3), RightY(2),
		Triggers(4, true) // 0f -> -1f is L, 0f -> 1f is R

		private final int code
		private final boolean reversed

		Axis(int code, boolean reversed = false) {
			this.code = code
			this.reversed = reversed
		}

		int getCode() {
			return code
		}
	}
}