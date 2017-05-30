package pl.shockah.godwit.controllers.xinput

import com.ivan.xinput.XInputBatteryInformation
import com.ivan.xinput.XInputDevice
import com.ivan.xinput.XInputDevice14
import com.ivan.xinput.enums.XInputAxis
import com.ivan.xinput.enums.XInputBatteryDeviceType
import com.ivan.xinput.enums.XInputBatteryLevel
import com.ivan.xinput.enums.XInputBatteryType
import com.ivan.xinput.enums.XInputButton
import com.ivan.xinput.listener.XInputDeviceListener
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerAnalog
import pl.shockah.godwit.controllers.ControllerAxis

@CompileStatic
class XInputController extends Controller implements XInputDeviceListener {
	public static final Set<XInputButton> POV_BUTTONS = Collections.unmodifiableSet(EnumSet.of(
			XInputButton.DPAD_LEFT, XInputButton.DPAD_RIGHT, XInputButton.DPAD_UP, XInputButton.DPAD_DOWN
	))

	protected final XInputDevice device

	protected Map<XInputButton, XInputControllerButton> buttonMap = new HashMap<>()
	protected Map<XInputAxis, XInputControllerAxis> axisMap = new HashMap<>()

	XInputController(XInputDevice device) {
		this.device = device

		registerPov(new XInputControllerPov(this))

		for (XInputButton button : XInputButton.values()) {
			if (button in POV_BUTTONS)
				continue
			XInputControllerButton controllerButton = new XInputControllerButton(this, button)
			buttonMap[button] = controllerButton
			registerButton(controllerButton)
		}

		for (XInputAxis axis in [
				XInputAxis.LEFT_THUMBSTICK_X, XInputAxis.LEFT_THUMBSTICK_Y,
				XInputAxis.RIGHT_THUMBSTICK_X, XInputAxis.RIGHT_THUMBSTICK_Y,
				XInputAxis.LEFT_TRIGGER, XInputAxis.RIGHT_TRIGGER
		]) {
			ControllerAxis.MappedRange mappedRange = new ControllerAxis.MappedRange()
			if (axis in [XInputAxis.LEFT_THUMBSTICK_Y, XInputAxis.RIGHT_THUMBSTICK_Y])
				mappedRange = mappedRange.reversed
			XInputControllerAxis controllerAxis = new XInputControllerAxis(this, axis, mappedRange)
			axisMap[axis] = controllerAxis
		}

		registerAnalog(new ControllerAnalog(axisMap[XInputAxis.LEFT_THUMBSTICK_X], axisMap[XInputAxis.LEFT_THUMBSTICK_Y], "Left Analog"))
		registerAnalog(new ControllerAnalog(axisMap[XInputAxis.RIGHT_THUMBSTICK_X], axisMap[XInputAxis.RIGHT_THUMBSTICK_Y], "Right Analog"))

		registerAxis(axisMap[XInputAxis.LEFT_TRIGGER])
		registerAxis(axisMap[XInputAxis.RIGHT_TRIGGER])
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
		device.poll()
	}

	@Override
	boolean isConnected() {
		return device.isConnected()
	}

	@Override
	Float getBatteryStatus() {
		if (device instanceof XInputDevice14) {
			XInputDevice14 device14 = device as XInputDevice14
			XInputBatteryInformation batteryInfo = device14.getBatteryInformation(XInputBatteryDeviceType.GAMEPAD)
			if (batteryInfo) {
				if (batteryInfo.type == XInputBatteryType.WIRED)
					return null
				else
					return 1f * batteryInfo.level.ordinal() / XInputBatteryLevel.values().length
			}
		}
		return null
	}

	@Override
	void connected() {
	}

	@Override
	void disconnected() {
	}

	@Override
	void buttonChanged(XInputButton button, boolean pressed) {
		XInputControllerButton controllerButton = buttonMap[button]
		if (controllerButton) {
			if (pressed)
				controllerButton.press = true
			else
				controllerButton.release = true
		}
	}
}