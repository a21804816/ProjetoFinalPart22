import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler

class ShakeDetector(private val context: Context, private val onShake: () -> Unit) : SensorEventListener {

    private val shakeThresholdGravity = 2.7F
    private val shakeSlopTimeMs = 500
    private val shakeCountResetTimeMs = 3000

    private var shakeTimestamp: Long = 0
    private var shakeCount: Int = 0

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val shakeHandler = Handler()

    private val shakeRunnable = Runnable {
        onShake.invoke()
    }

    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH

            val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

            if (gForce > shakeThresholdGravity) {
                val now = System.currentTimeMillis()
                if (shakeTimestamp + shakeSlopTimeMs > now) {
                    return
                }

                if (shakeTimestamp + shakeCountResetTimeMs < now) {
                    shakeCount = 0
                }

                shakeTimestamp = now
                shakeCount++

                shakeHandler.removeCallbacks(shakeRunnable)
                shakeHandler.postDelayed(shakeRunnable, shakeSlopTimeMs.toLong())
            }
        }
    }
}
