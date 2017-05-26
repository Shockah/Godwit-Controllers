package pl.shockah.godwit.controllers.xinput

import com.ivan.xinput.XInputButtons
import com.ivan.xinput.enums.XInputButton
import groovy.transform.CompileStatic

@CompileStatic
final class Extensions {
	static String getName(XInputButton self) {
		switch (self) {
			case XInputButton.BACK:
				return "Back"
			case XInputButton.START:
				return "Start"
			case XInputButton.LEFT_SHOULDER:
				return "Left Button"
			case XInputButton.RIGHT_SHOULDER:
				return "Right Button"
			case XInputButton.LEFT_THUMBSTICK:
				return "Left Stick"
			case XInputButton.RIGHT_THUMBSTICK:
				return "Right Stick"
			case XInputButton.DPAD_UP:
				return "POV Up"
			case XInputButton.DPAD_DOWN:
				return "POV Down"
			case XInputButton.DPAD_LEFT:
				return "POV Left"
			case XInputButton.DPAD_RIGHT:
				return "POV Right"
			case XInputButton.GUIDE_BUTTON:
				return "Guide"
			default:
				return self.name()
		}
	}

	static boolean isDown(XInputButtons self, XInputButton button) {
		switch (button) {
			case XInputButton.A:
				return self.a
			case XInputButton.B:
				return self.b
			case XInputButton.X:
				return self.x
			case XInputButton.Y:
				return self.y
			case XInputButton.BACK:
				return self.back
			case XInputButton.START:
				return self.start
			case XInputButton.LEFT_SHOULDER:
				return self.lShoulder
			case XInputButton.RIGHT_SHOULDER:
				return self.rShoulder
			case XInputButton.LEFT_THUMBSTICK:
				return self.lThumb
			case XInputButton.RIGHT_THUMBSTICK:
				return self.rThumb
			case XInputButton.DPAD_UP:
				return self.up
			case XInputButton.DPAD_DOWN:
				return self.down
			case XInputButton.DPAD_LEFT:
				return self.left
			case XInputButton.DPAD_RIGHT:
				return self.right
			case XInputButton.GUIDE_BUTTON:
				return self.guide
		}
	}
}