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

	ControllerAxis asFakeAxis(PovDirection direction, float releasedValue = 0f, float pressedValue = 1f) {
		return asFakeButton(direction).asFakeAxis(releasedValue, pressedValue)
	}

	ControllerAnalog asFakeAnalog(float releasedValue = 0f, float pressedValue = 1f) {
		return new ControllerAnalog(
				asFakeAxis(PovDirection.east, releasedValue, pressedValue),
				asFakeAxis(PovDirection.south, releasedValue, pressedValue),
				"${name} as Analog"
		)
	}

	static ControllerPov fakePovFromButtons(ControllerButton left, ControllerButton right, ControllerButton up, ControllerButton down, String customName = null) {
		assert left.controller == right.controller
		assert left.controller == up.controller
		assert left.controller == down.controller
		assert ([left, right, up, down] as Set<ControllerButton>).size() == 4
		return new FakePovFromButtons(left, right, up, down, customName ? customName : "${[left, right, up, down].join(", ")} as POV" as String)
	}

	private static final class FakePovFromButtons extends ControllerPov {
		ControllerButton left
		ControllerButton right
		ControllerButton up
		ControllerButton down

		FakePovFromButtons(ControllerButton left, ControllerButton right, ControllerButton up, ControllerButton down, String name) {
			super(left.controller, name)
			this.left = left
			this.right = right
			this.up = up
			this.down = down
		}

		@Override
		ControllerPovState getState() {
			return new ControllerPovState(
					this,
					StaticExtensions.getDirectionFromStates(null, left.state.isDown, right.state.isDown, up.state.isDown, down.state.isDown)
			)
		}
	}
}