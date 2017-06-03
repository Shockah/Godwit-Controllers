package pl.shockah.godwit.controllers.desktop

import com.badlogic.gdx.Gdx
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerButton
import pl.shockah.godwit.controllers.ControllerButtonState

@CompileStatic
class DesktopMouseControllerButton extends ControllerButton {
	final int buttonCode

	DesktopMouseControllerButton(Controller controller, String name, int buttonCode) {
		super(controller, name)
		this.buttonCode = buttonCode
	}

	@Override
	ControllerButtonState getState() {
		ControllerButtonState lastState = this.lastState
		boolean isDown = Gdx.input.isButtonPressed(buttonCode)
		return new ControllerButtonState(
				this,
				isDown,
				lastState ? !lastState.isDown && isDown : isDown,
				lastState ? lastState.isDown && !isDown : false
		)
	}
}