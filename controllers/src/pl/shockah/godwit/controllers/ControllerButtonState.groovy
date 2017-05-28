package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
final class ControllerButtonState {
	final ControllerButton button
	final boolean isDown
	final boolean didPress
	final boolean didRelease

	ControllerButtonState(ControllerButton button, boolean isDown, boolean didPress, boolean didRelease) {
		this.button = button
		this.isDown = isDown
		this.didPress = didPress
		this.didRelease = didRelease
	}
}