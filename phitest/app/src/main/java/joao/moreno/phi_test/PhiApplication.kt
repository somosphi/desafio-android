package joao.moreno.phi_test


import androidx.multidex.MultiDexApplication


class PhiApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: PhiApplication
            private set

    }


}