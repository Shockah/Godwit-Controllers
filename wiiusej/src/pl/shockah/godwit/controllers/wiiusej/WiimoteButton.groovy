package pl.shockah.godwit.controllers.wiiusej

import groovy.transform.CompileStatic
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent

@CompileStatic
enum WiimoteButton {
	A(), B(), One("1"), Two("2"), Plus("+"), Minus("-"), Home()

	private final String customName

	WiimoteButton(String customName = null) {
		this.customName = customName
	}

	String getName() {
		return customName ? customName : name()
	}

	boolean isDown(WiimoteButtonsEvent event) {
		switch (this) {
			case A:
				return event.buttonAPressed
			case B:
				return event.buttonBPressed
			case One:
				return event.buttonOnePressed
			case Two:
				return event.buttonTwoPressed
			case Plus:
				return event.buttonPlusPressed
			case Minus:
				return event.buttonMinusPressed
			case Home:
				return event.buttonHomePressed
		}
	}

	boolean isPressed(WiimoteButtonsEvent event) {
		switch (this) {
			case A:
				return event.buttonAJustPressed
			case B:
				return event.buttonBJustPressed
			case One:
				return event.buttonOneJustPressed
			case Two:
				return event.buttonTwoJustPressed
			case Plus:
				return event.buttonPlusJustPressed
			case Minus:
				return event.buttonMinusJustPressed
			case Home:
				return event.buttonHomeJustPressed
		}
	}

	boolean isReleased(WiimoteButtonsEvent event) {
		switch (this) {
			case A:
				return event.buttonAJustReleased
			case B:
				return event.buttonBJustReleased
			case One:
				return event.buttonOneJustReleased
			case Two:
				return event.buttonTwoJustReleased
			case Plus:
				return event.buttonPlusJustReleased
			case Minus:
				return event.buttonMinusJustReleased
			case Home:
				return event.buttonHomeJustReleased
		}
	}
}