package pl.shockah.godwit.controllers.directinput

import com.badlogic.gdx.controllers.ControllerAdapter
import com.badlogic.gdx.controllers.Controllers
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerProvider

@CompileStatic
class DirectInputControllerProvider extends ControllerAdapter implements ControllerProvider {
    protected final Map<com.badlogic.gdx.controllers.Controller, DirectInputController> nativeToAbstractMap = new LinkedHashMap<>()
    protected final Set<DirectInputControllerImplementationProvider> implementationProviders = new LinkedHashSet<>()

    DirectInputControllerProvider() {
        Controllers.addListener(this)
    }

    @Override
    List<Controller> getControllers() {
        return nativeToAbstractMap.values() as List<Controller>
    }

    @Override
    void onUpdate() {
        for (DirectInputController abstractController : nativeToAbstractMap.values()) {
            abstractController.onUpdate()
        }
    }

    @Override
    void postUpdate() {
        for (DirectInputController abstractController : nativeToAbstractMap.values()) {
            abstractController.postUpdate()
        }
    }

    @Override
    void connected(com.badlogic.gdx.controllers.Controller controller) {
        DirectInputController abstractController = nativeToAbstractMap[controller]
        if (!abstractController) {
            for (DirectInputControllerImplementationProvider implementationProvider : implementationProviders) {
                abstractController = implementationProvider.provide(controller)
                if (abstractController) {
                    nativeToAbstractMap[controller] = abstractController
                    break
                }
            }
        }
        abstractController.onUpdate()
        abstractController.postUpdate()
    }

    @Override
    void disconnected(com.badlogic.gdx.controllers.Controller controller) {
        DirectInputController abstractController = nativeToAbstractMap[controller]
        if (abstractController) {
            abstractController.onUpdate()
            abstractController.postUpdate()
            nativeToAbstractMap.remove(controller)
        }
    }
}