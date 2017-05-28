package pl.shockah.godwit.controllers

import com.badlogic.gdx.controllers.PovDirection
import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerPov extends ControllerComponent {
	protected ControllerPovState cachedState
	protected ControllerPovState lastState

	ControllerPov(Controller controller, String name) {
		super(controller, name)
		lastState = new ControllerPovState(
				pov: this,
				direction: PovDirection.center
		)
	}

	@Override
	protected void onUpdate() {
		super.onUpdate()
		lastState = cachedState
	}

	@Override
	protected void postUpdate() {
		super.postUpdate()
		cachedState = state
	}

	abstract ControllerPovState getState()

	ControllerPovState getLastState() {
		return lastState
	}

	ControllerButton asFakeButton(PovDirection direction) {
		assert direction in StaticExtensions.cardinalDirections
		return new ControllerButton(controller, "${name}->${direction} as Button") {
			@Override
			ControllerButtonState getState() {
				ControllerPovState povState = ControllerPov.this.state
				boolean isDown = povState.direction in Extensions.getWithNeighbors(direction)
				return new ControllerButtonState(
						button: this,
						isDown: isDown,
						didPress: !lastState.isDown && isDown,
						didRelease: lastState.isDown && !isDown
				)
			}
		}
	}

	static ControllerPov fakePovFromButtons(ControllerButton left, ControllerButton right, ControllerButton up, ControllerButton down) {
		assert left.controller == right.controller
		assert left.controller == up.controller
		assert left.controller == down.controller
		assert ([left, right, up, down] as Set<ControllerButton>).size() == 4

		return new ControllerPov(left.controller, "${[left, right, up, down].join(", ")} as POV") {
			@Override
			ControllerPovState getState() {
				return new ControllerPovState(
						pov: this,
						direction: StaticExtensions.getDirectionFromStates(null, left.state.isDown, right.state.isDown, up.state.isDown, down.state.isDown)
				)
			}
		}
	}
}