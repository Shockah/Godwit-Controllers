package pl.shockah.godwit.controllers.lwjgl3

import com.badlogic.gdx.controllers.Controller
import groovy.transform.CompileStatic

@CompileStatic
interface Lwjgl3ControllerImplementationProvider {
	Lwjgl3Controller provide(Controller controller)
}