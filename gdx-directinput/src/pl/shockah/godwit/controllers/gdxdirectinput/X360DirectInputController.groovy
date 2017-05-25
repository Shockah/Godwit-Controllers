package pl.shockah.godwit.controllers.gdxdirectinput

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.PovDirection
import groovy.transform.CompileStatic

@CompileStatic
class X360DirectInputController extends DirectInputController implements ControllerListener {
	public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
	public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
	public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
	public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;

	X360DirectInputController(Controller controller) {
		super(controller)
	}

	@Override
	void setupComponents() {

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