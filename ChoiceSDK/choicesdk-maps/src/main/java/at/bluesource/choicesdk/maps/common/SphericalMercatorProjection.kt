package at.bluesource.choicesdk.maps.common

import at.bluesource.choicesdk.core.MobileServicesDetector
import at.bluesource.choicesdk.maps.gms.GmsSphericalMercatorProjectionFactory
import at.bluesource.choicesdk.maps.hms.HmsSphericalMercatorProjectionFactory

abstract class SphericalMercatorProjection {

    abstract fun toPoint(latLng: LatLng): Point
    abstract fun toLatLng(point: Point): LatLng

    interface Factory {
        fun create(worldWidth: Double): SphericalMercatorProjection
    }

    companion object {
        @JvmStatic
        fun getFactory(): Factory {
            return when {
                MobileServicesDetector.isGmsAvailable() -> GmsSphericalMercatorProjectionFactory()
                MobileServicesDetector.isHmsAvailable() -> HmsSphericalMercatorProjectionFactory()
                else -> throw IllegalStateException("Neither GMS nor HMS services are available.")
            }
        }
    }

}