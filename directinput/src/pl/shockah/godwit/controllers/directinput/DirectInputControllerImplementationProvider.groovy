package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

@CompileStatic
interface DirectInputControllerImplementationProvider {
	DirectInputController provide(Controller controller)
}