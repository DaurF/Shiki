package mob.dau.ren.shiki

import android.app.Application
import mob.dau.ren.shiki.database.ShikiDatabase

class ShikiApplication : Application() {
    val database: ShikiDatabase by lazy {
        ShikiDatabase.getDatabase(this)
    }
}