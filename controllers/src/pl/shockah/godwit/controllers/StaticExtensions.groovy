package pl.shockah.godwit.controllers

import com.badlogic.gdx.controllers.PovDirection
import groovy.transform.CompileStatic

@CompileStatic
final class StaticExtensions {
	static PovDirection getDirectionFromStates(final PovDirection _, boolean left, boolean right, boolean up, boolean down) {
		if (left && right) {
			left = false
			right = false
		}
		if (up && down) {
			up = false
			down = false
		}

		if (left && up)
			return PovDirection.northWest
		else if (up)
			return PovDirection.north
		else if (up && right)
			return PovDirection.northEast
		else if (right)
			return PovDirection.east
		else if (right && down)
			return PovDirection.southEast
		else if (down)
			return PovDirection.south
		else if (down && left)
			return PovDirection.southWest
		else if (left)
			return PovDirection.west
		else
			return PovDirection.center
	}
}