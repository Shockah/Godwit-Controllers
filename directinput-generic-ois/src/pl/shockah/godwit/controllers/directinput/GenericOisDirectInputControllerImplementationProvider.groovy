package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

@CompileStatic
class GenericOisDirectInputControllerImplementationProvider implements DirectInputControllerImplementationProvider {
	@Override
	DirectInputController provide(Controller controller) {
		try {
			if (Class.forName("com.badlogic.gdx.controllers.desktop.OisControllers\$OisController").isInstance(controller))
				return new GenericOisDirectInputController(controller)
		} catch (Exception e) {
		}
		return null
	}
}