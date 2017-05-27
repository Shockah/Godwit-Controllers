package pl.shockah.godwit.controllers.xinput

import com.ivan.xinput.XInputDevice
import com.ivan.xinput.XInputDevice14
import groovy.transform.CompileStatic
import pl.shockah.godwit.controllers.Controller
import pl.shockah.godwit.controllers.ControllerProvider

@CompileStatic
class XInputControllerProvider extends ControllerProvider {
    protected final Map<XInputDevice, XInputController> nativeToAbstractMap = new LinkedHashMap<>()

    XInputControllerProvider() {
        if (XInputDevice14.available)
            setupDevices(XInputDevice14.allDevices)
        else
            setupDevices(XInputDevice.allDevices)
    }

    private void setupDevices(XInputDevice[] devices) {
        for (XInputDevice device : devices) {
            nativeToAbstractMap[device] = new XInputController(device)
        }
    }

    @Override
    List<Controller> getControllers() {
        return nativeToAbstractMap.values() as List<Controller>
    }
}