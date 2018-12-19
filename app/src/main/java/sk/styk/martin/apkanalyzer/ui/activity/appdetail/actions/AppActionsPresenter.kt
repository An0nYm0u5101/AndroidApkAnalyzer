package sk.styk.martin.apkanalyzer.ui.activity.appdetail.actions

import android.os.Bundle
import sk.styk.martin.apkanalyzer.model.detail.AppDetailData
import sk.styk.martin.apkanalyzer.ui.activity.repackageddetection.RepackagedDetectionFragment


/**
 * @author Martin Styk
 * @version 28.01.2018.
 */
class AppActionsPresenter : AppActionsContract.Presenter {

    override lateinit var view: AppActionsContract.View
    override lateinit var appDetailData: AppDetailData

    override fun initialize(bundle: Bundle) {
        appDetailData = bundle.getParcelable(AppActionsContract.PACKAGE_TO_PERFORM_ACTIONS) ?: throw IllegalArgumentException()
    }

    override fun exportClick() = view.startApkExport(appDetailData)

    override fun shareClick() = view.startSharingActivity(appDetailData.generalData.apkDirectory)

    override fun showGooglePlayClick() = view.openGooglePlay(appDetailData.generalData.packageName)

    override fun repackagedDetectionClick() = view.openRepackagedDetection(RepackagedDetectionFragment.newInstance(appDetailData))

    override fun showManifestClick() = view.openManifestActivity(appDetailData)

    override fun showSystemPageClick() = view.openSystemAboutActivity(appDetailData.generalData.packageName)

    override fun installAppClick() = view.startApkInstall(appDetailData.generalData.apkDirectory)

    override fun saveIconClick() = view.startIconSave(appDetailData)

    override fun showMoreClick() = view.showMoreActionsDialog(appDetailData)
}