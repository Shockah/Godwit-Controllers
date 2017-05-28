package pl.shockah.godwit.controllers

import com.badlogic.gdx.controllers.PovDirection
import groovy.transform.CompileStatic

@CompileStatic
final class ControllerPovState {
	final ControllerPov pov
	final PovDirection direction

	ControllerPovState(ControllerPov pov, PovDirection direction) {
		this.pov = pov
		this.direction = direction
	}
}