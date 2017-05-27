package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
final class ControllerAxisState {
	final ControllerAxis axis
	final float value
	final float minAbsValue
	final float maxAbsValue
}