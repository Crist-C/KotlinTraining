/**
 * Resumen
    OOP consta de cuatro principios principales: encapsulamiento, abstracción, herencia y polimorfismo.
    Las clases se definen con la palabra clave class y contienen propiedades y métodos.
    Las propiedades son similares a las variables, excepto que pueden tener métodos get y set personalizados.
    Un constructor especifica cómo crear instancias de los objetos de una clase.
    Puedes omitir la palabra clave constructor cuando defines un constructor principal.
    La herencia facilita la reutilización de código.
    La relación IS-A se refiere a la herencia.
    La relación HAS-A se refiere a la composición.
    Los modificadores de visibilidad son importantes para lograr el encapsulamiento.
    Kotlin ofrece cuatro modificadores de visibilidad: public, private, protected y internal.
    Un delegado de propiedad te permite reutilizar el código get y set en varias clases.
 */

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class RangeRegulator (
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue){
            fieldData = value
        }
    }

}

open class SmartDevice protected constructor(val name: String, val category: String) {

    var deviceStatus = "offline"

    open val deviceType = "unknow"

    protected constructor(name: String, category: String, statusCode: Int) : this(name = name, category = category) {
        deviceStatus = when(statusCode){
            0 -> "Offline"
            1 -> "Online"
            else -> "Unknow"
        }
    }


    open fun turnOn() {
        deviceStatus = "online"
    }

    open fun turnOff() {
        deviceStatus = "offline"
    }

    open fun printDeviceInfo(){
        println("Device name: $name, category: $category, type: $deviceType")
    }

}

class SmartTv (deviceName: String, deviceCategory: String, deviceStatusCode: Int = 1) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "SmartTv"

    private var speakerVolume by RangeRegulator(10, 0, 100)

    private var channelNumber by RangeRegulator(1,1,200)

    fun decreaseSpeakerVolume(){
        println("Speaker volume decreased to ${--speakerVolume}")
    }

    fun increaseSpeakerVolume(){
        println("Speaker volume increased to ${++speakerVolume}")
    }

    fun previousChannel() {
        println("Channel number increased to ${--channelNumber}")
    }

    fun nextChannel() {
        println("Channel number increased to ${++channelNumber}")
    }

    override fun turnOn() {
        super.turnOn()
        println("$name is turned on. Speaker volume is set to $speakerVolume and channel number is set to $channelNumber.")
    }

    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }

}

class SmartLightDevice(deviceName: String, deviceCategory: String, deviceStatusCode: Int = 1)
    : SmartDevice(name = deviceName, category = deviceCategory, statusCode = deviceStatusCode) {

    override val deviceType: String = "Smart Light"

    private var brightnessLevel by RangeRegulator(0,0,100)

    fun decreaseBrightness(){
        println("Brightness increased to ${--brightnessLevel}")
    }

    fun increaseBrightness(){
        println("Brightness increased to ${++brightnessLevel}")
    }

    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 15
        println("$name turned on. The brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart light turned off")
    }
}

class SmartHome (
    val smartTv: SmartTv,
    val smartLight: SmartLightDevice
) {

    private var deviceTurnOnCount = 0

    fun turnOnTv(){
        smartTv.turnOn()
        deviceTurnOnCount++
    }

    fun turnOffTv(){
        smartTv.turnOff()
        deviceTurnOnCount--
    }

    fun turnOnLight(){
        smartLight.turnOn()
        deviceTurnOnCount++
    }

    fun turnOffLight(){
        smartLight.turnOff()
        deviceTurnOnCount--
    }

    fun decreaseTvVolume(){
        validateDoAction(smartTv,{smartTv.decreaseSpeakerVolume()})
    }

    fun increaseTvVolume(){
        validateDoAction(smartTv, {smartTv.increaseSpeakerVolume()})
    }

    fun changeTvChannelToPrevious(){
        smartTv.previousChannel()
    }
    fun changeTvChannelToNext(){
        smartTv.nextChannel()
    }

    fun decreaseLightBrightness(){
        smartLight.decreaseBrightness()
    }

    fun increaseLightBrightness() {
        smartLight.increaseBrightness()
    }

    fun turnOffAllDevices() {
        smartTv.turnOff()
        smartLight.turnOff()
    }

    fun printSmartTvInfo(){
        smartTv.printDeviceInfo()
    }

    fun printSmartLightInfo(){
        smartLight.printDeviceInfo()
    }

    fun validateDoAction(smartDevice: SmartDevice,function: () -> Unit){
        if (smartDevice.deviceStatus == "online"){
            function()
        }else{
            println("${smartDevice.name} estate is ${smartDevice.deviceStatus}")
        }
    }
}

fun main() {

    val smartHome = SmartHome(
        SmartTv("Android TV 2", deviceCategory = "Entertainment"),
        SmartLightDevice("Google Light", "Utility")
    )

    smartHome.increaseTvVolume()
    smartHome.increaseTvVolume()
    smartHome.turnOnTv()
    smartHome.increaseTvVolume()
    smartHome.increaseTvVolume()

}
