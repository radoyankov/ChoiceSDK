package at.bluesource.choicesdk.maps.gms

import android.os.Parcel
import at.bluesource.choicesdk.maps.common.TileProvider
import at.bluesource.choicesdk.maps.common.TileProvider.Companion.toChoiceTileProvider
import at.bluesource.choicesdk.maps.common.TileProvider.Companion.toGmsTileProvider
import at.bluesource.choicesdk.maps.common.options.TileOverlayOptions

/**
 * Wrapper class for gms version of TileOverlayOptions
 *
 * @property tileOverlayOptions gms TileOverlayOptions instance
 * @see com.google.android.gms.maps.model.TileOverlayOptions
 */
internal class GmsTileOverlayOptions(private val tileOverlayOptions: com.google.android.gms.maps.model.TileOverlayOptions) :
    TileOverlayOptions {
    override fun fadeIn(fadeIn: Boolean): TileOverlayOptions {
        tileOverlayOptions.fadeIn(fadeIn)
        return this
    }

    override fun getFadeIn(): Boolean {
        return tileOverlayOptions.fadeIn
    }

    override fun getTileProvider(): TileProvider {
        return tileOverlayOptions.tileProvider.toChoiceTileProvider()
    }

    override fun getTransparency(): Float {
        return tileOverlayOptions.transparency
    }

    override fun getZIndex(): Float {
        return tileOverlayOptions.zIndex
    }

    override fun isVisible(): Boolean {
        return tileOverlayOptions.isVisible
    }

    override fun tileProvider(tileProvider: TileProvider): TileOverlayOptions {
        tileOverlayOptions.tileProvider(tileProvider.toGmsTileProvider())
        return this
    }

    override fun transparency(transparency: Float): TileOverlayOptions {
        tileOverlayOptions.transparency(transparency)
        return this
    }

    override fun visible(visible: Boolean): TileOverlayOptions {
        tileOverlayOptions.visible(visible)
        return this
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        tileOverlayOptions.writeToParcel(out, flags)
    }

    override fun zIndex(zIndex: Float): TileOverlayOptions {
        tileOverlayOptions.zIndex(zIndex)
        return this
    }

    internal fun getTileOverlayOptions(): com.google.android.gms.maps.model.TileOverlayOptions {
        return this.tileOverlayOptions
    }
}

