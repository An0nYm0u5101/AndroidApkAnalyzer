package sk.styk.martin.apkanalyzer.ui.activity.appdetail.actions

import android.content.Context
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import sk.styk.martin.apkanalyzer.R
import sk.styk.martin.apkanalyzer.ui.customview.SpeedDialMenuAdapter
import sk.styk.martin.apkanalyzer.ui.customview.SpeedDialMenuItem
import java.lang.ref.WeakReference

/**
 * @author Martin Styk {@literal <martin.styk@gmail.com>}
 */
class ApkFileActionsSpeedMenu(actionPresenter: AppActionsContract.Presenter) : SpeedDialMenuAdapter() {

    private val presenterRef: WeakReference<AppActionsContract.Presenter> = WeakReference(actionPresenter)

    override fun getCount(): Int = 6

    enum class AppActions {
        INSTALL,
        COPY,
        SHARE,
        SAVE_ICON,
        SHOW_MANIFEST,
        OPEN_PLAY
    }

    override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem = when (position) {
        AppActions.INSTALL.ordinal -> SpeedDialMenuItem(context, R.drawable.ic_allow, context.getString(R.string.install_app))
        AppActions.COPY.ordinal -> SpeedDialMenuItem(context, R.drawable.ic_allow, context.getString(R.string.copy_apk))
        AppActions.SHARE.ordinal -> SpeedDialMenuItem(context, R.drawable.ic_allow, context.getString(R.string.share_apk))
        AppActions.SAVE_ICON.ordinal -> SpeedDialMenuItem(context, R.drawable.ic_allow, context.getString(R.string.save_icon))
        AppActions.SHOW_MANIFEST.ordinal -> SpeedDialMenuItem(context, R.drawable.ic_allow, context.getString(R.string.show_manifest))
        AppActions.OPEN_PLAY.ordinal -> SpeedDialMenuItem(context, R.drawable.ic_allow, context.getString(R.string.show_app_google_play))
        else -> throw IllegalArgumentException("No menu item: $position")
    }

    override fun onMenuItemClick(position: Int): Boolean = presenterRef.get()?.let { presenter ->
        when (position) {
            AppActions.INSTALL.ordinal -> presenter.installAppClick()
            AppActions.COPY.ordinal -> presenter.exportClick()
            AppActions.SHARE.ordinal -> presenter.shareClick()
            AppActions.SAVE_ICON.ordinal -> presenter.saveIconClick()
            AppActions.SHOW_MANIFEST.ordinal -> presenter.showManifestClick()
            AppActions.OPEN_PLAY.ordinal -> presenter.showGooglePlayClick()
            else -> throw IllegalArgumentException("No menu item: $position")
        }
        true
    } ?: false

    @ColorInt
    override fun getBackgroundColour(position: Int, context: Context) = ContextCompat.getColor(context, R.color.colorLightGrey)


    override fun fabRotationDegrees(): Float = 135F
}
