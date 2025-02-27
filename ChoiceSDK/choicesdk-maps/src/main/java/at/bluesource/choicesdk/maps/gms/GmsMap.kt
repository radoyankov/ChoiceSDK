package at.bluesource.choicesdk.maps.gms

import android.graphics.Bitmap
import android.location.Location
import androidx.annotation.RequiresPermission
import at.bluesource.choicesdk.maps.common.*
import at.bluesource.choicesdk.maps.common.CameraPosition.Companion.toChoiceCameraPosition
import at.bluesource.choicesdk.maps.common.GroundOverlay.Companion.toChoiceGroundOverlay
import at.bluesource.choicesdk.maps.common.InfoWindowAdapter.Companion.toGmsInfoWindowAdapter
import at.bluesource.choicesdk.maps.common.LatLng.Companion.toChoiceLatLng
import at.bluesource.choicesdk.maps.common.LatLngBounds.Companion.toGmsLatLngBounds
import at.bluesource.choicesdk.maps.common.Map
import at.bluesource.choicesdk.maps.common.Marker.Companion.toChoiceMarker
import at.bluesource.choicesdk.maps.common.PointOfInterest.Companion.toChoicePoi
import at.bluesource.choicesdk.maps.common.Projection.Companion.toChoiceProjection
import at.bluesource.choicesdk.maps.common.UiSettings.Companion.toChoiceUiSettings
import at.bluesource.choicesdk.maps.common.listener.*
import at.bluesource.choicesdk.maps.common.options.GroundOverlayOptions
import at.bluesource.choicesdk.maps.common.options.GroundOverlayOptions.Companion.toGmsGroundOverlayOptions
import at.bluesource.choicesdk.maps.common.options.MarkerOptions
import at.bluesource.choicesdk.maps.common.options.TileOverlay
import at.bluesource.choicesdk.maps.common.options.TileOverlayOptions
import at.bluesource.choicesdk.maps.common.options.TileOverlayOptions.Companion.toGmsTileOverlayOptions
import at.bluesource.choicesdk.maps.common.shape.*
import at.bluesource.choicesdk.maps.common.shape.Circle.Companion.toChoiceCircle
import at.bluesource.choicesdk.maps.common.shape.CircleOptions.Companion.toGmsCircleOptions
import at.bluesource.choicesdk.maps.common.shape.Polygon.Companion.toChoicePolygon
import at.bluesource.choicesdk.maps.common.shape.PolygonOptions.Companion.toGmsPolygonOptions
import at.bluesource.choicesdk.maps.common.shape.Polyline.Companion.toChoicePolyline
import at.bluesource.choicesdk.maps.common.shape.PolylineOptions.Companion.toGmsPolylineOptions
import com.google.android.gms.maps.GoogleMap
import com.huawei.hms.maps.HuaweiMap

/**
 * Wrapper class for gms version of Map
 *
 * @property map GoogleMap instance
 * @see com.google.android.gms.maps.GoogleMap
 */
internal class GmsMap(private val map: GoogleMap) : Map {
    override var mapType: Int
        get() = map.mapType
        set(value) {
            map.mapType = value
        }
    override var maxZoomLevel: Float
        get() = map.maxZoomLevel
        set(value) {
            map.setMaxZoomPreference(value)
        }
    override var minZoomLevel: Float
        get() = map.minZoomLevel
        set(value) {
            map.setMinZoomPreference(value)
        }
    override var isTrafficEnabled: Boolean
        get() = map.isTrafficEnabled
        set(value) {
            map.isTrafficEnabled = value
        }
    override var isIndoorEnabled: Boolean
        get() = map.isIndoorEnabled
        set(value) {
            map.isIndoorEnabled = value
        }
    override var isMyLocationEnabled: Boolean
        get() = map.isMyLocationEnabled
        @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
        set(value) {
            map.isMyLocationEnabled = value
        }
    override var isBuildingsEnabled: Boolean
        get() = map.isBuildingsEnabled
        set(value) {
            map.isBuildingsEnabled = value
        }
    override val cameraPosition: CameraPosition
        get() = map.cameraPosition.toChoiceCameraPosition()

    /***************
     * Listener
     ***************/
    override fun setOnCameraIdleListener(listener: OnCameraIdleListener) {
        map.setOnCameraIdleListener {
            listener.onCameraIdle()
        }
    }

    override fun setOnCameraMoveCanceledListener(listener: OnCameraMoveCanceledListener) {
        map.setOnCameraMoveCanceledListener {
            listener.onCameraMoveCanceled()
        }
    }

    override fun setOnCameraMoveListener(listener: OnCameraMoveListener) {
        map.setOnCameraMoveListener {
            listener.onCameraMove()
        }
    }

    override fun setOnCameraMoveStartedListener(listener: OnCameraMoveStartedListener) {
        map.setOnCameraMoveStartedListener { reason ->
            listener.onCameraMoveStarted(reason)
        }
    }

    override fun setOnCircleClickListener(listener: OnCircleClickListener) {
        map.setOnCircleClickListener { circle ->
            listener.onCircleClick(circle.toChoiceCircle())
        }
    }

    override fun setOnGroundOverlayClickListener(listener: OnGroundOverlayClickListener) {
        map.setOnGroundOverlayClickListener { groundOverlay ->
            listener.onGroundOverlayClickListener(groundOverlay.toChoiceGroundOverlay())
        }
    }

    override fun setOnInfoWindowClickListener(listener: OnInfoWindowClickListener) {
        map.setOnInfoWindowClickListener {
            listener.onInfoWindowClick(it.toChoiceMarker())
        }
    }

    override fun setOnInfoWindowCloseListener(listener: OnInfoWindowCloseListener) {
        map.setOnInfoWindowCloseListener {
            listener.onInfoWindowClose(it.toChoiceMarker())
        }
    }

    override fun setOnInfoWindowLongClickListener(listener: OnInfoWindowLongClickListener) {
        map.setOnInfoWindowLongClickListener {
            listener.onInfoWindowLongClick(it.toChoiceMarker())
        }
    }

    override fun setOnMapClickListener(listener: OnMapClickListener) {
        map.setOnMapClickListener {
            listener.onMapClick(it.toChoiceLatLng())
        }
    }

    override fun setOnMapLoadedCallback(callback: OnMapLoadedCallback) {
        map.setOnMapLoadedCallback {
            callback.onMapLoaded()
        }
    }

    override fun setOnMapLongClickListener(listener: OnMapLongClickListener) {
        map.setOnMapLongClickListener {
            listener.onMapLongClick(it.toChoiceLatLng())
        }
    }

    override fun setOnMarkerClickListener(listener: OnMarkerClickListener) {
        map.setOnMarkerClickListener {
            listener.onMarkerClick(it.toChoiceMarker())
        }
    }

    override fun setOnMarkerDragListener(listener: OnMarkerDragListener) {
        map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(marker: com.google.android.gms.maps.model.Marker?) {
                marker?.let {
                    listener.onMarkerDragEnd(marker.toChoiceMarker())
                }
            }

            override fun onMarkerDragStart(marker: com.google.android.gms.maps.model.Marker?) {
                marker?.let {
                    listener.onMarkerDragStart(marker.toChoiceMarker())
                }
            }

            override fun onMarkerDrag(marker: com.google.android.gms.maps.model.Marker?) {
                marker?.let {
                    listener.onMarkerDrag(marker.toChoiceMarker())
                }
            }
        })
    }

    override fun setOnMyLocationButtonClickListener(listener: OnMyLocationButtonClickListener) {
        map.setOnMyLocationButtonClickListener {
            listener.onMyLocationButtonClick()
        }
    }

    override fun setOnMyLocationClickListener(listener: OnMyLocationClickListener) {
        map.setOnMyLocationClickListener { location ->
            listener.onMyLocationClick(location)
        }
    }

    override fun setOnPoiClickListener(listener: OnPoiClickListener) {
        map.setOnPoiClickListener {
            listener.onPoiClick(it.toChoicePoi())
        }
    }

    override fun setOnPolygonClickListener(listener: OnPolygonClickListener) {
        map.setOnPolygonClickListener { polygon ->
            listener.onPolygonClick(polygon.toChoicePolygon())
        }
    }

    override fun setOnPolyLineClickListener(listener: OnPolylineClickListener) {
        map.setOnPolylineClickListener { polyline ->
            listener.onPolylineClick(polyline.toChoicePolyline())
        }
    }

    /***************
     * Other
     ***************/

    override fun addMarker(options: MarkerOptions): Marker {
        return GmsMarker(map.addMarker(options.toGmsMarkerOptions()))
    }

    override fun addCircle(options: CircleOptions): Circle {
        return map.addCircle(options.toGmsCircleOptions()).toChoiceCircle()
    }

    override fun addGroundOverlay(options: GroundOverlayOptions) {
        map.addGroundOverlay(options.toGmsGroundOverlayOptions())
    }

    override fun addPolygon(options: PolygonOptions): Polygon {
        return map.addPolygon(options.toGmsPolygonOptions()).toChoicePolygon()
    }

    override fun addPolyline(options: PolylineOptions): Polyline {
        return map.addPolyline(options.toGmsPolylineOptions()).toChoicePolyline()
    }

    override fun addTileOverlay(options: TileOverlayOptions): TileOverlay {
        val overlay = map.addTileOverlay(options.toGmsTileOverlayOptions())
        return TileOverlay.create(overlay)
    }

    override fun animateCamera(update: CameraUpdate) {
        if (update is CameraUpdate.GmsUpdate) {
            map.animateCamera(update.value)
        }
    }

    override fun animateCamera(update: CameraUpdate, callback: CancelableCallback?) {
        if (update is CameraUpdate.GmsUpdate) {
            map.animateCamera(update.value, createOrReturnCallback(callback))
        }
    }

    override fun animateCamera(
        update: CameraUpdate,
        durationMilliseconds: Int,
        callback: CancelableCallback?
    ) {
        if (update is CameraUpdate.GmsUpdate) {
            map.animateCamera(update.value, durationMilliseconds, createOrReturnCallback(callback))
        }
    }

    override fun getProjection(): Projection {
        return map.projection.toChoiceProjection()
    }

    override fun clear() {
        map.clear()
    }

    override fun getUiSettings(): UiSettings {
        return map.uiSettings.toChoiceUiSettings()
    }

    override fun setMaxZoomPreference(maxZoomPreference: Float) {
        map.setMaxZoomPreference(maxZoomPreference)
    }

    override fun setMinZoomPreference(minZoomPreference: Float) {
        map.setMinZoomPreference(minZoomPreference)
    }

    override fun moveCamera(update: CameraUpdate) {
        if (update is CameraUpdate.GmsUpdate) {
            map.moveCamera(update.value)
        }
    }

    override fun resetMinMaxZoomPreference() {
        map.resetMinMaxZoomPreference()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        map.setPadding(left, top, right, bottom)
    }

    override fun setLatLngBoundsForCameraTarget(bounds: LatLngBounds) {
        map.setLatLngBoundsForCameraTarget(bounds.toGmsLatLngBounds())
    }

    override fun setLocationSource(source: LocationSource?) {
        map.setLocationSource(object : com.google.android.gms.maps.LocationSource {
            override fun deactivate() {
                source?.deactivate()
            }

            override fun activate(listener: com.google.android.gms.maps.LocationSource.OnLocationChangedListener?) {
                source?.activate(object : LocationSource.OnLocationChangedListener {
                    override fun onLocationChanged(location: Location) {
                        listener?.onLocationChanged(location)
                    }
                })
            }
        })
    }

    override fun setContentDescription(description: String) {
        map.setContentDescription(description)
    }

    override fun setInfoWindowAdapter(adapter: InfoWindowAdapter) {
        map.setInfoWindowAdapter(adapter.toGmsInfoWindowAdapter())
    }

    override fun snapshot(listener: OnSnapshotReadyCallback) {
        map.snapshot {
            listener.onSnapShotReady(it)
        }
    }

    override fun snapshot(listener: OnSnapshotReadyCallback, bitmap: Bitmap) {
        map.snapshot(
            { mBitmap -> listener.onSnapShotReady(mBitmap) },
            bitmap
        )
    }

    override fun stopAnimation() {
        map.stopAnimation()
    }

    private fun createOrReturnCallback(callback: CancelableCallback?): GoogleMap.CancelableCallback? {
        return when (callback) {
            null -> {
                callback
            }
            else -> {
                object : GoogleMap.CancelableCallback {
                    override fun onFinish() {
                        callback.onFinish()
                    }

                    override fun onCancel() {
                        callback.onCancel()
                    }
                }
            }
        }
    }

    override fun getGoogleMap(): GoogleMap {
        return map
    }

    override fun getHuaweiMap(): HuaweiMap? {
        return null
    }
}