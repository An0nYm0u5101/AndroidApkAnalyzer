package sk.styk.martin.apkanalyzer.ui.activity.appdetail.actions

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_apk_actions.*
import sk.styk.martin.apkanalyzer.R
import sk.styk.martin.apkanalyzer.model.detail.AppDetailData
import sk.styk.martin.apkanalyzer.ui.activity.appdetail.actions.AppActionsContract.Companion.PACKAGE_TO_PERFORM_ACTIONS
import sk.styk.martin.apkanalyzer.ui.activity.appdetail.pager.AppDetailPagerFragment.Companion.TAG


/**
 * @author Martin Styk
 * @version 05.01.2018.
 */
class AppActionsDialog : DialogFragment() {

    private lateinit var presenter: AppActionsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = AppActionsPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_apk_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = fragmentManager?.findFragmentByTag(TAG) as AppActionsContract.View
        presenter.initialize(arguments ?: Bundle())
        setUpViews()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
                .setView(R.layout.dialog_apk_actions)
                .setTitle(R.string.pick_action)
                .setNegativeButton(R.string.dismiss) { _, _ -> dismiss() }
                .create()
    }

    private fun setUpViews() {
        // setup buttons
        dialog.btn_copy.setOnClickListener { presenter.exportClick() }

        dialog.btn_share_apk.setOnClickListener { presenter.shareClick() }

        dialog.btn_save_icon.setOnClickListener { presenter.saveIconClick() }

        dialog.btn_show_app_google_play.setOnClickListener { presenter.showGooglePlayClick() }

//        dialog.btn_repackaged_detection.setOnClickListener { pagerPresenter.repackagedDetectionClick() }

        dialog.btn_show_manifest.setOnClickListener { presenter.showManifestClick() }

        dialog.btn_show_app_system_page.setOnClickListener { presenter.showSystemPageClick() }

        dialog.btn_install_app.setOnClickListener { presenter.installAppClick() }

        if (presenter.appDetailData.isAnalyzedApkFile) {
            dialog.btn_show_manifest.visibility = View.GONE
            dialog.btn_show_app_system_page.visibility = View.GONE
            dialog.btn_install_app.visibility = View.VISIBLE
        }
    }

    companion object {
        const val ACTIONS_MORE_REQUEST = 123453

        fun newInstance(appDetailData: AppDetailData): AppActionsDialog {
            val frag = AppActionsDialog()
            val args = Bundle()
            args.putParcelable(PACKAGE_TO_PERFORM_ACTIONS, appDetailData)
            frag.arguments = args
            return frag
        }
    }
}