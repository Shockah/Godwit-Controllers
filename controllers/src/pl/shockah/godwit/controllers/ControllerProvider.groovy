package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerProvider {
    boolean enabled = true
    abstract List<Controller> getControllers()

    final List<Controller> getConnectedControllers() {
        return controllers.findAll { it.connected }
    }

    void onUpdate() {
        for (Controller controller : controllers) {
            controller.onUpdate()
        }
    }

    void postUpdate() {
        for (Controller controller : controllers) {
            controller.postUpdate()
        }
    }
}