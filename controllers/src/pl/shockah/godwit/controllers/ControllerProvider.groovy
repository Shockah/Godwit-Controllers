package pl.shockah.godwit.controllers

import groovy.transform.CompileStatic

@CompileStatic
interface ControllerProvider {
    List<Controller> getControllers()

    void onUpdate()

    void postUpdate()
}