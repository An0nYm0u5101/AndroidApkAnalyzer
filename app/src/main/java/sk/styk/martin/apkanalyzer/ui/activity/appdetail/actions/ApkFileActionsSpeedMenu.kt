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
class ApkFileActionsSpeedMenu(actionPresenter: AppActionsContract.Presenter) : AppActionsSpeedMenu(actionPresenter) {

    override val menuItems = listOf(
            AppActions.OPEN_PLAY,
            AppActions.SAVE_ICON,
            AppActions.SHARE,
            AppActions.COPY,
            AppActions.SHOW_MANIFEST,
            AppActions.INSTALL
            )
}
