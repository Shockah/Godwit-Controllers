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
				this,
				PovDirection.center
		)
	}

	@Override
	protected void onUpdate() {
		lastState = cachedState
		super.onUpdate()
	}

	@Override
	protected void postUpdate() {
		cachedState = state
		super.postUpdate()
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
						this,
						isDown,
						!getLastState().isDown && isDown,
						getLastState().isDown && !isDown
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
						this,
						StaticExtensions.getDirectionFromStates(null, left.state.isDown, right.state.isDown, up.state.isDown, down.state.isDown)
				)
			}
		}
	}
}