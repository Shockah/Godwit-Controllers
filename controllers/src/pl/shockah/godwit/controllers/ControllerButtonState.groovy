package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
final class ControllerButtonState {
	final ControllerButton button
	final boolean isDown
	final boolean didPress
	final boolean didRelease
}