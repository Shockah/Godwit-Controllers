package pl.shockah.godwit.controllers.desktop

import com.badlogic.gdx.Gdx
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerButton
import pl.shockah.godwit.controllers.ControllerButtonState

@CompileStatic
class DesktopKeyboardControllerButton extends ControllerButton {
	final int keyCode

	DesktopKeyboardControllerButton(Controller controller, String name, int keyCode) {
		super(controller, name)
		this.keyCode = keyCode
	}

	@Override
	ControllerButtonState getState() {
		ControllerButtonState lastState = this.lastState
		boolean isDown = Gdx.input.isKeyPressed(keyCode)
		return new ControllerButtonState(
				this,
				isDown,
				Gdx.input.isKeyJustPressed(keyCode),
				lastState ? lastState.isDown && !isDown : false
		)
	}
}